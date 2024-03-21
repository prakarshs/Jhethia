package org.prakarshs.Syntax.Statements;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class CompoundStatement implements Statement{
    private final List<Statement> statementsToExecute = new ArrayList<>();

    public void addStatement(Statement statement) {
        if (statement != null)
            statementsToExecute.add(statement);
    }
    @Override
    public void execute() {
        statementsToExecute.forEach(Statement::execute);
    }
}
