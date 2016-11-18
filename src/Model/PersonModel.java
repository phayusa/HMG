package Model;

import tools.AnimationSprite;
import tools.GraphicalEntity;
import tools.Position;

/**
 * Created by Miche on 10/11/2016.
 */
public class PersonModel extends AnimationSprite{
    private double salary,salaryByDay,experience,ID;
    private String name,job;

	public PersonModel(String name,String job,double salary,GraphicalEntity graphicSettings,String pathOfImage,int Xsprite, int Ysprite,double sizeSpriteX,double sizeSpriteY,long frameBySecond){
		super(graphicSettings,pathOfImage,Xsprite,Ysprite,sizeSpriteX,sizeSpriteY,frameBySecond);
		this.name = name;
		this.job = job;
		this.salary = salary;
		salaryByDay = salary/20;
	}
    
	public PersonModel(String name,String job,double salary){
		this.name = name;
		this.job = job;
		this.salary = salary;
		salaryByDay = salary/20;
	}
    
	
    public double getSalary() {
		return salary;
	}

	public String getName() {
		return name;
	}

	public String getJob() {
		return job;
	}

	public double getSalaryByDay() {
		return salaryByDay;
	}

}
