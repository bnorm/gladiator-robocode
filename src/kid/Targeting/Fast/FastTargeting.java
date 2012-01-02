package kid.Targeting.Fast;

import robocode.*;
import kid.Data.MyRobotsInfo;
import kid.Data.Robot.*;
import kid.Colors;
import kid.RobocodeGraphicsDrawer;
import kid.Utils;
import kid.Targeting.Targeting;
import java.util.*;

public abstract class FastTargeting implements Targeting {

    protected Robot MyRobot = null;

    protected List LastAngle = new ArrayList();
    protected long LastTime = -1;

    public FastTargeting(Robot MyRobot) {
        this.MyRobot = MyRobot;
    }

    public FastTargeting(AdvancedRobot MyRobot) {
        this((Robot) MyRobot);
    }

    public FastTargeting(TeamRobot MyRobot) {
        this((Robot) MyRobot);
    }

    public abstract double getTargetingAngle(EnemyData robot, double firePower);

    public abstract String getNameOfTargeting();

    public String getTypeOfTargeting() {
        return "FastTargeting";
    }


    protected double getAngle(EnemyData EnemyRobot, double firePower, double deltaHeading, double deltaVelocity) {
        if (MyRobot.getTime() == LastTime) {
            for (int b = 0; b < LastAngle.size(); b++) {
                RobotFireAngle a = (RobotFireAngle) LastAngle.get(b);
                if (a.getEnemy() == EnemyRobot && a.getFirePower() == firePower) {
                    return a.getFireAngle();
                }
            }
        } else {
            LastAngle = new ArrayList();
            LastTime = MyRobot.getTime();
        }
        if (EnemyRobot != null || !EnemyRobot.isDead()) {
            firePower = Math.min(MyRobot.getEnergy(), firePower);
            if (firePower < 0.1)
                firePower = 0.1;
            else if (firePower > 3.0)
                firePower = 3.0;
            double BulletVelocity = Utils.bulletVelocity(firePower);
            int MWD = (int) MyRobotsInfo.MIN_WALL_DIST;
            double mX = MyRobot.getX(), mY = MyRobot.getY();
            int BFH = (int) (MyRobot.getBattleFieldHeight() - MWD);
            int BFW = (int) (MyRobot.getBattleFieldWidth() - MWD);
            double eX = EnemyRobot.getX(), eY = EnemyRobot.getY(), eH = EnemyRobot.getHeading();
            for (int t = -1; Math.pow(t * BulletVelocity, 2) < Utils.getDistSq(mX, mY, eX, eY); t++) {
                eX += deltaVelocity * Utils.sin(eH);
                eY += deltaVelocity * Utils.cos(eH);
                eH += deltaHeading;
                if (eX < MWD || eY < MWD || eX > BFW || eY > BFH) {
                    eX -= deltaVelocity * Utils.sin(eH);
                    eY -= deltaVelocity * Utils.cos(eH);
                    if (deltaHeading == 0.0)
                        break;
                }
            }
            double angle = Utils.atan2(eX - mX, eY - mY);
            LastAngle.add(new RobotFireAngle(EnemyRobot, firePower, angle, MyRobot.getTime()));
            return angle;
        }
        double GunHeading = Utils.relative(MyRobot.getGunHeading() + MyRobot.getGunHeading());
        LastAngle.add(new RobotFireAngle(EnemyRobot, firePower, GunHeading, MyRobot.getTime()));
        return GunHeading;
    }

    protected void drawAngle(RobocodeGraphicsDrawer g, EnemyData EnemyRobot, double firePower, double deltaHeading,
            double deltaVelocity) {
        if (EnemyRobot != null || !EnemyRobot.isDead()) {
            g.setColor(Colors.RED);
            firePower = Math.min(MyRobot.getEnergy(), firePower);
            if (firePower < 0.1)
                firePower = 0.1;
            else if (firePower > 3.0)
                firePower = 3.0;
            double BulletVelocity = Utils.bulletVelocity(firePower);
            int MWD = (int) MyRobotsInfo.MIN_WALL_DIST;
            double mX = MyRobot.getX(), mY = MyRobot.getY();
            int BFH = (int) (MyRobot.getBattleFieldHeight() - MWD);
            int BFW = (int) (MyRobot.getBattleFieldWidth() - MWD);
            double eX = EnemyRobot.getX(), eY = EnemyRobot.getY(), eH = EnemyRobot.getHeading();
            for (int t = -1; Math.pow(t * BulletVelocity, 2) < Utils.getDistSq(mX, mY, eX, eY); t++) {
                eX += deltaVelocity * Utils.sin(eH);
                eY += deltaVelocity * Utils.cos(eH);
                eH += deltaHeading;
                if (eX < MWD || eY < MWD || eX > BFW || eY > BFH) {
                    eX -= deltaVelocity * Utils.sin(eH);
                    eY -= deltaVelocity * Utils.cos(eH);
                    if (deltaHeading == 0.0)
                        break;
                }
                g.fillOvalCenter(eX, eY, 2, 2);
            }
        }
    }

}
