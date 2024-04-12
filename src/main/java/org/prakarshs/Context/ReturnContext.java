package org.prakarshs.Context;

import org.prakarshs.Syntax.Expressions.FunctionExpression;
import org.prakarshs.Syntax.Statements.CompositeStatement;
import org.prakarshs.Syntax.Statements.ReturnStatement;
import org.prakarshs.Syntax.Statements.Loops.AbstractLoopStatement;

/**
 * Associates a given {@link ReturnScope} with {@link CompositeStatement}
 * <p>
 *
 * @see AbstractLoopStatement
 * @see ReturnStatement
 * @see FunctionExpression
 */
public class ReturnContext {
    private static ReturnScope scope = new ReturnScope();

    /**
     * Get current {@link ReturnScope}
     */
    public static ReturnScope getScope() {
        return scope;
    }

    /**
     * Reset state of the {@link ReturnContext} on block exit
     */
    public static void reset() {
        ReturnContext.scope = new ReturnScope();
    }
}
