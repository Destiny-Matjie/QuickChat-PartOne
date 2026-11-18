package com.mycompany.quickchat.partone;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    @Test
    public void testMessage1Data() {
        Message m = new Message(0, "+27718693002", "Hi Keegan, did you receive the payment?");
        assertEquals(1, m.checkRecipientCell());
        assertEquals("Message ready to send.", m.checkMessageLength());
        assertEquals(10, m.getMessageID().length());
        assertTrue(m.checkMessageID());
        assertEquals("HI:0:HT", m.getMessageHash());
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
                assertEquals("Press 0 to delete message.", m.sentMessage(2));
            }
        }
    }

    @Test
    public void testSentMessageOptions() {
        Message m = new Message(1, "+27718693002", "Hi");
        assertEquals("Message successfully sent.", m.sentMessage(1));
        assertEquals("Press 0 to delete message.", m.sentMessage(2));
        assertEquals("Message successfully stored.", m.sentMessage(3));
    }

    @Test
    public void testMessageTooLong() {
        String longMessage = "a".repeat(251);
        Message m = new Message(0, "+27718693002", longMessage);
        assertTrue(m.checkMessageLength().contains("exceeds 250"));
    }

    @Test
    public void testRecipientCell() {
        Message good = new Message(0, "+27857597588", "Hi");
        assertEquals(1, good.checkRecipientCell());

        Message bad = new Message(0, "08575975889", "Hi");
        assertEquals(0, bad.checkRecipientCell());

        Message bad2 = new Message(0, "+2785759758", "Hi");
        assertEquals(0, bad2.checkRecipientCell());
    }

    @Test
    public void testHashLoop() {
        String[] messages = {
            "Hi Keegan, did you receive the payment?",
            "Where are you? You are late! I have asked you to be on time.",
            "Yohoooo, I am at your gate."
        };
        String[] expected = {"HI:0:HT", "WH:1:WE", "YO:2:YE"};
        for (int i = 0; i < messages.length; i++) {
            Message m = new Message(i, "+27831234567", messages[i]);
            assertEquals(expected[i], m.getMessageHash());
        }
    }

    @Test
    public void testMessageID() {
        Message m1 = new Message(0, "+27831234567", "Hi");
        Message m2 = new Message(1, "+27831234567", "Hi");
        assertEquals(10, m1.getMessageID().length());
        assertEquals(10, m2.getMessageID().length());
        assertFalse(m1.getMessageID().contains(":"));
    }

    @Test
    public void testHashRemovesPunctuation() {
        Message m = new Message(0, "+27831234567", "Hi Keegan, did you receive the payment?");
        assertEquals("HI:0:HT", m.getMessageHash());
    }
} 
