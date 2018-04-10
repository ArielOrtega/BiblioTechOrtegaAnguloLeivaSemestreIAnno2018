package Domain;

public class Loan {

    //Atributos
    private String studentId;
    private String signature;
    private String loanDay;
    private String deliveryDay;
    private boolean kind;

    //Contructor
    public Loan(String studentId, String signature, String loanDay, String deliveryDay, boolean kind) {
        this.studentId = studentId;
        this.signature = signature;
        this.loanDay = loanDay;
        this.deliveryDay = deliveryDay;
        this.kind = kind;
    }//fin constructor

    //Constructor vac'io
    public Loan() {
        this.studentId = "";
        this.signature = "";
        this.loanDay = "";
        this.deliveryDay = "";
        this.kind = false;
    }//fin constructor vac'io

    //Setters and Getters
    public boolean getKind() {
        return kind;
    }//fin

    public void setKind(boolean kind) {
        this.kind = kind;
    }//fin

    public String getStudentId() {
        return studentId;
    }//fin

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }//fin

    public String getSignature() {
        return signature;
    }//fin

    public void setSignature(String signature) {
        this.signature = signature;
    }//fin

    public String getLoanDay() {
        return loanDay;
    }//fin

    public void setLoanDay(String loanDay) {
        this.loanDay = loanDay;
    }//fin

    public String getDeliveryDay() {
        return deliveryDay;
    }//fin

    public void setDeliveryDay(String deliveryDay) {
        this.deliveryDay = deliveryDay;
    }//fin

    //toString
    @Override
    public String toString() {
        return "Prestamo{" + "carné del estudiante=" + studentId + ", signatura=" + signature
                + "\ndia de prestamo=" + loanDay + ", dia de entrega=" + deliveryDay + ", articulo=" + kind + '}';
    }//fin toString

    //Madici'on del tamanno de cada valor del atrivuto
    public int sizeInBytes() {
        return this.getStudentId().length() * 2 + 2
                + this.getSignature().length() * 2 + 2
                + this.getLoanDay().length() * 2 + 2
                + this.getDeliveryDay().length() * 2 + 2
                + 1;

    }//fin sizeInBytes()

}//fin clase
