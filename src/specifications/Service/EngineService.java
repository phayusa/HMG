/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: specifications/EngineService.java 2015-03-11 buixuan.
 * ******************************************************/
package specifications.Service;

import tools.User_Entry;

public interface EngineService{
  public void init();
  public void start();
  public void stop();
  public void setHeroesCommand(User_Entry.COMMAND c);
  public void releaseHeroesCommand(User_Entry.COMMAND c);
  public void modifyTestNbAnim();
  public void stopAnim();
}
