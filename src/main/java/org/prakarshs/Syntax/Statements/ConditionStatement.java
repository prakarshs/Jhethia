package org.prakarshs.Syntax.Statements;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.prakarshs.Constants.ErrorConstants;
import org.prakarshs.Exceptions.ExecutionException;
import org.prakarshs.Syntax.Expressions.Expression;
import org.prakarshs.Syntax.Literals.Literal;
import org.prakarshs.Syntax.Literals.LogicalLiteral;

@Data
@AllArgsConstructor
public class ConditionStatement extends CompoundStatement{
    private final Expression condition;

    @Override
    public void execute() {
    Literal<?> literal = condition.evaluate();
        if (literal instanceof LogicalLiteral) {
            if (((LogicalLiteral) literal).getLiteral()) {
                super.execute();
            }
        } else {
            String problem = ErrorConstants.EXECUTION_IMPOSSIBLE;
            String solution = String.format("Cannot compare non logical value `%s`", literal);
            System.out.println("Poblem : "+problem);
            System.out.println("Solution : "+solution);
            throw new ExecutionException(problem,solution);
        }
    }
}
