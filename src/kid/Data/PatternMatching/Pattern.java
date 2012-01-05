package kid.Data.PatternMatching;

public abstract class Pattern implements java.io.Serializable {

    protected static final double TEN_DEGREES = 10 * Math.PI / 180;
    protected static final double TWENTY_DEGREES = TEN_DEGREES * 2;

    public abstract double getHeadingChange();

    public abstract double getVelocity();

    public abstract char getSymbol();

    public abstract int getArrayPosition();

    public abstract double difference(Pattern p);

    public abstract boolean didScanThisTurn();

}
