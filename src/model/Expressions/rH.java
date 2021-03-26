package model.Expressions;

import Exceptions.ExpressionException;
import model.ADTs.MyIDictionary;
import model.ADTs.MyIHeap;
import model.Type.IntType;
import model.Type.RefType;
import model.Type.Type;
import model.Value.RefValue;
import model.Value.Value;


public class rH implements Exp {
    Exp expression;

    public rH(Exp expression) {
        this.expression = expression;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> symTbl, MyIHeap<Integer, Value> heap) throws ExpressionException {
        Value v = expression.eval(symTbl, heap);
        if (v.getType() instanceof RefType) {
            int addr = ((RefValue) v).getAddress();
            if (heap.lookup(addr) != null) {
                return heap.lookup(addr);
            }
            else throw new ExpressionException("The value is not an address in the heap table");
        }
        else throw new ExpressionException("Expression not evaluated as RefValue");
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws ExpressionException {
        Type t = expression.typecheck(typeEnv);
        if (t instanceof RefType) {
            RefType reft = (RefType) t;
            return reft.getInner();
        }
        else throw new ExpressionException("The rH argument is not a RefType");
    }

    @Override
    public String toString() {
        return "readHeap(" + expression.toString() + ")";
    }
}
