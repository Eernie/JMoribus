package nl.eernie.jmoribus.model;

import java.util.ArrayList;
import java.util.List;

public class Scenario extends Step implements StepContainer {

    private String title;
    private List<Step> steps = new ArrayList<>();

    private Table examplesTable;

    public Scenario(){
        super(StepType.REFERRING);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setExamplesTable(Table examplesTable)
    {
        this.examplesTable = examplesTable;
    }

    public Table getExamplesTable()
    {
        return examplesTable;
    }

}
