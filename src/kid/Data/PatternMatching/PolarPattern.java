package kid.Data.PatternMatching;

public class PolarPattern implements java.io.Serializable {

    public static final PolarPattern NullPattern = new PolarPattern();

    private static final double TEN_DEGREES = 10;
    private static final double TWENTY_DEGREES = 20;

    private static final long serialVersionUID = 657356682250083911L;

    private double HeadingChange;
    private double Velocity;
    private char Symbol;

    private int Index;
    private PolarPattern NextPattern = null;
    private PolarPattern PreviousPattern = null;
    private PolarPattern NextIndexPattern = null;
    private PolarPattern PreviousIndexPattern = null;
    
    public PolarPattern(int index) {
        this(10.0, 8.0, index);
    }

    public PolarPattern() {
        this(10.0, 8.0, -2);
    }

    public PolarPattern(double HeadingChange, double Velocity, int Index) {
        this.HeadingChange = HeadingChange;
        this.Velocity = Velocity;
        this.Index = Index;
        this.Symbol = (char) (((int) ((Velocity + 8) * 15 / 16) << 7) | ((int) ((HeadingChange + TEN_DEGREES) * 127 / TWENTY_DEGREES)));
    }

    public int getIndex() {
        return Index;
    }

    public boolean equals(Object o) {
        if (o instanceof PolarPattern) {
            PolarPattern p = (PolarPattern) o;
            return p.getSymbol() == Symbol;
        }
        return false;
    }

    public double getHeadingChange() {
        return HeadingChange;
    }

    public double getVelocity() {
        return Velocity;
    }

    public char getSymbol() {
        return Symbol;
    }

    public PolarPattern getNext() {
        return NextPattern;
    }

    public PolarPattern getPrevious() {
        return PreviousPattern;
    }

    public PolarPattern getNextIndex() {
        return NextIndexPattern;
    }

    public PolarPattern getPreviousIndex() {
        return PreviousIndexPattern;
    }

    public void setNext(PolarPattern Next) {
        NextPattern = Next;
    }

    public void setPrevious(PolarPattern Previous) {
        PreviousPattern = Previous;
    }

    public void setNextIndexPattern(PolarPattern NextIndex) {
        NextIndexPattern = NextIndex;
    }

    public void setPreviousIndexPattern(PolarPattern PreviousIndex) {
        PreviousIndexPattern = PreviousIndex;
    }

    public void clear() {
        NextPattern = null;
        PreviousPattern = null;
        NextIndexPattern = null;
        PreviousIndexPattern = null;
    }

}
