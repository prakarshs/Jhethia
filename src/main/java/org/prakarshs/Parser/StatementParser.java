package org.prakarshs.Parser;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.prakarshs.Constants.ErrorConstants;
import org.prakarshs.Exceptions.SyntaxException;
import org.prakarshs.Syntax.Expressions.Expression;
import org.prakarshs.Syntax.Statements.Statement;
import org.prakarshs.Tokens.Token;
import org.prakarshs.Tokens.TokenType;

import java.util.*;
import java.util.stream.Stream;

@Data
@AllArgsConstructor
public class StatementParser {
    private final List<Token> tokens;
    private int position;

    public StatementParser(List<Token> tokens){
        this.tokens = tokens;
    }

    public Statement parseExpression() {
        Token token = next(TokenType.Keyword, TokenType.Variable);

        switch (token.getType()) {
            case Variable:
                next(TokenType.Operator, "="); //skip equals

                Expression value;
                if (peek(TokenType.Keyword, "naya")) {
                    value = readInstance();
                } else {
                    value = readExpression();
                }

                return new AssignStatement(token.getValue(), value, variables::put);
        }
    }

    private Token next(TokenType type, TokenType... types) {
        TokenType[] tokenTypes = org.apache.commons.lang3.ArrayUtils.add(types, type);
        if (position < tokens.size()) {
            Token token = tokens.get(position);
            if (Stream.of(tokenTypes).anyMatch(t -> t == token.getType())) {
                position++;
                return token;
            }
        }
        Token previousToken = tokens.get(position - 1);

        String problem = ErrorConstants.SYNTAX_GALAT_HAI;
        String solution = String.format("After `%s` declaration expected any of the following lexemes `%s`", previousToken, Arrays.toString(tokenTypes));

        throw new SyntaxException(problem,solution);
    }

}
