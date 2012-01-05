package kid.Movement.Melee;

import kid.Data.Robot.*;
import kid.Movement.Melee.*;
import robocode.*;
import java.awt.geom.Point2D;
import kid.Utils;
import java.awt.geom.Rectangle2D;
import kid.Data.Virtual.VirtualBullet;
import kid.RobocodeGraphicsDrawer;
import java.awt.Color;

public class MinimumRiskPoint extends MeleeMovement {

    Point2D nextPosition;
    Point2D lastPosition;
    Rectangle2D.Double battleField;

    public MinimumRiskPoint(Robot MyRobot) {
        super(MyRobot);
        nextPosition = new Point2D.Double(MyRobot.getX(), MyRobot.getY());
        lastPosition = new Point2D.Double(MyRobot.getX(), MyRobot.getY());
        battleField = new Rectangle2D.Double(30, 30, MyRobot.getBattleFieldWidth() - 60,
                                             MyRobot.getBattleFieldHeight() - 60);
    }

    public MinimumRiskPoint(AdvancedRobot MyRobot) {
        super(MyRobot);
        nextPosition = new Point2D.Double(MyRobot.getX(), MyRobot.getY());
        lastPosition = new Point2D.Double(MyRobot.getX(), MyRobot.getY());
        battleField = new Rectangle2D.Double(30, 30, MyRobot.getBattleFieldWidth() - 60,
                                             MyRobot.getBattleFieldHeight() - 60);
    }

    public MinimumRiskPoint(TeamRobot MyRobot) {
        super(MyRobot);
        nextPosition = new Point2D.Double(MyRobot.getX(), MyRobot.getY());
        lastPosition = new Point2D.Double(MyRobot.getX(), MyRobot.getY());
        battleField = new Rectangle2D.Double(30, 30, MyRobot.getBattleFieldWidth() - 60,
                                             MyRobot.getBattleFieldHeight() - 60);
    }

    public void doMovement(EnemyData[] EnemyData) {
        Robot.setMoveToPoint(getMinimumRiskPoint(EnemyData));
    }

    public void doMovement(EnemyData[] EnemyData, TeammateData[] TeammateData, VirtualBullet[] VirtualBullets) {
        Robot.setMoveToPoint(getMinimumRiskPoint(EnemyData, TeammateData, VirtualBullets));
    }


    public void inEvent(Event e) {}

    public Point2D getMinimumRiskPoint(EnemyData[] EnemyData) {
        if (i.getTime() % 15 == 0) { //distanceToNextDestinationSq < 20 * 20 || disttoenemy < 200) {
            double myX = MyRobot.getX();
            double myY = MyRobot.getY();

            EnemyData closestenemy = new EnemyData();
            double disttoenemy = Double.POSITIVE_INFINITY;
            for (int b = 0; b < EnemyData.length; b++) {
                EnemyData bot = EnemyData[b];
                if (bot.isDead())
                    continue;
                double distToBot = i.DistToSq(bot);
                if (distToBot < disttoenemy) {
                    disttoenemy = distToBot;
                    closestenemy = bot;
                }
            }

            //disttoenemy = Math.sqrt(disttoenemy);
            //double distanceToNextDestinationSq = nextPosition.distanceSq(myX, myY);
            Point2D.Double testPoint;
            double evaluate = Double.POSITIVE_INFINITY;

            double avgangle = 0.0; int ae = 0;
            for (int e = 0; e < EnemyData.length; e++) {
                if (EnemyData[e].isDead())
                    continue;
                avgangle += i.AngleTo(EnemyData[e]);
                ae++;
            }
            avgangle /= ae;
            avgangle += i.AngleTo(closestenemy);
            avgangle /= 2;
            avgangle += 90;

            for (int p = 0; p < 200; p++) {
                double dist = 100 + 200 * Math.random();

                double angle;
                if (Math.random() >= 0.5)
                    angle = Utils.random(avgangle - (45 * Math.random()), avgangle + (45 * Math.random())); //360.0 * Math.random();
                else
                    angle = Utils.oppositeRelative(Utils.random(avgangle - (5 * Math.random()),
                            avgangle + (5 * Math.random())));

                testPoint = new Point2D.Double(Utils.getX(myX, dist, angle), Utils.getY(myY, dist, angle));
                double evaluateOfTestPoint = getPointRisk(testPoint, EnemyData);
                if (battleField.contains(testPoint) && evaluateOfTestPoint < evaluate) {
                    nextPosition = testPoint;
                    evaluate = evaluateOfTestPoint;
                }
            }
            lastPosition = new Point2D.Double(myX, myY);
        }
        return nextPosition;
    }

