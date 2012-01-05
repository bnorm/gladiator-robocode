package kid.Data.PatternMatching;

public abstract class Pattern implements java.io.Serializable {

    public abstract double getHeadingChange();
    public abstract double getVelocity();

    public abstract double difference(Pattern p);
    public abstract boolean didScanThisTurn();

}
