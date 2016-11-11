package Model;

import tools.GraphicalEntity;
import tools.Position;

import java.util.ArrayList;

/**
 * Created by Micdu95 on 11/11/2016.
 */
public class FactoryModel extends GraphicalEntity {
    private String name;
    private double budget;
    private ArrayList<PersonModel> EmployeeOfFactory;

    public FactoryModel(){
        EmployeeOfFactory = new ArrayList<PersonModel>();
        // TODO : replace by the load of csv file
        EmployeeOfFactory.add(new PersonModel("Michel","Project chief",500000,new GraphicalEntity(new Position(50,50),100,100,"file:Ressource/images/cyclope.png")));
        EmployeeOfFactory.add(new PersonModel("Geoffrey","Project chief",500000,new GraphicalEntity(new Position(150,50),100,100,"file:Ressource/images/cyclope.png")));
        EmployeeOfFactory.add(new PersonModel("Houssem","Project chief",500000,new GraphicalEntity(new Position(250,50),100,100,"file:Ressource/images/cyclope.png")));
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
}
