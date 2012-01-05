package kid.Data.PatternMatching;

import kid.Utils;

public class Symbol extends Pattern implements java.io.Serializable {

    private char PatternSymbol;

    private int HeadingChange;
    private int Velocity;

    private boolean DidScanThisTurn;

    private static final int bump = 100;

    public Symbol() {
        this(0.0, 0.0, false);
    }

    public Symbol(double HeadingChange, double Velocity, boolean DidScanThisTurn) {
        PatternSymbol = (char) (Math.abs((int) HeadingChange * bump) | Math.abs((int) Velocity << 8));
        HeadingChange = HeadingChange;
        Velocity = Velocity;
        this.DidScanThisTurn = DidScanThisTurn;
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
        if (p instanceof Symbol) {
            Symbol symbol = (Symbol) p;
            if (PatternSymbol == symbol.getSymbol())
                return 0;
            return 1;
        }
        char symbol = (char) (Math.abs((int) p.getHeadingChange() * bump) | Math.abs((int) p.getVelocity() << 8));
        if (symbol == PatternSymbol)
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
            return Math.abs(getVelocity() - p.getVelocity()) < 1.0 &&
                    Math.abs(getHeadingChange() - p.getHeadingChange()) < 2.0;
        }
        return false;
    }

    public boolean didScanThisTurn() {
        return DidScanThisTurn;
    }

}
