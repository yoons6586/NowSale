package com.example.yoonsung.nowsale;

public class ClientCouponData {
    String name;
    String content;
//    int imgId;
    int couponKey;
    public ClientCouponData(String name, String content/*,int imgId*/, int couponKey){
        this.name =name;
        this.content=content;
        this.couponKey=couponKey;
        //this.imgId=imgId;
    }



    public void setName(String name){
        this.name=name;
    }
    public void setContent(String content){
        this.content=content;
    }
    /*public void setImgId(int imgId){
        this.imgId=imgId;
    }*/
    public void setCouponKey(int couponKey){
        this.couponKey=couponKey;
    }
    public String getName(){
        return name;
    }
    public String getContent(){
        return content;
    }
//    public int getImgId(){
//        return imgId;
//    }
    public int getCouponKey(){return couponKey;}
}