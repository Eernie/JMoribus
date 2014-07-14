package nl.eernie.jmoribus;

import java.util.Arrays;
import java.util.List;


public class DefaultConfiguration implements Configuration {

    private ConcurrentReporter concurrentReporter = new ConcurrentReporter();

    public ConcurrentReporter getConcurrentReporter() {
        return concurrentReporter;
    }

    public void addReporter(Reporter reporter){
        concurrentReporter.addReporter(reporter);
    }

    @Override
    public List<Object> getSteps(Context context) {
        return Arrays.asList((Object) new Steps());
    }
}
