package com.example.quanla.quannet.database.models;

import com.example.quanla.quannet.R;

import java.util.ArrayList;

/**
 * Created by QuanLA on 3/19/2017.
 */

public class DCMTHANG {
    private ArrayList<Photos> images;

    public static final DCMTHANG instance = new DCMTHANG();

    public ArrayList<Photos> allImage(){
        images = new ArrayList<>();

        images.add(new Photos(R.drawable.ic_account_circle_black_24px));
        images.add(new Photos(R.drawable.ic_account_circle_white_24px));
        images.add(new Photos(R.drawable.ic_fiber_new_white_24px));
        images.add(new Photos(R.drawable.ic_fiber_new_white_24px));
        images.add(new Photos(R.drawable.ic_fiber_new_white_24px));
        images.add(new Photos(R.drawable.ic_fiber_new_white_24px));

        return images;
    }


}
