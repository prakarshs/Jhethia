package org.prakarshs.Syntax.Expressions.Operators;

import org.prakarshs.Constants.ErrorConstants;
import org.prakarshs.Exceptions.OperationException;
import org.prakarshs.Syntax.Expressions.Expression;
import org.prakarshs.Syntax.Literals.Literal;
import org.prakarshs.Syntax.Literals.NumericalLiteral;

public class MultiplicationOperatorExpression extends BinaryOperatorExpression {
    public MultiplicationOperatorExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Literal<?> calc(Literal<?> left, Literal<?> right) {
        if (left instanceof NumericalLiteral && right instanceof NumericalLiteral) {
            return new NumericalLiteral(((NumericalLiteral) left).getLiteral() * ((NumericalLiteral) right).getLiteral());
        } else {
            String problem = ErrorConstants.OPERATION_IMPOSSIBLE;
            String solution = String.format("Unable to multiply non numeric values `%s` and `%s`", left, right);
            throw new OperationException(problem, solution);
        }
    }

}
