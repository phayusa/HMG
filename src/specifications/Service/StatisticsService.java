package specifications.Service;

import java.util.HashMap;

public interface StatisticsService {
	public void init();
	public void stop();
	public void start();
	public HashMap<String, Double> getSalaryByJob();
	public Double getTotalSalary();
}
