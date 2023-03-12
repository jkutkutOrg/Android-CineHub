package org.cinehub.api.db.room;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.cinehub.api.APITest;
import org.cinehub.api.model.SpecialSeat;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests for the Room API.
 *
 * @author Jkutkut
 */
@RunWith(AndroidJUnit4.class)
public class RoomTest extends APITest {
    @Test
    public void getAll() {
        db.getRooms(
            getAllSuccessCallback(),
            getFailureCallback()
        );
    }

    @Test
    public void get() {
        int id = 0;
        db.getRoom(
            id,
            getOneSuccessCallback(),
            getFailureCallback()
        );
    }

    @Test
    public void getRoomConfiguration() {
        int id = 0;
        db.getRoomConfiguration(
                id,
                roomConfig -> {
                    if (roomConfig == null) {
                        print("roomConfig == null");
                        running.set(INVALID);
                    }
                    else {
                        print("roomConfig = \n");
                        StringBuilder sb = new StringBuilder();
                        for (char[] chars : roomConfig) {
                            sb.append(chars[0]);
                            for (int j = 1; j < chars.length; j++) {
                                sb.append(", ").append(chars[j]);
                            }
                            sb.append("\n");
                        }
                        print(sb.toString());
                        boolean[] allStates = new boolean[SpecialSeat.STATES.length - 1];
                        for (char[] chars : roomConfig) {
                            for (char aChar : chars) {
                                for (int j = 0; j < SpecialSeat.STATES.length - 1; j++) {
                                    if (aChar == SpecialSeat.STATES[j]) {
                                        allStates[j] = true;
                                    }
                                }
                            }
                        }
                        for (int i = 0; i < allStates.length - 1; i++) {
                            if (!allStates[i]) {
                                print("Missing state: " + SpecialSeat.STATES[i]);
                                running.set(INVALID);
                                return;
                            }
                        }
                        running.set(VALID);
                    }
                },
                getFailureCallback()
        );
    }
}