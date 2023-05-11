package kid.managers;

import kid.RobocodeGraphicsDrawer;
import kid.data.*;
import kid.data.robot.*;
import robocode.*;

/**
 * A manager <code>class</code> that stores information on the enemies and teammates that are in the current match.
 * Allows the user to easily access the <code>EnemyData</code> or <code>TeammateData</code> <code>classes</code>
 * that store the information on the robots and to also update them through the
 * <code>{@link #inEvent(Event) inEvent(Event)}</code> method.<br>
 * <br>
 * 
 * @author Brian Norman
 * @version 0.0.1 beta
 */
public class RobotManager implements Drawable {

   /**
    * The <code>{@link robocode.Robot Robot}</code> that this manager <code>class</code> is referencing. Used to
    * provide useful information about the battlefield and its position in it, along with number of robots currently on
    * the battlefield and other important information.
    */
   private Robot robot;

   /**
    * The total number of robots that are in the current match not including the
    * <code>{@link robocode.Robot Robot}</code> that is using this <code>class</code>.
    */
   private static int numRobots;

   /**
    * The total number of enemies to the <code>{@link robocode.Robot Robot}</code> that is using this
    * <code>class</code> that are in the current match.
    */
   private static int numEnemies;

   /**
    * The total number of teammates to the <code>Robot</code> that is using this <code>class</code> that are in the
    * current match.
    */
   private static int numTeammates;

   /**
    * An array of <code>{@link kid.data.robot.RoboData RobotData}</code> that represent the robots that are in the
    * current match excluding the <code>{@link robocode.Robot Robot}</code> that created this <code>class</code>.
    * 
    * @see kid.data.robot.RobotData
    */
   private static RobotData[] robots;

   /**
    * An array of <code>{@link kid.data.robot.EnemyData EnemyData}</code> that represent the enemies of the
    * <code>{@link robocode.Robot Robot}</code> that created this <code>class</code>.
    * 
    * @see kid.data.robot.EnemyData
    */
   private static EnemyData[] enemies;

   /**
    * An Array of <code>{@link kid.data.robot.TeammateData TeammateData}</code> that represent the teammates of the
    * <code>{@link robocode.Robot Robot}</code> that created this <code>class</code>.
    * 
    * @see kid.data.robot.TeammateData
    */
   private static TeammateData[] teammates;

   /**
    * This constructor is used to initiate all the static variables with the proper values. It should be called in a
    * similar way:
    * 
    * <pre>
    * class TestRobot extends Robot {
    * 
    *    RobotManager robots;
    * 
    *    public void run() {
    *       robots = new RobotManager(this);
    *       .
    *       .
    *       .
    *    }
    *    .
    *    .
    *    .
    * }
    * </pre>
    * 
    * @param myRobot - the robot that is creating the class.
    */
   public RobotManager(Robot myRobot) {
      init(myRobot);
   }

   /**
    * Initializes all the static variables with the proper values. Should only be called by the constructor.
    * 
    * @param myRobot - the robot that created the class.
    */
   private void init(Robot myRobot) {
      robot = myRobot;
      if (robots == null) {
         numRobots = 0;
         robots = new RobotData[robot.getOthers()];
         for (int i = 0; i < robots.length; i++)
            robots[i] = new RobotData();
         numTeammates = 0;
         teammates = new TeammateData[0];
         if (myRobot instanceof TeamRobot) {
            TeamRobot teamRobot = (TeamRobot) myRobot;
            if (teamRobot.getTeammates() != null) {
               teammates = new TeammateData[teamRobot.getTeammates().length];
               for (int i = 0; i < teammates.length; i++)
                  teammates[i] = new TeammateData();
            }
         }
         numEnemies = 0;
         enemies = new EnemyData[robot.getOthers() - teammates.length];
         for (int i = 0; i < enemies.length; i++)
            enemies[i] = new EnemyData();
      }
   }

