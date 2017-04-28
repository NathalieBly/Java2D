package game.main;

import edu.virginia.engine.display.AnimatedSprite;
import edu.virginia.engine.display.Brick;
import edu.virginia.engine.display.Coin;
import edu.virginia.engine.display.DisplayObject;
import edu.virginia.engine.display.Game;
import edu.virginia.engine.display.Sprite;
import edu.virginia.engine.events.*;
import edu.virginia.engine.tween.*;
import edu.virginia.engine.util.GameClock;
import edu.virginia.engine.util.Sound;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.imageio.ImageIO;



//TODO - 3/30
//1)Make button lift some gate ONLY when being pressed
//2)Allow ghosts to also throw collisions with game world
public class finalgame extends Game implements IEventListener
{

    
    AnimatedSprite Mario = new AnimatedSprite("Mario");
    Sprite saveStateMario1 = new Sprite("saveStateMario1","saved1.png");
    boolean save1 = false;
    Sprite saveStateMario2 = new Sprite("saveStateMario2","saved2.png");
    boolean save2 = false;
    int saveTracker = 1;
    // Quick fix for s key being pressed multiple times 
    int sFrames = 0;
    
    
    // See if button is being hit
    boolean buttonPressed = false;
    
    Brick brick = new Brick("Brick","Brick.png");
    Brick brick2 = new Brick("Brick","Brick.png");
    
    Brick button = new Brick("button","button.png");
    
    Brick gate = new Brick("gate","gate.png");
    
    // For showing we can "reset" our game state
    Sprite ball = new Sprite("ball","ball.png");
    
    Coin coin = new Coin("Coin","Coin.png");

    // Holds the starting positions of all our moveable sprites, so that they can be 
    // reset when we "save/reload"
    HashMap<Sprite, Point> startingPositions = new HashMap<Sprite, Point>();
    
    Tween coinTween = new Tween(coin);
    QuestManager manager = new QuestManager();
    private boolean condition=false;
    GameClock gameTimer;
    int time;
    TweenJuggler juggler = new TweenJuggler();


    public finalgame() {
        super("Prototype", 1200, 800);
        
        
        List<String> list = new ArrayList<String>();
        list.add("hero.png");
        list.add("hero.png");
        Mario = new AnimatedSprite("Mario",list);
        HashMap<String, int[]> animations = new HashMap<String, int[]>();
        int[] num = new int[2];  num[0] = 0; num[1] = 1;
        animations.put("run", num);
        Mario.setAnimations(animations);


        Sound bgm = new Sound("cooking.wav");
        bgm.loop();


        coin.setxPos(1050);
        coin.setyPos(700);


        Mario.setxPos(20);
        //Add mario's starting coords to starting coord Map
        startingPositions.put(Mario,new Point((int)Mario.getxPos(), (int)Mario.getyPos()));
        
        button.setxPos(550);
        button.setyPos(740);
   
        gate.setxPos(900);
        gate.setyPos(465);
        startingPositions.put(gate,new Point((int)gate.getxPos(), (int)gate.getyPos()));

        
        ball.setxPos(400);
        ball.setyPos(10);
        startingPositions.put(ball,new Point((int)ball.getxPos(), (int)ball.getyPos()));

        
        TweenTransitions transit = new TweenTransitions();
        Tween marioTween = new Tween(Mario, transit);

        marioTween.animate(TweenableParams.alpha, 0, 1, 1000);
        marioTween.animate(TweenableParams.yPos, 300, 670, 1000);

        juggler.add(marioTween);

        coin.addEventListener(this,  "CoinPickedUp");
        
        gate.addEventListener(this,  "collide2");

        button.addEventListener(this, "ButtonPressed");


        if (gameTimer == null) {
            gameTimer = new GameClock();
        }

    }

