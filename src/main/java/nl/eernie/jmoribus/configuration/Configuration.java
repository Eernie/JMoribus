package nl.eernie.jmoribus.configuration;


import nl.eernie.jmoribus.reporter.ConcurrentReporter;

import java.util.List;

public interface Configuration {

    ConcurrentReporter getConcurrentReporter();

    List<Object> getSteps(Context context);

    void addSteps(List<Object> steps);
}
