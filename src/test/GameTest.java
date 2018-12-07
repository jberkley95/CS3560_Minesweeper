package test;


import main.*;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DummyTest {

    @Test
    public void numOfMinesShouldFollowFormula(){
        assertEquals(7,new Game(7,
                        1).getNumMines(),
                "# of mines should be 'size * (1 + toughness / 2)'");

        assertEquals(14,new Game(7,
                        2).getNumMines(),
                "# of mines should be 'size * (1 + toughness / 2)'");

        assertEquals(14,new Game(7,
                        3).getNumMines(),
                "# of mines should be 'size * (1 + toughness / 2)'");
    }

    @Test
    public void clickingSmileButtonShouldChangeIcon(){
        Game gameTest = new Game(7,1);
        boolean smileTest = gameTest.isSmiling();

        if(smileTest){
            if(gameTest.getSmileButton() != null) {
                assertEquals(new ImageIcon(gameTest.getSmile()),
                        gameTest.getSmileButton().getIcon(),
                        "Image should be smiling when isSmiling is true");
            }
        }else{
            if(gameTest.getSmileButton() != null) {
                assertEquals(new ImageIcon(gameTest.getDead()),
                        gameTest.getSmileButton().getIcon(),
                        "Image should be dead when isSmiling is false");
            }
        }
    }



}
