package comp3350.quizrus.business;

import java.util.Collections;
import java.util.List;

public class Random {
    public static <T> boolean randomizeListItem(List<T> list) {
        if (list == null || list.isEmpty()) {
            return false;
        } else {
            Collections.shuffle(list);
            return true;
        }
    }
}
