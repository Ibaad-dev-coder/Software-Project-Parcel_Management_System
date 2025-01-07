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

    public void addCustomer(Customer customer) {
        customerList.add(customer);
        customer.setQueueNumber(queueNo);
        status = true;
        customer.setQueueNumber(queueNo);
        customer.setStatus(status);
        queueNo++;
    }

    public void moveCustomerToCollected(Customer customer) {
        if (customerList.contains(customer)) {
            customerList.remove(customer); // Remove from waiting list
            customer.setStatus(false); // Set status to collected
            collectedList.add(customer); // Add to collected list
        }
    }

    public List<Customer> getWaitingQueue() {
        return customerList;
    }

    // Get the collected list
    public List<Customer> getCollectedList() {
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
    public void printCollectedList() {
        System.out.println("Collected List:");
        for (Customer c : collectedList) {
            System.out.println(c);
        }
    }

}
