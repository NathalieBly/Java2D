package game.main;

import edu.virginia.engine.display.AnimatedSprite;
import edu.virginia.engine.display.Brick;
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
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public class finalgameBrickJump extends Game implements IEventListener
{
    AnimatedSprite Mario = new AnimatedSprite("player");
    Sprite saveStateMario1 = new Sprite("saveStateMario1","saved1.png");
    boolean save1 = false;
    Sprite saveStateMario2 = new Sprite("saveStateMario2","saved2.png");
    boolean save2 = false;
    int saveTracker = 1;
    // Quick fix for s key being pressed multiple times 
    int sFrames = 0;
    boolean buttonPressed = false;
    boolean buttonPressed2 = false;
    
    Brick brick = new Brick("Brick","Brick.png");
    Brick brick2 = new Brick("Brick2","Brick.png");
    Brick brick3 = new Brick("Brick3","Brick.png");
    Brick brick4 = new Brick("Brick4","Brick.png");


    Brick button = new Brick("button","button.png");
    Brick button2 = new Brick("button2","button.png");
    Brick button3 = new Brick("button","button.png");

    Brick gate = new Brick("gate","gate.png");
    Brick platform = new Brick("platform","platform.png");
    Brick platform2 = new Brick("platform2","platform.png");


    
    // For showing we can "reset" our game state
    Sprite angryBrick = new Sprite("angryBrick","AngryBrick.png");
    
    //The goal
    Sprite goal = new Sprite("goal","goal.png");
    Sprite complete = new Sprite("complete", "complete.png");
    

    // Holds the starting positions of all our moveable sprites, so that they can be 
    // reset when we "save/reload"
    HashMap<Sprite, Point> startingPositions = new HashMap<Sprite, Point>();
    Tween angryTween;
    Tween compTween;
    QuestManager manager = new QuestManager();
    private boolean condition=false;
    GameClock gameTimer;
    int time;
    TweenJuggler juggler = new TweenJuggler();
	private boolean buttonPressed3;


    public finalgameBrickJump() {
        super("BetaLVL06", 1200, 800);

        
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

        complete.setxPos(350);
        complete.setyPos(180);
        complete.setAlpha(0);
        complete.setxPivot(200);
        complete.setyPivot(280);
        
        goal.setxPos(30);
        goal.setyPos(35);
        

        brick.setxPos(775);
        brick.setyPos(475);
        brick.setAlpha(0);
        startingPositions.put(brick,new Point((int)brick.getxPos(), (int)brick.getyPos()));

        

        brick2.setxPos(900);
        brick2.setyPos(650);

        brick3.setxPos(650);
        brick3.setyPos(300);
        
        brick4.setxPos(220);
        brick4.setyPos(225);

        Mario.setxPos(20);
        Mario.setyPos(640);
        //Add mario's starting coords to starting coord Map
        startingPositions.put(Mario,new Point((int)Mario.getxPos(), (int)Mario.getyPos()));
        
        gate.setxPos(500);
        gate.setyPos(465);
        startingPositions.put(gate,new Point((int)gate.getxPos(), (int)gate.getyPos()));
        
        platform.setxPos(415);
        platform.setyPos(435);
        startingPositions.put(platform,new Point((int)platform.getxPos(), (int)platform.getyPos()));
        
        platform2.setxPos(20);
        platform2.setyPos(150);
        
        button.setxPos(300);
        button.setyPos(740);
        
        button2.setxPos(700);
        button2.setyPos(740);
        
        button3.setxPos(900);
        button3.setyPos(100);
        
        angryBrick.setxPos(300);
        angryBrick.setyPos(200);
        startingPositions.put(angryBrick,new Point((int)angryBrick.getxPos(), (int)angryBrick.getyPos()));

        
        TweenTransitions transit = new TweenTransitions();
        Tween marioTween = new Tween(Mario, transit);
        Tween angryTween = new Tween(angryBrick, transit);
        angryTween.addEventListener(this, TweenEvent.TWEEN_COMPLETE_EVENT);
		compTween = new Tween(complete,transit);
		
		
        marioTween.animate(TweenableParams.alpha, 0, 1, 1000);
        marioTween.animate(TweenableParams.yPos, 300, 670, 1000);
        angryTween.animate(TweenableParams.yPos, 225, 700, 2000);
        

        juggler.add(marioTween);
        juggler.add(angryTween);

        goal.addEventListener(this, "inGoalEvent");
        
        brick.addEventListener(this,  "collide");
        brick2.addEventListener(this, "collide3");
        brick3.addEventListener(this, "collideWithTopBrick");
        brick4.addEventListener(this, "collideWithgoalBrick");
        angryBrick.addEventListener(this, "hazardCollision");
        button.addEventListener(this, "ButtonPressed");
        button2.addEventListener(this, "ButtonPressed2");
        button3.addEventListener(this, "ButtonPressed3");
        saveStateMario1.addEventListener(this, "hitShadow1");
        saveStateMario2.addEventListener(this, "hitShadow2");
        gate.addEventListener(this,  "collide2");
        platform.addEventListener(this,  "collide4");
        platform2.addEventListener(this,  "collideWithTopPlatform");
       
        


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
    	//For gate
    	buttonPressed=false;
    	
    	//For brick
    	buttonPressed2=false;
    	
    	buttonPressed3=false;

    	TweenTransitions transit = new TweenTransitions();
    	
    	
    	// Our "save" function key
		if(pressedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_SPACE)) && sFrames == 0){
			
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
					
			        Tween marioTween = new Tween(Mario, transit);
			        Tween angryTween = new Tween(angryBrick, transit);

			        marioTween.animate(TweenableParams.alpha, 0, 1, 1000);
			        marioTween.animate(TweenableParams.yPos, 300, 670, 1000);
			        angryTween.animate(TweenableParams.yPos, 225, 680, 2000);

			        juggler.add(marioTween);
			        juggler.add(angryTween);
				}		  
			}
			

			// A ghetto way of making sure this s key if statement is called at max every 10 frames
			sFrames = 10;
		}

        if(Mario != null && button != null) {
        	
        	
        	// Resetting the button if it is not being stepped on
        	button.setDisplayImage("button.png");
        	button.setyPos(740);
        	
        	button2.setDisplayImage("button.png");
        	button2.setyPos(740);
        	
        	button3.setDisplayImage("button.png");
        	button3.setyPos(100);
        	
            //update y position accordingly
            Mario.setyPos(Mario.getyPos()+Mario.getV());
            


            // I do not know what setV is or what this code is doing - Nate
            //determine if on ground
            //boundary check
            if(Mario.getyPos()>=662) {
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
            if(Mario.getxPos()>1150) Mario.setxPos(1150);
            //if(Mario.getyPos()<0) Mario.setyPos(0);
            if(Mario.getyPos()>=662) Mario.setyPos(662);


            Mario.update(pressedKeys);
            
            if(Mario.isOnGround()) {
        		Mario.setOnGround(false);
        	}

		//Auto Hitbox for goal
        if (Mario.getHitBox().intersects(angryBrick.getHitBox())) {
			Event event = new Event("hazardCollision", angryBrick);
			angryBrick.dispatchEvent(event);
		}
            
        if (Mario.getHitBox().intersects(goal.getHitBox()) && 
        		pressedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_E)) &&
        		sFrames==0) {			
        	sFrames = 20;
        	Event event = new Event("inGoalEvent", goal);
			goal.dispatchEvent(event);
		}
		
		if(Mario.getHitBox().intersects(saveStateMario1.getHitBox())) {
			Event event = new Event("hitShadow1");
		        saveStateMario1.dispatchEvent(event);
		  }
		
		if(Mario.getHitBox().intersects(saveStateMario2.getHitBox())) {
			Event event = new Event("hitShadow2");
			saveStateMario2.dispatchEvent(event);
		  }
		
	    if(Mario.getHitBox().intersects(button.getHitBox())) {
			   Event event = new Event("ButtonPressed");
			   button.dispatchEvent(event);
		 }

		if(saveStateMario1.getHitBox().intersects(button.getHitBox())) {
		    Event event = new Event("ButtonPressed");
		    button.dispatchEvent(event);
		}
		
		if(saveStateMario2.getHitBox().intersects(button.getHitBox())) {
		    Event event = new Event("ButtonPressed");
		    button.dispatchEvent(event);
		}
		
		if(Mario.getHitBox().intersects(button2.getHitBox())) {
		    Event event = new Event("ButtonPressed2");
		        button.dispatchEvent(event);
		  }
		
		 if(saveStateMario1.getHitBox().intersects(button2.getHitBox())) {
		     Event event = new Event("ButtonPressed2");
		     button.dispatchEvent(event);
		 }
		 
		 if(saveStateMario2.getHitBox().intersects(button2.getHitBox())) {
		     Event event = new Event("ButtonPressed2");
		     button.dispatchEvent(event);
		 }
		    
		 if(Mario.getHitBox().intersects(button3.getHitBox())) {
			   Event event = new Event("ButtonPressed3");
			   button3.dispatchEvent(event);
		 }

		if(saveStateMario1.getHitBox().intersects(button3.getHitBox())) {
		    Event event = new Event("ButtonPressed3");
		    button3.dispatchEvent(event);
		}
		
		if(saveStateMario2.getHitBox().intersects(button3.getHitBox())) {
		    Event event = new Event("ButtonPressed3");
		    button3.dispatchEvent(event);
		}
		 
		 if(Mario.getHitBox().intersects(brick2.getHitBox())) {
		
		       Event event = new Event("collide2");
		      brick2.dispatchEvent(event);
		 }
		
		if(Mario.getHitBox().intersects(brick.getHitBox())) {
		
		    Event event = new Event("collide3");
		        brick.dispatchEvent(event);
		 }
		  //Collision with gate
		if(Mario.getHitBox().intersects(gate.getHitBox())) {
			Event event = new Event("gateCollision");
			gate.dispatchEvent(event);
		 }
		//Collision with platform
		if(Mario.getHitBox().intersects(platform.getHitBox())) {
			Event event = new Event("collide4");
			platform.dispatchEvent(event);
		 }
		//Collision with top platform
		if(Mario.getHitBox().intersects(platform2.getHitBox())) {
			Event event = new Event("collideWithTopPlatform");
			platform2.dispatchEvent(event);
		 }
		  //Collision with top brick
		if(Mario.getHitBox().intersects(brick3.getHitBox())) {
			Event event = new Event("collideWithTopBrick");
			brick3.dispatchEvent(event);
		 }    
		  //Collision with near goal brick
		if(Mario.getHitBox().intersects(brick4.getHitBox())) {
			Event event = new Event("collideWithgoalBrick");
			brick4.dispatchEvent(event);
		 }   

            juggler.getInstance().nextFrame();

     }
        
        if(sFrames > 0){
        	sFrames--;
        }
        
        if(buttonPressed == false){
        	if(gate.getyPos() < 465){
            	gate.setyPos(gate.getyPos()+gate.getV());
            	gate.setV((gate.getG()+gate.getV())/1);
            	platform.setyPos(platform.getyPos()+platform.getV());
            	platform.setV((platform.getG()+platform.getV())/1);
        	}
        }
        

        
        if(buttonPressed2 == false){
        	brick.setAlpha(0);
        }


        if(buttonPressed3 == false){
        	platform.setAlpha(0);
        }
     }



    /**
     * Engine automatically invokes draw() every frame as well. If we want to make sure Sun gets drawn to
     * the screen, we need to make sure to override this method and call Sun's draw method.
     * */
    @Override
    public void draw(Graphics g){
    	
    	g.setColor(Color.GRAY);
    	g.fillRect(0, 0, 1400, 900);

        if(Mario != null && button != null) {
        	button.draw(g);
        	angryBrick.draw(g);
        	brick.draw(g);
        	brick2.draw(g);
        	brick3.draw(g);
        	brick4.draw(g);
        	button2.draw(g);
        	button3.draw(g);
        	//Used for drawing our hitboxes
        	//g.fillRect((int)button.getHitBox().getX(),(int) button.getHitBox().getY(),(int)button.getHitBox().getWidth(),(int)button.getHitBox().getHeight());
            goal.draw(g);
            Mario.draw(g);
            gate.draw(g);
            platform.draw(g);
            platform2.draw(g);
            complete.draw(g);

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
        finalgameBrickJump game = new finalgameBrickJump();
        game.start();
    }

    // Where all our events are for right now

    public void handleEvent(Event event)
    {

    	if (event.getEventType() == "hazardCollision") {
			reset();
		}
    	
    	//Intersecting with door
		if (event.getEventType() == "inGoalEvent") {
		  
		  compTween.animate(TweenableParams.scaleX, 3, 1, 1500);
          compTween.animate(TweenableParams.scaleY, 3, 1, 1500);
          compTween.animate(TweenableParams.alpha, 0, 1, 1500);
          compTween.addEventListener(this, TweenEvent.TWEEN_COMPLETE_EVENT);

          juggler.add(compTween);
		}
        
        // This event is called when the button is pressed
        if(event.getEventType()=="ButtonPressed") {
        	buttonPressed = true;
            System.out.println("Button is being pressed");
            button.setDisplayImage("button_pressed.png");
            //Set position of the pressed button sprite a little bit lower so that it looks better
            button.setyPos(753);
            
            if(gate.getyPos()>320){
            	gate.setyPos((gate.getyPos()-5));
            	platform.setyPos((platform.getyPos()-5));
            }
            gate.setV(0);
            platform.setV(0);


        }
        
        if(event.getEventType()=="ButtonPressed2") {
        	brick.setAlpha(1);
        	buttonPressed2 = true;
            System.out.println("Button is being pressed2");
            button2.setDisplayImage("button_pressed.png");
            //Set position of the pressed button sprite a little bit lower so that it looks better
            button2.setyPos(753);
            
        }
        
        if(event.getEventType()=="ButtonPressed3") {
        	platform.setAlpha(1);
        	buttonPressed3 = true;
            System.out.println("Button is being pressed3");
            button3.setDisplayImage("button_pressed.png");
            //Set position of the pressed button sprite a little bit lower so that it looks better
            button3.setyPos(100);
            
        }

        // Tween event
        if(event.getEventType()==TweenEvent.TWEEN_COMPLETE_EVENT) {
           if(compTween.isComplete()) {
	           compTween.removeEventListener(this, TweenEvent.TWEEN_COMPLETE_EVENT);
	           System.out.println("Quest Completed");
	           this.pause();
           }
           if(angryTween.isComplete()) {
        	  
        	   System.out.println("angry is real");
           }
           
        }
        
        


        if(event.getEventType()=="collide") {
            System.out.println("Collision!");
            
            Rectangle inter = Mario.getHitBox().intersection(button.getHitBox());
            Rectangle inter1 = saveStateMario1.getHitBox().intersection(button.getHitBox());
            Rectangle inter2 = saveStateMario2.getHitBox().intersection(button.getHitBox());

            if(!inter.isEmpty()) {

                //intesect from above, then bottom does not touch ground
                //moreover, edge case
                if(inter.getY()+inter.getHeight()>button.getyPos()
                    && inter.getWidth()>=inter.getHeight()+5) {
                	    Mario.setyPos(button.getyPos()-Mario.getHeight());
                        Mario.setOnGround(true);

                }

                //intersect from left, hitbox start from left of goal
                else {

                    if(inter.getX()==button.getxPos()) {
                        Mario.setxPos(button.getxPos()-Mario.getWidth());
                    }

                    //intersect from right, hitbox start from right of goal
                    if(inter.getX()+inter.getWidth()==button.getxPos()+button.getWidth()) {
                        Mario.setxPos(button.getxPos()+button.getWidth());
                    }
                    Mario.setOnGround(false);
                }


            }

        }


        if(event.getEventType()=="collide3" && (brick.getAlpha() == 1)) {
            System.out.println("Collision!");
           
            Rectangle inter = Mario.getHitBox().intersection(brick.getHitBox());
            Rectangle inter3 = Mario.getHitBox().intersection(saveStateMario1.getHitBox());
            Rectangle inter4 = Mario.getHitBox().intersection(saveStateMario2.getHitBox());
            if(!inter.isEmpty()) {

                //intesect from above, then bottom does not touch ground
                //moreover, edge case
                if(inter.getY()+inter.getHeight()>=brick.getyPos()
                    && inter.getWidth()>=inter.getHeight()+5) {
                    if(inter.getY()+inter.getHeight()<=brick.getyPos()+(brick.getHeight()/2)) {
                    	Mario.setyPos(brick.getyPos()-Mario.getHeight());
                        Mario.setOnGround(true);
                    } else {
                        Mario.setV(0);;
                        Mario.setyPos(brick.getyPos()+brick.getHeight());
                    }
                }
                //intersect from left, hitbox start from left of goal
                else {

                    if(inter.getX()==brick.getxPos()) {
                        Mario.setxPos(brick.getxPos()-Mario.getWidth());
                    }

                    //intersect from right, hitbox start from right of goal
                    if(inter.getX()+inter.getWidth()==brick.getxPos()+brick.getWidth()) {
                        Mario.setxPos(brick.getxPos()+brick.getWidth());
                    }
                    Mario.setOnGround(false);
                }
            }
        }
        
        if(event.getEventType()=="collide4" && (platform.getAlpha() == 1)) {
        	System.out.println("Collision!");
            
            Rectangle inter = Mario.getHitBox().intersection(platform.getHitBox());
            Rectangle inter3 = Mario.getHitBox().intersection(saveStateMario1.getHitBox());
            Rectangle inter4 = Mario.getHitBox().intersection(saveStateMario2.getHitBox());
            if(!inter.isEmpty()) {

                //intesect from above, then bottom does not touch ground
                //moreover, edge case
                if(inter.getY()+inter.getHeight()>=platform.getyPos()
                    && inter.getWidth()>=inter.getHeight()+5) {
                    if(inter.getY()+inter.getHeight()<=platform.getyPos()+(platform.getHeight()/1.4)) {
                    	Mario.setyPos(platform.getyPos()-Mario.getHeight());
                        Mario.setOnGround(true);
                    } else {
                        Mario.setV(0);;
                        Mario.setyPos(platform.getyPos()+platform.getHeight());
                    }
                }
                //intersect from left, hitbox start from left of goal
                else {

                    if(inter.getX()==platform.getxPos()) {
                        Mario.setxPos(platform.getxPos()-Mario.getWidth());
                    }

                    //intersect from right, hitbox start from right of goal
                    if(inter.getX()+inter.getWidth()==platform.getxPos()+platform.getWidth()) {
                        Mario.setxPos(platform.getxPos()+platform.getWidth());
                    }
                    Mario.setOnGround(false);
                }
            }
        }
        
        
        if(event.getEventType()=="collideWithTopPlatform") {
        	System.out.println("Collision!");
            
            Rectangle inter = Mario.getHitBox().intersection(platform2.getHitBox());
            Rectangle inter3 = Mario.getHitBox().intersection(saveStateMario1.getHitBox());
            Rectangle inter4 = Mario.getHitBox().intersection(saveStateMario2.getHitBox());
            if(!inter.isEmpty()) {

                //intesect from above, then bottom does not touch ground
                //moreover, edge case
                if(inter.getY()+inter.getHeight()>=platform2.getyPos()
                    && inter.getWidth()>=inter.getHeight()+5) {
                    if(inter.getY()+inter.getHeight()<=platform2.getyPos()+(platform2.getHeight()/2)) {
                    	Mario.setyPos(platform2.getyPos()-Mario.getHeight());
                        Mario.setOnGround(true);
                    } else {
                        Mario.setV(0);;
                        Mario.setyPos(platform2.getyPos()+platform2.getHeight());
                    }
                }
                //intersect from left, hitbox start from left of goal
                else {

                    if(inter.getX()==platform2.getxPos()) {
                        Mario.setxPos(platform2.getxPos()-Mario.getWidth());
                    }

                    //intersect from right, hitbox start from right of goal
                    if(inter.getX()+inter.getWidth()==platform2.getxPos()+platform2.getWidth()) {
                        Mario.setxPos(platform2.getxPos()+platform2.getWidth());
                    }
                    Mario.setOnGround(false);
                }
            }
        }
        
        
        if(event.getEventType()=="collideWithTopBrick") {
            System.out.println("Collision!");
           
            Rectangle inter = Mario.getHitBox().intersection(brick3.getHitBox());
            Rectangle inter3 = Mario.getHitBox().intersection(saveStateMario1.getHitBox());
            Rectangle inter4 = Mario.getHitBox().intersection(saveStateMario2.getHitBox());
            if(!inter.isEmpty()) {

                //intesect from above, then bottom does not touch ground
                //moreover, edge case
                if(inter.getY()+inter.getHeight()>=brick3.getyPos()
                    && inter.getWidth()>=inter.getHeight()+5) {
                    if(inter.getY()+inter.getHeight()<=brick3.getyPos()+(brick3.getHeight()/2)) {
                    	Mario.setyPos(brick3.getyPos()-Mario.getHeight());
                        Mario.setOnGround(true);
                    } else {
                        Mario.setV(0);;
                        Mario.setyPos(brick3.getyPos()+brick3.getHeight());
                    }
                }
                //intersect from left, hitbox start froms
//                left of goal
                else {

                    if(inter.getX()==brick3.getxPos()) {
                        Mario.setxPos(brick3.getxPos()-Mario.getWidth());
                    }

                    //intersect from right, hitbox start from right of goal
                    if(inter.getX()+inter.getWidth()==brick3.getxPos()+brick3.getWidth()) {
                        Mario.setxPos(brick3.getxPos()+brick3.getWidth());
                    }
                    Mario.setOnGround(false);
                }
            }
        }
        
        
        
        if(event.getEventType()=="collideWithgoalBrick") {
            System.out.println("Collision!");
           
            Rectangle inter = Mario.getHitBox().intersection(brick4.getHitBox());
            Rectangle inter3 = Mario.getHitBox().intersection(saveStateMario1.getHitBox());
            Rectangle inter4 = Mario.getHitBox().intersection(saveStateMario2.getHitBox());
            if(!inter.isEmpty()) {

                //intesect from above, then bottom does not touch ground
                //moreover, edge case
                if(inter.getY()+inter.getHeight()>=brick4.getyPos()
                    && inter.getWidth()>=inter.getHeight()+5) {
                    if(inter.getY()+inter.getHeight()<=brick4.getyPos()+(brick4.getHeight()/2)) {
                    	Mario.setyPos(brick4.getyPos()-Mario.getHeight());
                        Mario.setOnGround(true);
                    } else {
                        Mario.setV(0);;
                        Mario.setyPos(brick4.getyPos()+brick4.getHeight());
                    }
                }
                //intersect from left, hitbox start from left of goal
                else {

                    if(inter.getX()==brick4.getxPos()) {
                        Mario.setxPos(brick4.getxPos()-Mario.getWidth());
                    }

                    //intersect from right, hitbox start from right of goal
                    if(inter.getX()+inter.getWidth()==brick4.getxPos()+brick4.getWidth()) {
                        Mario.setxPos(brick4.getxPos()+brick4.getWidth());
                    }
                    Mario.setOnGround(false);
                }
            }
        }
        
        
       
        
        if(event.getEventType()=="hitShadow1") {
            System.out.println("Collision! with shado");
            Rectangle inter3 = Mario.getHitBox().intersection(saveStateMario1.getHitBox());
            
            
            	if(!inter3.isEmpty()) {
                //intesect from above, then bottom does not touch ground
                //moreover, edge case
                if(inter3.getY()+inter3.getHeight()>=saveStateMario1.getyPos()
                    && inter3.getWidth()>=inter3.getHeight()+5) {
                    if(inter3.getY()+inter3.getHeight()<=saveStateMario1.getyPos()+(saveStateMario1.getHeight()/2)) {
                    	 Mario.setyPos(saveStateMario1.getyPos()-Mario.getHeight());

                    	Mario.setOnGround(true);
                    } else {
                        Mario.setV(0);;
                        Mario.setyPos(saveStateMario1.getyPos()+saveStateMario1.getHeight());
                    }
                }
                //intersect from left, hitbox start from left of goal
                else {

                    if(inter3.getX()==saveStateMario1.getxPos()) {
                        Mario.setxPos(saveStateMario1.getxPos()-saveStateMario1.getWidth());
                    }

                    //intersect from right, hitbox start from right of goal
                    if(inter3.getX()+inter3.getWidth()==saveStateMario1.getxPos()+saveStateMario1.getWidth()) {
                        Mario.setxPos(saveStateMario1.getxPos()+saveStateMario1.getWidth());
                    }
                    Mario.setOnGround(false);
                }
            }
        }
        
        
        if(event.getEventType()=="hitShadow2") {
            System.out.println("Collision!");
            Rectangle inter4 = Mario.getHitBox().intersection(saveStateMario2.getHitBox());
            if(!inter4.isEmpty()) {

                //intesect from above, then bottom does not touch ground
                //moreover, edge case
                if(inter4.getY()+inter4.getHeight()>=saveStateMario2.getyPos()
                    && inter4.getWidth()>=inter4.getHeight()+5) {
                    if(inter4.getY()+inter4.getHeight()<=saveStateMario2.getyPos()+(saveStateMario2.getHeight()/2)) {
                    	 Mario.setyPos(saveStateMario2.getyPos()-Mario.getHeight());

                    	Mario.setOnGround(true);
                    } else {
                        Mario.setV(0);
                        Mario.setyPos(saveStateMario2.getyPos()+saveStateMario2.getHeight());
                    }
                }
                //intersect from left, hitbox start from left of goal
                else {

                    if(inter4.getX()==saveStateMario2.getxPos()) {
                        Mario.setxPos(saveStateMario2.getxPos()-saveStateMario2.getWidth());
                    }

                    //intersect from right, hitbox start from right of goal
                    if(inter4.getX()+inter4.getWidth()==saveStateMario2.getxPos()+saveStateMario2.getWidth()) {
                        Mario.setxPos(saveStateMario2.getxPos()+saveStateMario2.getWidth());
                    }
                    Mario.setOnGround(false);
                }
            }
        }
        
        if(event.getEventType()=="gateCollision") {
            System.out.println("Collision! with gate");

            Rectangle inter = Mario.getHitBox().intersection(gate.getHitBox());

            if(!inter.isEmpty()) {

                //intesect from above, then bottom does not touch ground
                //moreover, edge case
                if(inter.getY()+inter.getHeight()>=gate.getyPos()
                    && inter.getWidth()>=inter.getHeight()+5) {
                    if(inter.getY()+inter.getHeight()<=gate.getyPos()+(gate.getHeight()/2)) {
                        Mario.setOnGround(false);

                    } else {
                        Mario.setV(0);
                        Mario.setyPos(gate.getyPos()+gate.getHeight());
                    }
                }
                //intersect from left, hitbox start from left of goal
                else {

                    if(inter.getX()==gate.getxPos()) {
                        Mario.setxPos(gate.getxPos()-Mario.getWidth());
                    }

                    //intersect from right, hitbox start from right of goal
                    if(inter.getX()+inter.getWidth()==gate.getxPos()+gate.getWidth()) {
                        Mario.setxPos(gate.getxPos()+gate.getWidth());
                    }
                    Mario.setOnGround(false);
                }
            }
        }
        
        if(event.getEventType()=="collide2") {
            System.out.println("Collision!");

            Rectangle inter = Mario.getHitBox().intersection(brick2.getHitBox());

            if(!inter.isEmpty()) {

                //intesect from above, then bottom does not touch ground
                //moreover, edge case
                if(inter.getY()+inter.getHeight()>=brick2.getyPos()
                    && inter.getWidth()>=inter.getHeight()+5) {
                    if(inter.getY()+inter.getHeight()<=brick2.getyPos()+(brick2.getHeight()/2)) {
                    	 Mario.setyPos(brick2.getyPos()-Mario.getHeight());
                        Mario.setOnGround(true);

                    } else {
                        Mario.setV(0);;
                        Mario.setyPos(brick2.getyPos()+brick2.getHeight());
                    }
                }
                //intersect from left, hitbox start from left of goal
                else {

                    if(inter.getX()==brick2.getxPos()) {
                        Mario.setxPos(brick2.getxPos()-Mario.getWidth());
                    }

                    //intersect from right, hitbox start from right of goal
                    if(inter.getX()+inter.getWidth()==brick2.getxPos()+brick2.getWidth()) {
                        Mario.setxPos(brick2.getxPos()+brick2.getWidth());
                    }
                    Mario.setOnGround(false);
                }
            }
        }
    }

	private void reset() {
		save1 = false;
		save2 = false;
		saveTracker=1;
		
		
			saveStateMario1.setxPos(99999);
			saveStateMario1.setyPos(99999);
		
			saveStateMario2.setxPos(999999);
			saveStateMario2.setyPos(999999);
			
			
		// We have a hashMap of <Sprite, Starting x and y>
		Iterator entries = startingPositions.entrySet().iterator();
		while (entries.hasNext()) {
			// Grab our sprite and Point
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
		        Tween angryTween = new Tween(angryBrick, transit);

		        marioTween.animate(TweenableParams.alpha, 0, 1, 1000);
		        marioTween.animate(TweenableParams.yPos, 300, 670, 1000);
		        angryTween.animate(TweenableParams.yPos, 225, 600, 2000);

		        juggler.add(marioTween);
		        juggler.add(angryTween);
			}
		}

	}
}