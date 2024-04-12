package org.prakarshs.Syntax.Expressions.Operators;

import org.prakarshs.context.ExceptionContext;
import org.prakarshs.Syntax.Expressions.Expression;
import org.prakarshs.Syntax.Values.ComparableValue;
import org.prakarshs.Syntax.Values.LogicalValue;
import org.prakarshs.Syntax.Values.Value;
import org.prakarshs.Syntax.Values.NullValue;

import java.util.Objects;

public class GreaterThanOperator extends BinaryOperatorExpression {
    public GreaterThanOperator(Expression left, Expression right) {
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
            return ExceptionContext.raiseException(String.format("Unable to perform greater than for NULL values `%s`, '%s'", left, right));
        } else if (Objects.equals(left.getClass(), right.getClass()) && left instanceof ComparableValue) {
            //noinspection unchecked,rawtypes
            result = ((Comparable) left.getValue()).compareTo(right.getValue()) > 0;
        } else {
            result = left.toString().compareTo(right.toString()) > 0;
        }
        return new LogicalValue(result);
    }
}

