package org.prakarshs.Syntax.Expressions;

import lombok.Data;
import org.prakarshs.Syntax.Expressions.Operators.OperatorEnum;

import java.util.Stack;
@Data
public class ExpressionReader {
    private final Stack<Expression> operands;
    private final Stack<OperatorEnum> operators;

    private ExpressionReader(){
        this.operands = new Stack<>();
        this.operators = new Stack<>();
    }



}
