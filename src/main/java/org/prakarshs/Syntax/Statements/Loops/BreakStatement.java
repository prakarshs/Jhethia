package org.prakarshs.Syntax.Statements.Loops;

import org.prakarshs.Context.BreakContext;
import org.prakarshs.Syntax.Statements.Statement;

public class BreakStatement extends Statement {
    public BreakStatement(Integer rowNumber, String blockName) {
        super(rowNumber, blockName);
    }

    @Override
    public void execute() {
        BreakContext.getScope().invoke();
    }
}
