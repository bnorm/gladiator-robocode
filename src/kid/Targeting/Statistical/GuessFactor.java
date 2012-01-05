package kid.Targeting.Statistical;

import java.awt.*;

import kid.*;
import kid.Data.Robot.*;
import robocode.*;
import robocode.Robot;

public class GuessFactor extends StatisticalTargeting {

    public GuessFactor(Robot MyRobot) {
        super(MyRobot);
    }

    public GuessFactor(AdvancedRobot MyRobot) {
        super(MyRobot);
    }

    public GuessFactor(TeamRobot MyRobot) {
        super(MyRobot);
    }

    public double getTargetingAngle(EnemyData EnemyRobot, double FirePower) {
        if (EnemyRobot.isDead())
            return MyRobot.getGunHeading();
        else if (EnemyRobot.getEnergy() == 0.0)
            return i.AngleTo(EnemyRobot);
        int[] sectors = EnemyRobot.getSectors();
        int bestindex = (sectors.length - 1) / 2;
        int besthits = sectors[bestindex];
        for (int i = 0; i < sectors.length; i++) {
            int hits = sectors[i];
            if (hits > besthits) {
                bestindex = i;
                besthits = hits;
            }
        }
        double guessfactor = Utils.getGuessFactor(bestindex, sectors.length);
        double angleOffset = Utils.getAngleOffset(MyRobot, EnemyRobot, guessfactor, FirePower);
        angleOffset = Utils.rollingAvg(EnemyRobot.getAvgAngleOffSet(), angleOffset, EnemyData.AvgLength, FirePower);
        return Utils.relative(i.AngleTo(EnemyRobot) + angleOffset);
    }

    public String getNameOfTargeting() {
        return "GuessFactor";
    }

    public Color getTargetingColor() {
        return Color.ORANGE;
    }

    public void drawTargeting(RobocodeGraphicsDrawer g, EnemyData EnemyRobot, double FirePower) {
        g.setColor(getTargetingColor());
        //double angle = 0.0;
        //if (EnemyRobot.isDead())
            //angle = MyRobot.getGunHeading();
        //else if (EnemyRobot.getEnergy() == 0.0)
            //angle = i.AngleTo(EnemyRobot);
        int[] sectors = EnemyRobot.getSectors();
        int bestindex = (sectors.length - 1) / 2;
        int besthits = sectors[bestindex];
        for (int i = 0; i < sectors.length; i++) {
            int hits = sectors[i];
            if (hits > besthits) {
                bestindex = i;
                besthits = hits;
            }
        }
        double guessfactor = Utils.getGuessFactor(bestindex, sectors.length);
        double angleOffset = Utils.getAngleOffset(MyRobot, EnemyRobot, guessfactor, FirePower);
        //angle = Utils.rollingAvg(EnemyRobot.getAvgAngleOffSet(), angleOffset, EnemyData.AvgLength, FirePower);

        g.fillOval((int) Utils.getX(MyRobot.getX(), i.DistTo(EnemyRobot), Utils.relative(i.AngleTo(EnemyRobot) + angleOffset)),
                   (int) Utils.getY(MyRobot.getY(), i.DistTo(EnemyRobot), Utils.relative(i.AngleTo(EnemyRobot) + angleOffset)), 5, 5);

    }
}
