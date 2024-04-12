package org.prakarshs.Syntax.Statements;

import lombok.Getter;
import org.prakarshs.Parser.StatementParser;
import org.prakarshs.context.definition.ClassDefinition;
import org.prakarshs.Tokens.Token;

@Getter
public class ClassStatement extends CompositeStatement {
    private final Integer rowNumber;

    public ClassStatement(Integer rowNumber, String blockName) {
        super(rowNumber, blockName);
        this.rowNumber = rowNumber;
    }
}
