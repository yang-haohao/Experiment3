package com.example.homework3;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class BindService extends Service {
    private int count;
    private boolean quit;
    //定义onBinder方法所返回的对象
    private MyBinder binder = new MyBinder();
    //通过继承Binder类来实现IBinder类
    public class MyBinder extends Binder{
        public int getCount(){
            return count;
            //获取Service运行状态：count
        }
    }
    //OnBind方法
    public IBinder onBind(Intent intent) {
        System.out.println("Service is Binded!");
        return binder;
    }
    //Service被创建时回调的方法
    public void onCreate(){
        super.onCreate();
        System.out.println("Service is Created!");
        //启动一条线程来计数，改变count
        new Thread(){
            public void run(){
                while (!quit){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) { }
                    count++;
                }
            }
        }.start();
    }
    //Service被断开时回调的方法
    public boolean onUnbind(Intent intent){
        System.out.println("Service is Unbinded!");
        return true;
    }
    //Service被关闭之前回调的方法
    public void onDestroy(){
        super.onDestroy();
        this.quit=true;
        System.out.println("Service is Destroy!");
    }
}
