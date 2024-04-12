package org.prakarshs.Syntax.Expressions.Operators;

import org.prakarshs.Context.ExceptionContext;
import org.prakarshs.Syntax.Expressions.Expression;
import org.prakarshs.Syntax.Expressions.VariableExpression;
import org.prakarshs.Syntax.Values.ClassValue;
import org.prakarshs.Syntax.Values.LogicalValue;
import org.prakarshs.Syntax.Values.Value;

public class ClassInstanceOfOperator extends BinaryOperatorExpression {
    public ClassInstanceOfOperator(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Value<?> evaluate() {
        Value<?> left = getLeft().evaluate();
        if (left == null) return null;
        if (left instanceof ClassValue && getRight() instanceof VariableExpression) {
            String classType = ((VariableExpression) getRight()).getName();
            return new LogicalValue(((ClassValue) left).containsRelation(classType));
        } else {
            return ExceptionContext.raiseException(String.format("Unable to perform `is` operator for the following operands `%s` and `%s`", left, getRight()));
        }
    }
}
