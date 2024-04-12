package org.prakarshs.Syntax.Expressions.Operators;

import org.prakarshs.Syntax.Expressions.Expression;
import org.prakarshs.Syntax.Literals.Literal;

public class StructureInstanceOperator extends UnaryOperator{
    public StructureInstanceOperator(Expression literal) {
        super(literal);
    }

    @Override
    public Literal<?> calc(Literal<?> literal) {
        return literal;
    }
}
