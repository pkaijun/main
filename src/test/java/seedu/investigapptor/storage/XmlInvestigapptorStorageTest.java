package seedu.investigapptor.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.investigapptor.testutil.TypicalPersons.ALICE;
import static seedu.investigapptor.testutil.TypicalPersons.HOON;
import static seedu.investigapptor.testutil.TypicalPersons.IDA;
import static seedu.investigapptor.testutil.TypicalPersons.getTypicalInvestigapptor;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.investigapptor.commons.exceptions.DataConversionException;
import seedu.investigapptor.commons.util.FileUtil;
import seedu.investigapptor.model.Investigapptor;
import seedu.investigapptor.model.Password;
import seedu.investigapptor.model.ReadOnlyInvestigapptor;

public class XmlInvestigapptorStorageTest {
    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlInvestigapptorStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readInvestigapptor_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readInvestigapptor(null);
    }

    private java.util.Optional<ReadOnlyInvestigapptor> readInvestigapptor(String filePath) throws Exception {
        return new XmlInvestigapptorStorage(filePath).readInvestigapptor(addToTestDataPathIfNotNull(filePath));
    }

    //@@author quentinkhoo
    private void readInvestigapptorWithPassword(String filePath, Password password) throws Exception {
        new XmlInvestigapptorStorage(filePath)
                .checkInvestigapptorPassword(addToTestDataPathIfNotNull(filePath), password);
    }
    //@@author

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readInvestigapptor("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readInvestigapptor("NotXmlFormatInvestigapptor.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readInvestigapptor_invalidPersonInvestigapptor_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readInvestigapptor("invalidPersonInvestigapptor.xml");
    }

    @Test
    public void readInvestigapptor_invalidAndValidPersonInvestigapptor_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readInvestigapptor("invalidAndValidPersonInvestigapptor.xml");
    }

    //@@author quentinkhoo
    @Test
    public void readInvestigapptorWithPassword_invalidAndValidPersonInvestigapptor_throwDataConversionException()
            throws Exception {
        thrown.expect(DataConversionException.class);
        readInvestigapptorWithPassword("invalidPasswordInvestigapptor.xml",
                new Password("password"));
    }
    //@@author

    @Test
    public void readAndSaveInvestigapptor_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempInvestigapptor.xml";
        Investigapptor original = getTypicalInvestigapptor();
        XmlInvestigapptorStorage xmlInvestigapptorStorage = new XmlInvestigapptorStorage(filePath);

        //Save in new file and read back
        xmlInvestigapptorStorage.saveInvestigapptor(original, filePath);
        ReadOnlyInvestigapptor readBack = xmlInvestigapptorStorage.readInvestigapptor(filePath).get();
        assertEquals(original, new Investigapptor(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(ALICE);
        xmlInvestigapptorStorage.saveInvestigapptor(original, filePath);
        readBack = xmlInvestigapptorStorage.readInvestigapptor(filePath).get();
        assertEquals(original, new Investigapptor(readBack));

        //Save and read without specifying file path
        original.addPerson(IDA);
        xmlInvestigapptorStorage.saveInvestigapptor(original); //file path not specified
        readBack = xmlInvestigapptorStorage.readInvestigapptor().get(); //file path not specified
        assertEquals(original, new Investigapptor(readBack));

    }

    @Test
    public void saveInvestigapptor_nullInvestigapptor_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveInvestigapptor(null, "SomeFile.xml");
    }

    /**
     * Saves {@code investigapptor} at the specified {@code filePath}.
     */
    private void saveInvestigapptor(ReadOnlyInvestigapptor investigapptor, String filePath) {
        try {
            new XmlInvestigapptorStorage(filePath).saveInvestigapptor(investigapptor,
                                                                        addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveInvestigapptor_nullFilePath_throwsNullPointerException() throws IOException {
        thrown.expect(NullPointerException.class);
        saveInvestigapptor(new Investigapptor(), null);
    }


}
