package com.example.mathewdunn.realmtest.data;

import io.realm.RealmObject;

/**
 * Created by mathewdunn on 28/02/2017.
 */

public class User extends RealmObject {

    String firstName, lastName;

    public User(){}

    public User(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
