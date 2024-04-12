package org.prakarshs.Syntax.Expressions.Operators;

import org.prakarshs.context.ExceptionContext;
import org.prakarshs.Syntax.Expressions.Expression;
import org.prakarshs.Syntax.Values.LogicalValue;
import org.prakarshs.Syntax.Values.Value;

public class NotOperator extends UnaryOperatorExpression {
    public NotOperator(Expression value) {
        super(value);
    }

    @Override
    public Value<?> evaluate() {
        Value<?> value = getValue().evaluate();
        if (value == null) return null;
        if (value instanceof LogicalValue) {
            return new LogicalValue(!(((LogicalValue) value).getValue()));
        } else {
            return ExceptionContext.raiseException(String.format("Unable to perform NOT operator for non logical value `%s`", value));
        }
    }
}

