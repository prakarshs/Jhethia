package org.prakarshs.Syntax.Expressions.Operators;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Objects;

@RequiredArgsConstructor
@Getter
public enum OperatorEnum {
    Not("!", NotOperator.class),
    Addition("+", AdditionOperator.class),
    Subtraction("-", SubstractionOperator.class),
    Equality("==", EqualsOperator.class),
    GreaterThan(">", GreaterThanOperator.class),
    LessThan("<", LessThanOperator.class),
    StructureValue("ka", StructOperator.class);

    private final String character;
    private final Class<? extends OperatorExpression> type;


    public static Class<? extends OperatorExpression> getType(String character) {
        return Arrays.stream(values())
                .filter(t -> Objects.equals(t.getCharacter(),character))
                .map(OperatorEnum::getType)
                .findAny().orElse(null);
    }

}
