package org.prakarshs.Context;

import lombok.Getter;
import org.prakarshs.Syntax.Statements.Loops.BreakStatement;

/**
 * Scope for the loop block defining if the <strong>break</strong> statement invoked
 * <p>
 *
 * @see BreakContext
 * @see BreakStatement
 */
@Getter
public class BreakScope {
    private boolean invoked;

    /**
     * Notify the loop block about invoking the <strong>break</strong> statement
     */
    public void invoke() {
        this.invoked = true;
    }
}
