package com.example.quanla.quannet.events;

import com.example.quanla.quannet.database.models.GameRoom;

/**
 * Created by QuanLA on 4/18/2017.
 */

public class FromInfoEvent {
    private GameRoom gameRoom;


    public FromInfoEvent() {
    }


    public FromInfoEvent(GameRoom gameRoom) {
        this.gameRoom = gameRoom;
    }



    public GameRoom getGameRoom() {
        return gameRoom;
    }

    public void setGameRoom(GameRoom gameRoom) {
        this.gameRoom = gameRoom;
    }



    @Override
    public String toString() {
        return "ActivityReplaceEvent{" +
                "gameRoom=" + gameRoom +
                '}';
    }
}
