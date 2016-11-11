package tools;

import javafx.geometry.Pos;

/**
 * Created by Miche on 10/11/2016.
 */
public class AnimationSprite extends GraphicalEntity {
    int count;
    int columns;
    int offsetX;
    int offsetY;
    int SpeedOnX;
    int SpeedOnY;

    public AnimationSprite(Position startPosition,int width,int height,String path){
        super(startPosition,width,height,path);
    }


}
