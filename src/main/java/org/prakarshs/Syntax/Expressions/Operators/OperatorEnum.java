package org.prakarshs.Syntax.Expressions.Operators;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Objects;

@RequiredArgsConstructor
public enum OperatorEnum {
    Not("!", NotOperatorExpression.class),
    Addition("+", AdditionOperatorExpression.class),
    Subtraction("-", SubstractionOperatorExpression.class),
    Equality("==", EqualsOperatorExpression.class),
    GreaterThan(">", GreaterThanOperatorExpression.class),
    LessThan("<", LessThanOperatorExpression.class),
    private final String character;
    private final Class<? extends OperatorExpression> operatorType;

    public static Class<? extends OperatorExpression> getType(String character) {
        return Arrays.stream(values())
                .filter(t -> Objects.equals(t.getCharacter(), character))
                .map(OperatorExpression::getOperatorType)
                .findAny().orElse(null);
    }
}
