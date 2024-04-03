package org.prakarshs.Syntax.Expressions.Operators;

import lombok.Data;
import org.prakarshs.Syntax.Expressions.Expression;
import org.prakarshs.Syntax.Literals.Literal;

@Data
public abstract class BinaryOperator implements OperatorExpression {
    private final Expression left;
    private final Expression right;
    public abstract Literal<?> calc(Literal<?> left, Literal<?> right);

    @Override
    public Literal<?> evaluate() {
        return calc(getLeft().evaluate(), getRight().evaluate());
    }
}
