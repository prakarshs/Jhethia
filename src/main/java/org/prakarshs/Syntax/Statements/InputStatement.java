package org.prakarshs.Syntax.Statements;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.prakarshs.Syntax.Literals.Literal;
import org.prakarshs.Syntax.Literals.LogicalLiteral;
import org.prakarshs.Syntax.Literals.NumericalLiteral;
import org.prakarshs.Syntax.Literals.TextLiteral;

import java.util.function.BiConsumer;
import java.util.function.Supplier;
@Data
@AllArgsConstructor
public class InputStatement implements Statement{
    private final String variableName;
    private final Supplier<String> consoleSupplier;
    private final BiConsumer<String, Literal<?>> variableSetter;
    @Override
    public void execute() {
        System.out.printf("enter \"%s\" >>> ", variableName.replace("_", " "));
        String line = consoleSupplier.get();

        Literal<?> literal;
        if (line.matches("[0-9]+")) {
            literal = new NumericalLiteral(Integer.parseInt(line));
        } else if (line.matches("sahi_baat_hai|galat_baat_hai")) {
            literal = new LogicalLiteral(Boolean.valueOf(line));
        } else {
            literal = new TextLiteral(line);
        }

        variableSetter.accept(variableName,literal);
    }
}
