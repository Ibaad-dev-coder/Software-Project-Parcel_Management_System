import java.util.ArrayList;
import java.util.List;

public class QueueOfCustomers {
    private List<Customer> customerList;
    private List<Customer> collectedList;
    private int queueNo = 1;
    private boolean status = true;


    public QueueOfCustomers() {
        this.customerList = new ArrayList<>();
        this.collectedList = new ArrayList<>();
    }
}
