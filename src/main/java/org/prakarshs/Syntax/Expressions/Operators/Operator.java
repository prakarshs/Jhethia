package org.prakarshs.Syntax.Expressions.Operators;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum Operator {
    Not("!", NotOperator.class, 7),
    ClassInstance("naya", ClassInstanceOperator.class, 7),
    NestedClassInstance("ka\\s+naya", NestedClassInstanceOperator.class, 7),
    ClassProperty("ka", ClassPropertyOperator.class, 7),
    ClassCast("as", ClassCastOperator.class, 7),
    ClassInstanceOf("is", ClassInstanceOfOperator.class, 7),

    ExponentiationOperator("\\*{2}", ExponentiationOperator.class, 6),
    Multiplication("\\*", MultiplicationOperator.class, 6),
    Division("/", DivisionOperator.class, 6),
    FloorDivision("//", FloorDivisionOperator.class, 6),
    Modulo("%", ModuloOperator.class, 6),

    Addition("\\+", AdditionOperator.class, 5),
    Subtraction("-", SubtractionOperator.class, 5),

    Equals("==", EqualsOperator.class, 4),
    NotEquals("!=", NotEqualsOperator.class, 4),
    LessThan("<", LessThanOperator.class, 4),
    LessThanOrEqualTo("<=", LessThanOrEqualToOperator.class, 4),
    GreaterThan(">", GreaterThanOperator.class, 4),
    GreaterThanOrEqualTo(">=", GreaterThanOrEqualToOperator.class, 4),

    LeftParen("\\(", 3),
    RightParen("\\)", 3),

    LogicalAnd("aur", LogicalAndOperator.class, 2),
    LogicalOr("ya", LogicalOrOperator.class, 1),

    ArrayAppend("<<", ArrayAppendOperator.class, 0),
    Assignment("=", AssignmentOperator.class, 0);

    private final String character;
    private final Class<? extends OperatorExpression> type;
    private final Integer precedence;

    Operator(String character, Integer precedence) {
        this(character, null, precedence);
    }

    public static Operator getType(String character) {
        return Arrays.stream(values())
                .filter(t -> character.matches(t.getCharacter()))
                .findAny().orElse(null);
    }

    public boolean greaterThan(Operator o) {
        return getPrecedence().compareTo(o.getPrecedence()) >= 0;
    }
}
