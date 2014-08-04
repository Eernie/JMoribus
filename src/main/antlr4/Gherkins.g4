grammar Gherkins;

@header {
    package nl.eernie.jmoribus;
}

feature	:	NEWLINE* comment? NEWLINE* SPACE* tags? NEWLINE* SPACE* feature_keyword SPACE* line_to_eol NEWLINE+ (feature_elements .)* feature_elements ;

feature_elements 
	:	(scenario_outline | scenario | background)* ;
 
background
	:	comment? tags? background_keyword SPACE* line_to_eol NEWLINE+ steps ;

scenario
	:	comment? tags? scenario_keyword SPACE* line_to_eol NEWLINE+ steps ;
	
scenario_outline 
	:	comment? tags? scenario_outline_keyword SPACE* line_to_eol NEWLINE+ steps examples_sections ;
 
steps 	:	step* ;
 
step	:	step_keyword SPACE* line_to_eol NEWLINE+ multiline_arg? ;
 
examples_sections 
	:	examples+ ;
	
examples 
	:	examples_keyword SPACE* line_to_eol NEWLINE+ table ;
 
multiline_arg  
	:	table ;
 
table 	:	table_row+ ;
 
table_row 
	: 	SPACE* '|' (cell '|')+ SPACE* (NEWLINE+) ;
 
cell	: 	(~('|' | NEWLINE) .)* ;
 
tags	: 	tag (SPACE tag)* NEWLINE+ ;
 
tag 	:  	'@' tag_name=ID ;
 
comment	
	: 	(comment_line NEWLINE)* ;
 
comment_line 
	: 	'#' line_to_eol;
 
line_to_eol 
	: 	(~NEWLINE)* ;
 
feature_keyword 
	:	'Feature' ':'? ;

background_keyword
    :   'Background' ':'?;
 
scenario_keyword
	: 	'Scenario' ':'? ;
 
scenario_outline_keyword 
	: 	'Scenario Outline' ':'? ;
 
step_keyword 
	: 	'Given' | 'When' | 'Then' | 'And' | 'Prologue';
 
examples_keyword 
	: 	'Examples' ':'? ;
 
 
ID 	: 	('a'..'z'|'A'..'Z'|'_')+ ;
 
NEWLINE :  	(('\r')? '\n' )+ ;
 
SPACE	: 	(' '|'\t')+ {skip();};