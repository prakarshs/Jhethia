package org.prakarshs.Syntax.Expressions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import org.prakarshs.Context.definition.ClassDefinition;
import org.prakarshs.Context.definition.DefinitionContext;
import org.prakarshs.Syntax.Values.ClassValue;
import org.prakarshs.Syntax.Values.NullValue;
import org.prakarshs.Syntax.Values.Value;
import org.prakarshs.Syntax.Statements.ClassStatement;
import org.prakarshs.Context.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Getter
@ToString(onlyExplicitlyIncluded = true)
public class ClassExpression implements Expression {
    @ToString.Include
    private final String name;
    private final List<? extends Expression> propertiesExpressions;
    // contains Derived class and all the Base classes chain that Derived class inherits
    private final Map<String, ClassValue> relations;

    public ClassExpression(String name, List<? extends Expression> propertiesExpressions) {
        this(name, propertiesExpressions, new HashMap<>());
    }

    @Override
    public Value<?> evaluate() {
        //initialize class's properties
        List<ValueReference> values = new ArrayList<>(propertiesExpressions.size());
        for (Expression expression : propertiesExpressions) {
            ValueReference value = ValueReference.instanceOf(expression);
            if (value == null) return null;
            values.add(value);
        }
        return evaluate(values);
    }

    /**
     * Evaluate nested class
     *
     * @param classValue instance of the parent class
     */
    public Value<?> evaluate(ClassValue classValue) {
        //initialize class's properties
        List<ValueReference> values = new ArrayList<>(propertiesExpressions.size());
        for (Expression expression : propertiesExpressions) {
            ValueReference value = ValueReference.instanceOf(expression);
            if (value == null) return null;
            values.add(value);
        }

        //set parent class's definition
        ClassDefinition classDefinition = classValue.getValue();
        DefinitionContext.pushScope(classDefinition.getDefinitionScope());

        try {
            return evaluate(values);
        } finally {
            DefinitionContext.endScope();
        }
    }

    private Value<?> evaluate(List<ValueReference> values) {
        //get class's definition and statement
        ClassDefinition definition = DefinitionContext.getScope().getClass(name);
        if (definition == null) {
            return ExceptionContext.raiseException(String.format("Kilass `%s` is not defined", name));
        }
        ClassStatement classStatement = definition.getStatement();

        //set separate scope
        MemoryScope classScope = new MemoryScope(null);
        MemoryContext.pushScope(classScope);

        //initialize constructor arguments
        ClassValue classValue = new ClassValue(definition, classScope, relations);
        relations.put(name, classValue);

        // fill the missing properties with NullValue.NULL_INSTANCE
        // class A [arg1, arg2]
        // new A [arg1] -> new A [arg1, null]
        // new A [arg1, arg2, arg3] -> new A [arg1, arg2]
        List<ValueReference> valuesToSet = IntStream.range(0, definition.getClassDetails().getProperties().size())
                .boxed()
                .map(i -> values.size() > i ? values.get(i) : ValueReference.instanceOf(NullValue.NULL_INSTANCE))
                .collect(Collectors.toList());

        //invoke constructors of the base classes and set a ClassValue relation
        definition.getBaseTypes()
                .stream()
                .map(baseType -> {
                    // initialize base class's properties
                    // class A [a_arg]
                    // class B [b_arg1, b_arg2]: A [b_arg1]
                    List<ValueReference> baseClassProperties = baseType.getProperties().stream()
                            .map(t -> definition.getClassDetails().getProperties().indexOf(t))
                            .map(valuesToSet::get)
                            .collect(Collectors.toList());
                    return new ClassExpression(baseType.getName(), baseClassProperties, relations);
                })
                .forEach(ClassExpression::evaluate);

        try {
            ClassInstanceContext.pushValue(classValue);
            IntStream.range(0, definition.getClassDetails().getProperties().size()).boxed()
                    .forEach(i -> MemoryContext.getScope()
                            .setLocal(definition.getClassDetails().getProperties().get(i), valuesToSet.get(i)));

            //execute function body
            DefinitionContext.pushScope(definition.getDefinitionScope());
            try {
                classStatement.execute();
            } finally {
                DefinitionContext.endScope();
            }

            // if exception have been thrown in the constructor
            if (ExceptionContext.isRaised())
                return null;

            return classValue;
        } finally {
            MemoryContext.endScope();
            ClassInstanceContext.popValue();
        }
    }
}
