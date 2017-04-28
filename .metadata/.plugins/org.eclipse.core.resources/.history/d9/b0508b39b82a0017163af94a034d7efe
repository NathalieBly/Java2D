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

public class Beta extends Game implements IEventListener {
	 //Size of our Game
	static int MAXHEIGHT = 800;
	static int MAXWIDTH = 1200;
	
	//Completed levels?
	boolean lvl01Complete = false;
	boolean lvl02Complete = false;
	boolean lvl03Complete = false;
	boolean lvl04Complete = false;
	boolean lvl05Complete = false;
	boolean lvl06Complete = false;

	
	// Checking out how to use the level switcher
	static Beta game;
	int eFrames;
	static BetaLVL01 level01;
	static BetaLVL02 level02;
	static BetaLVL03 level03;
	static BetaLVL04 level04;
	static BetaLVL05 level05;
	static BetaLVL06 level06;



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
	Sprite button2 = new Sprite("button2", "button.png");
	boolean ButtonPressed2 = false;

	// Platform sprites and variables
	Brick brick = new Brick("Brick","Brick.png");


    Brick cageDoor4 = new Brick("CageDoor4","cage.png");
    Brick cageDoor5 = new Brick("CageDoor5","cage.png");


	// Hazards sprites and variables
    Brick angryBrick = new Brick("AngryBrick","AngryBrick.png");
    Brick angryBrick2 = new Brick("AngryBrick2","AngryBrick.png");
    Brick angryBrick3 = new Brick("AngryBrick3","AngryBrick.png");

	// Objective sprites and variables
	Sprite door1 = new Sprite("door1","door1.png");
	boolean inDoor1 = false;
	Sprite door2 = new Sprite("door2","door2.png");
	boolean inDoor2 = false;
	Sprite door3 = new Sprite("door3","door3.png");
	boolean inDoor3 = false;
	Sprite door4 = new Sprite("door4","door4.png");
	boolean inDoor4 = false;
	Sprite door5 = new Sprite("door5","door5.png");
	boolean inDoor5 = false;
	Sprite door6 = new Sprite("door6","door6.png");
	boolean inDoor6 = false;
	
	// Holds the starting positions of all our moveable sprites, so that they
	// can be reset when we "save/reload"
	HashMap<Sprite, Point> startingPositions = new HashMap<Sprite, Point>();

	// Managers and singletons
	QuestManager manager = new QuestManager();
	GameClock gameTimer;
	TweenJuggler juggler = new TweenJuggler();
	
	// BGM
	Sound bgm;
	
	public void setLevelComplete(int inLevel){
		switch(inLevel){
		case 1:
			lvl01Complete = true;
			break;
		case 2:
			lvl01Complete = true;
			lvl02Complete = true;
			break;
		case 3:
			lvl01Complete = true;
			lvl02Complete = true;
			lvl03Complete = true;
			break;
		case 4:
			lvl01Complete = true;
			lvl02Complete = true;
			lvl03Complete = true;
			lvl04Complete = true;
			break;
		case 5:
			lvl01Complete = true;
			lvl02Complete = true;
			lvl03Complete = true;
			lvl04Complete = true;
			lvl05Complete = true;
			break;
		}
		
	}
	
	public void reset(){
		save1 = false;
		save2 = false;
		//Quickly put our old saves states in the corner so that they do not get in the way
		saveState1.setxPos(0);
		saveState1.setyPos(0);
		saveState2.setxPos(0);
		saveState2.setyPos(0);
		
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
	}
	

