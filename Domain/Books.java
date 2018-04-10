
package Domain;


public class Books extends Material{
    //Atributos
    private String author;
    private String genre;
    private String language;
    private boolean digital;
    
    //Constructor
    public Books(String author, String genre, String language, boolean digital, String name, String signature,  int availability, String description) {
        super(name, signature, availability, description);
        this.author = author;
        this.genre = genre;
        this.language = language;
        this.digital = digital;
    }//fin constructor
    
    //Constructor vac'io
    public Books() {
        super();
        this.author = "";
        this.genre = "";
        this.language = "";
    }//fin constructor vac'io
    
    //Setters and Getters
    public String getAutor() {
        return author;
    }//fin

    public void setAutor(String author) {
        this.author = author;
    }//fin

    public String getGenre() {
        return genre;
    }//fin

    public void setGenre(String genre) {
        this.genre = genre;
    }//fin

    public String getLanguage() {
        return language;
    }//fin

    public void setLanguage(String language) {
        this.language = language;
    }//fin

    public boolean isDigital() {
        return digital;
    }

    public void setDigital(boolean digital) {
        this.digital = digital;
    }
    
    


    //toString
    @Override
    public String toString(){        
        return "Books{" + "author=" + author + ", genre=" + genre
                + ", language=" + language + ", digital=" + digital + '}';
    }

    //fin toString
    //Madici'on del tamanno de cada valor del atrivuto
    public int sizeInBytes() {        
        return super.getName().length() * 2 + super.getSignature().length() * 2 + super.getDescription().length() * 2
                + 4 + this.author.length() * 2 + this.genre.length() * 2 + this.language.length() * 2 + 1;
    } //fin m'etodo

}//Fin clase
