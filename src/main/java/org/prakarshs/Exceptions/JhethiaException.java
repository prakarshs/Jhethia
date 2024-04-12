package org.prakarshs.Exceptions;

import lombok.Data;

@Data
public class JhethiaException extends RuntimeException{
    private String solution;
    public JhethiaException (String problem, String solution){
        super(problem);
        this.solution = solution;
    }
}