	public Beta() {
		super("Beta", MAXWIDTH, MAXHEIGHT);
		
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

		// 
		if(bgm!=null) {
			bgm.stop();
		} else {
			bgm = new Sound("cooking.wav");
			bgm.loop();
		}
		
		// Sprite positioning (SHOULD PROBABLY RE WORK THIS AT SOME POINT)
		player.setxPos(20);
		player.setyPos(640);
		startingPositions.put(player, new Point((int) player.getxPos(), (int) player.getyPos()));
		
		
		button.setxPos(80);
		button.setyPos(740);

		door1.setxPos(MAXWIDTH/2-door1.getWidth()-200);
		door1.setyPos(625);
		
		brick.setxPos(MAXWIDTH/2-door2.getWidth());
		brick.setyPos(650);
		
		door2.setxPos(MAXWIDTH/2-door3.getWidth()+200);
		door2.setyPos(625);
		
		angryBrick.setxPos(MAXWIDTH/2-door2.getWidth()+300);
		angryBrick.setyPos(650);
		
		door3.setxPos(MAXWIDTH/2-door3.getWidth()+400);
		door3.setyPos(625);
		
		door4.setxPos(MAXWIDTH/2-door4.getWidth()-200);
		door4.setyPos(275);
		
		cageDoor4.setxPos(MAXWIDTH/2-cageDoor4.getWidth()-140);
		cageDoor4.setyPos(250);
		
		door5.setxPos(MAXWIDTH/2-door5.getWidth()+100);
		door5.setyPos(150);
		
		cageDoor5.setxPos(MAXWIDTH/2-cageDoor5.getWidth()+160);
		cageDoor5.setyPos(125);
		
		button2.setxPos(MAXWIDTH-50);
		button2.setyPos(740);
		
		door6.setxPos(MAXWIDTH-door6.getWidth()-100);
		door6.setyPos(100);
		
		angryBrick2.setxPos(MAXWIDTH-door6.getWidth()-190);
		angryBrick2.setyPos(10);
		
		angryBrick3.setxPos(MAXWIDTH-door6.getWidth()-25);
		angryBrick3.setyPos(MAXHEIGHT/2);
		
		// Player tweens
		TweenTransitions transit = new TweenTransitions();
		Tween marioTween = new Tween(player, transit);
		marioTween.animate(TweenableParams.alpha, 0, 1, 1000);
		marioTween.animate(TweenableParams.yPos, 300, 670, 1000);
		juggler.add(marioTween);

		// Event registering
		saveState1.addEventListener(this, "playerCollision");
		saveState2.addEventListener(this, "playerCollision");
		
		brick.addEventListener(this, "playerCollision");
		
		angryBrick.addEventListener(this, "hazardCollision");
		angryBrick2.addEventListener(this, "hazardCollision");
		angryBrick3.addEventListener(this, "hazardCollision");
		
		cageDoor4.addEventListener(this, "playerCollision");
		cageDoor5.addEventListener(this, "playerCollision");
		button.addEventListener(this, "ButtonPressed");
		button2.addEventListener(this, "ButtonPressed");



		door1.addEventListener(this, "inDoor1Event");
		door2.addEventListener(this, "inDoor2Event");
		door3.addEventListener(this, "inDoor3Event");
		door4.addEventListener(this, "inDoor4Event");
		door5.addEventListener(this, "inDoor5Event");
		door6.addEventListener(this, "inDoor6Event");


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
		inDoor1 = false;
		inDoor2 = false;
		inDoor3 = false;
		inDoor4 = false;
		inDoor5 = false;
		inDoor6 = false;
		ButtonPressed = false;
		ButtonPressed2 = false;

		
		button.setDisplayImage("button.png");
		button.setyPos(740);
		
		button2.setDisplayImage("button.png");
		button2.setyPos(740);
		
		if(lvl05Complete){
            // Show falling brick 2
            angryBrick2.setyPos(angryBrick2.getyPos()+angryBrick2.getV());
            angryBrick2.setV((angryBrick2.getG()+angryBrick2.getV()));
            
            if(angryBrick2.getyPos() > MAXHEIGHT){
            	angryBrick2.setyPos(50);
            	angryBrick2.setV(0);
            	
            }
            
            
            
            // Show falling brick 3
            angryBrick3.setyPos(angryBrick3.getyPos()+angryBrick3.getV());
            angryBrick3.setV((angryBrick3.getG()+angryBrick3.getV()));
            
            if(angryBrick3.getyPos() > MAXHEIGHT){
            	angryBrick3.setyPos(20);
            	angryBrick3.setV(0);
            	
            }
		}


		//Door logic
		if (player.getHitBox().intersects(door1.getHitBox())) {			
			Event event = new Event("inDoor1Event", door1);
			door1.dispatchEvent(event);
		}
		if (player.getHitBox().intersects(door2.getHitBox())) {			
			Event event = new Event("inDoor2Event", door2);
			door2.dispatchEvent(event);
		}
		if (player.getHitBox().intersects(door3.getHitBox())) {			
			Event event = new Event("inDoor3Event", door3);
			door3.dispatchEvent(event);
		}
		if (player.getHitBox().intersects(door4.getHitBox())) {			
			Event event = new Event("inDoor4Event", door4);
			door4.dispatchEvent(event);
		}
		if (player.getHitBox().intersects(door5.getHitBox())) {			
			Event event = new Event("inDoor5Event", door5);
			door5.dispatchEvent(event);
		}
		if (player.getHitBox().intersects(door6.getHitBox())) {			
			Event event = new Event("inDoor6Event", door6);
			door6.dispatchEvent(event);
		}
		
		//Building in what we would do if we die, debugging with r key
		if (pressedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_R))){
			save1 = false;
			save2 = false;
			
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
			
		}
		
		///Key logic
		if (pressedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_E)) && eFrames == 0) {
			// A ghetto way of making sure this s key if statement is called at
			// max every 10 frames
			eFrames = 20;
			
			
			
			//Check if we are intersecting with door1
			if(inDoor1 && eFrames == 20){
				bgm.stop();
				level01 = new BetaLVL01();
				level01.start();	
				this.exitGame();
			}
			
			//Check if we are intersecting with door2
			if(inDoor2 && eFrames == 20){
				bgm.stop();
				level02 = new BetaLVL02();
				level02.start();			
				this.exitGame();
			}
			
			//Check if we are intersecting with door3
			if(inDoor3 && eFrames == 20){
				bgm.stop();
				level03 = new BetaLVL03();
				level03.start();			
				this.exitGame();
			}
			
			//Check if we are intersecting with door4
			if(inDoor4 && eFrames == 20){
				bgm.stop();
				level04 = new BetaLVL04();
				level04.start();			
				this.exitGame();
			}
			//Check if we are intersecting with door5
			if(inDoor5 && eFrames == 20){
				bgm.stop();
				level05 = new BetaLVL05();
				level05.start();			
				this.exitGame();
			}
			
			//Check if we are intersecting with door6
			if(inDoor6 && eFrames == 20){
				bgm.stop();
				level06 = new BetaLVL06();
				level06.start();			
				this.exitGame();
			}


			

		}
		
		//Button
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
		
		if (player.getHitBox().intersects(button2.getHitBox())) {
			Event event = new Event("ButtonPressed2", button2);
			button2.dispatchEvent(event);
		}
		
		if (saveState1.getHitBox().intersects(button2.getHitBox())) {
			Event event = new Event("ButtonPressed2", button2);
			button2.dispatchEvent(event);
		}
		
		if (saveState2.getHitBox().intersects(button2.getHitBox())) {
			Event event = new Event("ButtonPressed2", button2);
			button2.dispatchEvent(event);
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


			//Object hitboxes
				//Also want to check that we beat lvl01
			if (player.getHitBox().intersects(brick.getHitBox()) && lvl01Complete) {
				Event event = new Event("playerCollision", brick);
				brick.dispatchEvent(event);
			}
			if (player.getHitBox().intersects(angryBrick.getHitBox()) && lvl02Complete) {
				Event event = new Event("hazardCollision", angryBrick);
				angryBrick.dispatchEvent(event);
			}
			if (player.getHitBox().intersects(angryBrick2.getHitBox()) && lvl05Complete) {
				Event event = new Event("hazardCollision", angryBrick2);
				angryBrick2.dispatchEvent(event);
			}
			if (player.getHitBox().intersects(angryBrick3.getHitBox()) && lvl05Complete) {
				Event event = new Event("hazardCollision", angryBrick3);
				angryBrick3.dispatchEvent(event);
			}
			if (player.getHitBox().intersects(cageDoor4.getHitBox()) && lvl03Complete && ButtonPressed == false) {
				Event event = new Event("playerCollision", cageDoor4);
				cageDoor4.dispatchEvent(event);
			}
			if (player.getHitBox().intersects(cageDoor5.getHitBox()) && lvl04Complete && ButtonPressed2 == false) {
				Event event = new Event("playerCollision", cageDoor5);
				cageDoor5.dispatchEvent(event);
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
		
		
		if(lvl01Complete && door2 != null){
			door2.draw(g);
			brick.draw(g);
		}
		
		if(lvl02Complete && door3 != null){
			door3.draw(g);
			angryBrick.draw(g);

		}
		if(lvl03Complete && door4 != null && ButtonPressed == false){
			door4.draw(g);
			cageDoor4.draw(g);
			button.draw(g);
		}
		if(lvl03Complete && door4 != null && ButtonPressed){
			door4.draw(g);
			//cageDoor4.draw(g);
			button.draw(g);
		}
		if(lvl04Complete && door5 != null && ButtonPressed2 == false){
			door5.draw(g);
			cageDoor5.draw(g);
			button2.draw(g);
		}
		if(lvl04Complete && door5 != null && ButtonPressed2){
			door5.draw(g);
			//cageDoor5.draw(g);
			button2.draw(g);
		}
		if(lvl05Complete && door6 != null){
			door6.draw(g);
			angryBrick2.draw(g);
			angryBrick3.draw(g);

		}


		if (player != null) {
			door1.draw(g);
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
		game = new Beta();
		game.start();

	}

	// Where all our events are for right now

	public void handleEvent(Event event) {

		// Objective event
		if (event.getEventType() == "CoinPickedUp") {
			player.setAlpha(0);
			System.out.println("Quest is completed!");

		}

		//Intersecting with door1
		if (event.getEventType() == "inDoor1Event") {
			System.out.println("inDoor1Event");
			inDoor1 = true;

		}
		
		//Intersecting with door2
		if (event.getEventType() == "inDoor2Event") {
			System.out.println("inDoor2Event");
			inDoor2 = true;

		}
		
		//Intersecting with door3
		if (event.getEventType() == "inDoor3Event") {
			System.out.println("inDoor3Event");
			inDoor3 = true;

		}
		//Intersecting with door4
		if (event.getEventType() == "inDoor4Event") {
			System.out.println("inDoor4Event");
			inDoor4 = true;

		}
		//Intersecting with door5
		if (event.getEventType() == "inDoor5Event") {
			System.out.println("inDoor5Event");
			inDoor5 = true;

		}
		//Intersecting with door6
		if (event.getEventType() == "inDoor6Event") {
			System.out.println("inDoor6Event");
			inDoor6 = true;

		}

		// Button pressed event
		if (event.getEventType() == "ButtonPressed") {
			System.out.println("Button is being pressed");
	        button.setDisplayImage("button_pressed.png");
            //Set position of the pressed button sprite a little bit lower so that it looks better
	        button.setyPos(760);
	        ButtonPressed = true;

		}
		
		// Button pressed 2 event
		if (event.getEventType() == "ButtonPressed2") {
			System.out.println("Button is being pressed2");
	        button2.setDisplayImage("button_pressed.png");
            //Set position of the pressed button sprite a little bit lower so that it looks better
	        button2.setyPos(760);
	        ButtonPressed2 = true;

		}
		
		//Reset event
		if (event.getEventType() == "hazardCollision") {
			reset();

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