package tools;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by Miche on 10/11/2016.
 */
public class GraphicalEntity {
    protected Position positionOfEntity;
    protected double width,height;
    // the image will be be load only once
    protected ImageView imageOfEntity;

    public  GraphicalEntity(){}

    public GraphicalEntity(Position startPosition,double width,double height){
        positionOfEntity = startPosition;
        this.width = width;
        this.height = height;
    }

    public GraphicalEntity(Position startPosition,double width,double height,String pathOfImage){
        positionOfEntity = startPosition;
        this.width = width;
        this.height = height;
        imageOfEntity = new ImageView(new Image(pathOfImage));
    }

    //Copy constructor
    public GraphicalEntity(GraphicalEntity toCopyEntity){
        positionOfEntity = toCopyEntity.positionOfEntity;
        width = toCopyEntity.width;
        height = toCopyEntity.height;
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
        this.positionOfEntity = positionOfEntity;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
