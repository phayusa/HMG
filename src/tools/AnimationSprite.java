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

    private double Speed;
    private Timer timeToUpdateFrame;
    private int indexOfSprite;
    private ArrayList<ImageView> Sprites;
    private int startIndex;
    private int endIndex;
    private int sizeOfLine;
    private boolean inMovement;

    @Deprecated
    public AnimationSprite(GraphicalEntity initGraphicalEntity,String prefix,String suffix,double speed,int startcount ,int countSprites){
        super(initGraphicalEntity);
        this.Speed = speed;
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
    public AnimationSprite(GraphicalEntity initGraphicalEntity,String prefix,String suffix,double speed,int startcount ,int countSprites,long frameBySecond){
        super(initGraphicalEntity);
        this.Speed = speed;
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

    public AnimationSprite(GraphicalEntity initGraphicalEntity,String pathOfImage,int Xsprite, int Ysprite,double sizeSpriteX,double sizeSpriteY,double speed,long frameBySecond){
        super(initGraphicalEntity);
        this.Speed = speed;
        this.Sprites = new ArrayList<ImageView>();
        for(int y = 0;y<Xsprite;y++)
            for (int x=0;x<Ysprite;x++) {
                System.out.println(x + " "+y);
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
        if(inMovement)
            return;
        inMovement = true;
        startIndex = line * sizeOfLine;
        endIndex = startIndex + sizeOfLine;
    }

    public ImageView getCurrentSprite(){
        return Sprites.get(indexOfSprite);
    }

    public double getSpeed() {
        return Speed;
    }

    public void stopAnim(){
        inMovement = false;
        indexOfSprite = startIndex + sizeOfLine/2;
    }

    //If the stop case is not the middle image of spriteSheet
    public void stopAnim(int indexOfLine){
        inMovement = false;
        indexOfSprite = startIndex + indexOfLine;
    }


}
