package engine;

import java.util.HashMap;
import java.util.Timer;

import Model.FactoryModel;
import Model.PersonModel;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import specifications.Require.RequireDataService;
import specifications.Service.DataService;
import specifications.Service.StatisticsService;

public class StatisticsController implements StatisticsService, RequireDataService {

	private DataService dataOfWorld;
	private FactoryModel factory;
	private HashMap<String, Double> salaryByJob;
	private Timer engineClock;
	private Double percentSalary, totalPercentSalary, totalSalary;
	public StatisticsController(){
		
	}
	
	@Override
	public void init() {
	    engineClock = new Timer();
	    salaryByJob = new HashMap<>();
	    factory = dataOfWorld.getUserFactory();
	}

	@Override
	public void stop() {
		engineClock.cancel();
	    System.exit(0);
	}

	@Override
	public void start() {
		
	}
	
	public HashMap<String, Double> getSalaryByJob() {
		totalPercentSalary = 0.0;
		percentSalary = 0.0;
		salaryByJob = new HashMap<>();
		if (factory.getBudget() != 0) {
			for (PersonModel employee : factory.getEmployeeOfFactory()) {
				  if (salaryByJob.get(employee.getJob()) != null && salaryByJob.containsKey(employee.getJob())) {
					  Double totalSalary = salaryByJob.get(employee.getJob()) + employee.getSalary();
					  percentSalary = 100 * totalSalary / factory.getBudget();
					  totalPercentSalary += percentSalary;
					  salaryByJob.put(employee.getJob(), percentSalary);
				  } else {
					  percentSalary = 100 * employee.getSalary() / factory.getBudget();
					  totalPercentSalary += percentSalary;
					  salaryByJob.put(employee.getJob(), percentSalary);
				  } 
			  }
			  if (totalPercentSalary < 100) {
			    	salaryByJob.put("Restant", 100 - (Double) totalPercentSalary);
			  }
			  else {
				  salaryByJob.remove("Restant");
			  }
	
		}
		 return salaryByJob;
	}
	
	public Double getTotalSalary(){
		totalSalary = 0.0;
		for (PersonModel employee : factory.getEmployeeOfFactory()) {
			if (salaryByJob.get(employee.getJob()) != null && salaryByJob.containsKey(employee.getJob())) {
				totalSalary += employee.getSalary();
			} 
		}
		return totalSalary;
	}
	
	//TODO create function when employee leave company

	@Override
	public void bindDataService(DataService service) {
		dataOfWorld=service;		
	}

}
