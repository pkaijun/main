package seedu.investigapptor.logic.commands;

import static seedu.investigapptor.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.investigapptor.model.crimecase.Status.CASE_CLOSE;
import static seedu.investigapptor.testutil.TypicalCrimeCases.getTypicalInvestigapptor;
import static seedu.investigapptor.testutil.TypicalIndexes.INDEX_FIRST_CASE;
import static seedu.investigapptor.testutil.TypicalIndexes.INDEX_SECOND_CASE;

import org.junit.Test;

import seedu.investigapptor.commons.core.index.Index;
import seedu.investigapptor.logic.CommandHistory;
import seedu.investigapptor.logic.UndoRedoStack;
import seedu.investigapptor.model.Investigapptor;
import seedu.investigapptor.model.Model;
import seedu.investigapptor.model.ModelManager;
import seedu.investigapptor.model.UserPrefs;
import seedu.investigapptor.model.crimecase.CrimeCase;
import seedu.investigapptor.testutil.CrimeCaseBuilder;

//@@author pkaijun
/**
 * Testing
 */
public class CloseCaseCommandTest {

    private Model model = new ModelManager(getTypicalInvestigapptor(), new UserPrefs());

    @Test
    public void execute_closecase_success() throws Exception {
        CloseCaseCommand closeCaseCommand = prepareCommand(INDEX_FIRST_CASE);
        CrimeCase closedCase = model.getFilteredCrimeCaseList().get(0);
        closedCase.getStatus().closeCase();

        String expectedMessage = String.format(CloseCaseCommand.MESSAGE_CLOSE_CASE_SUCCESS, closedCase.getStatus());

        Model expectedModel = new ModelManager(new Investigapptor(model.getInvestigapptor()), new UserPrefs());
        expectedModel.updateCrimeCase(model.getFilteredCrimeCaseList().get(0), closedCase);

        assertCommandSuccess(closeCaseCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Returns an {@code CloseCaseCommand} with parameters {@code index}
     */
    private CloseCaseCommand prepareCommand(Index index) {
        CloseCaseCommand closeCaseCommand = new CloseCaseCommand(index);
        closeCaseCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return closeCaseCommand;
    }
}
