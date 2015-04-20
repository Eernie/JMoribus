package nl.eernie.jmoribus.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A Step consists of multiple steplines. This class represents a table.
 */
public class Table implements StepLine
{
    private List<String> header;
    private List<List<String>> rows;

    public Table()
    {
        rows = new ArrayList<>();
    }

    public List<String> getHeader()
    {
        return header;
    }

    public void setHeader(List<String> header)
    {
        this.header = header;
    }

    public List<List<String>> getRows()
    {
        return rows;
    }

    @Override
    public String getText()
    {
        return null;
    }
}
