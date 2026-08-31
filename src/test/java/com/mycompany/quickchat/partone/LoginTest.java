
package com.mycompany.quickchat.partone;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {
    Login login = new Login("Kyle","Smith");
    @Test public void testUserNameCorrect(){ assertTrue(login.checkUserName("kyl_1")); }
    @Test public void testUserNameIncorrect(){ assertFalse(login.checkUserName("kyle!!!!!!!")); }
    @Test public void testPasswordCorrect(){ assertTrue(login.checkPasswordComplexity("Ch&&sec@ke99!")); }
    @Test public void testPasswordIncorrect(){ assertFalse(login.checkPasswordComplexity("password")); }
    @Test public void testCellCorrect(){ assertTrue(login.checkCellPhoneNumber("+27838968976")); }
    @Test public void testCellIncorrect(){ assertFalse(login.checkCellPhoneNumber("08966553")); }
}