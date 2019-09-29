package es.source.code.model;

import java.io.Serializable;

/**
 * Created by chenwuji on 2017/10/12.
 */


public class User implements Serializable {
    String userName;
    String password;
    Boolean oldUser;
    public String GetteruserName(){
        return this.userName;
    }
    public String Getterpassword(){
        return this.password;
    }
    public Boolean GetteroldUser(){
        return this.oldUser;
    }
    public void SetteruserName(String string){
        this.userName=string;
    }
    public void Setterpassword(String string){
        this.password=string;
    }
    public void SetteroldUser(Boolean boolean1){
        this.oldUser=boolean1;
    }

}

