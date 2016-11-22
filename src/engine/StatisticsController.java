package engine;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;

import Model.FactoryModel;
import Model.PersonModel;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import specifications.Require.RequireDataService;
import specifications.Service.DataService;
import specifications.Service.StatisticsService;
import tools.HardCodedParameters;

public class StatisticsController implements StatisticsService, RequireDataService {

	private DataService dataOfWorld;
	private FactoryModel factory;
	private HashMap<String, Double> salaryByDay, salaryByJob, initDataEmployees;
	private Double percentSalary, totalPercentSalary, totalSalary,
				   salaryOfEmployee;
	ObservableList<PieChart.Data> pieChartDataStatic, pieChartDataSimulate;
	
	public StatisticsController(){
		
	}
	
	@Override
	public void init() {
		salaryByDay = new HashMap<>();
		salaryByJob = new HashMap<>();
	    factory = dataOfWorld.getUserFactory();
		initDataEmployees = getSalaryByJob();
		generateEstimateChart();
		generateSimulateChart();
		totalPercentSalary = 0.0;
		percentSalary = 0.0;

	}

	@Override
	public void stop() {
	    System.exit(0);
	}

	public void resetStat() {
		salaryByDay = new HashMap<>();
		factory = dataOfWorld.getUserFactory();
		initDataEmployees = getSalaryByJob();
		generateEstimateChart();
		generateSimulateChart();
		totalPercentSalary = 0.0;
		percentSalary = 0.0;
	}
	
	@Override
	public void start() {
		
	}
	
	//Data for estimate chart
	public HashMap<String, Double> getSalaryByJob() {
	
		//20 = 1 Month Working day
		for (int i = 0 ; i<HardCodedParameters.workDayInMonth; i++) {
			totalPercentSalary = 0.0;
			percentSalary = 0.0;
			totalSalary = 0.0;
			if (factory.getBudget() != 0) {
				for (PersonModel employee : factory.getEmployeeOfFactory()) {
				  if (salaryByJob.get(employee.getJob()) != null && salaryByJob.containsKey(employee.getJob())) {
					  totalSalary = (salaryByJob.get(employee.getJob()) * 100) + (employee.getSalaryByDay());
					  percentSalary = (100 * totalSalary) / factory.getBudget();
					  salaryByJob.put(employee.getJob(), percentSalary);
				  } else {
					  percentSalary = (100 * (employee.getSalaryByDay())) / factory.getBudget();
					  salaryByJob.put(employee.getJob(), percentSalary);
				  } 
					
				}
			   
			  if (getTotalPercentEmployee(salaryByJob) < 100) {
				  salaryByJob.put("Restant", 100 - getTotalPercentEmployee(salaryByJob));
			  }
			  else {
				  salaryByJob.remove("Restant");
			  }
			}
		}
		return salaryByJob;
	}
	
	//Data for simulate chart
	public HashMap<String, Double> getBudgetDay(int nbDay) {
		salaryOfEmployee = 0.0;
		totalSalary = 0.0;
		totalPercentSalary = 0.0;
		if (factory.getBudget() != 0) {
			if (nbDay <= 1) {
				salaryByDay.put("Restant", (double) 100);
			}
			else {
				for (PersonModel employee : factory.getEmployeeOfFactory()) {
				  if (salaryByDay.get(employee.getJob()) != null && salaryByDay.containsKey(employee.getJob())) {
					  salaryOfEmployee = employee.getSalaryByDay();
					  totalSalary = (salaryByDay.get(employee.getJob()) * 100) + salaryOfEmployee;
					  percentSalary = (100 * totalSalary) / factory.getBudget();
					  salaryByDay.put(employee.getJob(), percentSalary);
				  } else {
					  salaryOfEmployee = employee.getSalaryByDay();
					  percentSalary = 100 * salaryOfEmployee / factory.getBudget();
					  salaryByDay.put(employee.getJob(), percentSalary);
				  } 
				  
			  }
		    
			if (totalPercentSalary < 100) {
				salaryByDay.put("Restant", 100 - getTotalPercentEmployee(salaryByDay));
			  }
			  else {
				  salaryByDay.remove("Restant");
			  }
			}
		}
		return salaryByDay;
	}
	
	public void generateEstimateChart() {
	    if (!initDataEmployees.isEmpty()) {
    	  pieChartDataStatic = initDataEmployees.entrySet().stream()
  			    .map(entry -> new PieChart.Data(entry.getKey(), entry.getValue()))
  			    .collect(Collectors.toCollection(() -> FXCollections.observableArrayList()));

    	  pieChartDataStatic.forEach(dataChart ->
  	        dataChart.nameProperty().bind(
  	                Bindings.concat(
  	                		dataChart.getName(), " ",
  	                		//IF
  	                		dataChart.pieValueProperty().intValue() > 1?
  	                		//THEN
  	                		dataChart.pieValueProperty().intValue() + "%":
  	                		//ELSE
  	                		"< 1%"
  			                )
	  			        )
	  			);
		    }
	    dataOfWorld.setEstimateChart(new PieChart(pieChartDataStatic));		
	}
	
	public void generateSimulateChart() {
	   
	  pieChartDataSimulate = getBudgetDay(dataOfWorld.getCurrentDay()).entrySet().stream()
		    .map(entry -> new PieChart.Data(entry.getKey(), entry.getValue()))
		    .collect(Collectors.toCollection(() -> FXCollections.observableArrayList()));

	  pieChartDataSimulate.forEach(dataChart ->
        dataChart.nameProperty().bind(
                Bindings.concat(
                		dataChart.getName(), " ",
                		//IF
                		dataChart.pieValueProperty().intValue() > 1?
                		//THEN
                		dataChart.pieValueProperty().intValue() + "%":
                		//ELSE
                		"< 1%"
		                )
  			        )
  			);
    
	   dataOfWorld.setSimulateChart(new PieChart(pieChartDataSimulate));		
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
	
	public Double getTotalPercentEmployee(HashMap allSalary) {
		totalPercentSalary = 0.0;
		Iterator it = allSalary.entrySet().iterator();
		   while (it.hasNext()) {
	         Map.Entry pair = (Map.Entry)it.next();
	          if (pair.getKey() != "Restant") {
	        	totalPercentSalary += (double) pair.getValue();
		      }
		   }
	    return totalPercentSalary;	
	}

	@Override
	public void bindDataService(DataService service) {
		dataOfWorld=service;		
	}

}
