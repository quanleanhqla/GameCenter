package com.example.quanla.quannet.events;

import com.example.quanla.quannet.database.models.GameRoom;

/**
 * Created by QuanLA on 3/20/2017.
 */

public class ActivityReplaceEvent {
    private GameRoom gameRoom;
    private MoveToMap moveToMap;


    public ActivityReplaceEvent() {
    }


    public ActivityReplaceEvent(GameRoom gameRoom) {
        this.gameRoom = gameRoom;
        this.moveToMap = MoveToMap.FROMDETAIL;
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

    @Override
    public String toString() {
        return "ActivityReplaceEvent{" +
                "gameRoom=" + gameRoom +
                '}';
    }
}
