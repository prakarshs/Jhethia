package org.prakarshs.Syntax.Statements;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.prakarshs.Syntax.Expressions.Expression;
import org.prakarshs.Syntax.Literals.Literal;
import org.prakarshs.Syntax.Literals.LogicalLiteral;

@Data
@AllArgsConstructor
public class PrintStatement implements Statement{
    private final Expression expression;
    @Override
    public void execute() {
        Literal<?> literal = expression.evaluate();
        boolean value = (Boolean) literal.getLiteral();
        if(literal instanceof LogicalLiteral){
            if (value) {
                System.out.println("sahi baat hai!");
            } else {
                System.out.println("galat baat hai!");
            }
        }
        else {
            System.out.println(literal);
        }
    }
}
