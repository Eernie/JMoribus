package nl.eernie.jmoribus;


import java.util.List;

public interface Configuration {

    ConcurrentReporter getConcurrentReporter();

    List<Object> getSteps(Context context);


}
