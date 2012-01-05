package kid.Data.PatternMatching;

public class Polar extends Pattern implements java.io.Serializable {

    private static final long serialVersionUID = 657356682250083911L;
    
    private double HeadingChange;
    private double Velocity;
    private int ArrayPosition;

    public Polar() {
        this(0.0, 0.0, -1);
    }

    public Polar(double HeadingChange, double Velocity, int ArrayPosition) {
        this.HeadingChange = HeadingChange;
        this.Velocity = Velocity;
        this.ArrayPosition = ArrayPosition;
    }

    public double getVelocity() {
        return Velocity;
    }

    public double getHeadingChange() {
        return HeadingChange;
    }

    public double difference(Pattern p) {
        return Math.abs(Velocity - p.getVelocity()) + Math.abs(HeadingChange - p.getHeadingChange());
    }

    public boolean didScanThisTurn() {
        return false;
    }

    public boolean equals(Object o) {
        if (o instanceof Pattern) {
            Pattern p = (Pattern) o;
            return Math.abs(getVelocity() - p.getVelocity()) < 1.0 &&
                    Math.abs(getHeadingChange() - p.getHeadingChange()) < 1.0;
        }
        return false;
    }

    public char getSymbol() {
        return (char) (((int) ((Velocity + 8) * 15 / 16) << 5) |
                       ((int) ((Math.toRadians(HeadingChange) + TEN_DEGREES) * 31 / TWENTY_DEGREES)));
    }

    public int getArrayPosition() {
        return ArrayPosition;
    }

}
