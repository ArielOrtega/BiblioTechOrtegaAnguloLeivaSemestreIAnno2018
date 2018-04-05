
package File;

import Domain.Student;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.util.ArrayList;
import java.util.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class StudentFile {
    String path= "studentFile.txt";
    
   
    public void writeFile(ArrayList<Student> array) {
               
        try {
            try (FileOutputStream fos = new FileOutputStream(path, true)) {
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(array);
                oos.writeInt(array.size());
                oos.close();
            }
        } catch (IOException ioe) {
        }
        
    }  
        
    public  ObservableList<Student> readFile() throws FileNotFoundException, ClassNotFoundException, OptionalDataException {

        ObservableList<Student> arrayStudents= FXCollections.observableArrayList();
                
            try (FileInputStream fis = new FileInputStream(path); 
                ObjectInputStream ois = new ObjectInputStream(fis)) {
                
                @SuppressWarnings("unchecked")
                ArrayList<Student> arraylist = (ArrayList<Student>) ois.readObject();
       
                int num= ois.readInt();
                for (int i = 0; i < num; i++) {
                    System.out.println(arraylist.get(i));
                    arrayStudents.add(arraylist.get(i));
                }
                System.out.println("\n");


         }catch(IOException | ClassNotFoundException ioe){}
            
        return arrayStudents;
        }
    
    }    
    
    

