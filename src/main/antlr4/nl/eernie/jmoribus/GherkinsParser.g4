parser grammar GherkinsParser;

options { tokenVocab=GherkinsLexer;}

story
    : prologue? scenario+;

prologue
    : PROLOGUE SPACE? stepLine+;

scenario
    : SCENARIO stepLine+;

title
    : (TEXT|SPACE)*;

stepLine
    : NEWLINE STEPS;

step
    : STEPS SPACE ;

until_keyword
    : ~KEYWORD+
    ;
