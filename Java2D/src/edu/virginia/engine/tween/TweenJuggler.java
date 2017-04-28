package edu.virginia.engine.tween;

import java.util.ArrayList;

public class TweenJuggler
{
    static ArrayList<Tween> tweenList;
    private static TweenJuggler instance;

    public TweenJuggler() {
        if(instance!=null) {
            System.out.println("There is already a Juggler exists.");
            return;
        }
        instance = this;
        tweenList = new ArrayList<Tween>();
    }

    public static TweenJuggler getInstance() {
        return instance;
    }

    public static void add(Tween tween) {
        tweenList.add(tween);
    }

    //invoked every frame by Game, calls update() on every Tween and cleans up old / complete Tweens
    public void nextFrame() {
        try {
            /* Update all objects on the stage */
            for(Tween t:tweenList) {
                if(t.isComplete()) {
                    tweenList.remove(t);
                } else {
                    t.update();
                }
            }
        } catch (Exception e) {
            System.out.println("Exception in nextFrame of TweenJuggler.");
//            e.printStackTrace();
        }
    }

}
