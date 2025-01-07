public class Worker {
    private String workerID;
    private String workerName;

    public Worker() {
        this.workerID = workerID;
        this.workerName = workerName;
    }

    public double calculateFee(Parcel p) {
        // Constants for the calculation
        double baseFee = 5.0; // Base fee in currency units
        double dimensionFactorMultiplier = 0.01; // Multiplier for volume
        double weightFactorMultiplier = 2.0; // Multiplier for weight
        double daysFactorMultiplier = 0.5; // Multiplier for days in depot

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
