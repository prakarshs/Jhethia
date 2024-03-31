package org.prakarshs.Syntax.Expressions.Operators;

import org.prakarshs.Syntax.Expressions.Expression;
import org.prakarshs.Syntax.Literals.Literal;
import org.prakarshs.Syntax.Literals.NumericalLiteral;
import org.prakarshs.Syntax.Literals.TextLiteral;

public class AdditionOperatorExpression extends BinaryOperatorExpression {
    public AdditionOperatorExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Literal<?> calc(Literal<?> left, Literal<?> right) {
        if (left instanceof NumericalLiteral && right instanceof NumericalLiteral) {
            return new NumericalLiteral(((NumericalLiteral) left).getLiteral() + ((NumericalLiteral) right).getLiteral());
        } else {
            return new TextLiteral(left.toString() + right.toString());
        }
    }

}
