package org.prakarshs.Syntax.Expressions;

import org.prakarshs.Syntax.Literals.Literal;

public interface Expression {
    Literal<?> evaluate();
}
