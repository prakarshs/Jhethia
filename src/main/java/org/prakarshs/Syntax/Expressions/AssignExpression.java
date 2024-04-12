package org.prakarshs.Syntax.Expressions;

import org.prakarshs.Syntax.Values.Value;

public interface AssignExpression {
    Value<?> assign(Value<?> value);
}
