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

    public Manager() {
        this.managerId = managerId;
        this.managerName = managerName;
        this.employeeList = new ArrayList<>();
        this.taskList = new ArrayList<>();
        this.pStorage = new ArrayList<>();
        this.cStorage = new ArrayList<>();


    }

    File mainParcel = new File("src/Parcels.csv");
    File mainCusts = new File("src/Custs.csv");
    File dummyParcel = new File("dummyParcels.csv");
    File dummyCusts = new File("dummyCusts.csv");


    public void makeDummies() {
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


    public ArrayList<String[]> pStoring() {

        try (BufferedReader dummyPReader = new BufferedReader(new FileReader(dummyParcel))) {

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

    public ArrayList<String[]> cStoring() {

        try (BufferedReader dummyCReader = new BufferedReader(new FileReader(dummyCusts))) {

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


    public ParcelMap parcelInstantiation(ParcelMap parcelMap) {


        for (String[] x : pStorage) {
            String parcelID = x[0];
            int days = Integer.parseInt(x[5]);
            double weight = Double.parseDouble(x[1]);
            String dimensions = String.join(" ", x[2], x[3], x[4]);
            String surname = "";

            for (String[] y: cStorage){
                nameParts = y[0].split(" ");
                if(y[1].equals(x[0])) {
                    surname = nameParts[1];
                    break;
                }
            }

            Parcel parcel = new Parcel(parcelID, weight, dimensions, days, true, surname);
            parcelMap.addParcel(parcel);
        }

        return parcelMap;
    }

    public QueueOfCustomers customerInstantiation(QueueOfCustomers queue) {

        for (String[] x : cStorage) {
            String FullName = x[0];
            String parcelID = x[1];
            Customer customer = new Customer(FullName,parcelID);
            queue.addCustomer(customer);

        }
        return queue;

    }

}
