import java.util.ArrayList;
import java.util.List;

public class Customer {
    private String customerName;
    private String ParcelID;
    private int queueNo;
    private List<Parcel> parcelList;
    private String firstName;
    private String surname;
    private String Status;
    private String[] nameParts;
    private Log log = Log.getInstance();

    Customer(String customerName,String ParcelID) {
        this.customerName = customerName;
        this.ParcelID = ParcelID;
        this.parcelList = new ArrayList<Parcel>();
        this.nameParts = customerName.split(" ");
        this.firstName = nameParts[0];
        this.surname = nameParts[1];
        this.Status = "Unregistered";
        log.addEntry("Customer created with Name: " + customerName + " Parcel ID: " + ParcelID);
    }

    // Customer Name Setter
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
        log.addEntry("setCustomerName() called with value: " + customerName);
    }

    // Parcel ID Setter
    public void setParcelID(String ParcelID) {
        this.ParcelID = ParcelID;
        log.addEntry("setParcelID() called with value: " + ParcelID);
    }

    // First Name Setter
    public void setFirstName(String firstName) {
        this.firstName = firstName;
        log.addEntry("setFirstName() called with value: " + firstName);
    }

    // Surname Setter
    public void setSurname(String surname){
        this.surname = surname;
        log.addEntry("setSurname() called with value: " + surname);
    }

    // Queue No Setter - Not needed
    public void setQueueNo(int queueNo) {
        this.queueNo = queueNo;
        log.addEntry("setQueueNo() called with value: " + queueNo);
    }

    public void setStatus(String Status) {
        this.Status = Status;
        log.addEntry("setStatus() called with value: " + Status);
    }



    // Customer Name Getter
    public String getCustomerName() {
        log.addEntry("getCustomerName() called");
        return customerName;
    }

    // Parcel ID Getter
    public String getParcelID() {
        log.addEntry("getParcelID() called");
        return ParcelID;
    }

    // First Name Getter
    public String getFirstName() {
        log.addEntry("getFirstName() called");
        return firstName;
    }

    // Surname Getter
    public String getSurname(){
        log.addEntry("getSurname() called");
        return surname;
    }

    // Queue No Getter
    public int getQueueNo() {
        log.addEntry("getQueueNo() called");
        return queueNo;
    }

    // Status Getter
    public String getStatus() {
        log.addEntry("getStatus() called");
        return Status;
    }


    // Adding Parcels
    public void addParcel(Parcel parcel) {
        parcelList.add(parcel);
        log.addEntry("Parcel added to customer details");
    }

    // Parcel List Getter
    public List<Parcel> getParcelList() {
        log.addEntry("getParcelList() called");
        return parcelList;
    }


    @Override
    public String toString() {
        String result = "Customer Name: " + customerName + ", Parcel ID: " + ParcelID;
        if (queueNo > 0) { // Assuming queueNo is assigned a positive integer when queued
            result += ", Queue No: " + queueNo;
        }
        else {
            result += ", Queue No: Queue Number not Assigned yet, Status: " + Status;
        }
        log.addEntry("toString() called for Customer Name: " + customerName + " Associated with parcel ID: " + ParcelID);
        return result+"\n";
    }





}
