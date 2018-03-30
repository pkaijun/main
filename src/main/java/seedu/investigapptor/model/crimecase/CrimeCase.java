package seedu.investigapptor.model.crimecase;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.investigapptor.model.person.investigator.Investigator;
import seedu.investigapptor.model.tag.Tag;
import seedu.investigapptor.model.tag.UniqueTagList;

/**
 * Represents a Crime Case in the Investigapptor.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class CrimeCase {

    private final CaseName name;
    private final Description description;
    private final Date startDate;
    private final Investigator currentInvestigator;
    private final Status status;
    private final Date endDate;

    private final UniqueTagList tags;

    /**
     * Every field must be present and not null
     */
    public CrimeCase(CaseName name, Description description, Investigator currentInvestigator,
                     Date startDate, Status status, Set<Tag> tags) {
        this.name = name;
        this.description = description;
        this.currentInvestigator = currentInvestigator;
        this.startDate = startDate;
        this.endDate = null;    // Default value is null
        this.status = status;
        this.tags = new UniqueTagList(tags);
    }

    public CaseName getCaseName() {
        return name;
    }

    public Description getDescription() {
        return description;
    }

    public Investigator getCurrentInvestigator() {
        return currentInvestigator;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Status getStatus() {
        return status;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags.toSet());
    }

    /**
     * Returns an immutable tag set of type String
     */
    public Set<String> getTagsRaw() {
        Set<String> rawTags = new HashSet<>();
        for (Tag s : tags) {
            rawTags.add(s.getRawString().toLowerCase());
        }

        return rawTags;
    }

    /**
     * Deletes (@code toDelete) tag
     */
    public void deleteTag(Tag toDelete) {
        tags.delete(toDelete);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CrimeCase)) {
            return false;
        }

        CrimeCase otherCrimeCase = (CrimeCase) other;
        return otherCrimeCase.getCaseName().equals(this.getCaseName())
                && otherCrimeCase.getDescription().equals(this.getDescription())
                && otherCrimeCase.getCurrentInvestigator().equals(this.getCurrentInvestigator())
                && otherCrimeCase.getStartDate().equals(this.getStartDate());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, startDate, status, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getCaseName())
                .append(" Description: ")
                .append(getDescription())
                .append(" Current Investigator: ")
                .append(getCurrentInvestigator().getName())
                .append(" Start Date: ")
                .append(getStartDate())
                .append(" Status: ")
                .append(getStatus())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
