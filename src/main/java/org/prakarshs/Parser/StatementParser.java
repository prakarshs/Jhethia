package org.prakarshs.Parser;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.prakarshs.Tokens.Token;

import java.util.*;

@Data
@AllArgsConstructor
public class StatementParser {
    private final List<Token> tokens;
    private int position;



}
