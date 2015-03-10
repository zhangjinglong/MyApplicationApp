package com.ivali.myapplicationapp;

import java.io.File;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Context;
import android.database.ContentObserver;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends Activity implements OnClickListener{
	 private Button bt_start;  
     private Button bt_stop;  
     private Button bt_redownload;  
     private EditText et_url_value;  
     private ProgressBar pb_progress;  
     private TextView tv_progress;  
     private HttpHandler handler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	       bt_start = (Button) findViewById(R.id.bt_start);  
	       bt_stop = (Button) findViewById(R.id.bt_stop);
	       bt_redownload = (Button) findViewById(R.id.bt_continue);
	       et_url_value = (EditText) findViewById(R.id.et_url_value);  
	       pb_progress = (ProgressBar) findViewById(R.id.pb_progress);  
	       tv_progress = (TextView)findViewById(R.id.tv_progress);  
	       bt_start.setOnClickListener(this);  
	       bt_stop.setOnClickListener(this);  
	       bt_redownload.setOnClickListener(this);
	    }  


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == bt_start){  
			String path = et_url_value.getText().toString().trim();

            if (TextUtils.isEmpty(path)) {
                    Toast.makeText(this, "路径不能为空", Toast.LENGTH_SHORT).show();
                    return;
            } else {

                    HttpUtils http = new HttpUtils();
                    Log.e(getClass().toString(),"path:"+path);
                    handler = http.download(path, "/sdcard/newAPK.apk", true, // 如果目标文件存在，接着未完成的部分继续下载。服务器不支持RANGE时将从新下载。
                                    true, // 如果从请求返回信息中获取到文件名，下载完成后自动重命名。
                                    new RequestCallBack<File>() {

                                            @Override
                                            public void onStart() {
                                            	tv_progress.setText("conn...");
                                            }

                                            @Override
                                            public void onLoading(long total, long current,
                                                            boolean isUploading) {
                                            	int progress = (int) ((current / (double) total) * 100);
                                            	tv_progress.setText(progress + "%");
                                            	pb_progress.setMax((int)total);
                                            	pb_progress.setProgress((int)current);
                                            }
                                            
                                            @Override
                                            public void onFailure(HttpException error, String msg) {
                                            	tv_progress.setText(msg);
                                            }

											@Override
											public void onSuccess(
													ResponseInfo<File> responseInfo) {
												// TODO Auto-generated method stub
												tv_progress.setText("下载完毕:"
                                                        + responseInfo.result.getPath());
											}

                                    });

            }

      }else if(v == bt_stop){  
    	  if(handler!=null){
    		  //handler.pause();
    		  handler.cancel();//可以用
    	  }
    	  
    	  
      } else if(v == bt_redownload){ 
    	  if(handler!=null){
    		  handler.resume();
    	  }
    	  
      }   
		
	}

}
