package org.prakarshs.Parser;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.prakarshs.Constants.ErrorConstants;
import org.prakarshs.Exceptions.SyntaxException;
import org.prakarshs.Syntax.Expressions.Expression;
import org.prakarshs.Syntax.Expressions.Operators.OperatorEnum;
import org.prakarshs.Syntax.Expressions.Operators.OperatorExpression;
import org.prakarshs.Syntax.Expressions.Variable;
import org.prakarshs.Syntax.Literals.Literal;
import org.prakarshs.Syntax.Literals.LogicalLiteral;
import org.prakarshs.Syntax.Literals.NumericalLiteral;
import org.prakarshs.Syntax.Literals.TextLiteral;
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
    private final Map<String, Literal<?>> variables;

    public StatementParser(List<Token> tokens){
        this.tokens = tokens;
        this.variables = new HashMap<>();
    }

    public Statement parseExpression() {
        Token token = next(TokenType.Keyword, TokenType.Variable);

        switch (token.getType()) {
            case Variable:
                next(TokenType.Operator, TokenType.valueOf("=")); //skip equals

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

    private boolean peek(TokenType type, String value) {
        if (position < tokens.size()) {
            Token token = tokens.get(position);
            return type == token.getType() && token.getValue().equals(value);
        }
        return false;
    }

    private Expression readExpression() {
        Expression left;
        Token token = next(TokenType.Variable, TokenType.Numeric, TokenType.Logical, TokenType.Text);
        String value = token.getValue();
        switch (token.getType()) {
            case Numeric:
                left = new NumericalLiteral(Integer.parseInt(value));
            case Logical:
                left = new LogicalLiteral(Boolean.valueOf(value));
            case Text:
                left = new TextLiteral(value);
            case Variable:
            default:
                left = new Variable(value, variables::get);
        }

        Token operation = next(TokenType.Operator);
        Class<? extends OperatorExpression> operatorType = OperatorEnum.getType(operation.getValue());

    }
}
