package com.dd.entities.monsters;

import com.dd.entities.Monster;
import com.dd.entities.Entity;

import java.util.Random;

public class Zombie extends Monster {
    private boolean conSave;
    private int deathCounter = 0;
    
    public Zombie (String name, int health, int attack, int defense) {
    	super(name,health,attack,defense);
    }

    @Override
    public void attack(Entity player) {
        // Zombie will deal damage and do a basic attack to the player
        Random random = new Random();
        if(!conSave) {
            super.attack(player, 2);
            text += "The stench of the zombie causes you to lower your defenses slightly. ";
            if(random.nextInt(3) + 1 == 4) {
                conSave = true;
                text += "However you find the strength to power through it. ";
            }
        }
        text += "The zombie lunges at you and slashes with its claws. ";
        text += "The zombie lunges at you and sinks its teeth into your skin. ";
        super.attack(player);
    }

    @Override
    public void die() {
        //25% chance the zombie comes back to life, increasing as the zombie dies more
        Random random = new Random();
        int deathCounterCopy = deathCounter + 1;
        while(deathCounterCopy != 0) {
            int roll = random.nextInt(3) + 1;
            if(roll == 4) {
                text += "As you deal a fatal blow, the zombie's eyes breath undead life once again. ";
                this.stats.setHealth(3);
                deathCounter++;
                return;
            }
            deathCounterCopy--;
        }
        text += "(if wizard) As you cast your final spell, exhausted by the fight, you find "
        		+ "the zombie moves no more. The battle is won. ";
        text += "(if fighter) You raise your weapon and take to the zombie's head. As it rolls "
        		+ "onto the ground, you breath a sigh of relief. The battle is won. ";
        super.die();
    }

    @Override
    public String confrontText() {
    	String confrontText = "As you enter the room, immediately you notice the smell of rotting meat. The smell is almost "
                + "too much to bear, ";
        Random random = new Random();
        if(random.nextInt(1) + 1 == 2) {
            conSave = true;
            confrontText += "but you pull through and find the smell. ";
        }
        else {
            conSave = false;
            confrontText += "and it is. ";
        }
        confrontText += "As you peer down the room, you notice a humanoid creature which you can assume is the"
            +" source of the rotting smell. You ready yourself for the impending attack. ";
        return confrontText;
    }

    @Override
    public String examineText() {
        return getTitle() + " looks hungry. The smell is awful eminating from the creature. ";
    }
    
    @Override
	public String getType() {
		return "Zombie";
	}
    
    @Override
    public String getTitle() {
    	return "\"" + getName() + "\"";
    }
}