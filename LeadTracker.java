import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LeadTracker {
    public static void main(String[] args) {
        List<Lead> leads = new ArrayList<>();

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                showMenu();
                String choice = readInput(scanner, "Choose an option: ");
                if (choice == null) {
                    return;
                }

                switch (choice.trim()) {
                    case "1":
                        addLead(scanner, leads);
                        break;
                    case "2":
                        viewLeads(leads);
                        break;
                    case "3":
                        System.out.println("Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid option. Please enter 1, 2, or 3.");
                }
            }
        }
    }

    private static void showMenu() {
        System.out.println("\nSimple Lead Tracker");
        System.out.println("1. Add a lead");
        System.out.println("2. View all leads");
        System.out.println("3. Exit");
    }

    private static String readInput(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.hasNextLine() ? scanner.nextLine() : null;
    }

    private static void addLead(Scanner scanner, List<Lead> leads) {
        String name = readInput(scanner, "Name: ");
        if (name == null) {
            return;
        }
        if (name.trim().isEmpty()) {
            System.out.println("Invalid input: Name cannot be empty.");
            return;
        }
        String email = readInput(scanner, "Email: ");
        if (email == null) {
            return;
        }
        if (email.trim().isEmpty()) {
            System.out.println("Invalid input: Email cannot be empty.");
            return;
        }
        String scoreInput = readInput(scanner, "Score (0-100): ");
        if (scoreInput == null) {
            return;
        }

        try {
            int score = Integer.parseInt(scoreInput.trim());
            Lead lead = new Lead(name, email, score);
            leads.add(lead);
            System.out.println("Lead added successfully.");
        } catch (NumberFormatException exception) {
            System.out.println("Invalid input: Score must be a whole number between 0 and 100.");
        } catch (IllegalArgumentException exception) {
            System.out.println("Invalid input: " + exception.getMessage());
        }
    }

    private static void viewLeads(List<Lead> leads) {
        if (leads.isEmpty()) {
            System.out.println("No leads yet. Choose option 1 to add a lead.");
            return;
        }

        for (Lead lead : leads) {
            System.out.printf("Name: %s | Email: %s | Score: %d | Priority: %s%n",
                    lead.getName(), lead.getEmail(), lead.getScore(), lead.calculatePriority());
        }
    }
}
