package kid.Data.Gravity;

/**A simple class describing a point of gravity*/
public class GravityPoint {
    /**The x coordinate of the point*/
    public double x;
    /**The y coordinate of the point*/
    public double y;
    /**The attractiveness of the point(negative will repel)*/
    public double strength;
    /**The time at which the point was created*/
    public long ctime;
    /**The point's lifetime (a lifetime of 0 never dies)*/
    public long life;

    /**Default constructor initializes all memebers to 0*/
    public GravityPoint() {
        this(0, 0, 0, 0, 0);
    }

    /**
     * Constructs a GravPoint with the specified position,
     * strength, and infinite life.
     */
    public GravityPoint(double x, double y, double strength) {
        this(x, y, strength, 0, 0);
    }

    /**Constructs a GravPoint with the specified attributes*/
    public GravityPoint(double x, double y, double strength, long ctime, long life) {
        this.x = x;
        this.y = y;
        this.strength = strength;
        this.ctime = ctime;
        this.life = life;
    }

    /**
     * Update the point. Returns true if the point needs to be killed.
     * For GravPoint this merely checks if our lifetime has been
     * exceeded, but, since this is called before calculations are
     * made using the point, it could be used to adjust position and
     * strength in subclasses.
     *
     * @param time The current game time
     * @returns true if the point needs to be killed, false otherwise
     */
    public boolean update(long time) {
        return((life != 0) && (ctime + life) < time);
    }

}
