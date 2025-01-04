import java.util.Map;
import java.util.TreeMap;

public class ParcelMap {

    private Map<String, Parcel> parcelMap;
    private Map<String, Parcel> collectedMap;


    public ParcelMap() {
        this.parcelMap = new TreeMap<>();
        this.collectedMap = new TreeMap<>();
    }

}
