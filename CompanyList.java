import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.*;

class CompanyList {
    private List<SRCompany> companies;

    // Constructor, addCompany method and findCompanyByNumber remain unchanged
    public CompanyList() {
        this.companies = new ArrayList<>();
    }

     // Method to add a company to the list
    public void addCompany(SRCompany company) {
        this.companies.add(company);
    }

    public List<SRCompany> getCompanies() {
        return companies;
    }
    
    
    // Summary statistics methods
    public double getAverageSharePrice() {
        return companies.stream()
                .mapToDouble(SRCompany::getAverageShareprice)
                .average()
                .orElse(Double.NaN);
    }

    public SRCompany getCompanyWithHighestSharePrice() {
        return companies.stream()
                .max(Comparator.comparingDouble(SRCompany::getAverageShareprice))
                .orElse(null);
    }

    public double getMaxSharePrice() {
        return companies.stream()
                .flatMapToInt(company -> Arrays.stream(company.getSharepriceArray()))
                .max()
                .orElse(Integer.MIN_VALUE);
    }

    public double getMinSharePrice() {
        return companies.stream()
                .flatMapToInt(company -> Arrays.stream(company.getSharepriceArray()))
                .min()
                .orElse(Integer.MAX_VALUE);
    }

    public Map<Integer, Long> getSharePriceFrequency() {
        return companies.stream()
                .flatMapToInt(company -> Arrays.stream(company.getSharepriceArray()))
                .boxed()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }
}
