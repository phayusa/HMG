package tools;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Miche on 10/11/2016.
 */
public class AnimationSprite extends GraphicalEntity {
    private int count;
//    int columns;
//    int offsetX;
//    int offsetY;
    private double Speed;
    private Timer timeToUpdateFrame;
    private int indexOfSprite;
    private ArrayList<ImageView> Sprites;

    public AnimationSprite(GraphicalEntity initGraphicalEntity,String prefix,String suffix,double speed,int startcount ,int countSprites){
        super(initGraphicalEntity);
        this.Speed = speed;
        this.count = countSprites;
        this.Sprites = new ArrayList<ImageView>();
        if(suffix != ""){
            for(int i=startcount;i<=countSprites;i++){
                ImageView test = new ImageView(new Image(prefix+i+suffix
                ));
                Sprites.add(test);
            }
        }
        long timeToUpdate = 1000/startcount;
        System.out.println(timeToUpdate);
        indexOfSprite = 0;
        timeToUpdateFrame = new Timer();
        timeToUpdateFrame.schedule(new TimerTask() {
            @Override
            public void run() {
                indexOfSprite++;
                if(indexOfSprite == count){
                    indexOfSprite = 0;
                }
            }
        },0,timeToUpdate);
    }

    public AnimationSprite(GraphicalEntity initGraphicalEntity,String prefix,String suffix,double speed,int startcount ,int countSprites,long frameBySecond){
        super(initGraphicalEntity);
        this.Speed = speed;
        this.count = countSprites;
        this.Sprites = new ArrayList<ImageView>();
        if(suffix != ""){
            for(int i=startcount;i<=countSprites;i++){
                ImageView test = new ImageView(new Image(prefix+i+suffix
                ));
                Sprites.add(test);
            }
        }
        long numberFrame = 1000/frameBySecond;
        indexOfSprite = 0;
        timeToUpdateFrame = new Timer();
        timeToUpdateFrame.schedule(new TimerTask() {
            @Override
            public void run() {
                indexOfSprite++;
                if(indexOfSprite == count){
                    indexOfSprite = 0;
                }
            }
        },0,numberFrame);
    }

    public ImageView getCurrentSprite(){
        System.out.println(indexOfSprite);
        return Sprites.get(indexOfSprite);
    }

    public double getSpeed() {
        return Speed;
    }


}
