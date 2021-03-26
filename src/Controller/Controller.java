package Controller;

import Exceptions.ExpressionException;
import Repository.IRepository;
import model.ADTs.MyIStack;
import model.PrgState.PrgState;
import model.Statements.IStmt;
import model.Value.RefValue;
import model.Value.Value;

import java.sql.Ref;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class Controller {
    IRepository repo;
    ExecutorService executor;

    public Controller(IRepository repo) {
        this.repo = repo;
        executor = Executors.newFixedThreadPool(3);
    }

    public void oneStepForAll(List<PrgState> prgList) throws Exception {
        prgList.forEach(prg -> {
            try {
                repo.logPrgStateExec(prg);
            } catch (Exception e) {
                System.out.println("Controller exception");
            }
        });

        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>)(() -> { return p.oneStep(); }))
                .collect(Collectors.toList());

        List<PrgState> newPrgList = executor.invokeAll(callList).stream()
                                    .map(future -> { try { return future.get(); }
                                    catch (InterruptedException | ExecutionException e) {
                                        System.out.println("Controller exception");
                                        return null; }
                                    })
                                    .filter(p -> p != null)
                                    .collect(Collectors.toList());

        prgList.addAll(newPrgList);

        prgList.forEach(prg -> {
            try {
                repo.logPrgStateExec(prg);
            } catch (Exception e) {
                System.out.println("Controller exception");
            }
        });

        repo.setPrgList((ArrayList<PrgState>) prgList);
    }

    public void allStep() throws Exception {
        executor = Executors.newFixedThreadPool(2);

        List<PrgState> prgList = removeCompletedPrograms(repo.getPrgList());

        while(prgList.size() > 0) {
            for (PrgState prg : prgList)
            {
                prg.getHeap().setContent(garbageCollector(
                        getAddrFromSymTbl(prg.getSymTable().getContent().values()),
                        getAddrFromHeapAddresses(prg.getHeap().getContent().values()),
                        prg.getHeap().getContent()
                ));
            }
            oneStepForAll(prgList);
            prgList = removeCompletedPrograms(repo.getPrgList());
        }

        executor.shutdownNow();

        repo.setPrgList(prgList);
    }

    public List<PrgState> removeCompletedPrograms(List<PrgState> inPrgList) {
        return inPrgList.stream()
                .filter(PrgState::isNotCompleted)
                .collect(Collectors.toList());
    }

    public void displayCrtPrgState(PrgState prg) throws Exception {
        System.out.println(prg);
        repo.logPrgStateExec(prg);
    }

    Map<Integer, Value> garbageCollector(List<Integer> symTblAddresses, List<Integer> heapAddresses, Map<Integer, Value> heap) {
        return heap.entrySet().stream()
                .filter(e -> symTblAddresses.contains(e.getKey()) || heapAddresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    List<Integer> getAddrFromSymTbl(Collection<Value> symTableValues) {
        return symTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> { RefValue v1 = (RefValue) v; return v1.getAddress(); })
                .collect(Collectors.toList());
    }

    List<Integer> getAddrFromHeapAddresses(Collection<Value> heapValues) {
        return heapValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> { RefValue v1 = (RefValue) v; return v1.getAddress(); })
                .collect(Collectors.toList());
    }

    public List<PrgState> getPrgList() {
        return repo.getPrgList();
    }

    public void evaluateOnlyOneStep() throws Exception {
        List<PrgState> list = removeCompletedPrograms(repo.getPrgList());
        for(PrgState p : list){
            p.getHeap().setContent((HashMap<Integer, Value>) garbageCollector(getAddrFromSymTbl(p.getSymTable().getContent().values()),
                    getAddrFromHeapAddresses(p.getHeap().getContent().values()), p.getHeap().getContent()));
        }
        oneStepForAll(list);
        list = removeCompletedPrograms(repo.getPrgList());
        repo.setPrgList((ArrayList<PrgState>) list);
        if (list.isEmpty()) {
            executor.shutdownNow();
        }
    }

    public String toString() {
        return repo.getOriginalProgram().toString();
    }
}
