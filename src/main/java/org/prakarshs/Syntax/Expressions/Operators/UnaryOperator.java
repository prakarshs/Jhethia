package org.prakarshs.Syntax.Expressions.Operators;

import lombok.Data;
import org.prakarshs.Syntax.Expressions.Expression;
import org.prakarshs.Syntax.Literals.Literal;

@Data
public abstract class UnaryOperator implements Operator{
    private final Expression literal;

    public abstract Literal<?> calc(Literal<?> literal);
    @Override
    public Literal<?> evaluate() {
        return calc(getLiteral().evaluate());
    }
}
