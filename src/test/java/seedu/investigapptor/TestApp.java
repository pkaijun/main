package seedu.investigapptor;

import java.io.File;
import java.io.IOException;
import java.util.function.Supplier;

import javafx.stage.Screen;
import javafx.stage.Stage;
import seedu.investigapptor.commons.core.Config;
import seedu.investigapptor.commons.core.GuiSettings;
import seedu.investigapptor.commons.exceptions.DataConversionException;
import seedu.investigapptor.commons.exceptions.WrongPasswordException;
import seedu.investigapptor.commons.util.FileUtil;
import seedu.investigapptor.commons.util.XmlUtil;
import seedu.investigapptor.model.Investigapptor;
import seedu.investigapptor.model.Model;
import seedu.investigapptor.model.ModelManager;
import seedu.investigapptor.model.ReadOnlyInvestigapptor;
import seedu.investigapptor.model.UserPrefs;
import seedu.investigapptor.storage.UserPrefsStorage;
import seedu.investigapptor.storage.XmlSerializableInvestigapptor;
import seedu.investigapptor.testutil.TestUtil;
import systemtests.ModelHelper;

/**
 * This class is meant to override some properties of MainApp so that it will be suited for
 * testing
 */
public class TestApp extends MainApp {

    public static final String SAVE_LOCATION_FOR_TESTING = TestUtil.getFilePathInSandboxFolder("sampleData.xml");
    public static final String APP_TITLE = "Test App";

    protected static final String DEFAULT_PREF_FILE_LOCATION_FOR_TESTING =
            TestUtil.getFilePathInSandboxFolder("pref_testing.json");
    protected static final String ADDRESS_BOOK_NAME = "Test";
    protected Supplier<ReadOnlyInvestigapptor> initialDataSupplier = () -> null;
    protected String saveFileLocation = SAVE_LOCATION_FOR_TESTING;

    public TestApp() {
    }

    public TestApp(Supplier<ReadOnlyInvestigapptor> initialDataSupplier, String saveFileLocation) {
        super();
        this.initialDataSupplier = initialDataSupplier;
        this.saveFileLocation = saveFileLocation;

        // If some initial local data has been provided, write those to the file
        if (initialDataSupplier.get() != null) {
            createDataFileWithData(new XmlSerializableInvestigapptor(this.initialDataSupplier.get()),
                    this.saveFileLocation);
        }
    }

    @Override
    protected Config initConfig(String configFilePath) {
        Config config = super.initConfig(configFilePath);
        config.setAppTitle(APP_TITLE);
        config.setUserPrefsFilePath(DEFAULT_PREF_FILE_LOCATION_FOR_TESTING);
        return config;
    }

    @Override
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        UserPrefs userPrefs = super.initPrefs(storage);
        double x = Screen.getPrimary().getVisualBounds().getMinX();
        double y = Screen.getPrimary().getVisualBounds().getMinY();
        userPrefs.updateLastUsedGuiSetting(new GuiSettings(600.0, 600.0, (int) x, (int) y));
        userPrefs.setInvestigapptorFilePath(saveFileLocation);
        userPrefs.setInvestigapptorName(ADDRESS_BOOK_NAME);
        return userPrefs;
    }

    /**
     * Returns a defensive copy of the investigapptor book data stored inside the storage file.
     */
    public Investigapptor readStorageInvestigapptor() {
        try {
            return new Investigapptor(storage.readInvestigapptor().get());
        } catch (WrongPasswordException wpe) {
            throw new AssertionError("Invalid password");
        } catch (DataConversionException dce) {
            throw new AssertionError("Data is not in the Investigapptor format.");
        } catch (IOException ioe) {
            throw new AssertionError("Storage file cannot be found.");
        }
    }

    /**
     * Returns the file path of the storage file.
     */
    public String getStorageSaveLocation() {
        return storage.getInvestigapptorFilePath();
    }

    /**
     * Returns a defensive copy of the model.
     */
    public Model getModel() {
        Model copy = new ModelManager((model.getInvestigapptor()), new UserPrefs());
        ModelHelper.setFilteredPersonList(copy, model.getFilteredPersonList());
        return copy;
    }

    @Override
    public void start(Stage primaryStage) {
        ui.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Creates an XML file at the {@code filePath} with the {@code data}.
     */
    private <T> void createDataFileWithData(T data, String filePath) {
        try {
            File saveFileForTesting = new File(filePath);
            FileUtil.createIfMissing(saveFileForTesting);
            XmlUtil.saveDataToFile(saveFileForTesting, data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
