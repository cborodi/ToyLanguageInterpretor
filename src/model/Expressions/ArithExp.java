package model.Expressions;

import Exceptions.ExpressionException;
import model.ADTs.MyIDictionary;
import model.ADTs.MyIHeap;
import model.Type.IntType;
import model.Type.Type;
import model.Value.IntValue;
import model.Value.Value;

public class ArithExp implements Exp {
    Exp e1;
    Exp e2;
    int operation;

    public ArithExp(Exp e1, Exp e2, int op) {
        this.e1 = e1;
        this.e2 = e2;
        this.operation = op;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> symTbl, MyIHeap<Integer, Value> heap) throws ExpressionException {
        Value v1, v2;
        v1 = e1.eval(symTbl, heap);
        if(v1.getType().equals(new IntType())) {
            v2 = e2.eval(symTbl, heap);
            if(v2.getType().equals(new IntType())) {
                IntValue i1 =(IntValue) v1;
                IntValue i2 =(IntValue) v2;
                int n1, n2;
                n1 = i1.getVal();
                n2 = i2.getVal();
                if(this.operation == 1) { return new IntValue(n1 + n2);}
                else if(this.operation == 2) { return new IntValue(n1 - n2);}
                else if(this.operation == 3) { return new IntValue(n1 * n2);}
                else if(this.operation == 4) { if (n2 != 0) return new IntValue(n1 / n2);
                                               else throw new ExpressionException("Division by 0");}
            }
            else throw new ExpressionException("Type of second operand of arithmetic expression is not int");
        }
        throw new ExpressionException("Type of first operand of arithmetic expression is not int");
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws ExpressionException {
        Type t1 = e1.typecheck(typeEnv);
        Type t2 = e2.typecheck(typeEnv);
        if (t1.equals(new IntType())) {
            if (t2.equals(new IntType()))
                return new IntType();
            else throw new ExpressionException("Second operand is not an integer");
        }
        else throw new ExpressionException("First operand is not an integer");
    }

    @Override
    public String toString() {
        return switch (this.operation) {
            case 1 -> this.e1.toString() + " + " + this.e2.toString();
            case 2 -> this.e1.toString() + " - " + this.e2.toString();
            case 3 -> this.e1.toString() + " * " + this.e2.toString();
            case 4 -> this.e1.toString() + " / " + this.e2.toString();
            default -> null;
        };
    }
}
