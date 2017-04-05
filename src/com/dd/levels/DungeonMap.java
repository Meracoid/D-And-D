package com.dd.levels;

import java.lang.IllegalArgumentException;
import java.util.Random;

import com.dd.entities.Equip;
import com.dd.entities.monsters.*;
import com.dd.items.*;

public class DungeonMap {

	private Room[][] rooms;
	private int maxRow = 10;
	private int maxCol = 10;
	private Random rand;
	private MapPosition startPosition;
	private MapPosition endPosition;
	private final String[] suitNames = {
			"Cloth Armor",
			"Chain Mail",
			"Splint",
			"Brass Armor",
			"Gold Plate Armor",
			"Dragon Hide Armor"
	};
	private final String[] shieldNames = {
			"Iron Shield",
			"Iron Shield",
			"Brass Shield",
			"Golden Shield",
			"Dragon Hide Armor"
	};
	private final String[] potionNames = {
			"Potion of Healing",
			"Potion of Greater Healing",
			"Potion of Superior Healing",
			"Potion of Exceptional Healing"
	};
	private final String[] twoSwordNames = {
			"Greatsword",
			"Greatsword of Kuu",
			"Great Axe",
			"Blood Axe",
			"Kravenedge, the Great Sword"
	};
	private final String[] oneSwordNames = {
			"Longsword",
			"Longsword of Ilon",
			"Club",
			"Club of Durrak",
			"Dragonscale Sword"
	};
	private final String[] dragNames = {
			"Thordak",
			"Raishan",
			"Galisha",
			"Velica",
			"Vorical",
			"Dairak",
			"Kelshawn",
			"Herp",
			"Saphera",
			"Puff",
			"Shenron",
			"Smaug",
			"Vizarion",
			"Valoo",
			"Faizon"
	};
	private final String[] gobNames = {
			"Bogoblin",
			"Rowllin",
			"Stellart",
			"Combarn",
			"Meltion",
			"Ori",
			"Romil"
	};
	private final String[] beholdNames = {
			"K'varn",
			"Re'tunar",
			"Ei'Soma",
			"Emo'Sutra",
			"Brah'Zumar",
			"Ko'Rita",
			"Tumo'Uta",
			"Ai'Mantra",
			"Ma'Kefet",
			"Zi'Tumo"
	};
	
	public DungeonMap(int seed) {
		rand = new Random(seed);
		rooms = new Room[maxRow][maxCol];
		generateDungeon();
	}

	public int getMaxRow() {
		return maxRow;
	}

	public int getMaxCol() {
		return maxCol;
	}

	public boolean isRoom(MapPosition p) {
		boolean retCode = true;
		if(isOutOfBounds(p) || rooms[p.getY()][p.getX()]==null) {
			retCode = false;
		}
		return retCode;
	}
	
	public boolean isRoomInDir(MapPosition p, Direction direction) {
		boolean retCode = true;
		MapPosition mp;
		switch(direction) {
		case NORTH:
			mp = p.translate(Direction.NORTH);
			break;
		case SOUTH:
			mp = p.translate(Direction.SOUTH);
			break;
		case EAST:
			mp = p.translate(Direction.EAST);
			break;
		case WEST:
			mp = p.translate(Direction.WEST);
			break;
		default:
			mp = p;
		}
		if(isOutOfBounds(mp) || rooms[mp.getY()][mp.getX()] == null) {
			retCode = false;
		}
		return retCode;
	}

	public MapPosition getStartPosition() {
		return startPosition;
	}

	public void setStartPosition(MapPosition startPosition) {
		this.startPosition = startPosition;
	}

	public void addRoom(Room room, MapPosition position) {
		if(isOutOfBounds(position)){
			throw new IllegalArgumentException("The position ("
												+ Integer.toString(position.getX())
												+ ", "
												+ Integer.toString(position.getY())
												+") is out of bounds of the map. Room not added to DungeonMap.");
		}
		else if(rooms[position.getY()][position.getX()] != null) {
			throw new IllegalArgumentException("The position ("
												+ Integer.toString(position.getX())
												+ ", "
												+ Integer.toString(position.getY())
												+ ") already contains a room. Room not added to DungeonMap.");
		}
		rooms[position.getY()][position.getX()] = room;
	}
	
	public Room getRoom(MapPosition pos) {
		if(isOutOfBounds(pos)) {
			System.out.println("e:DungeonMap.getRoom():out of bounds of map");
			return null;
		}
		return rooms[pos.getY()][pos.getX()];
	}

	public boolean isOutOfBounds(MapPosition pos) {
		return pos.getX() < 0
				|| pos.getX() > rooms.length - 1
				|| pos.getY() < 0
				|| pos.getY() > rooms[pos.getY()].length - 1;
	}
	
