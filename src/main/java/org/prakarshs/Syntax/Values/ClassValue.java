package org.prakarshs.Syntax.Values;

import lombok.Getter;
import org.prakarshs.Context.MemoryContext;
import org.prakarshs.Context.MemoryScope;
import org.prakarshs.Context.definition.ClassDefinition;

import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;

import static org.prakarshs.Syntax.Values.NullValue.NULL_INSTANCE;

@Getter
public class ClassValue extends IterableValue<ClassDefinition> {
    private final MemoryScope memoryScope;
    // contains ClassValue for the Derived class and all the Base classes chain that Derived class inherits
    private final Map<String, ClassValue> relations;

    public ClassValue(ClassDefinition definition, MemoryScope memoryScope, Map<String, ClassValue> relations) {
        super(definition);
        this.memoryScope = memoryScope;
        this.relations = relations;
    }

    public ClassValue getRelation(String name) {
        return relations.get(name);
    }

    public boolean containsRelation(String name) {
        return relations.containsKey(name);
    }

    @Override
    public String toString() {
        MemoryContext.pushScope(memoryScope);
        try {
            return getValue().getClassDetails().getProperties().stream()
                    .map(t -> t + " = " + getValue(t))
                    .collect(Collectors.joining(", ", getValue().getClassDetails().getName() + " [ ", " ]"));
        } finally {
            MemoryContext.endScope();
        }
    }

    public Value<?> getValue(String name) {
        MemoryContext.pushScope(memoryScope);
        try {
            Value<?> result = MemoryContext.getScope().getLocal(name);
            return result != null ? result : NULL_INSTANCE;
        } finally {
            MemoryContext.endScope();
        }
    }

    public void setValue(String name, Value<?> value) {
        MemoryContext.pushScope(memoryScope);
        try {
            MemoryContext.getScope().setLocal(name, value);
        } finally {
            MemoryContext.endScope();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (getClass() != o.getClass()) return false;
        ClassValue oValue = (ClassValue) o;

        return getValue()
                .getClassDetails()
                .getProperties()
                .stream()
                .allMatch(e -> getValue(e).equals(oValue.getValue(e)));
    }

    @Override
    public Iterator<Value<?>> iterator() {
        return getValue()
                .getClassDetails()
                .getProperties()
                .stream()
                .<Value<?>>map(this::getValue)
                .iterator();
    }
}
