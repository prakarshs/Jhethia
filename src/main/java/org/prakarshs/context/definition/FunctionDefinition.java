package org.prakarshs.context.definition;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.prakarshs.Parser.StatementParser;
import org.prakarshs.Syntax.Statements.FunctionStatement;
import org.prakarshs.Tokens.Token;

/**
 * Definition for a function
 * <p>
 *
 * @see StatementParser#parseFunctionDefinition(Token)
 */
@RequiredArgsConstructor
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FunctionDefinition implements Definition {
    /**
     * Details for a function
     */
    @EqualsAndHashCode.Include
    private final FunctionDetails details;
    /**
     * Statement(s) defined in the function body
     */
    private final FunctionStatement statement;
    /**
     * Contains nested classes and functions defined in this function
     */
    private final DefinitionScope definitionScope;
}
