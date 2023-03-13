package org.cinehub.api;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

import org.cinehub.api.model.Movie;
import org.cinehub.api.model.Projection;
import org.cinehub.api.model.Reservation;
import org.cinehub.api.model.Room;
import org.cinehub.api.model.SpecialSeat;
import org.cinehub.api.model.SeatReservation;
import org.cinehub.api.model.User;
import org.cinehub.api.result.OnFailureCallback;
import org.cinehub.api.result.OnSuccessCallback;
import org.cinehub.api.result.OnSuccessValueCallback;

import java.util.ArrayList;

public class CinehubAPI implements CinehubAuth, CinehubDB {

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
                dbRef.child(getDBRef(User.class)).push().setValue(new User(name, email))
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

    // ********* DB *********

    // ** Movies **

    public void getMovies(
        OnSuccessValueCallback<ArrayList<Movie>> onSuccessValueCallback,
        OnFailureCallback<String> onFailureCallback
    ) {
        getAll(Movie.class, onSuccessValueCallback, onFailureCallback);
    }

    public void getMovie(
        int movieId,
        OnSuccessValueCallback<Movie> onSuccessValueCallback,
        OnFailureCallback<String> onFailureCallback
    ) {
        get(Movie.class, movieId, onSuccessValueCallback, onFailureCallback);
    }

    // ** Projection **

    public void getProjections(
        OnSuccessValueCallback<ArrayList<Projection>> onSuccessValueCallback,
        OnFailureCallback<String> onFailureCallback
    ) {
        getAll(Projection.class, onSuccessValueCallback, onFailureCallback);
    }

    public void getProjection(
        int projectionId,
        OnSuccessValueCallback<Projection> onSuccessValueCallback,
        OnFailureCallback<String> onFailureCallback
    ) {
        get(Projection.class, projectionId, onSuccessValueCallback, onFailureCallback);
    }

    public void getProjectionConfiguration(
        int projectionId,
        OnSuccessValueCallback<char[][]> onSuccessValueCallback,
        OnFailureCallback<String> onFailureCallback
    ) {
        getProjection(
            projectionId,
            projection -> getRoomConfiguration(
                    projection.getRoom(),
                    roomArr -> getSeatReservationsProjection(
                        projectionId,
                        lstSeatReservations -> {
                            for (SeatReservation r : lstSeatReservations)
                                roomArr[r.getRow()][r.getCol()] = SpecialSeat.OCCUPIED;
                            execute(onSuccessValueCallback, roomArr);
                        },
                        onFailureCallback
                    ),
                    onFailureCallback
            ),
            onFailureCallback
        );
    }

    // ** Reservation **

    public void getReservations(
        OnSuccessValueCallback<ArrayList<Reservation>> onSuccessValueCallback,
        OnFailureCallback<String> onFailureCallback
    ) {
        getAll(Reservation.class, onSuccessValueCallback, onFailureCallback);
    }

    public void getReservation(
        int reservationId,
        OnSuccessValueCallback<Reservation> onSuccessValueCallback,
        OnFailureCallback<String> onFailureCallback
    ) {
        get(Reservation.class, reservationId, onSuccessValueCallback, onFailureCallback);
    }

    // ** Room **
    public void getRooms(
        OnSuccessValueCallback<ArrayList<Room>> onSuccessValueCallback,
        OnFailureCallback<String> onFailureCallback
    ) {
        getAll(Room.class, onSuccessValueCallback, onFailureCallback);
    }

    public void getRoom(
        int roomId,
        OnSuccessValueCallback<Room> onSuccessValueCallback,
        OnFailureCallback<String> onFailureCallback
    ) {
        get(Room.class, roomId, onSuccessValueCallback, onFailureCallback);
    }

    public void getRoomConfiguration(
        int roomId,
        OnSuccessValueCallback<char[][]> onSuccessValueCallback,
        OnFailureCallback<String> onFailureCallback
    ) {
        getRoom(
            roomId,
            room -> getSpecialSeatsRoom(
                roomId,
                lstSeats -> {
                    char[][] seats = new char[room.getRows()][room.getCols()];
                    for (int i = 0; i < room.getRows(); i++)
                        for (int j = 0; j < room.getCols(); j++)
                            seats[i][j] = SpecialSeat.FREE;
                    for (SpecialSeat seat : lstSeats)
                        seats[seat.getRow()][seat.getCol()] = seat.getType();
                    execute(onSuccessValueCallback, seats);
                },
                onFailureCallback
            ),
            onFailureCallback
        );
    }

