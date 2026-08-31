package com.mycompany.quickchat.partone;

public class Login {
    private String firstName, lastName, username, password, cellNumber;
    public Login(String fn, String ln){ firstName=fn; lastName=ln; }
    public boolean checkUserName(String u){ return u.contains("_") && u.length()<=5; }
    public boolean checkPasswordComplexity(String p){ return p.length()>=8 && p.matches(".*[A-Z].*") && p.matches(".*[0-9].*") && p.matches(".*[!@#$%^&*].*"); }
    public boolean checkCellPhoneNumber(String c){ return c.matches("^\\+27[0-9]{9}$"); }
    public String registerUser(String u,String p,String c){
        if(!checkUserName(u)) return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        if(!checkPasswordComplexity(p)) return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        if(!checkCellPhoneNumber(c)) return "Cell phone number incorrectly formatted or does not contain international code.";
        username=u; password=p; cellNumber=c; return "User registered successfully.";
    }
    public boolean loginUser(String u,String p){ if(username==null) return false; return username.equals(u)&&password.equals(p); }
    public String returnLoginStatus(String u,String p){
        if(loginUser(u,p)) return "Welcome "+firstName+", "+lastName+" it is great to see you again.";
        else return "Username or password incorrect, please try again.";
    }
}