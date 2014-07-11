package nl.eernie.jmoribus;

import java.util.Arrays;
import java.util.List;


public class ConfigurationImpl implements Configuration {
    @Override
    public List<Object> getSteps(Context context) {
        return Arrays.asList((Object) new Steps());
    }
}
