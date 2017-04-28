package game.main;

//Imports from our packages
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

//Imports from  java packages
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

public class BetaLVL05 extends Game implements IEventListener {
	 //Size of our Game
		static int MAXHEIGHT = 800;
		static int MAXWIDTH = 1200;
		
		// Checking out how to use the level switcher
		static Beta game;
		int eFrames;
		Sound bgm;

		// Player sprite and save state variables
		AnimatedSprite player = new AnimatedSprite("player");
		Sprite saveState1 = new Sprite("saveState1", "saved1.png");
		boolean save1 = false;
		Sprite saveState2 = new Sprite("saveState2", "saved2.png");
		boolean save2 = false;
		int saveTracker = 1;
		int sFrames = 0;

		// Button sprites and variables

		// Platform sprites and variables
	    Brick brick = new Brick("Brick","Brick.png");
	    Brick brick2 = new Brick("Brick2","Brick.png");
	    Brick brick3 = new Brick("Brick3","Brick.png");
	    Brick brick4 = new Brick("Brick4","Brick.png");
	    Brick brick5 = new Brick("Brick5","Brick.png");
	    Brick brick6 = new Brick("Brick6","Brick.png");
	    Brick brick7 = new Brick("Brick7","Brick.png");
	    Brick brick8 = new Brick("Brick8","Brick.png");
	    Brick brick9 = new Brick("Brick9","Brick.png");
	    Brick brick10 = new Brick("Brick10","Brick.png");
	    Brick brick11 = new Brick("Brick11","Brick.png");
	    
	    Brick button = new Brick("button","button.png");
	    Brick button2 = new Brick("button2","button.png");
	    

		// Hazards sprites and variables

		// Objective sprites and variables
		Sprite goal = new Sprite("goal","goal.png");
		boolean inGoal = false;
	
		
		// Holds the starting positions of all our moveable sprites, so that they
		// can be reset when we "save/reload"
		HashMap<Sprite, Point> startingPositions = new HashMap<Sprite, Point>();

		// Managers and singletons
		QuestManager manager = new QuestManager();
		GameClock gameTimer;
		TweenJuggler juggler = new TweenJuggler();
		private boolean buttonPressed;
		private boolean buttonPressed2;

		public BetaLVL05() {
			super("BetaLVL0005", MAXWIDTH, MAXHEIGHT);
			
			// Animated sprite, not doing anything now
			List<String> animatedSpriteList = new ArrayList<String>();
			animatedSpriteList.add("hero.png");
			animatedSpriteList.add("hero.png");
			player = new AnimatedSprite("player", animatedSpriteList);
			HashMap<String, int[]> animations = new HashMap<String, int[]>();
			int[] num = new int[2];
			num[0] = 0;
			num[1] = 1;
			animations.put("run", num);
			player.setAnimations(animations);

			// Sound info
			bgm = new Sound("cooking.wav");
			bgm.loop();

			// Sprite positioning (SHOULD PROBABLY RE WORK THIS AT SOME POINT)
			player.setxPos(20);
			player.setyPos(640);
			startingPositions.put(player, new Point((int) player.getxPos(), (int) player.getyPos()));

			brick.setxPos(100);
			brick.setyPos(650);

			brick2.setxPos(180);
			brick2.setyPos(550);
			
			brick3.setxPos(260);
			brick3.setyPos(450);
			
			brick4.setxPos(340);
			brick4.setyPos(350);
			
			brick5.setxPos(420);
			brick5.setyPos(250);
			
			brick6.setxPos(500);
			brick6.setyPos(350);
			
			brick7.setxPos(580);
			brick7.setyPos(450);
			
			brick8.setxPos(660);
			brick8.setyPos(550);
			
			brick9.setxPos(720);
			brick9.setyPos(650);
			
			brick10.setxPos(825);
			brick10.setyPos(150);
			
			brick11.setxPos(500);
			brick11.setyPos(600);
			
			button.setxPos(850);
			button.setyPos(180);
			
			button2.setxPos(850);
			button2.setyPos(120);
			 
			
			goal.setxPos(250);
			goal.setyPos(645);
			
			// Player tweens
			TweenTransitions transit = new TweenTransitions();
			Tween marioTween = new Tween(player, transit);
			marioTween.animate(TweenableParams.alpha, 0, 1, 100);
//			marioTween.animate(TweenableParams.yPos, 300, 670, 100);
			juggler.add(marioTween);

			// Event registering
			saveState1.addEventListener(this, "playerCollision");
			saveState2.addEventListener(this, "playerCollision");
			brick.addEventListener(this, "playerCollision");
			brick2.addEventListener(this, "playerCollision");
			brick3.addEventListener(this, "playerCollision");
			brick4.addEventListener(this, "playerCollision");
			brick5.addEventListener(this, "playerCollision");
			brick6.addEventListener(this, "playerCollision");
			brick7.addEventListener(this, "playerCollision");
			brick8.addEventListener(this, "playerCollision");
			brick9.addEventListener(this, "playerCollision");
			brick10.addEventListener(this, "playerCollision");
			brick11.addEventListener(this, "playerCollision");
			goal.addEventListener(this, "inGoalEvent");
			button.addEventListener(this, "ButtonPressed");
			button2.addEventListener(this, "ButtonPressed2");

			if (gameTimer == null) {
				gameTimer = new GameClock();
			}

		}

