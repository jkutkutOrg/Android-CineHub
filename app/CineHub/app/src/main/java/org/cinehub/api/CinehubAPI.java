package org.cinehub.api;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

import org.cinehub.api.model.CinehubModel;
import org.cinehub.api.model.Movie;
import org.cinehub.api.model.Projection;
import org.cinehub.api.model.Reservation;
import org.cinehub.api.model.Room;
import org.cinehub.api.model.RoomConfiguration;
import org.cinehub.api.model.SeatReservation;
import org.cinehub.api.model.User;
import org.cinehub.api.result.OnFailureCallback;
import org.cinehub.api.result.OnSuccessCallback;
import org.cinehub.api.result.OnSuccessValueCallback;

import java.util.ArrayList;

public class CinehubAPI implements CinehubAuth, CinehubDB {

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
                dbRef.child(User.getDBRef()).push().setValue(new User(name, email))
                    .addOnSuccessListener(aVoid -> execute(onSuccessCallback))
                    .addOnFailureListener(e -> execute(onFailureCallback, e.getMessage()));
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

    // ** Movies **

    public void getMovies(
        OnSuccessValueCallback<ArrayList<Movie>> onSuccessValueCallback,
        OnFailureCallback<String> onFailureCallback
    ) {
        getAll(Movie.class, onSuccessValueCallback, onFailureCallback);
    }

    // ** Projection **

    public void getProjections(
        OnSuccessValueCallback<ArrayList<Projection>> onSuccessValueCallback,
        OnFailureCallback<String> onFailureCallback
    ) {
        getAll(Projection.class, onSuccessValueCallback, onFailureCallback);
    }

    // ** Reservation **

    public void getReservations(
        OnSuccessValueCallback<ArrayList<Reservation>> onSuccessValueCallback,
        OnFailureCallback<String> onFailureCallback
    ) {
        getAll(Reservation.class, onSuccessValueCallback, onFailureCallback);
    }

    // ** Room **
    public void getRooms(
        OnSuccessValueCallback<ArrayList<Room>> onSuccessValueCallback,
        OnFailureCallback<String> onFailureCallback
    ) {
        getAll(Room.class, onSuccessValueCallback, onFailureCallback);
    }

    // ** RoomConfiguration **
    public void getRoomConfigurations(
        OnSuccessValueCallback<ArrayList<RoomConfiguration>> onSuccessValueCallback,
        OnFailureCallback<String> onFailureCallback
    ) {
        getAll(RoomConfiguration.class, onSuccessValueCallback, onFailureCallback);
    }

    // ** SeatReservation **
    public void getSeatReservations(
        OnSuccessValueCallback<ArrayList<SeatReservation>> onSuccessValueCallback,
        OnFailureCallback<String> onFailureCallback
    ) {
        getAll(SeatReservation.class, onSuccessValueCallback, onFailureCallback);
    }

    // ** Users **
    public void getUsers(
        OnSuccessValueCallback<ArrayList<User>> onSuccessListener,
        OnFailureCallback<String> onFailureCallback
    ) {
        getAll(User.class, onSuccessListener, onFailureCallback);
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

    public void getUserById( // TODO doc
        int id,
        OnSuccessValueCallback<User> onSuccessListener,
        OnFailureCallback<String> onFailureCallback
    ) {
        if (id < 0) {
            execute(onFailureCallback, "Invalid id");
            return;
        }
        dbRef.child(User.getDBRef()).child(String.valueOf(id)).get()
            .addOnSuccessListener(dataSnapshot -> {
                User user = dataSnapshot.getValue(User.class);
                if (user == null)
                    execute(onFailureCallback, "User not found");
                else
                    execute(onSuccessListener, user);
            })
            .addOnFailureListener(e -> execute(onFailureCallback, e.getMessage()));
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

    // TODO

    // ********* Utils *********
    protected <T extends CinehubModel> void getAll(
        Class<T> clazz,
        OnSuccessValueCallback<ArrayList<T>> onSuccessListener,
        OnFailureCallback<String> onFailureCallback
    ) {
        dbRef.child(T.getDBRef()).get().addOnSuccessListener(dataSnapshot -> {
            ArrayList<T> list = new ArrayList<>();
            for (DataSnapshot snap : dataSnapshot.getChildren())
                list.add(snap.getValue(clazz));
            execute(onSuccessListener, list);
        }).addOnFailureListener(e -> execute(onFailureCallback, e.getMessage()));
    }

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
