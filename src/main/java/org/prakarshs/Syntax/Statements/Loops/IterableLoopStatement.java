package org.prakarshs.Syntax.Statements.Loops;

import org.prakarshs.context.ExceptionContext;
import org.prakarshs.context.MemoryContext;
import org.prakarshs.Syntax.Expressions.Expression;
import org.prakarshs.Syntax.Expressions.VariableExpression;
import org.prakarshs.Syntax.Values.IterableValue;
import org.prakarshs.Syntax.Values.Value;

import java.util.Iterator;

public class IterableLoopStatement extends AbstractLoopStatement {
    private final VariableExpression variableExpression;
    private final Expression iterableExpression;

    private Iterator<Value<?>> iterator;

    public IterableLoopStatement(Integer rowNumber, String blockName, VariableExpression variableExpression, Expression iterableExpression) {
        super(rowNumber, blockName);
        this.variableExpression = variableExpression;
        this.iterableExpression = iterableExpression;
    }

    @Override
    protected void init() {
        Value<?> value = iterableExpression.evaluate();
        if (!(value instanceof IterableValue)) {
            ExceptionContext.raiseException(String.format("Ghum Nahi Pa Raha`%s`", value));
            return;
        }
        this.iterator = ((IterableValue<?>) value).iterator();
    }

    @Override
    protected boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    protected void preIncrement() {
        MemoryContext.getScope().set(variableExpression.getName(), iterator.next());
    }

    @Override
    protected void postIncrement() {
    }
}
