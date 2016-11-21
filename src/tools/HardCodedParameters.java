/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: tools/HardCodedParameters.java 2015-03-11 buixuan.
 * ******************************************************/
package tools;

import java.util.HashMap;

public class HardCodedParameters {
  //---HARD-CODED-PARAMETERS---//
  public static String defaultParamFileName = "in.parameters";
  public static final int defaultWidth = 1280, defaultHeight = 720,
                          FactoryWidth = 700, FactoryHeight = 510,
                          OfficeWidth = 150,OfficeHeight = 80,numberOfficeInFactory=6,
                          EmployeeStartX = -260;
    public static final double FactoryStartX = 0, FactoryStartY = 0;
    public static final int TimeBetweenDaysInMilli = 10000;

    // ---- UI Dimension---//
    public static int maxLines = 8;

  //----Panels Dimension---//
  public static final int companySizeX = 700, companySizeY = 510,
				          companyTranslateX = -260, companyTranslateY = -80,
				          statSizeX = 476, statSizeY = 510,
				          statTranslateX = 394, statTranslateY = -80,
				          backSizeX = 476, backSizeY = 148,
				          backTranslateX = 394, backTranslateY = 253;
  
  public static final double startBudget = 10000;
  public static final int enginePaceMillis = 100,
                          spriteSlowDownRate = 7,
                          workDayInMonth = 20;
  
  public static final String urlBackground = "file:Ressource/images/floor.jpg";

}
