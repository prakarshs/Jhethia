package org.prakarshs.Syntax.Expressions.Operators;

import org.prakarshs.context.ExceptionContext;
import org.prakarshs.Syntax.Expressions.Expression;
import org.prakarshs.Syntax.Values.NumericValue;
import org.prakarshs.Syntax.Values.TextValue;
import org.prakarshs.Syntax.Values.Value;
import org.prakarshs.Syntax.Values.NullValue;

public class MultiplicationOperator extends BinaryOperatorExpression {
    public MultiplicationOperator(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Value<?> evaluate() {
        Value<?> left = getLeft().evaluate();
        if (left == null) return null;
        Value<?> right = getRight().evaluate();
        if (right == null) return null;
        if (left == NullValue.NULL_INSTANCE || right == NullValue.NULL_INSTANCE) {
            return ExceptionContext.raiseException(String.format("Unable to perform multiplication for NULL values `%s`, '%s'", left, right));
        } else if (left instanceof NumericValue && right instanceof NumericValue) {
            return new NumericValue(((NumericValue) left).getValue() * ((NumericValue) right).getValue());
        } else if (left instanceof NumericValue) {
            return new TextValue(right.toString().repeat(((NumericValue) left).getValue().intValue()));
        } else if (right instanceof NumericValue) {
            return new TextValue(left.toString().repeat(((NumericValue) right).getValue().intValue()));
        } else {
            return ExceptionContext.raiseException(String.format("Unable to multiply non numeric values `%s` and `%s`", left, right));
        }
    }
}
