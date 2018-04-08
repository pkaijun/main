package seedu.investigapptor.testutil;

import static seedu.investigapptor.logic.commands.CommandTestUtil.VALID_CASENAME_APPLE;
import static seedu.investigapptor.logic.commands.CommandTestUtil.VALID_CASENAME_BANANA;
import static seedu.investigapptor.logic.commands.CommandTestUtil.VALID_DESCRIPTION_APPLE;
import static seedu.investigapptor.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BANANA;
import static seedu.investigapptor.logic.commands.CommandTestUtil.VALID_INVESTIGATOR_APPLE;
import static seedu.investigapptor.logic.commands.CommandTestUtil.VALID_INVESTIGATOR_BANANA;
import static seedu.investigapptor.logic.commands.CommandTestUtil.VALID_STARTDATE_APPLE;
import static seedu.investigapptor.logic.commands.CommandTestUtil.VALID_STARTDATE_BANANA;
import static seedu.investigapptor.logic.commands.CommandTestUtil.VALID_TAG_FRAUD;
import static seedu.investigapptor.logic.commands.CommandTestUtil.VALID_TAG_MURDER;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.investigapptor.model.Investigapptor;
import seedu.investigapptor.model.crimecase.CrimeCase;
import seedu.investigapptor.model.crimecase.exceptions.DuplicateCrimeCaseException;

/**
 * A utility class containing a list of {@code CrimeCase} objects to be used in tests.
 */
public class TypicalCrimeCases {

    public static final CrimeCase ALFA = new CrimeCaseBuilder().withName("Project Alfa")
            .withDescription("Murder on the orient express").withStartDate("10/11/2015")
            .withEndDate().toggleStatus()
            .withTags("Murder").build();
    public static final CrimeCase BRAVO = new CrimeCaseBuilder().withName("Project Bravo Johnny")
            .withDescription("Crooked house")
            .withStartDate("12/03/2016").withEndDate()
            .withTags("Kidnap", "Homicide").build();
    public static final CrimeCase CHARLIE = new CrimeCaseBuilder().withName("Project Charlie").toggleStatus()
            .withStartDate("18/01/2012").withEndDate()
            .withDescription("ABC murders").build();
    public static final CrimeCase DELTA = new CrimeCaseBuilder().withName("Project Delta Johnny")
            .withStartDate("27/11/1999").withEndDate()
            .withDescription("Peril at End House").build();
    public static final CrimeCase ECHO = new CrimeCaseBuilder().withName("Project Echo")
            .withStartDate("23/11/1965").withEndDate()
            .withDescription("A study in scarlet").build();
    public static final CrimeCase FOXTROT = new CrimeCaseBuilder().withName("Project Foxtrot").toggleStatus()
            .withStartDate("09/07/2017").withEndDate()
            .withDescription("The sign of the four").build();
    public static final CrimeCase GOLF = new CrimeCaseBuilder().withName("Project Golf")
            .withStartDate("02/08/2017").withEndDate()
            .withDescription("The hound of the baskervilles").build();

    // For findCaseTags testing
    public static final CrimeCase ONE = new CrimeCaseBuilder().withName("Project One")
            .withDescription("Scary case").withStartDate("10/12/2016")
            .toggleStatus().withEndDate()
            .withTags("Murder", "Homicide", "Missing").build();
    public static final CrimeCase TWO = new CrimeCaseBuilder().withName("Project Two")
            .withDescription("Not so scary case").withStartDate("14/12/2016")
            .toggleStatus().withEndDate()
            .withTags("Robbery", "Prank", "Kidnap").build();
    public static final CrimeCase THREE = new CrimeCaseBuilder().withName("Project Three")
            .withDescription("Supernatural Case").withStartDate("18/12/2016")
            .toggleStatus().withEndDate()
            .withTags("Murder", "Supernatural").build();
    public static final CrimeCase FOUR = new CrimeCaseBuilder().withName("Project Four")
            .withDescription("Small case").withStartDate("11/12/2016")
            .toggleStatus().withEndDate()
            .withTags("Theft").build();

    // Manually added
    public static final CrimeCase BLUE = new CrimeCaseBuilder().withName("Project Blue").toggleStatus()
            .withStartDate("15/06/2014").withEndDate().withDescription("Reichenbach fall").build();
    public static final CrimeCase YELLOW = new CrimeCaseBuilder().withName("Project Yellow")
            .withStartDate("19/12/2012").withEndDate().withDescription("Scandal in Belgravia").build();

