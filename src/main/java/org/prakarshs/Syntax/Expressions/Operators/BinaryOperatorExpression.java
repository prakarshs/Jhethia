package org.prakarshs.Syntax.Expressions.Operators;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.prakarshs.Syntax.Expressions.Expression;

@RequiredArgsConstructor
@Getter
@ToString
public abstract class BinaryOperatorExpression implements OperatorExpression {
    private final Expression left;
    private final Expression right;
}

