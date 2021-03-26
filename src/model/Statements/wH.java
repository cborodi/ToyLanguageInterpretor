package model.Statements;

import Exceptions.ExpressionException;
import Exceptions.StatementException;
import model.ADTs.MyIDictionary;
import model.ADTs.MyIHeap;
import model.Expressions.Exp;
import model.PrgState.PrgState;
import model.Type.RefType;
import model.Type.Type;
import model.Value.RefValue;
import model.Value.Value;

public class wH implements IStmt {
    String id;
    Exp expression;

    public wH(String var_name, Exp expression) {
        this.id = var_name;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException {
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIHeap<Integer, Value> heap = state.getHeap();
        if (symTbl.lookup(this.id) != null) {
            Value v = symTbl.lookup(this.id);
            if (v.getType() instanceof RefType) {
                int loc = ((RefValue) v).getAddress();
                if(heap.lookup(loc) != null) {
                    try {
                        Value ev = expression.eval(symTbl, heap);
                        if (ev.getType().equals(((RefValue) v).getLocationType())) {
                            heap.update(loc, ev);
                            return null;
                        }
                        else throw new StatementException("Expression type is not equal to to the type of the variable");
                    }
                    catch (ExpressionException e) { throw new StatementException("Expression Exception"); }
                }
                else throw new StatementException("The address from the considered RefValue is not a key in the heap");
            }
            else throw new StatementException("The type of the variable must be RefType");
        }
        else throw new StatementException("Variable undefined");
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws StatementException {
        Type t1 = typeEnv.lookup(id);
        try {
            Type t2 = expression.typecheck(typeEnv);
            if (t1.equals(new RefType(t2))) {
                return typeEnv;
            }
            else throw new StatementException("Wrong expression type");
        }
        catch (ExpressionException e) {
            throw new StatementException("Expression Exception");
        }
    }

    @Override
    public String toString() {
        return "writeHeap(" + this.id + ", " + expression.toString() + ")";
    }

}
