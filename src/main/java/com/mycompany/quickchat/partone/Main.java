package com.mycompany.quickchat.partone;
// Main.java handles flow - Scanner for console input
import java.util.Scanner;
public class Main {
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        System.out.print("First Name: "); 
        String fn=sc.nextLine();
        System.out.print("Last Name: "); 
        String ln=sc.nextLine();
        Login login=new Login(fn,ln);
        System.out.print("Username: "); 
        String u=sc.nextLine();
        System.out.print("Password: "); 
        String p=sc.nextLine();
        System.out.print("Cell (+27...): "); 
        String c=sc.nextLine();
        String m=login.registerUser(u,p,c); 
        System.out.println(m);
        if(m.equals("User registered successfully.")){
            System.out.print("Login User: "); 
            String lu=sc.nextLine();
            System.out.print("Login Pass: "); 
            String lp=sc.nextLine();
            System.out.println(login.returnLoginStatus(lu,lp));
        }
    }
}
