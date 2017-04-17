package com.dd.entities.monsters;

import com.dd.entities.Entity;
import com.dd.entities.Monster;

import java.util.Random;

public class Skeleton extends Monster {

    public Skeleton (String name, int health, int attack, int defense) {
    	super(name,health,attack,defense);
    }

    @Override
    public void die() {
        Random random = new Random();
        if(random.nextInt(5) + 1 == 5
                || this.stats.getAttack() == 1
                || this.stats.getDefense() == 1) {
            this.stats.setHealth(1);
            this.stats.setAttack(this.stats.getAttack() - 1);
            this.stats.setDefense(this.stats.getDefense() - 1);
            text += "As you deal a deadly blow, the skeleton hastily tries to keep itself together, losing some pieces in the process. ";
        }
        else {
            super.die();
            text += "The skeleton falls into a pile of ash at your feet. ";
        }
    }

    @Override
    public void attack(Entity entity) {
        entity.takeDamage(stats.getAttack());
        text += "The skeleton throws itself at you. "
        		+ titleToString() + " deals " + stats.getAttack() + " damage to " + entity.titleToString() + ". ";
    }
    
    @Override
    public int takeDamage(int damage) {
    	int damageDealt = stats.getHealth() - damage;
        stats.setHealth(damageDealt);
        if(!survives()){
        }
        return damageDealt;
    }

    @Override
    public String confrontText() {
        return "As you enter the room, you don't notice anything from the start. A few moments pass and you "
                + "start to walk around. As you take your first steps into the room proper, you hear a clatter of bones."
                + " You realize in the corner next to you there is a humanoid skeleton walking towards you. ";
    }

    @Override
    public String examineText() {
        return "A skeleton who has a bone to pick with you. ";
    }
    
    @Override
	public String typeToString() {
		return "Skeleton";
	}
}