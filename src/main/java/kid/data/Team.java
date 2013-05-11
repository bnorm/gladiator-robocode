package kid.data;

import java.util.Collection;
import java.util.LinkedList;

import kid.robot.EnemyData;
import kid.robot.RobotData;
import kid.robot.TeammateData;
import kid.utils.Utils;
import robocode.Robot;
import robocode.TeamRobot;

// TODO document class

public class Team extends RobotChooser {

   private static final double CLOSER_MARGEN = 0.9D;

   @Override
   public RobotData getRobotOverride(Robot myRobot, Collection<RobotData> robots) {
      TeamRobot myTeamRobot = (TeamRobot) myRobot;
      RobotData robot = lastRobot;
      double distSq = Utils.distSq(myRobot.getX(), myRobot.getY(), lastRobot.getX(), lastRobot.getY());

      Collection<RobotData> teammates = new LinkedList<RobotData>();
      for (RobotData r : robots) {
         if (myTeamRobot.isTeammate(r.getName()))
            teammates.add(r);
      }

      for (RobotData r : robots) {
         if (!r.isDead()) {
            double distTemp = Utils.distSq(myRobot.getX(), myRobot.getY(), r.getX(), r.getY());
            if (distTemp < distSq * CLOSER_MARGEN) {
               robot = r;
               distSq = distTemp;
            }

         }
      }
      return robot;
   }

   @Override
   protected EnemyData getEnemyOverride(Robot myRobot, Collection<EnemyData> enemys) {
      TeamRobot myTeamRobot = (TeamRobot) myRobot;
      EnemyData robot = lastEnemy;
      double distSq = Utils.distSq(myRobot.getX(), myRobot.getY(), lastRobot.getX(), lastRobot.getY());

      Collection<EnemyData> teammates = new LinkedList<EnemyData>();
      for (EnemyData r : enemys) {
         if (myTeamRobot.isTeammate(r.getName()))
            teammates.add(r);
      }

      for (EnemyData r : enemys) {
         if (!r.isDead()) {
            double distTemp = Utils.distSq(myRobot.getX(), myRobot.getY(), r.getX(), r.getY());
            if (distTemp < distSq * CLOSER_MARGEN) {
               robot = r;
               distSq = distTemp;
            }

         }
      }
      return robot;
   }

   @Override
   protected TeammateData getTeammateOverride(Robot myRobot, Collection<TeammateData> teammates) {
      // TODO Auto-generated method stub
      return null;
   }

}