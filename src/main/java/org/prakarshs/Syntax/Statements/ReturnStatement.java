package org.prakarshs.Syntax.Statements;

import lombok.Getter;
import org.prakarshs.context.ExceptionContext;
import org.prakarshs.context.ReturnContext;
import org.prakarshs.Syntax.Expressions.Expression;
import org.prakarshs.Syntax.Values.Value;

@Getter
public class ReturnStatement extends Statement {
    private final Expression expression;

    public ReturnStatement(Integer rowNumber, String blockName, Expression expression) {
        super(rowNumber, blockName);
        this.expression = expression;
    }

    @Override
    public void execute() {
        Value<?> result = expression.evaluate();
        if (result != null) {
            ReturnContext.getScope().invoke(result);
        }
        ExceptionContext.addTracedStatement(this);
    }
}
