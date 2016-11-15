/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: data/Data.java 2015-03-11 buixuan.
 * ******************************************************/
package data;

import Model.FactoryModel;
import specifications.Service.DataService;
import tools.AnimationSprite;
import tools.GraphicalEntity;
import tools.HardCodedParameters;
import tools.Position;

public class DataOfWorld implements DataService{
    //  private Sound.SOUND sound
    private String name;
    private FactoryModel userFactory;
    private AnimationSprite testSprite;

    private double budget;

    public DataOfWorld(){}

    @Override
    public void init(){
        userFactory = new FactoryModel(new GraphicalEntity(new Position(HardCodedParameters.FactoryStartX,HardCodedParameters.FactoryStartY), HardCodedParameters.FactoryWidth,HardCodedParameters.FactoryHeight));
    }


    @Override
    public double getBudget() {
        return budget;
    }

    @Override
    public void setBudget(double budget) {
        this.budget = budget;
    }

    @Override
    public FactoryModel getUserFactory() {
        return userFactory;
    }

    @Override
    public void setUserFactory(FactoryModel userFactory) {
        this.userFactory = userFactory;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
    
    public void loadCSVFile () {
    
    }


    @Override
	public void generateCsvFile() {
		// TODO Auto-generated method stub
		
	}
}
