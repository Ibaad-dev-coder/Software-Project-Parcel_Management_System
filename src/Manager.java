import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Manager {
    private String managerId;
    private String managerName;
    private List<Worker> employeeList;
    private List<String> taskList;
    private ArrayList<String[]> pStorage;
    private ArrayList<String[]> cStorage;
    private String[] nameParts;
    private Log log = Log.getInstance();
    private ParcelMap parcelMap;
    private QueueOfCustomers queue;


    public Manager(ParcelMap parcelMap, QueueOfCustomers queue) {
        this.parcelMap = parcelMap;
        this.queue = queue;
        this.managerId = managerId;
        this.managerName = managerName;
        this.employeeList = new ArrayList<>();
        this.taskList = new ArrayList<>();
        this.pStorage = new ArrayList<>();
        this.cStorage = new ArrayList<>();
        log.addEntry("Manager created.");


    }

    File mainParcel = new File("src/Parcels.csv");
    File mainCusts = new File("src/Custs.csv");
    File dummyParcel = new File("dummyParcels.csv");
    File dummyCusts = new File("dummyCusts.csv");


    public void makeDummies() {
        log.addEntry("Creating dummy data...");
        try (BufferedReader pReader = new BufferedReader(new FileReader(mainParcel));
             BufferedWriter pWriter = new BufferedWriter(new FileWriter(dummyParcel));
             BufferedReader cReader = new BufferedReader(new FileReader(mainCusts));
             BufferedWriter cWriter = new BufferedWriter(new FileWriter(dummyCusts))) {

            String pLine;
            String cLine;
            // Read from main file and write to dummy file
            while ((pLine = pReader.readLine()) != null) {
                pWriter.write(pLine);
                pWriter.newLine(); // Write a new line to the dummy file
            }
            System.out.println("Dummy Parcel File Created.");
            while ((cLine = cReader.readLine()) != null) {
                cWriter.write(cLine);
                cWriter.newLine(); // Write a new line to the dummy file
            }
            System.out.println("Dummy Customer File Created.");
        } catch (IOException e) {
            System.out.println("An error occurred while creating the dummy files.");
            e.printStackTrace();
        }
    }


    public ArrayList<String[]> pStoring(File file) {


        try (BufferedReader dummyPReader = new BufferedReader(new FileReader(file))) {

            String line;
            while ((line = dummyPReader.readLine()) != null) {
                String[] values = line.split(",");
                pStorage.add(values);
            }

        } catch (IOException e) {
            System.out.println("An error occurred while creating the dummy file.");
            e.printStackTrace();
        }
        return pStorage;
    }

    public ArrayList<String[]> getpStorage() {
        return this.pStorage;
    }

    public ArrayList<String[]> cStoring(File file) {


        try (BufferedReader dummyCReader = new BufferedReader(new FileReader(file))) {

            String line;
            while ((line = dummyCReader.readLine()) != null) {
                String[] values = line.split(",");
                cStorage.add(values);
            }

        } catch (IOException e) {
            System.out.println("An error occurred while creating the dummy file.");
            e.printStackTrace();
        }

        return cStorage;
    }

    public ArrayList<String[]> getcStorage(){
        return this.cStorage;
    }


    public ParcelMap parcelInstantiation(ParcelMap parcelMap) {
        log.addEntry("Initializing parcels...");


        for (String[] x : pStorage) {
            if (x.length == 7) {
                String parcelID = x[0];
                double weight = Double.parseDouble(x[1]);
                String dimensions = String.join(" ", x[2], x[3], x[4]);
                int days = Integer.parseInt(x[5]);
                String surname = x[6];
                Parcel parcel = new Parcel(parcelID, weight, dimensions, days, surname);
                parcelMap.addParcel(parcel);
                log.addEntry("Parcel Instantiated successfully!");
            } else {
                System.out.println("Invalid Format. Try again");
                log.addEntry("Invalid Format. Try again");
            }
        }

        return parcelMap;
    }

    public ParcelMap singleParcelForFileInstantiation(ParcelMap parcelMap, String[] x) {
        if (x.length == 7){
            String parcelID = x[0];
            double weight = Double.parseDouble(x[1]);
            String dimensions = String.join(" ", x[2], x[3], x[4]);
            int days = Integer.parseInt(x[5]);
            String surname = x[6];
            Parcel parcel = new Parcel(parcelID, weight, dimensions, days, surname);
            parcelMap.addParcel(parcel);
            log.addEntry("Parcel Instantiated successfully!");

        }
        else {
            System.out.println("Invalid Format. Try again");
            log.addEntry("Invalid Format. Try again");
        }
        return parcelMap;
    }

    public ParcelMap singleParcelInputInstantiation(ParcelMap parcelMap, String[] x) {
        if (x.length == 5){
            String parcelID = x[0];
            double weight = Double.parseDouble(x[1]);
            String dimensions = x[2];
            int days = Integer.parseInt(x[3]);
            String surname = x[4];
            Parcel parcel = new Parcel(parcelID, weight, dimensions, days, surname);
            parcelMap.addParcel(parcel);
            log.addEntry("Parcel Instantiated successfully!");

        }
        else {
            System.out.println("Invalid Format. Try again");
            log.addEntry("Invalid Format. Try again");
        }
        return parcelMap;
    }

    public QueueOfCustomers customerInstantiation(QueueOfCustomers queue) {
        log.addEntry("Initializing customers...");

        for (String[] x : cStorage) {
            if (x.length == 2) {
                String FullName = x[0];
                String parcelID = x[1];
                Customer customer = new Customer(FullName, parcelID);
                queue.addCustomer(customer);
                log.addEntry("Customer Instantiated successfully!");
            } else {
                System.out.println("Invalid Format. Try again");
                log.addEntry("Invalid Format. Try again");
            }
        }
        return queue;
    }


    public QueueOfCustomers singleCustomerInstantiation(QueueOfCustomers queue, String[] x) {
        if (x.length == 2) {
            String FullName = x[0];
            String parcelID = x[1];
            Customer customer = new Customer(FullName, parcelID);
            queue.addCustomer(customer);
            log.addEntry("Customer Instantiated successfully!");

        } else {
            System.out.println("Invalid Format. Try again");
            log.addEntry("Invalid Format. Try again");
        }
        return queue;
    }
}

