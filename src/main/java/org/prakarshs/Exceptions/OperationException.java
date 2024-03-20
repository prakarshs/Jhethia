package org.prakarshs.Exceptions;

import lombok.Data;

@Data
public class OperationException extends JhethiaException{

    public OperationException(String problem, String solution) {
        super(problem, solution);
    }
}
