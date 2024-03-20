package org.prakarshs.Syntax.Expressions.Operators;

import org.prakarshs.Constants.ErrorConstants;
import org.prakarshs.Exceptions.OperationException;
import org.prakarshs.Syntax.Expression;
import org.prakarshs.Syntax.Literals.Literal;
import org.prakarshs.Syntax.Literals.LogicalLiteral;

public class NotOperator extends UnaryOperator {
    public NotOperator(Expression value) {
        super(value);
    }

    @Override
    public Literal<?> evaluate() {
        Literal<?> literal = getLiteral().evaluate();
        if (literal == null) return null;
        if (literal instanceof LogicalLiteral) {
            return new LogicalLiteral(!(((LogicalLiteral) literal).getLiteral()));
        } else {
            String problem = ErrorConstants.OPERATION_IMPOSSIBLE;
            String solution = String.format("Unable to perform NOT operator for non logical value `%s`", literal);
            throw new OperationException(problem,solution);
        }
    }
}

