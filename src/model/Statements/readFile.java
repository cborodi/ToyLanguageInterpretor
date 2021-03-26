package model.Statements;

import Exceptions.ExpressionException;
import Exceptions.StatementException;
import model.ADTs.MyIDictionary;
import model.ADTs.MyIHeap;
import model.Expressions.Exp;
import model.PrgState.PrgState;
import model.Type.IntType;
import model.Type.StringType;
import model.Type.Type;
import model.Value.IntValue;
import model.Value.StringValue;
import model.Value.Value;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class readFile implements IStmt {
    Exp expression;
    String id;

    public readFile(Exp exp, String id) {
        this.expression = exp;
        this.id = id;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException {
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIDictionary<StringValue, BufferedReader> filetable = state.getFileTable();
        MyIHeap<Integer, Value> heap = state.getHeap();
        if (symTbl.lookup(id) != null) {
            Value v1 = symTbl.lookup(id);
            if(v1.getType().equals(new IntType())) {
                try {
                    Value v2 = expression.eval(symTbl, heap);
                    if (v2.getType().equals(new StringType())) {
                        StringValue sv = (StringValue) v2;
                        if (filetable.lookup(sv) != null) {
                            BufferedReader bufferedReader = filetable.lookup(sv);
                            try {
                                String line = bufferedReader.readLine();
                                IntValue iv;
                                if (line == null) {
                                    iv = new IntValue(0);
                                }
                                else iv = new IntValue(Integer.parseInt(line));
                                symTbl.update(id, iv);
                                return null;
                            }
                            catch (IOException e) { throw new StatementException("IOException"); }
                        }
                        else throw new StatementException("There is no file with that name defined");
                    }
                    else throw new StatementException("Filename is not string");
                }
                catch (ExpressionException e) { throw new StatementException("Expression exception"); }
            }
            else throw new StatementException("Variable type is not int");
        }
        else throw new StatementException("Variable is not defined");
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws StatementException {
        Type typevar = typeEnv.lookup(id);
        try {
            Type exptyp = expression.typecheck(typeEnv);
            if (typevar.equals(new IntType()) && exptyp.equals(new StringType())) {
                return typeEnv;
            }
            else throw new StatementException("readFile: File is not a string");
        }
        catch (ExpressionException e) {
            throw new StatementException("Expression Exception");
        }
    }

    @Override
    public String toString() {
        return "readfile(" + expression.toString() + "," + id + ")";
    }
}