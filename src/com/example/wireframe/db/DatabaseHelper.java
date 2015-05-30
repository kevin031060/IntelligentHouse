package com.example.wireframe.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by likaiwen on 15/4/29.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int VERSON = 1;//默认的数据库版本

    private static final String DB_NAME = "test2.db";
    //继承SQLiteOpenHelper类的类必须有自己的构造函数
    //该构造函数4个参数，直接调用父类的构造函数。其中第一个参数为该类本身；第二个参数为数据库的名字；
    //第3个参数是用来设置游标对象的，这里一般设置为null；参数四是数据库的版本号。
    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int verson) {
        super(context, name, factory, verson);
    }

    //该构造函数有3个参数，因为它把上面函数的第3个参数固定为null了
    public DatabaseHelper(Context context, String name, int verson) {
        this(context, name, null, verson);
    }

    //该构造函数只有2个参数，在上面函数 的基础山将版本号固定了
    public DatabaseHelper(Context context, String name) {
        this(context, name, VERSON);
    }


    public DatabaseHelper(Context context) {
        this(context, DB_NAME, VERSON);
    }

    //该函数在数据库第一次被建立时调用
    @Override
    public void onCreate(SQLiteDatabase arg0) {
        // TODO Auto-generated method stub
        System.out.println("create a sqlite database");
        //execSQL()为执行参数里面的SQL语句，因此参数中的语句需要符合SQL语法,这里是创建一个表
        arg0.execSQL("create table if not exists Energy(id integer primary key autoincrement, " +
                "data SMALLINT," +
                " month varchar(20)," +
                " day varchar(20), " +
                "time varchar(20))");
        arg0.execSQL("create table if not exists Devices(id integer primary key autoincrement, " +
                "name varchar(60))");
        System.out.println("ok");


    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub
        arg0.execSQL("drop table if exists Devices");
        arg0.execSQL("drop table if exists Energy");
        System.out.println("update a sqlite database");
    }
}