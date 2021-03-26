package model.Statements;

import Exceptions.ExpressionException;
import Exceptions.StatementException;
import model.ADTs.MyIDictionary;
import model.ADTs.MyIHeap;
import model.ADTs.MyIList;
import model.Expressions.Exp;
import model.PrgState.PrgState;
import model.Type.Type;
import model.Value.Value;

public class PrintStmt implements IStmt {
    Exp expression;

    public PrintStmt(Exp expression) {
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException {
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIList<Value> out = state.getOut();
        MyIHeap<Integer, Value> heap = state.getHeap();
        try {
            out.add(expression.eval(symTbl, heap));
            return null;
        }
        catch(ExpressionException e) {
            throw new StatementException("Expression exception");
        }
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws StatementException {
        try {
            expression.typecheck(typeEnv);
            return typeEnv;
        }
        catch (ExpressionException e) {
            throw new StatementException("Expression Exception");
        }
    }

    @Override
    public String toString() {
        return "print(" + expression.toString() + ")";
    }
}
