package org.cinehub.api.projection;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.cinehub.api.APITest;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ProjectionTest extends APITest {

    @Test
    public void getProjections() {
        db.getProjections(
            projections -> {
                if (projections == null) {
                    print("projections == null");
                    running.set(INVALID);
                }
                else if (projections.isEmpty()) {
                    print("projections.isEmpty()");
                    running.set(INVALID);
                }
                else {
                    print("projections.size() = " + projections.size());
                    running.set(VALID);
                }
            },
            e -> {
                print("Error: " + e);
                running.set(INVALID);
            }
        );
    }

    @Test
    public void getProjectionConfiguration() {
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
                    for (int i = 0; i < projectionConfig.length; i++) {
                        sb.append(projectionConfig[i][0]);
                        for (int j = 1; j < projectionConfig[i].length; j++) {
                            sb.append(", ").append(projectionConfig[i][j]);
                        }
                        sb.append("\n");
                    }
                    print(sb.toString());
                    running.set(VALID);
                }
            },
            e -> {
                print("Error: " + e);
                running.set(INVALID);
            }
        );
    }
}