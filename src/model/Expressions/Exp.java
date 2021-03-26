package model.Expressions;

import Exceptions.ExpressionException;
import model.ADTs.MyIDictionary;
import model.ADTs.MyIHeap;
import model.Type.Type;
import model.Value.IntValue;
import model.Value.Value;

public interface Exp {
    Value eval(MyIDictionary<String, Value> symTbl, MyIHeap<Integer, Value> heap) throws ExpressionException;

    Type typecheck(MyIDictionary<String, Type> typeEnv) throws ExpressionException;
}
