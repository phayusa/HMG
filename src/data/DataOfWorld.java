/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: data/Data.java 2015-03-11 buixuan.
 * ******************************************************/
package data;

import java.util.ArrayList;

import Model.FactoryModel;
import specifications.Service.DataService;
import tools.AnimationSprite;
import tools.GraphicalEntity;
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
        userFactory = new FactoryModel();
//        testSprite = new AnimationSprite(new GraphicalEntity(new Position(100,100),50,50),"file:Ressource/images/wizard/walk_",".png",10,1,4,8);
//        testSprite = new AnimationSprite(new GraphicalEntity(new Position(100,100),50,50),"file:Ressource/images/test.png",4,3,24,32,10,8);
        testSprite = new AnimationSprite(new GraphicalEntity(new Position(100,100),50,50),"file:Ressource/images/test2.png",4,3,32,32,8);
        testSprite.setNbAnim(1);
        testSprite.stopAnim();
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

    public AnimationSprite getTestSprite() {
        return testSprite;
    }

    public void setTestSprite(AnimationSprite testSprite) {
        this.testSprite = testSprite;
    }

    @Override
	public void generateCsvFile() {
		// TODO Auto-generated method stub
		
	}
}
