package com.example.quanla.quannet.database;

import com.example.quanla.quannet.database.models.Computer;

import java.util.ArrayList;

/**
 * Created by QuanLA on 4/1/2017.
 */

public class DbComputer {
    private ArrayList<Computer> computerArrayList;

    public static final DbComputer instance = new DbComputer();

    public ArrayList<Computer> getComputerArrayList(){
        computerArrayList = new ArrayList<>();
        computerArrayList.add(new Computer(false, 1));
        computerArrayList.add(new Computer(false, 2));
        computerArrayList.add(new Computer(false, 3));
        computerArrayList.add(new Computer(false, 4));
        computerArrayList.add(new Computer(true, 5));
        computerArrayList.add(new Computer(false, 6));
        computerArrayList.add(new Computer(false, 7));
        computerArrayList.add(new Computer(false, 8));
        computerArrayList.add(new Computer(true, 9));
        computerArrayList.add(new Computer(false, 10));
        return  computerArrayList;
    }
}
