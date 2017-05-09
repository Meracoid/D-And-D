package com.dd.network;

import com.dd.DandD;
import com.dd.GameState;
import com.dd.entities.Player;
import com.dd.levels.DungeonMap;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ServerGameState extends GameState implements Serializable {
    private Map<UUID, Player> allRegisteredPlayers;
    private Map<UUID, Player> allActivePlayers;

    public ServerGameState(String name, Player localPlayer, DungeonMap map){
        super(name, map);
        allRegisteredPlayers = new HashMap<UUID, Player>();
        allActivePlayers = new HashMap<UUID, Player>();
        registerPlayer(DandD.getGameUUID(), localPlayer);
        addActivePlayer(DandD.getGameUUID(), localPlayer);
        activePlayer = localPlayer;
    }

    public void registerPlayer(UUID id, Player player){
        if(player == null) {
            throw new IllegalArgumentException("Player passed to ServerGameState is null. Registration failed.");
        }
        if(!allRegisteredPlayers.containsKey(id)) {
            allRegisteredPlayers.put(id, player);
        }
        else {
            throw new IllegalArgumentException("Player already registered to this GameState. Registration failed.");
        }
    }

    public void unregisterPlayer(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("UUID passed to ServerGameState is null. Unregistration failed.");
        }
        if (allRegisteredPlayers.remove(id) == null) {
            throw new IllegalArgumentException("Player not registered in this GameState. Unregistration failed.");
        }
    }

    public void addActivePlayer(UUID id, Player player) {
        if(player == null) {
            throw new IllegalArgumentException("Player passed to ServerGameState is null. Addition failed.");
        }
        if(!allActivePlayers.containsKey(id)
                && allRegisteredPlayers.containsKey(id)){
            allActivePlayers.put(id, player);
        }
        else{
            throw new IllegalArgumentException("Player already active in this GameState. Addition failed.");
        }
    }

    public void removeActivePlayer(UUID id) {
        if(id == null) {
            throw new IllegalArgumentException("UUID passed to ServerGameState is null. Removal failed.");
        }
        if(allActivePlayers.remove(id) == null){
            throw new IllegalArgumentException("Player is not currently active in this GameState. Removal failed.");
        }
    }

    public Player getRegisteredPlayer(UUID playerID){
        return allRegisteredPlayers.get(playerID);
    }

    public Player getActivePlayer(UUID playerID){
        return allActivePlayers.get(playerID);
    }

    public Collection<Player> getActivePlayersList(){
        return allActivePlayers.values();
    }

    public Collection<Player> getRegisteredPlayersList() { return allRegisteredPlayers.values(); }

    public DungeonMap getMap(){
        return map;
    }

    public void setMap(DungeonMap map){
        this.map = map;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
}
