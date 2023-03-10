package org.cinehub.api;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

import org.cinehub.api.model.User;
import org.cinehub.api.result.OnFailureCallback;
import org.cinehub.api.result.OnSuccessCallback;
import org.cinehub.api.result.OnSuccessValueCallback;

import java.util.ArrayList;

public class CinehubAPI implements CinehubAuth, CinehubDB {

    // TODO implement db interface
    // TODO implement storage interface

    private static final String DB_REF = "db";

    private static CinehubAPI instance;

    private final FirebaseAuth auth;
    private final DatabaseReference dbRef;
    private final StorageReference storageRef;

    protected static CinehubAPI getInstance() {
        if (instance == null)
            instance = new CinehubAPI();
        return instance;
    }

    public static CinehubAuth getAuthInstance() {
        return getInstance();
    }

    public static CinehubDB getDBInstance() {
        return getInstance();
    }

    public CinehubAPI() {
        auth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference().child(DB_REF);
        storageRef = null; // TODO
    }

    // ********* Auth *********

    @Override
    public void login(
        String email, String password,
        OnSuccessCallback onSuccessCallback,
        OnFailureCallback<String> onFailureCallback
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener(authResult -> execute(onSuccessCallback))
            .addOnFailureListener(e -> execute(onFailureCallback, e.getMessage()));
    }

    @Override
    public void signup(
        String name, String email, String password,
        OnSuccessCallback onSuccessCallback,
        OnFailureCallback<String> onFailureCallback
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener(authResult -> {
                // TODO add user to db
                execute(onSuccessCallback);
            })
            .addOnFailureListener(e -> execute(onFailureCallback, e.getMessage()));
    }

    @Override
    public void autoLogin(
        OnSuccessValueCallback<String> onSuccessListener,
        OnFailureCallback<String> onFailureListener
    ) {
        FirebaseUser usr = auth.getCurrentUser();
        if (usr == null)
            execute(onFailureListener, "Not logged in");
        else
            execute(onSuccessListener, usr.getEmail());
    }

    // ********* DB *********
    public void getUsers(
        OnSuccessValueCallback<ArrayList<User>> onSuccessListener,
        OnFailureCallback<String> onFailureCallback
    ) {
        dbRef.child(User.DB_REF).get().addOnSuccessListener(dataSnapshot -> {
            ArrayList<User> users = new ArrayList<>();
            for (DataSnapshot userSnap : dataSnapshot.getChildren())
                users.add(userSnap.getValue(User.class));
            execute(onSuccessListener, users);
        }).addOnFailureListener(e -> execute(onFailureCallback, e.getMessage()));
    }

    public void getUser(
        String email,
        OnSuccessValueCallback<User> onSuccessListener,
        OnFailureCallback<String> onFailureCallback
    ) {
        getUsers(
            users -> {
                for (User user : users)
                    if (user.getEmail().equals(email)) {
                        execute(onSuccessListener, user);
                        return;
                    }
                execute(onFailureCallback, "User not found");
            },
            onFailureCallback
        );
    }

    public void whoami(
        OnSuccessValueCallback<User> onSuccessListener,
        OnFailureCallback<String> onFailureCallback
    ) {
        FirebaseUser usr = auth.getCurrentUser();
        if (usr == null) {
            execute(onFailureCallback, "Not logged in");
            return;
        }
        getUser(usr.getEmail(), onSuccessListener, onFailureCallback);
    }

    // ********* Storage *********

    // ********* Utils *********
    protected void execute(OnSuccessCallback callback) {
        if (callback != null)
            callback.onSuccess();
    }
    protected <T> void execute(OnSuccessValueCallback<T> callback, T data) {
        if (callback != null)
            callback.onSuccess(data);
    }
    protected <T> void execute(OnFailureCallback<T> callback, T error) {
        if (callback != null)
            callback.onFailure(error);
    }
}
