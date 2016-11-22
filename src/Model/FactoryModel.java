package Model;

import tools.CSVReader;
import tools.GraphicalEntity;
import tools.HardCodedParameters;
import tools.Position;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Micdu95 on 11/11/2016.
 */
public class FactoryModel extends GraphicalEntity {
    private String name;
    private double budget;
    private double averageSalaryByDay;
    private int numberOfEmployee;
    private static CSVReader csvReader;
    private ArrayList<PersonModel> EmployeeOfFactory;
    private ArrayList<OfficeModel> Offices;
//    private HashMap<Position,Boolean> occupiedOffices;
    private GraphicalEntity HideRoom;


    public FactoryModel(GraphicalEntity factoryEntity){
        super(factoryEntity);
//        Occupied_Offices = new HashMap<Position,Boolean>();
        budget = HardCodedParameters.startBudget;
        EmployeeOfFactory = new ArrayList<PersonModel>();
        loadCSVFile();
        generateCSVFile();
        Offices = new ArrayList<OfficeModel>();
        double deltaToMove = width/5;
        double deltaHeight = height/12;
        int halfOffice = HardCodedParameters.numberOfficeInFactory/2;
        for(int i=1;i<=HardCodedParameters.numberOfficeInFactory;i++){
            if(i<=halfOffice)
                Offices.add(new OfficeModel(new Position(positionOfEntity.x + i * deltaToMove,positionOfEntity.y + deltaHeight),HardCodedParameters.OfficeWidth,HardCodedParameters.OfficeHeight,"file:Ressource/images/tableFront.png"));
            else
                Offices.add(new OfficeModel(new Position(positionOfEntity.x + (i-halfOffice) * deltaToMove,positionOfEntity.y + height - deltaHeight * 4), HardCodedParameters.OfficeWidth,HardCodedParameters.OfficeHeight,"file:Ressource/images/tableBack.png"));
        }
        

        numberOfEmployee = EmployeeOfFactory.size();
        HideRoom = new GraphicalEntity(new Position(positionOfEntity.x + width - 100,HardCodedParameters.FactoryStartY+HardCodedParameters.FactoryHeight/2-50),100,100,"file:Ressource/images/Door.png");

        int i = 0;
//        int numberIterate = 1;
        averageSalaryByDay = 0;
        int numberOfPlace = HardCodedParameters.numberOfficeInFactory;
        int indexOfOffice = 0;
        for (PersonModel e : EmployeeOfFactory){

            if(indexOfOffice < numberOfPlace){
                OfficeModel avalaibleOffice = Offices.get(indexOfOffice);
                if(!avalaibleOffice.isOccupiedLeft()){
                    e.setNewPosition(avalaibleOffice.getPositionLeft());
                    e.setAssignedOffice(avalaibleOffice.getPositionLeft());
                    avalaibleOffice.setOccupiedLeft(true);
                    continue;
                }
                if(!avalaibleOffice.isOccupiedRight()){
                    e.setNewPosition(avalaibleOffice.getPositionRight());
                    e.setAssignedOffice(avalaibleOffice.getPositionRight());
                    avalaibleOffice.setOccupiedRight(true);
                    indexOfOffice++;
                    continue;
                }
            }else{
                e.setNewPosition(new Position(HideRoom.getPositionOfEntity().x+ HideRoom.getWidth(),HideRoom.getPositionOfEntity().y+HideRoom.getHeight()/2));
                e.setAssignedOffice(new Position(HideRoom.getPositionOfEntity().x+ HideRoom.getWidth(),HideRoom.getPositionOfEntity().y+HideRoom.getHeight()/2));
            }
            averageSalaryByDay += e.getSalaryByDay();
        }
        averageSalaryByDay/=EmployeeOfFactory.size();


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
        averageSalaryByDay = 0;
        for (PersonModel e : EmployeeOfFactory){
            averageSalaryByDay += e.getSalaryByDay();
        }
        numberOfEmployee = EmployeeOfFactory.size();
        averageSalaryByDay/=numberOfEmployee;
        EmployeeOfFactory = employeeOfFactory;
    }

