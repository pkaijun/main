package seedu.investigapptor.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.investigapptor.logic.commands.exceptions.InvalidPasswordException;
import seedu.investigapptor.model.crimecase.CrimeCase;
import seedu.investigapptor.model.crimecase.exceptions.CrimeCaseNotFoundException;
import seedu.investigapptor.model.crimecase.exceptions.DuplicateCrimeCaseException;
import seedu.investigapptor.model.person.Person;
import seedu.investigapptor.model.person.exceptions.DuplicatePersonException;
import seedu.investigapptor.model.person.exceptions.PersonNotFoundException;
import seedu.investigapptor.model.tag.Tag;
import seedu.investigapptor.model.tag.exceptions.TagNotFoundException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<CrimeCase> PREDICATE_SHOW_ALL_CASES = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyInvestigapptor newData);

    /** Returns the Investigapptor */
    ReadOnlyInvestigapptor getInvestigapptor();

    /** Deletes the given person. */
    void deletePerson(Person target) throws PersonNotFoundException;

    /** Adds the given person */
    void addPerson(Person person) throws DuplicatePersonException;
    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *      another existing person in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     */
    void updatePerson(Person target, Person editedPerson)
            throws DuplicatePersonException, PersonNotFoundException;

    /** Deletes the given case. */
    void deleteCrimeCase(CrimeCase target) throws CrimeCaseNotFoundException;
    /** Adds the given case */
    void addCrimeCase(CrimeCase crimecase) throws DuplicateCrimeCaseException;
    /**
     * Replaces the given case {@code target} with {@code editedCase}.
     *
     * @throws DuplicateCrimeCaseException if updating the crimecase's details causes the crimecase to be equivalent to
     *      another existing crimecase in the list.
     * @throws CrimeCaseNotFoundException if {@code target} could not be found in the list.
     */
    void updateCrimeCase(CrimeCase target, CrimeCase editedCrimeCase)
            throws DuplicateCrimeCaseException, CrimeCaseNotFoundException;

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /** Returns an unmodifiable view of the filtered investigator list */
    //ObservableList<Investigator> getFilteredInvestigatorList();

    /** Returns an unmodifiable view of the filtered case list */
    ObservableList<CrimeCase> getFilteredCrimeCaseList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Updates the filter of the filtered case list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredCrimeCaseList(Predicate<CrimeCase> predicate);

    /**
     * Deletes given tag from system
     */
    void deleteTag(Tag toDelete)throws TagNotFoundException;

    /** Save Investigapptor information as a new name */
    void backUpInvestigapptor(String fileName);

    //@@author quentinkhoo
    /**
     * Updates the password with the given password.
     */
    void updatePassword(Password password) throws InvalidPasswordException;

    /**
     * Removes the existing password
     */
    void removePassword();
    //@@author
}
