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
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    public boolean checkStudentRecord(String idStudent) throws FileNotFoundException, ClassNotFoundException, OptionalDataException, IOException{
        
        for (int i = 0; i < stf.readList().size(); i++) {
            if (stf.readList().get(i).getId().equalsIgnoreCase(idStudent)) {
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
    
    public String buscarSignatura(String nameArticle) throws IOException{
        
        BooksFile bf= new BooksFile(new File("./Books.dat"));
        String signature="";
    
        for (int i = 0; i < bf.getAllBooks().size() ; i++) {
            if(bf.getAllBooks().get(i).getName().equalsIgnoreCase(nameArticle)){
                signature= bf.getAllBooks().get(i).getSignature();
            }
        }
        
        return signature;
    }
    
}

