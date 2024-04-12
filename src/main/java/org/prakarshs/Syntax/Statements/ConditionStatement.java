package org.prakarshs.Syntax.Statements;

import lombok.Getter;
import org.prakarshs.Context.MemoryContext;
import org.prakarshs.Syntax.Expressions.Expression;
import org.prakarshs.Syntax.Values.LogicalValue;
import org.prakarshs.Syntax.Values.Value;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
public class ConditionStatement extends Statement {
    private final Map<Expression, CompositeStatement> cases;

    public ConditionStatement(Integer rowNumber, String blockName) {
        super(rowNumber, blockName);
        //keep the cases order
        this.cases = new LinkedHashMap<>();
    }

    public void addCase(Expression caseCondition, CompositeStatement caseStatement) {
        cases.put(caseCondition, caseStatement);
    }

    @Override
    public void execute() {
        for (Map.Entry<Expression, CompositeStatement> entry : cases.entrySet()) {

            Expression condition = entry.getKey();
            Value<?> value = condition.evaluate();
            if (value instanceof LogicalValue && ((LogicalValue) value).getValue()) {
                MemoryContext.pushScope(MemoryContext.newScope());
                try {
                    CompositeStatement statement = entry.getValue();
                    statement.execute();
                } finally {
                    MemoryContext.endScope();
                }
                break;
            }
        }
    }
}
