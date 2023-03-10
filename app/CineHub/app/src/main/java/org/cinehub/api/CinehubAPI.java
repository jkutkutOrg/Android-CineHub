package org.cinehub.api;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

import org.cinehub.api.model.User;
import org.cinehub.api.result.OnFailureListener;
import org.cinehub.api.result.OnSuccessListener;
import org.cinehub.api.result.OnSuccessValueListener;

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
        OnSuccessListener onSuccessListener,
        OnFailureListener<String> onFailureListener
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener(authResult -> onSuccessListener.onSuccess())
            .addOnFailureListener(e -> onFailureListener.onFailure(e.getMessage()));
    }

    @Override
    public void signup(
        String name, String email, String password,
        OnSuccessListener onSuccessListener,
        OnFailureListener<String> onFailureListener
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener(authResult -> {
                // TODO add user to db
                onSuccessListener.onSuccess();
            })
            .addOnFailureListener(e -> onFailureListener.onFailure(e.getMessage()));
    }

    @Override
    public void autoLogin(
        OnSuccessValueListener<String> onSuccessListener
    ) {
        FirebaseUser usr = auth.getCurrentUser();
        if (usr != null)
            onSuccessListener.onSuccess(usr.getEmail());
    }

    // ********* DB *********
    public void getUsers(
        OnSuccessValueListener<ArrayList<User>> onSuccessListener,
        OnFailureListener<String> onFailureListener
    ) {
        dbRef.child(User.DB_REF).get().addOnSuccessListener(dataSnapshot -> {
            ArrayList<User> users = new ArrayList<>();
            for (DataSnapshot userSnap : dataSnapshot.getChildren())
                users.add(userSnap.getValue(User.class));
            onSuccessListener.onSuccess(users);
        }).addOnFailureListener(e -> onFailureListener.onFailure(e.getMessage()));
    }

    public void getUser(
        String email,
        OnSuccessValueListener<User> onSuccessListener,
        OnFailureListener<String> onFailureListener
    ) {
        getUsers(
            users -> {
                for (User user : users)
                    if (user.getEmail().equals(email)) {
                        onSuccessListener.onSuccess(user);
                        return;
                    }
                onFailureListener.onFailure("User not found");
            },
            onFailureListener
        );
    }

    public void whoami(
        OnSuccessValueListener<User> onSuccessListener,
        OnFailureListener<String> onFailureListener
    ) {
        FirebaseUser usr = auth.getCurrentUser();
        if (usr == null) {
            onFailureListener.onFailure("Not logged in");
            return;
        }
        getUser(usr.getEmail(), onSuccessListener, onFailureListener);
    }
}
