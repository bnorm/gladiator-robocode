package kid.Data.PatternMatching;

public class LatVelPattern implements java.io.Serializable {

    public static final LatVelPattern NullPattern = new LatVelPattern();

    private static final long serialVersionUID = 657356682250083911L;

    private double LatVel;
    private char Symbol;

    private int Index;
    private LatVelPattern NextPattern = null;
    private LatVelPattern PreviousPattern = null;
    private LatVelPattern NextIndexPattern = null;
    private LatVelPattern PreviousIndexPattern = null;
    
    public LatVelPattern(int index) {
        this(9, index);
    }

    public LatVelPattern() {
        this(9, -2);
    }

    public LatVelPattern(double LatVel, int Index) {
        this.LatVel = LatVel;
        this.Index = Index;
        this.Symbol = (char) (int) ((LatVel + 8) * 127 / 16);
    }

    public int getIndex() {
        return Index;
    }
    
    public boolean equals(Object o) {
        if (o instanceof LatVelPattern) {
            LatVelPattern p = (LatVelPattern) o;
            return p.getSymbol() == Symbol;
        }
        return false;
    }

    public double getLatVel() {
        return LatVel;
    }

    public char getSymbol() {
        return Symbol;
    }

    public LatVelPattern getNext() {
        return NextPattern;
    }

    public LatVelPattern getPrevious() {
        return PreviousPattern;
    }

    public LatVelPattern getNextIndex() {
        return NextIndexPattern;
    }

    public LatVelPattern getPreviousIndex() {
        return PreviousIndexPattern;
    }

    public void setNext(LatVelPattern Next) {
        NextPattern = Next;
    }

    public void setPrevious(LatVelPattern Previous) {
        PreviousPattern = Previous;
    }

    public void setNextIndexPattern(LatVelPattern NextIndex) {
        NextIndexPattern = NextIndex;
    }

    public void setPreviousIndexPattern(LatVelPattern PreviousIndex) {
        PreviousIndexPattern = PreviousIndex;
    }

    public void clear() {
        NextPattern = null;
        PreviousPattern = null;
        NextIndexPattern = null;
        PreviousIndexPattern = null;
    }

}
