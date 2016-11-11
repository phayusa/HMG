package Model;

import tools.GraphicalEntity;
import tools.Position;

/**
 * Created by Miche on 10/11/2016.
 */
public class PersonModel extends GraphicalEntity{
    double salary,experience,ID;
    String name,job;

    public PersonModel(){}

    public PersonModel(String name,String job,double salary,GraphicalEntity graphicSettings){
        super(graphicSettings);
        this.name = name;
        this.job = job;
        this.salary = salary;
    }
}
