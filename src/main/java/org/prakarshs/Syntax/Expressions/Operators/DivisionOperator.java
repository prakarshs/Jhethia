package org.prakarshs.Syntax.Expressions.Operators;

import org.prakarshs.Constants.ErrorConstants;
import org.prakarshs.Exceptions.OperationException;
import org.prakarshs.Syntax.Expressions.Expression;
import org.prakarshs.Syntax.Literals.Literal;
import org.prakarshs.Syntax.Literals.NumericalLiteral;

public class DivisionOperator extends BinaryOperator{
    public DivisionOperator(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Literal<?> calc(Literal<?> left, Literal<?> right) {
        if (left instanceof NumericalLiteral && right instanceof NumericalLiteral) {
            Integer rightValue = ((NumericalLiteral) right).getLiteral();
            if (rightValue == 0) {
                System.out.println("Poblem : "+ErrorConstants.DIVISION_BY_ZERO);
                System.out.println("Solution : "+"Try With A different Denominator");
                throw new OperationException(ErrorConstants.DIVISION_BY_ZERO,"Try With A different Denominator" );
            }
            return new NumericalLiteral(((NumericalLiteral) left).getLiteral() / rightValue);
        } else {
            String problem = ErrorConstants.OPERATION_IMPOSSIBLE;
            String solution = String.format("Unable to divide non-numeric values `%s` by `%s`", left, right);
            System.out.println("Poblem : "+problem);
            System.out.println("Solution : "+solution);
            throw new OperationException(problem, solution);
        }
    }
}
