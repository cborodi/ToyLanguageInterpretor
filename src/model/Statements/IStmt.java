package model.Statements;

import Exceptions.StatementException;
import model.ADTs.MyIDictionary;
import model.PrgState.PrgState;
import model.Type.Type;

public interface IStmt {
    PrgState execute(PrgState state) throws StatementException;

    MyIDictionary<String,Type> typecheck(MyIDictionary<String, Type> typeEnv) throws StatementException;
}
