package seedu.investigapptor.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.investigapptor.model.Investigapptor;
import seedu.investigapptor.model.ReadOnlyInvestigapptor;
import seedu.investigapptor.model.person.Address;
import seedu.investigapptor.model.person.Email;
import seedu.investigapptor.model.person.Name;
import seedu.investigapptor.model.person.Person;
import seedu.investigapptor.model.person.Phone;
import seedu.investigapptor.model.person.exceptions.DuplicatePersonException;
import seedu.investigapptor.model.person.investigator.Investigator;
import seedu.investigapptor.model.person.investigator.Rank;
import seedu.investigapptor.model.tag.Tag;

/**
 * Contains utility methods for populating {@code Investigapptor} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Investigator(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"), new Rank("4"),
                getTagSet("friends")),
            new Investigator(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Rank("2"),
                getTagSet("colleagues", "friends")),
            new Investigator(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Rank("1"),
                getTagSet("neighbours")),
            new Investigator(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new Rank("3"),
                getTagSet("family")),
            new Investigator(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"), new Rank("5"),
                getTagSet("classmates")),
            new Investigator(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"), new Rank("3"),
                getTagSet("colleagues"))
        };
    }

    public static ReadOnlyInvestigapptor getSampleInvestigapptor() {
        try {
            Investigapptor sampleAb = new Investigapptor();

            for (Person samplePerson : getSamplePersons()) {
                sampleAb.addPerson(samplePerson);
            }
            return sampleAb;
        } catch (DuplicatePersonException e) {
            throw new AssertionError("sample data cannot contain duplicate persons", e);
        }
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        HashSet<Tag> tags = new HashSet<>();
        for (String s : strings) {
            tags.add(new Tag(s));
        }

        return tags;
    }

}
