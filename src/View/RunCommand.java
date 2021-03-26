package View;

import Controller.Controller;

public class RunCommand extends Command {
    private Controller controller;

    public RunCommand(String key, String desc, Controller controller) {
        super(key, desc);
        this.controller = controller;
    }

    @Override
    public void execute() {
        try {
            this.controller.allStep();
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
