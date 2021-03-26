package Repository;

import model.PrgState.PrgState;
import model.Statements.IStmt;

import java.util.List;

public interface IRepository {
    List<PrgState> getPrgList();
    void setPrgList(List<PrgState> newPrograms);
    void logPrgStateExec(PrgState state) throws Exception;
    IStmt getOriginalProgram();
}
