package org.prakarshs.Syntax.Statements.Loops;

import org.prakarshs.Syntax.Expressions.Expression;
import org.prakarshs.Syntax.Values.LogicalValue;
import org.prakarshs.Syntax.Values.Value;

public class WhileLoopStatement extends AbstractLoopStatement {
    private final Expression hasNext;

    public WhileLoopStatement(Integer rowNumber, String blockName, Expression hasNext) {
        super(rowNumber, blockName);
        this.hasNext = hasNext;
    }

    @Override
    protected void init() {
    }

    @Override
    protected boolean hasNext() {
        Value<?> value = hasNext.evaluate();
        return value instanceof LogicalValue && ((LogicalValue) value).getValue();
    }

    @Override
    protected void preIncrement() {
    }

    @Override
    protected void postIncrement() {
    }
}
