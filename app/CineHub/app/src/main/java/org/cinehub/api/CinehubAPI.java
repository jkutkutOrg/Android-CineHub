package org.cinehub.api;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

import org.cinehub.api.result.OnFailureListener;
import org.cinehub.api.result.OnSuccessListener;
import org.cinehub.api.result.OnSuccessValueListener;

public class CinehubAPI implements CinehubAuth {

    // TODO implement db interface
    // TODO implement storage interface

    private static final String DB_REF = "db";

    private static CinehubAPI instance;

    private final FirebaseAuth auth;
    private final DatabaseReference dbRef;
    private final StorageReference storageRef;

    public static CinehubAuth getAuthInstance() {
        if (instance == null)
            instance = new CinehubAPI();
        return instance;
    }

    public CinehubAPI() {
        auth = FirebaseAuth.getInstance();
        dbRef = null;
//        dbRef = FirebaseDatabase.getInstance().getReference().child(DB_REF);
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
}
