package org.prakarshs.Syntax.Statements;

import lombok.Getter;
import org.prakarshs.Context.ExceptionContext;
import org.prakarshs.Syntax.Expressions.Expression;
import org.prakarshs.Syntax.Values.NullValue;
import org.prakarshs.Syntax.Values.TextValue;
import org.prakarshs.Syntax.Values.Value;

@Getter
public class RaiseExceptionStatement extends Statement {
    private final Expression expression;

    public RaiseExceptionStatement(Integer rowNumber, String blockName, Expression expression) {
        super(rowNumber, blockName);
        this.expression = expression;
    }

    @Override
    public void execute() {
        Value<?> value = expression.evaluate();
        if (value != null) {
            if (value == NullValue.NULL_INSTANCE) {
                value = new TextValue("Khaali(Null) Exception Hai Daya!");
            }
            ExceptionContext.raiseException(value);
        }
        ExceptionContext.addTracedStatement(this);
    }
}