    // ** SeatReservation **
    public void getSeatReservations(
        OnSuccessValueCallback<ArrayList<SeatReservation>> onSuccessValueCallback,
        OnFailureCallback<String> onFailureCallback
    ) {
        getAll(SeatReservation.class, onSuccessValueCallback, onFailureCallback);
    }

    public void getSeatReservation(
        int seatReservationId,
        OnSuccessValueCallback<SeatReservation> onSuccessValueCallback,
        OnFailureCallback<String> onFailureCallback
    ) {
        get(SeatReservation.class, seatReservationId, onSuccessValueCallback, onFailureCallback);
    }

    /**
     * Gets all the seat reservations for a projection.
     * @param projectionId the projection id.
     * @param onSuccessValueCallback the callback to execute on success.
     * @param onFailureCallback the callback to execute on failure.
     */
    protected void getSeatReservationsProjection(
        int projectionId,
        OnSuccessValueCallback<ArrayList<SeatReservation>> onSuccessValueCallback,
        OnFailureCallback<String> onFailureCallback
    ) {
        getSeatReservations(
            reservations -> {
                ArrayList<SeatReservation> lstReservations = new ArrayList<>();
                for (SeatReservation r : reservations)
                    if (r.getProjection() == projectionId)
                        lstReservations.add(r);
                execute(onSuccessValueCallback, lstReservations);
            },
            onFailureCallback
        );
    }

    // ** SpecialSeat **
    public void getSpecialSeats(
        OnSuccessValueCallback<ArrayList<SpecialSeat>> onSuccessValueCallback,
        OnFailureCallback<String> onFailureCallback
    ) {
        getAll(SpecialSeat.class, onSuccessValueCallback, onFailureCallback);
    }

    public void getSpecialSeat(
        int roomConfigurationId,
        OnSuccessValueCallback<SpecialSeat> onSuccessValueCallback,
        OnFailureCallback<String> onFailureCallback
    ) {
        get(SpecialSeat.class, roomConfigurationId, onSuccessValueCallback, onFailureCallback);
    }

    /**
     * Gets all the special seats for a room.
     *
     * @param roomId the room id.
     * @param onSuccessValueCallback the callback to execute on success.
     * @param onFailureCallback the callback to execute on failure.
     */
    protected void getSpecialSeatsRoom(
        int roomId,
        OnSuccessValueCallback<ArrayList<SpecialSeat>> onSuccessValueCallback,
        OnFailureCallback<String> onFailureCallback
    ) {
        getSpecialSeats(
            specialSeats -> {
                ArrayList<SpecialSeat> set = new ArrayList<>();
                for (SpecialSeat seat : specialSeats) {
                    if (seat.getRoom() != roomId)
                        set.add(seat);
                }
                execute(onSuccessValueCallback, set);
            },
            onFailureCallback
        );
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

    public void getUserById(
        int id,
        OnSuccessValueCallback<User> onSuccessListener,
        OnFailureCallback<String> onFailureCallback
    ) {
        get(User.class, id, onSuccessListener, onFailureCallback);
    }

    // ********* Storage *********

    // TODO storage
    // TODO implement storage interface

    // ********* Utils *********
    protected <T> void getAll(
        Class<T> clazz,
        OnSuccessValueCallback<ArrayList<T>> onSuccessListener,
        OnFailureCallback<String> onFailureCallback
    ) {
        dbRef.child(getDBRef(clazz)).get().addOnSuccessListener(dataSnapshot -> {
            ArrayList<T> list = new ArrayList<>();
            for (DataSnapshot snap : dataSnapshot.getChildren())
                list.add(snap.getValue(clazz));
            execute(onSuccessListener, list);
        }).addOnFailureListener(e -> execute(onFailureCallback, e.getMessage()));
    }

    protected <T> void get(
        Class<T> clazz,
        int id,
        OnSuccessValueCallback<T> onSuccessListener,
        OnFailureCallback<String> onFailureCallback
    ) {
        if (id < 0) {
            execute(onFailureCallback, "Invalid id");
            return;
        }
        dbRef.child(getDBRef(clazz)).child(String.valueOf(id)).get()
            .addOnSuccessListener(dataSnapshot -> {
                T obj = dataSnapshot.getValue(clazz);
                if (obj == null)
                    execute(onFailureCallback, clazz.getName() + " not found");
                else
                    execute(onSuccessListener, obj);
            })
            .addOnFailureListener(e -> execute(onFailureCallback, e.getMessage()));
    }

    protected String getDBRef(Class<?> clazz) {
        String[] arr = clazz.getName().split("\\.");
        return arr[arr.length - 1].replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
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
