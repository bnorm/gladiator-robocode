package kid.Data.Virtual;

import kid.Data.Robot.*;
import kid.Targeting.*;
import kid.Targeting.Fast.*;
import robocode.*;
import java.util.*;
import kid.RobocodeGraphicsDrawer;

public class VirtualGun implements java.io.Serializable {

    private transient AdvancedRobot MyRobot;
    private transient Targeting Targeting;
    private transient EnemyData Target;

    private transient List Bullets = new ArrayList();
    private short FIRED = 0;
    private short HIT = 0;

    public VirtualGun(AdvancedRobot MyRobot) {
        this(MyRobot, new EnemyData(), new HeadOn(MyRobot));
    }

    public VirtualGun(AdvancedRobot MyRobot, EnemyData Target, Targeting Targeting) {
        this.MyRobot = MyRobot;
        this.Target = Target;
        this.Targeting = Targeting;
        MyRobot.addCustomEvent(new BulletWatcher());
    }

    public VirtualGun(TeamRobot MyRobot, EnemyData Target, Targeting Targeting) {
        this((AdvancedRobot) MyRobot, Target, Targeting);
    }

    public void updateVirtualGun(AdvancedRobot MyRobot, EnemyData Enemy, Targeting Targeting) {
        this.MyRobot = MyRobot;
        this.Targeting = Targeting;
        Target = Enemy;
        if (!this.Targeting.getNameOfTargeting().equals(Targeting.getNameOfTargeting())) {
            FIRED = 0;
            HIT = 0;
        }
        Bullets = new ArrayList();
        MyRobot.addCustomEvent(new BulletWatcher());
    }

    public void updateVirtualGun(TeamRobot MyRobot, EnemyData Enemy, Targeting Targeting) {
        updateVirtualGun(MyRobot, Enemy, Targeting);
    }


    public void fireVirtualBullet(double firePower) {
        Bullets.add(new VirtualBullet(Target, MyRobot.getX(), MyRobot.getY(),
                                      Targeting.getTargetingAngle(Target, firePower), firePower, MyRobot.getTime()));
    }


    public double getHitRate() {
        if (FIRED > 10000) {
            FIRED /= 2;
            HIT /= 2;
        }
        double HitRate = HIT / (double) FIRED;
        if (HitRate != HitRate) {
            HitRate = .001;
        }
        return HitRate;
    }

    public Targeting getTargeting() {
        return Targeting;
    }

    public EnemyData getTarget() {
        return Target;
    }

    public void drawBullets(RobocodeGraphicsDrawer g) {
        long t = MyRobot.getTime();
        for (int b = 0; b < Bullets.size(); b++) {
            g.setColor(Targeting.getTargetingColor());
            VirtualBullet bullet = (VirtualBullet) Bullets.get(b);
            g.fillOval((int) bullet.getX(t), (int) bullet.getY(t), (int) 2, (int) 2);
        }
    }

    public void printStatus() {
        System.out.println("    " + Targeting.getNameOfTargeting() + " Type: " + Targeting.getTypeOfTargeting());
        System.out.println("        Hit Rate: " + getHitRate() + "=" + HIT + "/" + FIRED);
    }

    private class BulletWatcher extends Condition {
        public boolean test() {
            long t = MyRobot.getTime();
            for (int b = 0; b < Bullets.size(); b++) {
                VirtualBullet bullet = (VirtualBullet) Bullets.get(b);
                if (bullet.testHit(t)) {
                    FIRED++;
                    HIT++;
                    Bullets.remove(bullet);
                    b--;
                } else if (bullet.testMissed(t)) {
                    FIRED++;
                    Bullets.remove(bullet);
                    b--;
                }
            }
            return false;
        }
    }

}
