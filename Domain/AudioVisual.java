
package Domain;


public class AudioVisual extends Material{
    private String brand;

    public AudioVisual(String brand, String name, String signature, int availability, String description) {
        super(name, signature, availability, description);
        this.brand = brand;
    }
    
    //constructor vacio
    public AudioVisual(){
        super();
        this.brand = "";
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    

    @Override
    public String toString() {
        return super.toString() + "\nAudioVisual{" + "marco=" + brand + '}';
    }
    
    public int sizeInBytes(){
        return super.getName().length() * 2 + super.getSignature().length() * 2 + super.getDescription().length() * 2
                + 4 + this.getBrand().length() * 2;
    }
}
