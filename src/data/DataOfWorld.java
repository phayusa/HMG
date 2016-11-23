/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: data/Data.java 2015-03-11 buixuan.
 * ******************************************************/
package data;

import java.util.ArrayList;

import Model.FactoryModel;
import Model.PersonModel;
import javafx.scene.chart.PieChart;
import specifications.Service.DataService;
import tools.GraphicalEntity;
import tools.HardCodedParameters;
import tools.Position;

public class DataOfWorld implements DataService{
    //  private Sound.SOUND sound
    private String name;
    private FactoryModel userFactory;
    private int ProgressionOfWork,NumberOfDaysForProject,CurrentDay;
    private int MaxProgressionByDay;
    private PieChart estimateChart, simulateChart;
    private ArrayList<String> CurrentLog;
    private ArrayList<String> TotalLog;
    private double EmployeeStartY;


	private double budget;

    public DataOfWorld(){}

    @Override
    public void init(){
        userFactory = new FactoryModel(
        		new GraphicalEntity(
        				new Position(HardCodedParameters.FactoryStartX,HardCodedParameters.FactoryStartY), 
        				HardCodedParameters.FactoryWidth,
        				HardCodedParameters.FactoryHeight,
        				HardCodedParameters.urlBackground
        				)
        		);
        CurrentDay = 1;
        CurrentLog = new ArrayList<String>(HardCodedParameters.maxLines);
        TotalLog = new ArrayList<String>();
        TotalLog.add("Jour1 :");
        ProgressionOfWork = 0;
        EmployeeStartY = HardCodedParameters.FactoryStartY+HardCodedParameters.FactoryHeight/3;
        setNumberOfDaysForProject(HardCodedParameters.workDayInMonth);
    }


    @Override
    public double getBudget() {
        return budget;
    }

    @Override
    public void setBudget(double budget) {
        this.budget = budget;
    }

    @Override
    public FactoryModel getUserFactory() {
        return userFactory;
    }

    @Override
    public void setUserFactory(FactoryModel userFactory) {
        this.userFactory = userFactory;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
    
    public void loadCSVFile () {
    
    }

    @Override
    public float getProgressOfWorkInFloat() {
        return ((float) ProgressionOfWork)/100;
    }

    @Override
    public int getProgressOfWork() {
        return ProgressionOfWork;
    }


    @Override
    public void setProgressionOfWork(int progressionOfWork) {
        ProgressionOfWork = progressionOfWork;
    }

    @Override
	public void generateCsvFile() {
		// TODO Auto-generated method stub
		
	}

	@Override
    public int getNumberOfDaysForProject() {
        return NumberOfDaysForProject;
    }

    @Override
    public void setNumberOfDaysForProject(int numberOfDaysForProject) {
        NumberOfDaysForProject = numberOfDaysForProject;
        MaxProgressionByDay = ((int) ((1.0 / ((double) numberOfDaysForProject)) * 100));
    }

    @Override
    public int getCurrentDay() {
        return CurrentDay;
    }

    @Override
    public void setCurrentDay(int currentDay) {
        CurrentDay = currentDay;
    }

    @Override
    public int getMaxProgressionByDay() {
        return MaxProgressionByDay;
    }

    @Override
    public void setEmployeeOfFactory(ArrayList<PersonModel> employeeOfFactory) {
        userFactory.setEmployeeOfFactory(employeeOfFactory);
    }
    
    public PieChart getEstimateChart() {
		return estimateChart;
	}

	public void setEstimateChart(PieChart estimateChart) {
		this.estimateChart = estimateChart;
	}
	
	public PieChart getSimulateChart() {
		return simulateChart;
	}

	public void setSimulateChart(PieChart simulateChart) {
		this.simulateChart = simulateChart;
	}

    @Override
    public ArrayList<String> getCurrentLog() {
        return CurrentLog;
    }

    @Override
    public ArrayList<String> getTotalLog() {
        return TotalLog;
    }

    @Override
    public void addLineCurrentLog(String line) {
        if(CurrentLog.size() >= HardCodedParameters.maxLines){
            CurrentLog.remove(0);
        }
        CurrentLog.add(line);
    }

    @Override
    public void addLineTotalLog(String line) {
        TotalLog.add(line);
    }

    @Override
    public String getLogsInString() {
        return String.join("\n", CurrentLog);
    }

    @Override
    public double getEmployeeStartY() {
        return EmployeeStartY;
    }
}
