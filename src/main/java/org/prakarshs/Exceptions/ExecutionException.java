package org.prakarshs.Exceptions;

import lombok.Data;

@Data
public class ExecutionException extends JhethiaException{

    public ExecutionException(String problem, String solution) {
        super(problem, solution);
    }
}
