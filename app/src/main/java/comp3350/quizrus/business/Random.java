package comp3350.quizrus.business;

import java.util.Collections;
import java.util.List;

public class Random {
    public static <T> void randomizeListItem(List<T> list){
        Collections.shuffle(list);
    }
}
