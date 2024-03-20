package org.prakarshs.Syntax.Expressions.Operators;

import lombok.Data;
import org.prakarshs.Syntax.Expression;
import org.prakarshs.Syntax.Literals.Literal;

@Data
public abstract class UnaryOperator implements Operator{
    private final Expression literal;


}
