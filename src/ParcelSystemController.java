public class ParcelSystemController {
    private ParcelMap parcelMap;
    private QueueOfCustomers queue;

    private Worker worker;
    private Log log = Log.getInstance();

    public ParcelSystemController() {
        this.parcelMap = parcelMap;
        this.queue = queue;
        this.worker = new Worker(parcelMap, queue);
    }


    public void addCustomerToQueue(String surname, String parcelID) {
        Customer customer = new Customer(surname, parcelID);
        queue.addCustomer(customer);
        log.addEntry("Customer added to the queue: " + customer.getCustomerName());
    }

    public void addParcelToWaitingList(String surname, String parcelID, String dimensions, double weight, int daysInDepot) {
        Parcel parcel = new Parcel(parcelID,weight,dimensions,daysInDepot,surname);
        parcelMap.addParcel(parcel);
        log.addEntry("Parcel added to waiting list: " + parcelID);
    }

    public void moveCustomerToCollected(String customerName) {
        Customer customer = worker.getCustomerObjbyName(customerName);
        if (customer != null) {
            queue.moveCustomerToCollected(customer);
            log.addEntry("Customer moved to collected queue: " + customer.getCustomerName());
        } else {
            log.addEntry("Customer not found in the queue: " + customerName);
            System.out.println("Customer not found in the queue.");
        }
    }

    public void moveParcelToCollected(String surname, String parcelID) {
        String compositeKey = surname + "_" + parcelID;
        parcelMap.moveParcelToCollected(compositeKey);
        log.addEntry("Parcel with ID: " + compositeKey + " moved to collected list.");
    }

    public void processNextCustomer() {
        worker.processNextCustomer();
    }

    // Process all customers in the queue
    public void processAllCustomers() {
        worker.processAllCustomers();
    }

    // Get the waiting list of parcels
    public void printWaitingParcels() {
        String waitingList = parcelMap.printWaitingList();
        System.out.println(waitingList);
    }

    // Get the collected list of parcels
    public void printCollectedParcels() {
        String collectedList = parcelMap.printCollectedList();
        System.out.println(collectedList);
    }

    // Get the waiting queue of customers
    public void printWaitingQueue() {
        queue.printWaitingQueue();
    }

    // Get the collected queue of customers
    public void printCollectedQueue() {
        queue.printCollectedQueue();
    }

    // Clear the waiting list of parcels
    public void clearWaitingList() {
        parcelMap.delWaitingList();
        log.addEntry("Waiting list cleared.");
    }

    // Clear the collected list of parcels
    public void clearCollectedList() {
        parcelMap.delCollectedList();
        log.addEntry("Collected list cleared.");
    }

    // Clear the waiting queue of customers
    public void clearWaitingQueue() {
        queue.delWaitingQueue();
        log.addEntry("Waiting queue cleared.");
    }

    // Clear the collected queue of customers
    public void clearCollectedQueue() {
        queue.delCollectedQueue();
        log.addEntry("Collected queue cleared.");
    }

}
