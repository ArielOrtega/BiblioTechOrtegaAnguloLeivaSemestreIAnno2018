
package Domain;

import java.io.Serializable;


public class Student implements Serializable{
    
    private static final long serialVersionUID=1L;
    private String name;
    private int entryYear;
    private String career;
    private String previousLoans;
    private String id;

    public Student(String name, int entryYear, String career, String previousLoans, String id) {
        this.name = name;
        this.entryYear = entryYear;
        this.career = career;
        this.previousLoans = previousLoans;
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

    public String getPreviousLoans() {
        return previousLoans;
    }

    public void setPreviousLoans(String previousLoans) {
        this.previousLoans = previousLoans;
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
                + "\nprestamos previos=" + previousLoans + ", carne=" + id + '}';
    }
    
    
}
