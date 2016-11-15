package Model;

import tools.CSVReader;
import tools.GraphicalEntity;
import tools.HardCodedParameters;
import tools.Position;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Micdu95 on 11/11/2016.
 */
public class FactoryModel extends GraphicalEntity {
    private String name;
    private double budget;
    private static CSVReader csvReader;
    private ArrayList<PersonModel> EmployeeOfFactory;
    private ArrayList<GraphicalEntity> Offices;

    public FactoryModel(GraphicalEntity factoryEntity){
        super(factoryEntity);
        EmployeeOfFactory = new ArrayList<PersonModel>();
        loadCSVFile();
        generateCSVFile();
        Offices = new ArrayList<GraphicalEntity>();
        double deltaToMove = width/5;
        double deltaHeight = height/12;
        int halfOffice = HardCodedParameters.numberOfficeInFactory/2;
        for(int i=1;i<=HardCodedParameters.numberOfficeInFactory;i++){
            if(i<=halfOffice)
                Offices.add(new GraphicalEntity(new Position(positionOfEntity.x + i * deltaToMove,positionOfEntity.y + deltaHeight),HardCodedParameters.OfficeWidth,HardCodedParameters.OfficeHeight,"file:Ressource/images/tableFront.png"));
            else
                Offices.add(new GraphicalEntity(new Position(positionOfEntity.x + (i-halfOffice) * deltaToMove,positionOfEntity.y + height - deltaHeight * 4), HardCodedParameters.OfficeWidth,HardCodedParameters.OfficeHeight,"file:Ressource/images/tableBack.png"));
        }
        int i = 0;
        int numberIterate = 1;
        for (PersonModel e : EmployeeOfFactory){

            if(numberIterate%2==1) {
                e.setNewPosition(new Position(Offices.get(i).getPositionOfEntity().x + Offices.get(i).getWidth()/3, Offices.get(i).getPositionOfEntity().y));

            }else{
                e.setNewPosition(new Position(Offices.get(i).getPositionOfEntity().x + Offices.get(i).getWidth() - Offices.get(i).getWidth()/16 , Offices.get(i).getPositionOfEntity().y));
                i++;
            }
            numberIterate++;
        }

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
    	DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH mm ss");
    	Date date = new Date();
    	String csvFile = "Ressource/files/res.csv";
//    	String csvFile = "Ressource/files/res "+dateFormat.format(date)+".csv";
    	CSVReader.generateCsvFile(csvFile, this.EmployeeOfFactory);
    	
    }

    public ArrayList<GraphicalEntity> getOffices() {
        return Offices;
    }
}