	public MapPosition randPos(){
		int yStart = rand.nextInt(10);
		int xStart = rand.nextInt(10);
		return new MapPosition(xStart, yStart);
	}
	
	public void setRoom(Room room, MapPosition position) {
		rooms[position.getY()][position.getX()] = room;
	}

	public void generateDungeon() {
		Room start = new Room();
		start.addItem(new OneHandedWeapon("wooden sword", 2));
		start.addItem(new Shield("wooden shield", 1));
		startPosition = randPos();
		setRoom(start, startPosition);
		Room end = new Room();
		end.addMonster(new Dragon(dragNames[rand.nextInt(15)], 40, 10, 10));
		int xEnd = rand.nextInt(10);
		int yEnd = rand.nextInt(10);
		while(xEnd <= startPosition.getX() + 3
				&& xEnd >= startPosition.getX() - 3
				&& xEnd > startPosition.getX() + 3
				&& xEnd <= startPosition.getX() - 3) {
			xEnd = rand.nextInt(10);
		}
		while(yEnd <= startPosition.getY() + 3
				&& yEnd >= startPosition.getY() - 3
				&& yEnd <= yEnd - 3
				&& yEnd >= startPosition.getY() + 3) {
			yEnd = rand.nextInt(10);
		}
		endPosition = new MapPosition(xEnd, yEnd);
		generateLineToEnd(startPosition, endPosition, rand);
		setRoom(end, new MapPosition(xEnd, yEnd));
	}

	public void generateLineToEnd(MapPosition start, MapPosition end, Random rand) {
		int xTransfer = start.getX();
		int yTransfer = start.getY();
		boolean vertical = false;
		boolean yTrue = false;
		boolean xTrue = false;
		boolean bothTrue = false;
		while (!bothTrue) {
			if(xTrue) {
				vertical = true;
			}
			else if(yTrue) {
				vertical = false;
			}
			if(vertical) {
				if(yTransfer > end.getY()) {
					yTransfer--;
				}
				else if(yTransfer < end.getY()) {
					yTransfer++;
				}
				else {
					yTrue = true;
					bothTrue = xTrue && yTrue;
					if(bothTrue) {
						return;
					}
				}
				generateRoom(xTransfer, yTransfer, rand);
				vertical = false;
			}
			else {
				if(xTransfer > end.getX()) {
					xTransfer--;
				}
				else if(xTransfer < end.getX()) {
					xTransfer++;
				}
				else {
					xTrue = true;
					bothTrue = xTrue && yTrue;
					if(bothTrue) {
						return;
					}
				}
				generateRoom(xTransfer, yTransfer, rand);
				vertical = true;
			}
		}
	}

	public void generateRoom(int x, int y, Random rand) {
		int monster = rand.nextInt(2);
		Room room = new Room();
		if(monster == 1) {
			int type = rand.nextInt(4);
			if(type == 0) {
				String name = beholdNames[rand.nextInt(10)];
				Beholder mon = new Beholder(name, 20, 1, 4);
				room.addMonster(mon);
				int swordNum = rand.nextInt(5);
				room.addItem(new TwoHandedWeapon(twoSwordNames[swordNum], 6 + swordNum));
				room.addItem(new Artifact(name + " Amulet", 5 + rand.nextInt(6), 5 + rand.nextInt(6), 0, 5 + rand.nextInt(6)));
			}
			else if(type == 1) {
				Goblin mon = new Goblin(gobNames[rand.nextInt(7)] + " the goblin", 5 + rand.nextInt(11), 2 + rand.nextInt(2), 1);
				room.addMonster(mon);
				int shieldNum = rand.nextInt(5);
				room.addItem(new Shield(shieldNames[shieldNum], 1 + shieldNum));
			}
			else if(type == 2) {
				Skeleton mon = new Skeleton("skel", 10 + rand.nextInt(6), 4 + rand.nextInt(4), 1);
				room.addMonster(mon);
				int suitNum = rand.nextInt(6);
				room.addItem(new Suit(suitNames[suitNum], suitNum + 3));
			}
			else if(type == 3) {
				Zombie mon = new Zombie("zomb", 10 + rand.nextInt(6), 4, 4);
				room.addMonster(mon);
				int swordNum = rand.nextInt(5);
				room.addItem(new OneHandedWeapon(oneSwordNames[swordNum], 5 + swordNum));
			}
		}
		int loot = rand.nextInt(2);
		if(loot == 1) {
			int potNum = rand.nextInt(4);
			room.addItem(new Potion(potionNames[potNum], (potNum + 1) * 4));
		}
		rooms[y][x] = room;
	}
}
