//package edu.virginia.cointest;
//
//import edu.virginia.engine.display.AnimatedSprite;
//import edu.virginia.engine.display.Brick;
//import edu.virginia.engine.display.Coin;
//import edu.virginia.engine.display.DisplayObject;
//import edu.virginia.engine.display.Game;
//import edu.virginia.engine.display.Sprite;
//import edu.virginia.engine.events.*;
//import edu.virginia.engine.tween.*;
//import edu.virginia.engine.util.GameClock;
//import edu.virginia.engine.util.Sound;
//import java.awt.Graphics;
//import java.awt.Graphics2D;
//import java.awt.Rectangle;
//import java.awt.event.KeyEvent;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//public class tween_main extends Game implements IEventListener
//{
//
//    /* Create a sprite object for our game. We'll use Sun */
//    AnimatedSprite Mario = new AnimatedSprite("Mario");
////    Sprite Mario = new Sprite("Mario","Mario.png");
//    Brick brick = new Brick("Brick","Brick.png");
//    Brick brick2 = new Brick("Brick","Brick.png");
//    Coin coin = new Coin("Coin","Coin.png");
//
//    Tween coinTween = new Tween(coin);
//    QuestManager manager = new QuestManager();
//    private boolean condition=false;
//    GameClock gameTimer;
//    int time;
//    TweenJuggler juggler = new TweenJuggler();
//
//    /**
//     * Constructor. See constructor in Game.java for details on the parameters given
//     * */
//    public tween_main() {
//        super("CoinGametem", 1200, 800);
//
//        List<String> list = new ArrayList<String>();
//        list.add("mario1.png");
//        list.add("mario2.png");
//        Mario = new AnimatedSprite("Mario",list);
//        HashMap<String, int[]> animations = new HashMap<String, int[]>();
//        int[] num = new int[2];  num[0] = 0; num[1] = 1;
//        animations.put("run", num);
//        Mario.setAnimations(animations);
//
//
//        Sound bgm = new Sound("cooking.wav");
//        bgm.loop();
//
//
//
//        brick.setxPos(300);
//        brick.setyPos(600);
////        coin.setScaleX(0.5);
////        coin.setScaleY(0.5);
//
//        brick2.setxPos(650);
//        brick2.setyPos(400);
//
//        coin.setxPos(950);
//        coin.setyPos(100);
//
//
//        Mario.setxPos(20);
//        Mario.setyPos(640);
//
//
//        TweenTransitions transit = new TweenTransitions();
//        Tween marioTween = new Tween(Mario, transit);
//
//        marioTween.animate(TweenableParams.alpha, 0, 1, 1000);
//        marioTween.animate(TweenableParams.yPos, 300, 640, 1000);
//
//        juggler.add(marioTween);
//
//        coin.addEventListener(this,  "CoinPickedUp");
//        brick.addEventListener(this,  "collide");
//        brick2.addEventListener(this, "collide2");
//
//
//
//        if (gameTimer == null) {
//            gameTimer = new GameClock();
//        }
//
//    }
//
//    /**
//     * Engine will automatically call this update method once per frame and pass to us
//     * the set of keys (as strings) that are currently being pressed down
//     * */
//    @Override
//    public void update(ArrayList<String> pressedKeys){
//
//        if(Mario != null) {
//            //update y position accordingly
//            Mario.setyPos(Mario.getyPos()+Mario.getV());
//
//
//            //determine if on ground
//            if(Mario.getyPos()>=650) {
//                Mario.setOnGround(true);
//            }
//            //if on ground, no need to udpate v
//            if(Mario.isOnGround()) {
//                Mario.setV(0);
//            }
//            //if not, updaet v
//            else {
//                Mario.setV(Mario.getV()+Mario.getG());
//            }
//
//            //boundary checking
//            if(Mario.getxPos()<0) Mario.setxPos(0);
//            if(Mario.getxPos()>1200) Mario.setxPos(1200);
//            if(Mario.getyPos()<0) Mario.setyPos(0);
//            if(Mario.getyPos()>800) Mario.setyPos(800);
//
//
//
//            Mario.update(pressedKeys);
//
//
//            //Auto Hitbox for coin
//            if(Mario.getHitBox().intersects(coin.getHitBox())) {
//                   System.out.println("Hit!!");
//                   PickedUpEvent event = new PickedUpEvent();
//                   coin.dispatchEvent(event);
//             }
//
//            if(Mario.getHitBox().intersects(brick.getHitBox())) {
//                   Event event = new Event("collide");
//                   brick.dispatchEvent(event);
//             }
//
//            if(Mario.getHitBox().intersects(brick2.getHitBox())) {
//
//                  Event event = new Event("collide2");
//                  brick2.dispatchEvent(event);
//             }
//
//            juggler.getInstance().nextFrame();
//
//        }
//    }
//
//    /**
//     * Engine automatically invokes draw() every frame as well. If we want to make sure Sun gets drawn to
//     * the screen, we need to make sure to override this method and call Sun's draw method.
//     * */
//    @Override
//    public void draw(Graphics g){
//
//        if(Mario != null) {
//            brick.draw(g);
//            brick2.draw(g);
//            coin.draw(g);
//            Mario.draw(g);
//        }
//
//    }
//
//    /**
//     * Quick main class that simply creates an instance of our game and starts the timer
//     * that calls update() and draw() every frame
//     * */
//    public static void main(String[] args) {
//        tween_main game = new tween_main();
//        game.start();
//    }
//
//    @Override
//    public void handleEvent(Event event)
//    {
//        if(event.getEventType()=="CoinPickedUp") {
//            Mario.setAlpha(0);
//            System.out.println("Quest is completed!");
////            coin.setAlpha(0);
//
//            coinTween.animate(TweenableParams.xPos, 950, 420, 1500);
//            coinTween.animate(TweenableParams.yPos, 100, 230, 1500);
//            coinTween.animate(TweenableParams.scaleX, 1, 3, 1500);
//            coinTween.animate(TweenableParams.scaleY, 1, 3, 1500);
//            coinTween.addEventListener(this, TweenEvent.TWEEN_COMPLETE_EVENT);
//
//            juggler.add(coinTween);
//        }
//
//        if(event.getEventType()==TweenEvent.TWEEN_COMPLETE_EVENT) {
//           coinTween.removeEventListener(this, TweenEvent.TWEEN_COMPLETE_EVENT);
//           TweenTransitions transit = new TweenTransitions();
//           Tween fadeTween = new Tween(coin,transit);
//           fadeTween.animate(TweenableParams.alpha,1,0,2000);
//           fadeTween.animate(TweenableParams.rotation,1,30,2000);
//           juggler.add(fadeTween);
//        }
//
//
//        if(event.getEventType()=="collide") {
//            System.out.println("Collision!");
//
//            Rectangle inter = Mario.getHitBox().intersection(brick.getHitBox());
//
//
//
//            if(!inter.isEmpty()) {
//
//                //intesect from above, then bottom does not touch ground
//                //moreover, edge case
//                if(inter.getY()+inter.getHeight()>brick.getyPos()
//                    && inter.getWidth()>=inter.getHeight()+5) {
//                        Mario.setOnGround(true);
//                }
//
//                //intersect from left, hitbox start from left of coin
//                else {
//
//                    if(inter.getX()==brick.getxPos()) {
//                        Mario.setxPos(brick.getxPos()-Mario.getWidth());
//                    }
//
//                    //intersect from right, hitbox start from right of coin
//                    if(inter.getX()+inter.getWidth()==brick.getxPos()+brick.getWidth()) {
//                        Mario.setxPos(brick.getxPos()+brick.getWidth());
//                    }
//                    Mario.setOnGround(false);
//                }
//
//
//            }
//
//        }
//
//
//        if(event.getEventType()=="collide2") {
//            System.out.println("Collision!");
//
//            Rectangle inter = Mario.getHitBox().intersection(brick2.getHitBox());
//
//            if(!inter.isEmpty()) {
//
//                //intesect from above, then bottom does not touch ground
//                //moreover, edge case
//                if(inter.getY()+inter.getHeight()>=brick2.getyPos()
//                    && inter.getWidth()>=inter.getHeight()+5) {
//                    if(inter.getY()+inter.getHeight()<=brick2.getyPos()+(brick2.getHeight()/2)) {
//                        Mario.setOnGround(true);
//                    } else {
//                        Mario.setV(0);;
//                        Mario.setyPos(brick2.getyPos()+brick2.getHeight());
//                    }
//                }
//
//                //intersect from left, hitbox start from left of coin
//                else {
//
//                    if(inter.getX()==brick2.getxPos()) {
//                        Mario.setxPos(brick2.getxPos()-Mario.getWidth());
//                    }
//
//                    //intersect from right, hitbox start from right of coin
//                    if(inter.getX()+inter.getWidth()==brick2.getxPos()+brick2.getWidth()) {
//                        Mario.setxPos(brick2.getxPos()+brick2.getWidth());
//                    }
//                    Mario.setOnGround(false);
//                }
//
//
//            }
//
//        }
//
//
//
//
//
//    }
//}