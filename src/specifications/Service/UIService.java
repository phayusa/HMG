/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: specifications/DataService.java 2015-03-11 buixuan.
 * ******************************************************/
package specifications.Service;

import javafx.stage.Stage;

public interface UIService{
  public void init();
  public void addLineLog(String line);
  public void clearLog();
  public void exportLog(String path);
  public void addEmployeeDialog();
  public void dialogEndProject();
  public void dialogEndDay();
  public String getResult();
  public void setResult(String result);
  public void dialogClearExport();
  public void exportCharts();
  public boolean getStartPanel(Stage stage);
}
