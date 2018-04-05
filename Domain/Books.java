
package Domain;


public class Books extends Material{
    private String author;
    private String genre;
    private String language;
    
    
    public Books(String author, String genre, String language, String name, String signature,  int availability, String description) {
        super(name, signature, availability, description);
        this.author = author;
        this.genre = genre;
        this.language = language;
    }
    
    //contructor vacio
    public Books() {
        super();
        this.author = "";
        this.genre = "";
        this.language = "";
    }
    
    public String getAutor() {
        return author;
    }

    public void setAutor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }


    @Override
    public String toString() {
        return super.toString() + "\nLibros{" + "autor=" + author + ", genero=" + genre + ", idioma=" + language + '}';
    }

    public int sizeInBytes(){
        
        return super.getName().length() * 2 + super.getSignature().length() * 2 + super.getDescription().length() * 2
                + 4 + this.author.length() * 2 + this.genre.length() * 2 + this.language.length() * 2;
    }

}
