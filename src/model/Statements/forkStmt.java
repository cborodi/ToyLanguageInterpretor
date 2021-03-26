package model.Statements;

import Exceptions.ExpressionException;
import Exceptions.StatementException;
import model.ADTs.MyIDictionary;
import model.ADTs.MyIStack;
import model.ADTs.MyStack;
import model.PrgState.PrgState;
import model.Type.BoolType;
import model.Type.Type;
import model.Value.Value;

public class forkStmt implements IStmt {
    IStmt statement;

    public forkStmt(IStmt statement) {
        this.statement = statement;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException {
        return new PrgState(new MyStack<>(), state.getSymTable().deepCopy(), state.getOut(), state.getFileTable(), state.getHeap(), statement);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws StatementException {
        statement.typecheck(typeEnv.deepCopy());
        return typeEnv;
    }

    @Override
    public String toString() {
        return "fork(" + statement.toString() + ")";
    }
}
