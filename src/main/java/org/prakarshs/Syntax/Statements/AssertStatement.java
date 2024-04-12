package org.prakarshs.Syntax.Statements;

import lombok.Getter;
import org.prakarshs.Context.ExceptionContext;
import org.prakarshs.Syntax.Expressions.Expression;
import org.prakarshs.Syntax.Values.LogicalValue;
import org.prakarshs.Syntax.Values.Value;

@Getter
public class AssertStatement extends Statement {
    private final Expression expression;

    public AssertStatement(Integer rowNumber, String blockName, Expression expression) {
        super(rowNumber, blockName);
        this.expression = expression;
    }

    @Override
    public void execute() {
        Value<?> value = expression.evaluate();
        if (value instanceof LogicalValue && !((LogicalValue) value).getValue()) {
            ExceptionContext.raiseException("Assert Karne Me Error");
            ExceptionContext.addTracedStatement(this);
        }
    }
}
