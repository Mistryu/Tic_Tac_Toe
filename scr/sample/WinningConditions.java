package sample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


//We Create a different class for it //Static Block //Static Initialization

public class WinningConditions {

    //Winning combinations
    final static List<Integer> list_LEFT_COLUMN = Arrays.asList(1, 2, 3);
    final static List<Integer> list_MIDDLE_COLUMN = Arrays.asList(4, 5, 6);
    final static List<Integer> list_RIGHT_COLUMN = Arrays.asList(7, 8, 9);
    final static List<Integer> list_TOP_ROW= Arrays.asList(1, 4, 7);
    final static List<Integer> list_MIDDLE_ROW = Arrays.asList(2, 5, 8);
    final static List<Integer> list_DOWN_ROW = Arrays.asList(3, 6, 9);
    final static List<Integer> list_CROSS1 = Arrays.asList(1, 5, 9);
    final static List<Integer> list_CROSS2 = Arrays.asList(3, 5, 7);

    static List<List> winning = new ArrayList<>();

    static {
        //Adding them to the list
        winning.add(list_TOP_ROW);
        winning.add(list_MIDDLE_ROW);
        winning.add(list_DOWN_ROW);
        winning.add(list_LEFT_COLUMN);
        winning.add(list_MIDDLE_COLUMN);
        winning.add(list_RIGHT_COLUMN);
        winning.add(list_CROSS1);
        winning.add(list_CROSS2);
    }
}