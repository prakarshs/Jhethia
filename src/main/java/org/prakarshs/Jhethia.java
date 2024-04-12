package org.prakarshs;

import lombok.SneakyThrows;
import org.prakarshs.Context.ExceptionContext;
import org.prakarshs.Context.MemoryContext;
import org.prakarshs.Context.definition.DefinitionContext;
import org.prakarshs.Syntax.Statements.CompositeStatement;
import org.prakarshs.Parser.LexicalParser;
import org.prakarshs.Parser.StatementParser;
import org.prakarshs.Tokens.Token;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Jhethia {

    @SneakyThrows
    public void execute(Path path) {
        String sourceCode = Files.readString(path);
        List<Token> tokens = LexicalParser.parse(sourceCode);

        DefinitionContext.pushScope(DefinitionContext.newScope());
        MemoryContext.pushScope(MemoryContext.newScope());
        try {
            CompositeStatement statement = new CompositeStatement(null, path.getFileName().toString());
            StatementParser.parse(tokens, statement);
            statement.execute();
        } finally {
            DefinitionContext.endScope();
            MemoryContext.endScope();

            if (ExceptionContext.isRaised()) {
                ExceptionContext.printStackTrace();
            }
        }
    }

}
