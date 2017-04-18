package com.dd;

import com.dd.entities.*;
import com.dd.levels.DungeonMap;

import java.lang.IllegalArgumentException;
import java.util.ArrayList;
import java.util.List;

public class GameServerState extends GameState{
    private List<Player> allRegisteredPlayers;

    public GameServerState(String name, Fighter fighter, ArrayList<Player> playerList, DungeonMap map, int maxNumPlayers){
        super(name, fighter, map, maxNumPlayers);
    }
    
    public GameServerState(String name, Wizard wizard, ArrayList<Player> playerList, DungeonMap map, int maxNumPlayers){
        super(name, wizard, map, maxNumPlayers);
    }

    public void registerPlayer(Player player) {
        if(player == null)
            throw new IllegalArgumentException("Player passed to GameServerState is null. Registration failed.");
        if(!allRegisteredPlayers.contains(player))
            allRegisteredPlayers.add(player);
        else
            throw new IllegalArgumentException("Player already registered to this GameState. Registration failed.");
    }

    public void unregisterPlayer(Player player) {
        if(player == null)
            throw new IllegalArgumentException("Player passed to GameServerState is null. Unregistration failed.");
        if(!allRegisteredPlayers.remove(player))
            throw new IllegalArgumentException("Player not registered in this GameState. Unregistration failed.");
    }

    @Override
    public void addActivePlayer(Player player) {
        if(player == null)
            throw new IllegalArgumentException("Player passed to GameServerState is null. Addition failed.");
        if(!allActivePlayers.contains(player)
            && allRegisteredPlayers.contains(player))
            allActivePlayers.add(player);
        else
            throw new IllegalArgumentException("Player already active in this GameState. Addition failed.");
    }

    @Override
    public void removeActivePlayer(Player player) {
        if(player == null)
            throw new IllegalArgumentException("Player passed to GameServerState is null. Removal failed.");
        if(!allActivePlayers.remove(player))
            throw new IllegalArgumentException("Player is not currently active in this GameState. Removal failed.");
    }
}