		/**
		 * Engine will automatically call this update method once per frame and pass
		 * to us the set of keys (as strings) that are currently being pressed down
		 */
		@Override
		public void update(ArrayList<String> pressedKeys) {
			//Reset our flags at start of frame
			buttonPressed = false;
			buttonPressed2 = false;
			
			inGoal = false;

			//Door logic?
			if (player.getHitBox().intersects(goal.getHitBox())) {			
				Event event = new Event("inGoalEvent", goal);
				goal.dispatchEvent(event);
			}
			
			
			
			
			///Key logic
			if (pressedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_E)) && eFrames == 0) {
				// A ghetto way of making sure this s key if statement is called at
				// max every 10 frames
				eFrames = 20;
				
				
				
				
				//Check if we are intersecting with door1
				if(inGoal && eFrames == 20){
					bgm.stop();
					game = new Beta();
					game.start();
					game.setLevelComplete(5);
					this.exitGame();
				}


				

			}

			// Our "save" function key
			if (pressedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_SPACE)) && sFrames == 0) {

				// Figure out where to save our old player ghost
				if (save1 == false) {
					saveState1.setxPos(player.getxPos());
					saveState1.setyPos(player.getyPos());
					save1 = true;
				} else if (save2 == false) {
					saveState2.setxPos(player.getxPos());
					saveState2.setyPos(player.getyPos());
					save2 = true;
				}
				// This is for overwriting the ghosts
				else if (save1 && save2) {
					if (saveTracker == 1) {
						saveState1.setxPos(player.getxPos());
						saveState1.setyPos(player.getyPos());
						saveTracker = 2;
					} else if (saveTracker == 2) {
						saveState2.setxPos(player.getxPos());
						saveState2.setyPos(player.getyPos());
						saveTracker = 1;
					}
				}

				// We have a hashMap of <Sprite, Starting x and y>
				Iterator entries = startingPositions.entrySet().iterator();
				while (entries.hasNext()) {
					// Grab our sprite and Point
					Entry thisEntry = (Entry) entries.next();
					Object sprite = thisEntry.getKey();
					Object pos = thisEntry.getValue();

					// Set our sprite back to it's starting position
					((Sprite) sprite).setxPos(((Point) pos).getX());
					((Sprite) sprite).setyPos(((Point) pos).getY());

					// An if check to replay our tween if its the player
					if (((Sprite) sprite).getId().equals("player")) {
						TweenTransitions transit = new TweenTransitions();
						Tween marioTween = new Tween(player, transit);

						marioTween.animate(TweenableParams.alpha, 0, 1, 1000);
//						marioTween.animate(TweenableParams.yPos, 300, 670, 1000);

						juggler.add(marioTween);
					}
				}

				// A ghetto way of making sure this s key if statement is called at
				// max every 10 frames
				sFrames = 10;
			}

			//hitbox logic
			if (player != null && button!=null) {
				
				// Resetting the button if it is not being stepped on
				button.setDisplayImage("button.png");
	        	button.setyPos(740);
	        	
	        	button2.setDisplayImage("button.png");
	        	button2.setyPos(120);
	        	
				// Jumping and falling
				player.setyPos(player.getyPos() + player.getV());

				// Check if we are on ground?
				if (player.getyPos() >= 650) {
					player.setOnGround(true);
				}

				// Reset velocity to 0 if player is on ground
				if (player.isOnGround()) {
					player.setV(0);
				}

				// Check if we are in air
				else {
					player.setV(player.getV() + player.getG());
					System.out.println("falling");
				}

				// boundary checking
				if (player.getxPos() < 0)
					player.setxPos(0);
				if (player.getxPos() > 1150)
					player.setxPos(1150);
				if (player.getyPos() > 800)
					player.setyPos(800);

				player.update(pressedKeys);

				if (player.isOnGround()) {
					player.setOnGround(false);
				}

				// Savestate hit boxes
				if (player.getHitBox().intersects(saveState1.getHitBox())) {
					Event event = new Event("playerCollision", saveState1);
					saveState1.dispatchEvent(event);
				}
				if (player.getHitBox().intersects(saveState2.getHitBox())) {
					Event event = new Event("playerCollision", saveState2);
					saveState2.dispatchEvent(event);
				}
				
				if(player.getHitBox().intersects(button.getHitBox())) {
					   Event event = new Event("ButtonPressed");
					   button.dispatchEvent(event);
				 }
				
				if(saveState1.getHitBox().intersects(button.getHitBox())) {
				    Event event = new Event("ButtonPressed");
				    button.dispatchEvent(event);
				}
				
				if(saveState2.getHitBox().intersects(button.getHitBox())) {
				    Event event = new Event("ButtonPressed");
				    button.dispatchEvent(event);
				}
				
				
				
				if(player.getHitBox().intersects(button2.getHitBox())) {
					   Event event = new Event("ButtonPressed2");
					   button2.dispatchEvent(event);
				 }
				
				if(saveState1.getHitBox().intersects(button2.getHitBox())) {
				    Event event = new Event("ButtonPressed2");
				    button2.dispatchEvent(event);
				}
				
				if(saveState2.getHitBox().intersects(button2.getHitBox())) {
				    Event event = new Event("ButtonPressed2");
				    button2.dispatchEvent(event);
				}
				
				
				
				
				if (player.getHitBox().intersects(brick.getHitBox())) {
					Event event = new Event("playerCollision", brick);
					brick.dispatchEvent(event);
				}
				if (player.getHitBox().intersects(brick2.getHitBox())) {
					Event event = new Event("playerCollision", brick2);
					brick2.dispatchEvent(event);
				}
				if (player.getHitBox().intersects(brick3.getHitBox())) {
					Event event = new Event("playerCollision", brick3);
					brick3.dispatchEvent(event);
				}
				if (player.getHitBox().intersects(brick4.getHitBox())) {
					Event event = new Event("playerCollision", brick4);
					brick4.dispatchEvent(event);
				}
				if (player.getHitBox().intersects(brick5.getHitBox())) {
					Event event = new Event("playerCollision", brick5);
					brick5.dispatchEvent(event);
				}
				if (player.getHitBox().intersects(brick6.getHitBox())) {
					Event event = new Event("playerCollision", brick6);
					brick6.dispatchEvent(event);
				}
				if (player.getHitBox().intersects(brick7.getHitBox())) {
					Event event = new Event("playerCollision", brick7);
					brick7.dispatchEvent(event);
				}
				if (player.getHitBox().intersects(brick8.getHitBox())) {
					Event event = new Event("playerCollision", brick8);
					brick8.dispatchEvent(event);
				}
				if (player.getHitBox().intersects(brick9.getHitBox())&&buttonPressed==false) {
					Event event = new Event("playerCollision", brick9);
					brick9.dispatchEvent(event);
				}
				if (player.getHitBox().intersects(brick10.getHitBox())) {
					Event event = new Event("playerCollision", brick10);
					brick10.dispatchEvent(event);
				}
				if (player.getHitBox().intersects(brick11.getHitBox())) {
					Event event = new Event("playerCollision", brick11);
					brick11.dispatchEvent(event);
				}
				

				juggler.getInstance().nextFrame();

			}
			
			 if(buttonPressed == false){
				 brick9.setxPos(720);
				 brick9.setyPos(650);
			 }
			 
			 if(buttonPressed2 == false){
				 brick11.setxPos(500);
				 brick11.setyPos(600);
			 }

			// Making it so we can only hit S once every ten frames
			if (sFrames > 0) {
				sFrames--;
			}

			//Making it so we can only hit E once every ten frames
			if (eFrames > 0) {
				eFrames--;
			}
		}

		/**
		 * Engine automatically invokes draw() every frame as well. If we want to
		 * make sure Sun gets drawn to the screen, we need to make sure to override
		 * this method and call Sun's draw method.
		 */
		@Override
		public void draw(Graphics g) {

			// Background
			g.setColor(Color.GRAY);
			g.fillRect(0, 0, 1400, 900);

			if (player != null) {
				goal.draw(g);
				brick.draw(g);
				brick2.draw(g);
				brick3.draw(g);
				brick4.draw(g);
				brick5.draw(g);
				brick6.draw(g);
				brick7.draw(g);
				brick8.draw(g);
				brick9.draw(g);
				brick10.draw(g);
				brick11.draw(g);
				button.draw(g);
				button2.draw(g);
				
				player.draw(g);


			}

			// Draw savestates
			if (saveState1 != null && save1)
				saveState1.draw(g);
			if (saveState2 != null && save2)
				saveState2.draw(g);

		}

		/**
		 * Quick main class that simply creates an instance of our game and starts
		 * the timer that calls update() and draw() every frame
		 */
		public static void main(String[] args) {
			BetaLVL05 game = new BetaLVL05();
			game.start();

		}

		// Where all our events are for right now

		public void handleEvent(Event event) {

			// This event is called when the button is pressed
	        if(event.getEventType()=="ButtonPressed") {
	        	buttonPressed = true;
	            System.out.println("Button is being pressed");
	            button.setDisplayImage("button_pressed.png");
	            //Set position of the pressed button sprite a little bit lower so that it looks better
	            button.setyPos(753);
	            
	            brick9.setxPos(1000000);
	            brick9.setyPos(1000000);
	        }
	        
	        if(event.getEventType()=="ButtonPressed2") {
	        	buttonPressed2 = true;
	            System.out.println("Button2 is being pressed");
	            button2.setDisplayImage("button_pressed.png");
	            //Set position of the pressed button sprite a little bit lower so that it looks better
	            button2.setyPos(135);
	            
	            brick11.setxPos(10000);
	            brick11.setyPos(10000);
	        }

			//Intersecting with door
			if (event.getEventType() == "inGoalEvent") {
				inGoal = true;
			}


			if (event.getEventType() == "playerCollision") {
				System.out.println("Collision with: ");
				Sprite source = (Sprite) event.getSource();

				Rectangle inter = player.getHitBox().intersection(source.getHitBox());
				Rectangle inter3 = player.getHitBox().intersection(saveState1.getHitBox());
				Rectangle inter4 = player.getHitBox().intersection(saveState2.getHitBox());
				if (!inter.isEmpty()) {

					// intesect from above, then bottom does not touch ground
					// moreover, edge case
					if (inter.getY() + inter.getHeight() >= source.getyPos() && inter.getWidth() >= inter.getHeight() + 5) {
						if (inter.getY() + inter.getHeight() <= source.getyPos() + (source.getHeight() / 2)) {
							player.setyPos(source.getyPos() - player.getHeight());
							player.setOnGround(true);
						} else {
							player.setV(0);
							player.setyPos(source.getyPos() + source.getHeight());
						}
					}
					// intersect from left, hitbox start from left of coin
					else {

						if (inter.getX() == source.getxPos()) {
							player.setxPos(source.getxPos() - player.getWidth()-10);
						}

						// intersect from right, hitbox start from right of coin
						if (inter.getX() + inter.getWidth() == source.getxPos() + source.getWidth()) {
							player.setxPos(source.getxPos() + source.getWidth()+10);
						}
						player.setOnGround(false);
					}
				}
			}

		}
	}