package nl.eernie.jmoribus.configuration;

import nl.eernie.jmoribus.reporter.ConcurrentReporter;

import nl.eernie.jmoribus.reporter.Reporter;

import java.util.ArrayList;

import java.util.List;


public class DefaultConfiguration implements Configuration {

    private ConcurrentReporter concurrentReporter = new ConcurrentReporter();

    private List<Object> steps = new ArrayList<Object>();

    public ConcurrentReporter getConcurrentReporter() {
        return concurrentReporter;
    }

    public void addReporter(Reporter reporter){
        concurrentReporter.addReporter(reporter);
    }

    @Override
    public List<Object> getSteps(Context context) {
        return steps;
    }

    @Override
    public void addSteps(List<Object> steps) {
        steps.addAll(steps);
    }


}
