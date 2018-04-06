
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
    
    public void serializeList(Student student) throws IOException, ClassNotFoundException {

        
        File studentFile = new File(path+ name);
        List<Student> studentArray = new ArrayList<Student>();

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
    }
    
    public List<Student> readList() throws IOException, ClassNotFoundException {

        File studenFile = new File(path + name);
        List<Student> studentArray = new ArrayList<Student>();

        
        if (studenFile.exists()) {
            ObjectInputStream objectInput = new ObjectInputStream(new FileInputStream(path + name));
            Object aux = objectInput.readObject();

            System.out.println(aux.toString());
            studentArray = (List<Student>) aux;
            objectInput.close();
        }
        
        return studentArray;
    }
    
    //Retorna la info de estudiante
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
    }    

}    
    
    

