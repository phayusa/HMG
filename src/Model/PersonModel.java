package Model;

import javafx.scene.control.Label;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;
import tools.AnimationSprite;
import tools.GraphicalEntity;



/**
 * Created by Miche on 10/11/2016.
 */
public class PersonModel extends AnimationSprite{
    private double salary,salaryByDay,experience,ID;
    private String name,job;
    private javafx.scene.control.Label labelOfEmployee;

	public PersonModel(String name,String job,double salary,GraphicalEntity graphicSettings,String pathOfImage,int Xsprite, int Ysprite,double sizeSpriteX,double sizeSpriteY,long frameBySecond){
		super(graphicSettings,pathOfImage,Xsprite,Ysprite,sizeSpriteX,sizeSpriteY,frameBySecond);
		this.name = name;
		this.job = job;
		this.salary = salary;
		salaryByDay = salary/20;
		labelOfEmployee = new Label(name);
		labelOfEmployee.setTextFill(Color.BLACK);
	}
    
	public PersonModel(String name,String job,double salary){
		this.name = name;
		this.job = job;
		this.salary = salary;
		salaryByDay = salary/20;
	}


	public Label getLabelOfEmployee(double widthScreen,double heightScreen) {
		labelOfEmployee.setTranslateX(ratioX * widthScreen);
		labelOfEmployee.setTranslateY(ratioY * heightScreen);
		return labelOfEmployee;
	}

	public void setLabelOfEmployee(Label labelOfEmployee) {
		this.labelOfEmployee = labelOfEmployee;
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
