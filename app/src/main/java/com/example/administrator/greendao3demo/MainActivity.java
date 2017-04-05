package com.example.administrator.greendao3demo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.greendao3demo.gen.UserDao;

import org.greenrobot.greendao.query.Query;

import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private  EditText etId;
    private  EditText etName;
    private  EditText etAge;
    private  EditText etPhone;
    private  Button btnAdd;
    private  Button btnDelete;
    private  Button btnUpdate;
    private  Button btnQuery;
    private Cursor cursor;
    private ListView lvList;
    private SimpleCursorAdapter adapter;
    private long tId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initListener();
        initAdapter();

    }

    private void initView() {
        etId = (EditText) findViewById(R.id.tv_id);
        etName = (EditText)findViewById(R.id.et_name);
        etAge = (EditText)findViewById(R.id.et_age);
        etPhone = (EditText)findViewById(R.id.et_phone);
        btnAdd = (Button)findViewById(R.id.btn_add);
        btnDelete = (Button)findViewById(R.id.btn_delete);
        btnUpdate = (Button)findViewById(R.id.btn_update);
        btnQuery = (Button)findViewById(R.id.btn_query);
        lvList = (ListView) findViewById(R.id.lv_list);
    }

    private void initData() {
        String orderBy=UserDao.Properties.Id.columnName+" DESC";
        cursor=getDb().query(getUserDao().getTablename()
                ,getUserDao().getAllColumns(),null,null,null,null,orderBy);
    }

    private void initListener() {
        btnAdd.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnQuery.setOnClickListener(this);
    }

    private void initAdapter() {
        String idColumn = UserDao.Properties.Id.columnName;
        String nameColumn = UserDao.Properties.Name.columnName;
        String ageColumn = UserDao.Properties.Age.columnName;
        String phoneColumn = UserDao.Properties.Phone.columnName;

        String[] from = {idColumn,nameColumn,ageColumn,phoneColumn};
        int[] to = {R.id.item_tv_id,R.id.item_tv_name,R.id.item_tv_age,R.id.item_tv_phone};

        adapter = new SimpleCursorAdapter(this,R.layout.listview_item,cursor,from,to,0);
        lvList.setAdapter(adapter);


        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tId = cursor.getLong(0);

                etId.setText(id+"");

                etName.setText(cursor.getString(1));

                etAge.setText(cursor.getString(2)+"");

                etPhone.setText(cursor.getString(3)+"");
            }
        });

    }
    private UserDao getUserDao() {

        //通过BaseApplication类提供的getDaoSession()获取具体Dao

        return((BaseApplication)this.getApplicationContext()).getDaoSession().getUserDao();

    }

    private SQLiteDatabase getDb() {

        //通过BaseApplication类提供的getDb()获取具体db

        return((BaseApplication)this.getApplicationContext()).getDb();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add: {
                String name = etName.getText().toString().trim();
                String age = etAge.getText().toString().trim();
                String phone = etPhone.getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(this, "姓名不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                User user = new User(null, name, age, phone);
                getUserDao().insert(user);
                cursor.requery();
                adapter.notifyDataSetChanged();
            }
                break;
            case R.id.btn_delete:
                getUserDao().deleteByKey(tId);
                cursor.requery();
                adapter.notifyDataSetChanged();
                break;
            case R.id.btn_update: {
                String name = etName.getText().toString().trim();
                String age = etAge.getText().toString().trim();
                String phone = etPhone.getText().toString().trim();
                User user = new User(tId, name, age, phone);

                getUserDao().update(user);
                cursor.requery();
                adapter.notifyDataSetChanged();
            }
                break;
            case R.id.btn_query:{
                String name = etName.getText().toString().trim();
                if(TextUtils.isEmpty(name)){
                    Toast.makeText(this,"名字不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                Query<User> query=getUserDao().queryBuilder()
                        .where(UserDao.Properties.Name.eq(name))
                        .orderAsc(UserDao.Properties.Id)
                        .build();
                List<User> list = query.list();
                Toast.makeText(this,list.size()+"",Toast.LENGTH_SHORT).show();
            }
                break;
        }
    }
}
