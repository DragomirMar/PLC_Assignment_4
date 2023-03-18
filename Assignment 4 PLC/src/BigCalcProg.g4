grammar BigCalcProg;

root
        : (expressionStatement) + EOF
        ;

expressionStatement
        : expression ';'                        #expState
        | assign ';'                            #assState
        ;

assign
        : Identifier '=' expression
        ;


expression
        : '('expression')'                      # para
        | expression op=('*' | '/') expression  # mulDiv
        | expression op=('+' | '-') expression  # addSub
        | Number                                # num
        | Identifier                            # var
        | equals '?' expression ':' expression  # example1
        ;

equals
        : expression '=' expression             #eqstate
        ;

Number
        : Digit+ '.' Digit+ ('E'|'e') ('+'|'-')? Digit
        | Digit* '.' Digit+
        | Digit+

        ;

Digit
        : [0-9]
        ;

Identifier
        :[a-zA-Z][a-zA-Z0-9_]*
        ;

WS      : [ \t\r\n\u000C]+ -> skip
        ;

COMMENT
        :   '/*' .*? '*/' -> skip
        ;

LINE_COMMENT
        : '//' ~[\r\n]* -> skip
        ;