   /**
    * Properly handles all <code>{@link robocode.Event Events}</code> that are overridden by the
    * <code>{@link robocode.Robot Robot}</code>.<br>
    * <br>
    * The only events currently needing to be passed are:
    * <ul>
    * <li><code>ScannedRobotEvent</code>.</li>
    * <li><code>RobotDeathEvent</code>.</li>
    * <li><code>DeathEvent</code>.</li>
    * </ul>
    * <br>
    * Example:
    * 
    * <pre>
    * class ExampleRobot extends Robot {
    *    
    *    RobotManager robots;
    *    
    *    void run() {
    *    
    *       robots = new RobotManager(this);
    *       .
    *       .
    *       .
    *    }
    *    
    *    void onScannedRobot(ScannedRobotEvent sre) {
    *       robots.inEvent(sre);
    *       .
    *       .
    *       .
    *    }
    *    
    *    void onRobotDeath(RobotDeathEvent rde) {
    *       robots.inEvent(rde);
    *       .
    *       .
    *       .
    *    }
    *    .
    *    .
    *    .
    * }
    * </pre>
    * 
    * @param event - a generic <code>Event</code>.
    */
   public void inEvent(Event event) {
      if (event instanceof ScannedRobotEvent)
         handleScannedRobot((ScannedRobotEvent) event);
      else if (event instanceof RobotDeathEvent)
         handleRobotDeath((RobotDeathEvent) event);
      else if (event instanceof DeathEvent)
         handleDeath((DeathEvent) event);
   }

   /**
    * Properly handles a <code>ScannedRobotEvent</code> by updating a robot or adding a new robot when needed.
    * 
    * @param sre - a <code>ScannedRobotEvent</code>.
    */
   private void handleScannedRobot(ScannedRobotEvent sre) {
      RobotData r = getRobot(sre.getName());
      if (!r.isDummy()) {
         r.update(sre, robot);
      } else {
         boolean isEnemy = true;
         if (robot instanceof TeamRobot) {
            TeamRobot teamRobot = (TeamRobot) robot;
            if (teamRobot.isTeammate(sre.getName())) {
               TeammateData t = new TeammateData(sre, robot);
               teammates[numTeammates++] = t;
               robots[numRobots++] = t;
               isEnemy = false;
            }
         }
         if (isEnemy) {
            EnemyData e = new EnemyData(sre, robot);
            enemies[numEnemies++] = e;
            robots[numRobots++] = e;
         }
      }
   }

   /**
    * Properly handles a <code>RobotDeathEvent</code> by setting the robot's alias to
    * <code>{@link kid.data.robot.RobotData#DEAD DEAD}</code>.
    * 
    * @param rde - a <code>RobotDeathEvent</code>.
    */
   private void handleRobotDeath(RobotDeathEvent rde) {
      getRobot(rde.getName()).setDeath();
   }

   /**
    * Properly handles a <code>DeathEvent</code> by setting all robot's aliases to
    * <code>{@link kid.data.robot.RobotData#DEAD DEAD}</code>. In this way, when a new round starts, all robots will
    * be considered dead and will then be reset for a new round.
    * 
    * @param rde - a <code>DeathEvent</code>.
    */
   private void handleDeath(DeathEvent rde) {
      for (RobotData r : robots)
         r.setDeath();
   }

   /**
    * Return an <code>array</code> of <code>RobotData</code> <code>classes</code> that represent all the robots
    * that have currently been scanned.
    * 
    * @return the robots that have been scanned.
    */
   public RobotData[] getRobots() {
      return robots;
   }

   /**
    * Returns an <code>array</code> of <code>RobotData</code> <code>classes</code> that represent all the robots
    * that are currently still active and have been scanned.
    * 
    * @return the robots that are still active and have been scanned.
    */
   public RobotData[] getAliveRobots() {
      RobotData[] aliveRobots = new RobotData[robot.getOthers()];
      int i = 0;
      for (RobotData r : robots)
         if (!r.isDead())
            aliveRobots[i++] = r;
      return aliveRobots;
   }

   /**
    * Returns an <code>array</code> of <code>EnemyData</code> <code>classes</code> that represent all the enemies
    * that have currently been scanned.
    * 
    * @return the enemies that have been scanned.
    */
   public EnemyData[] getEnemies() {
      return enemies;
   }

