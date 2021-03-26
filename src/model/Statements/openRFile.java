package model.Statements;

import Exceptions.ExpressionException;
import Exceptions.StatementException;
import model.ADTs.MyIDictionary;
import model.ADTs.MyIHeap;
import model.Expressions.Exp;
import model.PrgState.PrgState;
import model.Type.StringType;
import model.Type.Type;
import model.Value.StringValue;
import model.Value.Value;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class openRFile implements IStmt {
    Exp expression;

    public openRFile(Exp exp) {
        this.expression = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException {
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIDictionary<StringValue, BufferedReader> filetable = state.getFileTable();
        MyIHeap<Integer, Value> heap = state.getHeap();
        try {
            Value v = expression.eval(symTbl, heap);
            if (v.getType().equals(new StringType())) {
                StringValue sv = (StringValue) v;
                if (filetable.lookup(sv) == null) {
                    try {
                        BufferedReader bufferedReader = new BufferedReader(new FileReader(sv.getVal()));
                        filetable.add(sv, bufferedReader);
                    }
                    catch(FileNotFoundException e) { throw new StatementException("File not found"); }
                }
                else throw new StatementException("The file already exists");
            }
            else throw new StatementException("Type of filename is not string");
        }
        catch (ExpressionException e) { throw new StatementException("Expression exception"); }
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws StatementException {
        try {
            Type exptyp = expression.typecheck(typeEnv);
            if (exptyp.equals(new StringType())) {
                return typeEnv;
            }
            else throw new StatementException("Open file expression is not a string");
        }
        catch (ExpressionException e) {
            throw new StatementException("Expression Exception");
        }
    }

    @Override
    public String toString() {
        return "openfile(" + expression.toString() + ")";
    }
}
