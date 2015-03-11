package com.ivali.myapplicationapp;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GetAppInfoActivity extends ActionBarActivity {
    /**
     * 获取当前手机的安装的所有App,并显示在ListView中
     */
    private List<Map<String, Object>> data;
    Map<String, Object> item;
    private ListView listView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_app_info);
        listView =(ListView)findViewById(R.id.listView);
        data = new ArrayList<Map<String, Object>>();
        listPackages();
        SimpleAdapter adapter = new SimpleAdapter(this, data,android.R.layout.simple_list_item_2, new String[] { "appname","pname" }, new int[] { android.R.id.text1,android.R.id.text2, });
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String packagename= (String) data.get(i).get("pname");
                Log.i("TTT","packagename:"+packagename);
                if(packagename!=null){
                      try {
                          PackageManager packageManager = listView.getContext().getPackageManager();
                          Intent intent = packageManager.getLaunchIntentForPackage(packagename);
                          //"jp.co.johospace.jorte"就是我们获得要启动应用的包名
                          startActivity(intent);
                      }catch (Exception e){
                          e.printStackTrace();
                          Toast.makeText(listView.getContext(),"这是一个系统应用",Toast.LENGTH_SHORT).show();
                      }
//                    if((((int)data.get(i).get("flag")) & ApplicationInfo.FLAG_SYSTEM) != 0){
//                        //系统应用
//                        Toast.makeText(listView.getContext(),"这是一个系统应用",Toast.LENGTH_SHORT).show();
//                    }else{
//                        PackageManager packageManager = listView.getContext().getPackageManager();
//                        Intent intent = packageManager.getLaunchIntentForPackage(packagename);
//                        //"jp.co.johospace.jorte"就是我们获得要启动应用的包名
//                        startActivity(intent);
//                    }
                }
            }
        });
    }

    class PInfo {
        private String appname = "";
        private String pname = "";
        private String versionName = "";
        private int versionCode = 0;
        private Drawable icon;
        private int flag;
        private void prettyPrint() {
            Log.i("taskmanger", appname + "\t" + pname + "\t" + versionName
                    + "\t" + versionCode + "\t"+flag + "\t");
        }
    }
    private void listPackages() {
        ArrayList<PInfo> apps = getInstalledApps(false);
        final int max = apps.size();
        for (int i = 0; i < max; i++) {
            apps.get(i).prettyPrint();
            item = new HashMap<String, Object>();
            item.put("appname", apps.get(i).appname);
            item.put("pname", apps.get(i).pname);
            item.put("flag",apps.get(i).flag);
            data.add(item);
        }
    }

    private ArrayList<PInfo> getInstalledApps(boolean getSysPackages) {
        ArrayList<PInfo> res = new ArrayList<PInfo>();
        List<PackageInfo> packs = getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packs.size(); i++) {
            PackageInfo p = packs.get(i);
            if ((!getSysPackages) && (p.versionName == null)) {
                continue;
            }
            PInfo newInfo = new PInfo();
            newInfo.appname = p.applicationInfo.loadLabel(getPackageManager())
                    .toString();
            newInfo.pname = p.packageName;
            newInfo.versionName = p.versionName;
            newInfo.versionCode = p.versionCode;
            newInfo.icon = p.applicationInfo.loadIcon(getPackageManager());
            newInfo.flag= p.applicationInfo.flags;
            res.add(newInfo);
        }
        return res;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_get_app_info, menu);
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
