package com.ivali.myapplicationapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ivali.myapplicationapp.utils.DecriptTest;


public class MainActivity extends ActionBarActivity {

    private TextView resultTv,sha1result,sharesult;
    private EditText et_input;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultTv= (TextView) findViewById(R.id.textView3);
        sha1result= (TextView) findViewById(R.id.textView4);
        sharesult= (TextView) findViewById(R.id.textView5);
        et_input= (EditText) findViewById(R.id.editText);
    }

    public void jiaMiForMD5(){
        String  text=et_input.getText().toString().trim();
        if (text.length()<1){
            return;
        }
        String  result="";
        try{
            result= DecriptTest.MD5(text);
        }catch (Exception e){
            e.printStackTrace();
        }
        if (result.length()==0){
            return;
        }else{
            resultTv.setText(result);
        }
    }
    public void jiaMiForSHA1(){
        String  text=et_input.getText().toString().trim();
        if (text.length()<1){
            return;
        }
        String  result="";
        try{
            result= DecriptTest.SHA1(text);
        }catch (Exception e){
            e.printStackTrace();
        }
        if (result.length()==0){
            return;
        }else{
            sha1result.setText(result);
        }
    }
    public void jiaMiForSHA(){
        String  text=et_input.getText().toString().trim();
        if (text.length()<1){
            return;
        }
        String  result="";
        try{
            result= DecriptTest.SHA(text);
        }catch (Exception e){
            e.printStackTrace();
        }
        if (result.length()==0){
            return;
        }else{
            sharesult.setText(result);
        }
    }
    public  void onClick(View v){
        Intent intent=new Intent(MainActivity.this,Main.class);
        startActivity(intent);
    }
    public  void  onMD5(View view){
        jiaMiForMD5();
    }
    public  void  onSHA1(View view){
        jiaMiForSHA1();
    }
    public  void  onSHA(View view){
        jiaMiForSHA();
    }
    public  void  onGetApps(View v){
        Intent intent=new Intent(MainActivity.this,GetAppInfoActivity.class);
        startActivity(intent);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
