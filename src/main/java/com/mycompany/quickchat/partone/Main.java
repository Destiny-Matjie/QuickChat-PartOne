package com.mycompany.quickchat.partone;

import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("First Name: ");
        String fn = input.nextLine();
        System.out.print("Last Name: ");
        String ln = input.nextLine();
        Login login = new Login(fn, ln);

        System.out.print("Username: ");
        String u = input.nextLine();
        System.out.print("Password: ");
        String p = input.nextLine();
        System.out.print("Cell (+27...): ");
        String c = input.nextLine();

        String regResult = login.registerUser(u, p, c);
        System.out.println(regResult);
        if (!regResult.equals("User registered successfully.")) {
            input.close();
            return;
        }

        System.out.print("Login User: ");
        String lu = input.nextLine();
        System.out.print("Login Pass: ");
        String lp = input.nextLine();
        String loginStatus = login.returnLoginStatus(lu, lp);
        System.out.println(loginStatus);
        if (!loginStatus.contains("Welcome")) {
            input.close();
            return;
        }

        System.out.println("Welcome to QuickChat.");

        System.out.print("How many messages do you wish to send? ");
        int numMessages = Integer.parseInt(input.nextLine());

        ArrayList<Message> sentMessages = new ArrayList<>();
        int totalSent = 0;
        int messageCounter = 0;
        boolean keepRunning = true;

        while (keepRunning) {
            System.out.println("\n--- QUICKCHAT MENU ---");
            System.out.println("1. Send messages");
            System.out.println("2. Show recently sent messages");
            System.out.println("3. Quit");
            System.out.print("Choose an option: ");
            String choice = input.nextLine();

            switch (choice) {
                case "1":
                    if (messageCounter >= numMessages) {
                        System.out.println("You have reached your limit of " + numMessages + " messages.");
                        break;
                    }

                    System.out.print("Enter Recipient Cell Number (+27 followed by 9 digits): ");
                    String recipient = input.nextLine();

                    Message checkMsg = new Message(0, recipient, "test");
                    if (checkMsg.checkRecipientCell() == 0) {
                        System.out.println("Cell phone number is incorrectly formatted or does not contain international code.");
                        break;
                    }

                    System.out.print("Enter your message: ");
                    String content = input.nextLine();
                    if (content.length() > 250) {
                        int excess = content.length() - 250;
                        System.out.println("Message exceeds 250 characters by " + excess + ", please reduce the size.");
                        break;
                    }

                    messageCounter++;
                    Message msg = new Message(messageCounter, recipient, content);
                    System.out.println("\n" + msg.printMessage());

                    System.out.println("\nChoose an option for this message:");
                    System.out.println("1. Send message");
                    System.out.println("2. Disregard message");
                    System.out.println("3. Store message to send later");
                    System.out.print("Selection: ");
                    String action = input.nextLine();

                    if (action.equals("1")) {
                        System.out.println(msg.sentMessage());
                        sentMessages.add(msg);
                        totalSent++;
                    } else if (action.equals("2")) {
                        System.out.println("Press 0 to delete message.");
                        messageCounter--;
                    } else if (action.equals("3")) {
                        msg.storeMessage();
                        System.out.println("Message successfully stored.");
                        sentMessages.add(msg);
                        totalSent++;
                    } else {
                        System.out.println("Invalid option. Message not saved.");
                        messageCounter--;
                    }
                    break;

                case "2":
                    if (sentMessages.isEmpty()) {
                        System.out.println("No messages sent yet.");
                    } else {
                        System.out.println("\n--- RECENTLY SENT MESSAGES ---");
                        for (Message m : sentMessages) {
                            System.out.println(m.printMessages());
                            System.out.println("--------------------");
                        }
                    }
                    break;

                case "3":
                    System.out.println("Total messages sent: " + totalSent);
                    System.out.println("Exiting QuickChat. Goodbye!");
                    keepRunning = false;
                    break;

                default:
                    System.out.println("Invalid option. Please enter 1, 2, or 3.");
                    break;
            }
        }
        input.close();
    }
}

