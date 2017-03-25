package com.example.quanla.quannet.events;

import com.example.quanla.quannet.database.models.GameRoom;

/**
 * Created by QuanLA on 3/22/2017.
 */

public class MoveToMapEvent {
    private GameRoom gameRoom;
    private MoveToMap moveToMap;

    public MoveToMapEvent() {
    }

    public MoveToMapEvent(MoveToMap moveToMap){
        this.moveToMap = moveToMap;
    }



    public MoveToMapEvent(GameRoom gameRoom, MoveToMap moveToMap) {
        this.gameRoom = gameRoom;
        this.moveToMap = moveToMap;
    }

    public GameRoom getGameRoom() {
        return gameRoom;
    }

    public void setGameRoom(GameRoom gameRoom) {
        this.gameRoom = gameRoom;
    }

    public MoveToMap getMoveToMap() {
        return moveToMap;
    }

    public void setMoveToMap(MoveToMap moveToMap) {
        this.moveToMap = moveToMap;
    }
}
