package com.ivali.myapplicationapp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.ivali.myapplicationapp.dbmanager.DBManager;
import com.ivali.myapplicationapp.dbmanager.Person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DBShowActivity extends ActionBarActivity {

    private DBManager dm;
    private ListView lv;

    private Button add;
    private Button query;
    private Button update;
    private Button delete;
    private Button deleteall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);
        lv = (ListView)findViewById(R.id.lv_00);
        query = (Button)findViewById(R.id.query00);
        add = (Button)findViewById(R.id.add00);
        update = (Button)findViewById(R.id.update00);
        deleteall = (Button)findViewById(R.id.deleteAll00);
        delete = (Button)findViewById(R.id.delete00);
        dm = new DBManager(this);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add(v);
            }
        });

        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                query(v);
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update(v);
            }
        });

        deleteall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteALL(v);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(v);
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dm.closeDB();
    }

    public void add(View view){
        List<Person> persons = new ArrayList<>();
        Person p1 = new Person("tom",21,"lively boy");
        Person p2 = new Person("bill",23,"handsome");
        Person p3 = new Person("gate",22,"sexy boy");
        Person p4 = new Person("joe",24,"hot boy");
        Person p5 = new Person("jhon",29,"pretty");

        persons.add(p1);
        persons.add(p2);
        persons.add(p3);
        persons.add(p4);
        persons.add(p5);

        dm.add(persons);
    }
    public void update(View view){
        Person p = new Person();
        p.setName("jhon");
        p.setAge(40);
        dm.updateAge(p);
    }

    public void deleteALL(View view){
        dm.delete();
    }

    public void delete(View view){
        Person p = new Person();
        p.setAge(40);
        dm.deleteOldPerson(p);
    }



    public void query(View view){
        List<Person>persons    = dm.findAllPerson();
        ArrayList<Map<String,String>> list = new ArrayList<>();
        for (Person p:persons){
            HashMap<String,String> map = new HashMap<>();
            map.put("name",p.getName());
            map.put("info",p.getAge()+" "+p.getInfo());
            list.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(this,list,android.R.layout.simple_list_item_2,
                new String[]{"name","info"},new int[]{android.R.id.text1,android.R.id.text2});
        lv.setAdapter(adapter);
        if(list.isEmpty()){
            Toast.makeText(this, "列表里还没人呢", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dbshow, menu);
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
