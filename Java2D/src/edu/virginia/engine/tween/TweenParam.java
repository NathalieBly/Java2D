package edu.virginia.engine.tween;

public class TweenParam
{
    private TweenableParams paramToTween;
    private double startVal;
    private double endVal;
    private double duration;

    public TweenParam(TweenableParams paramToTween, double startVal, double endVal, double duration) {
        this.setParamToTween(paramToTween);
        this.setStartVal(startVal);
        this.setEndVal(endVal);
        this.setDuration(duration);
    }

    public double getStartVal()
    {
        return startVal;
    }

    public void setStartVal(double startVal)
    {
        this.startVal = startVal;
    }

    public double getEndVal()
    {
        return endVal;
    }

    public void setEndVal(double endVal)
    {
        this.endVal = endVal;
    }

    public double getDuration()
    {
        return duration;
    }

    public void setDuration(double duration)
    {
        this.duration = duration;
    }

    public TweenableParams getParamToTween()
    {
        return paramToTween;
    }

    public void setParamToTween(TweenableParams paramToTween)
    {
        this.paramToTween = paramToTween;
    }



}
