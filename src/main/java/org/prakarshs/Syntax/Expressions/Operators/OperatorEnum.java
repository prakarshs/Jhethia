package org.prakarshs.Syntax.Expressions.Operators;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Objects;

@RequiredArgsConstructor
@Getter
public enum OperatorEnum {
    Not("!", NotOperator.class,7),
    Addition("+", AdditionOperator.class,5),
    Subtraction("-", SubstractionOperator.class,5),
    Equality("==", EqualsOperator.class,4),
    GreaterThan(">", GreaterThanOperator.class,4),
    LessThan("<", LessThanOperator.class,4);

    private final String character;
    private final Class<? extends OperatorExpression> type;
    private final Integer precedence;

    OperatorEnum(String character, Integer precedence) {
        this(character, null, precedence);
    }

    public static OperatorEnum getType(String character) {
        return Arrays.stream(values())
                .filter(t -> character.matches(t.getCharacter()))
                .findAny().orElse(null);
    }

    public boolean greaterThan(OperatorEnum o) {
        return getPrecedence().compareTo(o.getPrecedence()) >= 0;
    }
}
