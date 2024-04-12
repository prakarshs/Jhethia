package org.prakarshs.context.definition;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.prakarshs.Parser.StatementParser;
import org.prakarshs.Syntax.Statements.ClassStatement;
import org.prakarshs.Tokens.Token;

import java.util.Set;

/**
 * Definition for a class
 * <p>
 *
 * @see StatementParser#parseClassDefinition(Token)
 */
@RequiredArgsConstructor
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ClassDefinition implements Definition {
    /**
     * Details for a class
     */
    @EqualsAndHashCode.Include
    private final ClassDetails classDetails;
    /**
     * Details of the inherited (super) classes
     */
    private final Set<ClassDetails> baseTypes;
    /**
     * Constructor statement
     */
    private final ClassStatement statement;
    /**
     * Contains nested classes and functions defined in this class
     */
    private final DefinitionScope definitionScope;
}

