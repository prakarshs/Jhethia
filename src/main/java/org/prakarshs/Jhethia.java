package org.prakarshs;

import lombok.SneakyThrows;
import org.prakarshs.Parser.LexicalParser;
import org.prakarshs.Parser.StatementParser;
import org.prakarshs.Syntax.Statements.Statement;
import org.prakarshs.Tokens.Token;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Jhethia {
    @SneakyThrows
    public void execute(Path path) {
        String source = Files.readString(path);
        LexicalParser lexicalParser = new LexicalParser(source);
        List<Token> tokens = lexicalParser.parse();
        StatementParser statementParser = new StatementParser(tokens);
        Statement statement = statementParser.parse();
        statement.execute();
    }
}
