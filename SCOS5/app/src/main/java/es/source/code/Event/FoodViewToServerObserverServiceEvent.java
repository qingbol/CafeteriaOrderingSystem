package es.source.code.Event;

/**
 * Created by Administrator on 2017/10/31.
 */


public class FoodViewToServerObserverServiceEvent {

    private int mMsg;
    public FoodViewToServerObserverServiceEvent(int msg) {
        mMsg = msg;
    }
    public int getMsg(){
        return mMsg;
    }
}
