//@@author pkaijun
package seedu.investigapptor.logic.commands;

import java.util.List;

import seedu.investigapptor.commons.core.EventsCenter;
import seedu.investigapptor.commons.core.Messages;
import seedu.investigapptor.commons.core.index.Index;
import seedu.investigapptor.commons.events.ui.JumpToCrimeCaseListRequestEvent;
import seedu.investigapptor.logic.commands.exceptions.CommandException;
import seedu.investigapptor.model.crimecase.CrimeCase;

/**
 * Exports the case details that was selected
 */
public class ExportCaseDetailsCommand extends Command {

    public static final String COMMAND_WORD = "exportcase";
    public static final String COMMAND_ALIAS = "ec";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Exports the case details identified by the index number used in the last listing of cases.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_EXPORT_CASE_SUCCESS = "Case details exported!";

    private final Index targetIndex;

    public ExportCaseDetailsCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<CrimeCase> lastShownList = model.getFilteredCrimeCaseList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CASE_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new JumpToCrimeCaseListRequestEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_EXPORT_CASE_SUCCESS, targetIndex.getOneBased()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExportCaseDetailsCommand // instanceof handles nulls
                && this.targetIndex.equals(((ExportCaseDetailsCommand) other).targetIndex)); // state check
    }
}
