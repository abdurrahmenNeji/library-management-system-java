package projet;

public class Book {
    private int id;
    private String title;
    private String summary;
    private int year;
    private String category;
    private String authors;
    private String format;
    private String dateTelechargement; // Downloaded book date
    private String dateRetour;         // Borrowed book date
    private int categoryId;

    // Unified Constructor
    public Book(int id, String title, String summary, int year, String category, String authors, String format, String dateTelechargement, String dateRetour, int categoryId) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.year = year;
        this.category = category;
        this.authors = authors;
        this.format = format;
        this.dateTelechargement = dateTelechargement; // Set for downloaded books
        this.dateRetour = dateRetour;                 // Set for borrowed books
        this.categoryId = categoryId;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }

    public int getYear() {
        return year;
    }

    public String getCategory() {
        return category;
    }

    public String getAuthors() {
        return authors;
    }

    public String getFormat() {
        return format;
    }

    public String getDateTelechargement() {
        return dateTelechargement;
    }

    public String getDateRetour() {
        return dateRetour;
    }
    public int getCategoryId() {
        return categoryId; // Ensure categoryId is a field in the Book class
    }

}
