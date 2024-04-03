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
import org.prakarshs.Syntax.Statements.*;
import org.prakarshs.Tokens.Token;
import org.prakarshs.Tokens.TokenStack;
import org.prakarshs.Tokens.TokenType;

import java.util.*;
import java.util.stream.Stream;

@Data
@AllArgsConstructor
public class StatementParser {
    private final TokenStack tokens;
    private final Scanner scanner;
    private final CompoundStatement compositeStatement;

    public static void parse(StatementParser parent, CompoundStatement compositeStatement) {
            StatementParser parser = new StatementParser(parent.getTokens(), parent.getScanner(), compositeStatement);
            while (parser.hasNextStatement()) {
                parser.parseExpression();
            }

    }

    public static void parse(List<Token> tokens, CompoundStatement compositeStatement) {
        StatementParser parser = new StatementParser(new TokenStack(tokens), new Scanner(System.in), compositeStatement);
        while (parser.hasNextStatement()) {
            parser.parseExpression();
        }
    }

    private boolean hasNextStatement() {
        if (!tokens.hasNext())
            return false;
        if (tokens.peek(TokenType.Operator, TokenType.Variable, TokenType.This))
            return true;
        if (tokens.peek(TokenType.Keyword)) {
            return !tokens.peek(TokenType.Keyword, "yafir", "ya", "rescue", "ensure", "khatam");
        }
        return false;
    }

    private void parseExpression() {
        Token token = tokens.next(TokenType.Keyword, TokenType.Variable, TokenType.This, TokenType.Operator);
        switch (token.getType()) {
            case Variable:
            case Operator:
            case This:
                parseExpressionStatement(token);
                break;
            case Keyword:
                parseKeywordStatement(token);
                break;
            default:{
                String problem = ErrorConstants.SYNTAX_GALAT_HAI;
                String solution = String.format("Statement can't start with the following lexeme `%s`", token);

                throw new SyntaxException(problem,solution);
            }

        }
    }

    private void parseExpressionStatement(Token rowToken) {
        tokens.back(); // go back to read an expression from the beginning
        Expression value = ExpressionReader.readExpression(tokens);
        ExpressionStatement statement = new ExpressionStatement(rowToken.getRowNumber(), compositeStatement.getBlockName(), value);
        compositeStatement.addStatement(statement);
    }

    private void parseKeywordStatement(Token token) {
        switch (token.getValue()) {
            case "dekhiye_baapuji":
                parsePrintStatement(token);
                break;
            case "lijiye_baapuji":
                parseInputStatement(token);
                break;
            case "if":
                parseConditionStatement(token);
                break;
            case "class":
                parseClassDefinition(token);
                break;
            case "fun":
                parseFunctionDefinition(token);
                break;
            case "return":
                parseReturnStatement(token);
                break;
            case "loop":
                parseLoopStatement(token);
                break;
            case "break":
                parseBreakStatement(token);
                break;
            case "next":
                parseNextStatement(token);
                break;
            case "assert":
                parseAssertStatement(token);
                break;
            case "raise":
                parseRaiseExceptionStatement(token);
                break;
            case "begin":
                parseHandleExceptionStatement(token);
                break;
            default:
                throw new SyntaxException(String.format("Failed to parse a keyword: %s", token.getValue()));
        }
    }

    private void parsePrintStatement(Token rowToken) {
        Expression expression = ExpressionReader.readExpression(tokens);
        PrintStatement statement = new PrintStatement(rowToken.getRowNumber(), compositeStatement.getBlockName(), expression);
        compositeStatement.addStatement(statement);
    }

    private void parseInputStatement(Token rowToken) {
        Token variable = tokens.next(TokenType.Variable);
        InputStatement statement = new InputStatement(rowToken.getRowNumber(), compositeStatement.getBlockName(), variable.getValue(), scanner::nextLine);
        compositeStatement.addStatement(statement);
    }

