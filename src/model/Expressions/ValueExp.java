package model.Expressions;

import Exceptions.ExpressionException;
import model.ADTs.MyIDictionary;
import model.ADTs.MyIHeap;
import model.Type.Type;
import model.Value.Value;

public class ValueExp implements Exp {
    Value e;

    public ValueExp(Value e) {
        this.e = e;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> symTbl, MyIHeap<Integer, Value> heap) throws ExpressionException {
        return e;
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws ExpressionException {
        return e.getType();
    }

    @Override
    public String toString() {
        return e.toString();
    }
}
