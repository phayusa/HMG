package Model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import tools.CSVReader;
import tools.GraphicalEntity;

/**
 * Created by Micdu95 on 11/11/2016.
 */
public class FactoryModel extends GraphicalEntity {
    private String name;
    private double budget;
    private static CSVReader csvReader;
    private ArrayList<PersonModel> EmployeeOfFactory;

    public FactoryModel(){
        EmployeeOfFactory = new ArrayList<PersonModel>();
        loadCSVFile();
        generateCSVFile();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public ArrayList<PersonModel> getEmployeeOfFactory() {
        return EmployeeOfFactory;
    }

    public void setEmployeeOfFactory(ArrayList<PersonModel> employeeOfFactory) {
        EmployeeOfFactory = employeeOfFactory;
    }
    
    public void loadCSVFile () {
    	String csvFile = "Ressource/files/test.csv";
    	ArrayList<PersonModel> employeeOfFactory = CSVReader.getCSVFile(csvFile, this.EmployeeOfFactory);
        this.EmployeeOfFactory = employeeOfFactory;
    }
    
    public void generateCSVFile () {
    	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
    	Date date = new Date();
    	String csvFile = "Ressource/files/test_" + dateFormat.format(date) + ".csv";
    	CSVReader.generateCsvFile(csvFile, this.EmployeeOfFactory);
    	
    }
  
}
