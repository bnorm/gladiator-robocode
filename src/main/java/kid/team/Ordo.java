package kid.team;

import java.awt.event.MouseEvent;

import kid.communication.RobotMessage;
import kid.communication.ScannedRobotMessage;
import kid.data.RobotChooser;
import kid.graphics.Colors;
import kid.graphics.DrawMenu;
import kid.graphics.RGraphics;
import kid.management.RobotManager;
import kid.management.TargetingManager;
import kid.management.TeamManager;
import kid.movement.radar.RadarMovement;
import kid.movement.robot.Movement;
import kid.movement.robot.Perpendicular;
import kid.robot.EnemyData;
import kid.targeting.CircularTargeting;
import kid.targeting.HeadOnTargeting;
import kid.targeting.LinearTargeting;
import kid.targeting.Targeting;
import kid.utils.Utils;
import robocode.BulletHitBulletEvent;
import robocode.BulletHitEvent;
import robocode.DeathEvent;
import robocode.HitByBulletEvent;
import robocode.MessageEvent;
import robocode.RobotDeathEvent;
import robocode.Rules;
import robocode.ScannedRobotEvent;
import robocode.SkippedTurnEvent;
import robocode.TeamRobot;
import robocode.WinEvent;

public class Ordo extends TeamRobot {

    private Movement movement;
    private RadarMovement radar;

    private RobotManager robots;
    private TargetingManager targeting;
    private TeamManager team;

    @Override
    public void run() {

        setColors(Colors.MUD_RED, Colors.SILVER, Colors.VISER_BLUE);
        setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);

        Targeting[] targetings =
                { new HeadOnTargeting(this), new LinearTargeting(this), new LinearTargeting(this, true), new CircularTargeting(this), new CircularTargeting(this, false, true),
                        new CircularTargeting(this, true, false), new CircularTargeting(this, true, true) };

        robots = new RobotManager(this);
        targeting = new TargetingManager(this, targetings);
        team = new TeamManager(this);

        movement = new Perpendicular(this);

        radar = new RadarMovement(this);

        setTurnRadarRight(Double.POSITIVE_INFINITY);
        while (true) {
            EnemyData enemy = robots.getEnemy(RobotChooser.EASIEST);

            setTurnRadarRight(Utils.sign(radar.info.getTurnRemaining()) * Double.POSITIVE_INFINITY);
            if (getGunHeat() < .4 || robots.numAliveEnemies() == 1)
                radar.setSweep(enemy, Utils.EIGHTIETH_CIRCLE / 3);

            if (!enemy.isDead() && targeting.getBestGun(enemy) != null)
                targeting.fire(enemy, getFirePower(enemy));

            movement.move(robots.getRobots(), team.getTeammateBullets());

            targeting.broadcastBullets();
            team.broadcast(new RobotMessage(this));

            execute();
        }
    }

    public double getFirePower(final EnemyData enemy) {
        if (getEnergy() <= 1.0D)
            return 0.0D;
        double firePower = 900.0D / Utils.dist(getX(), getY(), enemy.getX(), enemy.getY());
        double hitRate = targeting.getBestGun(enemy).getHitRate();
        if (hitRate > 0.0D) {
            firePower *= 0.85D + hitRate;
        }
        if (getEnergy() < 20.0D) {
            if (enemy.getEnergy() > getEnergy())
                firePower /= 3.0D;
            else
                firePower /= 2.0D;
        }
        firePower = Math.min(firePower, enemy.getEnergy() / 4.0D);
        return Utils.limit(0.0D, firePower, Rules.MAX_BULLET_POWER);
    }

    @Override
    public void onPaint(final java.awt.Graphics2D graphics) {
        RGraphics grid = new RGraphics(graphics, this);
        DrawMenu.draw(grid);
        robots.draw(grid);
        targeting.draw(grid);
        team.draw(grid);
    }

    @Override
    public void onMessageReceived(final MessageEvent e) {
        team.inEvent(e);
        robots.inEvent(e);
    }

    @Override
    public void onScannedRobot(final ScannedRobotEvent e) {
        team.broadcast(new ScannedRobotMessage(e, this));
        robots.inEvent(e);
        targeting.inEvent(e);
        team.inEvent(e);
        movement.inEvent(e);
    }

    @Override
    public void onRobotDeath(final RobotDeathEvent e) {
        robots.inEvent(e);
        targeting.inEvent(e);
        movement.inEvent(e);
    }

    @Override
    public void onHitByBullet(final HitByBulletEvent e) {
    }

    @Override
    public void onBulletHit(final BulletHitEvent e) {
    }

    @Override
    public void onBulletHitBullet(final BulletHitBulletEvent e) {
    }

    @Override
    public void onWin(final WinEvent e) {
        robots.inEvent(e);
        targeting.inEvent(e);
    }

    @Override
    public void onDeath(final DeathEvent e) {
        robots.inEvent(e);
        targeting.inEvent(e);
    }

    @Override
    public void onMouseClicked(final MouseEvent e) {
        DrawMenu.inMouseEvent(e);
    }

    @Override
    public void onSkippedTurn(final SkippedTurnEvent e) {
        out.println("SKIPPED TURN! " + e.getTime());
    }

}