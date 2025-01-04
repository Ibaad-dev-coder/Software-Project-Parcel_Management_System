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
        this.Status = Status;
        this.surname = surname;

    }
}