    // Manually added - CrimeCase's details found in {@code CommandTestUtil}
    public static final CrimeCase APPLE = new CrimeCaseBuilder().withName(VALID_CASENAME_APPLE)
            .withDescription(VALID_DESCRIPTION_APPLE).withInvestigator(VALID_INVESTIGATOR_APPLE)
            .withStartDate(VALID_STARTDATE_APPLE).withTags(VALID_TAG_FRAUD).build();
    public static final CrimeCase BANANA = new CrimeCaseBuilder().withName(VALID_CASENAME_BANANA)
            .withDescription(VALID_DESCRIPTION_BANANA).withInvestigator(VALID_INVESTIGATOR_BANANA)
            .withStartDate(VALID_STARTDATE_BANANA).toggleStatus()
            .withTags(VALID_TAG_MURDER, VALID_TAG_FRAUD)
            .build();

    public static final String KEYWORD_MATCHING_HOMICIDE = "homicide"; // A keyword that matches homicide
    public static final String KEYWORD_MATCHING_MURDER = "MURDER"; // A keyword that matches murder
    public static final String KEYWORD_MATCHING_JOHNNY = "Johnny"; // A keyword that matches JOHNNY

    private TypicalCrimeCases() {} // prevents instantiation

    /**
     * Returns an {@code Investigapptor} with all the typical cases.
     */
    public static Investigapptor getTypicalInvestigapptor() {
        Investigapptor ia = new Investigapptor();
        for (CrimeCase crimeCase : getTypicalCrimeCases()) {
            try {
                ia.addCrimeCase(crimeCase);
            } catch (DuplicateCrimeCaseException e) {
                throw new AssertionError("not possible");
            }
        }
        return ia;
    }

    public static List<CrimeCase> getTypicalCrimeCases() {
        CrimeCase alfa = new CrimeCaseBuilder().withName("Project Alfa")
                .withDescription("Murder on the orient express").withStartDate("10/11/2015")
                .withEndDate().toggleStatus()
                .withTags("Murder").build();
        CrimeCase bravo = new CrimeCaseBuilder().withName("Project Bravo Johnny")
                .withDescription("Crooked house")
                .withStartDate("12/03/2016").withEndDate()
                .withTags("Kidnap", "Homicide").build();
        CrimeCase charlie = new CrimeCaseBuilder().withName("Project Charlie").toggleStatus()
                .withStartDate("18/01/2012").withEndDate()
                .withDescription("ABC murders").build();
        CrimeCase delta = new CrimeCaseBuilder().withName("Project Delta Johnny")
                .withStartDate("27/11/1999").withEndDate()
                .withDescription("Peril at End House").build();
        CrimeCase echo = new CrimeCaseBuilder().withName("Project Echo")
                .withStartDate("23/11/1965").withEndDate()
                .withDescription("A study in scarlet").build();
        CrimeCase foxtrot = new CrimeCaseBuilder().withName("Project Foxtrot").toggleStatus()
                .withStartDate("09/07/2017").withEndDate()
                .withDescription("The sign of the four").build();
        CrimeCase golf = new CrimeCaseBuilder().withName("Project Golf")
                .withStartDate("02/08/2017").withEndDate()
                .withDescription("The hound of the baskervilles").build();
        CrimeCase one = new CrimeCaseBuilder().withName("Project One")
                .withDescription("Scary case").withStartDate("10/12/2016")
                .withEndDate().toggleStatus()
                .withTags("Murder", "Homicide", "Missing").build();
        CrimeCase two = new CrimeCaseBuilder().withName("Project Two")
                .withDescription("Not so scary case").withStartDate("14/12/2016")
                .withEndDate().toggleStatus()
                .withTags("Robbery", "Prank", "Kidnap").build();
        CrimeCase three = new CrimeCaseBuilder().withName("Project Three")
                .withDescription("Supernatural Case").withStartDate("18/12/2016")
                .withEndDate().toggleStatus()
                .withTags("Murder", "Supernatural").build();
        CrimeCase four = new CrimeCaseBuilder().withName("Project Four")
                .withDescription("Small case").withStartDate("11/12/2016")
                .withEndDate().toggleStatus()
                .withTags("Theft").build();
        return new ArrayList<>(Arrays.asList(alfa, bravo, charlie, delta, echo, foxtrot, golf,
                one, two, three, four));
    }
}
