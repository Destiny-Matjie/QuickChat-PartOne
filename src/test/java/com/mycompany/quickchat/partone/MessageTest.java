package com.mycompany.quickchat.partone;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {


    @Test
    public void testMessageLengthSuccess() {
        Message m = new Message(1, "+27718693002", "Hi Mike, can you join us for dinner tonight");
        assertEquals("Message ready to send.", m.checkMessageLength());
    }

    @Test
    public void testMessageLengthFailure() {
        String longMsg = "a".repeat(251);
        Message m = new Message(1, "+27718693002", longMsg);
        assertEquals("Message exceeds 250 characters by 1, please reduce the size.", m.checkMessageLength());
    }

    @Test
    public void testRecipientCellSuccess() {
        Message m = new Message(1, "+27718693002", "Hi Mike");
        assertEquals("Cell phone number successfully captured.", m.checkRecipientStatus());
        assertEquals(1, m.checkRecipientCell());
    }

    @Test
    public void testRecipientCellFailure() {
        Message m = new Message(1, "08575975889", "Hi Mike");
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.", m.checkRecipientStatus());
        assertEquals(0, m.checkRecipientCell());
    }

    @Test
    public void testMessageHash() {
        Message m = new Message(0, "+27718693002", "Hi Mike, can you join us for dinner tonight");
        String hash = m.createMessageHash();
        assertTrue(hash.endsWith(":0:HITONIGHT"), "Hash should end with :0:HITONIGHT but was " + hash);
    }

    @Test
    public void testSentMessageOptions() {
        Message m = new Message(1, "+27718693002", "Hi");
        assertEquals("Message successfully sent.", m.getSendOption(0));
        assertEquals("Press 0 to delete message.", m.getSendOption(1));
        assertEquals("Message successfully stored.", m.getSendOption(2));
    }

 
    @Test
    public void testMessage2Data() {
        String[] messages = {
            "Hi Mike, can you join us for dinner tonight",
            "Hi Keegan, did you receive the payment?"
        };
        String[] recipients = {"+27718693002", "08575975889"};

        for (int i = 0; i < messages.length; i++) {
            Message m = new Message(i, recipients[i], messages[i]);
            if (i == 0) {
                assertEquals(1, m.checkRecipientCell());
                assertEquals("Message ready to send.", m.checkMessageLength());
            } else {
                assertEquals(0, m.checkRecipientCell());
                assertEquals("Press 0 to delete message.", m.getSendOption(1));
            }
        }
    }
}
