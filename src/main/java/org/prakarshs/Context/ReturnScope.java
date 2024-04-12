package org.prakarshs.Context;

import lombok.Getter;
import org.prakarshs.Syntax.Values.Value;
import org.prakarshs.Syntax.Statements.CompositeStatement;

/**
 * Scope for the {@link CompositeStatement} defining if the <strong>return</strong> statement invoked
 * <p>
 *
 * @see BreakContext
 */
@Getter
public class ReturnScope {
    private boolean invoked;
    private Value<?> result;

    /**
     * Notify current scope that <strong>return</strong> statement invoked
     */
    public void invoke(Value<?> result) {
        this.invoked = true;
        this.result = result;
    }
}
