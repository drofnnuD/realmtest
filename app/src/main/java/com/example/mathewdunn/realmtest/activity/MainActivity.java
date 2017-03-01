package com.example.mathewdunn.realmtest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import com.example.mathewdunn.realmtest.R;
import com.example.mathewdunn.realmtest.data.User;
import com.example.mathewdunn.realmtest.utils.BaseActivity;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends BaseActivity {

    private TextView txt_hello_world;
    private User user;

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Realm.init(this);

        getTextFields();
        saveSomethingInRealm();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this, SecondActivity.class));
            }
        }, 3000);
    }

    private void getTextFields(){
        txt_hello_world = (TextView)findViewById(R.id.txt_hello_world);
    }

    private void saveSomethingInRealm(){
        realm = Realm.getDefaultInstance();
        user = new User("Matt", "Dunn");
        realm.beginTransaction();
        realm.copyToRealm(user);
        realm.commitTransaction();

    }

    private void getSomethingOutOfRealm(){
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmResults<User> users = realm.where(User.class).findAll();
        Log.d("test", users.get(0).getFirstName() + " " + users.get(0).getLastName());
        realm.commitTransaction();
    }

}
