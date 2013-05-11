package kid.management;

import java.util.HashMap;

import kid.communication.Message;
import kid.communication.RobotMessage;
import kid.communication.ScannedRobotMessage;
import kid.data.Drawable;
import kid.data.RobotChooser;
import kid.graphics.RGraphics;
import kid.robot.EnemyData;
import kid.robot.RobotData;
import kid.robot.TeammateData;
import robocode.DeathEvent;
import robocode.Event;
import robocode.MessageEvent;
import robocode.Robot;
import robocode.RobotDeathEvent;
import robocode.ScannedRobotEvent;
import robocode.TeamRobot;
import robocode.WinEvent;

/**
 * A manager <code>class</code> that stores information on the enemies and teammates that are in the current match.
 * Allows the user to easily access the <code>EnemyData</code> or <code>TeammateData</code> <code>classes</code> that
 * store the information on the robots and to also update them through the
 * <code>{@link #inEvent(Event) inEvent(Event)}</code> method.<br>
 * <br>
 * 
 * @author Brian Norman
 * @version 0.0.5 beta
 */
public class RobotManager implements Drawable {

   /**
    * The <code>{@link robocode.Robot Robot}</code> that this manager <code>class</code> is referencing. Used to provide
    * useful information about the battlefield and its position in it, along with number of robots currently on the
    * battlefield and other important information.
    */
   private Robot robot;

   /**
    * An array of <code>{@link kid.data.robot.RoboData RobotData}</code> that represent the robots that are in the
    * current match excluding the <code>{@link robocode.Robot Robot}</code> that created this <code>class</code>.
    * 
    * @see kid.robot.RobotData
    */
   private static HashMap<String, RobotData> robots;

   /**
    * An array of <code>{@link kid.robot.EnemyData EnemyData}</code> that represent the enemies of the
    * <code>{@link robocode.Robot Robot}</code> that created this <code>class</code>.
    * 
    * @see kid.robot.EnemyData
    */
   private static HashMap<String, EnemyData> enemies;

   /**
    * An Array of <code>{@link kid.robot.TeammateData TeammateData}</code> that represent the teammates of the
    * <code>{@link robocode.Robot Robot}</code> that created this <code>class</code>.
    * 
    * @see kid.robot.TeammateData
    */
   private static HashMap<String, TeammateData> teammates;

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
   public RobotManager(final Robot myRobot) {
      init(myRobot);
   }

