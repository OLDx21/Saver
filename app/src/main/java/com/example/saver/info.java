package com.example.saver;

public class info {
   static String name;
    static String login;
   static String sec;

   static  String sec2;
   static  String mail;

    public static void setSec2(String sec2) {
        info.sec2 = sec2;
    }

    public static void setMail(String mail) {
        info.mail = mail;
    }

    public  static String getSec2() {
        return sec2;
    }

    public static String getMail() {
        return mail;
    }

    public void setNamee(String name) {
        this.name = name;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setSec(String sec) {
        this.sec = sec;
    }

    public static String getNamee() {
        return name;
    }

    public static String getLogin() {
        return login;
    }

    public static String getSec() {
        return sec;
    }
}
