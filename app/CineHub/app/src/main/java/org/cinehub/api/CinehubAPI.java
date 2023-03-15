package org.cinehub.api;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.cinehub.api.model.Movie;
import org.cinehub.api.model.Projection;
import org.cinehub.api.model.Reservation;
import org.cinehub.api.model.Room;
import org.cinehub.api.model.Seat;
import org.cinehub.api.model.SpecialSeat;
import org.cinehub.api.model.SeatReservation;
import org.cinehub.api.model.User;
import org.cinehub.api.result.OnFailureCallback;
import org.cinehub.api.result.OnSuccessCallback;
import org.cinehub.api.result.OnSuccessValueCallback;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class CinehubAPI implements CinehubAuth, CinehubDB, CinehubStorage {

    private static final String DB_REF = "db";
    private static final String IMG_REF = "img";

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

    public static CinehubStorage getStorageInstance() {
        return getInstance();
    }

    private CinehubAPI() {
        auth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference().child(DB_REF);
        storageRef = FirebaseStorage.getInstance().getReference();
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
            .addOnSuccessListener(authResult -> append(
                User.class, new User(email, name),
                s -> execute(onSuccessCallback), onFailureCallback
            ))
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

    public void getMoviesWithBanner(
        OnSuccessValueCallback<ArrayList<Movie>> onSuccessValueCallback,
        OnFailureCallback<String> onFailureCallback
    ) {
        getMovies(
            movies -> concatBannerCalls(
                movies,
                0,
                onSuccessValueCallback,
                onFailureCallback
            ),
            onFailureCallback
        );
    }

    protected void concatBannerCalls(
        ArrayList<Movie> movies,
        int index,
       OnSuccessValueCallback<ArrayList<Movie>> onSuccessValueCallback,
       OnFailureCallback<String> onFailureCallback
    ) {
        if (index >= movies.size()) {
            onSuccessValueCallback.onSuccess(movies);
            return;
        }
        Movie currentMovie = movies.get(index);
        getBanner(
            currentMovie,
            imgUrl -> {
                System.out.println(currentMovie.getName() + " " + imgUrl);
                currentMovie.setBanner(imgUrl);
                concatBannerCalls(
                    movies,
                    index + 1,
                    onSuccessValueCallback,
                    onFailureCallback
                );
            },
            onFailureCallback
        );
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

    public void addReservation(
        User usr,
        Projection projection,
        ArrayList<Seat> seats,
        OnSuccessCallback onSuccessCallback,
        OnFailureCallback<String> onFailureCallback
    ) {
        getId(
            projection,
            projectionId -> ensureSeatsAvailable(
                projectionId,
                seats,
                () -> getId(
                    usr,
                    usrId -> append(
                        Reservation.class,
                        new Reservation(usrId),
                        newReservationId -> {
                            int reservationId = newReservationId - 1;
                            ArrayList<SeatReservation> rlst = new ArrayList<>();
                            for (Seat s : seats)
                                rlst.add(new SeatReservation(
                                    projectionId, s.getRow(), s.getCol(), reservationId
                                ));
                            append(
                                SeatReservation.class, rlst,
                                size -> execute(onSuccessCallback),
                                onFailureCallback
                            );
                        },
                        onFailureCallback
                    ),
                    onFailureCallback
                ),
                onFailureCallback
            ),
            onFailureCallback
        );
    }

    protected void getReservationsIdsUser(
        int usrId,
        OnSuccessValueCallback<ArrayList<Integer>> onSuccessValueCallback,
        OnFailureCallback<String> onFailureCallback
    ) {
        getReservations(
            lstReservations -> {
                ArrayList<Integer> rlst = new ArrayList<>();
                for (int i = 0; i < lstReservations.size(); i++)
                    if (lstReservations.get(i).getUser() == usrId)
                        rlst.add(i);
                execute(onSuccessValueCallback, rlst);
            },
            onFailureCallback
        );
    }

    public void getReservationsIdsUser(
        User user,
        OnSuccessValueCallback<ArrayList<Integer>> onSuccessValueCallback,
        OnFailureCallback<String> onFailureCallback
    ) {
        getId(
            user,
            usrId -> getReservationsIdsUser(
                usrId,
                onSuccessValueCallback,
                onFailureCallback
            ),
            onFailureCallback
        );
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

    public void getSeatReservationUser(
        User usr,
        OnSuccessValueCallback<ArrayList<SeatReservation>> onSuccessValueCallback,
        OnFailureCallback<String> onFailureCallback
    ) {
        getId(
            usr,
            usrId -> getReservationsIdsUser(
                usrId,
                reservationsIds -> getSeatReservations(
                    lstSeatReservations -> {
                        ArrayList<SeatReservation> lst = new ArrayList<>();
                        for (SeatReservation s : lstSeatReservations)
                            for (Integer j : reservationsIds)
                                if (s.getReservation() == j)
                                    lst.add(s);
                        execute(onSuccessValueCallback, lst);
                    },
                    onFailureCallback
                ),
                onFailureCallback
            ),
            onFailureCallback
        );
    }

    public void getSeatReservationReservation(
        int reservationId,
        OnSuccessValueCallback<ArrayList<SeatReservation>> onSuccessValueCallback,
        OnFailureCallback<String> onFailureCallback
    ) {
        getSeatReservations(
            lstSeatReservations -> {
                ArrayList<SeatReservation> lst = new ArrayList<>();
                for (SeatReservation s : lstSeatReservations)
                    if (s.getReservation() == reservationId)
                        lst.add(s);
                execute(onSuccessValueCallback, lst);
            },
            onFailureCallback
        );
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

    /**
     * Ensures that the seats are available to be reserved.
     *
     * @param projectionId the projection id.
     * @param seats the seats to be reserved.
     * @param onSuccessCallback the callback to execute on success.
     * @param onFailureCallback the callback to execute on failure.
     */
    protected void ensureSeatsAvailable(
            int projectionId,
            ArrayList<Seat> seats,
            OnSuccessCallback onSuccessCallback,
            OnFailureCallback<String> onFailureCallback
    ) {
        // Note: RoomConfiguration unverified
        getSeatReservationsProjection(
            projectionId,
            reservations -> {
                for (Seat s : seats) {
                    for (SeatReservation r : reservations) {
                        if (r.getRow() == s.getRow() && r.getCol() == s.getCol()) {
                            execute(onFailureCallback, "Seat already reserved");
                            return;
                        }
                    }
                }
                execute(onSuccessCallback);
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
                    if (Objects.equals(user.getEmail(), email)) {
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

    public void getBanner(
            Movie movie,
            OnSuccessValueCallback<String> onSuccessValueCallback,
            OnFailureCallback<String> onFailureCallback
    ) {
        String img = movie.getBanner() + ".png";
        StorageReference s = storageRef.child(IMG_REF).child(img);
        s.getDownloadUrl().addOnSuccessListener(
            uri -> execute(onSuccessValueCallback, uri.toString())
        ).addOnFailureListener(
            e -> execute(onFailureCallback, e.getMessage())
        );
    }

    // ********* Utils *********

    /**
     * Obtains all the objects from the given class in the database.
     *
     * @param clazz the class of the objects.
     * @param onSuccessListener the callback to execute on success.
     * @param onFailureCallback the callback to execute on failure.
     * @param <T> The model class.
     */
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

    /**
     * Obtains the object with the given id from the given class in the database.
     *
     * @param clazz the class of the object.
     * @param id the id of the object.
     * @param onSuccessListener the callback to execute on success.
     * @param onFailureCallback the callback to execute on failure.
     * @param <T> The model class.
     */
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

    /**
     * Obtains the id of the given object in the database.
     *
     * @param data the object to search.
     * @param onSuccessValueCallback the callback to execute on success.
     * @param onFailureCallback the callback to execute on failure.
     * @param <T> The model class.
     */
    protected <T> void getId(
        T data,
        OnSuccessValueCallback<Integer> onSuccessValueCallback,
        OnFailureCallback<String> onFailureCallback
    ) {
        getAll(
            data.getClass(),
            list -> {
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).equals(data)) {
                        execute(onSuccessValueCallback, i);
                        return;
                    }
                }
                execute(onFailureCallback, data.getClass().getName() + " not found");
            },
            onFailureCallback
        );
    }

    /**
     * Obtains the amount of elements of the given class in the database.
     *
     * @param clazz the class of the objects.
     * @param onSuccessValueCallback the callback to execute on success.
     * @param onFailureCallback the callback to execute on failure.
     * @param <T> The model class.
     */
    protected <T> void getSize(
        Class<T> clazz,
        OnSuccessValueCallback<Integer> onSuccessValueCallback,
        OnFailureCallback<String> onFailureCallback
    ) {
        dbRef.child(getDBRef(clazz)).get()
            .addOnSuccessListener(d ->
                execute(onSuccessValueCallback, (int) d.getChildrenCount())
            )
            .addOnFailureListener(e -> execute(onFailureCallback, e.getMessage()));
    }

    /**
     * Adds the given objects to the database.
     *
     * @param clazz the class of the object.
     * @param lst the list of objects to add.
     * @param onSuccessCallback the callback to execute on success.
     * @param onFailureCallback the callback to execute on failure.
     * @param <T> The model class.
     */
    protected <T> void append(
        Class<T> clazz,
        ArrayList<T> lst,
        OnSuccessValueCallback<Integer> onSuccessCallback,
        OnFailureCallback<String> onFailureCallback
    ) {
        if (lst.isEmpty()) {
            execute(onFailureCallback, "Empty list");
            return;
        }
        getSize(
            clazz,
            size -> {
                Method[] getters = getGetters(clazz);
                String[] attrs = Arrays.stream(getters)
                        .map(this::method2attr).toArray(String[]::new);
                dbRef.child(getDBRef(clazz)).runTransaction(new Transaction.Handler() {
                    @NonNull @Override
                    public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                        try {
                            for (int i, j = 0; j < lst.size(); j++) {
                                for (i = 0; i < getters.length; i++) {
                                    mutableData.child(String.valueOf(size + j))
                                        .child(attrs[i])
                                        .setValue(getters[i].invoke(lst.get(j)));
                                }
                            }
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            execute(onFailureCallback, e.getMessage());
                            return Transaction.abort();
                        }
                        return Transaction.success(mutableData);
                    }
                    @Override
                    public void onComplete(
                        @Nullable DatabaseError databaseError,
                        boolean b,
                        @Nullable DataSnapshot dataSnapshot
                    ) {
                        if (databaseError != null)
                            execute(onFailureCallback, databaseError.getMessage());
                        else
                            execute(onSuccessCallback, size + lst.size());
                    }
                });
            },
            onFailureCallback
        );
    }

    /**
     * Adds the given object to the database.
     *
     * @param clazz the class of the object.
     * @param data the object to add.
     * @param onSuccessCallback the callback to execute on success.
     * @param onFailureCallback the callback to execute on failure.
     * @param <T> The model class.
     */
    protected <T> void append(
        Class<T> clazz,
        T data,
        OnSuccessValueCallback<Integer> onSuccessCallback,
        OnFailureCallback<String> onFailureCallback
    ) {
        ArrayList<T> lst = new ArrayList<>();
        lst.add(data);
        append(
            clazz,
            lst,
            onSuccessCallback,
            onFailureCallback
        );
    }

    /**
     * Obtains all the getter methods of the given class.
     * @param clazz the class to search.
     * @return an array of methods.
     */
    protected Method[] getGetters(Class<?> clazz) {
        return Arrays.stream(clazz.getMethods())
            .filter(method ->
                method.getName().startsWith("get") &&
                !method.getName().equals("getClass")
            )
            .toArray(Method[]::new);
    }

    /**
     * Obtains the attribute name from the given getter method.
     * @param method method to use.
     * @return the attribute name.
     */
    protected String method2attr(Method method) {
        return method.getName().substring(3).toLowerCase();
    }

    /**
     * From the given class, obtains the name of the database reference.
     * @param clazz the class to obtain the reference from.
     * @return the name of the database reference.
     */
    protected String getDBRef(Class<?> clazz) {
        String[] arr = clazz.getName().split("\\.");
        return arr[arr.length - 1]
            .replaceAll("([a-z])([A-Z])", "$1_$2")
            .toLowerCase();
    }

    /**
     * Executes the given callback.
     * @param callback the callback to execute.
     */
    protected void execute(OnSuccessCallback callback) {
        if (callback != null)
            callback.onSuccess();
    }

    /**
     * Executes the given callback.
     * @param callback the callback to execute.
     * @param data the data to pass to the callback.
     * @param <T> the type of the data.
     */
    protected <T> void execute(OnSuccessValueCallback<T> callback, T data) {
        if (callback != null)
            callback.onSuccess(data);
    }

    /**
     * Executes the given callback.
     *
     * @param callback the callback to execute.
     * @param error the error to pass to the callback.
     * @param <T> the type of the error.
     */
    protected <T> void execute(OnFailureCallback<T> callback, T error) {
        if (callback != null)
            callback.onFailure(error);
    }
}
