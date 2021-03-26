package model.Statements;

import Exceptions.ExpressionException;
import Exceptions.StatementException;
import model.ADTs.MyIDictionary;
import model.ADTs.MyIHeap;
import model.Expressions.Exp;
import model.PrgState.PrgState;
import model.Type.RefType;
import model.Type.Type;
import model.Value.IntValue;
import model.Value.RefValue;
import model.Value.Value;

public class NewStmt implements IStmt {
    String id;
    Exp expression;

    public NewStmt(String id, Exp expression) {
        this.id = id;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException {
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIHeap<Integer, Value> heap = state.getHeap();
        if (symTbl.lookup(id) != null) {
            Value v1 = symTbl.lookup(id);
            Type typ = v1.getType();
            if (typ instanceof RefType) {
                RefValue rv1 = (RefValue) v1;
                try {
                    Value v2 = expression.eval(symTbl, heap);
                    if (v2.getType().equals(rv1.getLocationType())) {
                        int loc = heap.getNextFreeLocation();
                        heap.add(loc, v2);
                        symTbl.update(id, new RefValue(loc, v2.getType()));
                    }
                    else throw new StatementException("The types are not equal");
                }
                catch (ExpressionException e) { throw new StatementException("Expression exception"); }
            }
            else throw new StatementException("Variable is not of RefType");
        }
        else throw new StatementException("Variable undefined");
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws StatementException {
        Type t1 = typeEnv.lookup(id);
        try {
            Type t2 = expression.typecheck(typeEnv);
            if (t1.equals(new RefType(t2))) {
                return typeEnv;
            }
            else throw new StatementException("Types do not match");
        }
        catch (ExpressionException e) { throw new StatementException("Expression exception"); }
    }

    @Override
    public String toString() {
        return "New(" + id + ", " + expression.toString() + ")";
    }
}
