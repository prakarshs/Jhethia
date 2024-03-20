package org.prakarshs.Syntax.Expressions.Operators;

import lombok.Data;
import org.prakarshs.Syntax.Expression;
@Data
public class UnaryOperator implements Operator{
    private final Expression literal;
}
