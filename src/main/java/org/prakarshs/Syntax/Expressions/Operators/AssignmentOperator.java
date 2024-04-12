package org.prakarshs.Syntax.Expressions.Operators;

import org.prakarshs.Syntax.Expressions.Expression;
import org.prakarshs.Syntax.Expressions.Variable;
import org.prakarshs.Syntax.Literals.Literal;

public class AssignmentOperator extends BinaryOperator{
    public AssignmentOperator(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Literal<?> calc(Literal<?> left, Literal<?> right) {
        if(getLeft() instanceof Variable) {
            ((Variable) getLeft()).setValue(right);
        }
        return left;
    }
}
