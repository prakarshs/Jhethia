package org.prakarshs.Syntax.Expressions.Operators;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.prakarshs.Syntax.Expressions.Expression;

@RequiredArgsConstructor
@Getter
@ToString
public abstract class UnaryOperatorExpression implements OperatorExpression {
    private final Expression value;
}

