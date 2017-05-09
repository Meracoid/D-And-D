package com.dd.entities.monsters;

import com.dd.entities.Entity;
import com.dd.entities.Monster;

import java.util.Random;

public class Beholder extends Monster {

    private boolean revieved = false;

    public Beholder (String name, int health, int attack, int defense) {
    	super(name,health,attack,defense);
    }

    @Override
    public void attack(Entity entity) {
        // Will use the Beholder's attackHelper method to help decide which attack it is using
        Random random = new Random();
        int attackPatern = random.nextInt(9) + 1;
        // Determines which attack the beholder does
        String altText = "";
        switch(attackPatern){
            case 1: case 3:case 5:case 7: case 9:
                altText += "5 of the eye stalks look towards you and shoot out a red beam. ";
                super.attack(entity, 2 + attackPatern);
                break;
            case 2:case 4:case 6:case 8:case 10:
            	altText += "5 of the eye stalks look towards you and shoot out a yellow beam. ";
                super.attack(entity, 2 + 4 + attackPatern/2);
                break;
        }
        altText += "Critical hit by " + getTitle() + ". ";
        if(attackPatern == 10) {
        	altText += "As you are hit, dazed by the hit, the big eye in the center of the being looks at you and you are hit "
            		+ "with a giant black beam from the main eye. ";
            super.attack(entity, 10, altText);
        }
    }
    
    @Override
    public void die() {
        Random random = new Random();
        int diePercentage = random.nextInt(19) + 1;
        if (diePercentage == 20 && !revieved) {
            text += "You stab the main eye, hoping for it to be the end of the beholder. It looks injured, and falters. But "
            		+ getTitle()
            		+ " is not one to die to such an inferior being. ";
            this.stats.addHealth(4);
            revieved = true;
        }
        else {
            super.die("You strike 5 eye stalks down, then aim for the last five. As the last eye stalks fall, " + getTitle()
            		+ "screams out \"YOU SHALL PERISH! IF NOT BY ME, BY MY BROTHERS!\" You slice through the main eye. The battle is won. ");
        }
    }
    
    @Override
    public String confrontText() {
        return "You enter a room and get a strange feeling in the air. You turn around and see a creature" +
        " with multiple eyes. \"Hahaha!\" it laughs, fear tingling through your spine. \"I am " + getTitle() +
                " feared amongst many, known by few. Those who know me know to fear me. Those who don't, soon will\". ";
    }

    @Override
    public String examineText() {
        return "10 eye stalks attached to one giant eye. It floats off the ground and stares at you intently. ";
    }
    
    @Override
	public String getType() {
		return "Beholder";
	}
}
