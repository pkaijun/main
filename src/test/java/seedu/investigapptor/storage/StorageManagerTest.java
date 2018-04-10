package seedu.investigapptor.storage;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.investigapptor.testutil.TypicalPersons.getTypicalInvestigapptor;

import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.investigapptor.commons.events.model.InvestigapptorBackupEvent;
import seedu.investigapptor.commons.events.model.InvestigapptorChangedEvent;
import seedu.investigapptor.commons.events.storage.DataSavingExceptionEvent;
import seedu.investigapptor.commons.exceptions.WrongPasswordException;
import seedu.investigapptor.model.Investigapptor;
import seedu.investigapptor.model.Password;
import seedu.investigapptor.model.ReadOnlyInvestigapptor;
import seedu.investigapptor.model.UserPrefs;
import seedu.investigapptor.ui.testutil.EventsCollectorRule;

public class StorageManagerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private StorageManager storageManager;

    @Before
    public void setUp() {
        XmlInvestigapptorStorage investigapptorStorage = new XmlInvestigapptorStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(investigapptorStorage, userPrefsStorage);
    }

    private String getTempFilePath(String fileName) {
        return testFolder.getRoot().getPath() + fileName;
    }


    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(300, 600, 4, 6);
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void investigapptorReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlInvestigapptorStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlInvestigapptorStorageTest} class.
         */
        Investigapptor original = getTypicalInvestigapptor();
        storageManager.saveInvestigapptor(original);
        ReadOnlyInvestigapptor retrieved = storageManager.readInvestigapptor().get();
        assertEquals(original, new Investigapptor(retrieved));
    }

    //@@author quentinkhoo
    @Test
    public void investigapptorReadWithWrongPassword() throws Exception {
        thrown.expect(WrongPasswordException.class);
        Investigapptor original = getTypicalInvestigapptor();
        Password password = new Password("password");
        original.updatePassword(password);
        storageManager.saveInvestigapptor(original);
        Password wrongPassword = new Password("p@ssword");
        storageManager.readInvestigapptorWithPassword(wrongPassword);
    }

    //@@author

    @Test
    public void getInvestigapptorFilePath() {
        assertNotNull(storageManager.getInvestigapptorFilePath());
    }

    @Test
    public void handleInvestigapptorChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlInvestigapptorStorageExceptionThrowingStub("dummy"),
                                             new JsonUserPrefsStorage("dummy"));
        storage.handleInvestigapptorChangedEvent(new InvestigapptorChangedEvent(new Investigapptor()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }
    @Test
    public void handleInvestigapptorBackupdEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlInvestigapptorStorageExceptionThrowingStub("dummy"),
                new JsonUserPrefsStorage("dummy"));
        storage.handleInvestigapptorBackupEvent(new InvestigapptorBackupEvent(new Investigapptor(), "dummy"));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }

    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlInvestigapptorStorageExceptionThrowingStub extends XmlInvestigapptorStorage {

        public XmlInvestigapptorStorageExceptionThrowingStub(String filePath) {
            super(filePath);
        }

        @Override
        public void saveInvestigapptor(ReadOnlyInvestigapptor investigapptor, String filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }


}
