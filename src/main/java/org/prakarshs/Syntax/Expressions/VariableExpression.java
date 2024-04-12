package org.prakarshs.Syntax.Expressions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.prakarshs.context.MemoryContext;
import org.prakarshs.Syntax.Values.Value;

@RequiredArgsConstructor
@Getter
@ToString
public class VariableExpression implements Expression, AssignExpression {
    private final String name;

    @Override
    public Value<?> evaluate() {
        return MemoryContext.getScope().get(name);
    }

    @Override
    public Value<?> assign(Value<?> value) {
        if (value == null) return null;
        MemoryContext.getScope().set(name, value);
        return value;
    }
}
