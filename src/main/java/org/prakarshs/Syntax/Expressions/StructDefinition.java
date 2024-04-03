package org.prakarshs.Syntax.Expressions;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class StructDefinition {
    @EqualsAndHashCode.Include
    private final String structName;
    private final List<String> arguments;
}