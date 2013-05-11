package bnorm;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import bnorm.base.AdvGun;
import bnorm.base.AdvRadar;
import bnorm.draw.DrawMenu;
import bnorm.draw.RobotDraw;
import bnorm.draw.WaveDraw;
import bnorm.events.RobotFiredEvent;
import bnorm.events.RobotFiredEventListener;
import bnorm.manage.RobotManager;
import bnorm.manage.TeamManager;
import bnorm.messages.Message;
import bnorm.robots.IRobot;
import bnorm.robots.IRobotSnapshot;
import bnorm.robots.RobotFactory;
import bnorm.robots.RobotSnapshotFactory;
import bnorm.utils.Utils;
import bnorm.virtual.BulletWave;
import bnorm.virtual.EnemyBulletWave;
import robocode.Bullet;
import robocode.DeathEvent;
import robocode.MessageEvent;
import robocode.RobotDeathEvent;
import robocode.ScannedRobotEvent;
import robocode.TeamRobot;
import robocode.WinEvent;


public class Rinzler extends TeamRobot implements RobotFiredEventListener {

   public static final String DRAW_MENU = "menu.draw";
   public static final Color NEON_ORANGE = new Color(255, 143, 0);

   private static final RobotSnapshotFactory snapshotFactory_ = new RobotSnapshotFactory();
   private static final RobotFactory robotFactory_ = new RobotFactory(snapshotFactory_);

   public IRobot me;

   public RobotManager robots;
   public TeamManager team;
   public IRobot enemy;

   public AdvGun gun;
   public AdvRadar radar;

   public List<BulletWave> bullets = new LinkedList<BulletWave>();
   public List<EnemyBulletWave> waves = new LinkedList<EnemyBulletWave>();

   @Override
   public void run() {
      DrawMenu.load(getDataFile(DRAW_MENU));

      setColors(Color.BLACK, Color.BLACK, NEON_ORANGE, NEON_ORANGE, NEON_ORANGE);
      setAdjustGunForRobotTurn(true);
      setAdjustRadarForGunTurn(true);

      me = robotFactory_.create(this);

      robots = RobotManager.getInstance(this, robotFactory_, snapshotFactory_);
      team = TeamManager.getInstance(this, snapshotFactory_);
      enemy = null;

      gun = new AdvGun(this);
      radar = new AdvRadar(this);

      setTurnRadarRightRadians(Double.POSITIVE_INFINITY);

      while (true) {
         team.sendMe();
         me.add(snapshotFactory_.create(this));

         setAhead(Double.POSITIVE_INFINITY);
         setTurnRightRadians(Double.POSITIVE_INFINITY);

         enemy = robots.getRobot(new Comparator<IRobot>() {
            private double distSq(IRobotSnapshot snapshot) {
               double distSq = gun.distSq(snapshot);
               if (snapshot.equals(enemy)) {
                  distSq *= 0.8;
               }
               return distSq;
            }

            @Override
            public int compare(IRobot r1, IRobot r2) {
               return Utils.signZ(distSq(r1.getSnapshot()) - distSq(r2.getSnapshot()));
            }
         });

         IRobotSnapshot snapshot = enemy.getSnapshot();
         if ((gun.getHeat() <= 0.5 || getOthers() == 1) && getTime() - snapshot.getTime() < 10L) {
            radar.setSweepDist(snapshot, 36.0);
         } else {
            setTurnRadarRightRadians(Double.POSITIVE_INFINITY);
         }
         gun.setTurnTo(snapshot);
         Bullet b = gun.setFire(1.5);
         if (b != null) {
            bullets.add(new BulletWave(b, getTime()));
         }
         execute();
      }
   }

   @Override
   public void onPaint(Graphics2D g) {
      long time = getTime();
      ListIterator<BulletWave> iter1 = bullets.listIterator();
      while (iter1.hasNext()) {
         if (!iter1.next().isActive(time)) {
            iter1.remove();
         }
      }
      ListIterator<EnemyBulletWave> iter2 = waves.listIterator();
      while (iter2.hasNext()) {
         if (!iter2.next().isActive(time, me)) {
            iter2.remove();
         }
      }

      DrawMenu.draw(g);
      RobotDraw.drawPaths(g, robots.getRobots());
      RobotDraw.drawBoxes(g, robots.getRobots());
      WaveDraw.drawWaves(g, bullets, getTime(), Color.BLUE);
      WaveDraw.drawWaves(g, waves, getTime(), Color.RED);
      RobotDraw.drawPath(g, me);
   }

   @Override
   public void onMouseClicked(MouseEvent e) {
      DrawMenu.inMouseEvent(e);
   }

   @Override
   public void onMessageReceived(MessageEvent event) {
      if (event.getMessage() instanceof Message) {
         robots.inMessage((Message) event.getMessage());
      }
   }

   @Override
   public void onScannedRobot(ScannedRobotEvent event) {
      IRobot before = robots.getRobot(event.getName());
      robots.inEvent(event);
      team.inEvent(event);

      if (!before.getName().equals(event.getName())) {
         robots.getRobot(event.getName()).addListener(this);
      }
   }

   @Override
   public void onRobotDeath(RobotDeathEvent event) {
      robots.inEvent(event);
   }

   @Override
   public void onDeath(DeathEvent event) {
      enemy = null;
      DrawMenu.save(getDataFile(DRAW_MENU));
   }

   @Override
   public void onWin(WinEvent event) {
      DrawMenu.save(getDataFile(DRAW_MENU));
   }

   @Override
   public void handle(RobotFiredEvent event) {
      IRobotSnapshot snapshot = robots.getRobot(event.getName()).getSnapshot(event.getTime());
      waves.add(new EnemyBulletWave(snapshot, event));
   }
}
