
package File;

import Domain.Student;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.util.ArrayList;
import java.util.List;


public class StudentFile {
    String path= "src//";
    String name= "studentFile.txt";
    File file= new File(path + name);
    
   
    public void serializeStudent(Student s) throws IOException, ClassNotFoundException {

        File studentFile = new File(path+ name);

        ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(path + name));
        output.writeUnshared(s);
        output.close();
    }
    
    //M'etodo para serializar la lista de estudiantes
    public void serializeList(Student student) throws IOException, ClassNotFoundException {

        File studentFile = new File(path+ name);
        List<Student> studentArray = new ArrayList<Student>();

        //Validaci'on
        if (studentFile.exists()) {
            ObjectInputStream objectInput = new ObjectInputStream(new FileInputStream(path + name));
            Object aux = objectInput.readObject();
 
            studentArray = (List<Student>) aux;
            objectInput.close();
        }

        studentArray.add(student);

        ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(path + name));
        output.writeUnshared(studentArray);

        output.close();
    }//Fin del m'etodo
    
    //M'etodo para leer la lista de estudiantes (lista que ha sido serializada)
    public List<Student> readList() throws IOException, ClassNotFoundException {

        File studenFile = new File(path + name);
        List<Student> studentArray = new ArrayList<Student>();

        //Validaci'on
        if (studenFile.exists()) {
            ObjectInputStream objectInput = new ObjectInputStream(new FileInputStream(path + name));
            Object aux = objectInput.readObject();

            studentArray = (List<Student>) aux;
            objectInput.close();
        }
        
        return studentArray;
    }//Fin del m'etodo
    
    //Retorna la informaci'on de estudiante
    public String studentInfo(String idStudent) throws FileNotFoundException, ClassNotFoundException, OptionalDataException, IOException {
        String info= "";
        
        for (int i = 0; i < readList().size(); i++) {
            if (readList().get(i).getId().equalsIgnoreCase(idStudent)) {
                info= "Student Data:\n\nName: "+ readList().get(i).getName()
                        +"\nEntry year: "+ readList().get(i).getEntryYear()
                        +"\nCareer: "+ readList().get(i).getCareer()
                        +"\nPhone Number: "+ readList().get(i).getPhoneNumber();
            }
        }//for
        return info;
    }//Fin del m'etodo

    public String getStudentId(String career, String year, int number) {
        String id= String.valueOf(career.charAt(0)
            +String.valueOf(year.charAt(3))
            +String.format("%03d", number));
    
    return id;        
    }

    public boolean checkStudentRecord(String idStudent) throws IOException, ClassNotFoundException {
        
        for (int i = 0; i < readList().size(); i++) {
            if (readList().get(i).getId().equalsIgnoreCase(idStudent)) {
                return true;
            }
        }//for
        return false;        
    }
    
    
    public String[] autocompleteOptions() throws IOException, ClassNotFoundException{
        
        String[] options= new String[readList().size()];
        
        for (int i = 0; i < readList().size() ; i++) {
            options[i]= readList().get(i).getId();
        }
        
        return options;
    }       

}//Fin de la clase  

