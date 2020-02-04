import command.Command;
import common.Storage;
import exception.DukeException;
import parser.Parser;
import task.TaskList;
import ui.TextUi;

/**
 * Control class of Duke, including the logic of Duke.
 */
public class Duke {

    private TextUi textUi;
    private Storage dukeStorage;
    private TaskList tasks;
    private boolean isFinished;

    /**
     * Initializes ui and storage.
     */
    public Duke() {
        textUi = new TextUi();
        dukeStorage = new Storage("tasks.txt");
        try {
            tasks = new TaskList(dukeStorage.readFromFile());
        } catch (exception.DukeException e) {
            textUi.showError(e.getMessage());
            tasks = new TaskList();
        }
    }

    /**
     * Returns a string of duke's respond base on the user input command.
     * @param text user input text.
     * @return duke's response
     */
    public String getResponse(String text) {
        try {
            Command c = Parser.parse(text.trim());
            boolean isExit = c.isExit();
            String commandResult = c.execute(tasks, textUi, dukeStorage);
            if(commandResult.equals("     It is time to say goodbye :(")){
                this.isFinished = true;
            }
            return commandResult;
        } catch (DukeException e) {
            return e.getMessage();
        }
    }

    /**
     * Takes in the user inputs and excutes each command.
     */
    public void run() {
        textUi.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = textUi.readCommand();
                Command c = Parser.parse(fullCommand);
                String commandResult = c.execute(tasks, textUi, dukeStorage);
                System.out.println(commandResult);
                isExit = c.isExit();
            } catch (DukeException e) {
                textUi.showError(e.getMessage());

            }
        }
        exit();
    }

    public static void main(String[] args) {
          new Duke().run();
      }

    /**
     * Ends the conversation and exits the system.
     */
    public void exit() {
        textUi.showGoodBye();
        System.exit(0);
    }

    public boolean isFinished() {
        return this.isFinished;
    }
}
