package seedu.investigapptor.commons.events.ui;

import seedu.investigapptor.commons.events.BaseEvent;
import seedu.investigapptor.ui.CrimeCaseCard;

/**
 * Represents a selection change in the Crime Case List Panel
 */
public class CrimeCasePanelSelectionChangedEvent extends BaseEvent {


    private final CrimeCaseCard newSelection;

    public CrimeCasePanelSelectionChangedEvent(CrimeCaseCard newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public CrimeCaseCard getNewSelection() {
        return newSelection;
    }
}
