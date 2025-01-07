public class Parcel {
    private String parcelID;
    private double weight;
    private String dimensions;
    private int daysInDepot;
    private boolean Status;
    private String surname;

    // Constructor
    public Parcel(String parcelID,double weight,String dimensions,int daysInDepot,boolean Status,String surname) {
        this.parcelID = parcelID;
        this.weight = weight;
        this.dimensions = dimensions;
        this.daysInDepot = daysInDepot;
        this.Status = true;
        this.surname = surname;
    }

    public String getParcelID() {
        return parcelID;
    }

    // Weight Getter
    public double getWeight() {
        return weight;
    }

    // Dimensions Getter
    public String getDimensions(){
        return dimensions;
    }

    // Days in Depot Getter
    public int getDaysInDepot(){
        return daysInDepot;
    }

    // Status Getter
    public boolean getStatus() {
        return Status;
    }

    // Surname Getter
    public String getSurname() {
        return surname;
    }

    // Parcel ID Setter
    public void setParcelID(String parcelID) {
        this.parcelID = parcelID;
    }

    // Weight Setter

    public void setWeight(double weight){
        this.weight = weight;
    }

    // Dimensions Setter
    public void setDimensions(String dimensions){
        this.dimensions = dimensions;
    }

    // Days in Depot Setter
    public void setDaysInDepot(int daysInDepot){
        this.daysInDepot = daysInDepot;
    }

    // Status Setter
    public void setStatus(boolean Status) {
        this.Status = Status;
    }

    // Surname Getter
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String toString() {
        return "ParcelID : "+parcelID+"\nDimensions : "+dimensions+"\nWeight : "+weight+"\nDays In Depot : "+daysInDepot+"\nStatus : "+Status+"\nSurname : "+surname+"\n";
    }
}
