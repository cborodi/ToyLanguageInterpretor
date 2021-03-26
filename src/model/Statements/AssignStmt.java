package model.Statements;

import Exceptions.ExpressionException;
import Exceptions.StatementException;
import model.ADTs.MyIDictionary;
import model.ADTs.MyIHeap;
import model.Expressions.Exp;
import model.PrgState.PrgState;
import model.Type.Type;
import model.Value.Value;

public class AssignStmt implements IStmt {
    String id;
    Exp exp;

    public AssignStmt(String id, Exp exp) {
        this.id = id;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException {
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIHeap<Integer, Value> heap = state.getHeap();
        if (symTbl.lookup(id) != null) {
            try {
                Value val = exp.eval(symTbl, heap);
                if(val.getType().equals(symTbl.lookup(id).getType()))
                {
                    symTbl.update(id, val);
                    return null;
                }
                else throw new StatementException("Types do not match");
            }
            catch (ExpressionException e) {
                throw new StatementException("Expression Exception");
            }
        }
        else throw new StatementException("Variable is not defined");
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws StatementException {
        Type t1 = typeEnv.lookup(id);
        try {
            Type t2 = exp.typecheck(typeEnv);
            if (t1.equals(t2)) {
                return typeEnv;
            }
            else throw new StatementException("Types do not match");
        }
        catch (ExpressionException e) { throw new StatementException("Expression exception"); }
    }

    @Override
    public String toString() {
        return id + " = " + exp.toString();
    }
}
