package model.Statements;

import Exceptions.ExpressionException;
import Exceptions.StatementException;
import model.ADTs.MyIDictionary;
import model.PrgState.PrgState;
import model.Type.IntType;
import model.Type.Type;
import model.Value.BoolValue;
import model.Value.IntValue;
import model.Value.Value;

public class VarDeclStmt implements IStmt {
    String name;
    Type typ;

    public VarDeclStmt(String name, Type typ) {
        this.name = name;
        this.typ = typ;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException {
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        if (symTbl.lookup(name) == null) {
            symTbl.add(name, typ.defaultValue());
            return null;
        }
        else throw new StatementException("Variable already defined");
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws StatementException {
        typeEnv.add(name, typ);
        return typeEnv;
    }

    @Override
    public String toString() {
        return typ.toString() + " " + name;
    }

}
