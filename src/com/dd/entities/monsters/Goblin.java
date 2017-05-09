package com.dd.entities.monsters;

import com.dd.entities.Entity;
import com.dd.entities.Monster;

public class Goblin extends Monster {

    public Goblin (String name, int health, int attack, int defense) {
    	super(name,health,attack,defense);
    }

    @Override
    public void attack(Entity entity) {
        entity.takeDamage(stats.getAttack());
        String altText = "The goblin swings with all its might a wooden club at you. ";
        super.attack(entity, altText);
    }
    
    @Override
    public int takeDamage(int damage) {
    	return super.takeDamage(damage, "As you deal the final blow, the goblin's lifeless body on the ground, a sense of relief come upon you. The battle is won. ");
    }

    @Override
    public String confrontText() {
        return "You enter a small room where you see a small humanoid creature. You approach it and hear a growl, "
                + "\"You wanna mess with " + getTitle() + "? then come at me!\". ";
    }

    @Override
    public String examineText() {
        return getTitle() + ". He isn't very bright, but sure can hit hard. ";
    }
    
    @Override
	public String getType() {
		return "Goblin";
	}
}