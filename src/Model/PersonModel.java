package Model;

import tools.AnimationSprite;
import tools.GraphicalEntity;
import tools.HardCodedParameters;
import tools.Position;

/**
 * Created by Miche on 10/11/2016.
 */
public class PersonModel extends AnimationSprite{
    private double salary,salaryByDay,experience,ID;
    private String name,job;
	private boolean inFactory;
	private Position assignedOffice;

	public PersonModel(String name,String job,double salary,GraphicalEntity graphicSettings,String pathOfImage,int Xsprite, int Ysprite,double sizeSpriteX,double sizeSpriteY,long frameBySecond){
		super(graphicSettings,pathOfImage,Xsprite,Ysprite,sizeSpriteX,sizeSpriteY,frameBySecond);
		this.name = name;
		this.job = job;
		this.salary = salary;
		inFactory = true;
		assignedOffice = new Position(-1,-1);
		salaryByDay = salary/20;
	}
    
	public PersonModel(String name,String job,double salary){
		this.name = name;
		this.job = job;
		this.salary = salary;
		inFactory = true;
		assignedOffice = new Position(-1,-1);
		salaryByDay = salary/20;
	}


	@Override
	public void setNewPosition(Position newPosition) {
		super.setNewPosition(newPosition);
		int halfFactory = HardCodedParameters.FactoryHeight/3;
		if(newPosition.x == HardCodedParameters.EmployeeStartX && positionOfEntity.x != newPosition.x)
			if(newPosition.y == HardCodedParameters.FactoryStartY+halfFactory && positionOfEntity.y != newPosition.y) {
				inFactory = false;
				assignedOffice = new Position(-1,-1);
				return;
			}

	}

	@Override
	public void stopAnim() {
		super.stopAnim();
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


	public boolean isInFactory() {
		return inFactory;
	}

	public void setInFactory(boolean inFactory) {
		this.inFactory = inFactory;
	}

	public Position getAssignedOffice() {
		return assignedOffice;
	}

	public void setAssignedOffice(Position assignedOffice) {
		this.assignedOffice = assignedOffice;
	}
}
