package org.prakarshs.Syntax.Expressions.Operators;

import org.prakarshs.Syntax.Expressions.Expression;
import org.prakarshs.Syntax.Values.LogicalValue;
import org.prakarshs.Syntax.Values.Value;
import org.prakarshs.Syntax.Values.NullValue;

import java.util.Objects;

public class NotEqualsOperator extends BinaryOperatorExpression {
    public NotEqualsOperator(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Value<?> evaluate() {
        Value<?> left = getLeft().evaluate();
        if (left == null) return null;
        Value<?> right = getRight().evaluate();
        if (right == null) return null;
        boolean result;
        if (left == NullValue.NULL_INSTANCE || right == NullValue.NULL_INSTANCE) {
            result = left != right;
        } else if (Objects.equals(left.getClass(), right.getClass())) {
            result = !left.getValue().equals(right.getValue());
        } else {
            result = !left.toString().equals(right.toString());
        }
        return new LogicalValue(result);
    }
}
