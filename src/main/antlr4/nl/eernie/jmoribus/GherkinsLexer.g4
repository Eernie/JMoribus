lexer grammar GherkinsLexer;

FEATURE     :	'Feature:';

PROLOGUE    :   'Prologue:';
 
SCENARIO    : 	'Scenario:';

GIVEN       :   'Given';

WHEN        :   'When';

AND         :   'And';

THEN        :   'Then';

REVERING    :   'Revering';

STEPS
	: 	GIVEN | WHEN | THEN | REVERING | AND
	;

SPACE	: ' ' | '\t';


TEXT
    : (UPPERCASE_LETTER | LOWERCASE_LETTER | DIGIT| SYMBOL )
    ;

KEYWORD
    : STEPS
    | SCENARIO
    | PROLOGUE
    | FEATURE
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

LINE_COMMENT
    : '#--' ~[\r\n]* -> channel(HIDDEN)
    ;

COMMENT
    : '/*' .*? '*/' -> channel(HIDDEN)
    ;

NEWLINE : ('\r'? '\n' | '\r');


WS : [ \t\n\r]+ -> skip ;

