package com.dd.network;

import com.dd.GameState;
import com.dd.entities.Player;
import com.dd.levels.DungeonMap;

public class ClientGameState extends GameState{
    public ClientGameState(Player activePlayer, DungeonMap map){
        super("", activePlayer, map);
    }
}
