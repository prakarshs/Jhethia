package org.prakarshs.Syntax.Statements;

import lombok.Getter;
import org.prakarshs.Context.ExceptionContext;
import org.prakarshs.Syntax.Expressions.Expression;
import org.prakarshs.Syntax.Values.Value;

@Getter
public class PrintStatement extends Statement {
    private final Expression expression;

    public PrintStatement(Integer rowNumber, String blockName, Expression expression) {
        super(rowNumber, blockName);
        this.expression = expression;
    }

    @Override
    public void execute() {
        Value<?> value = expression.evaluate();
        if (value != null) {
            System.out.println(value);
        }
        ExceptionContext.addTracedStatement(this);
    }
}
