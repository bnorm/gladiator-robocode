package kid.data;

import java.util.Collection;

import kid.robot.EnemyData;
import kid.robot.RobotData;
import kid.robot.TeammateData;
import kid.utils.Utils;
import robocode.Robot;

// TODO document class

public class Closest extends RobotChooser {

   private static final double CLOSER_MARGEN = 0.9D;

   @Override
   public RobotData getRobotOverride(Robot myRobot, Collection<RobotData> robots) {
      RobotData robot = lastRobot;
      double distSq = Utils.distSq(myRobot.getX(), myRobot.getY(), lastRobot.getX(), lastRobot.getY());
      for (RobotData r : robots) {
         if (r.isDead())
            continue;
         double distTemp = Utils.distSq(myRobot.getX(), myRobot.getY(), r.getX(), r.getY());
         if (distTemp < distSq * CLOSER_MARGEN) {
            robot = r;
            distSq = distTemp;
         }
      }
      return robot;
   }

   @Override
   public EnemyData getEnemyOverride(Robot myRobot, Collection<EnemyData> enemys) {
      EnemyData enemy = lastEnemy;
      double distSq = Utils.distSq(myRobot.getX(), myRobot.getY(), lastEnemy.getX(), lastEnemy.getY());
      for (EnemyData e : enemys) {
         if (e.isDead())
            continue;
         double distTemp = Utils.distSq(myRobot.getX(), myRobot.getY(), e.getX(), e.getY());
         if (distTemp < distSq * CLOSER_MARGEN) {
            enemy = e;
            distSq = distTemp;
         }
      }
      return enemy;
   }

   @Override
   public TeammateData getTeammateOverride(Robot myRobot, Collection<TeammateData> teammates) {
      TeammateData teammate = lastTeammate;
      double distSq = Utils.distSq(myRobot.getX(), myRobot.getY(), lastTeammate.getX(), lastTeammate.getY());
      for (TeammateData t : teammates) {
         if (t.isDead())
            continue;
         double distTemp = Utils.distSq(myRobot.getX(), myRobot.getY(), t.getX(), t.getY());
         if (distTemp < distSq * CLOSER_MARGEN) {
            teammate = t;
            distSq = distTemp;
         }
      }
      return teammate;
   }

}