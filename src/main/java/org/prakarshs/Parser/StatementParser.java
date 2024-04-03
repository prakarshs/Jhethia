package org.prakarshs.Parser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;
import org.prakarshs.Constants.ErrorConstants;
import org.prakarshs.Exceptions.SyntaxException;
import org.prakarshs.Syntax.Expressions.Expression;
import org.prakarshs.Syntax.Expressions.Operators.BinaryOperator;
import org.prakarshs.Syntax.Expressions.Operators.OperatorEnum;
import org.prakarshs.Syntax.Expressions.Operators.OperatorExpression;
import org.prakarshs.Syntax.Expressions.Operators.UnaryOperator;
import org.prakarshs.Syntax.Expressions.StructDefinition;
import org.prakarshs.Syntax.Expressions.StructExpression;
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
    private final Map<String, Literal<?>> variables;
    private int position;
    private final Map<String, StructDefinition> structures;

    public StatementParser(List<Token> tokens) {
        this.tokens = tokens;
        this.variables = new HashMap<>();
        this.structures = new HashMap<>();
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

        throw new SyntaxException(problem, solution);
    }

    private boolean peek(TokenType type, String value) {
        if (position < tokens.size()) {
            Token token = tokens.get(position);
            return type == token.getType() && token.getValue().equals(value);
        }
        return false;
    }

    @SneakyThrows
    private Expression readExpression() {
        Expression left = nextExpression();

        while (peek(TokenType.Operator)) {
            Token operation = next(TokenType.Operator);
            Class<? extends OperatorExpression> operatorType = OperatorEnum.getType(operation.getValue());

            if (BinaryOperator.class.isAssignableFrom(operatorType)) {
                Expression right = nextExpression();
                left = operatorType
                        .getConstructor(Expression.class, Expression.class)
                        .newInstance(left, right);
            } else if (UnaryOperator.class.isAssignableFrom(operatorType)) {
                left = operatorType
                        .getConstructor(Expression.class)
                        .newInstance(left);
            }
        }

        return left;
    }

    private Expression nextExpression() {
        Token token = next(TokenType.Variable, TokenType.Numeric, TokenType.Logical, TokenType.Text);
        String value = token.getValue();
        switch (token.getType()) {
            case Numeric:
                return new NumericalLiteral(Integer.parseInt(value));
            case Logical:
                return new LogicalLiteral(Boolean.valueOf(value));
            case Text:
                return new TextLiteral(value);
            case Variable:
            default:
                return new Variable(value, variables::get);
        }
    }
    private boolean peek(TokenType type) {
        if (position < tokens.size()) {
            Token token = tokens.get(position);
            return token.getType() == type;
        }
        return false;
    }

    private Expression readInstance() {
        next(TokenType.Keyword, TokenType.valueOf("naya")); //skip new
        Token type = next(TokenType.Variable);
        List<Expression> arguments = new ArrayList<>();

        if (peek(TokenType.GroupDivider, "[")) {

            next(TokenType.GroupDivider, TokenType.valueOf("[")); //skip open square bracket

            while (!peek(TokenType.GroupDivider, "]")) {
                Expression value = readExpression();
                arguments.add(value);
            }

            next(TokenType.GroupDivider, TokenType.valueOf("]")); //skip close square bracket
        }

        StructDefinition definition = structures.get(type.getValue());
        if (definition == null) {
            String problem = ErrorConstants.SYNTAX_GALAT_HAI;
            String solution = String.format("Structure is not defined: %s", type.getValue());
            throw new SyntaxException(problem, solution);
        }
        return new StructExpression(definition,variables::get,arguments);

    }


}

