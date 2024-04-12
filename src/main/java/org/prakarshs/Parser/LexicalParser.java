package org.prakarshs.Parser;

import org.prakarshs.Constants.ErrorConstants;
import org.prakarshs.Exceptions.SyntaxException;
import org.prakarshs.Tokens.Token;
import org.prakarshs.Tokens.TokenType;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LexicalParser {

    private final List<Token> tokens;

    private final String source;

    private int rowNumber;

    public static List<Token> parse(String sourceCode) {
        LexicalParser parser = new LexicalParser(sourceCode);
        parser.parse();
        return parser.tokens;
    }

    private LexicalParser(String source) {
        this.source = source;
        this.tokens = new ArrayList<>();
        this.rowNumber = 1;
    }

    private void parse() {
        int position = 0;
        while (position < source.length()) {
            position += nextToken(position);
        }
    }

    private int nextToken(int position) {
        String nextToken = source.substring(position);

        for (TokenType tokenType : TokenType.values()) {
            Pattern pattern = Pattern.compile("^" + tokenType.getRegex());
            Matcher matcher = pattern.matcher(nextToken);
            if (matcher.find()) {
                if (tokenType != TokenType.Whitespace) {
                    String value = matcher.groupCount() > 0 ? matcher.group(1) : matcher.group();
                    Token token = Token.builder().type(tokenType).value(value).rowNumber(rowNumber).build();
                    tokens.add(token);

                    if (tokenType == TokenType.LineBreak) {
                        rowNumber++;
                    }
                }

                return matcher.group().length();
            }
        }
        String problem = ErrorConstants.SYNTAX_GALAT_HAI;
        String solution = String.format("Invalid expression at line %d", rowNumber);
        System.out.println("Poblem : "+problem);
        System.out.println("Solution : "+solution);
        throw new SyntaxException(problem,solution);
    }

}
