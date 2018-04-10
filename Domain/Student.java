package Domain;

import java.io.Serializable;

public class Student implements Serializable {

    //Atributos
    private static final long serialVersionUID = 1L;
    private String name;
    private int entryYear;
    private String career;
    private String phoneNumber;
    private String id;

    //Constructor
    public Student(String name, int entryYear, String career, String phoneNumber, String id) {
        this.name = name;
        this.entryYear = entryYear;
        this.career = career;
        this.phoneNumber = phoneNumber;
        this.id = id;
    }//fin constructor

    //Constructor vac'io
    public Student() {
        this.name = "";
        this.entryYear = 0;
        this.career = "";
        this.phoneNumber = "";
        this.id = "";
    }//fin constructor vac'io

    //Setters and Getters
    public String getName() {
        return name;
    }//fin 

    public void setName(String name) {
        this.name = name;
    }//fin 

    public int getEntryYear() {
        return entryYear;
    }//fin 

    public void setEntryYear(int entryYear) {
        this.entryYear = entryYear;
    }//fin 

    public String getCareer() {
        return career;
    }//fin 

    public void setCareer(String career) {
        this.career = career;
    }//fin 

    public String getPhoneNumber() {
        return phoneNumber;
    }//fin 

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }//fin 

    public String getId() {
        return id;
    }//fin 

    public void setId(String id) {
        this.id = id;
    }//fin 

    //toString
    @Override
    public String toString() {
        return "Estudiante{" + "nombre=" + name + ", anno de ingreso=" + entryYear + ", carrera=" + career
                + "\nprestamos previos=" + phoneNumber + ", carne=" + id + '}';
    }//fin toString

}//fin  clase
