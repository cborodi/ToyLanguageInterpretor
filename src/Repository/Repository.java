package Repository;

import model.ADTs.*;
import model.PrgState.PrgState;
import model.Statements.IStmt;
import model.Value.IntValue;
import model.Value.StringValue;
import model.Value.Value;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

public class Repository implements IRepository {
    ArrayList<PrgState> programs;
    IStmt originalProgram;
    String logFilePath;

    public Repository(IStmt program, String logFilePath) {
        this.originalProgram = program;
        this.logFilePath = logFilePath;
        this.programs = new ArrayList<>();
        PrgState newState = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap<>(), program);
        programs.add(newState);
    }

    @Override
    public List<PrgState> getPrgList() {
        return this.programs;
    }

    @Override
    public IStmt getOriginalProgram() {
        return originalProgram;
    }

    @Override
    public void setPrgList(List<PrgState> newPrograms) {
        this.programs = (ArrayList<PrgState>) newPrograms;
    }

    @Override
    public void logPrgStateExec(PrgState state) throws Exception {
        PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        logFile.println("Id:");
        logFile.println(state.getID());
        logFile.println();
        logFile.println("ExeStack:");
        state.getExeStack().stream().map(x -> x + "\n").collect(Collectors.toCollection(LinkedList::new)).descendingIterator().forEachRemaining(logFile::print);
        logFile.println();
        logFile.println("SymTable:");
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        for (Map.Entry<String, Value> stringValueEntry : symTbl.entrySet()) {
            logFile.println(((Map.Entry) stringValueEntry).getKey() + " --> " + ((Map.Entry) stringValueEntry).getValue());
        }
        logFile.println();
        logFile.println("Out:");
        state.getOut().stream().map(x -> x + "\n").forEach(logFile::print);
        logFile.println();
        logFile.println("FileTable:");
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        for (Map.Entry<StringValue, BufferedReader> stringValueEntry : fileTable.entrySet()) {
            logFile.println(((Map.Entry) stringValueEntry).getKey());
        }
        logFile.println();
        logFile.println("HeapTable:");
        MyIHeap<Integer, Value> heapTbl = state.getHeap();
        for (Map.Entry<Integer, Value> stringValueEntry : heapTbl.entrySet()) {
            logFile.println(((Map.Entry) stringValueEntry).getKey() + " --> " + ((Map.Entry) stringValueEntry).getValue());
        }
        logFile.println();
        logFile.println("-------------------------------------------------------");
        logFile.println();
        logFile.close();
    }
}
