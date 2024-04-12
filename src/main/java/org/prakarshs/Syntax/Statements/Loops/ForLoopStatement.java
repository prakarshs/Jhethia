package org.prakarshs.Syntax.Statements.Loops;

import org.prakarshs.Context.MemoryContext;
import org.prakarshs.Syntax.Expressions.Expression;
import org.prakarshs.Syntax.Expressions.VariableExpression;
import org.prakarshs.Syntax.Expressions.Operators.AdditionOperator;
import org.prakarshs.Syntax.Expressions.Operators.LessThanOperator;
import org.prakarshs.Syntax.Values.LogicalValue;
import org.prakarshs.Syntax.Values.NumericValue;
import org.prakarshs.Syntax.Values.Value;

public class ForLoopStatement extends AbstractLoopStatement {
    private final VariableExpression variable;
    private final Expression lowerBound;
    private final Expression uppedBound;
    private final Expression step;
    private static final Expression DEFAULT_STEP = new NumericValue(1.0);

    public ForLoopStatement(Integer rowNumber, String blockName, VariableExpression variable, Expression lowerBound, Expression uppedBound) {
        this(rowNumber, blockName, variable, lowerBound, uppedBound, DEFAULT_STEP);
    }

    public ForLoopStatement(Integer rowNumber, String blockName, VariableExpression variable, Expression lowerBound, Expression uppedBound, Expression step) {
        super(rowNumber, blockName);
        this.variable = variable;
        this.lowerBound = lowerBound;
        this.uppedBound = uppedBound;
        this.step = step;
    }

    @Override
    protected void init() {
        MemoryContext.getScope().set(variable.getName(), lowerBound.evaluate());
    }

    @Override
    protected boolean hasNext() {
        LessThanOperator hasNext = new LessThanOperator(variable, uppedBound);
        Value<?> value = hasNext.evaluate();
        return value instanceof LogicalValue && ((LogicalValue) value).getValue();
    }

    @Override
    protected void preIncrement() {
    }

    @Override
    protected void postIncrement() {
        AdditionOperator stepOperator = new AdditionOperator(variable, step);
        MemoryContext.getScope().set(variable.getName(), stepOperator.evaluate());
    }
}
