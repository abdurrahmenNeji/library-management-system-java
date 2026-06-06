package projet;

import java.sql.Date;

public class BorrowedBook {
    private int id;
    private String title;
    private Date dateEmprunt;
    private Date dateRetour;

    public BorrowedBook(int id, String title, Date dateEmprunt, Date dateRetour) {
        this.id = id;
        this.title = title;
        this.dateEmprunt = dateEmprunt;
        this.dateRetour = dateRetour;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public Date getDateEmprunt() { return dateEmprunt; }
    public Date getDateRetour() { return dateRetour; }
}
