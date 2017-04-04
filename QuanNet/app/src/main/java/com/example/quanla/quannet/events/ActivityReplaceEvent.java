package com.example.quanla.quannet.events;

import com.example.quanla.quannet.database.models.GameRoom;

/**
 * Created by QuanLA on 3/20/2017.
 */

public class ActivityReplaceEvent {
    private GameRoom gameRoom;

    public ActivityReplaceEvent() {
    }


    public ActivityReplaceEvent(GameRoom gameRoom) {
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
