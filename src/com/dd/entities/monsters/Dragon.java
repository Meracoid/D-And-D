package com.dd.entities.monsters;

import com.dd.entities.Entity;
import com.dd.entities.Monster;

import java.util.Random;

public class Dragon extends Monster {

    private String dragColor;
    private boolean breathAttack;

    public Dragon (String name, int health, int attack, int defense) {
    	super(name,health,attack,defense);
        setColor();
    }

    public void setColor() {
        Random random = new Random();
        int color = random.nextInt(8) + 1;
        switch (color) {
            case 1: dragColor = "red";
                break;
            case 2: dragColor = "blue";
                break;
            case 3: dragColor = "black";
                break;
            case 4: dragColor = "green";
                break;
            case 5: dragColor = "white";
                break;
            case 6: dragColor = "bronze";
                break;
            case 7: dragColor = "brass";
                break;
            case 8: dragColor = "gold";
                break;
            case 9: dragColor = "silver";
                break;
        }
    }

    @Override
    public int takeDamage(int damage) {
    	print("You take your sword and ask for a blessing from any god that will hear. You leap up and" +
                " with a solid heave take " + getTitle() + "! The battle is won! ");
    	return super.takeDamage(damage);
    }
    
    @Override
    public void attack(Entity entity) {
        Random random = new Random();
        if(breathAttack){
        	print(getTitle() + "breathes at you with a fiery breath. It's lungs look as if they collapse a bit. ");
            super.attack(entity, 5);
            breathAttack = false;
        }else{
            if(random.nextInt(5) + 1 == 6){
                print("Before it attacks you, it regains composure in its lungs, readying its breath attack. ");
                breathAttack = true;
            }
        }
        print("It takes its claws out to slash at you, a fiery passion in its eyes. ");
        super.attack(entity);
    }
    
    @Override
    public String confrontText() {
        return "As you enter the large room, the first thing that catches your eye is jewels, mostly " + dragColor
                + ". As you walk around the abnormally large room, you hear a faint hum behind you. As you turn around, "
                + "a giant " + dragColor + " scaled dragon faces you, nostrils flared. \"Fool! You think you could steal "
                + "from " + getTitle() + " dragon? For this, you shall die!\". ";
    }

    @Override
    public String examineText() {
        return getTitle() + "The sound of the name vibrates the air. The name itself causes you to tremble in fear. ";
    }
    
    @Override
	public String getType() {
		return dragColor + " Dragon";
	}
}