package model.Statements;

import Exceptions.ExpressionException;
import Exceptions.StatementException;
import model.ADTs.MyIDictionary;
import model.PrgState.PrgState;
import model.Type.Type;

public class NopStmt implements IStmt {

    @Override
    public PrgState execute(PrgState state) throws StatementException {
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws StatementException {
        return typeEnv;
    }
}
