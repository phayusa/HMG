package specifications.Service;

import java.util.HashMap;

import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

public interface StatisticsService {
	public void init();
	public void stop();
	public void start();
	public HashMap<String, Double> getSalaryByJob();
	public void generateEstimateChart();
	public void generateSimulateChart();
	public Double getTotalSalary();
	public void resetStat();
}
