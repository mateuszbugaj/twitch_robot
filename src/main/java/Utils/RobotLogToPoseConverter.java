package Utils;

import java.util.function.Function;

public class RobotLogToPoseConverter implements Function<String, Float[]> {

    /**
     * Converts log of shape "x-?[0-9]{1,3}\sy-?[0-9]{1,3}\sz-?[0-9]{1,3}"
     * e.g. "x5 y-7 z10 Robot in range"
     * into Float array denoting position x, y and z
     * @param log robot log
     * @return Float array with position
     */
    @Override
    public Float[] apply(String log) {

        Float[] pose = new Float[3];
        String[] partsOfLog = log.split(" ");
        for (int i = 0; i < 3; i++) {
            pose[i] =  Float.parseFloat(partsOfLog[i].replaceAll("[xyz]", ""));
        }

        return pose;
    }
}
