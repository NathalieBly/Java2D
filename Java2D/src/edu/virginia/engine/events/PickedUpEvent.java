package edu.virginia.engine.events;

public class PickedUpEvent extends Event
{

    public static String COIN_PICKED_UP = "CoinPickedUp";
    public PickedUpEvent()
    {
        super("CoinPickedUp");
    }

}
