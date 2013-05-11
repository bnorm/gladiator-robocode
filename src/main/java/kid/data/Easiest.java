package kid.data;

import java.util.Collection;

import kid.robot.EnemyData;
import kid.robot.RobotData;
import kid.robot.TeammateData;
import kid.utils.Utils;
import robocode.Robot;

// TODO document class

public class Easiest extends RobotChooser {

   private static final double CLOSER_MARGEN = 1.0D;

   @Override
   protected RobotData getRobotOverride(Robot myRobot, Collection<RobotData> robots) {
      RobotData robot = lastRobot;
      double rating = getRating(myRobot, robot);
      for (RobotData r : robots) {
         if (r.isDead())
            continue;
         double ratingTemp = getRating(myRobot, r);
         if (ratingTemp < rating * CLOSER_MARGEN) {
            robot = r;
            rating = ratingTemp;
         }
      }
      return robot;
   }

   @Override
   protected EnemyData getEnemyOverride(Robot myRobot, Collection<EnemyData> enemys) {
      EnemyData enemy = lastEnemy;
      double rating = getRating(myRobot, enemy);
      for (EnemyData e : enemys) {
         if (e.isDead())
            continue;
         double ratingTemp = getRating(myRobot, e);
         if (ratingTemp < rating * CLOSER_MARGEN) {
            enemy = e;
            rating = ratingTemp;
         }
      }
      return enemy;
   }

   @Override
   protected TeammateData getTeammateOverride(Robot myRobot, Collection<TeammateData> teammates) {
      TeammateData teammate = lastTeammate;
      double rating = getRating(myRobot, teammate);
      for (TeammateData t : teammates) {
         if (t.isDead())
            continue;
         double ratingTemp = getRating(myRobot, t);
         if (ratingTemp < rating * CLOSER_MARGEN) {
            teammate = t;
            rating = ratingTemp;
         }
      }
      return teammate;
   }

   private double getRating(Robot myRobot, RobotData robot) {
      if (robot.isDead())
         return Double.POSITIVE_INFINITY;
      return robot.getEnergy() + Utils.dist(myRobot.getX(), myRobot.getY(), robot.getX(), robot.getY());
   }

}