package tools;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Miche on 10/11/2016.
 */
public class AnimationSprite extends GraphicalEntity {

    private Timer timeToUpdateFrame;
    private int indexOfSprite;
    private ArrayList<ImageView> Sprites;
    private int startIndex;
    private int endIndex;
    private int sizeOfLine;
    private boolean inMovement;
    private int leftAnim,rightAnim, upAnim,downAnim;
    private boolean left,right,up,down;
    private Position newPosition;
    private boolean inFactory;

    @Deprecated
    public AnimationSprite(GraphicalEntity initGraphicalEntity,String prefix,String suffix,int startcount ,int countSprites){
        super(initGraphicalEntity);
        startIndex = 0;
        endIndex = countSprites;
        this.Sprites = new ArrayList<ImageView>();
        if(suffix != ""){
            for(int i=startcount;i<=countSprites;i++){
                ImageView imageViewOfSprite = new ImageView(new Image(prefix+i+suffix
                ));
                Sprites.add(imageViewOfSprite);
            }
        }
        long timeToUpdate = 1000/startcount;
        System.out.println(timeToUpdate);
        commonInit(timeToUpdate);
    }

    @Deprecated
    public AnimationSprite(GraphicalEntity initGraphicalEntity,String prefix,String suffix,int startcount ,int countSprites,long frameBySecond){
        super(initGraphicalEntity);
        startIndex = 0;
        endIndex = countSprites;
        this.Sprites = new ArrayList<ImageView>();
        if(suffix != ""){
            for(int i=startcount;i<=countSprites;i++){
                ImageView imageViewOfSprite = new ImageView(new Image(prefix+i+suffix
                ));
                Sprites.add(imageViewOfSprite);
            }
        }
        long numberFrame = 1000/frameBySecond;
        commonInit(numberFrame);
    }
    
    public AnimationSprite(){
    	
    }

    public AnimationSprite(GraphicalEntity initGraphicalEntity,String pathOfImage,int Xsprite, int Ysprite,double sizeSpriteX,double sizeSpriteY,long frameBySecond){
        super(initGraphicalEntity);
        this.Sprites = new ArrayList<ImageView>();
        for(int y = 0;y<Xsprite;y++)
            for (int x=0;x<Ysprite;x++) {
                ImageView test = new ImageView(new Image(pathOfImage));
                test.setViewport(new Rectangle2D(x * sizeSpriteX, y * sizeSpriteY, sizeSpriteX, sizeSpriteY));
                Sprites.add(test);
            }
        sizeOfLine = Ysprite;
        long numberFrame = 1000/frameBySecond;
        commonInit(numberFrame);

    }

    private void commonInit(long numberFrame){
        inMovement = false;
        indexOfSprite = 0;
        inFactory = true;
        newPosition = positionOfEntity;
        timeToUpdateFrame = new Timer();
        timeToUpdateFrame.schedule(new TimerTask() {
            @Override
            public void run() {
                if(inMovement) {
                    indexOfSprite++;
                    if (indexOfSprite >= endIndex || indexOfSprite < startIndex) {
                        indexOfSprite = startIndex;
                    }
                }
            }
        },0,numberFrame);
    }

    public void setNbAnim(int line){
//        if(inMovement)
//            return;
        inMovement = true;
        startIndex = line * sizeOfLine;
        endIndex = startIndex + sizeOfLine;
        indexOfSprite = startIndex;
    }

    public ImageView getCurrentSprite(){
        return Sprites.get(indexOfSprite);
    }


    public void stopAnim(){
        inMovement = false;
        indexOfSprite = startIndex + sizeOfLine/2;
    }

    public void setPositionWithSpeed(double speedX, double speedY){
        setPositionOfEntity(new Position(positionOfEntity.x + speedX,positionOfEntity.y + speedY));
    }

    //If the stop case is not the middle image of spriteSheet
    public void stopAnim(int indexOfLine){
        inMovement = false;
        indexOfSprite = startIndex + indexOfLine;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public Position getNewPosition() {
        return newPosition;
    }

    public void setNewPosition(Position newPosition) {
        this.newPosition = newPosition;
    }

    public boolean isInFactory() {
        return inFactory;
    }

    public void setInFactory(boolean inFactory) {
        this.inFactory = inFactory;
    }

    public void setInMovement(boolean inMovement) {
        this.inMovement = inMovement;
    }
}
