package engine;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;

import Model.FactoryModel;
import Model.PersonModel;
import specifications.Require.RequireDataService;
import specifications.Service.DataService;
import specifications.Service.StatisticsService;

public class StatisticsController implements StatisticsService, RequireDataService {

	private DataService dataOfWorld;
	private FactoryModel factory;
	private HashMap<String, Double> salaryByJob;
	private Timer engineClock;
	private Double percentSalary, totalPercentSalary;
	//private ArrayList<String, Double> SalaryByJob = new ArrayList<>();
	public StatisticsController(){
		
	}
	
	@Override
	public void init() {
	    engineClock = new Timer();
	    salaryByJob = new HashMap<>();
	    factory = dataOfWorld.getUserFactory();
  	  	factory.setBudget(10000.0);
  	  	
	}

	@Override
	public void stop() {
		engineClock.cancel();
	    System.exit(0);
	}

	@Override
	public void start() {
		//getSalaryByJob();
	}
	
	public HashMap<String, Double> getSalaryByJob() {
		totalPercentSalary = 0.0;
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
		    	salaryByJob.put("Rest", (Double) totalPercentSalary-100);
		    }
		  return salaryByJob;
	}
	
	//TODO create function when employee leave company

	@Override
	public void bindDataService(DataService service) {
		dataOfWorld=service;		
	}

}