    private void updateOfficesOfFactory(){
        ArrayList<OfficeModel> freeOffice = new ArrayList<OfficeModel>();
        ArrayList<PersonModel> employeeWithoutOffice = new ArrayList<PersonModel>();

        //Check the different postions of the offices
        for (OfficeModel office: Offices) {
            if(!office.isOccupiedLeft())
                freeOffice.add(office);
            else
            if(!office.isOccupiedRight())
                freeOffice.add(office);
        }
        //Check the employee don't have assigned office
        for (PersonModel employee : EmployeeOfFactory) {
            if(employee.getAssignedOffice().x == -1)
                employeeWithoutOffice.add(employee);
        }

        int indexOfOffice = 0;
        int sizeOfOffice = freeOffice.size();
        for (PersonModel e : employeeWithoutOffice) {
            if (!freeOffice.isEmpty() && indexOfOffice < sizeOfOffice) {
                OfficeModel office = freeOffice.get(indexOfOffice);
                if (!office.isOccupiedLeft()) {
                    e.setNewPosition(office.getPositionLeft());
                    e.setAssignedOffice(office.getPositionLeft());
                    office.setOccupiedLeft(true);
                    continue;
                }
                if (!office.isOccupiedRight()) {
                    e.setNewPosition(office.getPositionRight());
                    e.setAssignedOffice(office.getPositionRight());
                    office.setOccupiedRight(true);
                    indexOfOffice++;
                    continue;
                }
            } else {
                e.setNewPosition(new Position(HideRoom.getPositionOfEntity().x + HideRoom.getWidth(), HideRoom.getPositionOfEntity().y + HideRoom.getHeight() / 2));
                e.setAssignedOffice(new Position(HideRoom.getPositionOfEntity().x + HideRoom.getWidth(), HideRoom.getPositionOfEntity().y + HideRoom.getHeight() / 2));
            }
        }
    }

    // Mumuse
    public void addNewEmployeePosition(String employeeName, String employeeJob, Double employeeSalary) {	  	
	  	PersonModel newEmployeeItem = new PersonModel(employeeName,employeeJob, employeeSalary, new GraphicalEntity(new Position(HardCodedParameters.EmployeeStartX,HardCodedParameters.FactoryStartY+(HardCodedParameters.FactoryHeight/2)),50,50),"file:Ressource/images/test2.png",4,3,32,32,8); 
	  	
	  	int i = 0;
        int numberIterate = 1;
        int numberOfPlace = HardCodedParameters.numberOfficeInFactory * 2;
        EmployeeOfFactory.add(newEmployeeItem);
        updateOfficesOfFactory();
//        	if(numberIterate <= numberOfPlace){
//                if (numberIterate % 2 == 1) {
//                	if(e.getNewPosition().x == Offices.get(i).getPositionOfEntity().x + Offices.get(i).getWidth() / 3 && Offices.get(i).getPositionOfEntity().y == e.getNewPosition().y){
//                        numberIterate++;
//                        continue;
//                    }
//                    e.setNewPosition(new Position(Offices.get(i).getPositionOfEntity().x + Offices.get(i).getWidth() / 3, Offices.get(i).getPositionOfEntity().y));
//
//                } else {
//                    if(e.getNewPosition().x == Offices.get(i).getPositionOfEntity().x + Offices.get(i).getWidth() - Offices.get(i).getWidth() / 16 && Offices.get(i).getPositionOfEntity().y == e.getNewPosition().y){
//                        numberIterate++;
//                        i++;
//                        continue;
//                    }
//                	e.setNewPosition(new Position(Offices.get(i).getPositionOfEntity().x + Offices.get(i).getWidth() - Offices.get(i).getWidth() / 16, Offices.get(i).getPositionOfEntity().y));
//                    i++;
//                }
//                numberIterate++;
//            }else{
//            	e.setNewPosition(new Position(HideRoom.getPositionOfEntity().x+ HideRoom.getWidth(),HideRoom.getPositionOfEntity().y+HideRoom.getHeight()/2));
//            }
//        }
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
    public void generateCSVFile (String filename) {
//    	String csvFile = "Ressource/files/res "+dateFormat.format(date)+".csv";
        CSVReader.generateCsvFile(filename, this.EmployeeOfFactory);

    }

    public GraphicalEntity getHideRoom() {
        return HideRoom;
    }

    public ArrayList<OfficeModel> getOffices() {
        return Offices;
    }

    public double getAverageSalaryByDay() {
        return averageSalaryByDay;
    }

    public int getNumberOfEmployee() {
        return numberOfEmployee;
    }

    public void setOffices(ArrayList<OfficeModel> offices) {
        Offices = offices;
    }
}
