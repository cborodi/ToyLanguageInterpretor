package View;

import Controller.Controller;
import Exceptions.StatementException;
import Repository.*;
import model.ADTs.MyDictionary;
import model.ADTs.MyIDictionary;
import model.Statements.*;
import model.Type.*;
import model.Value.*;
import model.Expressions.*;

import java.util.Scanner;

public class Interpretor {
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

    public static void main(String[] args) {
        //Scanner s = new Scanner(System.in);
        //String path = s.nextLine();

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));

        MyIDictionary<String, Type> typeEnv = new MyDictionary<>();
        try {
            example1().typecheck(typeEnv);
            IRepository repo1 = new Repository(example1(), "log1.txt");
            Controller controller1 = new Controller(repo1);
            menu.addCommand(new RunCommand("1", example1().toString(), controller1));
        }
        catch (StatementException e) {
            System.out.println(e.toString());
        }

        try{
            example2().typecheck(typeEnv);
            Repository repository2 = new Repository(example2(), "log2.txt");
            Controller controller2 = new Controller(repository2);
            menu.addCommand(new RunCommand("2", example2().toString(), controller2));
        }
        catch (StatementException e) {
            System.out.println(e.toString());
        }

        try{
            example3().typecheck(typeEnv);
            Repository repository3 = new Repository(example3(), "log3.txt");
            Controller controller3 = new Controller(repository3);
            menu.addCommand(new RunCommand("3", example3().toString(), controller3));
        }
        catch (StatementException e) {
            System.out.println(e.toString());
        }

        try{
            example4().typecheck(typeEnv);
            Repository repository4 = new Repository(example4(), "log4.txt");
            Controller controller4 = new Controller(repository4);
            menu.addCommand(new RunCommand("4", example4().toString(), controller4));
        }
        catch (StatementException e) {
            System.out.println(e.toString());
        }

        try{
            example5().typecheck(typeEnv);
            Repository repository5 = new Repository(example5(), "log5.txt");
            Controller controller5 = new Controller(repository5);
            menu.addCommand(new RunCommand("5", example5().toString(), controller5));
        }
        catch (StatementException e) {
            System.out.println(e.toString());
        }

        try{
            example6().typecheck(typeEnv);
            Repository repository6 = new Repository(example6(), "log6.txt");
            Controller controller6 = new Controller(repository6);
            menu.addCommand(new RunCommand("6", example6().toString(), controller6));
        }
        catch (StatementException e) {
            System.out.println(e.toString());
        }

        try{
            example7().typecheck(typeEnv);
            Repository repository7 = new Repository(example7(), "log7.txt");
            Controller controller7 = new Controller(repository7);
            menu.addCommand(new RunCommand("7", example7().toString(), controller7));
        }
        catch (StatementException e) {
            System.out.println(e.toString());
        }

        try{
            example8().typecheck(typeEnv);
            Repository repository8 = new Repository(example8(), "log8.txt");
            Controller controller8 = new Controller(repository8);
            menu.addCommand(new RunCommand("8", example8().toString(), controller8));
        }
        catch (StatementException e) {
            System.out.println(e.toString());
        }

        try{
            example9().typecheck(typeEnv);
            Repository repository9 = new Repository(example9(), "log9.txt");
            Controller controller9 = new Controller(repository9);
            menu.addCommand(new RunCommand("9", example9().toString(), controller9));
        }
        catch (StatementException e) {
            System.out.println(e.toString());
        }

        try{
            example10().typecheck(typeEnv);
            Repository repository10 = new Repository(example10(), "log10.txt");
            Controller controller10 = new Controller(repository10);
            menu.addCommand(new RunCommand("10", example10().toString(), controller10));
        }
        catch (StatementException e) {
            System.out.println(e.toString());
        }

        try{
            example11().typecheck(typeEnv);
            Repository repository11 = new Repository(example11(), "log11.txt");
            Controller controller11 = new Controller(repository11);
            menu.addCommand(new RunCommand("11", example11().toString(), controller11));
        }
        catch (StatementException e) {
            System.out.println(e.toString());
        }

        menu.show();
    }
}
