/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: specifications/WriteService.java 2015-03-11 buixuan.
 * ******************************************************/
package specifications.Service;

import java.util.ArrayList;

import Model.FactoryModel;
import Model.PersonModel;
import javafx.scene.chart.PieChart;
import tools.Sound;

public interface WriteService {
    public void setBudget(double budget);
    public void setUserFactory(FactoryModel userFactory);
    public void setCurrentDay(int currentDay);
    public void setEmployeeOfFactory(ArrayList<PersonModel> employeeOfFactory);
    public void setProgressionOfWork(int progressionOfWork);
    public void setName(String name);
    public void loadCSVFile(String csvPath);
    public void generateCsvFile();
    public void setEstimateChart(PieChart estimateChart);
    public void setSimulateChart(PieChart simulateChart);
    public void addLineCurrentLog(String line);
    public void addLineTotalLog(String line);
    public void setNumberOfDaysForProject(int numberOfDaysForProject);
    public void setSound(Sound.SOUND sound);

}
