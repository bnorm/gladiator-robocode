package kid.Data.PatternMatching;

public class Symbol extends Pattern implements java.io.Serializable {

    private static final long serialVersionUID = 5693020064032396097L;

    private char PatternSymbol;

    private double HeadingChange;
    private double Velocity;
    private int ArrayPosition;

    public Symbol() {
        this(0.0, 0.0, -1);
    }

    public Symbol(double HeadingChange, double Velocity, int ArrayPosition) {
        PatternSymbol = (char) (((int) ((Velocity + 8) * 15 / 16) << 5) | ((int) ((Math.toRadians(HeadingChange) + TEN_DEGREES) * 31 / TWENTY_DEGREES)));
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

    public char getSymbol() {
        return PatternSymbol;
    }

    public double difference(Pattern p) {
        if (PatternSymbol == p.getSymbol())
            return 0;
        return 1;
    }

    public boolean equals(Object o) {
        if (o instanceof Symbol) {
            Symbol symbol = (Symbol) o;
            if (PatternSymbol == symbol.getSymbol())
                return true;
        } else if (o instanceof Pattern) {
            Pattern p = (Pattern) o;
            return Math.abs(getVelocity() - p.getVelocity()) < 1.0
                    && Math.abs(getHeadingChange() - p.getHeadingChange()) < 2.0;
        }
        return false;
    }

    public boolean didScanThisTurn() {
        return false;
    }

    public int getArrayPosition() {
        return ArrayPosition;
    }

}