   /**
    * Returns an <code>array</code> of <code>TeammateData</code> <code>classes</code> that represent all the
    * teammates that have currently been scanned.
    * 
    * @return the teammates that have been scanned.
    */
   public TeammateData[] getTeammates() {
      return teammates;
   }

   /**
    * Returns a <code>RobotData</code> <code>class</code> that is chosen be a
    * <code>RobotChooser</code> <code>class</code>. This method is best called by something similar to the
    * following example:
    * 
    * <pre>
    * class ExampleRobot extends Robot {
    *    
    *    RobotManager robots;
    *    
    *    void run() {
    *       
    *       robots = new RobotManager(this);
    *       
    *       while (true) {
    *          RobotData robot = robots.getRobot(RobotChooser.CLOSEST);
    *          .
    *          .
    *          .
    *       }
    *    }
    *    .
    *    .
    *    .
    * }
    * </pre>
    * 
    * @param chooser - the <code>RobotChooser</code> <code>class</code> that chooses the best robot.
    * @return the robot that best matches the <code>RobotChooser</code> <code>class</code>.
    */
   public RobotData getRobot(RobotChooser chooser) {
      return chooser.getRobot(robot, robots);
   }

   /**
    * Returns a <code>EnemyData</code> <code>class</code> that is chosen be a
    * <code>RobotChooser</code> <code>class</code>. See
    * <code>{@link #getRobot(RobotChooser) getRobot(RobotChooser)}</code> for an example.
    * 
    * @param chooser - the <code>RobotChooser</code> <code>class</code> that chooses the best enemy.
    * @return the enemy that best matches the <code>RobotChooser</code> <code>class</code>.
    * @see #getRobot(RobotChooser)
    */
   public EnemyData getEnemy(RobotChooser chooser) {
      return chooser.getEnemy(robot, enemies);
   }

   /**
    * Returns a <code>TeammateData</code> <code>class</code> that is chosen be a
    * <code>RobotChooser</code> <code>class</code>. See
    * <code>{@link #getRobot(RobotChooser) getRobot(RobotChooser)}</code> for an example.
    * 
    * @param chooser - the <code>RobotChooser</code> <code>class</code> that chooses the best teammate.
    * @return the teammate that best matches the <code>RobotChooser</code> <code>class</code>.
    * @see #getRobot(RobotChooser)
    */
   public TeammateData getTeammate(RobotChooser chooser) {
      return chooser.getTeammate(robot, teammates);
   }

   /**
    * Returns the <code>RobotData</code> who's name matches the passed <code>string</code>. Will return a dummy
    * robot if no match is found.
    * 
    * @param name - the name of a possible robot.
    * @return the robot who's name matches the passed <code>string</code>.
    */
   public RobotData getRobot(String name) {
      RobotData robot = new RobotData();
      for (int i = 0; i < numRobots && robot.isDummy(); i++)
         if (robots[i].getName().equals(name))
            robot = robots[i];
      return robot;
   }

   /**
    * Returns the <code>EnemyData</code> who's name matches the passed <code>string</code>. Will return a dummy
    * enemy if no match is found.
    * 
    * @param name - the name of a possible enemy.
    * @return the enemy who's name matches the passed <code>string</code>.
    */
   public EnemyData getEnemy(String name) {
      EnemyData enemy = new EnemyData();
      for (int i = 0; i < numEnemies && enemy.isDummy(); i++)
         if (enemies[i].getName().equals(name))
            enemy = enemies[i];
      return enemy;
   }

   /**
    * Returns the <code>TeammateData</code> who's name matches the passed <code>string</code>. Will return a
    * teammate robot if no match is found.
    * 
    * @param name - the name of a possible teammate.
    * @return the teammate (who's name matches the passed <code>string</code>.
    */
   public TeammateData getTeammate(String name) {
      TeammateData teammate = new TeammateData();
      for (int i = 0; i < numTeammates && teammate.isDummy(); i++)
         if (teammates[i].getName().equals(name))
            teammate = teammates[i];
      return teammate;
   }

   @Override
   public void draw(RobocodeGraphicsDrawer grid, String commandString) {
      for (RobotData r : getRobots())
         r.draw(grid, commandString);
   }

}