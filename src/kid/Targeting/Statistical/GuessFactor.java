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
        double[] sectors = EnemyRobot.getSectors();
        int bestindex = (sectors.length - 1) / 2;
        double besthits = sectors[bestindex];
        for (int i = 0; i < sectors.length; i++) {
            double hits = sectors[i];
            if (hits > besthits) {
                bestindex = i;
                besthits = hits;
            }
        }
        double guessfactor = Utils.getGuessFactor(bestindex, sectors.length);
        double angleOffset = Utils.getAngleOffset(MyRobot, EnemyRobot, guessfactor, FirePower);
        angleOffset = Utils.rollingAvg(EnemyRobot.getAvgAngleOffSet(FirePower), angleOffset, 1, FirePower);
        // if (MyRobot.getGunHeat() == 0.0 && MyRobot.getEnergy() > 0) {
        // EnemyRobot.printObservation();
        // System.out.println();
        // }
        return Utils.relative(i.AngleTo(EnemyRobot) + angleOffset);
    }

    public String getNameOfTargeting() {
        return "GuessFactor";
    }

    public Color getTargetingColor() {
        return Color.ORANGE;
    }

    public void drawTargeting(RobocodeGraphicsDrawer g, EnemyData EnemyRobot, double FirePower) {
        if (EnemyRobot.isDead())
            return;
        else if (EnemyRobot.getEnergy() == 0.0)
            return;
        double angle = i.AngleTo(EnemyRobot);
        double dist = i.DistTo(EnemyRobot);

        double[] sectors = EnemyRobot.getSectors();
        int bestindex = (sectors.length - 1) / 2;
        double besthits = sectors[bestindex];
        for (int i = 0; i < sectors.length; i++) {
            double hits = sectors[i];
            if (hits > besthits) {
                bestindex = i;
                besthits = hits;
            }
        }
        double guessfactor = Utils.getGuessFactor(bestindex, sectors.length);
        double angleOffset = Utils.getAngleOffset(MyRobot, EnemyRobot, guessfactor, FirePower);
        double rollingangleOffset = Utils.rollingAvg(EnemyRobot.getAvgAngleOffSet(FirePower), angleOffset,
                EnemyData.AvgLength, FirePower);

        g.setColor(Colors.GREEN);
        g.drawLine(i.getX(), i.getY(), Utils.getX(i.getX(), dist, angle + angleOffset), Utils.getY(i.getY(), dist,
                angle + angleOffset));
        g.setColor(Colors.RED);
        g.drawLine(i.getX(), i.getY(), Utils.getX(i.getX(), dist, angle + rollingangleOffset), Utils.getY(i.getY(),
                dist, angle + rollingangleOffset));
        g.setColor(Colors.BLUE);
        g.drawLine(i.getX(), i.getY(), Utils.getX(i.getX(), dist, angle + EnemyRobot.getAvgAngleOffSet(FirePower)),
                Utils.getY(i.getY(), dist, angle + EnemyRobot.getAvgAngleOffSet(FirePower)));

    }
}
