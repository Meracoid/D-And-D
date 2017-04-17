package com.dd.levels;

import java.lang.IllegalArgumentException;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;

import com.dd.entities.Equip;
import com.dd.entities.monsters.*;
import com.dd.exceptions.NullRoomException;
import com.dd.items.*;

public class DungeonMap {

	private Room[][] rooms;
	private int maxRow = 10;
	private int maxCol = 10;
	private Random rand;
	private int seed;
	private MapPosition startPosition;
	private MapPosition endPosition;
	private int numberRooms;
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
	private final String[] magicNames = {
			"Wand of Fireballs",
			"Staff of Explosions",
			"Zeeko's Wand of Wisdom",
			"Praah's Staff of Prismatic Power",
			"Zaizon Quarterstaff of Magical Combat",
			"Shenron's Orb of Wish"
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
		this.seed = seed;
		rand = new Random(seed);
		rooms = new Room[maxRow][maxCol];
		numberRooms = 0;
		generateDungeon();
	}

	public int getSeed() {
		return seed;
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
	
	public Room getRoom(MapPosition pos) throws NullRoomException {
		if(isOutOfBounds(pos)) {
			throw new NullRoomException("Room is out of bounds. ");
		}
		if(rooms[pos.getY()][pos.getX()] == null) {
			throw new NullRoomException("Room does not exist. ");
		}
		return rooms[pos.getY()][pos.getX()];
	}

	public boolean isOutOfBounds(MapPosition pos) {
		return pos.getX() < 0
				|| pos.getX() > rooms.length - 1
				|| pos.getY() < 0
				|| pos.getY() > rooms[pos.getY()].length - 1;
	}
	
	private MapPosition randPos(){
		int yStart = rand.nextInt(10);
		int xStart = rand.nextInt(10);
		return new MapPosition(xStart, yStart);
	}
	
	public void setRoom(Room room, MapPosition position) {
		rooms[position.getY()][position.getX()] = room;
		numberRooms++;
	}

	private void generateDungeon() {
		Room start = new Room();
		start.addItem(new OneHandedWeapon("Wooden Sword", 2));
		start.addItem(new Shield("Wooden Shield", 1));
		startPosition = randPos();
		Room end = new Room();
		end.addMonster(new Dragon(dragNames[rand.nextInt(15)], 40, 10, 10));
		int xEnd = rand.nextInt(10);
		while(xEnd > startPosition.getX() - 3
				&& xEnd < startPosition.getX() + 3) {
			xEnd = rand.nextInt(10);
		}
		int yEnd = rand.nextInt(10);
		while(yEnd > startPosition.getY() - 3
				&& yEnd < startPosition.getY() + 3) {
			yEnd = rand.nextInt(10);
		}
		endPosition = new MapPosition(xEnd, yEnd);
		generateLineToEnd(startPosition, endPosition);
		int branches = (numberRooms + 2) / 3;
		generateBranches(branches);
		setRoom(start, startPosition);
		setRoom(end, endPosition);
	}

	private void generateLineToEnd(MapPosition start, MapPosition end) {
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
				if(yTransfer > end.getY() && yTransfer > 0) {
					yTransfer--;
				}
				else if(yTransfer < end.getY()  &&  yTransfer < 10) {
					yTransfer++;
				}
				else {
					yTrue = true;
					bothTrue = xTrue && yTrue;
					if(bothTrue) {
						return;
					}
				}
				generateRoom(xTransfer, yTransfer);
				vertical = false;
			}
			else {
				if(xTransfer > end.getX() && xTransfer > 0) {
					xTransfer--;
				}
				else if(xTransfer < end.getX() &&  xTransfer < 10) {
					xTransfer++;
				}
				else {
					xTrue = true;
					bothTrue = xTrue && yTrue;
					if(bothTrue) {
						return;
					}
				}
				generateRoom(xTransfer, yTransfer);
				vertical = true;
			}
		}
	}

	private void generateRoom(int x, int y) {
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
				Goblin mon = new Goblin(gobNames[rand.nextInt(7)], 5 + rand.nextInt(11), 2 + rand.nextInt(2), 1);
				room.addMonster(mon);
				int shieldNum = rand.nextInt(5);
				room.addItem(new Shield(shieldNames[shieldNum], 1 + shieldNum));
			}
			else if(type == 2) {
				Skeleton mon = new Skeleton("skeleton", 10 + rand.nextInt(6), 4 + rand.nextInt(4), 1);
				room.addMonster(mon);
				int suitNum = rand.nextInt(6);
				room.addItem(new Suit(suitNames[suitNum], suitNum + 3));
			}
			else if(type == 3) {
				Zombie mon = new Zombie("zombie", 10 + rand.nextInt(6), 4, 4);
				room.addMonster(mon);
				int swordNum = rand.nextInt(5);
				room.addItem(new OneHandedWeapon(oneSwordNames[swordNum], 5 + swordNum));
			}
		}
		int loot = rand.nextInt(2);
		if(loot == 1) {
			int lootType = rand.nextInt(2);
			if(lootType == 1) {
				int potNum = rand.nextInt(4);
				room.addItem(new Potion(potionNames[potNum], (potNum + 1) * 4));
			}
			else {
				int magicNum = rand.nextInt(6);
				room.addItem(new Magical(magicNames[magicNum], Equip.LEFTHAND, 1*magicNum, 1*magicNum, 3*magicNum, magicNum/3));
			}
		}
		setRoom(room, new MapPosition(x, y));
	}

	private void generateBranches(int branches) {
		int counter = 0;
		for(int i = 0; i < branches; counter++) {
			int x = rand.nextInt(10);
			int y = rand.nextInt(10);
			if(rooms[y][x] == null) {
				MapPosition point = findPointClose(x, y);
				generateLineToEnd(new MapPosition(x, y), point);
				generateRoom(x, y);
				i++;
			}
		}
		if(counter == branches){
			generateBranches(branches - 1);
		}
	}

	private MapPosition findPointClose(int x, int y) {
		int tempX = x;
		int tempY = y;
		ArrayList<CompareDistance> map = new ArrayList<>();
		int distance = 0;
		int sett = 0;
		for(; tempX < 10; tempX++) {
			for(; tempY < 10; tempY++) {
				distance++;
				if(rooms[tempY][tempX] != null) {
					map.add(new CompareDistance(distance, new MapPosition(tempX, tempY)));
					distance = sett;
				}
			}
			sett++;
		}
		distance = 0;
		sett = 0;
		for(tempX = x; tempX >= 0; tempX--) {
			for(tempY = y; tempY < 10; tempY++) {
				distance++;
				if(rooms[tempY][tempX] != null) {
					map.add(new CompareDistance(distance, new MapPosition(tempX, tempY)));
					distance = sett;
				}
			}
			sett++;
		}
		distance = 0;
		sett = 0;
		for(tempX = x; tempX < 10; tempX++) {
			for(tempY = y; tempY >= 0; tempY--) {
				distance++;
				if(rooms[tempY][tempX] != null) {
					map.add(new CompareDistance(distance, new MapPosition(tempX, tempY)));
					distance = sett;
				}
			}
			sett++;
		}
		distance = 0;
		sett = 0;
		for(tempX = x; tempX >= 0; tempX--) {
			for(tempY = y; tempY >= 0; tempY--) {
				distance++;
				if(rooms[tempY][tempX] != null) {
					map.add(new CompareDistance(distance, new MapPosition(tempX, tempY)));
					distance = sett;
				}
			}
			sett++;
		}
		MapPosition current = new MapPosition();
		int maxDistance = 999;
		for(int i = 0; i < map.size(); i++) {
			if(map.get(i).getDistance() < maxDistance) {
				current = map.get(i).getPosition();
			}
		}
		return current;
	}
	
	public MapPosition getEndPosition() {
		return endPosition;
	}
}
