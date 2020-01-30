package es.source.code.data;

import java.io.Serializable;

/**
 * Created by SamChen on 2017/10/16.
 */

public class HotFoodData{
    private String[] hotfoodname={"热菜1","热菜2","热菜3","热菜4",};
    private int[] hotfoodnum = {5,6,8,10};

    public String[] getHotfoodname() {
        return hotfoodname;
    }

    public int[] getHotfoodnum() {
        return hotfoodnum;
    }
}
