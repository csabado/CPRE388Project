package com.example.slangdictionary;

public class AdminAuth {
    final String admin1 = "cjsrocks2@gmail.com";
    final String admin2 = "swechha16@gmail.com";
    public String currentUser;
    public boolean isAdmin;

    public AdminAuth(String current, boolean isadmin) {
        currentUser = current;
        isAdmin = isadmin;
    }

    public void adminCheck(){
        if(currentUser.equals(admin1)){
            isAdmin = true;
        }else if(currentUser.equals(admin2)){
            isAdmin = true;
        }
        else{
            isAdmin = false;
        }
    }

//    public boolean getIsAdmin(){
//        return isAdmin;
//    }

}
