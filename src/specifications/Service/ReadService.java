/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: specifications/ReadService.java 2015-03-11 buixuan.
 * ******************************************************/
package specifications.Service;

import Model.FactoryModel;
import tools.Position;
import tools.Sound;

import java.util.ArrayList;

public interface ReadService {
 // public Sound.SOUND getSoundEffect();
     public double getBudget();
    public FactoryModel getUserFactory();
    public String getName();
}
