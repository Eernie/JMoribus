grammar Gherkins;

story
    : prologue? scenario*;

prologue
    : NEWLINE? prologue_keyword (NEWLINE step)+;

scenario
    : NEWLINE? scenario_keyword title (NEWLINE step)+;

title
    : line;

step
    : step_keyword line;

line
    : (SPACE|TEXT)*;

feature_keyword
	:	'Feature:';

prologue_keyword
    :   'Prologue:';
 
scenario_keyword
	: 	'Scenario:';
 
step_keyword
	: 	'Given' | 'When' | 'Then' | 'And' | 'Revering:';

SPACE	: ' ' | '\t';

NEWLINE : '\r'? '\n';

TEXT
    : (UPPERCASE_LETTER | LOWERCASE_LETTER | DIGIT| SYMBOL )
    ;

fragment UPPERCASE_LETTER
    : 'A'..'Z'
    ;

fragment LOWERCASE_LETTER
    : 'a'..'z'
    ;

fragment DIGIT
    : '0'..'9'
    ;

fragment SYMBOL
    : '\u0021'..'\u0027'
    | '\u002a'..'\u002f'
    | '\u003a'..'\u0040'
    | '\u005e'..'\u0060'
    | '\u00a1'..'\u00FF'
    | '\u0152'..'\u0192'
    | '\u2013'..'\u2122'
    | '\u2190'..'\u21FF'
    | '\u2200'..'\u22FF'
    | '('..')'
    ;


WS : [ \t\n\r]+ -> skip ;