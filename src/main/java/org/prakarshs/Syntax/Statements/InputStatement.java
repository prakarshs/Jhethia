package org.prakarshs.Syntax.Statements;

import lombok.Getter;
import org.prakarshs.Context.MemoryContext;
import org.prakarshs.Syntax.Values.LogicalValue;
import org.prakarshs.Syntax.Values.NumericValue;
import org.prakarshs.Syntax.Values.TextValue;
import org.prakarshs.Syntax.Values.Value;
import org.prakarshs.Tokens.TokenType;

import java.util.function.Supplier;

@Getter
public class InputStatement extends Statement {
    private final String name;
    private final Supplier<String> consoleSupplier;

    public InputStatement(Integer rowNumber, String blockName, String name, Supplier<String> consoleSupplier) {
        super(rowNumber, blockName);
        this.name = name;
        this.consoleSupplier = consoleSupplier;
    }

    @Override
    public void execute() {
        System.out.printf("enter \"%s\" >>> ", name.replace("_", " "));
        String line = consoleSupplier.get();

        Value<?> value=null;
        if (line.matches(TokenType.Numeric.getRegex())) {
            value = new NumericValue(Double.parseDouble(line));
        } else if (line.matches("sahi_baat_hai|galat_baat_hai")) {
            if(line.equals("sahi_baat_hai"))
                value = new LogicalValue(true);
            else if (line.equals("galat_baat_hai"))
                value = new LogicalValue(false);
        } else {
            value = new TextValue(line);
        }

        MemoryContext.getScope().set(name, value);
    }
}
