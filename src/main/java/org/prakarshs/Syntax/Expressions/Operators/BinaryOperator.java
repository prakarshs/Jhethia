package org.prakarshs.Syntax.Expressions.Operators;

import lombok.Data;
import org.prakarshs.Syntax.Expression;

@Data
public class BinaryOperator implements Operator{
    private final Expression left;
    private final Expression right;
}
