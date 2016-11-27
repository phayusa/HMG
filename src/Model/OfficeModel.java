package Model;

import tools.GraphicalEntity;
import tools.Position;

/**
 * Created by sokomo on 22/11/16.
 */
public class OfficeModel extends GraphicalEntity {
    private boolean occupiedLeft;
    private Position positionLeft;
    private boolean occupiedRight;
    private Position positionRight;

    public OfficeModel(Position startPosition, double width, double height, String pathOfImage) {
        super(startPosition, width, height, pathOfImage);
        commonInit();
    }


    public OfficeModel(Position startPosition, double width, double height) {
        super(startPosition, width, height);
        commonInit();
    }

    public OfficeModel(GraphicalEntity toCopyEntity) {
        super(toCopyEntity);
        commonInit();
    }

    private void commonInit(){
        occupiedLeft = false;
        occupiedRight = false;
        positionLeft = new Position(positionOfEntity.x + width / 3,positionOfEntity.y);
        positionRight = new Position(positionOfEntity.x + width - width/ 16,positionOfEntity.y);
    }

    public boolean isOccupiedLeft() {
        return occupiedLeft;
    }

    public void setOccupiedLeft(boolean occupiedLeft) {
        this.occupiedLeft = occupiedLeft;
    }

    public Position getPositionLeft() {
        return positionLeft;
    }

    public boolean isOccupiedRight() {
        return occupiedRight;
    }

    public void setOccupiedRight(boolean occupiedRight) {
        this.occupiedRight = occupiedRight;
    }

    public Position getPositionRight() {
        return positionRight;
    }

}
