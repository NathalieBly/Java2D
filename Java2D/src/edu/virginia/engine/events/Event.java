package edu.virginia.engine.events;

import edu.virginia.engine.display.Sprite;

public class Event
{
    private String eventType;
    private IEventDispatcher source;

    public Event(String eventType) {
        this.eventType=eventType;
    }
    public Event(String eventType, Sprite inSource) {
        this.eventType=eventType;
        source = inSource;
    }
       public String getEventType()
    {
        return eventType;
    }

    public void setEventType(String eventType)
    {
        this.eventType = eventType;
    }

    public IEventDispatcher getSource()
    {
        return source;
    }

    public void setSource(IEventDispatcher source)
    {
        this.source = source;
    }



}
