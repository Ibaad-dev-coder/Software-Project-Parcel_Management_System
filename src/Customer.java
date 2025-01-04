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
}
