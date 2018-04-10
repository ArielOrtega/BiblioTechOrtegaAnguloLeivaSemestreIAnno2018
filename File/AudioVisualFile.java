package File;

import Domain.AudioVisual;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AudioVisualFile {

    public RandomAccessFile randomAccessFile;
    private int regsQuantity;//cantidad de registros en el archivo
    private int regSize;//tamanno en bytes del registro
    private String myFilePath;//ruta

    public AudioVisualFile(File file) throws IOException {
        //Se almacena la ruta
        myFilePath = file.getPath();

        //Tamanno maximo
        this.regSize = 300;

        //Asegurarse de que el archivo existe
        if (file.exists() && !file.isFile()) {
            throw new IOException(file.getName() + " it is an invalid file");
        } else {
            //crear la nueva instancia de RAF rw= leer y escribir
            randomAccessFile = new RandomAccessFile(file, "rw");

            //Se indica la cantidad de registros que tiene el archivo
            this.regsQuantity
                    = (int) Math.ceil((double) randomAccessFile.length() / (double) regSize);
        }//fin else
    }//fin del m'etodo

    //M'etodo para cerrar el archivo
    public void close() throws IOException {
        randomAccessFile.close();
    }//fin del m'etodo

    //Establecer la cantidad de registros del archivo 
    public int fileSize() {
        return this.regsQuantity;
    }//fin del m'etodo

    //M'etodo para insertar un nuevo registro
    public boolean putValue(int position, AudioVisual audiovisual) throws IOException {
        //primero: verificar que sea valida la insercion
        if (position < 0 && position > this.regsQuantity) {
            System.err.println("1001 - Record position is out of bounds");
            return false;
        } else {
            if (audiovisual.sizeInBytes() > this.regSize) {
                System.err.println("1002 - Record size is out of bounds");
                return false;
            } else {

                randomAccessFile.seek(position * this.regSize);
                randomAccessFile.writeUTF(audiovisual.getBrand());
                randomAccessFile.writeUTF(audiovisual.getName());
                randomAccessFile.writeUTF(audiovisual.getSignature());
                randomAccessFile.writeInt(audiovisual.getAvailability());
                randomAccessFile.writeUTF(audiovisual.getDescription());
                return true;
            }
        }
    }//fin del m'etodo

    //M'etodo para insertar al final del archivo
    public boolean addEndRecord(AudioVisual audiovisual) throws IOException {
        boolean success = putValue(this.regsQuantity, audiovisual);
        if (success) {
            ++this.regsQuantity;
        }
        return success;
    }//fin del m'etodo

    //M'etodo para obtener un art'iculo audiovisual
    public AudioVisual getAudioVisual(int position) throws IOException {
        //validar la posici'on
        if (position >= 0 && position <= this.regsQuantity) {
            //colocamos el brazo en el lugar adecuado
            randomAccessFile.seek(position * this.regSize);

            //llevamos a cabo la lectura
            AudioVisual audioVisualTemp = new AudioVisual();
            audioVisualTemp.setBrand(randomAccessFile.readUTF());
            audioVisualTemp.setName(randomAccessFile.readUTF());
            audioVisualTemp.setSignature(randomAccessFile.readUTF());
            audioVisualTemp.setAvailability(randomAccessFile.readInt());
            audioVisualTemp.setDescription(randomAccessFile.readUTF());

            if (audioVisualTemp.getName().equalsIgnoreCase("deleted")) {
                return null;
            } else {
                return audioVisualTemp;
            }
        } else {
            System.err.println("1003 - position is out of bounds");
            return null;
        }
    }//fin del m'etodo

    //M'etodo para aumentar disponibilidad del art'iculo
    public boolean avaibilityAudioVisual(String signature, int quantity) throws IOException {
        AudioVisual myAudioVisual;

        //buscar el audioVisual
        for (int i = 0; i < this.regsQuantity; i++) {
            //obtener el audiovisual
            myAudioVisual = this.getAudioVisual(i);

            //Verificar que es el mismo objeto para aumentar su disponibilidad
            if (myAudioVisual.getSignature().equalsIgnoreCase(signature)) {
                //aumentar disponibilidad
                myAudioVisual.setAvailability(myAudioVisual.getAvailability() + quantity);

                return this.putValue(i, myAudioVisual);
            }
        }
        return false;
    }//fin del m'etodo

    //M'etodo para disminuir la disponibilidad
    public boolean lessAvaibilityAudioVisual(String signature) throws IOException {
        AudioVisual myAudioVisual;

        //buscar el audioVisual
        for (int i = 0; i < this.regsQuantity; i++) {
            //obtener el audiovisual
            myAudioVisual = this.getAudioVisual(i);

            //Verificar que es el mismo objeto para aumentar su disponibilidad
            if ((myAudioVisual.getSignature().equalsIgnoreCase(signature)) && myAudioVisual.getAvailability() > 0) {
                //disminuir disponibilidad
                myAudioVisual.setAvailability(myAudioVisual.getAvailability() - 1);

                return this.putValue(i, myAudioVisual);

            }
        }
        return false;
    }//fin del m'etodo

    //M'etodo que retorna una lista de audiovisuales
    public ArrayList<AudioVisual> getAllAudioVisuals() throws IOException {
        ArrayList<AudioVisual> audioVisualsArray = new ArrayList<AudioVisual>();

        for (int i = 0; i < this.regsQuantity; i++) {
            AudioVisual aTemp = this.getAudioVisual(i);

            if (aTemp != null) {
                audioVisualsArray.add(aTemp);
            }
        }//end for
        return audioVisualsArray;
    }//fin del m'etodo

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
    }//fin del m'etodo
    
    public boolean getAvailability(String signature) throws IOException {
        boolean availability = false;
        for (int i = 0; i < this.regsQuantity; i++) {
            if (this.getAudioVisual(i).getSignature().equalsIgnoreCase(signature) && (this.getAudioVisual(i).getAvailability() > 0)) {
                availability = true;
            }
        }//end for
        return availability;
    }
    
    public String[] autocompleteOptions() throws IOException{
        BooksFile bf= new BooksFile(new File("./Books.dat"));
        String[] options= new String[bf.getAllBooks().size()+getAllAudioVisuals().size()];
        int j= 0;
        for (int i = 0; i < bf.getAllBooks().size() + getAudioVisuals().size() ; i++) {
            if (i < bf.getAllBooks().size()) {
                options[i]= bf.getAllBooks().get(i).getName();
            }
            else{
                options[j]= getAllAudioVisuals().get(j).getName();
                j++;
            }
        }
        
        return options;
    }    

    public String getSignature(String nameArticle) throws IOException {
        String signature="";
    
        for (int i = 0; i < getAllAudioVisuals().size() ; i++) {
            if(getAllAudioVisuals().get(i).getName().equalsIgnoreCase(nameArticle)){
                signature= getAllAudioVisuals().get(i).getSignature();
            }
        }
        
        return signature;
    }
}
