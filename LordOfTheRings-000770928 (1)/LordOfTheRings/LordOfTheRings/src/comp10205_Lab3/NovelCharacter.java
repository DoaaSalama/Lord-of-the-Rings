/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp10205_Lab3;

import java.util.HashSet;

/**
 * I, Shamik Bhesaniya - 000770928, certify that this is my original work and no other person's work is used 
 * or given without due acknowledgement.
 * @author Shamik Bhesaniya
 */
public class NovelCharacter {
    
    private String characterName;
    
    private int numberofOccurrences;
    
    private int closenessCount;

    private float closenessFactor;
    
    
    private final HashSet<Integer> characterPositions ;

    public NovelCharacter(String characterName) {
        this.characterName = characterName;
        characterPositions = new HashSet<>();
    }
    
    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public int getNumberofOccurrences() {
        return numberofOccurrences;
    }

    public void  incrementNumberofOccurrences() {
        this.numberofOccurrences++;
    }

    public int getClosenessCount() {
        return closenessCount;
    }

    public void incrementClosenessCount() {
        this.closenessCount++;
    }

    public float getClosenessFactor() {
        return closenessFactor;
    }

    public void setClosenessFactor(float closenessFactor) {
        this.closenessFactor = closenessFactor;
    }

    public HashSet<Integer> getCharacterPositions() {
        return characterPositions;
    }

    public void AddNewCharacterPosition(int characterPosition) {
        this.characterPositions.add(characterPosition);
    }
    
    
    
}
