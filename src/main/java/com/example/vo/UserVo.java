package com.example.vo;

import java.io.Serializable;

/**
 * Created by think on 2020/4/7.
 */
public class UserVo implements Serializable{

    private String username;

    private String password;

    private long id;

    private String dbflag;

    private String repassword;

    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }


    public String getDbflag() {
        return dbflag;
    }


    public void setDbflag(String dbflag) {
        this.dbflag = dbflag;
    }


    public String getRepassword() {
        return repassword;
    }


    public void setRepassword(String repassword) {
        this.repassword = repassword;
    }




}