    private void parseConditionStatement(Token rowToken) {
        tokens.back();
        ConditionStatement conditionStatement = new ConditionStatement(rowToken.getRowNumber(), compositeStatement.getBlockName());

        while (!tokens.peek(TokenType.Keyword, "end")) {
            //read condition case
            Token type = tokens.next(TokenType.Keyword, "if", "elif", "else");
            Expression caseCondition;
            if (type.getValue().equals("else")) {
                caseCondition = new LogicalValue(true); //else case does not have the condition
            } else {
                caseCondition = ExpressionReader.readExpression(tokens);
            }

            //read case statements
            CompositeStatement caseStatement = new CompositeStatement(rowToken.getRowNumber(), compositeStatement.getBlockName());
            DefinitionScope caseScope = DefinitionContext.newScope();
            StatementParser.parse(this, caseStatement, caseScope);

            //add case
            conditionStatement.addCase(caseCondition, caseStatement);
        }
        tokens.next(TokenType.Keyword, "end");

        compositeStatement.addStatement(conditionStatement);
    }

    private void parseClassDefinition(Token rowToken) {
        // read class details
        ClassDetails classDetails = readClassDetails();

        // read base types
        Set<ClassDetails> baseTypes = new LinkedHashSet<>();
        if (tokens.peek(TokenType.GroupDivider, ":")) {
            while (tokens.peek(TokenType.GroupDivider, ":", ",")) {
                tokens.next();
                ClassDetails baseClassDetails = readClassDetails();
                baseTypes.add(baseClassDetails);
            }
        }

        // add class definition
        DefinitionScope classScope = DefinitionContext.newScope();
        ClassStatement classStatement = new ClassStatement(rowToken.getRowNumber(), classDetails.getName());
        ClassDefinition classDefinition = new ClassDefinition(classDetails, baseTypes, classStatement, classScope);
        DefinitionContext.getScope().addClass(classDefinition);

        //parse class's statements
        StatementParser.parse(this, classStatement, classScope);
        tokens.next(TokenType.Keyword, "end");
    }

    private ClassDetails readClassDetails() {
        Token className = tokens.next(TokenType.Variable);
        List<String> classArguments = new ArrayList<>();
        if (tokens.peek(TokenType.GroupDivider, "[")) {
            tokens.next(); //skip open square bracket

            while (!tokens.peek(TokenType.GroupDivider, "]")) {
                Token argumentToken = tokens.next(TokenType.Variable);
                classArguments.add(argumentToken.getValue());

                if (tokens.peek(TokenType.GroupDivider, ","))
                    tokens.next();
            }

            tokens.next(TokenType.GroupDivider, "]"); //skip close square bracket
        }
        return new ClassDetails(className.getValue(), classArguments);
    }

    private void parseFunctionDefinition(Token rowToken) {
        Token name = tokens.next(TokenType.Variable);

        List<String> arguments = new ArrayList<>();

        if (tokens.peek(TokenType.GroupDivider, "[")) {

            tokens.next(TokenType.GroupDivider, "["); //skip open square bracket

            while (!tokens.peek(TokenType.GroupDivider, "]")) {
                Token argumentToken = tokens.next(TokenType.Variable);
                arguments.add(argumentToken.getValue());

                if (tokens.peek(TokenType.GroupDivider, ","))
                    tokens.next();
            }

            tokens.next(TokenType.GroupDivider, "]"); //skip close square bracket
        }

        //add function definition
        String blockName = name.getValue();
        if (compositeStatement instanceof ClassStatement) {
            blockName = compositeStatement.getBlockName() + "#" + blockName;
        }
        FunctionStatement functionStatement = new FunctionStatement(rowToken.getRowNumber(), blockName);
        DefinitionScope functionScope = DefinitionContext.newScope();
        FunctionDetails functionDetails = new FunctionDetails(name.getValue(), arguments);
        FunctionDefinition functionDefinition = new FunctionDefinition(functionDetails, functionStatement, functionScope);
        DefinitionContext.getScope().addFunction(functionDefinition);

        //parse function statements
        StatementParser.parse(this, functionStatement, functionScope);
        tokens.next(TokenType.Keyword, "end");
    }

    private void parseReturnStatement(Token rowToken) {
        Expression expression = ExpressionReader.readExpression(tokens);
        ReturnStatement statement = new ReturnStatement(rowToken.getRowNumber(), compositeStatement.getBlockName(), expression);
        compositeStatement.addStatement(statement);
    }

