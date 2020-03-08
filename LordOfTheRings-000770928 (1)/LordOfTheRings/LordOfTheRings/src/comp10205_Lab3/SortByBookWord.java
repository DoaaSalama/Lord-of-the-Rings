/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp10205_Lab3;

import java.util.Comparator;

/**
 * I, Shamik Bhesaniya - 000770928, certify that this is my original work and no other person's work is used 
 * or given without due acknowledgement.
 * @author Shamik Bhesaniya
 */
public class SortByBookWord implements Comparator<BookWord> {

    @Override
    public int compare(BookWord o1, BookWord o2) {
        int result = o1.getCount() - o2.getCount();
        if (result == 0) {
            result = o2.getText().compareTo(o1.getText());
        }
        return result;
    }

}
