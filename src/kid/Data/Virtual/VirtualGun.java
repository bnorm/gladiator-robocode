package kid.Data.Virtual;

import kid.Data.Robot.*;
import kid.Targeting.*;
import kid.Targeting.Fast.*;
import robocode.*;
import java.util.*;
import kid.RobocodeGraphicsDrawer;

public class VirtualGun implements java.io.Serializable {

    private static final long serialVersionUID = 1715236387787837088L;
    
    private transient AdvancedRobot MyRobot;
    private transient Targeting Targeting;
    private transient EnemyData Target;

    private transient List Bullets = new ArrayList();
    private short FIRED = 0;
    private short HIT = 0;

    private int LAST_BULLETS_LENGHT = 20;
    private int[] LAST_BULLETS;
    private int LAST_BULLETS_POSITION;

    public VirtualGun(AdvancedRobot MyRobot) {
        this(MyRobot, new EnemyData(), new HeadOn(MyRobot));
    }

    public VirtualGun(AdvancedRobot MyRobot, EnemyData Target, Targeting Targeting) {
        this.MyRobot = MyRobot;
        this.Target = Target;
        this.Targeting = Targeting;
        MyRobot.addCustomEvent(new BulletWatcher());
        FIRED = 0;
        HIT = 0;
        LAST_BULLETS = new int[LAST_BULLETS_LENGHT];
        for (int b = 0; b < LAST_BULLETS.length; b++)
            LAST_BULLETS[b] = 1;
        LAST_BULLETS_POSITION = 0;
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
            LAST_BULLETS = new int[LAST_BULLETS_LENGHT];
            for (int b = 0; b < LAST_BULLETS.length; b++)
                LAST_BULLETS[b] = 1;
            LAST_BULLETS_POSITION = 0;
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

    public double getRollingRate() {
        double RollingRate = 0;
        for (int b = 0; b < LAST_BULLETS.length; b++)
            RollingRate += LAST_BULLETS[b];
        return RollingRate / LAST_BULLETS.length;
    }


    public Targeting getTargeting() {
        return Targeting;
    }

    public EnemyData getTarget() {
        return Target;
    }

    public void drawBullets(RobocodeGraphicsDrawer g) {
        long t = MyRobot.getTime();
        g.setColor(Targeting.getTargetingColor());
        for (int b = 0; b < Bullets.size(); b++) {
            VirtualBullet bullet = (VirtualBullet) Bullets.get(b);
            g.drawLine(bullet.getX(t - 1), bullet.getY(t - 1), bullet.getX(t), bullet.getY(t));
        }
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

                    LAST_BULLETS[LAST_BULLETS_POSITION % LAST_BULLETS.length] = 1;
                    LAST_BULLETS_POSITION++;
                    if (LAST_BULLETS_POSITION > LAST_BULLETS.length * 2)
                        LAST_BULLETS_POSITION -= LAST_BULLETS.length;

                    b--;
                } else if (bullet.testMissed(t)) {
                    FIRED++;
                    Bullets.remove(bullet);

                    LAST_BULLETS[LAST_BULLETS_POSITION % LAST_BULLETS.length] = 0;
                    LAST_BULLETS_POSITION++;
                    if (LAST_BULLETS_POSITION > LAST_BULLETS.length * 2)
                        LAST_BULLETS_POSITION -= LAST_BULLETS.length;

                    b--;
                }
            }
            return false;
        }
    }

}
