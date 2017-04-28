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

public class BetaLVL04 extends Game implements IEventListener {
	//Size of our Game
			static int MAXHEIGHT = 800;
			static int MAXWIDTH = 1200;
			
			// Checking out how to use the level switcher
			static Beta game;
			int eFrames;
			Sound bgm;

			
			Sprite twoInfo = new Sprite("twoInfo","twoInfo.png");
			// Player sprite and save state variables
			AnimatedSprite player = new AnimatedSprite("player");
			Sprite saveState1 = new Sprite("saveState1", "saved1.png");
			boolean save1 = false;
			Sprite saveState2 = new Sprite("saveState2", "saved2.png");
			boolean save2 = false;
			int saveTracker = 1;
			int sFrames = 0;

			// Button sprites and variables
			Sprite button = new Sprite("button", "button.png");
			boolean ButtonPressed = false;
			// Platform sprites and variables
		    Sprite gate = new Sprite("gate", "gate.png");

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

			public BetaLVL04() {
				super("BetaLVL04", MAXWIDTH, MAXHEIGHT);
				
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

				gate.setxPos(650);
				gate.setyPos(470);
				
				button.setxPos(400);
				button.setyPos(740);
				
				goal.setxPos(MAXWIDTH-goal.getWidth()-200);
				goal.setyPos(250);
				
				twoInfo.setxPos(MAXWIDTH-goal.getWidth()-400);
				twoInfo.setyPos(400);
				twoInfo.setAlpha(0f);
				
				// Player tweens
				TweenTransitions transit = new TweenTransitions();
				Tween marioTween = new Tween(player, transit);
				marioTween.animate(TweenableParams.alpha, 0, 1, 1000);
				marioTween.animate(TweenableParams.yPos, 300, 670, 1000);
				juggler.add(marioTween);

				// Event registering
				saveState1.addEventListener(this, "playerCollision");
				saveState2.addEventListener(this, "playerCollision");
				gate.addEventListener(this, "playerCollision");
				button.addEventListener(this, "buttonPressed");
				goal.addEventListener(this, "inGoalEvent");

				twoInfo.addEventListener(this, "infoShow");


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
				inGoal = false;
				ButtonPressed = false;
				button.setDisplayImage("button.png");
				button.setyPos(740);

				//Door logic?
				if (player.getHitBox().intersects(goal.getHitBox())) {			
					Event event = new Event("inGoalEvent", goal);
					goal.dispatchEvent(event);
				}
				
				if(twoInfo.getAlpha()>.05){
					twoInfo.setAlpha(twoInfo.getAlpha()-.05);

			}
				else{
					twoInfo.setAlpha(0f);

				}
				
				
//				Rectangle infoRectBox = new Rectangle((int)spaceInfo.getxPos(), 
//				(int)spaceInfo.getyPos(), (int)spaceInfo.getWidth(), 
//				(int)spaceInfo.getHeight()+500);
				if (player.getHitBox().intersects((int)twoInfo.getxPos()-50, 
						(int)twoInfo.getyPos(), (int)twoInfo.getWidth()+150, 
						(int)twoInfo.getHeight()+300)) {			
					Event event = new Event("infoShow", twoInfo);
					twoInfo.dispatchEvent(event);
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
						game.setLevelComplete(4);
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
							marioTween.animate(TweenableParams.yPos, 300, 670, 1000);

							juggler.add(marioTween);
						}
					}

					// A ghetto way of making sure this s key if statement is called at
					// max every 10 frames
					sFrames = 10;
				}

				//hitbox logic
				if (player != null) {

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

					if (player.getHitBox().intersects(gate.getHitBox())) {
						Event event = new Event("playerCollision", gate);
						gate.dispatchEvent(event);
					}
					
					if (player.getHitBox().intersects(button.getHitBox())) {
						Event event = new Event("ButtonPressed", button);
						button.dispatchEvent(event);
					}
					
					if (saveState1.getHitBox().intersects(button.getHitBox())) {
						Event event = new Event("ButtonPressed", button);
						button.dispatchEvent(event);
					}
					
					if (saveState2.getHitBox().intersects(button.getHitBox())) {
						Event event = new Event("ButtonPressed", button);
						button.dispatchEvent(event);
					}

					 if(ButtonPressed == false){
				        	if(gate.getyPos() < 465){
				            	gate.setyPos(gate.getyPos()+gate.getV());
				            	gate.setV((gate.getG()+gate.getV())/1);
				        	}
				        }
				    
					juggler.getInstance().nextFrame();

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
					button.draw(g);
					gate.draw(g);
					twoInfo.draw(g);
					player.draw(g);
					//brick.draw(g);
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
//				game = new Beta();
//				game.start();

			}

			// Where all our events are for right now

			public void handleEvent(Event event) {

				// Objective event
				if (event.getEventType() == "CoinPickedUp") {
					player.setAlpha(0);
					System.out.println("Quest is completed!");

				}

				//Intersecting with door
				if (event.getEventType() == "inGoalEvent") {
					inGoal = true;
				}
				
				//Intersecting with door1
				if (event.getEventType() == "infoShow") {
					System.out.println("infoShow");
					if(twoInfo.getAlpha()<.9){
						twoInfo.setAlpha(twoInfo.getAlpha()+.10);
					}

				}

				// Button pressed event
				if (event.getEventType() == "ButtonPressed") {
					System.out.println("Button is being pressed");
			        button.setDisplayImage("button_pressed.png");
			            //Set position of the pressed button sprite a little bit lower so that it looks better
			        button.setyPos(760);
			            
			            // Logic for getting gate to raise
			        ButtonPressed = true;
			           if(gate.getyPos()>220){
			            gate.setyPos((gate.getyPos()-5));
			           }
			           gate.setV(0);

				}

				if (event.getEventType() == "playerCollision") {
					System.out.println("Collision with: ");
					Sprite source = (Sprite) event.getSource();

					Rectangle inter = player.getHitBox().intersection(source.getHitBox());
					Rectangle inter3 = player.getHitBox().intersection(saveState1.getHitBox());
					Rectangle inter4 = player.getHitBox().intersection(saveState2.getHitBox());
					if (!inter.isEmpty()) {

						// intersect from above, then bottom does not touch ground
						// moreover, edge case
						if (inter.getY() + inter.getHeight() >= source.getyPos() && inter.getWidth() >= inter.getHeight() + 5) {
							if (inter.getY() + inter.getHeight() <= source.getyPos() + (source.getHeight() / 2)) {
								player.setyPos(source.getyPos() - player.getHeight());
								player.setOnGround(true);
							} else {
								player.setV(0);
								;
								player.setyPos(source.getyPos() + source.getHeight());
							}
						}
						// intersect from left, hitbox start from left of coin
						else {

							if (inter.getX() == source.getxPos()) {
								player.setxPos(source.getxPos() - player.getWidth());
							}

							// intersect from right, hitbox start from right of coin
							if (inter.getX() + inter.getWidth() == source.getxPos() + source.getWidth()) {
								player.setxPos(source.getxPos() + source.getWidth());
							}
							player.setOnGround(false);
						}
					}
				}

			}
	}