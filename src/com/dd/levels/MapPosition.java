package com.dd.levels;

public class MapPosition {
	
	private int x;
	private int y;
	private boolean isInDungeon;

	public MapPosition() {
		setX(0);
		setY(0);
	}
	
	public MapPosition(boolean isInDungeon) {
		if(isInDungeon == false){
			this.x = -1;
			this.y = -1;
		}
	}
	
	public MapPosition(int xPos, int yPos) {
		setY(yPos);
		setX(xPos);
	}

	public MapPosition getNorth(){
		return new MapPosition(x,y+1);
	}

	public MapPosition getSouth(){
		return new MapPosition(x,y-1);
	}

	public MapPosition getEast() {
		return new MapPosition(x+1, y);
	}

	public MapPosition getWest() {
		return new MapPosition(x-1, y);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		if(x < 0) {
			//kyle, what would be best way to error handle this?
			//I feel like it will already be handled in the isOutOfBounds(p) in DungeonMap
		}
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		if(y < 0) {
			//kyle, what would be best way to error handle this?
			//I feel like it will already be handled in the isOutOfBounds(p) in DungeonMap
		}
		this.y = y;
	}
	
	public void moveNorth() {
		this.y--;
	}
	
	public void moveSouth() {
		this.y++;
	}
	
	public void moveEast() {
		this.x++;
	}
	
	public void moveWest() {
		this.x--;
	}
	
	public MapPosition translate(Direction direction) {
		MapPosition mp=new MapPosition(getX(), getY());
		switch(direction) {
		case NORTH:
			mp.moveNorth();
			break;
		case SOUTH:
			mp.moveSouth();
			break;
		case EAST:
			mp.moveEast();
			break;
		case WEST:
			mp.moveWest();
			break;
		default:
			break;
		}
		return mp;
	}
}
