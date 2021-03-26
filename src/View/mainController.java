package View;

import Controller.Controller;
import Exceptions.StatementException;
import Repository.Repository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.ADTs.MyDictionary;
import model.ADTs.MyIDictionary;
import model.ADTs.MyList;
import model.Expressions.*;
import model.Statements.*;
import model.Type.*;
import model.Value.BoolValue;
import model.Value.IntValue;
import model.Value.StringValue;

import java.util.LinkedList;

public class mainController {
    @FXML
    private ListView<Controller> programListView;
    @FXML
    private Button runButton;

    @FXML
    private void runInstance(ActionEvent event) {
        Controller controller = programListView.getSelectionModel().getSelectedItem();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("runWindow.fxml"));
            RunWindowController rwc = new RunWindowController(controller);
            loader.setController(rwc);
            StackPane root = (StackPane) loader.load();
            Scene scene = new Scene(root, 600, 600);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Program run");
            stage.show();

        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Loading error");
            alert.setHeaderText("A loading error has occured");
            alert.setContentText(e.toString());

            alert.showAndWait();
        }
    }

    @FXML
    public void initialize() {
        programListView.setItems(getControllerList());
        programListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    static IStmt example1() {
        return new CompStmt(new VarDeclStmt("v",new IntType()),
                new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(2))), new PrintStmt(new VarExp("v"))));
    }

    static IStmt example2() {
        return new CompStmt( new VarDeclStmt("a",new IntType()),
                new CompStmt(new VarDeclStmt("b",new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp(new ValueExp(new IntValue(2)),new
                                ArithExp(new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5)),3), 1)),
                                new CompStmt(new AssignStmt("b",new ArithExp(new VarExp("a"), new
                                        ValueExp(new IntValue(1)), 1)), new PrintStmt(new VarExp("b"))))));
    }

    static IStmt example3() {
        return new CompStmt(new VarDeclStmt("a",new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarExp("a"),new AssignStmt("v",new ValueExp(new
                                        IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new
                                        VarExp("v"))))));
    }

    static IStmt example4() {
        return new CompStmt(
                new VarDeclStmt("varf", new StringType()),
                new CompStmt(
                        new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
                        new CompStmt(
                                new openRFile(new VarExp("varf")),
                                new CompStmt(
                                        new VarDeclStmt("varc", new IntType()),
                                        new CompStmt(
                                                new readFile(new VarExp("varf"), "varc"),
                                                new CompStmt(
                                                        new PrintStmt(new VarExp("varc")),
                                                        new CompStmt(
                                                                new readFile(new VarExp("varf"), "varc"),
                                                                new CompStmt(
                                                                        new PrintStmt(new VarExp("varc")),
                                                                        new closeRFile(new VarExp("varf"))
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )

                )
        );
    }

    static IStmt example5() {
        return new CompStmt(
                new VarDeclStmt("a", new IntType()),
                new CompStmt(
                        new AssignStmt("a", new ValueExp(new IntValue(7))),
                        new CompStmt(
                                new VarDeclStmt("b", new IntType()),
                                new CompStmt(
                                        new AssignStmt("b", new ValueExp(new IntValue(10))),
                                        new CompStmt(
                                                new VarDeclStmt("min", new IntType()),
                                                new CompStmt(
                                                        new IfStmt(new RelExp(new VarExp("a"), new VarExp("b"), 1), new AssignStmt("min", new VarExp("a")), new AssignStmt("min", new VarExp("b"))),
                                                        new PrintStmt(new VarExp("min"))
                                                )
                                        )
                                )
                        )
                )
        );
    }

    static IStmt example6() {
        return new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new VarExp("a")))))));
    }

    static IStmt example7() {
        return new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new PrintStmt(new rH(new VarExp("v"))),
                                                new PrintStmt(new ArithExp(new rH(new rH(new VarExp("a"))), new ValueExp(new IntValue(5)), 1)))))));
    }

    static IStmt example8() {
        return new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new PrintStmt(new rH(new VarExp("v"))),
                                new CompStmt(new wH("v", new ValueExp(new IntValue(30))),
                                        new PrintStmt(new ArithExp(new rH(new VarExp("v")), new ValueExp(new IntValue(5)), 1))))));
    }

    static IStmt example9() {
        return new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new NewStmt("v", new ValueExp(new IntValue(30))),
                                                new PrintStmt(new rH(new rH(new VarExp("a")))
                                                ))))));
    }

    static IStmt example10() {
        return new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(4))),
                        new CompStmt(new WhileStmt(new RelExp(new VarExp("v"), new ValueExp(new IntValue(0)), 5),
                                new CompStmt(new PrintStmt(new VarExp("v")),
                                        new AssignStmt("v", new ArithExp(new VarExp("v"), new ValueExp(new IntValue(1)), 2)))),
                                new PrintStmt(new VarExp("v")))));
    }

    static IStmt example11() {
        return new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new VarDeclStmt("a", new RefType(new IntType())),
                        new CompStmt(
                                new AssignStmt("v", new ValueExp(new IntValue(10))),
                                new CompStmt(
                                        new NewStmt("a", new ValueExp(new IntValue(22))),
                                        new CompStmt(
                                                new forkStmt(
                                                        new CompStmt(
                                                                new wH("a", new ValueExp(new IntValue(30))),
                                                                new CompStmt(
                                                                        new AssignStmt("v", new ValueExp(new IntValue(32))),
                                                                        new CompStmt(
                                                                                new PrintStmt(new VarExp("v")),
                                                                                new PrintStmt(new rH(new VarExp("a")))
                                                                        )
                                                                )
                                                        )
                                                ),
                                                new CompStmt(
                                                        new PrintStmt(new VarExp("v")),
                                                        new PrintStmt(new rH(new VarExp("a")))
                                                )
                                        )
                                )
                        )

                )
        );
    }

    private MyList<IStmt> getStatementList() {
        MyList<IStmt> statements = new MyList<>();
        statements.add(example1());
        statements.add(example2());
        statements.add(example3());
        statements.add(example4());
        statements.add(example5());
        statements.add(example6());
        statements.add(example7());
        statements.add(example8());
        statements.add(example9());
        statements.add(example10());
        statements.add(example11());
        return statements;
    }

    private ObservableList<Controller> getControllerList() {
        MyList<IStmt> statements = getStatementList();
        LinkedList<Controller> controllers = new LinkedList<>();
        MyIDictionary<String, Type> typeEnv = new MyDictionary<>();

        for(int i=0; i < statements.size(); i++) {
            try {
                typeEnv.clear();
                IStmt statement = statements.get(i);
                statement.typecheck(typeEnv);
                Repository repo = new Repository(statement, "log" + String.valueOf(i + 1) + ".txt");
                Controller controller = new Controller(repo);
                controllers.add(controller);
            }
            catch (StatementException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Statement/Typecheck Error");
                alert.setHeaderText("A statement/typecheck error has occured");
                alert.setContentText(e.toString());

                alert.showAndWait();
            }
        }
        return FXCollections.observableArrayList(controllers);
    }

}
