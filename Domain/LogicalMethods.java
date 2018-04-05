/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

import File.BooksFile;
import File.StudentFile;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OptionalDataException;

/**
 *
 * @author Maria
 */
public class LogicalMethods {
    StudentFile stf= new StudentFile();
    
    public String getStudentId(String career, String year, int number){
        String id= String.valueOf(career.charAt(0)
            +String.valueOf(year.charAt(3))
            +String.format("%03d", number));
    
    return id;
    }
    
    public boolean checkStudentRecord(String idStudent) throws FileNotFoundException, ClassNotFoundException, OptionalDataException{
        
        for (int i = 0; i < stf.readFile().size(); i++) {
            if (stf.readFile().get(i).getId().equalsIgnoreCase(idStudent)) {
                return true;
            }
        }//for
        return false;
    }
     
    public String[] autocompleteOptions() throws IOException{
        
        BooksFile bf= new BooksFile(new File("./Books.dat"));
        String[] options= new String[bf.getAllBooks().size()];
    
        for (int i = 0; i < bf.getAllBooks().size() ; i++) {
            options[i]= bf.getAllBooks().get(i).getName();
        }
        
        return options;
    }
}

