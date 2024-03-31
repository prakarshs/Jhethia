package org.prakarshs.Syntax.Expressions.Operators;

import org.prakarshs.Syntax.Expressions.Expression;
import org.prakarshs.Syntax.Literals.Literal;
import org.prakarshs.Syntax.Literals.LogicalLiteral;

import java.util.Objects;

public class LessThanOperatorExpression extends BinaryOperatorExpression {
    public LessThanOperatorExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Literal<?> calc(Literal<?> left, Literal<?> right) {
        boolean result;
        if (Objects.equals(left.getClass(), right.getClass())) {
            result = ((Comparable) left.getLiteral()).compareTo(right.getLiteral()) < 0;
        } else {
            result = left.toString().compareTo(right.toString()) < 0;
        }
        return new LogicalLiteral(result);

    }
}
