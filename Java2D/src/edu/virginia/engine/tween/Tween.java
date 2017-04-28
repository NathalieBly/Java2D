package edu.virginia.engine.tween;

import edu.virginia.engine.display.DisplayObject;
import edu.virginia.engine.events.Event;
import edu.virginia.engine.events.EventDispatcher;
import edu.virginia.engine.util.GameClock;
import java.util.ArrayList;

public class Tween extends EventDispatcher
{
    private DisplayObject object;
    private boolean isComplete = false;
    private TweenTransitions transition;
    private TweenParam tweenParam;
    private GameClock gameTimer;
    int time;

    ArrayList<TweenParam> list = new ArrayList<TweenParam>();

    //allow multiple animations
    private ArrayList<TweenParam> paramList;


    public Tween(DisplayObject object) {
        this.setObject(object);
        this.setComplete(false);
    }

    public Tween(DisplayObject object, TweenTransitions transition) {
        this.setObject(object);
        this.setTransition(transition);
    }

    public void animate(TweenableParams fieldToAnimate, double startVal, double endVal, double duration) {
        TweenParam param = new TweenParam(fieldToAnimate, startVal, endVal, duration);
        list.add(param);

//        this.setTweenParam(param);
    }

    //invoked once per frame by the TweenJuggler. Updates this tween / DisplayObject
    public void update() {
        //conduct animate to change parameters of object and then update object
        if (gameTimer == null) {
            gameTimer = new GameClock();
        }
        time = (int)gameTimer.getElapsedTime();

        //need to switch tweenParam to apply transition
        for(TweenParam param:list) {
            double percentComplete = time/param.getDuration();
            if(percentComplete<1 && this.getTransition()!=null) {
                double trans = this.getTransition().easeInOut(percentComplete);
                double value = (param.getEndVal()-param.getStartVal())*trans;
                this.setValue(param.getParamToTween(), param.getStartVal()+value);
            } else if(percentComplete<1) {
                double value = (param.getEndVal()-param.getStartVal())*percentComplete;
                this.setValue(param.getParamToTween(), param.getStartVal()+value);
            }
        }

        for(TweenParam param:list) {
            double percentComplete = time/param.getDuration();
            if(percentComplete<1) {
                break;
            }
            this.setComplete(true);
            Event event = new Event(TweenEvent.TWEEN_COMPLETE_EVENT);
            this.dispatchEvent(event);
        }
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setValue(TweenableParams param, double value) {
        switch(param){
            case xPos:
                this.getObject().setxPos(value);
                break;
            case yPos:
                this.getObject().setyPos(value);
                break;
            case scaleX:
                this.getObject().setScaleX(value);
                break;
            case scaleY:
                this.getObject().setScaleY(value);
                break;
            case rotation:
                this.getObject().setRotation(value);
                break;
            case alpha:
                this.getObject().setAlpha(value);
                break;
            default:
                break;
        }
    }

    public DisplayObject getObject()
    {
        return object;
    }

    public void setObject(DisplayObject object)
    {
        this.object = object;
    }

    public void setComplete(boolean isComplete)
    {
        this.isComplete = isComplete;
    }

    public TweenTransitions getTransition()
    {
        return transition;
    }

    public void setTransition(TweenTransitions transition)
    {
        this.transition = transition;
    }

    public TweenParam getTweenParam()
    {
        return tweenParam;
    }

    public void setTweenParam(TweenParam tweenParam)
    {
        this.tweenParam = tweenParam;
    }

    public GameClock getGameTimer()
    {
        return gameTimer;
    }

    public void setGameTimer(GameClock gameTimer)
    {
        this.gameTimer = gameTimer;
    }

}
