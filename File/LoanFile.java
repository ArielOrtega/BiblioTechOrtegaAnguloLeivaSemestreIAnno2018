package File;

import Domain.Loan;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LoanFile {

    public RandomAccessFile randomAccessFile;
    private int regsQuantity;
    private int regSize = 300;
    private String myFilePath;

    public LoanFile(File file) throws IOException {
        myFilePath = file.getPath();

        //validaci'on de que el archivo realmente exista y sea un archivo y no una carpeta u otra ruta
        if (file.exists() && !file.isFile()) {
            throw new IOException(file.getName() + " is an invalid file");
        } else {
            randomAccessFile = new RandomAccessFile(file, "rw");
            this.regsQuantity
                    = (int) Math.ceil((double) randomAccessFile.length() / (double) regSize);
        }

    }//Fin del m'etodo

    //M'etodo para cerrar el archivo luego de su uso
    public void close() throws IOException {
        randomAccessFile.close();

    }//Fin del m'etodo

    //Indicar la cantidad de registros de nuestro archivo
    public int fileSize() {
        return this.regsQuantity;
    }//Fin del m'etodo

    //M'etodo para insertar un nuevo registro en una posici'on espec'ifica
    public boolean putValue(int position, Loan loan) throws IOException {
        //primero: verificar que sea v'alida la inserci'on
        if (position < 0 && position > fileSize()) {
            System.err.println("1001 - Record position is out of bounds");
            return false;
        } else {
            if (loan.sizeInBytes() > this.regSize) {
                System.err.println("1002 - Record size is out of bounds");
                return false;
            } else {
                randomAccessFile.seek(position * this.regSize);
                randomAccessFile.writeUTF(loan.getStudentId());
                randomAccessFile.writeUTF(loan.getSignature());
                randomAccessFile.writeUTF(loan.getLoanDay());
                randomAccessFile.writeUTF(loan.getDeliveryDay());
                randomAccessFile.writeBoolean(loan.getKind());

                return true;
            }
        }
    }//Fin del m'etodo

    //M'etodo para que el siguiente ingreso de datos sea al final del archivo
    public void addEndRecord(Loan loan) throws IOException {
        if (putValue(this.regsQuantity, loan)) {
            regsQuantity++;
        }
    }//Fin del m'etodo

    //M'etodo para obtener la informaci´on un pr'estamo
    public Loan getLoan(int position) throws IOException {

        //Se valida que la posici'on sea correcta
        if (position >= 0 && position <= this.regsQuantity) {

            //Se coloca el puntero en el lugar indicado
            randomAccessFile.seek(position * this.regSize);

            //Se realiza la lectura 
            Loan loanTemp = new Loan();
            loanTemp.setStudentId(randomAccessFile.readUTF());
            loanTemp.setSignature(randomAccessFile.readUTF());
            loanTemp.setLoanDay(randomAccessFile.readUTF());
            loanTemp.setDeliveryDay(randomAccessFile.readUTF());
            loanTemp.setKind(randomAccessFile.readBoolean());

            return loanTemp;

        } else {
            System.err.println("1003 - position is out of bounds");
            return null;
        }
    }//Fin del m'etodo

    //M'etodo para eliminar los datos de un pr'estamo
    public boolean deleteLoan(String signature) throws IOException {

        Loan myLoan;

        //buscar el prestamo
        for (int i = 0; i < this.regsQuantity; i++) {
            myLoan = this.getLoan(i);

            //Se verifica si es el pr'estamo que se desea eliminar
            if (myLoan.getSignature().equalsIgnoreCase(signature)) {
                myLoan.setSignature("deleted");

                return this.putValue(i, myLoan);
            }
        }

        return false;
    }//Fin del m'etodo

    //M'etodo para retornar la lista de pr'estamos en un ObservableList
    public ObservableList<Loan> getAllLoans() throws IOException {
        ArrayList<Loan> loanArray = new ArrayList<Loan>();

        for (int i = 0; i < this.regsQuantity; i++) {
            Loan lTemp = this.getLoan(i);

            if (lTemp != null && !lTemp.getSignature().equalsIgnoreCase("deleted")) {
                loanArray.add(lTemp);
            }

        }//end for       
        ObservableList<Loan> listLoan = FXCollections.observableArrayList(loanArray);
        return listLoan;

    }//Fin del m'etodo

    public int searchLoan(String signature) throws IOException {
        Loan myLoan;

        //buscar el pr'estamo
        for (int i = 0; i < fileSize(); i++) {
            randomAccessFile.seek(i * regSize);
            //obtener el auto de la posici'on actual
            myLoan = this.getLoan(i);

            if (myLoan.getSignature().equalsIgnoreCase(signature)) {
                return i;
            }
        }
        return -1;
    }//Fin del m'etodo

    //M'etodo para buscar un pr'estamo a partir de de nombre y signatura para eliminar
    public int searchDeleteLoan(String signature, String idStudent) throws IOException {
        Loan myLoan;

        //buscar el prestamo
        for (int i = 0; i < fileSize(); i++) {
            randomAccessFile.seek(i * regSize);
            //obtener el pr'estamo de la posici'on actual
            myLoan = this.getLoan(i);

            if (myLoan.getSignature().equalsIgnoreCase(signature) && myLoan.getStudentId().equalsIgnoreCase(idStudent)) {
                return i;
            }
        }
        return -1;

    }//Fin del m'etodo

    //M'etodo para obtener el n'umero de d'ias de atraso en la devoluci'on
    public long numberOfDays(String date1, String date2) throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date deliveryDay = dateFormat.parse(date1);
        Date realDeDay = dateFormat.parse(date2);

        long dias = (long) ((realDeDay.getTime() - deliveryDay.getTime()) / 86400000);

        return dias;

    }//Fin del m'etodo

    //M'etodo para obtener el monto que se debe pagar en colones por el atraso 
    public long fineOfPayment(long numberOfdays) {

        long pay = 0;

        if (numberOfdays > 0) {

            pay = numberOfdays * 50;

        }
        return pay;
    }//Fin del m'etodo

}//Fin de la clase
