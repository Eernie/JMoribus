package nl.eernie.jmoribus.model;

public class Line implements StepLine {

    private String text;

    public Line(String text)
    {
        this.text = text;
    }

    public String getText()
    {
        return text;
    }

}
