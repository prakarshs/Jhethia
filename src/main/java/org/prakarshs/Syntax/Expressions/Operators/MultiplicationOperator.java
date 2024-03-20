package org.prakarshs.Syntax.Expressions.Operators;

import org.prakarshs.Constants.ErrorConstants;
import org.prakarshs.Exceptions.OperationException;
import org.prakarshs.Syntax.Expression;
import org.prakarshs.Syntax.Literals.Literal;
import org.prakarshs.Syntax.Literals.NumercialLiteral;
import org.prakarshs.Syntax.Literals.TextLiteral;

public class MultiplicationOperator extends BinaryOperator{
    public MultiplicationOperator(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Literal<?> calc(Literal<?> left, Literal<?> right) {
        if (left instanceof NumercialLiteral && right instanceof NumercialLiteral) {
            return new NumercialLiteral(((NumercialLiteral) left).getLiteral() * ((NumercialLiteral) right).getLiteral());
        } else {
            String problem = ErrorConstants.OPERATION_IMPOSSIBLE;
            String solution = String.format("Unable to multiply non numeric values `%s` and `%s`", left, right);
            throw new OperationException(problem, solution);
        }
    }

}
