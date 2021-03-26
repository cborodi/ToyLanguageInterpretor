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
import java.io.IOException;

public class closeRFile implements IStmt {
    Exp expression;

    public closeRFile(Exp exp) {
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
                if (filetable.lookup(sv) != null) {
                    BufferedReader bufferedReader = filetable.lookup(sv);
                    try {
                        bufferedReader.close();
                        filetable.delete(sv);
                    }
                    catch (IOException e) { throw new StatementException("IOException"); }
                }
                else throw new StatementException("File not defined");
            }
            else throw new StatementException("Type of filename is not string");
        } catch (ExpressionException e) {
            throw new StatementException("Expression exception");
        }
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws StatementException {
        try {
            Type exptyp = expression.typecheck(typeEnv);
            if (exptyp.equals(new StringType())) {
                return typeEnv;
            }
            else throw new StatementException("Close file expression is not a string");
        }
        catch (ExpressionException e) {
            throw new StatementException("Expression Exception");
        }
    }

    @Override
    public String toString() {
        return "closefile(" + expression.toString() + ")";
    }
}
