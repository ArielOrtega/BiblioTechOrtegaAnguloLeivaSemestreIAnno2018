package Domain;

public class AudioVisual extends Material {

    //Atrivutos
    private String brand;

    //Constructor
    public AudioVisual(String brand, String name, String signature, int availability, String description) {
        super(name, signature, availability, description);
        this.brand = brand;
    }//fin constructor

    //Constructor vac'io
    public AudioVisual() {
        super();
        this.brand = "";
    }//fin

    //Setters and Getters
    public String getBrand() {
        return brand;
    }//fin

    public void setBrand(String brand) {
        this.brand = brand;
    }//fin

    //toString
    @Override
    public String toString() {
        return super.toString() + "\nAudioVisual{" + "marco=" + brand + '}';
    }//fin toString

    //Madici'on del tamanno de cada valor del atrivuto
    public int sizeInBytes() {
        return super.getName().length() * 2 + super.getSignature().length() * 2 + super.getDescription().length() * 2
                + 4 + this.getBrand().length() * 2;
    }//fin m'etodo
}//fin clase
