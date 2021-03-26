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

public class WhileStmt implements IStmt {
    Exp expression;
    IStmt statement;

    public WhileStmt(Exp expression, IStmt statement) {
        this.expression = expression;
        this.statement = statement;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException {
        MyIStack<IStmt> exeStack = state.getExeStack();
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIHeap<Integer, Value> heap = state.getHeap();
        try {
            Value v = expression.eval(symTbl, heap);
            if(v.getType().equals(new BoolType())) {
                if(((BoolValue) v).getVal()) {
                    exeStack.push(this);
                    exeStack.push(statement);
                }
                return null;
            }
            else throw new StatementException("Condition is not bool");
        }
        catch (ExpressionException e) { throw new StatementException("Expression exception"); }
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws StatementException {
        try {
            Type t1 = expression.typecheck(typeEnv);
            if (t1.equals(new BoolType())) {
                statement.typecheck(typeEnv.deepCopy());
                return typeEnv;
            }
            else throw new StatementException("Condition is not bool");
        }
        catch (ExpressionException e) { throw new StatementException("Expression exception"); }
    }

    @Override
    public String toString() {
        return "while(" + expression.toString() + ")do(" + statement.toString() + ")";
    }
}
