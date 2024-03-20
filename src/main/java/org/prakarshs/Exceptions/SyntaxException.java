package org.prakarshs.Exceptions;

import lombok.Data;

@Data
public class SyntaxException extends JhethiaException{

    public SyntaxException(String problem, String solution) {
        super(problem, solution);
    }
}
