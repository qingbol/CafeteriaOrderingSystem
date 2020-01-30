package es.source.code.Event;

/**
 * Created by Administrator on 2017/10/31.
 */


public class ServerToFoodView_event {
    private String[] FoodName;
    private int[] FoodNumber;
    private int Msg;
    public ServerToFoodView_event(String[] name,int[]number,int msg) {
       FoodName=name;
        FoodNumber=number;
        Msg=msg;

    }
    public String[] getName(){
        return FoodName;
    }
    public int[] getNumber(){
        return FoodNumber;
    }
    public int getMsg(){
        return Msg;
    }
}
