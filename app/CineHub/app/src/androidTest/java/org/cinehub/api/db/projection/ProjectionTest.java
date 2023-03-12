package org.cinehub.api.db.projection;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.cinehub.api.APITest;
import org.cinehub.api.model.SpecialSeat;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests for the Projection API.
 *
 * @author Jkutkut
 */
@RunWith(AndroidJUnit4.class)
public class ProjectionTest extends APITest {

    @Test
    public void getProjections() {
        db.getProjections(
            getAllSuccessCallback(),
            getFailureCallback()
        );
    }

    @Test
    public void getProject() {
        int id = 0;
        db.getProjection(
            id,
            getOneSuccessCallback(),
            getFailureCallback()
        );
    }

    @Test
    public void getProjectionConfiguration() { // TODO fix
        int id = 0;
        db.getProjectionConfiguration(
            id,
            projectionConfig -> {
                if (projectionConfig == null) {
                    print("projectionConfig == null");
                    running.set(INVALID);
                }
                else {
                    print("projectionConfig = \n");
                    StringBuilder sb = new StringBuilder();
                    for (char[] chars : projectionConfig) {
                        sb.append(chars[0]);
                        for (int j = 1; j < chars.length; j++) {
                            sb.append(", ").append(chars[j]);
                        }
                        sb.append("\n");
                    }
                    print(sb.toString());
                    boolean[] allStates = new boolean[SpecialSeat.STATES.length];
                    for (char[] chars : projectionConfig) {
                        for (char aChar : chars) {
                            for (int j = 0; j < SpecialSeat.STATES.length; j++) {
                                if (aChar == SpecialSeat.STATES[j]) {
                                    allStates[j] = true;
                                }
                            }
                        }
                    }
                    for (int i = 0; i < allStates.length; i++) {
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