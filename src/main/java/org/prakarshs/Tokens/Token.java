package org.prakarshs.Tokens;

import lombok.Builder;
import lombok.Data;

// Token (lexeme) details
@Data
@Builder
public class Token {
    private final TokenType type;
    private final String value;
}
