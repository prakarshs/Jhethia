package org.prakarshs.Syntax.Statements;

import lombok.Getter;

@Getter
public class ClassStatement extends CompositeStatement {
    private final Integer rowNumber;

    public ClassStatement(Integer rowNumber, String blockName) {
        super(rowNumber, blockName);
        this.rowNumber = rowNumber;
    }
}
