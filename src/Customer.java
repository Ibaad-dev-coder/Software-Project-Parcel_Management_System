import java.util.ArrayList;
import java.util.List;

public class Customer {
    private String customerName;
    private String ParcelID;
    private int queueNo;
    private List<Parcel> parcelIds;
    private String firstName;
    private String surname;
    private boolean Status;
    private String[] nameParts;

    Customer(String customerName,String ParcelID) {
        this.customerName = customerName;
        this.ParcelID = ParcelID;
        this.parcelIds = new ArrayList<Parcel>();
        this.nameParts = customerName.split(" ");
        this.firstName = nameParts[0];
        this.surname = nameParts[1];
        this.Status = false;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname(){
        return surname;
    }

    public String getParcelID() {
        return ParcelID;
    }

    public int getQueueNo() {
        return queueNo;
    }

    public void addParcel(Parcel parcel) {
        parcelIds.add(parcel);
    }

    public void printParcels() {
        System.out.println(parcelIds);
    }

    @Override
    public String toString() {
        // Start with the basic details (customer name and parcel ID)
        String result = "Customer Name: " + customerName + ", Parcel ID: " + ParcelID;

        // Check if the customer has been added to a queue (queueNo assigned)
        if (queueNo > 0) { // Assuming queueNo is assigned a positive integer when queued
            result += ", Queue No: " + queueNo;

            // Add the status depending on whether the customer is waiting or has collected their parcel
            if (Status) {
                result += ", Status: Waiting for collection";
            } else {
                result += ", Status: Collected";
            }
        }
        else {
            result += ", Queue No: Queue No not Assigned, Status: Not queued yet ";
        }

        return result;
    }



    // Setters for queueNumber and status
    public void setQueueNumber(int queueNo) {
        this.queueNo = queueNo;
    }

    public void setStatus(boolean Status) {
        this.Status = Status;
    }

    // Getter methods for queueNumber and status (if needed later)
    public int getQueueNumber() {
        return queueNo;
    }

    public boolean getStatus() {
        return Status;
    }


}
