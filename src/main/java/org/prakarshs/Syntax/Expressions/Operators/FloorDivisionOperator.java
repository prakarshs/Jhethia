package org.prakarshs.Syntax.Expressions.Operators;

import org.prakarshs.Context.ExceptionContext;
import org.prakarshs.Syntax.Expressions.Expression;
import org.prakarshs.Syntax.Values.NumericValue;
import org.prakarshs.Syntax.Values.Value;
import org.prakarshs.Syntax.Values.NullValue;

public class FloorDivisionOperator extends BinaryOperatorExpression {
    public FloorDivisionOperator(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Value<?> evaluate() {
        Value<?> left = getLeft().evaluate();
        if (left == null) return null;
        Value<?> right = getRight().evaluate();
        if (right == null) return null;
        if (left == NullValue.NULL_INSTANCE || right == NullValue.NULL_INSTANCE) {
            return ExceptionContext.raiseException(String.format("Unable to perform floor division for NULL values `%s`, '%s'", left, right));
        } else if (left instanceof NumericValue && right instanceof NumericValue) {
            return new NumericValue(Math.floor(((NumericValue) left).getValue() / ((NumericValue) right).getValue()));
        } else {
            return ExceptionContext.raiseException(String.format("Unable to divide non numeric values `%s` and `%s`", left, right));
        }
    }
}
