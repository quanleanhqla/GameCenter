package com.example.quanla.quannet.database;

import com.example.quanla.quannet.database.models.GameRoom;

import java.util.ArrayList;

/**
 * Created by QuanLA on 3/29/2017.
 */

public class DbContextNew {
    public static final DbContextNew instance = new DbContextNew();
    private ArrayList<GameRoom> allRooms;


    public ArrayList<GameRoom> getAllRooms(){
        if(allRooms==null){
            allRooms = new ArrayList<>();
        }

//        allRooms.add(new GameRoom(null, "Ibma net", "147, Mai Dich, Phuong Mai Dich, Quan Cau Giay, Ha Noi", "GOOD", 21.0403135,105.7722691));
//        allRooms.add(new GameRoom(null, "360 Game", "29, To Hieu, Phuong Nghia Do, Quan Cau Giay, Ha Noi", "GOOD",21.044935,105.7952233));
//        allRooms.add(new GameRoom(null, "X - MEN Club", "408, La Thanh, Phuong O Cho Dua, Quan Dong Da, Ha Noi", "Excellent",21.0282912,105.8043646));
//        allRooms.add(new GameRoom(null, "Phoenix Cyber", "532A, Duong Lang, Quan Dong Da, Ha Noi", "Excellent",21.011554,105.8082833));
//        allRooms.add(new GameRoom(null, "Game Sieu Toc", "57 Be Van Dan, Phuong Quang Trung, Quan Ha Dong, Ha Noi", "Excellent",20.9698878,105.7712233));
//        allRooms.add(new GameRoom(null, "Game Online", "43 Dai An, Phuong Van Quan, Quan Ha Dong, Ha Noi", "Excellent",20.9799884,105.7884401));
//        allRooms.add(new GameRoom(null, "Syndra Gaming", "8 ngo 37, Le Thanh Nghi, Quan Hai Ba Trung, Ha Noi", "Excellent",21.001255, 105.846842));
//        allRooms.add(new GameRoom(null, "Victory Game", "101E4, Le Thanh Nghi, Bach Khoa, Ha Ba Trung, Ha Noi", "Excellent",21.0027043,105.8476039));
//        allRooms.add(new GameRoom(null, "Box-Game", "4, Ngo Tram, Phuong Hang Bong, Quan Hoan Kiem, Ha Noi", "Good", 21.0309029,105.8438024));
//        allRooms.add(new GameRoom(null, "Tung's Net", "20, Bat Dan, Phuong Hang Bong, Quan Hoan Kiem, Ha Noi", "Good", 21.033793,105.8453033));
//        allRooms.add(new GameRoom(null, "Auto Game", "118, Bui Xuong Trach, Phuong Khuong Dinh, Quan Thanh Xuan, Ha Noi", "Good", 20.9868133,105.8165591));
//        allRooms.add(new GameRoom(null, "Imba Esports Stadium", "86B, Le Trong Tan, Phuong Khuong Mai, Quan Thanh Xuan, Ha Noi", "Good", 20.998231,105.8265933));

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
