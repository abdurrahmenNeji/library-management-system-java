package projet;

public class Author {
    private int id;
    private String nom;
    private String dateOfBirth; // New field for the author's date of birth

    public Author(int id, String nom, String dateOfBirth) {
        this.id = id;
        this.nom = nom;
        this.dateOfBirth = dateOfBirth;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
