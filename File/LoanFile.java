package File;

import Domain.Loan;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LoanFile {

    public RandomAccessFile randomAccessFile;
    private int regsQuantity;
    private int regSize = 300;
    private String myFilePath;

    public LoanFile(File file) throws IOException {
        myFilePath = file.getPath();

        //validacion de que el archivo realmente exista y sea un archivo y no una carpeta u otra ruta
        if (file.exists() && !file.isFile()) {
            throw new IOException(file.getName() + " is an invalid file");
        } else {
            randomAccessFile = new RandomAccessFile(file, "rw");
            this.regsQuantity
                    = (int) Math.ceil((double) randomAccessFile.length() / (double) regSize);
        }

    }//end method

    //Metodo para cerrar el archivo
    public void close() throws IOException {
        randomAccessFile.close();

    }//end method

    //indicar la cantidad de registros de nuestro archivo
    public int fileSize() {
        return this.regsQuantity;
    }//end method

    //insertar un nuevo registro en una posici'on espec'ifica
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
                randomAccessFile.writeUTF(loan.getKind());

                return true;
            }
        }
    }//end method

    //Metodo para que el siguiente ingreso de datos sea al final del archivo
    public void addEndRecord(Loan car) throws IOException {
        if (putValue(this.regsQuantity, car)) {
            regsQuantity++;
        }
    }//end method

    //Metodo para obtener la informaci´on un prestamo
    public Loan getLoan(int position) throws IOException {

        //se valida que la posision sea correcta
        if (position >= 0 && position <= this.regsQuantity) {

            //Se coloca el puntero en el lugar indicado
            randomAccessFile.seek(position * this.regSize);

            //Se realiza la lectura la lectura
            Loan loanTemp = new Loan();
            loanTemp.setStudentId(randomAccessFile.readUTF());
            loanTemp.setSignature(randomAccessFile.readUTF());
            loanTemp.setLoanDay(randomAccessFile.readUTF());
            loanTemp.setDeliveryDay(randomAccessFile.readUTF());
            loanTemp.setKind(randomAccessFile.readUTF());

//            if (loanTemp.getSignature().equalsIgnoreCase("deleted")) {
//                return null;
//            } else {
            return loanTemp;
//            }

        } else {
            System.err.println("1003 - position is out of bounds");
            return null;
        }
    }//end method

    //Metodo para eliminar los datos de un prestamo
    public boolean deleteLoan(String signature) throws IOException {

        Loan myLoan;

        //buscar el prestamo
        for (int i = 0; i < this.regsQuantity; i++) {
            myLoan = this.getLoan(i);

            //Se verifica si es el prestamo que se desea eliminar
            if (myLoan.getSignature().equalsIgnoreCase(signature)) {
                myLoan.setSignature("deleted");

                return this.putValue(i, myLoan);
            }
        }

        return false;
    }//end method

    //Metodo para retornar la lista de prestamos en un ObservableList
    public ObservableList<Loan> getAllLoans() throws IOException {
        ArrayList<Loan> carsArray = new ArrayList<Loan>();

        for (int i = 0; i < this.regsQuantity; i++) {
            Loan lTemp = this.getLoan(i);

            if (lTemp != null && !lTemp.getSignature().equalsIgnoreCase("deleted")) {
                carsArray.add(lTemp);
            }

        }//end for       
        ObservableList<Loan> listCar = FXCollections.observableArrayList(carsArray);
        return listCar;

    }//end method

    public int searchLoan(String signature) throws IOException {
        Loan myLoan;

        //buscar el prestamo
        for (int i = 0; i < fileSize(); i++) {
            randomAccessFile.seek(i * regSize);
            //obtener el auto de la posici'on actual
            myLoan = this.getLoan(i);

            if (myLoan.getSignature().equalsIgnoreCase(signature)) {
                return i;
            }
        }
        return -1;
    }//end method

    //metodo para buscar un prestamo a partir de de nombre y signatura para eliminar
    public int searchDeleteLoan(String signature, String idStudent) throws IOException {
        Loan myLoan;

        //buscar el prestamo
        for (int i = 0; i < fileSize(); i++) {
            randomAccessFile.seek(i * regSize);
            //obtener el auto de la posici'on actual
            myLoan = this.getLoan(i);

            if (myLoan.getSignature().equalsIgnoreCase(signature) && myLoan.getStudentId().equalsIgnoreCase(idStudent)) {
                return i;
            }
        }
        return -1;

    }//end method

    public long numberOfDays(int deliveryDay, int deliveryMonth, int deliveyYear,
            int realDeDay, int realDeMonth, int realDeYear) {

        Calendar c = Calendar.getInstance();

        Calendar fechaInicio = new GregorianCalendar();
        fechaInicio.set(deliveyYear, deliveryMonth, deliveryDay);

        Calendar fechaFin = new GregorianCalendar();
        fechaFin.set(realDeDay, realDeMonth, realDeDay);

        c.setTimeInMillis(fechaFin.getTime().getTime() - fechaInicio.getTime().getTime());

        return c.get(Calendar.DAY_OF_YEAR);
    }//end method

    public long fineOfPayment(long numberOfdays) {

        long pay = 0;

        if (numberOfdays > 0) {

            pay = numberOfdays * 50;

        }
        return pay;
    }//end method

}//end LoanFile
