package com.example.homework3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button bind,unbind,getServiceStatus;
    //保持启动的Service的IBinder对象
    BindService.MyBinder binder;
    //定义一个ServiceConnection对象
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            System.out.println("Service is connected!");
            binder=(BindService.MyBinder)iBinder;
        }
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            System.out.println("Service is Disconnected!");
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取按钮
        bind=(Button)findViewById(R.id.bind);
        unbind=(Button)findViewById(R.id.unbind);
        getServiceStatus=(Button)findViewById(R.id.getServiceStatus);
        //创建启动Service的Intent
        final Intent intent = new Intent();
        //为Intent设置Action属性
        intent.setAction("com.example.service.BINDSERVICE");
        intent.setPackage(this.getPackageName());
        bind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //绑定到指定Service
                bindService(intent,conn, Service.BIND_AUTO_CREATE);
            }
        });
        unbind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unbindService(conn);
            }
        });
        getServiceStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"Service的count值为："+binder.getCount(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
