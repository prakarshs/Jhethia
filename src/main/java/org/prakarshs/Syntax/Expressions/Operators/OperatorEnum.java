package org.prakarshs.Syntax.Expressions.Operators;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public enum OperatorEnum {
    Not("!", NotOperator.class, 7),

    Multiplication("\\*", MultiplicationOperator.class, 6),
    Division("/", DivisionOperator.class, 6),
    Modulo("%", ModuloOperator.class, 6),

    Addition("\\+", AdditionOperator.class, 5),
    Subtraction("-", SubstractionOperator.class, 5),

    Equals("==", EqualsOperator.class, 4),
    NotEquals("!=", NotEqualsOperator.class, 4),
    LessThan("<", LessThanOperator.class, 4),
    LessThanOrEqualTo("<=", LessThanOrEqualToOperator.class, 4),
    GreaterThan(">", GreaterThanOperator.class, 4),
    GreaterThanOrEqualTo(">=", GreaterThanOrEqualToOperator.class, 4),

    LeftParen("\\(", 3),
    RightParen("\\)", 3),

    LogicalAnd("aur", LogicalAndOperator.class, 2),
    LogicalOr("yatoh", LogicalOrOperator.class, 1),

    Assignment("=", AssignmentOperator.class, 0);

    private final String character;
    private final Class<? extends Operator> type;
    private final Integer precedence;

    OperatorEnum(String character, Integer precedence) {
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
