package edu.virginia.engine.display;

import java.util.ArrayList;
import java.util.HashMap;

import edu.virginia.engine.events.Event;
import edu.virginia.engine.events.IEventDispatcher;
import edu.virginia.engine.events.IEventListener;

/**
 * Nothing in this class (yet) because there is nothing specific to a Sprite yet that a DisplayObject
 * doesn't already do. Leaving it here for convenience later. you will see!
 * */
public class Sprite extends DisplayObjectContainer implements IEventDispatcher {
	private ArrayList<IEventListener> listeners = new ArrayList<IEventListener>();
	public Sprite(String id) {
		super(id);
	}

	public Sprite(String id, String imageFileName) {
		super(id, imageFileName);
	}

	@Override
	public void update(ArrayList<String> pressedKeys) {
		super.update(pressedKeys);
	}

	  public void addEventListener(IEventListener listener, String eventType)
    {
        // TODO Auto-generated method stub
        listeners.add(listener);
    }

   
    public void removeEventListener(IEventListener listener, String eventType)
    {
        // TODO Auto-generated method stub
        listeners.remove(listener);
    }

    
    public void dispatchEvent(Event event)
    {
        // TODO Auto-generated method stub
        System.out.println("Notify All Listeners");
        for(IEventListener listener:listeners) {
            listener.handleEvent(event);
        }
    }

    
    public boolean hasEventListener(IEventListener listener, String eventType)
    {
        // TODO Auto-generated method stub
        return false;
    }

}