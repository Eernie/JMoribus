package nl.eernie.jmoribus.model;

import java.util.ArrayList;
import java.util.List;

public class Scenario implements StepContainer {

    private String title;
    private List<Step> steps = new ArrayList<Step>();

    private Table examplesTable;

    public Scenario(){
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
