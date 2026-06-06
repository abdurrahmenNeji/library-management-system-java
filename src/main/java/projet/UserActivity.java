package projet;

public class UserActivity {
    private int userId;
    private String username;
    private int totalBorrowed;
    private int totalDownloaded;

    // Constructor
    public UserActivity(int userId, String username, int totalBorrowed, int totalDownloaded) {
        this.userId = userId;
        this.username = username;
        this.totalBorrowed = totalBorrowed;
        this.totalDownloaded = totalDownloaded;
    }

    // Getters
    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public int getTotalBorrowed() {
        return totalBorrowed;
    }

    public int getTotalDownloaded() {
        return totalDownloaded;
    }

    // Method to calculate total activity
    public int getTotalActivity() {
        return totalBorrowed + totalDownloaded;
    }
}
