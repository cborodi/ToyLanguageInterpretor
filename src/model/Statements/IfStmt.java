package model.Statements;

import Exceptions.ExpressionException;
import Exceptions.StatementException;
import model.ADTs.MyIDictionary;
import model.ADTs.MyIHeap;
import model.ADTs.MyIStack;
import model.Expressions.Exp;
import model.PrgState.PrgState;
import model.Type.BoolType;
import model.Type.Type;
import model.Value.BoolValue;
import model.Value.Value;

public class IfStmt implements IStmt {
    Exp condition;
    IStmt thenS;
    IStmt elseS;

    public IfStmt(Exp condition, IStmt thenS, IStmt elseS) {
        this.condition = condition;
        this.thenS = thenS;
        this.elseS = elseS;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException {
        MyIStack<IStmt> exeStack = state.getExeStack();
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIHeap<Integer, Value> heap = state.getHeap();
        try {
            Value v = condition.eval(symTbl, heap);
            if (v.getType().equals(new BoolType())) {
                BoolValue v1 = (BoolValue) v;
                boolean cond = v1.getVal();
                if (cond) {
                    exeStack.push(this.thenS);
                }
                else {
                    exeStack.push(this.elseS);
                }
            }
            else throw new StatementException("Condition is not of bool type");
        }
        catch (ExpressionException e) { throw new StatementException("Expression exception"); }
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws StatementException {
        try {
            Type t1 = condition.typecheck(typeEnv);
            if (t1.equals(new BoolType())) {
                thenS.typecheck(typeEnv.deepCopy());
                elseS.typecheck(typeEnv.deepCopy());
                return typeEnv;
            }
            else throw new StatementException("Condition is not bool");
        }
        catch (ExpressionException e) { throw new StatementException("Expression exception"); }
    }

    @Override
    public String toString() {
        return "IF(" + condition.toString() + ")" + "THEN(" + thenS.toString() + ")ELSE(" + elseS.toString() + "))";
    }
}
