package edu.virginia.engine.display;

import edu.virginia.engine.util.GameClock;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.Timer;

/**
 * Nothing in this class (yet) because there is nothing specific to a Sprite yet that a DisplayObject
 * doesn't already do. Leaving it here for convenience later. you will see!
 * */
public class AnimatedSprite extends Sprite {

    private List<BufferedImage> frames = new ArrayList<BufferedImage>();
    private int currentFrame=0;
    private int startIndex;
    private int endIndex;
    private boolean isPlaying = true;
    private double speed;
    private String animationName;
    private HashMap<String, int[]> animations = new HashMap<String, int[]>();
    /* Timer that this game runs on */
    private GameClock gameTimer;
    int time;

	public AnimatedSprite(String id) {
		super(id);

	}

//	public AnimatedSprite(String id, List<BufferedImage> frames) {
//		super(id);
//		this.frames=frames;
//	}

	public AnimatedSprite(String id, List<String> imageFileName) {
        super(id);
        for(String file:imageFileName) {
            BufferedImage image = this.readImage(file);
            if (image!= null) {
                frames.add(image);
                this.setWidth(image.getWidth());
                this.setHeight(image.getHeight());
            }

        }
        if (gameTimer == null) {
            gameTimer = new GameClock();
        }
    }



	@Override
	public void update(ArrayList<String> pressedKeys) {
		super.update(pressedKeys);
		time = (int)gameTimer.getElapsedTime();
	}

	@Override
	public void draw(Graphics g) {
	    //this is whre you control the animation speed
	    if (isPlaying && time%8==0) {
	        if (currentFrame ==0) {
	            currentFrame++;
	        } else {
	            currentFrame=0;
	        }
	        super.setImage(frames.get(currentFrame));
	    }
	    super.draw(g);
	}

	public void animate(String name) {
	    if(animations.containsKey(name)) {
	        this.animationName=name;
	        startIndex = animations.get(name)[0];
	        endIndex = animations.get(name)[1];
	    }
	}


    public double getSpeed()
    {
        return speed;
    }

    public void setSpeed(double speed)
    {
        this.speed = speed;
    }

    public int getEndIndex()
    {
        return endIndex;
    }

    public void setEndIndex(int endIndex)
    {
        this.endIndex = endIndex;
    }

    public int getStartIndex()
    {
        return startIndex;
    }

    public void setStartIndex(int startIndex)
    {
        this.startIndex = startIndex;
    }

    public int getCurrentFrame()
    {
        return currentFrame;
    }

    public void setCurrentFrame(int currentFrame)
    {
        this.currentFrame = currentFrame;
    }

    public boolean isPlaying()
    {
        return isPlaying;
    }

    public void setPlaying(boolean isPlaying)
    {
        this.isPlaying = isPlaying;
    }

    public HashMap<String, int[]> getAnimations()
    {
        return animations;
    }

    public void setAnimations(HashMap<String, int[]> animations)
    {
        this.animations = animations;
    }



}