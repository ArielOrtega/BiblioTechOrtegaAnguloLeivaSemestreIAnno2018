
package File;

import Domain.Student;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
    
    public void serializeList(Student person_) throws IOException, ClassNotFoundException {

        
        File studentFile = new File(path+ name);
        List<Student> studentArray = new ArrayList<Student>();

        if (studentFile.exists()) {
            ObjectInputStream objectInput = new ObjectInputStream(new FileInputStream(path + name));
            Object aux = objectInput.readObject();
 
            studentArray = (List<Student>) aux;
            objectInput.close();
        }

        studentArray.add(person_);

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

}    
    
    

