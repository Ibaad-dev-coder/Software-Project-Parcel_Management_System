public class Parcel {
    private String parcelID;
    private double weight;
    private String dimensions;
    private int daysInDepot;
    private String Status;
    private String surname;
    private String compositeKey;
    private Log log = Log.getInstance();

    // Constructor
    public Parcel(String parcelID,double weight,String dimensions,int daysInDepot,String surname) {
        this.parcelID = parcelID;
        this.weight = weight;
        this.dimensions = dimensions;
        this.daysInDepot = daysInDepot;
        this.Status = "Unregistered";
        this.surname = surname;
        this.compositeKey = this.getSurname()+"_"+this.getParcelID();
        log.addEntry("Parcel created with ID: " + parcelID);
    }

    // Parcel ID Getter
    public String getParcelID() {
        log.addEntry("getParcelID() called");
        return parcelID;
    }

    // Weight Getter
    public double getWeight() {
        log.addEntry("getWeight() called");
        return weight;
    }

    // Dimensions Getter
    public String getDimensions(){
        log.addEntry("getDimensions() called");
        return dimensions;
    }

    // Days in Depot Getter
    public int getDaysInDepot(){
        log.addEntry("getDaysInDepot() called");
        return daysInDepot;
    }

    // Status Getter
    public String getStatus() {
        log.addEntry("getStatus() called");
        return Status;
    }

    // Surname Getter
    public String getSurname() {
        log.addEntry("getSurname() called");
        return surname;
    }

    public String getCompositeKey() {
        log.addEntry("getCompositeKey() called");
        return compositeKey;
    }

    // Parcel ID Setter
    public void setParcelID(String parcelID) {
        this.parcelID = parcelID;
        log.addEntry("setParcelID() called with value: " + parcelID);
    }

    // Weight Setter

    public void setWeight(double weight){
        this.weight = weight;
        log.addEntry("setWeight() called with value: " + weight);
    }

    // Dimensions Setter
    public void setDimensions(String dimensions){
        this.dimensions = dimensions;
        log.addEntry("setDimensions() called with value: " + dimensions);
    }

    // Days in Depot Setter
    public void setDaysInDepot(int daysInDepot){
        this.daysInDepot = daysInDepot;
        log.addEntry("setDaysInDepot() called with value: " + daysInDepot);
    }

    // Status Setter
    public void setStatus(String Status) {
        this.Status = Status;
        log.addEntry("setStatus() called with value: " + Status);
    }

    // Surname Getter
    public void setSurname(String surname) {
        this.surname = surname;
        log.addEntry("setSurname() called with value: " + surname);
    }

    public String toString() {
        log.addEntry("toString() called for parcel ID: " + parcelID);
        return "ParcelID : "+parcelID+"\nDimensions : "+dimensions+"\nWeight : "+weight+"\nDays In Depot : "+daysInDepot+"\nStatus : "+Status+"\nSurname : "+surname+"\n";
    }


}