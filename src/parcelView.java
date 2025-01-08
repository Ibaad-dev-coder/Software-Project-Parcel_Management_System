import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class parcelView {

    private JFrame frame;
    private JPanel parcelPanel, customerPanel, buttonPanel;
    private JTextArea parcelListArea, customerQueueArea, logArea;
    private JButton processButton, addParcelButton, addCustomerButton, registerParcelButton, showParcelsButton, showQueueButton;
    private JButton selectParcelFileButton, selectCustomerFileButton, queueCustomerButton;
    private List<String[]> parcelData = new ArrayList<>();
    private List<String[]> customerData = new ArrayList<>();
    private Map<String, Parcel> registeredParcels = new TreeMap();
    private List<Customer> queuedCustomers = new ArrayList<>();
    private int currentParcelIndex = 0;
    private int currentCustomerIndex = 0;
    private ParcelMap parcelMap = new ParcelMap();
    private QueueOfCustomers queue = new QueueOfCustomers();
    private Manager manage = new Manager(parcelMap,queue);
    private Worker worker = new Worker(parcelMap,queue);
    Log log = Log.getInstance();

    public parcelView() {
        initializeGUI();
    }

    private void initializeGUI() {
        // Main Frame
        frame = new JFrame("Parcel Management System");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Button Panel
        buttonPanel = new JPanel(new FlowLayout());
        processButton = new JButton("Process Next Customer");
        addParcelButton = new JButton("Add Parcel");
        addCustomerButton = new JButton("Add Customer");
        registerParcelButton = new JButton("Register Parcel");
        showParcelsButton = new JButton("Show Parcels");
        showQueueButton = new JButton("Show Queue");
        selectParcelFileButton = new JButton("Select Parcel File");
        selectCustomerFileButton = new JButton("Select Customer File");
        queueCustomerButton = new JButton("Queue Up Customer");

        buttonPanel.add(processButton);
        buttonPanel.add(addParcelButton);
        buttonPanel.add(addCustomerButton);
        buttonPanel.add(registerParcelButton);
        buttonPanel.add(showParcelsButton);
        buttonPanel.add(showQueueButton);
        buttonPanel.add(selectParcelFileButton);
        buttonPanel.add(selectCustomerFileButton);
        buttonPanel.add(queueCustomerButton);

        // Parcel Panel
        parcelPanel = new JPanel(new BorderLayout());
        parcelPanel.setBorder(BorderFactory.createTitledBorder("Parcels"));
        parcelListArea = new JTextArea();
        parcelListArea.setEditable(false);
        JScrollPane parcelScrollPane = new JScrollPane(parcelListArea);
        parcelPanel.add(parcelScrollPane, BorderLayout.CENTER);

        // Customer Panel
        customerPanel = new JPanel(new BorderLayout());
        customerPanel.setBorder(BorderFactory.createTitledBorder("Customer Queue"));
        customerQueueArea = new JTextArea();
        customerQueueArea.setEditable(false);
        JScrollPane customerScrollPane = new JScrollPane(customerQueueArea);
        customerPanel.add(customerScrollPane, BorderLayout.CENTER);

        // Log Area
        logArea = new JTextArea(10, 50);
        logArea.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(logArea);

        // Adding Action Listeners
        processButton.addActionListener(new ProcessCustomerListener());
        addParcelButton.addActionListener(new AddParcelListener());
        addCustomerButton.addActionListener(new AddCustomerListener());
        registerParcelButton.addActionListener(new RegisterParcelListener());
        showParcelsButton.addActionListener(new ShowParcelsListener());
        showQueueButton.addActionListener(new ShowQueueListener());
        selectParcelFileButton.addActionListener(new SelectParcelFileListener());
        selectCustomerFileButton.addActionListener(new SelectCustomerFileListener());
        queueCustomerButton.addActionListener(new QueueCustomerListener());

        // Layout Management
        frame.add(buttonPanel, BorderLayout.NORTH);
        frame.add(parcelPanel, BorderLayout.WEST);
        frame.add(customerPanel, BorderLayout.EAST);
        frame.add(logScrollPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }



    private void updateParcelList(Map<String,Parcel> parcels) {
        StringBuilder displayText = new StringBuilder();

        for (Map.Entry<String, Parcel> entry : parcels.entrySet()) {
            String parcelId = entry.getKey();
            Parcel parcel = entry.getValue();

            displayText.append("ID: ").append(parcelId)
                    .append(", Details: ").append(parcel.toString())
                    .append("\n");
        }

        parcelListArea.setText(displayText.toString());
        log.addEntry("parcelListArea updated");
    }



    private void updateCustomerQueue(List<Customer> customers) {
        StringBuilder displayText = new StringBuilder();

        for (Customer customer : customers) {
            // Customize what details to display for each customer
            displayText.append(customer.toString()).append("\n");
        }

        customerQueueArea.setText(displayText.toString());
        log.addEntry("customerQueueArea updated");

    }



    private void logEvent(String event) {
        logArea.append(event + "\n");
    }

    // Action Listeners
    private class ProcessCustomerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            logEvent("Processing next customer.");
            int i = 0;

            while (!queue.getWaitingQueue().isEmpty() && i < queue.getWaitingQueue().size()) {

                Customer customer = queue.getWaitingQueue().get(i);

                // Display a dialog with customer details and ask for confirmation
                int choice = JOptionPane.showConfirmDialog(frame,
                        "Processing Customer:\n" +
                                "Name: " + customer.getCustomerName() + "\n" +
                                "Queue No: " + customer.getQueueNo() + "\n" +
                                "Parcel ID: " + customer.getParcelID() + "\n\n" +
                                "Would you like to process this customer?",
                        "Process Customer",
                        JOptionPane.YES_NO_OPTION);

                if (choice == JOptionPane.YES_OPTION) {
                    // Generate the composite key
                    String compositeKey = customer.getSurname() + "_" + customer.getParcelID();
                    System.out.println(compositeKey);
                    Parcel parcel = parcelMap.getParcelByUniqueID(compositeKey);
                    System.out.println(parcel);

                    if (parcel != null ) {
                        // Calculate the fee
                        double fee = worker.calculateFee(parcel);

                        // Release the parcel

                        // Display confirmation
                        JOptionPane.showMessageDialog(
                                frame,
                                "Customer " + customer.getCustomerName() + " with Parcel ID " + customer.getParcelID() +
                                        " has been processed.\nFee: " + fee,
                                "Success",
                                JOptionPane.INFORMATION_MESSAGE
                        );

                        // Move the customer to the collected list
                        parcelMap = worker.releaseParcel(compositeKey);
                        queue.moveCustomerToCollected(customer);

                        // Remove processed customer and parcel details from display
                        updateDisplayAfterProcessing(customer, parcel);
                    } else {
                        // Display parcel not available or mismatched message
                        JOptionPane.showMessageDialog(
                                frame,
                                "Parcel with ID " + customer.getParcelID() + " does not match or is unavailable.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }

                    // Remove the processed or mismatched customer from the queue
                    i++;
                } else {
                    // User chose not to process
                    JOptionPane.showMessageDialog(frame, "Customer processing canceled.", "Canceled", JOptionPane.INFORMATION_MESSAGE);
                    break;
                }

            }

            // If the queue is empty after processing
            if (queue.getWaitingQueue().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No more customers in the queue.", "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        }

        // Method to update the display after processing a customer
        public void updateDisplayAfterProcessing(Customer customer, Parcel parcel) {
            String pStr = parcelListArea.getText();
            String cStr = customerQueueArea.getText();

            StringBuilder pBuilder = new StringBuilder(pStr);
            StringBuilder cBuilder = new StringBuilder(cStr);

            String startPMarker = parcel.getCompositeKey();
            String endPMarker = "Surname";

            String startCMarker = customer.getCustomerName();
            String endCMarker = "Queue";

            int startPIndex = pBuilder.indexOf(startPMarker) - 4;
            System.out.println(startPIndex);
            int endPIndex = pBuilder.indexOf(endPMarker) + endPMarker.length() + 3;
            System.out.println(pBuilder.indexOf(endPMarker));

            int startCIndex = cBuilder.indexOf(startCMarker) - 15;
            System.out.println(startCIndex);
            int endCIndex = cBuilder.indexOf(endCMarker) + endCMarker.length() + 6;
            System.out.println(cBuilder.indexOf(endCMarker));

            // Delete the content between and including the markers
            if (startPIndex != -1 && endPIndex != -1 && endPIndex > startPIndex) {
                pBuilder.delete(startPIndex, endPIndex);
            } else {
                System.out.println("Start or end marker of parcel not found!");
            }
            if (startCIndex != -1 && endCIndex != -1 && endCIndex > startCIndex) {
                cBuilder.delete(startCIndex, endCIndex);
            } else {
                System.out.println("Start or end marker of Customer not found!");
            }
            parcelListArea.setText("");
            customerQueueArea.setText("");
            parcelListArea.setText(pBuilder.toString());
            customerQueueArea.setText(cBuilder.toString());
            logEvent("Successfully processed, removing parcel and customer from waiting list...");
            log.addEntry("Successfully processed, removing parcel and customer from waiting list...");


        }
    }

    private class AddParcelListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTextField parcelIdField = new JTextField();
            JTextField weightField = new JTextField();
            JTextField dimensionsField = new JTextField();
            JTextField daysField = new JTextField();
            JTextField surnameField = new JTextField();

            JPanel inputPanel = new JPanel(new GridLayout(5, 2));
            inputPanel.add(new JLabel("Parcel ID:"));
            inputPanel.add(parcelIdField);
            inputPanel.add(new JLabel("Weight:"));
            inputPanel.add(weightField);
            inputPanel.add(new JLabel("Dimensions:"));
            inputPanel.add(dimensionsField);
            inputPanel.add(new JLabel("Days in Depot:"));
            inputPanel.add(daysField);
            inputPanel.add(new JLabel("Surname:"));
            inputPanel.add(surnameField);

            int result = JOptionPane.showConfirmDialog(frame, inputPanel, "Add Parcel", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                String[] newParcel = {
                        parcelIdField.getText(),
                        weightField.getText(),
                        dimensionsField.getText(),
                        daysField.getText(),
                        surnameField.getText()
                };
                manage.singleParcelInputInstantiation(parcelMap, newParcel);
                System.out.println(parcelMap.printWaitingList());
                registeredParcels = parcelMap.getWaitingMap();
                logEvent("Added new parcel: " + newParcel[0]);
                updateParcelList(registeredParcels);
            } else {
                logEvent("Parcel addition canceled.");
                log.addEntry("Parcel addition canceled.");
            }
        }
    }

    private class AddCustomerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTextField nameField = new JTextField();
            JTextField parcelIdField = new JTextField();

            JPanel inputPanel = new JPanel(new GridLayout(2, 2));
            inputPanel.add(new JLabel("Customer Name:"));
            inputPanel.add(nameField);
            inputPanel.add(new JLabel("Parcel ID:"));
            inputPanel.add(parcelIdField);

            int result = JOptionPane.showConfirmDialog(frame, inputPanel, "Add Customer", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                String[] newCustomer = {
                        nameField.getText(),
                        parcelIdField.getText()
                };
                manage.singleCustomerInstantiation(queue,newCustomer);
                queuedCustomers = queue.getWaitingQueue();
                logEvent("Added new customer: " + newCustomer[0]);
                updateCustomerQueue(queuedCustomers);
            } else {
                logEvent("Customer addition canceled.");
                log.addEntry("Customer addition canceled.");
            }
        }
    }

    private class RegisterParcelListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (currentParcelIndex < parcelData.size()) {
                String[] parcel = parcelData.get(currentParcelIndex);
                String parcelDetails = "Parcel ID: " + parcel[0] + "\nWeight: " + parcel[1] + "\nDimensions: " + parcel[2] + "\nDays in Depot: " + parcel[3] + "\nSurname: " + parcel[4];

                int result = JOptionPane.showConfirmDialog(frame, "Do you want to register this parcel?\n" + parcelDetails, "Register Parcel", JOptionPane.YES_NO_OPTION);

                if (result == JOptionPane.YES_OPTION) {
                    manage.singleParcelForFileInstantiation(parcelMap, parcel);
                    registeredParcels = parcelMap.getWaitingMap();
                    logEvent("Registered parcel: " + parcel[0]);
                    currentParcelIndex++;
                    updateParcelList(registeredParcels);
                } else {
                    logEvent("Parcel registration canceled.");
                    log.addEntry("Parcel registration canceled.");
                }
            } else {
                logEvent("No more parcels to register.");
                log.addEntry("No more parcels to register.");
            }
        }
    }

    private class ShowParcelsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!registeredParcels.isEmpty()) {
                logEvent("Displaying registered parcels.");
                System.out.println(parcelMap.printWaitingList());
                updateParcelList(registeredParcels);
            } else {
                logEvent("No parcels registered yet.");
                parcelListArea.setText("No parcels registered.");
            }
        }
    }

    private class ShowQueueListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!queuedCustomers.isEmpty()) {
                logEvent("Displaying customer queue.");
                updateCustomerQueue(queuedCustomers);
            } else {
                logEvent("No customers queued yet.");
                customerQueueArea.setText("No customers queued.");
            }
        }
    }

    private class SelectParcelFileListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                loadParcelData(file);
            } else {
                logEvent("Parcel file selection canceled.");
            }
        }
    }

    private class SelectCustomerFileListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                loadCustomerData(file);
            } else {
                logEvent("Customer file selection canceled.");
            }
        }
    }

    private class QueueCustomerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (currentCustomerIndex < customerData.size()) {
                String[] customer = customerData.get(currentCustomerIndex);
                String customerDetails = "Customer Name: " + customer[0] + "\nParcel ID: " + customer[1];

                int result = JOptionPane.showConfirmDialog(frame, "Do you want to queue this customer?\n" + customerDetails, "Queue Customer", JOptionPane.YES_NO_OPTION);

                if (result == JOptionPane.YES_OPTION) {
                    manage.singleCustomerInstantiation(queue,customer);
                    queuedCustomers = queue.getWaitingQueue();
                    logEvent("Queued customer: " + customer[0]);
                    currentCustomerIndex++;
                    updateCustomerQueue(queuedCustomers);
                } else {
                    logEvent("Customer queueing canceled.");
                }
            } else {
                logEvent("No more customers to queue.");
            }
        }
    }

    // File Loading Methods
    private void loadParcelData(File file) {
        parcelMap.delWaitingList();
        parcelMap.delCollectedList();
        parcelData.clear();
        parcelData = manage.pStoring(file);



        logEvent("Parcel data loaded successfully.");
    }


    private void loadCustomerData(File file) {
        queue.delWaitingQueue();
        queue.delCollectedQueue();
        customerData.clear();
        customerData = manage.cStoring(file);
        logEvent("Customer data loaded successfully.");


    }

}
