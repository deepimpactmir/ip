package duke;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import duke.command.ByeCommand;
import duke.command.Command;
import duke.command.DeadlineCommand;
import duke.command.DeleteCommand;
import duke.command.EventCommand;
import duke.command.FindCommand;
import duke.command.HelpCommand;
import duke.command.ListCommand;
import duke.command.MarkCommand;
import duke.command.SortCommand;
import duke.command.TodoCommand;
import duke.command.UnmarkCommand;
import duke.ui.Ui;

/**
 * Represents an abstraction that handles inputs from the user.
 *
 * Inspired by addressBook-level2
 */
public class Parser {
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<argument>.*)");
    private Storage storage;

    /**
     * Initialises the parser.
     *
     * @param storage Storage
     */
    public Parser(Storage storage) {
        this.storage = storage;
    }

    private Command prepareTodo(String argument) {
        Pattern pattern = Pattern.compile(TodoCommand.REGEX);
        Matcher matcher = pattern.matcher(argument);
        if (!matcher.matches()) {
            Ui.printMessage("The description of a todo cannot be empty.");
            return new HelpCommand();
        }
        return new TodoCommand(argument);
    }

    private Command prepareDeadline(String argument) {
        Pattern pattern = Pattern.compile(DeadlineCommand.REGEX);
        Matcher matcher = pattern.matcher(argument);
        if (!matcher.matches()) {
            Ui.printMessage("The description or datetime cannot be empty");
            return new HelpCommand();
        }
        String description = matcher.group("description").strip();
        String dateTime = matcher.group("dateTime").strip();
        return new DeadlineCommand(description, dateTime);
    }

    private Command prepareEvent(String argument) {
        Pattern pattern = Pattern.compile(EventCommand.REGEX);
        Matcher matcher = pattern.matcher(argument);
        if (!matcher.matches()) {
            Ui.printMessage("The description or datetime cannot be empty");
            return new HelpCommand();
        }
        String description = matcher.group("description").strip();
        String dateTime = matcher.group("dateTime").strip();
        return new EventCommand(description, dateTime);
    }

    private Command prepareFind(String argument) {
        Pattern pattern = Pattern.compile(FindCommand.REGEX);
        Matcher matcher = pattern.matcher(argument);
        if (!matcher.matches()) {
            Ui.printMessage("The description of find cannot be empty.");
            return new HelpCommand();
        }
        return new FindCommand(argument);
    }

    /**
     * Parses the input from the user
     *
     * @param input Input from the user
     * @return command Command from the parsed input
     */
    public Command parse(String input) {
        Matcher matcher = Parser.BASIC_COMMAND_FORMAT.matcher(input);
        if (!matcher.matches()) {
            Ui.printMessage("No matches");
            return new HelpCommand();
        }
        String command = matcher.group("commandWord").strip();
        String argument = matcher.group("argument").strip();
        switch (command) {

        case ListCommand.COMMAND_WORD: {
            return new ListCommand();
        }
        case MarkCommand.COMMAND_WORD: {
            try {
                int index = Integer.parseInt(argument) - 1;
                return new MarkCommand(index);
            } catch (NumberFormatException e) {
                Ui.printMessage("Error!", "Please use the format: mark <integer>");
            }
            return new HelpCommand();
        }
        case UnmarkCommand.COMMAND_WORD: {
            try {
                int index = Integer.parseInt(argument) - 1;
                return new UnmarkCommand(index);
            } catch (NumberFormatException e) {
                Ui.printMessage("Error!", "Please use the format: unmark <integer>");
            }
            return new HelpCommand();
        }
        case TodoCommand.COMMAND_WORD: {
            return prepareTodo(argument);
        }
        case DeadlineCommand.COMMAND_WORD: {
            return prepareDeadline(argument);
        }
        case EventCommand.COMMAND_WORD: {
            return prepareEvent(argument);
        }
        case DeleteCommand.COMMAND_WORD: {
            try {
                int index = Integer.parseInt(argument) - 1;
                return new DeleteCommand(index);
            } catch (NumberFormatException e) {
                Ui.printMessage("Error!", "Please use the format: delete <integer>");
            }
            return new HelpCommand();
        }
        case FindCommand.COMMAND_WORD: {
            return prepareFind(argument);
        }
        case SortCommand.COMMAND_WORD: {
            return new SortCommand();
        }
        case ByeCommand.COMMAND_WORD: {
            return new ByeCommand(storage);
        }
        default:
            return new HelpCommand(argument);
        }
    }
}
