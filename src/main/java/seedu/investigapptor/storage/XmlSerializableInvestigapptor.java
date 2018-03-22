package seedu.investigapptor.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.investigapptor.commons.exceptions.IllegalValueException;
import seedu.investigapptor.model.Investigapptor;
import seedu.investigapptor.model.ReadOnlyInvestigapptor;

/**
 * An Immutable Investigapptor that is serializable to XML format
 */
@XmlRootElement(name = "investigapptor")
public class XmlSerializableInvestigapptor {

    @XmlElement
    private List<XmlAdaptedCrimeCase> cases;
    @XmlElement
    private List<XmlAdaptedPerson> persons;
    @XmlElement
    private List<XmlAdaptedTag> tags;

    /**
     * Creates an empty XmlSerializableInvestigapptor.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableInvestigapptor() {
        cases = new ArrayList<>();
        persons = new ArrayList<>();
        tags = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableInvestigapptor(ReadOnlyInvestigapptor src) {
        this();
        cases.addAll(src.getCrimeCaseList().stream().map(XmlAdaptedCrimeCase::new).collect(Collectors.toList()));
        persons.addAll(src.getPersonList().stream().map(XmlAdaptedPerson::new).collect(Collectors.toList()));
        tags.addAll(src.getTagList().stream().map(XmlAdaptedTag::new).collect(Collectors.toList()));
    }

    /**
     * Converts this investigapptor into the model's {@code Investigapptor} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedCrimeCase}, {@code XmlAdaptedPerson} or {@code XmlAdaptedTag}.
     */
    public Investigapptor toModelType() throws IllegalValueException {
        Investigapptor investigapptor = new Investigapptor();
        for (XmlAdaptedTag t : tags) {
            investigapptor.addTag(t.toModelType());
        }
        for (XmlAdaptedCrimeCase c : cases) {
            investigapptor.addCrimeCase(c.toModelType());
        }
        for (XmlAdaptedPerson p : persons) {
            investigapptor.addPerson(p.toModelType());
        }
        return investigapptor;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableInvestigapptor)) {
            return false;
        }

        XmlSerializableInvestigapptor otherAb = (XmlSerializableInvestigapptor) other;
        return cases.equals(otherAb.cases) && persons.equals(otherAb.persons) && tags.equals(otherAb.tags);
    }
}
