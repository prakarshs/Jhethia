package org.prakarshs.Syntax.Expressions.Operators;

import org.prakarshs.Syntax.Expression;
import org.prakarshs.Syntax.Literals.Literal;
import org.prakarshs.Syntax.Literals.NumercialLiteral;
import org.prakarshs.Syntax.Literals.TextLiteral;

public class SubstractionOperator extends BinaryOperator{
    public SubstractionOperator(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Literal<?> calc(Literal<?> left, Literal<?> right) {
        if (left instanceof NumercialLiteral && right instanceof NumercialLiteral) {
            return new NumercialLiteral(((NumercialLiteral) left).getLiteral() - ((NumercialLiteral) right).getLiteral());
        } else {
            return new TextLiteral(left.toString().replaceAll(right.toString(), ""));
        }
    }

}
