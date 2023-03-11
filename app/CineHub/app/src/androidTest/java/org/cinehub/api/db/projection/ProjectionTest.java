package org.cinehub.api.db.projection;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.cinehub.api.APITest;
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
                    running.set(VALID);
                }
            },
            getFailureCallback()
        );
    }
}