package org.prakarshs.Syntax.Statements;

import lombok.Getter;
import org.prakarshs.Context.ExceptionContext;
import org.prakarshs.Syntax.Expressions.Expression;

@Getter
public class ExpressionStatement extends Statement {
    private final Expression expression;

    public ExpressionStatement(Integer rowNumber, String blockName, Expression expression) {
        super(rowNumber, blockName);
        this.expression = expression;
    }

    @Override
    public void execute() {
        expression.evaluate();
        ExceptionContext.addTracedStatement(this);
    }
}
