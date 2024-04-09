package org.prakarshs.Syntax.Expressions.Operators;

import org.prakarshs.Constants.ErrorConstants;
import org.prakarshs.Exceptions.OperationException;
import org.prakarshs.Syntax.Expressions.Expression;
import org.prakarshs.Syntax.Literals.Literal;
import org.prakarshs.Syntax.Literals.LogicalLiteral;


public class NotOperator extends UnaryOperator {
    public NotOperator(Expression value) {
        super(value);
    }

    @Override
    public Literal<?> calc(Literal<?> literal) {
        if (literal instanceof LogicalLiteral) {
//            System.out.println("in not operator class success logical");
            return new LogicalLiteral(!((LogicalLiteral) literal.getLiteral()).getLiteral());
        } else {
//            System.out.println("in not operator class fail logical");
            String problem = ErrorConstants.OPERATION_IMPOSSIBLE;
            String solution = String.format("Unable to perform NOT operator for non logical value `%s`", literal);
            System.out.println("Poblem : "+problem);
            System.out.println("Solution : "+solution);
            throw new OperationException(problem,solution);
        }
    }
}

