package model.PrgState;

import model.ADTs.MyIDictionary;
import model.ADTs.MyIHeap;
import model.ADTs.MyIList;
import model.ADTs.MyIStack;
import model.Statements.IStmt;
import model.Value.IntValue;
import model.Value.StringValue;
import model.Value.Value;

import java.io.BufferedReader;

public class PrgState {
    MyIStack<IStmt> exeStack;
    MyIDictionary<String, Value> symTable;
    MyIList<Value> out;
    MyIDictionary<StringValue, BufferedReader> fileTable;
    MyIHeap<Integer, Value> heap;
    IStmt originalProgram;
    int stateID;
    public static int id = 0;

    public PrgState(MyIStack<IStmt> exeStack, MyIDictionary<String, Value> symTable, MyIList<Value> out, MyIDictionary<StringValue, BufferedReader> fileTable, MyIHeap<Integer, Value> heap, IStmt originalProgram) {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.fileTable = fileTable;
        this.heap = heap;
        this.originalProgram = originalProgram;
        this.stateID = getNewID();
        exeStack.push(originalProgram);
    }

    public static synchronized int getNewID() { id++; return id; }

    public MyIStack<IStmt> getExeStack() { return this.exeStack; }
    public void setExeStack(MyIStack<IStmt> stack) { this.exeStack = stack; }

    public MyIDictionary<String, Value> getSymTable() { return this.symTable; }
    public void setSymTable(MyIDictionary<String, Value> dict) { this.symTable = dict; }

    public MyIList<Value> getOut() { return this.out; }
    public void setOut(MyIList<Value> list) { this.out = list; }

    public MyIDictionary<StringValue, BufferedReader> getFileTable() { return this.fileTable; }
    public void setOut(MyIDictionary<StringValue, BufferedReader> dict) { this.fileTable = dict; }

    public MyIHeap<Integer, Value> getHeap() { return this.heap; }
    public void setHeap(MyIHeap<Integer, Value> heap) { this.heap = heap; }

    public int getID() { return stateID; }

    public boolean isNotCompleted() {
        return !exeStack.isEmpty();
    }

    public PrgState oneStep() throws Exception {
        if (exeStack.isEmpty()) throw new Exception("PrgState stack is empty");
        IStmt crtStmt = exeStack.pop();
        return crtStmt.execute(this);
    }

    @Override
    public String toString() {
        return  "Id:\n" + id + "\n" +
                "Execution stack:\n" + exeStack.toString() + "\n" +
                "Symbol Table:\n" + symTable.toString() + "\n" +
                "Out:\n" + out.toString() + "\n" +
                "FileTable:\n" + fileTable.toString() + "\n" +
                "Heap:\n" + heap.toString() + "\n";
    }
}
