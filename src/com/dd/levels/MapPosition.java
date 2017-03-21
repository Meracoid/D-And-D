package com.dd.levels;

import com.dd.tester.DIR;

public class MapPosition {
	private int x;
	private int y;

	public MapPosition(){
		y = 0;
		x = 0;
	}
	public MapPosition(int xPos,int yPos){
		y = yPos;
		x = xPos;
	}

	public MapPosition getNorth(){
		return new MapPosition(x,y-1);
	}

	public MapPosition getSouth(){
		return new MapPosition(x,y+1);
	}

	public MapPosition getEast(){
		return new MapPosition(x+1,y);
	}

	public MapPosition getWest(){
		return new MapPosition(x-1,y);
	}

	public int getX(){
		return x;
	}

	public void setX(int x){
		this.x = x;
	}

	public int getY(){
		return y;
	}

	public void setY(int y){
		this.y = y;
	}
	
	public void moveNorth(){
		this.y--;
	}
	
	public void moveSouth(){
		this.y++;
	}
	
	public void moveEast(){
		this.x++;
	}
	
	public void moveWest(){
		this.x--;
	}
	
	public void translate(DIR direction){
		switch(direction){
		case NORTH:
			y--;
			break;
		case SOUTH:
			y++;
			break;
		case EAST:
			x++;
			break;
		case WEST:
			x--;
			break;
		default:
			break;
		}
	}
}
