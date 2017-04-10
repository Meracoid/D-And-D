package com.dd.entities.monsters;

import com.dd.entities.Entity;
import com.dd.entities.Monster;

public class Goblin extends Monster {

    public Goblin (String name, int health, int attack, int defense) {
    	super(name,health,attack,defense);
    }

    @Override
    public void attack(Entity entity) {
    	clearText();
        entity.takeDamage(stats.getAttack());
        text = text + "The goblin swings with all its might a wooden club at you. "
        		+ titleToString() + " deals " + stats.getAttack() + " damage to " + entity.titleToString() + ". ";
    }
    
    @Override
    public void takeDamage(int damage) {
        clearText();
        stats.setHealth(stats.getHealth() - damage);
        if(!survives()){
            text = text + "As you deal the final blow, the goblin's lifeless body on the ground, a sense of relief come upon you. The battle is won. ";
        }
    }

    @Override
    public String confrontText() {
        return "You enter a small room where you see a small humanoid creature. You approach it and hear a growl, "
                + "\"You wanna mess with " + titleToString() + "? then come at me!\". ";
    }

    @Override
    public String examineText() {
        return titleToString() + ". He isn't very bright, but sure can hit hard";
    }
    
    @Override
	public String typeToString() {
		return "Goblin";
	}
}