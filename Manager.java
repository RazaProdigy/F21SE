import java.io.*;
import java.util.*;
import java.util.stream.*;


public class Manager {
    private static final String PASSWORD = "raza"; // This should be stored securely in practice
    private CompanyList companyList;

    public Manager() {
        companyList = new CompanyList();
    }

    public void readCompaniesFromFile(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                SRCompany company = SRCompany.parseCSV(line);
                companyList.addCompany(company);
            }
        }
    }

    public void produceReport(String filename) throws FileNotFoundException {
        try (PrintWriter out = new PrintWriter(filename)) {
            // Print full details for all companies
            for (SRCompany company : companyList.getCompanies()) {
                out.println("Full details for " + String.format("%03d", company.getCompanyNumber()) + ":");
                out.println(company.getFullDetails());
                out.println(); // Empty line for readability
            }

            // Find and print the company with the highest share price
            SRCompany highestSharePriceCompany = companyList.getCompanyWithHighestSharePrice();
            if (highestSharePriceCompany != null) {
                out.println("The company with the highest average share price is " +
                            highestSharePriceCompany.getCompanyName() +
                            " with an average share price of " +
                            highestSharePriceCompany.getAverageShareprice() + ".");
            }

            // Print summary statistics
            out.println("STATISTICAL SUMMARY:");
            out.println("There are " + companyList.getCompanies().size() + " companies.");

            Map<Integer, Long> frequencyMap = companyList.getSharePriceFrequency();
            out.println("The following individual share prices were awarded with their frequency:");
            frequencyMap.forEach((sharePrice, freq) -> out.println("Share Price: " + sharePrice + " Frequency: " + freq));

            // Additional summary statistics can be added here as needed
        }
    }

    // Implement password check
    public boolean checkPassword(String inputPassword) {
        return PASSWORD.equals(inputPassword);
    }

    // Main method
    public static void main(String[] args) {
        Manager manager = new Manager();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        if (!manager.checkPassword(password)) {
            System.out.println("Invalid password!");
            return;
        }

        // Read companies from CSV file
        String csvFile = "companies.csv";
        try {
            manager.readCompaniesFromFile(csvFile);
            System.out.println("Company data loaded successfully.");
        } catch (FileNotFoundException e) {
            System.err.println("CSV file not found: " + e.getMessage());
            scanner.close();
            return;
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
            scanner.close();
            return;
        }

        // Produce and save the report to a text file
        String reportFile = "report.txt";
        try {
            manager.produceReport(reportFile);
            System.out.println("Report generated successfully. Check " + reportFile);
        } catch (FileNotFoundException e) {
            System.err.println("Error writing report file: " + e.getMessage());
        }


    }
}