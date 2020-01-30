package es.source.code.model;

/*
 * Created by SamChen on 2017/10/16.
 */

public class FoodDital {
    private int FoodId;
    private int FoodType;     //食物类别 1:冷菜  2:热菜  3:海鲜  4:酒水
    private int FoodNum;
    private String FoodName;
    private double FoodCost;
    private String FoodRemark;
    private boolean FoodFlag;            //点菜状态  true:已点  false:未点

    public FoodDital(int foodId, int foodType, String foodName, double foodCost, boolean foodFlag) {
        this.FoodId = foodId;
        this.FoodType = foodType;
        this.FoodName = foodName;
        this.FoodCost = foodCost;
        this.FoodFlag = foodFlag;
    }

    public int getFoodId() {
        return FoodId;
    }

    public void setFoodId(int foodId) {
        FoodId = foodId;
    }

    public int getFoodType() {
        return FoodType;
    }

    public void setFoodType(int foodType) {
        FoodType = foodType;
    }

    public int getFoodNum() {
        return FoodNum;
    }

    public void setFoodNum(int foodNum) {
        FoodNum = foodNum;
    }

    public String getFoodName() {
        return FoodName;
    }

    public void setFoodName(String foodName) {
        FoodName = foodName;
    }

    public double getFoodCost() {
        return FoodCost;
    }

    public void setFoodCost(double foodCost) {
        FoodCost = foodCost;
    }

    public String getFoodRemark() {
        return FoodRemark;
    }

    public void setFoodRemark(String foodRemark) {
        FoodRemark = foodRemark;
    }

    public boolean isFoodFlag() {
        return FoodFlag;
    }

    public void setFoodFlag(boolean foodFlag) {
        FoodFlag = foodFlag;
    }
}
