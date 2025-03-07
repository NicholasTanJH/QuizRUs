package comp3350.quizrus.tests.utils;

import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import comp3350.quizrus.application.Main;
import comp3350.quizrus.application.Services;
import comp3350.quizrus.persistence.stubs.UserPersistenceStub;

public class TestUtils {
    private static final File DB_SRC = new File("src/main/assets/db/quizrusdb.script");

    public static File copyDB() throws IOException {
        File target = new File("./testdb.script");
        Files.copy(DB_SRC, target);
        Main.setDBPathName(target.getAbsolutePath().replace(".script", ""));
        return target;
    }

    public static void deleteDB() {
        if (Services.getAnswerPersistence() instanceof UserPersistenceStub) {

        }
    }
}