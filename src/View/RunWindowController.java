package View;

import Controller.Controller;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.ADTs.*;
import model.PrgState.PrgState;
import model.Statements.IStmt;
import model.Value.StringValue;
import model.Value.Value;

import javafx.scene.input.*;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RunWindowController {
    Controller controller;

    @FXML
    private TextField textField;
    @FXML
    private TableView<HeapEntry> heapTblTableView;
    @FXML
    private TableView<SymTblEntry> symTblTableView;
    @FXML
    private ListView<Value> outListView;
    @FXML
    private ListView<StringValue> fileTableListView;
    @FXML
    private ListView<Integer> programStateListView;
    @FXML
    private ListView<IStmt> exeStackListView;
    @FXML
    private Button oneStepButton;
    @FXML
    private TableColumn<HeapEntry, String> heapAddress;
    @FXML
    private TableColumn<HeapEntry, String> heapValue;
    @FXML
    private TableColumn<SymTblEntry, String> symTblName;
    @FXML
    private TableColumn<SymTblEntry, String> symTblValue;
    private PrgState lastProgramState;

    public RunWindowController(Controller c) {
        this.controller = c;
        this.lastProgramState = null;
    }

    @FXML
    void initialize() {
        heapAddress.setCellValueFactory(new PropertyValueFactory<HeapEntry, String>("address"));
        heapValue.setCellValueFactory(new PropertyValueFactory<HeapEntry, String>("value"));
        symTblName.setCellValueFactory(new PropertyValueFactory<SymTblEntry, String>("name"));
        symTblValue.setCellValueFactory(new PropertyValueFactory<SymTblEntry, String>("value"));

        programStateListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        try {
            populateAll();
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Nothing to execute");
            alert.setHeaderText("There is nothing to execute");
            alert.showAndWait();
        }
    }

    private void populateAll() throws Exception {
        List<PrgState> prgStates = controller.getPrgList();

        PrgState currentProgram;

        if(prgStates.size() == 0) {
            textField.setText("0 programs");
            if (lastProgramState == null) {
                throw new Exception("No more program states");
            }
            else {
                currentProgram = lastProgramState;
                lastProgramState = null;
            }
        }
        else {
            lastProgramState = prgStates.get(0);
            currentProgram = prgStates.get(0);
            textField.setText(prgStates.size() + " programs");
        }

        populatePrgState(prgStates);
        programStateListView.getSelectionModel().selectIndices(0);

        try {
            populateHeapTable(currentProgram);
            populateOutList(currentProgram);
            populateFileTableList(currentProgram);
            populateSymTbl(currentProgram);
            populateExeStack(currentProgram);
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Populating Error");
            alert.setHeaderText("A populating error has occured");
            alert.setContentText(e.toString());
            alert.showAndWait();
        }
    }

    public void populateHeapTable(PrgState program) {
        MyIHeap<Integer, Value> heap = program.getHeap();
        ArrayList<HeapEntry> entries = new ArrayList<HeapEntry>();
        for(Map.Entry<Integer, Value> entry : heap.getContent().entrySet()) {
            entries.add(new HeapEntry(entry.getKey(), entry.getValue()));
        }
        heapTblTableView.setItems(FXCollections.observableArrayList(entries));
    }

    public void populateOutList(PrgState program) {
        MyIList<Value> out = program.getOut();
        ArrayList<Value> result = new ArrayList<Value>();
        for(int i=0; i<out.size(); i++) {
            result.add(out.get(i));
        }
        outListView.setItems(FXCollections.observableArrayList(result));
    }

    public void populateFileTableList(PrgState program) {
        MyIDictionary<StringValue, BufferedReader> fileTable = program.getFileTable();
        ArrayList<StringValue> list = new ArrayList<>(fileTable.getContent().keySet());
        fileTableListView.setItems(FXCollections.observableArrayList(list));
    }

    public void populatePrgState(List<PrgState> states) {
        List<Integer> ids = states.stream().map(PrgState::getID).collect(Collectors.toList());
        programStateListView.setItems(FXCollections.observableArrayList(ids));
    }

    public void populateSymTbl(PrgState program) {
        MyIDictionary<String, Value> symTbl = program.getSymTable();
        ArrayList<SymTblEntry> res = new ArrayList<SymTblEntry>();
        for(Map.Entry<String, Value> entry : symTbl.getContent().entrySet()) {
            res.add(new SymTblEntry(entry.getKey(), entry.getValue()));
        }
        symTblTableView.setItems(FXCollections.observableArrayList(res));
    }

    public void populateExeStack(PrgState program) {
        MyIStack<IStmt> exeStack = program.getExeStack();
        exeStackListView.setItems(FXCollections.observableArrayList(exeStack.toList()));
    }

    @FXML
    void runOneStep(MouseEvent event) {
        try {
            List<PrgState> states = controller.getPrgList();
            if (states.size() > 0 || lastProgramState != null)
                controller.evaluateOnlyOneStep();
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Execution Error");
            alert.setHeaderText("An execution error has occured!");
            alert.setContentText(e.toString());

            alert.showAndWait();

        }

        try {
            populateAll();
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Nothing to execute");
            alert.setHeaderText("There is nothing left to execute!");

            alert.showAndWait();
        }
    }

    @FXML
    void changeState(MouseEvent event) {
        List<PrgState> states = controller.getPrgList();
        int index = programStateListView.getSelectionModel().getSelectedIndex();
        PrgState program = states.get(index);
        try {
            populateExeStack(program);
            populateSymTbl(program);
        }
        catch(Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Switch error");
            alert.setHeaderText("Switch error occured");
            alert.setContentText(e.toString());
            alert.showAndWait();
        }
    }
}
