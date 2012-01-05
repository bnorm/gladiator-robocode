package kid.Data.PatternMatching;

public class Pattern implements java.io.Serializable {
    
    public static final Pattern NullPattern = new Pattern();

    private static final double TEN_DEGREES = 10;
    private static final double TWENTY_DEGREES = 20;

    private static final long serialVersionUID = 657356682250083911L;

    private double HeadingChange;
    private double Velocity;
    private char Symbol;

    private int Index;
    private Pattern NextPattern = null;
    private Pattern PreviousPattern = null;
    private Pattern NextIndexPattern = null;
    private Pattern PreviousIndexPattern = null;
    
    public Pattern(int index) {
        this(10.0, 8.0, index);
    }
    
    public Pattern() {
        this(10.0, 8.0, -2);
    }

    public Pattern(double HeadingChange, double Velocity, int Index) {
        this.HeadingChange = HeadingChange;
        this.Velocity = Velocity;
        this.Index = Index;
        this.Symbol = (char) (((int) ((Velocity + 8) * 15 / 16) << 7) | ((int) ((HeadingChange + TEN_DEGREES) * 127 / TWENTY_DEGREES)));
    }

    public double difference(Pattern p) {
        return Math.abs(Velocity - p.getVelocity()) + Math.abs(HeadingChange - p.getHeadingChange());
    }

    public boolean equals(Object o) {
        if (o instanceof Pattern) {
            Pattern p = (Pattern) o;
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

    public int getIndex() {
        return Index;
    }

    public Pattern getNext() {
        return NextPattern;
    }

    public Pattern getPrevious() {
        return PreviousPattern;
    }

    public Pattern getNextIndex() {
        return NextIndexPattern;
    }

    public Pattern getPreviousIndex() {
        return PreviousIndexPattern;
    }

    public void setNext(Pattern Next) {
        NextPattern = Next;
    }

    public void setPrevious(Pattern Previous) {
        PreviousPattern = Previous;
    }

    public void setNextIndexPattern(Pattern NextIndex) {
        NextIndexPattern = NextIndex;
    }

    public void setPreviousIndexPattern(Pattern PreviousIndex) {
        PreviousIndexPattern = PreviousIndex;
    }

    public void clear() {
        NextPattern = null;
        PreviousPattern = null;
        NextIndexPattern = null;
        PreviousIndexPattern = null;
    }

}
