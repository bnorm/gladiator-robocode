package kid.Targeting.Log;

import java.awt.*;
import java.util.*;
import java.util.List;

import kid.*;
import kid.Data.PatternMatching.*;
import kid.Data.Robot.*;
import robocode.*;
import robocode.Robot;

public class PatternMatchingLatVel extends LogTargeting {

    private List Enemys = new ArrayList();
    protected long LastTime = -1;

    public PatternMatchingLatVel(Robot MyRobot) {
        super(MyRobot);
    }

    public PatternMatchingLatVel(AdvancedRobot MyRobot) {
        super(MyRobot);
    }

    public PatternMatchingLatVel(TeamRobot MyRobot) {
        super(MyRobot);
    }


    public double getTargetingAngle(EnemyData EnemyRobot, double FirePower) {
        double Angle = 0.0;
        if (EnemyRobot != null) {
            if (MyRobot.getTime() == LastTime) {
                for (int b = 0; b < Enemys.size(); b++) {
                    RobotFireAngle a = (RobotFireAngle) Enemys.get(b);
                    if (a.getEnemy() == EnemyRobot && a.getFirePower() == FirePower)
                        return a.getFireAngle();
                }
            } else {
                Enemys = new ArrayList();
                LastTime = MyRobot.getTime();
            }
            if (FirePower < 0.1)
                FirePower = 0.1;
            else if (FirePower > 3.0)
                FirePower = 3.0;
            double BulletVelocity = Utils.bulletVelocity(FirePower);
            double mX = MyRobot.getX(), mY = MyRobot.getY();
            double eX = EnemyRobot.getX(), eY = EnemyRobot.getY();
            Angle = Utils.relative(Utils.atan2(eX - mX, eY - mY));
            double dist = Utils.getDist(mX, mY, eX, eY);
            int t = 0;
            try {
                LatVelPattern p = EnemyRobot.getLatVelPattern();
                for (t = -1; Math.pow(t * BulletVelocity, 2) < Utils.getDistSq(mX, mY, eX, eY) && p != null; t++) {
                    Angle += Utils.atan(p.getLatVel() / dist);
                    p = p.getNext();
                }
            } catch (java.lang.NullPointerException e) {
                e.printStackTrace();
            } catch (java.lang.ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        } else {
            Angle = MyRobot.getGunHeading();
        }
        Enemys.add(new RobotFireAngle(EnemyRobot, FirePower, Angle, MyRobot.getTime()));
        return Angle;
    }

    public String getNameOfTargeting() {
        return "PatternMatchingLatVel";
    }

    public Color getTargetingColor() {
        return Colors.BROWN;
    }

    public void drawTargeting(RobocodeGraphicsDrawer g, EnemyData EnemyRobot, double FirePower) {
        try {
            g.setColor(Color.RED);
            if (EnemyRobot != null) {
                if (FirePower < 0.1)
                    FirePower = 0.1;
                else if (FirePower > 3.0)
                    FirePower = 3.0;
                double mX = MyRobot.getX(), mY = MyRobot.getY();
                double eX = EnemyRobot.getX(), eY = EnemyRobot.getY();
                double dist = Utils.getDist(mX, mY, eX, eY);
                double Angle = Utils.relative(Utils.atan2(eX - mX, eY - mY));
                double BulletVelocity = Utils.bulletVelocity(FirePower);
                int t = 0;
                LatVelPattern start = EnemyRobot.getLatVelPattern();
                LatVelPattern p = start;
                for (t = 0; Math.pow(t * BulletVelocity, 2) < Utils.getDistSq(mX, mY, eX, eY) && p.getNext() != null; t++) {
                    Angle += Utils.atan(p.getLatVel() / dist);
                    eX = Utils.getX(mX, dist, Angle);
                    eY = Utils.getY(mY, dist, Angle);
                    g.fillOval(eX, eY, 3, 3);
                    p = p.getNext();
                }
                Angle += Utils.atan(p.getLatVel() / dist);
                eX = Utils.getX(mX, dist, Angle);
                eY = Utils.getY(mY, dist, Angle);
                g.drawLine(mX, mY, eX, eY);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
