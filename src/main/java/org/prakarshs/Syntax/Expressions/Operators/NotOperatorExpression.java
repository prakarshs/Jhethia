package org.prakarshs.Syntax.Expressions.Operators;

import org.prakarshs.Constants.ErrorConstants;
import org.prakarshs.Exceptions.OperationException;
import org.prakarshs.Syntax.Expressions.Expression;
import org.prakarshs.Syntax.Literals.Literal;
import org.prakarshs.Syntax.Literals.LogicalLiteral;


public class NotOperatorExpression extends UnaryOperatorExpression {
    public NotOperatorExpression(Expression value) {
        super(value);
    }

    @Override
    public Literal<?> calc(Literal<?> literal) {
        if (literal instanceof LogicalLiteral) {
            return new LogicalLiteral(!((LogicalLiteral) literal.getLiteral()).getLiteral());
        } else {
            String problem = ErrorConstants.OPERATION_IMPOSSIBLE;
            String solution = String.format("Unable to perform NOT operator for non logical value `%s`", literal);
            throw new OperationException(problem,solution);
        }
    }
}

