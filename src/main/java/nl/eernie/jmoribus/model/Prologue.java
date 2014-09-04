package nl.eernie.jmoribus.model;

import java.util.ArrayList;
import java.util.List;

public class Prologue implements StepContainer {

    private List<Step> steps = new ArrayList<>();

    public List<Step> getSteps() {
        return steps;
    }

}
