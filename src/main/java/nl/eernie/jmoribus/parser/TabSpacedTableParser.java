package nl.eernie.jmoribus.parser;

import nl.eernie.jmoribus.model.Table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TabSpacedTableParser implements TableParser{

    public Table parse(List<String> storyLines) {

        if(storyLines == null || storyLines.isEmpty())
        {
            return null;
        }

        boolean headerParsed = false;

        List<String> header = null;
        List<List<String>> rows = new ArrayList<List<String>>();

        for (String storyLine : storyLines) {

            List splitList = Arrays.asList(storyLine.split("\t"));

            if(headerParsed)
            {
                rows.add(splitList);
            }
            else
            {
                headerParsed = true;
                header = splitList;
            }
        }

        return new Table(header, rows);
    }
}