package com.mycompany.quickchat.partone;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class QuickChat {

    static List<Message> sentMessages = new ArrayList<>();
    static List<Message> storedMessages = new ArrayList<>();
    static List<Message> disregardedMessages = new ArrayList<>();

    public static void main(String[] args) {
        String firstName = JOptionPane.showInputDialog("Enter first name:");
        String lastName = JOptionPane.showInputDialog("Enter last name:");
        Login login = new Login(firstName, lastName);
        String username = JOptionPane.showInputDialog("Enter username:");
        String password = JOptionPane.showInputDialog("Enter password:");
        boolean loggedIn = login.loginUser(username, password);
        if (!loggedIn) {
            JOptionPane.showMessageDialog(null, login.returnLoginStatus(username, password));
            return;
        }
        JOptionPane.showMessageDialog(null, login.returnLoginStatus(username, password));
        JOptionPane.showMessageDialog(null, "Welcome to QuickChat.");

        while (true) {
            String choiceStr = JOptionPane.showInputDialog(
                    "Choose an option:\n1. Send Messages\n2. Show Recently Sent Messages\n3. Quit\n4. Stored Messages");
            if (choiceStr == null) {
                break;
            }
            int choice = Integer.parseInt(choiceStr);

            if (choice == 1) {
                sendMessages();
            } else if (choice == 2) {
                if (sentMessages.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No messages sent yet.");
                } else {
                    StringBuilder sb = new StringBuilder("Recently Sent:\n");
                    for (Message m : sentMessages) {
                        sb.append(m.printMessageDetails()).append("\n\n");
                    }
                    JOptionPane.showMessageDialog(null, sb.toString());
                }
            } else if (choice == 3) {
                JOptionPane.showMessageDialog(null, "Total messages sent: " + Message.returnTotalMessages());
                break;
            } else if (choice == 4) {
                StoredMessages.showMenu(sentMessages, storedMessages);
            }
        }
    }

    public static void sendMessages() {
        int numToSend = Integer.parseInt(JOptionPane.showInputDialog("How many messages do you want to send?"));

        for (int i = 0; i < numToSend; i++) {
            String recipient;
            while (true) {
                recipient = JOptionPane.showInputDialog("Enter recipient (+27xxxxxxxxx):");
                Message temp = new Message();
                temp.setRecipient(recipient);
                if (temp.checkRecipientCell() == 1) {
                    break;
                } else {
                    JOptionPane.showMessageDialog(null, temp.checkRecipientStatus());
                }
            }

            String msgText;
            while (true) {
                msgText = JOptionPane.showInputDialog("Enter message " + (i + 1) + ":");
                Message temp = new Message();
                temp.setMessage(msgText);
                if (temp.checkMessageLength().equals("Message ready to send.")) {
                    break;
                } else {
                    JOptionPane.showMessageDialog(null, temp.checkMessageLength());
                }
            }

            Message msg = new Message(i, recipient, msgText);

            int sendChoice = Integer.parseInt(JOptionPane.showInputDialog(
                    "Choose:\n1. Send\n2. Disregard\n3. Store"));
            String result = msg.sentMessage(sendChoice);
            msg.setFlag(result);

            if (sendChoice == 1) {
                sentMessages.add(msg);
                Message.incrementTotal();
                JOptionPane.showMessageDialog(null, msg.printMessageDetails() + "\n" + result);
            } else if (sendChoice == 2) {
                disregardedMessages.add(msg);
                JOptionPane.showMessageDialog(null, result);
            } else if (sendChoice == 3) {
                storedMessages.add(msg);
                Message.saveMessagesToJSON(storedMessages, "storedMessages.json");
                JOptionPane.showMessageDialog(null, msg.printMessageDetails() + "\n" + result);
            }
        }
    }
} 