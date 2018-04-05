package Domain;

public class Loan {

    private String studentId;
    private String signature;
    private String loanDay;
    private String deliveryDay;
    private String kind;

    public Loan(String studentId, String signature, String loanDay, String deliveryDay, String kind) {
        this.studentId = studentId;
        this.signature = signature;
        this.loanDay = loanDay;
        this.deliveryDay = deliveryDay;
        this.kind = kind;
    }

    public Loan() {
        this.studentId = "";
        this.signature = "";
        this.loanDay = "";
        this.deliveryDay = "";
        this.kind = "";
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }
    
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getLoanDay() {
        return loanDay;
    }

    public void setLoanDay(String loanDay) {
        this.loanDay = loanDay;
    }

    public String getDeliveryDay() {
        return deliveryDay;
    }

    public void setDeliveryDay(String deliveryDay) {
        this.deliveryDay = deliveryDay;
    }

    @Override
    public String toString() {
        return "Prestamo{" + "carné del estudiante=" + studentId + ", signatura=" + signature
                + "\ndia de prestamo=" + loanDay + ", dia de entrega=" + deliveryDay + ", articulo=" + kind +'}';
    }

    public int sizeInBytes() {
        //long: necesita dos bytes
        //String: necesita 2 bytes de espacio.
        return this.getStudentId().length() * 2 + 2
                + this.getSignature().length() * 2 + 2
                + this.getLoanDay().length() * 2 + 2
                + this.getDeliveryDay().length() * 2 + 2
                + this.getKind().length() * 2 + 2;

    }//fin sizeInBytes()

}//fin Loan
