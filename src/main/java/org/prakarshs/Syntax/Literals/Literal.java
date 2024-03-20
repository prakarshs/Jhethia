package org.prakarshs.Syntax.Literals;

import lombok.Data;
import org.prakarshs.Syntax.Expressions.Expression;

@Data
public class Literal<T extends Comparable<T>> implements Expression {
    private final T literal;
    @Override
    public String toString() {
        return literal.toString();
    }

    @Override
    public Literal<?> evaluate() {
        return this;
    }
}
