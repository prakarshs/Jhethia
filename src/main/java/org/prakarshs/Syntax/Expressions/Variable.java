package org.prakarshs.Syntax.Expressions;

import lombok.Data;
import org.prakarshs.Syntax.Expression;
@Data
public class Variable implements Expression {
    private final String variableName;
}
