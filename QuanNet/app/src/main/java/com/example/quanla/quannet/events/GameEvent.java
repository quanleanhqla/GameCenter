package com.example.quanla.quannet.events;

import com.example.quanla.quannet.database.models.GameRoom;

/**
 * Created by QuanLA on 4/19/2017.
 */

public class GameEvent {
    private GameRoom gameRoom;

    public GameEvent(GameRoom gameRoom) {
        this.gameRoom = gameRoom;
    }

    public GameRoom getGameRoom() {
        return gameRoom;
    }

    public void setGameRoom(GameRoom gameRoom) {
        this.gameRoom = gameRoom;
    }
}
