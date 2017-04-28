package edu.virginia.engine.tween;

public class TweenTransitions
{
    public double applyTransition(double percentDone) {
        return percentDone;
    }

    //applies a specific transition function, can have more of these for each
    //transition your engine supports. I will only list one here.
    public static double easeInOut(double percentDone) {
        if(percentDone<=1/2) {
            return percentDone*percentDone;
        } else {
            return percentDone*percentDone*percentDone;
        }

    }
}
