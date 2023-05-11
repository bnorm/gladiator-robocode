package kid.data;

import robocode.Robot;

import kid.data.robot.*;

// TODO documentation: class

public abstract class RobotChooser {

   protected RobotData lastRobot = new RobotData();
   protected long lastTime_Robot = -1;

   protected EnemyData lastEnemy = new EnemyData();
   protected long lastTime_Enemy = -1;

   protected TeammateData lastTeammate = new TeammateData();
   protected long lastTime_Teammate = -1;

   public final static RobotChooser CLOSEST = new Closest();
   public final static RobotChooser EASIEST = new Easiest();

   public RobotData getRobot(Robot myRobot, RobotData[] robots) {
      if (lastRobot.isDead()) {
         lastTime_Robot = -1;
         lastRobot = new RobotData();
      }
      if (myRobot.getTime() == lastTime_Robot)
         return lastRobot;
      lastRobot = this.getRobotOverride(myRobot, robots);
      lastTime_Robot = myRobot.getTime();
      return lastRobot;
   }

   public EnemyData getEnemy(Robot myRobot, EnemyData[] enemys) {
      if (lastEnemy.isDead()) {
         lastTime_Enemy = -1;
         lastEnemy = new EnemyData();
      }
      if (myRobot.getTime() == lastTime_Enemy)
         return lastEnemy;
      lastEnemy = this.getEnemyOverride(myRobot, enemys);
      lastTime_Enemy = myRobot.getTime();
      return lastEnemy;

   }

   public TeammateData getTeammate(Robot myRobot, TeammateData[] teammates) {
      if (lastTeammate.isDead()) {
         lastTime_Teammate = -1;
         lastTeammate = new TeammateData();
      }
      if (myRobot.getTime() == lastTime_Teammate)
         return lastTeammate;
      lastTeammate = this.getTeammateOverride(myRobot, teammates);
      lastTime_Teammate = myRobot.getTime();
      return lastTeammate;
   }

   protected abstract RobotData getRobotOverride(Robot myRobot, RobotData[] robots);

   protected abstract EnemyData getEnemyOverride(Robot myRobot, EnemyData[] enemys);

   protected abstract TeammateData getTeammateOverride(Robot myRobot, TeammateData[] teammates);

}