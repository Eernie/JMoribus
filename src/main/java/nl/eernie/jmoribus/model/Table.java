package nl.eernie.jmoribus.model;

import java.util.ArrayList;
import java.util.List;

public class Table implements StepLine {

    private List<String> header;
    private List<List<String>> rows ;

    public Table() {
        rows = new ArrayList<>();
    }

    public Table(List<String> header, List<List<String>> rows)
    {
        this.header = header;
        this.rows = rows;
    }

    public List<String> getHeader() {
        return header;
    }

    public void setHeader(List<String> header) {
        this.header = header;
    }

    public List<List<String>> getRows() {
        return rows;
    }

    @Override
    public String getText() {
        return null;
    }
}
