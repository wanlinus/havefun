package cn.wanlinus.timer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;

public class MyTimer {
    public static void main(String[] args) {
        //1. 创建Timer实例
        Timer timer = new Timer();
        //2. 创建一个MyTimerTask实例
        MyTimerTask myTimerTask = new MyTimerTask("No.1");
        //3. 通过timer定时顶平路调用myTimerTask的业务逻辑
        //即第一次执行是在当前时间的两秒之后,每隔一秒钟执行一次
        //timer.schedule(myTimerTask,2000,1000);

        /**
         * 获取当前时间,并设置成距离当前时间的三秒之后的时间
         * 如当前时间是2018-1-3 16:30:00
         * 设置后的时间则为2018-1-3 16:30:03
         */
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sf.format(calendar.getTime()));
        calendar.add(Calendar.SECOND, 3);

        // ------------schedule的用法
        /**
         * 1. 在时间等于或唱过time的时候执行且仅执行一次task
         * 如在2018-1-3 00:00:00执行一次task: 打印任务名字
         */
//        myTimerTask.setName("schedule1");
//        timer.schedule(myTimerTask, calendar.getTime());
        /**
         * 2. 时间等于或超过time时首次执行task
         * 之后每隔period哈秒重复执行一次task
         * 如在2018-1-3 00:00:00第一次执行task: 打印任务的名字
         */
//        myTimerTask.setName("schedule2");
//        timer.schedule(myTimerTask, calendar.getTime(), 2000);

        /**
         * 3. 等待delay毫秒后且执行一次task
         * 如现在是2018-01-03 18:17:00
         * 则在2018-01-03 18:17:01执行一次task:打印任务的名字
         */
//        myTimerTask.setName("schedule3");
//        timer.schedule(myTimerTask, 2000L);

        /**
         * 4. 等待delay毫秒后首次执行task
         * 之后每隔一次period执行task时间间隔
         */
//        myTimerTask.setName("schedule4");
//        timer.schedule(myTimerTask, 2000L, 1000L);

        //----------------------    scheduleAtFixedRate的用法

        /**
         * 基本用法和schedule差不多
         */
//        myTimerTask.setName("scheduleAtFixedRate1");
//        timer.scheduleAtFixedRate(myTimerTask, calendar.getTime(), 2000);

        /**
         * 2
         */

        myTimerTask.setName("scheduleAtFixedRate2");
        timer.scheduleAtFixedRate(myTimerTask, 2000, 1000);
    }
}
