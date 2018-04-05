
package File;

import Domain.AudioVisual;
import Domain.Books;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class AudioVisualFile {
    
    //atributos
    public RandomAccessFile randomAccessFile;
    private int regsQuantity;//cantidad de registros en el archivo
    private int regSize;//tamanno en bytes del registro
    private String myFilePath;//ruta
    
     public AudioVisualFile(File file) throws IOException{
        //almaceno la ruta
        myFilePath = file.getPath();
        
        //tamanno maximo
        this.regSize = 300;
        
        //Asegurarse de que el archivo existe
        if(file.exists() && !file.isFile()){
            throw new IOException(file.getName() + " es un archivo invalido");
        }else{
            //crear la nueva instancia de RAF rw= leer y escribir
            randomAccessFile = new RandomAccessFile(file, "rw");
            
            //necesitamos indicar cuantos registros tiene el archivo
            this.regsQuantity = 
                    (int)Math.ceil((double)randomAccessFile.length() / (double)regSize);
        }//fin else
    }
     
     public void close() throws IOException{
        randomAccessFile.close();
        
    }
    
    //establecer la cantidad de registros del archivo 
    public int fileSize(){
        return this.regsQuantity;
    }
    
    public boolean putValue(int position, AudioVisual audiovisual) throws IOException{
        //primero: verificar que sea valida la insercion
        if(position < 0 && position > this.regsQuantity){
            System.err.println("1001 - Record position is out of bounds");
            return false;
        }else{
            if(audiovisual.sizeInBytes() > this.regSize){
                System.err.println("1002 - Record size is out of bounds");
                return false;
            }else{
                
                randomAccessFile.seek(position * this.regSize);
                randomAccessFile.writeUTF(audiovisual.getBrand());
                randomAccessFile.writeUTF(audiovisual.getName());
                randomAccessFile.writeUTF(audiovisual.getSignature());
                randomAccessFile.writeInt(audiovisual.getAvailability());
                randomAccessFile.writeUTF(audiovisual.getDescription());
                return true;
            }
        }
    }
    
    //insertar al final del archivo
    public boolean addEndRecord(AudioVisual audiovisual) throws IOException{
        boolean success = putValue(this.regsQuantity, audiovisual);
        if(success){
            ++this.regsQuantity;
        }
        return success;
    }
    
    //obtener un audiovisual
    public AudioVisual getAudioVisual(int position) throws IOException{
        //validar la posicion
        if(position >= 0 && position <= this.regsQuantity){
            //colocamos el brazo en el lugar adecuado
            randomAccessFile.seek(position * this.regSize);
            
            //llevamos a cabo la lectura
            AudioVisual audioVisualTemp = new AudioVisual();
            audioVisualTemp.setBrand(randomAccessFile.readUTF());
            audioVisualTemp.setName(randomAccessFile.readUTF());
            audioVisualTemp.setSignature(randomAccessFile.readUTF());
            audioVisualTemp.setAvailability(randomAccessFile.readInt());
            audioVisualTemp.setDescription(randomAccessFile.readUTF());
            
            if(audioVisualTemp.getName().equalsIgnoreCase("deleted")){
                return null;
            }else{
                return audioVisualTemp;
            }
        }else{
            System.err.println("1003 - position is out of bounds");
            return null;
        }
    }//end method
    
    //aumentar disponibilidad
    public boolean avaibilityAudioVisual(String signature, int quantity) throws IOException{
        AudioVisual myAudioVisual;
        
        //buscar el audioVisual
        for(int i = 0; i < this.regsQuantity; i++){
            //obtener el audiovisual
            myAudioVisual= this.getAudioVisual(i);
            
            //Verificar que es el mismo objeto para aumentar su disponibilidad
            if(myAudioVisual.getSignature().equalsIgnoreCase(signature)){
                //aumentar disponibilidad
                myAudioVisual.setAvailability(myAudioVisual.getAvailability()+quantity);
                
                return this.putValue(i, myAudioVisual);
            }
        }
        return false;
    }
    
    public boolean lessAvaibilityAudioVisual(String signature) throws IOException{
        AudioVisual myAudioVisual;
        
        //buscar el audioVisual
        for(int i = 0; i < this.regsQuantity; i++){
            //obtener el audiovisual
            myAudioVisual= this.getAudioVisual(i);
            
            //Verificar que es el mismo objeto para aumentar su disponibilidad
            if((myAudioVisual.getSignature().equalsIgnoreCase(signature)) && myAudioVisual.getAvailability() > 0){
                //disminuir disponibilidad
                myAudioVisual.setAvailability(myAudioVisual.getAvailability()-1);
                
                return this.putValue(i, myAudioVisual);
                
            }
        }
        return false;
    }
    
    //retornar una lista de laudiovisuales
    public ArrayList<AudioVisual> getAllAudioVisuals() throws IOException{
        ArrayList<AudioVisual> audioVisualsArray = new ArrayList<AudioVisual>();
        
        for(int i = 0; i < this.regsQuantity; i++){
            AudioVisual aTemp = this.getAudioVisual(i);
            
            if(aTemp != null){
                audioVisualsArray.add(aTemp);
            }
        }//end for
        return audioVisualsArray;
    }
    
    public ObservableList<AudioVisual> getAudioVisuals() throws IOException {
        ArrayList<AudioVisual> audioVisualArray = new ArrayList<AudioVisual>();

        for (int i = 0; i < this.regsQuantity; i++) {
            AudioVisual avTemp = this.getAudioVisual(i);

            if (avTemp != null) {
                audioVisualArray.add(avTemp);
            }
   
        }//end for       
        ObservableList<AudioVisual> listAudioVisual = FXCollections.observableArrayList(audioVisualArray);
        return listAudioVisual;
        
    }
}
