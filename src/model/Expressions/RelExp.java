package model.Expressions;

import Exceptions.ExpressionException;
import model.ADTs.MyIDictionary;
import model.ADTs.MyIHeap;
import model.Type.BoolType;
import model.Type.IntType;
import model.Type.Type;
import model.Value.BoolValue;
import model.Value.IntValue;
import model.Value.Value;

public class RelExp implements Exp {
    Exp exp1;
    Exp exp2;
    int rel;

    public RelExp(Exp exp1, Exp exp2, int rel) {
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.rel = rel;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> symTbl, MyIHeap<Integer, Value> heap) throws ExpressionException {
        Value v1 = exp1.eval(symTbl, heap);
        Value v2 = exp2.eval(symTbl, heap);
        if (v1.getType().equals(new IntType()) && v2.getType().equals(new IntType())) {
            IntValue i1 =(IntValue) v1;
            IntValue i2 =(IntValue) v2;

            int n1 = i1.getVal();
            int n2 = i2.getVal();

            return switch (rel) {
                case 1 -> new BoolValue(n1 < n2);
                case 2 -> new BoolValue(n1 <= n2);
                case 3 -> new BoolValue(n1 == n2);
                case 4 -> new BoolValue(n1 != n2);
                case 5 -> new BoolValue(n1 > n2);
                case 6 -> new BoolValue(n1 >= n2);
                default -> throw new ExpressionException("Bad operator");
            };
        }
        else throw new ExpressionException("Operands are of type different than int");

    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws ExpressionException {
        Type t1 = exp1.typecheck(typeEnv);
        Type t2 = exp2.typecheck(typeEnv);
        if (t1.equals(new IntType())) {
            if (t2.equals(new IntType()))
                return new BoolType();
            else throw new ExpressionException("Second operand is not an integer");
        }
        else throw new ExpressionException("First operand is not an integer");
    }

    @Override
    public String toString() {
        return switch (this.rel) {
            case 1 -> this.exp1 + " < " + this.exp2;
            case 2 -> this.exp1 + " <= " + this.exp2;
            case 3 -> this.exp1 + " == " + this.exp2;
            case 4 -> this.exp1 + " != " + this.exp2;
            case 5 -> this.exp1 + " > " + this.exp2;
            case 6 -> this.exp1 + " >= " + this.exp2;
            default -> null;
        };
    }
}
