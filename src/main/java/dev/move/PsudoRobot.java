package dev.move;

import java.awt.geom.Rectangle2D;

import robocode.AdvancedRobot;
import robocode.Robot;
import robocode.Rules;
import dev.utils.Format;
import dev.utils.Trig;
import dev.utils.Utils;

public class PsudoRobot {

   private double forward;
   private double right;

   private double x;
   private double y;
   private double heading;
   private double velocity;

   private long   time;

   private double bfw;
   private double bfh;


   public PsudoRobot(double x, double y, double h, double v, long t, Rectangle2D field) {
      this.x = x;
      this.y = y;
      this.heading = h;
      this.velocity = v;
      this.time = t;

      this.bfw = field.getWidth();
      this.bfh = field.getHeight();
   }

   public PsudoRobot(Robot robot) {
      this(robot.getX(), robot.getY(), Math.toRadians(robot.getHeading()), robot.getVelocity(), robot.getTime(),
            new Rectangle2D.Double(0.0, 0.0, robot.getBattleFieldWidth(), robot.getBattleFieldHeight()));
      if (robot instanceof AdvancedRobot) {
         AdvancedRobot ar = (AdvancedRobot) robot;
         forward = ar.getDistanceRemaining();
         right = ar.getTurnRemainingRadians();
      }
   }

   public PsudoRobot(PsudoRobot robot) {
      this(robot.x, robot.y, robot.heading, robot.velocity, robot.time, new Rectangle2D.Double(0.0, 0.0, robot.bfw,
            robot.bfh));
      forward = robot.forward;
      right = robot.right;
   }

   public PsudoRobot() {
      this(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, 0.0, 0.0, Long.MIN_VALUE, new Rectangle2D.Double(0.0,
            0.0, 0.0, 0.0));
   }


   public void setTurnRight(double radians) {
      right = radians;
   }

   public void setAhead(double distance) {
      forward = distance;
   }

   public void tick() {
      double turn = Utils.sign(right) * Math.min(Rules.getTurnRateRadians(Math.abs(velocity)), Math.abs(right));
      heading += turn;
      right -= turn;

      if (velocity * forward < 0.0) {
         velocity = Utils.sign(velocity) * (Math.abs(velocity) - Rules.DECELERATION);
         forward -= velocity;
      } else if (velocity == 0.0) {
         velocity = Utils.sign(forward) * Math.min(Rules.MAX_VELOCITY, Math.abs(velocity) + Rules.ACCELERATION);
         forward -= velocity;
      } else {
         velocity = Utils.sign(velocity) * Math.min(Rules.MAX_VELOCITY, Math.abs(velocity) + Rules.ACCELERATION);
         forward -= velocity;
      }

      double dx = velocity * Trig.t_sin(heading);
      double dy = velocity * Trig.t_cos(heading);

      x += dx;
      y += dy;

      double nx = Utils.limit(18.0, x, bfw - 18.0);
      double ny = Utils.limit(18.0, y, bfh - 18.0);

      if (x != nx) {
         velocity = 0.0;
         y -= dy * (x - nx) / dx;
         x = nx;
      } else if (y != ny) {
         velocity = 0.0;
         x -= dx * (y - ny) / dy;
         y = ny;
      }

      time++;
   }

   public void compare(Robot robot) {
      robot.out.println("PSUDO: (" + Format.twoDec(x) + ", " + Format.twoDec(y) + ") H:"
            + Format.twoDec(Math.toDegrees(Utils.absolute(heading))) + " V:" + Format.twoDec(velocity) + " F:"
            + Format.twoDec(forward) + " T:" + Format.twoDec(right) + " TIME:" + time);
      robot.out.print("ROBOT: (" + Format.twoDec(robot.getX()) + ", " + Format.twoDec(robot.getY()) + ") H:"
            + Format.twoDec(robot.getHeading()) + " V:" + Format.twoDec(robot.getVelocity()));
      if (robot instanceof AdvancedRobot) {
         AdvancedRobot ar = (AdvancedRobot) robot;
         robot.out.print(" F:" + Format.twoDec(ar.getDistanceRemaining()) + " T:"
               + Format.twoDec(ar.getTurnRemainingRadians()));
      }
      robot.out.println(" TIME:" + robot.getTime());
      // robot.out.println();

      if (!Utils.isNear(x, robot.getX(), 0.01) || !Utils.isNear(y, robot.getY(), 0.01)) {
         robot.out.println("ERROR!");
      }
   }


   public double getX() {
      return x;
   }

   public double getY() {
      return y;
   }

   public double getHeading() {
      return heading;
   }

   public double getVelocity() {
      return velocity;
   }

   public long getTime() {
      return time;
   }

   public double getMovementRemaining() {
      return forward;
   }

   public double getTurnRemaining() {
      return right;
   }

   public PsudoRobot copy() {
      return new PsudoRobot(this);
   }

}
