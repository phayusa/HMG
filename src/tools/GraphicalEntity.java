package tools;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by Miche on 10/11/2016.
 */
public class GraphicalEntity {
    protected Position positionOfEntity;
    protected int width,height;
    protected  double ratioWidth,ratioHeight,ratioX,ratioY;
    // the image will be be load only once
    protected ImageView imageOfEntity;

    public  GraphicalEntity(){}

    public GraphicalEntity(Position startPosition,int width,int height){
//        positionOfEntity = startPosition;
        setPositionOfEntity(startPosition);
        setWidth(width);
        setHeight(height);
    }

    public GraphicalEntity(Position startPosition,int width,int height,String pathOfImage){
        setPositionOfEntity(startPosition);
        setWidth(width);
        setHeight(height);
        imageOfEntity = new ImageView(new Image(pathOfImage));
    }

    //Copy constructor
    public GraphicalEntity(GraphicalEntity toCopyEntity){
        positionOfEntity = toCopyEntity.positionOfEntity;
        width = toCopyEntity.width;
        height = toCopyEntity.height;
        ratioX = toCopyEntity.ratioX;
        ratioY = toCopyEntity.ratioY;
        ratioWidth = toCopyEntity.ratioWidth;
        ratioHeight = toCopyEntity.ratioHeight;
        imageOfEntity = toCopyEntity.imageOfEntity;
    }

    public Object clone() {
        Object o = null;
        try {
            // On récupère l'instance à renvoyer par l'appel de la
            // méthode super.clone()
            o = super.clone();
        } catch(CloneNotSupportedException cnse) {
            // Ne devrait jamais arriver car nous implémentons
            // l'interface Cloneable
            cnse.printStackTrace(System.err);
        }
        // on renvoie le clone
        return o;
    }

    public ImageView getImageOfEntity(){
        return imageOfEntity;
    }

    public Position getPositionOfEntity() {
        return positionOfEntity;
    }

    public void setPositionOfEntity(Position positionOfEntity) {
        ratioX = positionOfEntity.x/HardCodedParameters.defaultWidth;
        ratioY = positionOfEntity.y/HardCodedParameters.defaultHeight;
        this.positionOfEntity = positionOfEntity;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        ratioWidth = ((double) width)/HardCodedParameters.defaultWidth;
        this.width = width;
    }

    public int getHeight() {
        ratioHeight = ((double) height)/HardCodedParameters.defaultHeight;
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getRatioWidth() {
        return ratioWidth;
    }


    public double getRatioHeight() {
        return ratioHeight;
    }

    public double getRatioX() {
        return ratioX;
    }

    public double getRatioY() {
        return ratioY;
    }
}
