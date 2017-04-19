package com.example.quanla.quannet.database;

import com.example.quanla.quannet.database.models.GameRoom;

import java.util.ArrayList;

/**
 * Created by QuanLA on 4/19/2017.
 */

public class DbSale {
    public static final DbSale instance = new DbSale();
    private ArrayList<GameRoom> allRooms;


    public ArrayList<GameRoom> getAllRooms(){
        if(allRooms==null){
            allRooms = new ArrayList<>();
        }


        return allRooms;
    }

    public void add(GameRoom gameRoom){
        if(allRooms==null){
            allRooms = new ArrayList<>();
        }
        allRooms.add(gameRoom);
    }

    public void clear(){
        allRooms.clear();
    }
}
