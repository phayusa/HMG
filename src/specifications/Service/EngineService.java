/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: specifications/EngineService.java 2015-03-11 buixuan.
 * ******************************************************/
package specifications.Service;

import Model.PersonModel;
import tools.User_Entry;

import java.util.ArrayList;

public interface EngineService{
  public void init();
  public void start();
  public void stop();
  public void onPause();
  public void resetPosition();
  public void allLeave();
  public void ClearEmployeeOfNotInAction(ArrayList<PersonModel> test);
  public void RemoveFirst();
}