    /**
     * Engine will automatically call this update method once per frame and pass to us
     * the set of keys (as strings) that are currently being pressed down
     * */
    @Override
    public void update(ArrayList<String> pressedKeys){
    	
    	//Make button false if not on button
    	buttonPressed = false;
    	    	
    	// Our "save" function key
		if(pressedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_S)) && sFrames == 0){
			
			//Figure out where to save our old mario ghost
			if(save1 == false){
				saveStateMario1.setxPos(Mario.getxPos());
				saveStateMario1.setyPos(Mario.getyPos());
				save1 = true;
			}
			else if(save2 == false){
				saveStateMario2.setxPos(Mario.getxPos());
				saveStateMario2.setyPos(Mario.getyPos());
				save2 = true;
			}
			//This is for overwriting the ghosts
			else if(save1 && save2){
				if(saveTracker == 1){
					saveStateMario1.setxPos(Mario.getxPos());
					saveStateMario1.setyPos(Mario.getyPos());
					saveTracker = 2;
				}
				else if(saveTracker == 2){
					saveStateMario2.setxPos(Mario.getxPos());
					saveStateMario2.setyPos(Mario.getyPos());
					saveTracker = 1;
				}
			}
			
			//We have a hashMap of <Sprite, Starting x and y>
			Iterator entries = startingPositions.entrySet().iterator();
			while (entries.hasNext()) {
				//Grab our sprite and Point
				Entry thisEntry = (Entry) entries.next();
				Object sprite = thisEntry.getKey();
				Object pos = thisEntry.getValue();
			  
				//Set our sprite back to it's starting position
				((Sprite) sprite).setxPos(((Point) pos).getX());
				((Sprite) sprite).setyPos(((Point) pos).getY());
				
				//An if check to replay our tween if its mario
				if(((Sprite) sprite).getId().equals("Mario")){
					TweenTransitions transit = new TweenTransitions();
			        Tween marioTween = new Tween(Mario, transit);

			        marioTween.animate(TweenableParams.alpha, 0, 1, 1000);
			        marioTween.animate(TweenableParams.yPos, 300, 670, 1000);

			        juggler.add(marioTween);
				}		  
			}
			

			// A ghetto way of making sure this s key if statement is called at max every 10 frames
			sFrames = 10;
		}

        if(Mario != null && button != null) {
        	
        	// Resetting the button if it is not being stepped on
        	button.setDisplayImage("button.png");
        	button.setyPos(740);
        	
            //update y position accordingly
            Mario.setyPos(Mario.getyPos()+Mario.getV());
            
            // Show falling ball
            ball.setyPos(ball.getyPos()+ball.getV());
            ball.setV((ball.getG()+ball.getV())/1.1);
            

            

            // I do not know what setV is or what this code is doing - Nate
            //determine if on ground
            if(Mario.getyPos()>=663) {
                Mario.setOnGround(true);
            }
            //if on ground, no need to udpate v
            if(Mario.isOnGround()) {
                Mario.setV(0);
            }
            //if not, updaet v
            else {
                Mario.setV(Mario.getV()+Mario.getG());
                System.out.println("falling");
            }
            
            
            

            //boundary checking
            if(Mario.getxPos()<0) Mario.setxPos(0);
            if(Mario.getxPos()>1200) Mario.setxPos(1200);
            if(Mario.getyPos()<0) Mario.setyPos(0);
            if(Mario.getyPos()>700) Mario.setyPos(700);



            Mario.update(pressedKeys);
          
            
        //Where we are checking for intersections
            
            //Auto Hitbox for coin
            if(Mario.getHitBox().intersects(coin.getHitBox())) {
                   System.out.println("Hit!!");
                   PickedUpEvent event = new PickedUpEvent();
                   coin.dispatchEvent(event);
             }

            //Check for pressing button
            if(Mario.getHitBox().intersects(button.getHitBox())) {
                   Event event = new Event("ButtonPressed");
                   button.dispatchEvent(event);
             }
            //Adding the button pressed for saveState 1
            if(saveStateMario1.getHitBox().intersects(button.getHitBox())) {
                Event event = new Event("ButtonPressed");
                button.dispatchEvent(event);
            }
            //Adding the button pressed for saveState2
            if(saveStateMario2.getHitBox().intersects(button.getHitBox())) {
                Event event = new Event("ButtonPressed");
                button.dispatchEvent(event);
            }
			
			//Collision with gate
			if(Mario.getHitBox().intersects(gate.getHitBox())) {
				Event event = new Event("collide2");
				gate.dispatchEvent(event);
			 }

            juggler.getInstance().nextFrame();

        }
        
        if(sFrames > 0){
        	sFrames--;
        }
        
        // Show falling gate
        if(buttonPressed == false){
        	if(gate.getyPos() < 465){
            	gate.setyPos(gate.getyPos()+gate.getV());
            	gate.setV((gate.getG()+gate.getV())/1);
        	}
        }
    }

    public BufferedImage readImage(String imageName) {
		BufferedImage image = null;
		try {
			String file = ("resources" + File.separator + imageName);
			image = ImageIO.read(new File(file));
		} catch (IOException e) {
			System.out.println("[Error in DisplayObject.java:readImage] Could not read image " + imageName);
			e.printStackTrace();
		}
		return image;
	}
    /**
     * Engine automatically invokes draw() every frame as well. If we want to make sure Sun gets drawn to
     * the screen, we need to make sure to override this method and call Sun's draw method.
     * */
    @Override
    public void draw(Graphics g){
    	//set background scene
    	
    	g.setColor(Color.GRAY);
    	g.fillRect(0, 0, 1400, 900);

        if(Mario != null && button != null) {
        	button.draw(g);
        	ball.draw(g);
        	
            coin.draw(g);
            Mario.draw(g);
            gate.draw(g);

        }

        if(saveStateMario1 != null && save1)
        	saveStateMario1.draw(g);
        if(saveStateMario2 != null && save2)
        	saveStateMario2.draw(g);
        
    }

    /**
     * Quick main class that simply creates an instance of our game and starts the timer
     * that calls update() and draw() every frame
     * */
    public static void main(String[] args) {
        finalgame game = new finalgame();
        game.start();
    }

    // Where all our events are for right now

    public void handleEvent(Event event)
    {
        if(event.getEventType()=="CoinPickedUp") {
            Mario.setAlpha(0);
            System.out.println("Quest is completed!");

            coinTween.animate(TweenableParams.xPos, 950, 420, 1500);
            coinTween.animate(TweenableParams.yPos, 100, 230, 1500);
            coinTween.animate(TweenableParams.scaleX, 1, 3, 1500);
            coinTween.animate(TweenableParams.scaleY, 1, 3, 1500);
            coinTween.addEventListener(this, TweenEvent.TWEEN_COMPLETE_EVENT);

            juggler.add(coinTween);
        }
        
        // This event is called when the button is pressed
        if(event.getEventType()=="ButtonPressed") {
            System.out.println("Button is being pressed");
            button.setDisplayImage("button_pressed.png");
            //Set position of the pressed button sprite a little bit lower so that it looks better
            button.setyPos(765);
            
            // Logic for getting gate to raise
            buttonPressed = true;
            if(gate.getyPos()>220){
            	gate.setyPos((gate.getyPos()-5));
            }
            gate.setV(0);

        }

        // Tween event
        if(event.getEventType()==TweenEvent.TWEEN_COMPLETE_EVENT) {
           coinTween.removeEventListener(this, TweenEvent.TWEEN_COMPLETE_EVENT);
           TweenTransitions transit = new TweenTransitions();
           Tween fadeTween = new Tween(coin,transit);
           fadeTween.animate(TweenableParams.alpha,1,0,2000);
           fadeTween.animate(TweenableParams.rotation,1,30,2000);
           juggler.add(fadeTween);
        }
        
        

// Not doing anything rn
        if(event.getEventType()=="collide") {
//            System.out.println("Collision!");
//
//		//Added in saveState marios
//            Rectangle inter = Mario.getHitBox().intersection(button.getHitBox());
//            Rectangle inter1 = saveStateMario1.getHitBox().intersection(button.getHitBox());
//            Rectangle inter2 = saveStateMario2.getHitBox().intersection(button.getHitBox());
//
//            if(!inter.isEmpty()) {
//
//                //intesect from above, then bottom does not touch ground
//                //moreover, edge case
//                if(inter.getY()+inter.getHeight()>button.getyPos()
//                    && inter.getWidth()>=inter.getHeight()+5) {
//                        Mario.setOnGround(true);
//                }
//
//                //intersect from left, hitbox start from left of coin
//                else {
//
//                    if(inter.getX()==button.getxPos()) {
//                        Mario.setxPos(button.getxPos()-Mario.getWidth());
//                    }
//
//                    //intersect from right, hitbox start from right of coin
//                    if(inter.getX()+inter.getWidth()==button.getxPos()+button.getWidth()) {
//                        Mario.setxPos(button.getxPos()+button.getWidth());
//                    }
//                    Mario.setOnGround(false);
//                }
//
//
//            }

        }


        if(event.getEventType()=="collide2") {
            System.out.println("Collision! with gate");

            Rectangle inter = Mario.getHitBox().intersection(gate.getHitBox());

            if(!inter.isEmpty()) {

                //intesect from above, then bottom does not touch ground
                //moreover, edge case
                if(inter.getY()+inter.getHeight()>=gate.getyPos()
                    && inter.getWidth()>=inter.getHeight()+5) {
                    if(inter.getY()+inter.getHeight()<=gate.getyPos()+(gate.getHeight()/2)) {
                        Mario.setOnGround(true);
                    } else {
                        Mario.setV(0);;
                        Mario.setyPos(gate.getyPos()+gate.getHeight());
                    }
                }
                //intersect from left, hitbox start from left of coin
                else {

                    if(inter.getX()==gate.getxPos()) {
                        Mario.setxPos(gate.getxPos()-Mario.getWidth());
                    }

                    //intersect from right, hitbox start from right of coin
                    if(inter.getX()+inter.getWidth()==gate.getxPos()+gate.getWidth()) {
                        Mario.setxPos(gate.getxPos()+gate.getWidth());
                    }
                    Mario.setOnGround(false);
                }
            }
        }
    }
}
