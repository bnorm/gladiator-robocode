package kid.Movement.Melee;

import kid.Data.Gravity.*;
import kid.Data.Robot.*;
import kid.Data.Virtual.*;
import kid.Graphics.*;
import robocode.*;
import kid.RobocodeGraphicsDrawer;
import java.awt.Color;

public class AntiGravity extends MeleeMovement {

    protected GravityEngine GravityEngine;
    protected boolean HeadOnAvodence = false;

    public AntiGravity(Robot MyRobot) {
        super(MyRobot);
        GravityEngine = new GravityEngine(MyRobot.getBattleFieldWidth(), MyRobot.getBattleFieldHeight());
        GravityEngine.addPoint(new GravityPoint(0, 0, -100));
        GravityEngine.addPoint(new GravityPoint(0, MyRobot.getBattleFieldHeight(), -100));
        GravityEngine.addPoint(new GravityPoint(MyRobot.getBattleFieldWidth(), 0, -100));
        GravityEngine.addPoint(new GravityPoint(MyRobot.getBattleFieldWidth(), MyRobot.getBattleFieldHeight(), -100));
    }

    public AntiGravity(AdvancedRobot MyRobot) {
        super(MyRobot);
        GravityEngine = new GravityEngine(MyRobot.getBattleFieldWidth(), MyRobot.getBattleFieldHeight());
        GravityEngine.addPoint(new GravityPoint(0, 0, -100));
        GravityEngine.addPoint(new GravityPoint(0, MyRobot.getBattleFieldHeight(), -100));
        GravityEngine.addPoint(new GravityPoint(MyRobot.getBattleFieldWidth(), 0, -100));
        GravityEngine.addPoint(new GravityPoint(MyRobot.getBattleFieldWidth(), MyRobot.getBattleFieldHeight(), -100));
    }

    public AntiGravity(TeamRobot MyRobot) {
        this((AdvancedRobot) MyRobot);
    }

    /*
        public AntiGravity addHeadOnAvodence() {
            HeadOnAvodence = true;
            return this;
        }
     */

    public void doMovement(EnemyData[] EnemyData) {
        for (int e = 0; e < EnemyData.length; e++) {
            EnemyData ebot = EnemyData[e];
            GravityEngine.addPoint(new GravityPoint(ebot.getX(), ebot.getY(), -ebot.getEnergy(), MyRobot.getTime(), 1));
        }
        GravityEngine.update(MyRobot);
        double gx = GravityEngine.getXForce() + i.getX(), gy = GravityEngine.getYForce() + i.getY();
        if (MyRobot instanceof AdvancedRobot) {
            //if (MyRobot instanceof AdvancedRobot)
            //  ((AdvancedRobot) MyRobot).setMaxVelocity(i.getVelocity(i.RobotBearingTo(gx, gy)));
            Robot.setAhead(Double.POSITIVE_INFINITY * Robot.setTurnToXYwBF(gx, gy));
        } else {
        }
    }


    public void doMovement(EnemyData[] EnemyData, TeammateData[] TeammateData, VirtualBullet[] VirtualBullets) {
        for (int e = 0; e < EnemyData.length; e++) {
            EnemyData ebot = EnemyData[e];
            GravityEngine.addPoint(new GravityPoint(ebot.getX(), ebot.getY(), -ebot.getEnergy(), MyRobot.getTime(), 1));
        }
        for (int t = 0; t < TeammateData.length; t++) {
            TeammateData tbot = TeammateData[t];
            GravityEngine.addPoint(new GravityPoint(tbot.getX(), tbot.getY(), -tbot.getEnergy() / 2, MyRobot.getTime(),
                    1));
        }
        long t = i.getTime();
        for (int b = 0; b < VirtualBullets.length; b++) {
            VirtualBullet vbullet = VirtualBullets[b];
            GravityEngine.addPoint(new GravityPoint(vbullet.getX(t), vbullet.getY(t), -vbullet.getFirePower() * 33,
                    MyRobot.getTime(), 1));
        }

        GravityEngine.update(MyRobot);
        double gx = GravityEngine.getXForce() + i.getX(), gy = GravityEngine.getYForce() + i.getY();

        if (MyRobot instanceof AdvancedRobot) {
            Robot.setAhead(Double.POSITIVE_INFINITY * Robot.setTurnToXYwBF(gx, gy));
        } else {
        }
    }


    public void inEvent(Event e) {}

    public void drawMovement(RobocodeGraphicsDrawer g, EnemyData[] EnemyData) {
        g.setColor(Color.RED);
        for (int e = 0; e < EnemyData.length; e++) {
            EnemyData ebot = EnemyData[e];
            g.drawOval((int) ebot.getX(), (int) ebot.getY(), (int) ebot.getEnergy() * 2, (int) ebot.getEnergy() * 2);
        }
    }

    public void drawMovement(RobocodeGraphicsDrawer g, EnemyData[] EnemyData, TeammateData[] TeammateData,
                             VirtualBullet[] VirtualBullets) {
        g.setColor(Color.GREEN);
        for (int e = 0; e < EnemyData.length; e++) {
            EnemyData ebot = EnemyData[e];
            g.drawOval((int) ebot.getX(), (int) ebot.getY(), (int) ebot.getEnergy() * 2, (int) ebot.getEnergy() * 2);
        }
        for (int t = 0; t < TeammateData.length; t++) {
            TeammateData tbot = TeammateData[t];
            g.drawOval((int) tbot.getX(), (int) tbot.getY(), (int) tbot.getEnergy(), (int) tbot.getEnergy());
        }
        long t = i.getTime();
        for (int b = 0; b < VirtualBullets.length; b++) {
            VirtualBullet vbullet = VirtualBullets[b];
            g.drawOval((int) vbullet.getX(t), (int) vbullet.getY(t), (int) - vbullet.getFirePower() * 66,
                       (int) - vbullet.getFirePower() * 66);
        }
    }

}
