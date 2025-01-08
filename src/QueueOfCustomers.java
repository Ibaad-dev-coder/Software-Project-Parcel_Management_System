import java.util.ArrayList;
import java.util.List;

public class QueueOfCustomers {
    private List<Customer> customerList;
    private List<Customer> collectedList;
    private int queueNo = 1;
    private String compositeKey;
    private Log log = Log.getInstance();



    public QueueOfCustomers() {
        this.customerList = new ArrayList<>();
        this.collectedList = new ArrayList<>();
        log.addEntry("Waiting Queue for Customers created:");
        log.addEntry("Collected List for Customers created:");

    }

    public void generateCompositeKey(Customer customer) {
        compositeKey = customer.getSurname()+"_"+customer.getParcelID();
    }

    public String getCompositeKey() {
        return compositeKey;
    }

    public void addCustomer(Customer customer) {
        customerList.add(customer);
        customer.setQueueNo(queueNo);
        log.addEntry("Customer added to waiting queue: " + customer.getCustomerName());
        customer.setStatus("Queued up for collection");
        queueNo++;
    }

    public void moveCustomerToCollected(Customer customer) {
        if (customerList.contains(customer)) {
            customerList.remove(customer); // Remove from waiting list
            customer.setStatus("Collected their parcel"); // Set status to collected
            collectedList.add(customer); // Add to collected list
            log.addEntry("Customer moved to collected queue: " + customer.getCustomerName());

        }
    }

    public List<Customer> getWaitingQueue() {
        return customerList;
    }

    // Get the collected list
    public List<Customer> getCollectedQueue() {
        return collectedList;
    }

    // Print the waiting queue
    public void printWaitingQueue() {
        System.out.println("Waiting Queue:");
        for (Customer c : customerList) {
            System.out.println(c);
        }
    }

    // Print the collected list
    public void printCollectedQueue() {
        System.out.println("Collected List:");
        for (Customer c : collectedList) {
            System.out.println(c);
        }
    }

    public void delWaitingQueue() {
        this.getWaitingQueue().clear();
    }
    public void delCollectedQueue() {
        this.getCollectedQueue().clear();
    }

}
