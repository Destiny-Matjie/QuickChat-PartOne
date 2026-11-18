package com.mycompany.quickchat.partone;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Random;
import javax.swing.JOptionPane;

public class Message {

    private String messageID;
    private String messageHash;
    private String recipient;
    private String message;
    private String flag;
    private int messageNumber;
    private static int totalMessages = 0;

    public Message() {
    }

    public Message(int num, String recipient, String message) {
        this.messageNumber = num;
        this.recipient = recipient;
        this.message = message;
        this.messageID = createMessageID();
        this.messageHash = createMessageHash(num, message);
    }

    // ===== FIXED: Remove punctuation - lecturer feedback =====
    public String cleanMessage(String msg) {
        if (msg == null) {
            return "";
        }
        return msg.replaceAll("[^a-zA-Z0-9 ]", "").trim().replaceAll("\\s+", " ");
    }

    // ===== FIXED: Build ID with string manipulation - 10 digits, no colons =====
    public String createMessageID() {
        Random rand = new Random();
        StringBuilder id = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            id.append(rand.nextInt(10));
        }
        this.messageID = id.toString();
        return this.messageID;
    }

    // ===== FIXED: Hash = FIRST 2 letters : number : first letter + last letter =====
    // Example: "Hi Keegan, did you receive the payment?" -> HI:0:HT
    public String createMessageHash(int msgNum, String message) {
        String clean = cleanMessage(message);
        String[] words = clean.split(" ");
        String firstWord = words[0];
        String lastWord = words[words.length - 1];

        String hash = firstWord.substring(0, 2) + ":" + msgNum + ":"
                + firstWord.substring(0, 1) + lastWord.substring(lastWord.length() - 1);
        this.messageHash = hash.toUpperCase();
        return this.messageHash;
    }

    // ===== Checks =====
    public boolean checkMessageID() {
        return messageID != null && messageID.length() == 10;
    }

    public String checkMessageLength() {
        if (message.length() <= 250) {
            return "Message ready to send.";
        } else {
            int over = message.length() - 250;
            return "Message exceeds 250 characters by " + over + ", please reduce the size.";
        }
    }

    public int checkMessageLength(String msg) {
        return msg.length();
    }

    // ===== FIXED: ONE version only - +27 and 12 chars total =====
    public int checkRecipientCell() {
        if (recipient != null && recipient.startsWith("+27") && recipient.length() == 12) {
            return 1;
        } else {
            return 0;
        }
    }

    public String checkRecipientStatus() {
        if (checkRecipientCell() == 1) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    // ===== FIXED: Takes choice 1,2,3 and returns message =====
    public String sentMessage(int choice) {
        if (choice == 1) {
            return "Message successfully sent.";
        }
        if (choice == 2) {
            return "Press 0 to delete message.";
        }
        if (choice == 3) {
            return "Message successfully stored.";
        }
        return "Invalid choice.";
    }

    // ===== FIXED: Show details AFTER sent =====
    public String printMessageDetails() {
        return "Message ID: " + messageID + "\n"
                + "Message Hash: " + messageHash + "\n"
                + "Recipient: " + recipient + "\n"
                + "Message: " + message + "\n"
                + "Flag: " + flag;
    }

    // ===== FIXED: Save as proper JSON ARRAY =====
    // Attribution: Adapted from Oracle Java Docs - Files.writeString
    // https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/nio/file/Files.html
    public static void saveMessagesToJSON(List<Message> messages, String filename) {
        try {
            StringBuilder json = new StringBuilder("[\n");
            for (int i = 0; i < messages.size(); i++) {
                Message m = messages.get(i);
                json.append("  {\"messageID\": \"").append(m.getMessageID()).append("\"")
                        .append(", \"hash\": \"").append(m.getMessageHash()).append("\"")
                        .append(", \"recipient\": \"").append(m.getRecipient()).append("\"")
                        .append(", \"message\": \"").append(m.getMessage().replace("\"", "'")).append("\"")
                        .append(", \"flag\": \"").append(m.getFlag()).append("\"}");
                if (i < messages.size() - 1) {
                    json.append(",");
                }
                json.append("\n");
            }
            json.append("]");
            Files.writeString(Path.of(filename), json.toString());
            JOptionPane.showMessageDialog(null, "Messages saved to " + filename);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving JSON: " + e.getMessage());
        }
    }

    public static int returnTotalMessages() {
        return totalMessages;
    }

    public static void incrementTotal() {
        totalMessages++;
    }

    // ===== Getters / Setters =====
    public String getMessageID() { return messageID; }
    public String getMessageHash() { return messageHash; }
    public String getRecipient() { return recipient; }
    public String getMessage() { return message; }
    public String getFlag() { return flag; }
    public int getMessageNumber() { return messageNumber; }

    public void setMessageID(String id) { this.messageID = id; }
    public void setMessageHash(String hash) { this.messageHash = hash; }
    public void setRecipient(String recipient) { this.recipient = recipient; }
    public void setMessage(String message) { this.message = message; }
    public void setFlag(String flag) { this.flag = flag; }
    public void setMessageNumber(int num) { this.messageNumber = num; }
} 
