package model.Expressions;

import Exceptions.ExpressionException;
import model.ADTs.MyIDictionary;
import model.ADTs.MyIHeap;
import model.Type.Type;
import model.Value.Value;

public class VarExp implements Exp {
    String id;

    public VarExp(String id) {
        this.id = id;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> symTbl, MyIHeap<Integer, Value> heap) throws ExpressionException {
        if (symTbl.lookup(id) != null) {
            return symTbl.lookup(id);
        }
        else throw new ExpressionException("This variable was not defined");
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws ExpressionException {
        return typeEnv.lookup(id);
    }

    @Override
    public String toString() {
        return id;
    }
}
