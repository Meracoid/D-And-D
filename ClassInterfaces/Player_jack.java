//This class provides the the game runner with a Player ADT used to perform all
//the basic commands that user will call to manipulate their character

//import the Gson package to create json file
import com.google.gson.Gson;
//import these io packages for filehandling with the database
import java.io.File;
import java.io.FileNotFoundException;

public class Player {
	//the DIR enum will be used for the switch cases in the move method
	public enum DIR{
		NORTH,SOUTH,EAST,WEST
	}
	//the EQUIP enum will be used as the switch cases in the drop method
	public enum EQUIP{
		LEFTHAND,RIGHTHAND,HANDS,SUIT
	}
	//a name field to be used when the gameRunner will address the player
	//the name will also be used when saving the game for the database file
	private String name;
	//the stat field will be used to hold the player's current stats
	private Stats stat;
	//the position field will be used to hold the player's current position in the map
	private Point position;
	//the suit field will hold the item that is currently equipped to the player's suit
	private Item suit;
	//the leftHand field will hold the item that is currently equipped to the player's left hand
	private Item leftHand;
	//the rightHand field will hold the item that is currently equipped to the player's right hand
	private Item rightHand;
	//the inventory array will hold the items that are currently held in the player's inventory
	private Item[] inventory;
	//the isAlive boolean will be true when the health stat is above zero
	private boolean isAlive;
	//isInFight boolean will be true if the player is engaged in a fight
	private boolean isInFight;
	//A constructor to initialize a new player
	//@pre if(map.isRoom(Point p))
	public Player(String name,Point p,Stats s);
	//the move method will translate the player's position point based on the direction
	//@pre if(map.isRoom(Point p))
	//@post position.translate(x,y);
	public Point move(DIR direction,Map map);
	//the equip method assigns an item to a player's suit, left hand, or right hand
	//@pre if(i1 instanceof <ITEM SUBCLASS>)
	//@pre if(<AREA FIELD>==null)
	//@post <AREA FIED>=i1
	//@post stats.modifyStats(item.getStatModifyer())
	public void equip(Item i1);
	//The drop method allows the player to drop whatever is assigned to that area.
	//@pre if(<AREA FIELD>!=null)
	//@post <AREA FIELD>=null
	//@post stats.modifyStats(item.getNegStatModifyer())
	public void drop(EQUIP E);
	//the addtoInventory(Potion potion) method will add a potion to the player's inventory
	//@pre if(p1 instanceof Potion)
	//@pre if(inventory[i]==null)
	//@post inventory[i]=p1
	public void addtoInventory(Potion p1);
	//the addtoInventory(Artifact) method will add an artifact to the player's inventory
	//@pre if(a1 instanceof Artifact)
	//@pre if(inventory[i]==null)
	//@post equip(a1)
	public void addtoInventory(Artifact a1);
	//the removefromInventory method will remove an item from the inventory
	//@pre if(inventory[i]=i1)
	//@post inventory[i]=null
	//@post if(i1 instaceof Artifact){ stats.modifyStats(item.getNegStatModifyer()) }
	public void removefromInventory(Item i1);
	//the usePotion method will use a potion found in the player's inventory
	//@pre if(inventory[i].equals(p1))
	//@post stats.modifyStats(p1.getNegStatModifyer())
	//@post removefromInventory(p1)
	public void usePotion(Potion p1);
	//the attack method is used to deal damage to a monster, and calculate whether the attack is landed
	//@pre if(isInFight())
	//@post m1.takeDamage(attack)
	public void attack(Monster m1)
	//the isInFight method returns the isInFight boolean field
	public boolean isInFight()
	//the isAlive method returns the value of the isAlive boolean field
	public boolean isAlive();
	//the die method sets the isAlive boolean to false
	public void die();
	//the take damage method is used to change a player's health stat during a fight
	//@pre monster.attack(player) called in gameRunner
	//@post health-=damage
	//@post if(health<=0){ die() }
	public void takeDamage(int damage);
	//the save method creates a json formatted database file used to serialize the player object
	//@pre File gsonFile=new File(getName()+".json");
	//@pre PrintStream toGsonFile=new PrintStream(gsonFile);
	//@post toGsonFile.println(new Gson().toJson(player));
	public void save();
	//the getName method returns the player's name
	public String getName();
	//the getPostion method returns the player's current postion on the map
	public Point getPostion();
	//the setPostion method updates the player's postion field
	//@pre if(map.isRoom(Point p))
	//@post postion=p
	public void setPosition(Point p);
	//the getStat method returns the player's current stats
	public Stats getStat();
	//the setStat method updates the stat field
	//@post stats=s
	public void setStat(Stats s);
	//the getInventory method returns the inventory array
	public Item[] getInventory();
	//the toString method overrides the superclass's toString method. 
	public String toString();
}