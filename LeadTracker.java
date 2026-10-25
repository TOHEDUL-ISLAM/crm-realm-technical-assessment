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

    private static String readLeadInput(Scanner scanner, String prompt) {
        String input = readInput(scanner, prompt);
        if (input != null && "/cancel".equalsIgnoreCase(input.trim())) {
            System.out.println("Add cancelled. No lead was saved.");
            return null;
        }
        return input;
    }

    private static void addLead(Scanner scanner, List<Lead> leads) {
        System.out.println("\nAdd a lead");
        System.out.println("Type /cancel at any prompt to return to the menu without saving.");
        String name = readName(scanner);
        if (name == null) {
            return;
        }
        String email = readEmail(scanner);
        if (email == null) {
            return;
        }
        Integer score = readScore(scanner);
        if (score == null) {
            return;
        }

        Lead lead = new Lead(name, email, score);
        leads.add(lead);
        System.out.printf("%nLead added: %s | Score: %d | Priority: %s%n",
                lead.getName(), lead.getScore(), lead.calculatePriority());
    }

    private static String readName(Scanner scanner) {
        while (true) {
            String name = readLeadInput(scanner, "Full name: ");
            if (name == null) {
                return null;
            }
            if (!name.trim().isEmpty()) {
                return name.trim();
            }
            System.out.println("Please enter a name. It cannot be empty.");
        }
    }

    private static String readEmail(Scanner scanner) {
        while (true) {
            String email = readLeadInput(scanner, "Email (e.g. name@example.com): ");
            if (email == null) {
                return null;
            }
            try {
                Lead.validateEmail(email);
                return email.trim();
            } catch (IllegalArgumentException exception) {
                System.out.println(exception.getMessage() + " Please try again.");
            }
        }
    }

    private static Integer readScore(Scanner scanner) {
        while (true) {
            String input = readLeadInput(scanner, "Score (whole number, 0-100): ");
            if (input == null) {
                return null;
            }
            try {
                int score = Integer.parseInt(input.trim());
                if (score >= 0 && score <= 100) {
                    return score;
                }
                System.out.println("Please enter a score between 0 and 100.");
            } catch (NumberFormatException exception) {
                System.out.println("Please enter a whole number between 0 and 100 (e.g. 80).");
            }
        }
    }

    private static void viewLeads(List<Lead> leads) {
        System.out.printf("%nLeads: %d%n", leads.size());
        if (leads.isEmpty()) {
            System.out.println("No leads yet. Choose option 1 to add a lead.");
            return;
        }

        int nameWidth = "Name".length();
        int emailWidth = "Email".length();
        for (Lead lead : leads) {
            nameWidth = Math.max(nameWidth, lead.getName().length());
            emailWidth = Math.max(emailWidth, lead.getEmail().length());
        }
        String rowFormat = "%-4s | %-" + nameWidth + "s | %-" + emailWidth + "s | %-5s | %s%n";
        System.out.printf(rowFormat, "No.", "Name", "Email", "Score", "Priority");
        int number = 1;
        for (Lead lead : leads) {
            System.out.printf(rowFormat, number++,
                    lead.getName(), lead.getEmail(), lead.getScore(), lead.calculatePriority());
        }
    }
}
