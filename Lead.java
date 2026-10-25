public class Lead {
    private final String name;
    private final String email;
    private final int score;

    public Lead(String name, String email, int score) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty.");
        }
        if (score < 0 || score > 100) {
            throw new IllegalArgumentException("Score must be between 0 and 100.");
        }
        this.name = name.trim();
        this.email = email.trim();
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getScore() {
        return score;
    }

    public String calculatePriority() {
        if (score >= 80) {
            return "HOT";
        }
        if (score >= 50) {
            return "WARM";
        }
        return "COLD";
    }
}
