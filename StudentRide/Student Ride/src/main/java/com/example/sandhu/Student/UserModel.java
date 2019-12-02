package com.example.sandhu.Student;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class UserModel {
    private String fname, lname, sid, uni,pass,email,phone,sign;
    byte[] _img;
    private  String creditcard, expiry;
    private int  pid;
    private int id;

public UserModel(){

}
//    public UserModel(String sign){
//this.sign = sign;
//    }
public UserModel(String fname, String lname, String sid, String uni, byte[] img){
    this.fname = fname;
    this.lname = lname;
    this.sid = sid;
    this.uni = uni;
    this._img = img;
}
    public UserModel(String fname, String lname, String sid, String uni,String pass, String email, String phone, byte[] img){
        this.fname = fname;
        this.lname = lname;
        this.sid = sid;
        this.uni = uni;
        this.pass = pass;
        this.email = email;
        this.phone = phone;
        this._img = img;
    }
    public UserModel(String pass){
        this.pass = pass;
    }
    public UserModel(int id, String fname, String lname, String sid, String uni, byte[] img){
    this.id = id;
    this.fname = fname;
        this.lname = lname;
        this.sid = sid;
        this.uni = uni;
        this._img = img;
    }
    public UserModel(String creditcard, String expiry){
    this.creditcard = creditcard;
    this.expiry = expiry;
    }
    public UserModel(int pid, String creditcard){
    this.pid = pid;
    this.creditcard = creditcard;
    }
    //public UserModel(String creditcard){
    //this.creditcard = creditcard;
    //}

    public UserModel(int pid, String creditcard, String expiry){
    this.pid = pid;
    this.creditcard = creditcard;
        this.expiry = expiry;
    }

    public String getCreditcard() {
        return creditcard;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setCreditcard(String creditcard) {
        this.creditcard = creditcard;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public void setUni(String uni) {
        this.uni = uni;
    }

    public String getUni() {
        return uni;
    }

    public String getFname(){
    return fname;
}

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }
    public byte[] getImage(){
        return this._img;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPass() {
        return pass;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public void setImage(byte[] b){
        this._img=b;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }


}
