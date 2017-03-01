package com.example.mathewdunn.realmtest.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.mathewdunn.realmtest.R;
import com.example.mathewdunn.realmtest.data.User;
import com.example.mathewdunn.realmtest.utils.BaseActivity;

import io.realm.Realm;
import io.realm.RealmResults;

public class SecondActivity extends BaseActivity {

    private Realm realm;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Realm.init(this);

        realm = Realm.getDefaultInstance();

        getUser();
    }

    private void getUser(){
        RealmResults<User> realmUsers = realm.where(User.class).findAll();
        user = realmUsers.first();
        Log.d("Test", user.getFirstName() + " " + user.getLastName() + " Null Maybz");
    }
}
