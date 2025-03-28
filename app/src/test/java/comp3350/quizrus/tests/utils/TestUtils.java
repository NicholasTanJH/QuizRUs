package comp3350.quizrus.tests.utils;

import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import comp3350.quizrus.application.Main;

public class TestUtils {
    private static final File DB_SRC = new File("src/main/assets/db/quizrusdb.script");

    /**
     * Creates a copy of the database file for testing purposes.
     * The copied database file is saved in the current directory.
     * Also sets the database name.
     */
    public static File copyDB() throws IOException {
        File target = new File("./testdb.script");
        Files.copy(DB_SRC, target);
        Main.setDBPathName(target.getAbsolutePath().replace(".script", ""));
        return target;
    }
}