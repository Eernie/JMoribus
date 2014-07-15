package nl.eernie.jmoribus.parser;

import nl.eernie.jmoribus.model.Table;

import java.util.List;

public interface TableParser {

    Table parse(List<String> storyLines);
}
