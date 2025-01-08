import java.util.Map;
import java.util.TreeMap;

public class ParcelMap {
    private Map<String, Parcel> parcelMap;
    private Map<String, Parcel> collectedMap;
    private String compositeKey;
    private Log log = Log.getInstance();



    public ParcelMap() {
        this.parcelMap = new TreeMap<>();
        this.collectedMap = new TreeMap<>();
        log.addEntry("Waiting List for Parcels created:");
        log.addEntry("Collected List for Parcels created:");


    }

    public void addParcel(Parcel parcel) {
        compositeKey = parcel.getSurname()+"_"+parcel.getParcelID();
        parcelMap.put(compositeKey, parcel);
        parcel.setStatus("Waiting for Collection");
        log.addEntry("Parcel added with Unique ID: " + compositeKey);
        System.out.println("Parcel added successfully");
    }

    public void moveParcelToCollected(String compositeKey) {
        if(parcelMap.containsKey(compositeKey)) {
            Parcel parcel = parcelMap.remove(compositeKey);
            parcel.setStatus("Collected");
            collectedMap.put(compositeKey, parcel);
            log.addEntry("Parcel added with Unique ID: " + compositeKey + " has been collected and removed form the waiting list");
        }
        else{
            System.out.println("The Parcel does not exist or has been removed or Your inputted format might be wrong. Format : surname_parcelID");
            log.addEntry("Parcel added with Unique ID: " + compositeKey + " does not exist or has been removed or the inputted format might be wrong");
        }
    }





    public Parcel getParcelByUniqueID(String compositeKey) {
        Parcel parcel = parcelMap.get(compositeKey);
        if (parcel != null) {
            log.addEntry("Parcel retrieved: " + compositeKey);
        } else {
            log.addEntry("Parcel not found with ID: " + compositeKey);
        }
        return parcel;
    }
    public Map<String, Parcel> getWaitingMap() {
        return parcelMap;
    }

    public Map<String, Parcel> getCollectedMap() {
        return collectedMap;
    }

    public String printWaitingList() {
        StringBuilder sb = new StringBuilder();
        sb.append("ParcelMap Contents (Waiting to be collected):\n\n");
        for (Map.Entry<String, Parcel> entry : getWaitingMap().entrySet()) {
            String compositeKey = entry.getKey();
            Parcel parcel = entry.getValue();

            sb.append("Unique ID: ").append(compositeKey)
                    .append("\n"+parcel)
                    .append("\n");
        }

        return sb.toString();
    }

    public String printCollectedList() {
        StringBuilder sb = new StringBuilder();
        sb.append("ParcelMap Contents (Collected):\n\n");
        for (Map.Entry<String, Parcel> entry : getCollectedMap().entrySet()) {
            String compositeKey = entry.getKey();
            Parcel parcel = entry.getValue();

            sb.append("Unique ID: ").append(compositeKey)
                    .append("\n"+parcel)
                    .append("\n");
        }

        return sb.toString();
    }


    public void delWaitingList() {
        this.getWaitingMap().clear();
    }

    public void delCollectedList() {
        this.getCollectedMap().clear();;
    }

}
