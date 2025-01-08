public class Worker {
    private ParcelMap parcelMap;
    private QueueOfCustomers queueOfCustomers;
    private Log log = Log.getInstance();

    public Worker( ParcelMap parcelMap, QueueOfCustomers queueOfCustomers) {
        this.parcelMap = parcelMap;
        this.queueOfCustomers = queueOfCustomers;
        log.addEntry("Worker created.");

    }

    public void processNextCustomer() {
        if (!queueOfCustomers.getWaitingQueue().isEmpty()) {
            log.addEntry("Processing customer... ");

            // Fetch the next customer from the front of the queue
            Customer customer = queueOfCustomers.getWaitingQueue().get(0); // The first customer in the queue

            System.out.println("Processing customer: " + customer.getCustomerName() + " (Queue No: " + customer.getQueueNo() + " Parcel ID : " + customer.getParcelID() +  ")");

            // Step 1: Process the customer's parcel (fetch, check status, calculate fee, release parcel)
            processCustomerParcel(customer);

            // Step 2: Move the customer to the collected list after processing
            queueOfCustomers.moveCustomerToCollected(customer);
        } else {
            System.out.println("No customers in the queue to process.");
        }
    }

    public Customer getCustomerObjbyName(String name) {
        Customer cobj = null;
        for (Customer customer : queueOfCustomers.getWaitingQueue()) {
            if (customer.getCustomerName().equalsIgnoreCase(name)) {
                cobj = customer; // Return the customer if the name matches
            }
            else {
                System.out.println("Could not find Customer by Name Since it does not exist");
                return cobj;
            }
        }
        return cobj;
    }
    private void processCustomerParcel(Customer customer) {

        String compositeKey = customer.getSurname() + "_" + customer.getParcelID();
        Parcel parcel = parcelMap.getParcelByUniqueID(compositeKey);

        if (parcel != null) {
            // Step 1: Check if the parcel is ready for collection
            if (parcel.getStatus().equals("Waiting for Collection")) {
                // Step 2: Calculate the fee
                double fee = calculateFee(parcel);
                System.out.println("Fee for customer " + customer.getCustomerName() + ": " + fee);

                // Log the fee calculation (could be extended to use a Logger)
                System.out.println("Fee calculated for parcel ID: " + compositeKey + " is " + fee);

                // Step 3: Process customer (assume they pay the fee and collect parcel)
                System.out.println("Processing parcel collection for customer: " + customer.getCustomerName());

                // Step 4: Release the parcel and update the status
                releaseParcel(compositeKey);
            } else {
                System.out.println("Parcel with ID: " + compositeKey + " is not available for collection.");
            }
        } else {
            System.out.println("Parcel with ID: " + compositeKey + " does not exist.");
        }
    }

    public ParcelMap releaseParcel(String compositeKey) {
        parcelMap.moveParcelToCollected(compositeKey); // Move the parcel to collected list
        System.out.println("Parcel with ID: " + compositeKey + " has been released.");
        return parcelMap;
    }

    // Method to process all customers in the queue until it's empty
    public void processAllCustomers() {
        while (!queueOfCustomers.getWaitingQueue().isEmpty()) {
            processNextCustomer();
        }
    }


    public double calculateFee(Parcel p) {

        double baseFee = 5.0;
        double dimensionFactorMultiplier = 0.01;
        double weightFactorMultiplier = 2.0;
        double daysFactorMultiplier = 0.5;

        // Calculate individual factors
        String dimensions = p.getDimensions();
        String[] numbers = dimensions.split(" ");
        int product = 1;
        for (String num: numbers){
            product *= Integer.parseInt(num);
        }
        double dimensionFactor = product * dimensionFactorMultiplier;
        double weightFactor = p.getWeight() * weightFactorMultiplier;
        double daysFactor = p.getDaysInDepot() * daysFactorMultiplier;

        // Determine the service multiplier
        double serviceMultiplier = getServiceMultiplier(p.getParcelID());


        // Calculate the total fee
        double totalFee = (baseFee + dimensionFactor + weightFactor + daysFactor) * serviceMultiplier;
        return totalFee;
    }

    // Method to determine the service multiplier
    public static double getServiceMultiplier(String parcelID) {
        if (parcelID.startsWith("X")) {
            return 1.0; // Multiplier for IDs starting with 'X'
        } else if (parcelID.startsWith("C")) {
            return 0.8; // Multiplier for IDs starting with 'C'
        } else {
            return 1.0; // Default multiplier
        }
    }

}
