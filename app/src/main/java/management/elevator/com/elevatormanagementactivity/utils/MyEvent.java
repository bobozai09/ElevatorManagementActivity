package management.elevator.com.elevatormanagementactivity.utils;

/**
 * Created by Administrator on 2017/1/6 0006.
 */

public class MyEvent {
    private String mMsg;
    private  int mNumber;
    public MyEvent(String msg,int number){
        this.mMsg=msg;
        this.mNumber=number;
    }
    public String getMsg(){

        return mMsg;
    }
}
