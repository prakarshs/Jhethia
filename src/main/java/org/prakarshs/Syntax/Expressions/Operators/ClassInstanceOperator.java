package org.prakarshs.Syntax.Expressions.Operators;

import org.prakarshs.Syntax.Expressions.Expression;
import org.prakarshs.Syntax.Values.Value;

public class ClassInstanceOperator extends UnaryOperatorExpression {
    public ClassInstanceOperator(Expression value) {
        super(value);
    }

    @Override
    public Value<?> evaluate() {
        return getValue().evaluate(); // will return toString() value
    }
}

