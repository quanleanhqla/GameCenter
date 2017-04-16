package com.example.quanla.quannet.database;

import com.example.quanla.quannet.R;
import com.example.quanla.quannet.database.models.Photos;

import java.util.ArrayList;

/**
 * Created by QuanLA on 4/16/2017.
 */

public class DbPhoto {
    private ArrayList<Photos> arrayList;

    public static final DbPhoto instance = new DbPhoto();

    public ArrayList<Photos> getArrayList(){
        arrayList = new ArrayList<>();

        arrayList.add(new Photos(R.drawable.ic_access_time_white_24px));
        arrayList.add(new Photos(R.drawable.ic_account_circle_black_24px));
        arrayList.add(new Photos(R.drawable.ic_arrow_back_black_24dp));
        arrayList.add(new Photos(R.drawable.ic_clear_black_24dp));

        return arrayList;
    }
}
