package nl.eernie.jmoribus.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A Step consists of multiple steplines. This class represents a table.
 */
public class Table implements StepLine
{
    private final String uniqueID = UUID.randomUUID().toString();
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

    public String getUniqueID()
    {
        return uniqueID;
    }
}
