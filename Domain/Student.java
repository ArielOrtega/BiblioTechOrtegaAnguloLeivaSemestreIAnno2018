
package Domain;

import java.io.Serializable;


public class Student implements Serializable{
    
    private static final long serialVersionUID=1L;
    private String name;
    private int entryYear;
    private String career;
    private String phoneNumber;
    private String id;

    public Student(String name, int entryYear, String career, String phoneNumber, String id) {
        this.name = name;
        this.entryYear = entryYear;
        this.career = career;
        this.phoneNumber = phoneNumber;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEntryYear() {
        return entryYear;
    }

    public void setEntryYear(int entryYear) {
        this.entryYear = entryYear;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    

    @Override
    public String toString() {
        return "Estudiante{" + "nombre=" + name + ", anno de ingreso=" + entryYear + ", carrera=" + career 
                + "\nprestamos previos=" + phoneNumber + ", carne=" + id + '}';
    }
    
    
}
