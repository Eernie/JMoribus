package nl.eernie.jmoribus.model;

public class Feature {
    private String title;
    private String inOrder;
    private String asA;
    private String iWant;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInOrder() {
        return inOrder;
    }

    public void setInOrder(String inOrder) {
        this.inOrder = inOrder;
    }

    public String getAsA() {
        return asA;
    }

    public void setAsA(String asA) {
        this.asA = asA;
    }

    public String getiWant() {
        return iWant;
    }

    public void setiWant(String iWant) {
        this.iWant = iWant;
    }

    @Override
    public String toString() {
        return "Feature{" +
                "title='" + title + '\'' +
                ", inOrder='" + inOrder + '\'' +
                ", asA='" + asA + '\'' +
                ", iWant='" + iWant + '\'' +
                '}';
    }
}
