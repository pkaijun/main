package seedu.investigapptor.commons.events.model;

import seedu.investigapptor.commons.events.BaseEvent;
import seedu.investigapptor.model.ReadOnlyInvestigapptor;

/** Indicates the Investigapptor in the model has changed*/
public class InvestigapptorChangedEvent extends BaseEvent {

    public final ReadOnlyInvestigapptor data;

    public InvestigapptorChangedEvent(ReadOnlyInvestigapptor data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of cases " + data.getCrimeCaseList().size()
                + ", number of persons " + data.getPersonList().size()
                + ", number of tags " + data.getTagList().size();
    }
}
