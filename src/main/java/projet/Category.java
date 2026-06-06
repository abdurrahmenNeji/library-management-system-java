package projet;

public class Category {
    private int id;
    private String nom; // Match the column name 'nom'

    public Category(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    // Getter for ID
    public int getId() {
        return id;
    }

    // Corrected Setter for ID
    public void setId(int id) { // Add parameter 'id'
        this.id = id;
    }

    // Getter for 'nom'
    public String getNom() {
        return nom;
    }

    // Setter for 'nom'
    public void setNom(String nom) {
        this.nom = nom;
    }
}
