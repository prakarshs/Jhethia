package org.prakarshs.context;

import org.prakarshs.Syntax.Statements.Loops.AbstractLoopStatement;
import org.prakarshs.Syntax.Statements.Loops.BreakStatement;

/**
 * Associates a given {@link BreakScope} with a loop block
 * <p>
 *
 * @see AbstractLoopStatement
 * @see BreakStatement
 */
public class BreakContext {
    private static BreakScope scope = new BreakScope();

    /**
     * Get current {@link BreakScope}
     */
    public static BreakScope getScope() {
        return scope;
    }

    /**
     * Reset state of the {@link BreakContext} on loop exit
     */
    public static void reset() {
        BreakContext.scope = new BreakScope();
    }
}
