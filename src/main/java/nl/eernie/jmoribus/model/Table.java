package nl.eernie.jmoribus.model;

import java.util.List;

public class Table {

    private List<String> header;
    private List<List<String>> rows;

    public Table(List<String> header, List<List<String>> rows)
    {
        this.header = header;
        this.rows = rows;
    }

    public List<String> getHeader() {
        return header;
    }

    public List<List<String>> getRows() {
        return rows;
    }
}