package com.hsd.desgin.visitor;

public class VisitorClient {


    public static void main(String[] args) {
        BigHuYouCompany bigHuYou= new BigHuYouCompany();
        //可以很轻松的更换Visitor，但是要求BigHuYouCompany的结构稳定
        System.out.println("-----------------启动社交APP项目--------------------");
        bigHuYou.startProject(new SocialApp());
        System.out.println("-----------------启动短视频APP项目--------------------");
        //bigHuYou.startProject(new LiveApp());
    }
}
