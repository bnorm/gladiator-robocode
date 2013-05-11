package kid.targeting;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.io.PrintStream;

import kid.data.pattern.FoldedEntry;
import kid.data.pattern.PatternMatcher;
import kid.data.pattern.Polar;
import kid.graphics.Colors;
import kid.info.RobotInfo;
import kid.robot.RobotData;
import kid.utils.Utils;
import robocode.RobocodeFileOutputStream;
import robocode.Robot;
import robocode.Rules;

// TODO documentation: class

public class PatternMatchingTargeting extends Targeting {

   /**
    * Determines if a deserialized file is compatible with this class.<BR>
    * <BR>
    * Maintainers must change this value if and only if the new version of this class is not
    * compatible with old versions.
    */
   private static final long serialVersionUID = 6581499402431094042L;

   public static final int MAX_MATCH_LENGTH = 500;

   private PatternMatcher<Polar> matcher;

   public PatternMatchingTargeting(Robot myRobot, PatternMatcher<Polar> matcher) {
      super(myRobot);
      init(matcher);
   }

   public PatternMatchingTargeting(PatternMatchingTargeting targeting) {
      super(targeting);
      init(targeting.matcher);
   }

   private void init(PatternMatcher<Polar> matcher) {
      this.matcher = matcher;
   }

   // BORED code: start at -1? why?...
   @Override
   public double getAngle(final RobotData target, double firePower) {
      if (target == null || target.isDead())
         return Utils.relative(gunInfo.getHeading() + gunInfo.getHeading());
      if (target.getEnergy() == 0.0)
         return gunInfo.angle(target);
      FoldedEntry<Polar> movement = matcher.getMatch(target.getName());

      firePower = Math.min(robot.getEnergy(), firePower);
      firePower = Utils.limit(Rules.MIN_BULLET_POWER, firePower, Rules.MAX_BULLET_POWER);
      double bulletVelocity = Rules.getBulletSpeed(firePower);

      double MWD = RobotInfo.MIN_WALL_DIST;
      Rectangle2D battleField = new Rectangle2D.Double(MWD, MWD, gunInfo.getBattleFieldWidth() - 2.0D * MWD, gunInfo
            .getBattleFieldHeight()
            - 2.0D * MWD);

      double myX = robot.getX();
      double myY = robot.getY();
      double prodictedX = target.getX();
      double prodictedY = target.getY();
      double enemyHeading = target.getHeading();

      Polar p;
      for (int t = -1; goodNext(movement)
            && Utils.sqr(t * bulletVelocity) < Utils.distSq(myX, myY, prodictedX, prodictedY); t++) {
         movement = movement.next();
         p = movement.item();

         enemyHeading += p.getDeltaHeading();
         double enemyVelocity = p.getVelocity();
         prodictedX += Utils.getX(0.0D, enemyVelocity, enemyHeading);
         prodictedY += Utils.getY(0.0D, enemyVelocity, enemyHeading);
         if (!battleField.contains(prodictedX, prodictedY)) {
            prodictedX -= Utils.getX(0.0D, enemyVelocity, enemyHeading);
            prodictedY -= Utils.getY(0.0D, enemyVelocity, enemyHeading);
            break;
         }
         if (!goodNext(movement))
            movement = matcher.getMatch(target.getName());
      }
      return gunInfo.angle(prodictedX, prodictedY);
   }

   private boolean goodNext(FoldedEntry<Polar> entry) {
      return entry != null && entry.next() != null && entry.next().item() != Polar.DUMMY_PATTERN;
   }


   @Override
   public Color getColor() {
      return Colors.WHITE;
   }

   @Override
   public String getName() {
      return new String("Pattern Matching Targeting");
   }

   @Override
   public String getType() {
      return new String("Log");
   }

   public void print(final PrintStream console) {
      // TODO method stub
   }

   public void print(final RobocodeFileOutputStream output) {
      // TODO method stub
   }

   public void debug(final PrintStream console) {
      // TODO method stub
   }

   public void debug(final RobocodeFileOutputStream output) {
      // TODO method stub
   }

   @Override
   public Object clone() {
      return new PatternMatchingTargeting(this);
   }

   @Override
   public boolean equals(final Object obj) {
      if (obj instanceof PatternMatchingTargeting) {
         PatternMatchingTargeting targeting = (PatternMatchingTargeting) obj;
         if (targeting.robot != null && targeting.robot.getName() != null && this.robot != null)
            return targeting.robot.getName().equals(this.robot.getName());
      }
      return false;
   }

   @Override
   public String toString() {
      // TODO method stub
      return new String();
   }

   @Override
   protected void finalize() throws Throwable {
      super.finalize();
   }

}
