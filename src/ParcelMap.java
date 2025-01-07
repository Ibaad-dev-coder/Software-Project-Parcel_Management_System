import java.util.Map;
import java.util.TreeMap;

public class ParcelMap {
    private Map<String, Parcel> parcelMap;
    private Map<String, Parcel> collectedMap;


    public ParcelMap() {
        this.parcelMap = new TreeMap<>();
        this.collectedMap = new TreeMap<>();
    }

    public void addParcel(Parcel parcel) {
        parcelMap.put(parcel.getSurname(), parcel);
    }

    public void moveParcelToCollected(Parcel parcel) {
        if(parcelMap.containsKey(parcel.getSurname())) {
            parcelMap.remove(parcel.getSurname());
            parcel.setStatus(false);
            collectedMap.put(parcel.getSurname(), parcel);
        }

    }

    public Parcel getParcel(String parcelname) {
        return parcelMap.get(parcelname);
    }

    public Map<String, Parcel> getWaitingMap() {
        return parcelMap;
    }

    public Map<String, Parcel> getcollectdMap() {
        return collectedMap;
    }

    public void waitingPrintMap() {
        System.out.println(getWaitingMap());
    }

    public void collectdPrintMap() {
        System.out.println(getcollectdMap());
    }
}
