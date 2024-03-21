package org.prakarshs.Syntax.Statements;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.prakarshs.Syntax.Expressions.Expression;
import org.prakarshs.Syntax.Literals.Literal;

import java.util.function.BiConsumer;

@Data
@AllArgsConstructor
public class AssignStatement implements Statement{
    private final String variableName;
    private final Expression expression;

    private final BiConsumer<String, Literal<?>> variableSetter;
    @Override
    public void execute() {
        variableSetter.accept(variableName, expression.evaluate());
    }
}
