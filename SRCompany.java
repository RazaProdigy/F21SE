import java.io.*;
import java.util.*;
import java.util.stream.*;

public class SRCompany {

    // Instance variables
    private int companyNumber;
    private String companyName;
    private String[] productsOrServices;
    private int stockExchangeRanking;
    private String additionalAttribute;
    private int[] sharePrices; // Array of integer share prices

    // Constructor
    public SRCompany(int companyNumber, String companyName, String[] productsOrServices,
                     int stockExchangeRanking, String additionalAttribute, int[] sharePrices) {
        this.companyNumber = companyNumber;
        this.companyName = companyName;
        this.productsOrServices = productsOrServices;
        this.stockExchangeRanking = stockExchangeRanking;
        this.additionalAttribute = additionalAttribute;
        this.sharePrices = sharePrices; // Initialize the sharePrices array
    }

    // Get and Set methods for each instance variable
    // ...

    // Method to get the share price array
    public int[] getSharepriceArray() {
        return sharePrices;
    }

     // Method to get the share price array
    public int getCompanyNumber() {
        return companyNumber;
    }

     // Method to get the share price array
    public String getCompanyName() {
        return companyName;
    }

    // Method to calculate the average share price
    public double getAverageShareprice() {
        // Example calculation: average of share prices disregarding the highest and lowest prices
        Arrays.sort(sharePrices); // Sort the array to easily find and exclude the highest and lowest
        int sum = 0;
        for (int i = 1; i < sharePrices.length - 1; i++) { // Start from 1 and end at length - 1 to exclude highest and lowest
            sum += sharePrices[i];
        }
        // Calculate the average. Cast to double to get decimal number.
        return (double) sum / (sharePrices.length - 2);
    }

    // Method to get full details of the company including share prices
    public String getFullDetails() {
        String details = String.format("Company number %03d, name %s, country %s.\n%s is a %s, founded 20 years ago and has a past five-day share price: ",
                                       companyNumber, companyName, additionalAttribute, companyName, String.join(", ", productsOrServices));
        for (int i = 0; i < sharePrices.length; i++) {
            details += sharePrices[i];
            if (i < sharePrices.length - 1) {
                details += ", ";
            }
        }
        details += String.format("\nTherefore, they have an average unaltered share price of %.1f.", getAverageShareprice());
        return details;
    }

    // Static method to parse a CSV line into a SRCompany object
    public static SRCompany parseCSV(String line) {
        String[] tokens = line.split(",");
        int companyNumber = Integer.parseInt(tokens[0]);
        String companyName = tokens[1];
        String[] productsOrServices = Arrays.copyOfRange(tokens, 2, 3); // Assuming only one product/service per company for this example
        int stockExchangeRanking = Integer.parseInt(tokens[3]);
        String additionalAttribute = tokens[4];
        int[] sharePrices = Arrays.stream(tokens, 5, tokens.length).mapToInt(Integer::parseInt).toArray();
        return new SRCompany(companyNumber, companyName, productsOrServices, stockExchangeRanking, additionalAttribute, sharePrices);
    }

    // Method to get short details of the company remains unchanged
    // ...

    // Main method to test the class
    public static void main(String[] args) {
        String companyName = "Tesla Inc"; // Direct String assignment
        String[] products = {"Cars", "Batteries", "Solar Panels"}; // Example products/services
        int[] sharePrices = {246, 244, 242, 259, 244}; // Example share prices
        SRCompany company = new SRCompany(1, companyName, products, 1, "USA", sharePrices);
        System.out.println(company.getFullDetails());
        // Test other methods as needed
    }


}





