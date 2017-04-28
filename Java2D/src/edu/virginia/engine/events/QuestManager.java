package edu.virginia.engine.events;

public class QuestManager implements IEventListener
{

    private int coinCount=0;

    public int getCoinCount()
    {
        return coinCount;
    }

    public void handleEvent(Event event)
    {
        if(event.getEventType()=="CoinPickedUp") {
            System.out.println("Quest is completed!");;
        }


    }
}
