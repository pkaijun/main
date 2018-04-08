package seedu.investigapptor.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.investigapptor.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.investigapptor.testutil.TypicalPersons.MDM_ONG;
import static seedu.investigapptor.testutil.TypicalPersons.SIR_CHONG;
import static seedu.investigapptor.testutil.TypicalPersons.SIR_LIM;
import static seedu.investigapptor.testutil.TypicalPersons.getTypicalInvestigapptor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.investigapptor.logic.CommandHistory;
import seedu.investigapptor.logic.UndoRedoStack;
import seedu.investigapptor.model.Investigapptor;
import seedu.investigapptor.model.Model;
import seedu.investigapptor.model.ModelManager;
import seedu.investigapptor.model.UserPrefs;
import seedu.investigapptor.model.person.Person;
import seedu.investigapptor.model.person.investigator.TagContainsKeywordsPredicate;

//@@author pkaijun
/**
 * Contains integration tests (interaction with the Model) for {@code FindInvestTagsCommand}.
 */
public class FindInvestTagsCommandTest {
    private Model model = new ModelManager(getTypicalInvestigapptor(), new UserPrefs());

    @Test
    public void equals() {
        TagContainsKeywordsPredicate firstPredicate =
                new TagContainsKeywordsPredicate(Collections.singletonList("first"));
        TagContainsKeywordsPredicate secondPredicate =
                new TagContainsKeywordsPredicate(Collections.singletonList("second"));

        FindInvestTagsCommand findFirstCommand = new FindInvestTagsCommand(firstPredicate);
        FindInvestTagsCommand findSecondCommand = new FindInvestTagsCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindInvestTagsCommand findFirstCommandCopy = new FindInvestTagsCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        FindInvestTagsCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        String userInput = "teamB new".toLowerCase();  // Tags are converted to lowercase during comparison
        FindInvestTagsCommand command = prepareCommand(userInput);
        assertCommandSuccess(command, expectedMessage, Arrays.asList(SIR_LIM, MDM_ONG, SIR_CHONG));
    }

    /**
     * Parses {@code userInput} into a {@code FindInvestTagsCommand}.
     */
    private FindInvestTagsCommand prepareCommand(String userInput) {
        FindInvestTagsCommand command =
                new FindInvestTagsCommand(new TagContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<Person>} is equal to {@code expectedList}<br>
     *     - the {@code Investigapptor} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindInvestTagsCommand command, String expectedMessage,
                                      List<Person> expectedList) {
        Investigapptor expectedInvestigapptor = new Investigapptor(model.getInvestigapptor());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedInvestigapptor, model.getInvestigapptor());
    }
}
