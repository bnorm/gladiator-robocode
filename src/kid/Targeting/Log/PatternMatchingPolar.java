package kid.Targeting.Log;

import java.awt.*;
import java.util.*;
import java.util.List;

import kid.*;
import kid.Data.PatternMatching.*;
import kid.Data.Robot.*;
import robocode.*;
import robocode.Robot;

public class PatternMatchingPolar extends LogTargeting {

    private List Enemys = new ArrayList();
    protected long LastTime = -1;

    public PatternMatchingPolar(Robot MyRobot) {
        super(MyRobot);
    }

    public PatternMatchingPolar(AdvancedRobot MyRobot) {
        super(MyRobot);
    }

    public PatternMatchingPolar(TeamRobot MyRobot) {
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
            double battleFieldHeight = MyRobot.getBattleFieldHeight() - 18.0, battleFieldWidth = MyRobot
                    .getBattleFieldWidth() - 18.0;
            double eX = EnemyRobot.getX(), eY = EnemyRobot.getY(), eH = EnemyRobot.getHeading();
            int t = 0;
            try {
                PolarPattern start = EnemyRobot.getPolarPattern();
                PolarPattern p = start;
                for (t = -1; Math.pow(t * BulletVelocity, 2) < Utils.getDistSq(mX, mY, eX, eY) && p != null; t++) {
                    // MyRobot.out.println("Start Update XYH");
                    eX += p.getVelocity() * Utils.sin(eH);
                    eY += p.getVelocity() * Utils.cos(eH);
                    eH += p.getHeadingChange();
                    if (eX < 18.0 || eY < 18.0 || eX > battleFieldWidth || eY > battleFieldHeight) {
                        eX = Math.min(Math.max(18.0, eX), battleFieldWidth);
                        eY = Math.min(Math.max(18.0, eY), battleFieldHeight);
                        break;
                    }
                    p = p.getNext();
                }

                Angle = Utils.relative(Utils.atan2(eX - mX, eY - mY));
            } catch (java.lang.NullPointerException e) {
                e.printStackTrace();
                Angle = Utils.relative(Utils.atan2(eX - mX, eY - mY));
            } catch (java.lang.ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
                Angle = Utils.relative(Utils.atan2(eX - mX, eY - mY));
            }
        } else {
            Angle = MyRobot.getGunHeading();
        }
        Enemys.add(new RobotFireAngle(EnemyRobot, FirePower, Angle, MyRobot.getTime()));
        return Angle;
    }

    public String getNameOfTargeting() {
        return "PatternMatchingTargeting";
    }

    public Color getTargetingColor() {
        return Colors.PURPLE;
    }

    public void drawTargeting(RobocodeGraphicsDrawer g, EnemyData EnemyRobot, double FirePower) {
        g.setColor(Color.RED);
        if (EnemyRobot != null) {
            if (FirePower < 0.1)
                FirePower = 0.1;
            else if (FirePower > 3.0)
                FirePower = 3.0;
            double BulletVelocity = Utils.bulletVelocity(FirePower);
            double mX = MyRobot.getX(), mY = MyRobot.getY();
            double battleFieldHeight = MyRobot.getBattleFieldHeight() - 18.0, battleFieldWidth = MyRobot
                    .getBattleFieldWidth() - 18.0;
            double eX = EnemyRobot.getX(), eY = EnemyRobot.getY(), eH = EnemyRobot.getHeading();
            int t = 0;
            try {

                PolarPattern start = EnemyRobot.getPolarPattern();
                PolarPattern p = start;
                for (t = 0; Math.pow(t * BulletVelocity, 2) < Utils.getDistSq(mX, mY, eX, eY) && p != null; t++) {
                    eX += p.getVelocity() * Utils.sin(eH);
                    eY += p.getVelocity() * Utils.cos(eH);
                    eH += p.getHeadingChange();
                    if (eX < 18.0 || eY < 18.0 || eX > battleFieldWidth || eY > battleFieldHeight) {
                        eX = Math.min(Math.max(18.0, eX), battleFieldWidth);
                        eY = Math.min(Math.max(18.0, eY), battleFieldHeight);
                        break;
                    }
                    g.drawOval((int) eX, (int) eY, 2, 2);
                    p = p.getNext();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
