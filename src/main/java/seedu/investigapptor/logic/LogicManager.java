package seedu.investigapptor.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.investigapptor.commons.core.ComponentManager;
import seedu.investigapptor.commons.core.LogsCenter;
import seedu.investigapptor.logic.commands.Command;
import seedu.investigapptor.logic.commands.CommandResult;
import seedu.investigapptor.logic.commands.exceptions.CommandException;
import seedu.investigapptor.logic.parser.InvestigapptorParser;
import seedu.investigapptor.logic.parser.exceptions.ParseException;
import seedu.investigapptor.model.Model;
import seedu.investigapptor.model.crimecase.CrimeCase;
import seedu.investigapptor.model.person.Person;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final CommandHistory history;
    private final InvestigapptorParser investigapptorParser;
    private final UndoRedoStack undoRedoStack;

    public LogicManager(Model model) {
        this.model = model;
        history = new CommandHistory();
        investigapptorParser = new InvestigapptorParser();
        undoRedoStack = new UndoRedoStack();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        try {
            Command command = investigapptorParser.parseCommand(commandText);
            command.setData(model, history, undoRedoStack);
            CommandResult result = command.execute();
            undoRedoStack.push(command);
            return result;
        } finally {
            history.add(commandText);
        }
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public ObservableList<CrimeCase> getFilteredCrimeCaseList() {
        return model.getFilteredCrimeCaseList();
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }
}
