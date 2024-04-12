package org.prakarshs.Syntax.Expressions.Operators;

import org.prakarshs.Syntax.Expressions.Expression;
import org.prakarshs.Syntax.Values.ArrayValue;
import org.prakarshs.Syntax.Values.Value;

public class ArrayAppendOperator extends BinaryOperatorExpression {
    public ArrayAppendOperator(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Value<?> evaluate() {
        Value<?> left = getLeft().evaluate();
        if (left == null) return null;
        Value<?> right = getRight().evaluate();
        if (right == null) return null;

        if (left instanceof ArrayValue) {
            ((ArrayValue) left).appendValue(right);
        }
        return left;
    }
}
