package com.mycompany.quickchat.partone;

import java.util.List;
import javax.swing.JOptionPane;

public class StoredMessages {
    public static void showMenu(List<Message> sent, List<Message> stored) {
        while (true) {
            String c = JOptionPane.showInputDialog(
                    "Stored Messages:\n1. Display Senders & Recipients\n2. Display Longest Message\n3. Search by Hash\n4. Search by Recipient\n5. Delete by Hash\n6. Read from JSON\n7. Back");
            if (c == null) return;
            int choice = Integer.parseInt(c);
            if (choice == 7) return;

            if (choice == 1) {
                StringBuilder sb = new StringBuilder();
                for (Message m : sent) sb.append("Sender: You | Recipient: ").append(m.getRecipient()).append("\n");
                for (Message m : stored) sb.append("Sender: You | Recipient: ").append(m.getRecipient()).append("\n");
                JOptionPane.showMessageDialog(null, sb.length() == 0 ? "No messages." : sb.toString());
            }
            if (choice == 2) {
                Message longest = null;
                for (Message m : sent) if (longest == null || m.getMessage().length() > longest.getMessage().length()) longest = m;
                for (Message m : stored) if (longest == null || m.getMessage().length() > longest.getMessage().length()) longest = m;
                JOptionPane.showMessageDialog(null, longest == null ? "No messages." : longest.printMessageDetails());
            }
            if (choice == 3) {
                String hash = JOptionPane.showInputDialog("Enter hash:");
                boolean found = false;
                for (Message m : sent) if (m.getMessageHash().equalsIgnoreCase(hash)) { JOptionPane.showMessageDialog(null, m.printMessageDetails()); found = true; }
                for (Message m : stored) if (m.getMessageHash().equalsIgnoreCase(hash)) { JOptionPane.showMessageDialog(null, m.printMessageDetails()); found = true; }
                if (!found) JOptionPane.showMessageDialog(null, "Not found.");
            }
            if (choice == 4) {
                String rec = JOptionPane.showInputDialog("Enter recipient:");
                StringBuilder sb = new StringBuilder();
                for (Message m : sent) if (m.getRecipient().equals(rec)) sb.append(m.printMessageDetails()).append("\n\n");
                for (Message m : stored) if (m.getRecipient().equals(rec)) sb.append(m.printMessageDetails()).append("\n\n");
                JOptionPane.showMessageDialog(null, sb.length() == 0 ? "Not found." : sb.toString());
            }
            if (choice == 5) {
                String hash = JOptionPane.showInputDialog("Enter hash to delete:");
                boolean removed = sent.removeIf(m -> m.getMessageHash().equalsIgnoreCase(hash));
                removed = stored.removeIf(m -> m.getMessageHash().equalsIgnoreCase(hash)) || removed;
                JOptionPane.showMessageDialog(null, removed ? "Message deleted." : "Not found.");
            }
            if (choice == 6) {
                JOptionPane.showMessageDialog(null, "Messages are stored in storedMessages.json in your project folder.");
            }
        }
    }
} 
