package org.prakarshs.Syntax.Statements;

import lombok.Getter;

@Getter
public class FunctionStatement extends CompositeStatement {
    public FunctionStatement(Integer rowNumber, String blockName) {
        super(rowNumber, blockName);
    }
}
