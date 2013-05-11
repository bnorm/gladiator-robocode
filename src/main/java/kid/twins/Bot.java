package kid.twins;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Bot extends Point2D.Double {

   /**
    * Determines if a de-serialized file is compatible with this class.<BR>
    * <BR>
    * Maintainers must change this value if and only if the new version of this class is not compatible with old
    * versions.
    */
   private static final long serialVersionUID = 7769582559562460893L;

   public static final int DISTANCE_SEGMENTS = 4;
   public static final int VELOCITY_CHANGE_TIME_SEGMENTS = 4;
   public static final int VELOCITY_CHANGE_FREQUENCY_SEGMENTS = 4;

   public String NAME;
   public transient double ENERGY;
   public transient double HEADING;
   public transient double VELOCITY;
   public transient long TIME;

   public static final int SEARCH_TIME = 100;
   public long[] VELOCITY_CHANGE_TIMES = new long[SEARCH_TIME];
   public int VCT_INDEX;

   public ArrayList<Wave> ENEMY_WAVES;

}
