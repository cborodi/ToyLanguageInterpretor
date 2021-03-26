package model.Expressions;

import Exceptions.ExpressionException;
import model.ADTs.MyIDictionary;
import model.ADTs.MyIHeap;
import model.Type.BoolType;
import model.Type.IntType;
import model.Type.Type;
import model.Value.BoolValue;
import model.Value.Value;

public class LogicExp implements Exp {
    Exp e1;
    Exp e2;
    int operation;

    public LogicExp(Exp e1, Exp e2, int op) {
        this.e1 = e1;
        this.e2 = e2;
        this.operation = op;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> symTbl, MyIHeap<Integer, Value> heap) throws ExpressionException {
        Value v1, v2;
        v1 = e1.eval(symTbl, heap);
        if(v1.getType().equals(new BoolType())) {
            v2 = e2.eval(symTbl, heap);
            if(v2.getType().equals(new BoolType())) {
                BoolValue i1 =(BoolValue) v1;
                BoolValue i2 =(BoolValue) v2;
                boolean n1, n2;
                n1 = i1.getVal();
                n2 = i2.getVal();
                if(this.operation == 1) { return new BoolValue(n1 && n2);}
                else if(this.operation == 2) { return new BoolValue(n1 || n2);}
            }
            else throw new ExpressionException("Type of second operand of logic expression is not boolean");
        }
        throw new ExpressionException("Type of first operand of logic expression is not boolean");
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws ExpressionException {
        Type t1 = e1.typecheck(typeEnv);
        Type t2 = e2.typecheck(typeEnv);
        if (t1.equals(new BoolType())) {
            if (t2.equals(new BoolType()))
                return new BoolType();
            else throw new ExpressionException("Second operand is not a boolean");
        }
        else throw new ExpressionException("First operand is not a boolean");
    }

    @Override
    public String toString() {
        return switch (this.operation) {
            case 1 -> this.e1.toString() + " && " + this.e2.toString();
            case 2 -> this.e1.toString() + " || " + this.e2.toString();
            default -> null;
        };
    }
}
