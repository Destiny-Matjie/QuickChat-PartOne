package com.mycompany.quickchat.partone;

import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;

public class Message {
    private String messageID;
    private int messageNumber;
    private String recipient;
    private String message;
    private String messageHash;

    public Message(int messageNumber, String recipient, String message) {
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.message = message;
        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();
    }

    private String generateMessageID() {
        Random rand = new Random();
        StringBuilder id = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            id.append(rand.nextInt(10));
        }
        return id.toString();
    }

    public int checkRecipientCell() {
        if (recipient!= null && recipient.matches("^\\+27[0-9]{9}$")) return 1;
        return 0;
    }

    public String checkRecipientStatus() {
        if (checkRecipientCell() == 1) return "Cell phone number successfully captured.";
        return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
    }

    public String checkMessageLength() {
        if (message.length() <= 250) return "Message ready to send.";
        int excess = message.length() - 250;
        return "Message exceeds 250 characters by " + excess + ", please reduce the size.";
    }

    public String createMessageHash() {
        if (message == null || message.length() > 250) {
            return "Message hash not created - message too long.";
        }
        String[] words = message.trim().split("\\s+");
        String firstWord = words[0].toUpperCase();
        String lastWord = words[words.length - 1].toUpperCase();
        return messageID.substring(0, 2) + ":" + messageNumber + ":" + firstWord + lastWord;
    }

    public String sentMessage() {
        return "Message successfully sent.";
    }

    public String getSendOption(int option) {
        if (option == 0) return "Message successfully sent.";
        if (option == 1) return "Press 0 to delete message.";
        if (option == 2) {
            storeMessage();
            return "Message successfully stored.";
        }
        return "";
    }

    public void storeMessage() {
        try (FileWriter writer = new FileWriter("storedMessages.json", true)) {
            writer.write("{\"messageID\":\"" + messageID + "\",\"messageNumber\":" + messageNumber
                    + ",\"recipient\":\"" + recipient + "\",\"message\":\"" + message.replace("\"", "'")
                    + "\",\"hash\":\"" + messageHash + "\"}\n");
        } catch (IOException e) {
            System.out.println("Error storing message: " + e.getMessage());
        }
    }

    public String printMessage() {
        return "MessageID: " + messageID + "\nMessage Hash: " + messageHash
                + "\nRecipient: " + recipient + "\nMessage: " + message;
    }

    public String printMessages() {
        return printMessage();
    }

    public String getMessageID() { return messageID; }
    public String getRecipient() { return recipient; }
    public String getMessage() { return message; }
    public String getMessageHash() { return messageHash; }
    public int getMessageNumber() { return messageNumber; }
}
