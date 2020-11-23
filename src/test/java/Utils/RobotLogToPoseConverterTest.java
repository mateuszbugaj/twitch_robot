package Utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
public class RobotLogToPoseConverterTest {
    private RobotLogToPoseConverter converter;

    @Before
    public void setup(){
        converter = new RobotLogToPoseConverter();
    }

    @Test
    public void convertBasicLogIntoPosition() {
        // Given
        String log = "x5 y-7 z10";
        Float[] expectedPose = new Float[]{5f, -7f, 10f};

        // When
        Float[] pose = converter.apply(log);

        // Then
        Assert.assertArrayEquals(expectedPose, pose);
    }

    @Test
    public void convertLogWithMessageIntoPosition() {
        // Given
        String log = "x0 y999 z-999 Log message";
        Float[] expectedPose = new Float[]{0f, 999f, -999f};

        // When
        Float[] pose = converter.apply(log);

        // Then
        Assert.assertArrayEquals(expectedPose, pose);
    }
}