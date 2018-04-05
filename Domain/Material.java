
package Domain;


public class Material {
    private String name;
    private String signature;
    private int availability;
    private String description;

    public Material(String name, String signature, int availability, String description) {
        this.name = name;
        this.signature = signature;
        this.availability = availability;
        this.description = description;
    }
    
    public Material(){
        this.name = "";
        this.signature = "";
        this.availability = 0;
        this.description = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public int getAvailability() {
        return availability;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Material{" + "nombre=" + name + ", signatura=" + signature + ", disponibilidad=" + availability 
                + "\ndescripcion=" + description + '}';
    }
    
   
}