    private void parseLoopStatement(Token rowToken) {
        Expression loopExpression = ExpressionReader.readExpression(tokens);
        if (loopExpression instanceof OperatorExpression || loopExpression instanceof VariableExpression) {
            AbstractLoopStatement loopStatement;

            if (loopExpression instanceof VariableExpression && tokens.peek(TokenType.Keyword, "in")) {
                // loop <variable> in <bounds>
                VariableExpression variable = (VariableExpression) loopExpression;
                tokens.next(TokenType.Keyword, "in");
                Expression bounds = ExpressionReader.readExpression(tokens);

                if (tokens.peek(TokenType.GroupDivider, "..")) {
                    // loop <variable> in <lower_bound>..<upper_bound>
                    tokens.next(TokenType.GroupDivider, "..");
                    Expression upperBound = ExpressionReader.readExpression(tokens);

                    if (tokens.peek(TokenType.Keyword, "by")) {
                        // loop <variable> in <lower_bound>..<upper_bound> by <step>
                        tokens.next(TokenType.Keyword, "by");
                        Expression step = ExpressionReader.readExpression(tokens);
                        loopStatement = new ForLoopStatement(rowToken.getRowNumber(), compositeStatement.getBlockName(), variable, bounds, upperBound, step);
                    } else {
                        // use default step
                        // loop <variable> in <lower_bound>..<upper_bound>
                        loopStatement = new ForLoopStatement(rowToken.getRowNumber(), compositeStatement.getBlockName(), variable, bounds, upperBound);
                    }

                } else {
                    // loop <variable> in <iterable>
                    loopStatement = new IterableLoopStatement(rowToken.getRowNumber(), compositeStatement.getBlockName(), variable, bounds);
                }

            } else {
                // loop <condition>
                loopStatement = new WhileLoopStatement(rowToken.getRowNumber(), compositeStatement.getBlockName(), loopExpression);
            }

            DefinitionScope loopScope = DefinitionContext.newScope();
            StatementParser.parse(this, loopStatement, loopScope);
            tokens.next(TokenType.Keyword, "end");

            compositeStatement.addStatement(loopStatement);
        }

    }

    private void parseBreakStatement(Token rowToken) {
        BreakStatement statement = new BreakStatement(rowToken.getRowNumber(), compositeStatement.getBlockName());
        compositeStatement.addStatement(statement);
    }

    private void parseNextStatement(Token rowToken) {
        NextStatement statement = new NextStatement(rowToken.getRowNumber(), compositeStatement.getBlockName());
        compositeStatement.addStatement(statement);
    }

    private void parseAssertStatement(Token rowToken) {
        Expression expression = ExpressionReader.readExpression(tokens);
        AssertStatement statement = new AssertStatement(rowToken.getRowNumber(), compositeStatement.getBlockName(), expression);
        compositeStatement.addStatement(statement);
    }

    private void parseRaiseExceptionStatement(Token rowToken) {
        Expression expression = ExpressionReader.readExpression(tokens);
        RaiseExceptionStatement statement = new RaiseExceptionStatement(rowToken.getRowNumber(), compositeStatement.getBlockName(), expression);
        compositeStatement.addStatement(statement);
    }

    private void parseHandleExceptionStatement(Token rowToken) {
        // read begin block
        CompositeStatement beginStatement = new CompositeStatement(rowToken.getRowNumber(), compositeStatement.getBlockName());
        DefinitionScope beginScope = DefinitionContext.newScope();
        StatementParser.parse(this, beginStatement, beginScope);

        // read rescue block
        CompositeStatement rescueStatement = null;
        String errorVariable = null;
        if (tokens.peek(TokenType.Keyword, "rescue")) {
            tokens.next();

            if (tokens.peekSameLine(TokenType.Variable)) {
                errorVariable = tokens.next().getValue();
            }

            rescueStatement = new CompositeStatement(rowToken.getRowNumber(), compositeStatement.getBlockName());
            DefinitionScope rescueScope = DefinitionContext.newScope();
            StatementParser.parse(this, rescueStatement, rescueScope);
        }

        // read ensure block
        CompositeStatement ensureStatement = null;
        if (tokens.peek(TokenType.Keyword, "ensure")) {
            tokens.next();

            ensureStatement = new CompositeStatement(rowToken.getRowNumber(), compositeStatement.getBlockName());
            DefinitionScope ensureScope = DefinitionContext.newScope();
            StatementParser.parse(this, ensureStatement, ensureScope);
        }

        // skip end keyword
        tokens.next(TokenType.Keyword, "end");

        // construct a statement
        HandleExceptionStatement statement = new HandleExceptionStatement(rowToken.getRowNumber(), compositeStatement.getBlockName(),
                beginStatement, rescueStatement, ensureStatement, errorVariable);
        compositeStatement.addStatement(statement);
    }
}

