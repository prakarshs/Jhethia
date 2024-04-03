package org.prakarshs.Syntax.Expressions.Operators;

import org.prakarshs.Syntax.Expressions.Expression;
import org.prakarshs.Syntax.Literals.Literal;
import org.prakarshs.Syntax.Literals.StructLiteral;

public class StructOperator extends BinaryOperator{
    public StructOperator(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Literal<?> calc(Literal<?> left, Literal<?> right) {
        if (left instanceof StructLiteral)
            return ((StructLiteral) left).getLiteral().getArgumentValue(right.toString());
        return left;
    }
}