   /**
    * Initializes all the static variables with the proper values. Should only be called by the constructor.
    * 
    * @param myRobot - the robot that created the class.
    */
   private void init(final Robot myRobot) {
      robot = myRobot;
      if (robots == null) {
         robots = new HashMap<String, RobotData>(robot.getOthers());
         teammates = new HashMap<String, TeammateData>(0);
         enemies = new HashMap<String, EnemyData>(robot.getOthers());
         if (myRobot instanceof TeamRobot) {
            TeamRobot teamRobot = (TeamRobot) myRobot;
            if (teamRobot.getTeammates() != null) {
               teammates = new HashMap<String, TeammateData>(teamRobot.getTeammates().length);
               enemies = new HashMap<String, EnemyData>(robot.getOthers() - teamRobot.getTeammates().length);
            }
         }
      } else {
         for (RobotData r : robots.values())
            r.setDeath();
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
    * <li><code>WinEvent</code><small> - For teams only</small>.</li>
    * <li><code>MessageEvent</code><small> - For teams only</small>.</li>
    * </ul>
    * <br>
    * Example:
    * 
    * <pre>
    * public class ExampleRobot extends Robot {
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
    * @param event a generic <code>Event</code>.
    */
   public void inEvent(final Event event) {
      if (event instanceof ScannedRobotEvent)
         handleScannedRobot(new ScannedRobotMessage((ScannedRobotEvent) event, robot));
      else if (event instanceof RobotDeathEvent)
         handleRobotDeath((RobotDeathEvent) event);
      else if (event instanceof DeathEvent)
         handleDeath((DeathEvent) event);
      else if (event instanceof WinEvent)
         handleWin((WinEvent) event);
      else if (event instanceof MessageEvent)
         handleMessage((MessageEvent) event);
   }

   // BORED documentation: method inMessage(Message)
   private void inMessage(final Message message) {
      if (message instanceof ScannedRobotMessage)
         handleScannedRobot((ScannedRobotMessage) message);
      else if (message instanceof RobotMessage)
         handleRobotMessage((RobotMessage) message);
   }

   /**
    * Properly handles a <code>{@link robocode.ScannedRobotEvent ScannedRobotEvent}</code> by updating a robot or adding
    * a new robot when needed.
    * 
    * @param srm a <code>ScannedRobotMessage</code>.
    */
   private void handleScannedRobot(final ScannedRobotMessage srm) {
      RobotData r = getRobot(srm.getName());
      if (!r.isDummy()) {
         r.update(srm, robot);
      } else if (!srm.getName().equals(robot.getName())) {
         boolean isEnemy = true;
         if (robot instanceof TeamRobot) {
            TeamRobot teamRobot = (TeamRobot) robot;
            if (teamRobot.isTeammate(srm.getName())) {
               TeammateData t = new TeammateData(srm, robot);
               teammates.put(t.getName(), t);
               robots.put(t.getName(), t);
               isEnemy = false;
            }
         }
         if (isEnemy) {
            EnemyData e = new EnemyData(srm, robot);
            enemies.put(e.getName(), e);
            robots.put(e.getName(), e);
         }
      }
   }

   // TODO documentation: method - handleRobotMessage(RobotMessage)
   private void handleRobotMessage(final RobotMessage rm) {
      RobotData r = getRobot(rm.getName());
      if (!r.isDummy()) {
         r.update(rm, robot);
      }
   }

   /**
    * Properly handles a <code>{@link robocode.RobotDeathEvent RobotDeathEvent}</code> by setting the robot's alias to
    * <code>{@link kid.robot.RobotData#DEAD DEAD}</code>.
    * 
    * @param rde a <code>RobotDeathEvent</code>.
    */
   private void handleRobotDeath(final RobotDeathEvent rde) {
      if (rde != null)
         getRobot(rde.getName()).setDeath();
   }

   /**
    * Properly handles a <code>{@link robocode.DeathEvent DeathEvent}</code> by setting all robot's aliases to
    * <code>{@link kid.robot.RobotData#DEAD DEAD}</code>. In this way, when a new round starts, all robots will be
    * considered dead and will then be reset for a new round.
    * 
    * @param de a <code>DeathEvent</code>.
    */
   private void handleDeath(final DeathEvent de) {
      for (RobotData r : robots.values())
         r.setDeath();
   }

   /**
    * Properly handles a <code>{@link robocode.DeathEvent DeathEvent}</code> by setting all robot's aliases to
    * <code>{@link kid.robot.RobotData#DEAD DEAD}</code>. In this way, when a new round starts, all robots will be
    * considered dead and will then be reset for a new round.
    * 
    * @param we a <code>DeathEvent</code>.
    */
   private void handleWin(final WinEvent we) {
      for (RobotData r : robots.values())
         r.setDeath();
   }

   // BORED documentation: method handleMessage(MessageEvent)
   /**
    * Properly handles a <code>{@link robocode.MessageEvent MessageEvent}</code> ...
    * 
    * @param me a <code>MessageEvent</code>.
    */
   private void handleMessage(final MessageEvent me) {
      if (me.getMessage() instanceof Message)
         inMessage((Message) me.getMessage());
   }

   /**
    * Return an <code>array</code> of <code>{@link kid.robot.RobotData RobotData}</code> <code>classes</code> that
    * represent all the robots that have currently been scanned.
    * 
    * @return the robots that have been scanned.
    */
   public RobotData[] getRobots() {
      return robots.values().toArray(new RobotData[0]);
   }

   /**
    * Returns an <code>array</code> of <code>{@link kid.robot.RobotData RobotData}</code> <code>classes</code> that
    * represent all the robots that are currently still active and have been scanned.
    * 
    * @return the robots that are still active and have been scanned.
    */
   public RobotData[] getAliveRobots() {
      RobotData[] aliveRobots = new RobotData[numAliveRobots()];
      int i = 0;
      for (RobotData r : robots.values())
         if (!r.isDead())
            aliveRobots[i++] = r;
      return aliveRobots;
   }

   // TODO documentation: method - numAliveRobots()
   public int numAliveRobots() {
      int num = 0;
      for (RobotData r : robots.values())
         if (!r.isDead())
            num++;
      return num;
   }

   /**
    * Returns an <code>array</code> of <code>{@link kid.robot.EnemyData EnemyData}</code> <code>class</code> es that
    * represent all the enemies that have currently been scanned.
    * 
    * @return the enemies that have been scanned.
    */
   public EnemyData[] getEnemies() {
      return enemies.values().toArray(new EnemyData[0]);
   }

   /**
    * Returns an <code>array</code> of <code>{@link kid.robot.EnemyData EnemyData}</code> <code>class</code> es that
    * represent the enemies that are currently still active and have been scanned.
    * 
    * @return the enemies that are still active and have been scanned.
    */
   public EnemyData[] getAliveEnemies() {
      EnemyData[] aliveEnemies = new EnemyData[numAliveEnemies()];
      int i = 0;
      for (EnemyData e : enemies.values())
         if (!e.isDead())
            aliveEnemies[i++] = e;
      return aliveEnemies;
   }

   // BORED documentation: method - numAliveEnemies()
   public int numAliveEnemies() {
      int num = 0;
      for (EnemyData e : enemies.values())
         if (!e.isDead())
            num++;
      return num;
   }

   /**
    * Returns an <code>array</code> of <code>{@link kid.robot.TeammateData TeammateData}</code> <code>classes</code>
    * that represent all the teammates that have currently been scanned.
    * 
    * @return the teammates that have been scanned.
    */
   public TeammateData[] getTeammates() {
      return teammates.values().toArray(new TeammateData[0]);
   }

   /**
    * Returns a <code>{@link kid.robot.RobotData RobotData}</code> <code>class</code> that is chosen be a
    * <code>RobotChooser</code> <code>class</code>. This method is best called by something similar to the following
    * example:
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
    * @param chooser the <code>RobotChooser</code> <code>class</code> that chooses the best robot.
    * @return the robot that best matches the <code>RobotChooser</code> <code>class</code>.
    */
   public RobotData getRobot(final RobotChooser chooser) {
      return chooser.getRobot(robot, robots.values());
   }

   /**
    * Returns a <code>{@link kid.robot.EnemyData EnemyData}</code> <code>class</code> that is chosen be a
    * <code>RobotChooser</code> <code>class</code>. See
    * <code>{@link #getRobot(RobotChooser) getRobot(RobotChooser)}</code> for an example.
    * 
    * @param chooser the <code>RobotChooser</code> <code>class</code> that chooses the best enemy.
    * @return the enemy that best matches the <code>RobotChooser</code> <code>class</code>.
    * @see #getRobot(RobotChooser)
    */
   public EnemyData getEnemy(final RobotChooser chooser) {
      return chooser.getEnemy(robot, enemies.values());
   }

   /**
    * Returns a <code>{@link kid.robot.TeammateData TeammateData}</code> <code>class</code> that is chosen be a
    * <code>RobotChooser</code> <code>class</code>. See
    * <code>{@link #getRobot(RobotChooser) getRobot(RobotChooser)}</code> for an example.
    * 
    * @param chooser the <code>RobotChooser</code> <code>class</code> that chooses the best teammate.
    * @return the teammate that best matches the <code>RobotChooser</code> <code>class</code>.
    * @see #getRobot(RobotChooser)
    */
   public TeammateData getTeammate(final RobotChooser chooser) {
      return chooser.getTeammate(robot, teammates.values());
   }

   /**
    * Returns the <code>{@link kid.robot.RobotData RobotData}</code> who's name matches the passed <code>string</code>.
    * Will return a dummy robot if no match is found.
    * 
    * @param name the name of a possible robot.
    * @return the robot who's name matches the passed <code>string</code>.
    */
   public RobotData getRobot(final String name) {
      RobotData robot = robots.get(name);
      return (robot != null ? robot : new RobotData());
   }

   /**
    * Returns the <code>{@link kid.robot.EnemyData EnemyData}</code> who's name matches the passed <code>string</code>.
    * Will return a dummy enemy if no match is found.
    * 
    * @param name the name of a possible enemy.
    * @return the enemy who's name matches the passed <code>string</code>.
    */
   public EnemyData getEnemy(final String name) {
      EnemyData enemy = enemies.get(name);
      return (enemy != null ? enemy : new EnemyData());
   }

   /**
    * Returns the <code>{@link kid.robot.TeammateData TeammateData}</code> who's name matches the passed
    * <code>string</code>. Will return a teammate robot if no match is found.
    * 
    * @param name the name of a possible teammate.
    * @return the teammate (who's name matches the passed <code>string</code>.
    */
   public TeammateData getTeammate(final String name) {
      TeammateData teammate = teammates.get(name);
      return (teammate != null ? teammate : new TeammateData());
   }

   // BORED documentation: method - draw(RobocodeGraphicsDrawer, String)
   @Override
   public void draw(final RGraphics grid) {
      for (RobotData r : getRobots())
         r.draw(grid);
   }

}