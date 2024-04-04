package org.prakarshs.Parser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;
import org.apache.commons.lang3.ArrayUtils;
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
import org.prakarshs.Syntax.Statements.*;
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
    private final Scanner scanner;

    public StatementParser(List<Token> tokens) {
        this.tokens = tokens;
        this.variables = new LinkedHashMap<>();
        this.structures = new LinkedHashMap<>();
        this.scanner = new Scanner(System.in);
    }

    public Statement parseExpression() {
        Token token = next(TokenType.Keyword, TokenType.Variable);
        switch (token.getType()) {
            case Variable: {
                next(TokenType.Operator, "="); //skip equals
                Expression value;
                if (peek(TokenType.Keyword, "naya")) {
                    value = readInstance();
                } else {
                    value = readExpression();
                }

                return new AssignStatement(token.getValue(), value, variables::put);
            }
            case Keyword: {
                switch (token.getValue()) {
                    case "dekhiye_baapuji": {
                        Expression expression = readExpression();
                        return new PrintStatement(expression);
                    }
                    case "lijiye_baapuji": {
                        Token variable = next(TokenType.Variable);
                        return new InputStatement(variable.getValue(), scanner::nextLine, variables::put);
                    }
                    case "agar": {
                        Expression condition = readExpression();
                        next(TokenType.Keyword, "toh"); //skip toh

                        ConditionStatement conditionStatement = new ConditionStatement(condition);
                        while (!peek(TokenType.Keyword, "khatam")) {
                            Statement statement = parseExpression();
                            conditionStatement.addStatement(statement);
                        }
                        next(TokenType.Keyword, "khatam"); //skip khatam

                        return conditionStatement;
                    }
                    case "dhancha": {
                        Token type = next(TokenType.Variable);

                        Set<String> args = new LinkedHashSet<>();
                        while (!peek(TokenType.Keyword, "khatam")) {

                            next(TokenType.Keyword, "yeh_lo");

                            Token arg = next(TokenType.Variable);
                            args.add(arg.getValue());

                        }
                        next(TokenType.Keyword, "khatam"); //skip end

                        structures.put(type.getValue(), new StructDefinition(type.getValue(), new ArrayList<>(args)));
                        return null;
                    }

                }
            }
            default:
            {
                String problem = ErrorConstants.SYNTAX_GALAT_HAI;
                String solution = String.format("Statement can't start with the following lexeme `%s`", token);
                System.out.println("Poblem : "+problem);
                System.out.println("Solution : "+solution);
                throw new SyntaxException(problem,solution);
            }
        }
    }

    private Token next(TokenType type, TokenType... types) {
        TokenType[] tokenTypes = ArrayUtils.add(types, type);
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
        System.out.println("Poblem : "+problem);
        System.out.println("Solution : "+solution);
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

        next(TokenType.Keyword, "naya"); //skip new
        Token type = next(TokenType.Variable);
        List<Expression> arguments = new ArrayList<>();

        if (peek(TokenType.GroupDivider, "[")) {

            next(TokenType.GroupDivider, "["); //skip open square bracket

            while (!peek(TokenType.GroupDivider, "]")) {
                Expression value = readExpression();
                arguments.add(value);
            }

            next(TokenType.GroupDivider, "]"); //skip close square bracket
        }

        StructDefinition definition = structures.get(type.getValue());
        if (definition == null) {
            String problem = ErrorConstants.SYNTAX_GALAT_HAI;
            String solution = String.format("Structure is not defined: %s", type.getValue());
            System.out.println("Poblem : "+problem);
            System.out.println("Solution : "+solution);
            throw new SyntaxException(problem, solution);
        }
        return new StructExpression(definition,variables::get,arguments);

    }

    public Statement parse() {
        CompoundStatement root = new CompoundStatement();
        while (position < tokens.size()) {
            Statement statement = parseExpression();
            root.addStatement(statement);
        }
        return root;
    }

    public Token next(TokenType type, String value, String... values) {
        if (position < tokens.size()) {
            String[] allValues = ArrayUtils.add(values, value);
            Token token = tokens.get(position);
            if (token.getType() == type && Arrays.stream(allValues).anyMatch(t -> Objects.equals(t, token.getValue()))) {
                position++;
                return token;
            }
        }
        Token previousToken = tokens.get(position - 1);

        String problem = ErrorConstants.SYNTAX_GALAT_HAI;
        String solution = String.format("After `%s` declaration expected `%s, %s` lexeme", previousToken,type, value);
        System.out.println("Poblem : "+problem);
        System.out.println("Solution : "+solution);
        throw new SyntaxException(problem, solution);
    }

}

