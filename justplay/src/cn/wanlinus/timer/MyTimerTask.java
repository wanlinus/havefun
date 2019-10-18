package cn.wanlinus.timer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimerTask;

public class MyTimerTask extends TimerTask {

    private String name;
    private int count = 0;

    public MyTimerTask(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        if(count < 3) {
            //以yyyy-MM-dd HH:mm:ss的格式打印当前执行时间
            //如2018-1-3 00:00:00
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println("Current exec time is:" + sf.format(calendar.getTime()));


            //打印当前name的内容
            System.out.println("Current exec name is:" + name);
        }else {
            cancel();
            System.out.println("Timer Cancel");
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
