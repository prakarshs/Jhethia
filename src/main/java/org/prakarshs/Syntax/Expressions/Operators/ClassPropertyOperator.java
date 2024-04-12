package org.prakarshs.Syntax.Expressions.Operators;

import org.prakarshs.Context.ExceptionContext;
import org.prakarshs.Syntax.Expressions.AssignExpression;
import org.prakarshs.Syntax.Expressions.Expression;
import org.prakarshs.Syntax.Expressions.FunctionExpression;
import org.prakarshs.Syntax.Expressions.VariableExpression;
import org.prakarshs.Syntax.Values.ClassValue;
import org.prakarshs.Syntax.Values.ThisValue;
import org.prakarshs.Syntax.Values.Value;

public class ClassPropertyOperator extends BinaryOperatorExpression implements AssignExpression {
    public ClassPropertyOperator(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Value<?> evaluate() {
        Value<?> left = getLeft().evaluate();
        if (left == null) return null;

        // access class's property via this instance
        // this :: class_argument
        if (left instanceof ThisValue) {
            left = ((ThisValue) left).getValue();
        }

        if (left instanceof ClassValue) {
            if (getRight() instanceof VariableExpression) {
                // access class's property
                // new Class [] :: class_property
                return ((ClassValue) left).getValue(((VariableExpression) getRight()).getName());
            } else if (getRight() instanceof FunctionExpression) {
                // execute class's function
                // new Class [] :: class_function []
                return ((FunctionExpression) getRight()).evaluate((ClassValue) left);
            }
        }

        return ExceptionContext.raiseException(String.format("Class Ki Property Access Nahi Kar Pa Raha Mehta Saab `%s``", getRight()));
    }

    @Override
    public Value<?> assign(Value<?> value) {
        Value<?> left = getLeft().evaluate();
        if (left == null) return null;

        // access class's property via this instance
        // this :: class_argument
        if (left instanceof ThisValue) {
            left = ((ThisValue) left).getValue();
        }

        if (left instanceof ClassValue && getRight() instanceof VariableExpression) {
            String propertyName = ((VariableExpression) getRight()).getName();
            ((ClassValue) left).setValue(propertyName, value);
        }
        return left;
    }
}
