package kid.Data.PatternMatching;

public class Polar extends Pattern implements java.io.Serializable {

    private int HeadingChange;
    private int Velocity;

    boolean DidScanThisTurn;

    private static final int bump = 100;

    public Polar() {
        this(0.0, 0.0, false);
    }

    public Polar(double HeadingChange, double Velocity, boolean DidScanThisTurn) {
        this.HeadingChange = (int) (HeadingChange * bump);
        this.Velocity = (int) Velocity;
        this.DidScanThisTurn = DidScanThisTurn;
    }

    public double getVelocity() {
        return Velocity;
    }

    public double getHeadingChange() {
        return (double) HeadingChange / bump;
    }

    public double difference(Pattern p) {
        return Math.abs((int) Velocity - (int) p.getVelocity()) +
                Math.abs((int) HeadingChange - (int) (p.getHeadingChange() * bump));
    }

    public boolean didScanThisTurn() {
        return DidScanThisTurn;
    }

    public boolean equals(Object o) {
        if (o instanceof Pattern) {
            Pattern p = (Pattern) o;
            return Math.abs(getVelocity() - p.getVelocity()) < 1.0 &&
                    Math.abs(getHeadingChange() - p.getHeadingChange()) < 2.0;
        }
        return false;
    }

}
