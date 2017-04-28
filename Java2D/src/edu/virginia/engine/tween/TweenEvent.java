package edu.virginia.engine.tween;

import edu.virginia.engine.events.Event;

public class TweenEvent extends Event
{

    private Tween tween;
    public final static String TWEEN_COMPLETE_EVENT = "tweencomplete";


    public TweenEvent(String eventType, Tween tween)
    {
        super(eventType);
        this.setTween(tween);
        // TODO Auto-generated constructor stub
    }


    public Tween getTween()
    {
        return tween;
    }


    public void setTween(Tween tween)
    {
        this.tween = tween;
    }

}
