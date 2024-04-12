package org.prakarshs.Syntax.Expressions.Operators;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Objects;

@RequiredArgsConstructor
@Getter
public enum OperatorEnum {
    Not("!", NotOperator.class,5),
    Addition("+", AdditionOperator.class,3),
    Subtraction("-", SubstractionOperator.class,3),
    Multiplication("*", MultiplicationOperator.class,4),
    Division("/", DivisionOperator.class,4),
    Equality("==", EqualsOperator.class,2),
    GreaterThan(">", GreaterThanOperator.class,2),
    LessThan("<", LessThanOperator.class,2),
    StructureValue("ka", StructOperator.class,5),
    StructureInstance("naya", StructureInstanceOperator.class,5),
    LeftParen("(",1),
    RightParen(")",1);

    private final String character;
    private final Class<? extends OperatorExpression> type;
    private final Integer precedence;

    OperatorEnum(String character, Integer precedence) {
        this(character, null, precedence);
    }
    public static OperatorEnum getType(String character) {
        return Arrays.stream(values())
                .filter(t -> Objects.equals(t.getCharacter(),character))
                .findAny().orElse(null);
    }

    public boolean greaterThan(OperatorEnum operator) {
        return getPrecedence().compareTo(operator.getPrecedence()) > 0;
    }
}
