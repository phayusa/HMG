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
    
    public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}
}
