package org.prakarshs.Syntax.Expressions;

import lombok.Data;
import org.prakarshs.Syntax.Literals.Literal;
import org.prakarshs.Syntax.Literals.TextLiteral;

import java.util.function.BiConsumer;
import java.util.function.Function;

@Data
public class Variable implements Expression {
    private final String variableName;
    private final Function<String, Literal<?>> variableLiteral;
    private final BiConsumer<String, Literal<?>> setter;

    @Override
    public Literal<?> evaluate() {
        Literal<?> value = variableLiteral.apply(variableName);
        if (value == null) {
            return new TextLiteral(variableName);
        }
        return value;
    }

    public void setValue(Literal<?> literal) {
        setter.accept(variableName,literal);
    }
}
