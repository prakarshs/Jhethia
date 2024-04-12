package org.prakarshs.Syntax.Values;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.prakarshs.Syntax.Expressions.Expression;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Value<T> implements Expression {
    @EqualsAndHashCode.Include
    private T value;

    public Value(T v) {
        setValue(v);
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public void setValue(T v) {
        this.value = v;
    }

    @Override
    public Value<?> evaluate() {
        return this;
    }
}
