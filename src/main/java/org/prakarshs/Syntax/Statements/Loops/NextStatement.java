package org.prakarshs.Syntax.Statements.Loops;

import org.prakarshs.Syntax.Statements.Statement;
import org.prakarshs.context.NextContext;

public class NextStatement extends Statement {
    public NextStatement(Integer rowNumber, String blockName) {
        super(rowNumber, blockName);
    }

    @Override
    public void execute() {
        NextContext.getScope().invoke();
    }
}
