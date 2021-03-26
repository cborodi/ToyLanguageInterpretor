package model.Statements;

import Exceptions.StatementException;
import model.ADTs.MyIDictionary;
import model.ADTs.MyIStack;
import model.PrgState.PrgState;
import model.Type.Type;

public class CompStmt implements IStmt {
    IStmt statement1;
    IStmt statement2;

    public CompStmt(IStmt statement1, IStmt statement2) {
        this.statement1 = statement1;
        this.statement2 = statement2;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException {
        MyIStack<IStmt> stack = state.getExeStack();
        stack.push(statement2);
        stack.push(statement1);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws StatementException {
        return statement2.typecheck(statement1.typecheck(typeEnv));
    }

    @Override
    public String toString() {
        return "(" + statement1.toString() + ";" + statement2.toString() + ")";
    }
}
