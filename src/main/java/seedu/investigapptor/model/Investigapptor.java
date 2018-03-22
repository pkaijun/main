package seedu.investigapptor.model;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.investigapptor.model.crimecase.CrimeCase;
import seedu.investigapptor.model.crimecase.UniqueCrimeCaseList;
import seedu.investigapptor.model.crimecase.exceptions.DuplicateCrimeCaseException;
import seedu.investigapptor.model.person.Person;
import seedu.investigapptor.model.person.UniquePersonList;
import seedu.investigapptor.model.person.exceptions.DuplicatePersonException;
import seedu.investigapptor.model.person.exceptions.PersonNotFoundException;
import seedu.investigapptor.model.tag.Tag;
import seedu.investigapptor.model.tag.UniqueTagList;
import seedu.investigapptor.model.tag.exceptions.TagNotFoundException;

/**
 * Wraps all data at the investigapptor-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class Investigapptor implements ReadOnlyInvestigapptor {

    private final UniquePersonList persons;
    private final UniqueCrimeCaseList cases;
    private final UniqueTagList tags;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        cases = new UniqueCrimeCaseList();
        tags = new UniqueTagList();
    }

    public Investigapptor() {}

    /**
     * Creates an Investigapptor using the Persons and Tags in the {@code toBeCopied}
     */
    public Investigapptor(ReadOnlyInvestigapptor toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    public void setPersons(List<Person> persons) throws DuplicatePersonException {
        this.persons.setPersons(persons);
    }

    public void setCrimeCases(List<CrimeCase> cases) throws DuplicateCrimeCaseException {
        this.cases.setCrimeCases(cases);
    }

    public void setTags(Set<Tag> tags) {
        this.tags.setTags(tags);
    }

    /**
     * Resets the existing data of this {@code Investigapptor} with {@code newData}.
     */
    public void resetData(ReadOnlyInvestigapptor newData) {
        requireNonNull(newData);
        setTags(new HashSet<>(newData.getTagList()));
        List<CrimeCase> syncedCrimeCaseList = newData.getCrimeCaseList().stream()
                .map(this::syncWithMasterTagList)
                .collect(Collectors.toList());
        List<Person> syncedPersonList = newData.getPersonList().stream()
                .map(this::syncWithMasterTagList)
                .collect(Collectors.toList());
        try {
            setPersons(syncedPersonList);
        } catch (DuplicatePersonException e) {
            throw new AssertionError("Investigapptors should not have duplicate persons");
        }
        try {
            setCrimeCases(syncedCrimeCaseList);
        } catch (DuplicateCrimeCaseException e) {
            throw new AssertionError("Investigapptors should not have duplicate cases");
        }

    }
    //// person-level operations

    /**
     * Adds a person to the investigapptor book.
     * Also checks the new person's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the person to point to those in {@link #tags}.
     *
     * @throws DuplicatePersonException if an equivalent person already exists.
     */
    public void addPerson(Person p) throws DuplicatePersonException {
        Person person = syncWithMasterTagList(p);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        persons.add(person);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code Investigapptor}'s tag list will be updated with the tags of {@code editedPerson}.
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *      another existing person in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     *
     * @see #syncWithMasterTagList(Person)
     */
    public void updatePerson(Person target, Person editedPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireNonNull(editedPerson);

        Person syncedEditedPerson = syncWithMasterTagList(editedPerson);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        persons.setPerson(target, syncedEditedPerson);
    }

    /**
     * Removes {@code key} from this {@code Investigapptor}.
     * @throws PersonNotFoundException if the {@code key} is not in this {@code Investigapptor}.
     */
    public boolean removePerson(Person key) throws PersonNotFoundException {
        if (persons.remove(key)) {
            return true;
        } else {
            throw new PersonNotFoundException();
        }
    }

    //// case-level operations

    /**
     * Adds a case to the investigapptor book.
     * Also checks the new case's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the person to point to those in {@link #tags}.
     *
     * @throws DuplicateCrimeCaseException if an equivalent case already exists.
     */
    public void addCrimeCase(CrimeCase c) throws DuplicateCrimeCaseException {
        CrimeCase crimecase = syncWithMasterTagList(c);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any case
        // in the case list.
        cases.add(crimecase);
    }

    //// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

    /**
     * Deletes {@code Investigapptor} from all person and UniqueTagList
     */
    public void deleteTag(Tag toDelete) throws TagNotFoundException {
        if (tags.contains(toDelete)) {
            tags.delete(toDelete);
            persons.deleteTagFromPersons(toDelete);
            cases.deleteTagFromCrimeCases(toDelete);
        } else {
            throw new TagNotFoundException();
        }
    }

    /**
     *  Updates the master tag list to include tags in {@code person} that are not in the list.
     *  @return a copy of this {@code person} such that every tag in this person points to a Tag object in the master
     *  list.
     */
    private Person syncWithMasterTagList(Person person) {
        final UniqueTagList personTags = new UniqueTagList(person.getTags());
        tags.mergeFrom(personTags);

        // Create map with values = tag object references in the master list
        // used for checking person tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        tags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of person tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        personTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));
        return new Person(
                person.getName(), person.getPhone(), person.getEmail(), person.getAddress(), correctTagReferences);
    }

    /**
     *  Updates the master tag list to include tags in {@code crimecase} that are not in the list.
     *  @return a copy of this {@code crimecase} such that every tag in this case points to a Tag object in the master
     *  list.
     */
    private CrimeCase syncWithMasterTagList(CrimeCase crimecase) {
        final UniqueTagList crimecaseTags = new UniqueTagList(crimecase.getTags());
        tags.mergeFrom(crimecaseTags);

        // Create map with values = tag object references in the master list
        // used for checking case tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        tags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of case tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        crimecaseTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));
        return new CrimeCase(
                crimecase.getCaseName(), crimecase.getDescription(), crimecase.getCurrentInvestigator(),
                crimecase.getStartDate(), crimecase.getStatus(), correctTagReferences);
    }
    //// util methods

    @Override
    public String toString() {
        return persons.asObservableList().size() + " persons, " + tags.asObservableList().size() +  " tags";
        // TODO: refine later
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asObservableList();
    }

    @Override
    public ObservableList<CrimeCase> getCrimeCaseList() {
        return cases.asObservableList();
    }

    @Override
    public ObservableList<Tag> getTagList() {
        return tags.asObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Investigapptor // instanceof handles nulls
                && this.persons.equals(((Investigapptor) other).persons)
                && this.tags.equalsOrderInsensitive(((Investigapptor) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(persons, tags);
    }
}
