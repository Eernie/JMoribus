package nl.eernie.jmoribus.parser;

public class TableParserFactory {

    public static TableParser getParser(String row)
    {
        if(row.contains("\t"))
        {
            return new TabSpacedTableParser();
        }
        else
        {
            throw new IllegalArgumentException("Cannot determine correct table parser for input [" + row + "]");
        }
    }
}
