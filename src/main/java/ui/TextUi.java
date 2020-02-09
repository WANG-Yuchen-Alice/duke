package ui;

import exception.DukeException;

import common.Message;
import common.Storage;
import task.Task;
import task.TaskList;

import java.io.PrintStream;
import java.util.Scanner;

public class TextUi {

    private Scanner sc;
    private PrintStream out;

    /**
     * Constructs new TextUi object to handle user-system interactions.
     */
    public TextUi() {
        this.sc = new Scanner(System.in);
        this.out = new PrintStream(System.out);
    }

    /**
     * Shows (lines of) message(s) to the user.
     *
     * @param message the message that is to be shown to the user
     */
    public void showToUser(String... message) {
        for (String m : message) {
            out.println(m);
        }
    }


    /**
     * Shows welcome words to the user during the initialization.
     */
    public void showWelcome() {
        showToUser(
                "Hello from",
                Message.MESSAGE_LOGO,
                Message.MESSAGE_LINE,
                "     Hello! I'm Duke",
                "     What can I do for you?",
                Message.MESSAGE_LINE
        );
    }

    /**
     * Shows goodBye words to the user.
     */
    public void showGoodBye() {
        showToUser(
                Message.MESSAGE_LINE,
                "     Bye. Hope to see you again soon!",
                Message.MESSAGE_LINE
        );
    }

    /**
     * Returns messages after adding a task to the task list.
     *
     * @param givenTask the newly added task object
     * @param tasks the current task list
     */
    public String showAddingTask_Str(Task givenTask, TaskList tasks){
        StringBuilder sb = new StringBuilder();
        sb.append(Message.MESSAGE_LINE + "\n");
        sb.append(Message.MESSAGE_GOTIT + "\n");
        sb.append("     " + givenTask.toString() + "\n");
        sb.append("     Now you have " + tasks.getList().size() + " tasks in the list." + "\n");
        sb.append(Message.MESSAGE_LINE);
        return sb.toString();
    }

    /**
     * Returns the deleted task message to the user.
     *
     * @param index the index of the deleted task in the task list
     * @param tasks the task list
     */
    public String showDeletingTask_Str(int index, TaskList tasks) {
        StringBuilder sb = new StringBuilder();
        sb.append(Message.MESSAGE_LINE + "\n");
        sb.append(Message.MESSAGE_DELETEIT + "\n");
        sb.append("     " + tasks.getList().get(index).toString() + "\n");
        sb.append("     Now you have " + (tasks.getList().size() - 1) + " tasks in the list." + "\n");
        sb.append(Message.MESSAGE_LINE);
        return sb.toString();
    }

    /**
     * Returns the done task message to the user.
     *
     * @param index the index of the done task in the task list
     * @param tasks the task list
     */
    public String showDoneTask_Str(int index, TaskList tasks) {
        StringBuilder sb = new StringBuilder();
        sb.append(Message.MESSAGE_LINE + "\n");
        sb.append(Message.MESSAGE_MARKASDONE + "\n");
        sb.append("     " + tasks.getList().get(index).toString() + "\n");
        sb.append(Message.MESSAGE_LINE);
        return sb.toString();
    }

    /**
     * Shows the error message to the user.
     *
     * @param errorMessage the error message generated by the dukeException
     */
    public void showError(String errorMessage) {
        showToUser(
                new DukeException(errorMessage).toString()
        );

    }

    /**
     * Returns the error message to the user.
     *
     * @param errorMessage the error message generated by the dukeException
     */
    public String showError_Str(String errorMessage) {
        return new DukeException(errorMessage).toString();
    }

    /**
     * Takes in the command from the user.
     *
     * @return the command of the user
     */
    public String readCommand() {
        String input = sc.nextLine().trim();
        while (input.equals("")) {
            input = sc.nextLine();
        }
        return input;
    }

    /**
     * Returns the string of task list to to the user.
     *
     * @param tasks the task list
     * @param dukeStorage the storage
     */
    public String displayList_Str(TaskList tasks, Storage dukeStorage) {
        StringBuilder sb = new StringBuilder();
        sb.append(Message.MESSAGE_LINE + "\n");
        sb.append(Message.MESSAGE_SHOWLIST + "\n");
        try {
            tasks.renewList(dukeStorage.readFromFile());
        } catch (Exception exp) {
            sb.append("     Something went wrong with the file." + "\n" + "     Better say 'bye' now.");
            sb.append(Message.MESSAGE_LINE);
            return sb.toString();
        }
        if (tasks.getList().isEmpty()) {
            sb.append(Message.MESSAGE_EMPTYLIST + "\n");
            sb.append(Message.MESSAGE_LINE);
            return sb.toString();
        } else {
            for (int i = 0; i < tasks.getList().size(); i++) {
                sb.append("     " + (i + 1) + ". " + tasks.getList().get(i).toString() + "\n");
            }
            sb.append(Message.MESSAGE_LINE);
        }
        return sb.toString();
    }

    /**
     * Returns the string of tasks in the task list which contain the keyword.
     *
     * @param tasks the task list
     * @param item the keyword of the user-targeted tasks
     */
    public String findList_Str(TaskList tasks, String item) {
        StringBuilder sb = new StringBuilder();
        sb.append(Message.MESSAGE_LINE + "\n");
        sb.append(Message.MESSAGE_FIND + "\n");
        int marker = 1;
        String thisItem;
        for (int i = 0; i < tasks.getList().size(); i++) {
            thisItem = tasks.getList().get(i).toString();
            if (thisItem.toLowerCase().contains(item.toLowerCase())) {
                sb.append("     ").append(marker).append(". ").append(thisItem).append("\n");
                marker++;
            }
        }
        if (marker == 1) {
            sb.append(Message.MESSAGE_NULL + "\n");
        }
        sb.append(Message.MESSAGE_LINE);
        return sb.toString();
    }
}
