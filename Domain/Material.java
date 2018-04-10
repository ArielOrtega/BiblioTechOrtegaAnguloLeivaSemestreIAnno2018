
package Domain;


public class Material {
    //Atributos
    private String name;
    private String signature;
    private int availability;
    private String description;

    //Constructor
    public Material(String name, String signature, int availability, String description) {
        this.name = name;
        this.signature = signature;
        this.availability = availability;
        this.description = description;
    }//fin constructor
    
    //Constructor vac'io
    public Material(){
        this.name = "";
        this.signature = "";
        this.availability = 0;
        this.description = "";
    }//fin constructor vac'io

    //Setters and Getters
    public String getName() {
        return name;
    }//fin

    public void setName(String name) {
        this.name = name;
    }//fin

    public String getSignature() {
        return signature;
    }//fin

    public void setSignature(String signature) {
        this.signature = signature;
    }//fin

    public int getAvailability() {
        return availability;
    }//fin

    public void setAvailability(int availability) {
        this.availability = availability;
    }//fin

    public String getDescription() {
        return description;
    }//fin

    public void setDescription(String description) {
        this.description = description;
    }//fin

    //toString
    @Override
    public String toString() {
        return "Material{" + "nombre=" + name + ", signatura=" + signature + ", disponibilidad=" + availability 
                + "\ndescripcion=" + description + '}';
    }//fin toString
    
}//Fin clase
