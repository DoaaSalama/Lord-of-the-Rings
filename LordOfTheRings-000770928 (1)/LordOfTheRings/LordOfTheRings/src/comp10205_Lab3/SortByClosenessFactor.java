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
public class SortByClosenessFactor implements Comparator<NovelCharacter>{

    @Override
    public int compare(NovelCharacter o1, NovelCharacter o2) {
      return Float.compare(o1.getClosenessFactor(), o2.getClosenessFactor());
    }
}