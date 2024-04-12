package org.prakarshs.Syntax.Expressions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.prakarshs.Syntax.Values.ArrayValue;
import org.prakarshs.Syntax.Values.Value;

import java.util.List;

@RequiredArgsConstructor
@Getter
@ToString
public class ArrayExpression implements Expression {
    private final List<Expression> values;

    @Override
    public Value<?> evaluate() {
        return new ArrayValue(this);
    }
}