    public Point2D getMinimumRiskPoint(EnemyData[] EnemyData, TeammateData[] TeammateData,
                                       VirtualBullet[] VirtualBullets) {
        double myX = MyRobot.getX();
        double myY = MyRobot.getY();

        RobotData closest = new EnemyData();
        double distto = Double.POSITIVE_INFINITY;
        for (int b = 0; b < EnemyData.length; b++) {
            EnemyData bot = EnemyData[b];
            if (bot.isDead())
                continue;
            double distToBot = i.DistToSq(bot);
            if (distToBot < distto) {
                distto = distToBot;
                closest = bot;
            }
        }
        for (int b = 0; b < TeammateData.length; b++) {
            TeammateData bot = TeammateData[b];
            if (bot.isDead())
                continue;
            double distToBot = i.DistToSq(bot);
            if (distToBot < distto) {
                distto = distToBot;
                closest = bot;
            }
        }
        if (VirtualBullets != null) {
            for (int b = 0; b < VirtualBullets.length; b++) {
                VirtualBullet vb = VirtualBullets[b];
                if (vb.testActive(i.getTime()))
                    continue;
                double distToBot = i.DistToSq(vb);
                if (distToBot < distto) {
                    distto = distToBot;
                }
            }
        }

        distto = Math.sqrt(distto);
        double distanceToNextDestinationSq = nextPosition.distanceSq(myX, myY);
        if (distanceToNextDestinationSq < 20 * 20 || distto < 200) {
            Point2D.Double testPoint;
            double evaluate = Double.POSITIVE_INFINITY;

            double avgangle = 0.0;
            avgangle = i.AngleTo(closest) + 90;

            for (int p = 0; p < 200; p++) {
                double dist = Math.min(100 + 200 * Math.random(), distto);

                double angle;
                if (Math.random() >= 0.5)
                    angle = Utils.random(avgangle - (45 * Math.random()), avgangle + (45 * Math.random())); //360.0 * Math.random();
                else
                    angle = Utils.oppositeRelative(Utils.random(avgangle - (5 * Math.random()),
                            avgangle + (5 * Math.random())));

                testPoint = new Point2D.Double(Utils.getX(myX, dist, angle), Utils.getY(myY, dist, angle));
                double evaluateOfTestPoint = getPointRisk(testPoint, EnemyData, TeammateData, VirtualBullets);
                if (battleField.contains(testPoint) && evaluateOfTestPoint < evaluate) {
                    nextPosition = testPoint;
                    evaluate = evaluateOfTestPoint;
                }
            }
            lastPosition = new Point2D.Double(myX, myY);
        }
        return nextPosition;

    }

    private double getPointRisk(Point2D p, EnemyData[] EnemyData) {
        double risk = 0.0;
        for (int b = 0; b < EnemyData.length; b++) {
            EnemyData Enemy = EnemyData[b];
            if (!Enemy.isDead())
                risk += Enemy.getEnergy() / p.distanceSq(Enemy.getX(), Enemy.getY());
        }

        return risk;
    }

    private double getPointRisk(Point2D p, EnemyData[] EnemyData, TeammateData[] TeammateData,
                                VirtualBullet[] VirtualBullets) {
        double risk = 0.0;
        for (int b = 0; b < EnemyData.length; b++) {
            EnemyData Enemy = EnemyData[b];
            if (!Enemy.isDead())
                risk += Enemy.getEnergy() / p.distanceSq(Enemy.getX(), Enemy.getY());
        }
        for (int b = 0; b < TeammateData.length; b++) {
            TeammateData Teammate = TeammateData[b];
            if (!Teammate.isDead())
                risk += Teammate.getEnergy() / p.distanceSq(Teammate.getX(), Teammate.getY());
        }
        if (VirtualBullets != null) {
            for (int b = 0; b < VirtualBullets.length; b++) {
                VirtualBullet VirtualBullet = VirtualBullets[b];
                if (!VirtualBullet.testActive(i.getTime()))
                    risk += VirtualBullet.getFirePower() * (100D / 3D) /
                            p.distanceSq(VirtualBullet.getX(i.getTime()), VirtualBullet.getY((i.getTime())));
            }
        }
        return risk;
    }

    public void drawMovement(RobocodeGraphicsDrawer g, EnemyData[] EnemyData) {
        g.setColor(Color.GREEN);
        getMinimumRiskPoint(EnemyData);
        g.fillOval((int) nextPosition.getX(), (int) nextPosition.getY(), 5, 5);
    }

    public void drawMovement(RobocodeGraphicsDrawer g, EnemyData[] EnemyData, TeammateData[] TeammateData,
                             VirtualBullet[] VirtualBullets) {
        g.setColor(Color.GREEN);
        getMinimumRiskPoint(EnemyData, TeammateData, VirtualBullets);
        g.fillOval((int) nextPosition.getX(), (int) nextPosition.getY(), 5, 5);
    }


}
