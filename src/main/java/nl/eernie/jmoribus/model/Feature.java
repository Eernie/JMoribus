package nl.eernie.jmoribus.model;

public class Feature {
    private String title;
    private String inOrder;
    private String asA;
    private String iWant;

    /**
     * Created by erwin on 09/07/14.
     */
    public static enum StepType {
        WHEN,
        THEN,
        GIVEN
    }
}
