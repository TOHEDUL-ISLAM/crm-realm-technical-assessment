import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LeadTracker {
    private static final int SECTION_WIDTH = 56;

    public static void main(String[] args) {
        List<Lead> leads = new ArrayList<>();

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                showMenu(leads.size());
                String choice = readInput(scanner, "  Choose an option [1-3]: ");
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
                        System.out.println("\n  Goodbye!");
                        return;
                    default:
                        System.out.println("  [!] Invalid option. Please enter 1, 2, or 3.");
                }
            }
        }
    }

    private static void showMenu(int leadCount) {
        printHeading("SIMPLE LEAD TRACKER");
        System.out.printf("  Leads in this session: %d%n%n", leadCount);
        System.out.println("  [1] Add a lead");
        System.out.println("  [2] View all leads");
        System.out.println("  [3] Exit\n");
    }

    private static void printHeading(String title) {
        System.out.println("\n" + repeat('=', SECTION_WIDTH));
        System.out.println("  " + title);
        System.out.println(repeat('=', SECTION_WIDTH));
    }

    private static String repeat(char character, int count) {
        StringBuilder result = new StringBuilder();
        for (int index = 0; index < count; index++) {
            result.append(character);
        }
        return result.toString();
    }

    private static String readInput(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.hasNextLine() ? scanner.nextLine() : null;
    }

    private static String readLeadInput(Scanner scanner, String prompt) {
        String input = readInput(scanner, prompt);
        if (input != null && "/cancel".equalsIgnoreCase(input.trim())) {
            System.out.println("\n  [CANCELLED] Add cancelled. No lead was saved.");
            return null;
        }
        return input;
    }

    private static void addLead(Scanner scanner, List<Lead> leads) {
        printHeading("ADD A LEAD");
        System.out.println("  Type /cancel at any prompt to return without saving.\n");
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
        System.out.printf("%n  [OK] Lead added: %s | Score: %d | Priority: %s%n",
                lead.getName(), lead.getScore(), lead.calculatePriority());
    }

    private static String readName(Scanner scanner) {
        while (true) {
            String name = readLeadInput(scanner, "  Full name: ");
            if (name == null) {
                return null;
            }
            if (!name.trim().isEmpty()) {
                return name.trim();
            }
            System.out.println("  [!] Please enter a name. It cannot be empty.");
        }
    }

    private static String readEmail(Scanner scanner) {
        while (true) {
            String email = readLeadInput(scanner, "  Email (e.g. name@example.com): ");
            if (email == null) {
                return null;
            }
            try {
                Lead.validateEmail(email);
                return email.trim();
            } catch (IllegalArgumentException exception) {
                System.out.println("  [!] " + exception.getMessage() + " Please try again.");
            }
        }
    }

    private static Integer readScore(Scanner scanner) {
        while (true) {
            String input = readLeadInput(scanner, "  Score (whole number, 0-100): ");
            if (input == null) {
                return null;
            }
            try {
                int score = Integer.parseInt(input.trim());
                if (score >= 0 && score <= 100) {
                    return score;
                }
                System.out.println("  [!] Please enter a score between 0 and 100.");
            } catch (NumberFormatException exception) {
                System.out.println("  [!] Please enter a whole number between 0 and 100 (e.g. 80).");
            }
        }
    }

    private static void viewLeads(List<Lead> leads) {
        printHeading("ALL LEADS");
        System.out.printf("  Leads: %d%n%n", leads.size());
        if (leads.isEmpty()) {
            System.out.println("  No leads yet. Choose [1] Add a lead to get started.");
            return;
        }

        int numberWidth = Math.max("No.".length(), String.valueOf(leads.size()).length());
        int nameWidth = "Name".length();
        int emailWidth = "Email".length();
        for (Lead lead : leads) {
            nameWidth = Math.max(nameWidth, lead.getName().length());
            emailWidth = Math.max(emailWidth, lead.getEmail().length());
        }
        String rowFormat = "  | %" + numberWidth + "s | %-" + nameWidth
                + "s | %-" + emailWidth + "s | %5s | %-8s |%n";
        String border = "  +-" + repeat('-', numberWidth) + "-+-" + repeat('-', nameWidth)
                + "-+-" + repeat('-', emailWidth) + "-+-------+----------+";
        System.out.println(border);
        System.out.printf(rowFormat, "No.", "Name", "Email", "Score", "Priority");
        System.out.println(border);
        int number = 1;
        for (Lead lead : leads) {
            System.out.printf(rowFormat, number++,
                    lead.getName(), lead.getEmail(), lead.getScore(), lead.calculatePriority());
        }
        System.out.println(border);
    }
}
