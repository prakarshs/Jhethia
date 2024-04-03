package org.prakarshs.Syntax.Expressions;

import lombok.Data;
import org.prakarshs.Syntax.Literals.Literal;
import org.prakarshs.Syntax.Literals.StructLiteral;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class StructExpression implements Expression, Comparable<StructExpression> {
    private final StructDefinition structDefinition;
    private final Function<String, Literal<?>> variableValue;
    private final List<Expression> expressionList;

    @Override
    public Literal<?> evaluate() {
        return new StructLiteral(this);
    }

    public Literal<?> getArgumentValue(String field) {
        return IntStream
                .range(0, expressionList.size())
                .filter(i -> structDefinition.getArguments().get(i).equals(field))
                .mapToObj(this::getValue) //will be implemented later
                .findFirst()
                .orElse(null);
    }

    private Literal<?> getValue(int index) {
        Expression expression = expressionList.get(index);
        if (expression instanceof Variable) {
            return variableValue.apply(((Variable) expression).getVariableName());
        } else {
            return expression.evaluate();
        }
    }

    @Override
    public int compareTo(StructExpression o) {
        for (String field : structDefinition.getArguments()) {
            Literal<?> value = getArgumentValue(field);
            Literal<?> oValue = o.getArgumentValue(field);
            if (value == null && oValue == null) continue;
            if (value == null) return -1;
            if (oValue == null) return 1;
            //noinspection unchecked,rawtypes
            int result = ((Comparable) value.getLiteral()).compareTo(oValue.getLiteral());
            if (result != 0) return result;
        }
        return 0;
    }

    @Override
    public String toString() {
        return IntStream
                .range(0, expressionList.size())
                .mapToObj(i -> {
                    Literal<?> value = getValue(i); //will be implemented later
                    String fieldName = structDefinition.getArguments().get(i);
                    return fieldName + " = " + value;
                })
                .collect(Collectors.joining(", ", structDefinition.getStructName() + " [ ", " ]"));
    }

}
