package kid.Movement.Melee;

import kid.Data.Robot.*;
import robocode.*;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;
import kid.Utils;
import kid.Data.Virtual.VirtualBullet;
import kid.RobocodeGraphicsDrawer;
import java.awt.Color;

public class MinimumRiskPoint extends MeleeMovement {

    private Point2D nextPosition;
    private RoundRectangle2D battleField;

    private int NUM_OF_GENERATED_POINTS = 36;
    private int CORNER_RISK = 2;
    private int BOT_RISK = 100;
    private int PERPEN_BOT_RISK = 1;

    public MinimumRiskPoint(Robot MyRobot) {
        super(MyRobot);
        nextPosition = new Point2D.Double(MyRobot.getX(), MyRobot.getY());
        battleField = new RoundRectangle2D.Double(30, 30, MyRobot.getBattleFieldWidth() - 60, MyRobot
                .getBattleFieldHeight() - 60, 200, 200);
    }

    public MinimumRiskPoint(AdvancedRobot MyRobot) {
        super(MyRobot);
        nextPosition = new Point2D.Double(MyRobot.getX(), MyRobot.getY());
        battleField = new RoundRectangle2D.Double(30, 30, MyRobot.getBattleFieldWidth() - 60, MyRobot
                .getBattleFieldHeight() - 60, 300, 300);
    }

    public MinimumRiskPoint(TeamRobot MyRobot) {
        super(MyRobot);
        nextPosition = new Point2D.Double(MyRobot.getX(), MyRobot.getY());
        battleField = new RoundRectangle2D.Double(30, 30, MyRobot.getBattleFieldWidth() - 60, MyRobot
                .getBattleFieldHeight() - 60, 200, 200);
    }


    public void doMovement(EnemyData[] EnemyData) {
        Robot.setMoveToPoint(getMinimumRiskPoint(EnemyData));
    }

    public void doMovement(EnemyData[] EnemyData, TeammateData[] TeammateData, VirtualBullet[] VirtualBullets) {
        Robot.setMoveToPoint(getMinimumRiskPoint(EnemyData, TeammateData, VirtualBullets));
    }


    public void inEvent(Event e) {
    }

    public void changeSettings(int BotRisk, int CornerRisk) {
        changeCornerRisk(BotRisk);
        changeBotRisk(CornerRisk);
    }
    public void changeCornerRisk(int CornerRisk) {
        CORNER_RISK = CornerRisk;
    }
    public void changeBotRisk(int BotRisk) {
        BOT_RISK = BotRisk;
    }


    public Point2D getMinimumRiskPoint(EnemyData[] EnemyData) {
        double mx = i.getX(), my = i.getY();
        double enemydist = Double.POSITIVE_INFINITY;
        for (int b = 0; b < EnemyData.length; b++) {
            EnemyData bot = EnemyData[b];
            if (bot.isDead())
                continue;
            double botdist = i.DistToSq(bot);
            if (botdist < enemydist) {
                enemydist = botdist;
            }
        }
        enemydist = Math.sqrt(enemydist);
        double nextdist = nextPosition.distanceSq(mx, my);

        Point2D point;
        double pointrisk = Double.POSITIVE_INFINITY;
        if (nextdist > 20 * 20 && enemydist > 200)
            pointrisk = getPointRisk(nextPosition, EnemyData);

        double dist = Utils.random(enemydist / 2, enemydist);
        for (double a = 0; a < 360; a += 360 / NUM_OF_GENERATED_POINTS) {
            double angle = Utils.random(a, a + 360 / NUM_OF_GENERATED_POINTS);
            point = new Point2D.Double(Utils.getX(mx, dist, angle), Utils.getY(my, dist, angle));
            if (battleField.contains(point)) {
                double risk = getPointRisk(point, EnemyData);
                if (risk < pointrisk) {
                    nextPosition = point;
                    pointrisk = risk;
                }
            }
        }
        return nextPosition;
    }
    public Point2D getMinimumRiskPoint(EnemyData[] EnemyData, TeammateData[] TeammateData,
            VirtualBullet[] VirtualBullets) {
        double mx = i.getX(), my = i.getY();
        double distto = Double.POSITIVE_INFINITY;
        for (int b = 0; b < EnemyData.length; b++) {
            EnemyData bot = EnemyData[b];
            if (bot.isDead())
                continue;
            double distToBot = i.DistToSq(bot);
            if (distToBot < distto) {
                distto = distToBot;
            }
        }
        for (int b = 0; b < TeammateData.length; b++) {
            TeammateData bot = TeammateData[b];
            if (bot.isDead())
                continue;
            double distToBot = i.DistToSq(bot);
            if (distToBot < distto) {
                distto = distToBot;
            }
        }
        if (VirtualBullets != null) {
            for (int b = 0; b < VirtualBullets.length; b++) {
                VirtualBullet vb = VirtualBullets[b];
                double distToBot = i.DistToSq(vb);
                if (distToBot < distto) {
                    distto = distToBot;
                }
            }
        }

        distto = Math.sqrt(distto);
        double nextdist = nextPosition.distanceSq(mx, my);

        Point2D point;
        double pointrisk = Double.POSITIVE_INFINITY;
        if (nextdist > 20 * 20 && distto > 200)
            pointrisk = getPointRisk(nextPosition, EnemyData);// * .9;

        double dist = Utils.random(distto / 2, distto);
        for (double a = 0; a < 360; a += 360 / NUM_OF_GENERATED_POINTS) {
            double angle = Utils.random(a, a + 360 / NUM_OF_GENERATED_POINTS);
            point = new Point2D.Double(Utils.getX(mx, dist, angle), Utils.getY(my, dist, angle));
            if (battleField.contains(point)) {
                double risk = getPointRisk(point, EnemyData, TeammateData, VirtualBullets);
                if (risk < pointrisk) {
                    nextPosition = point;
                    pointrisk = risk;
                }
            }
        }
        return nextPosition;
    }


    private double getPointRisk(Point2D p, EnemyData[] EnemyData) {
        double mx = i.getX(), my = i.getY();
        double angle = Utils.getAngle(mx, my, p.getX(), p.getY());
        double risk = 0.0;
        for (int b = 0; b < EnemyData.length; b++) {
            EnemyData Enemy = EnemyData[b];
            if (Enemy.isDead())
                continue;
            double botrisk = (BOT_RISK == 0 ? Enemy.getEnergy() : BOT_RISK);
            botrisk *= PERPEN_BOT_RISK
                    * (1 + Math.abs(Utils.cos(angle - Utils.getAngle(mx, my, Enemy.getX(), Enemy.getY()))));
            botrisk /= Utils.getDistSq(p, Enemy.getX(), Enemy.getY());
            risk += botrisk;
        }

        risk += i.getOthers() / p.distanceSq(i.getBattleFieldWidth() / 2, i.getBattleFieldHeight() / 2);

        risk += CORNER_RISK / Utils.getDistSq(p, i.getBattleFieldWidth(), i.getBattleFieldHeight());
        risk += CORNER_RISK / Utils.getDistSq(p, 0, i.getBattleFieldHeight());
        risk += CORNER_RISK / Utils.getDistSq(p, 0, 0);
        risk += CORNER_RISK / Utils.getDistSq(p, i.getBattleFieldWidth(), 0);

        // risk += CORNER_RISK / 2 / Utils.getDistSq(p, 0,
        // i.getBattleFieldHeight() / 2);
        // risk += CORNER_RISK / 2 / Utils.getDistSq(p, i.getBattleFieldWidth()
        // / 2, i.getBattleFieldHeight());
        // risk += CORNER_RISK / 2 / Utils.getDistSq(p, i.getBattleFieldWidth()
        // / 2, 0);
        // risk += CORNER_RISK / 2 / Utils.getDistSq(p, i.getBattleFieldWidth(),
        // i.getBattleFieldHeight() / 2);

        return risk;
    }

    private double getPointRisk(Point2D p, EnemyData[] EnemyData, TeammateData[] TeammateData,
            VirtualBullet[] VirtualBullets) {
        double mx = i.getX(), my = i.getY();
        double angle = Utils.getAngle(mx, my, p.getX(), p.getY());
        double risk = 0.0;
        for (int b = 0; b < EnemyData.length; b++) {
            EnemyData Enemy = EnemyData[b];
            if (!Enemy.isDead()) {
                double botrisk = (BOT_RISK == 0 ? Enemy.getEnergy() : BOT_RISK);
                botrisk *= PERPEN_BOT_RISK
                        * (1 + Math.abs(Utils.cos(angle - Utils.getAngle(mx, my, Enemy.getX(), Enemy.getY()))));
                botrisk /= Utils.getDistSq(p, Enemy.getX(), Enemy.getY());
                risk += botrisk;
            }
        }
        for (int b = 0; b < TeammateData.length; b++) {
            TeammateData Teammate = TeammateData[b];
            if (!Teammate.isDead()) {
                double botrisk = 50 / p.distanceSq(Teammate.getX(), Teammate.getY());
                risk += botrisk;
            }
        }

        if (VirtualBullets != null) {
            long t = i.getTime();
            for (int b = 0; b < VirtualBullets.length; b++) {
                VirtualBullet VirtualBullet = VirtualBullets[b];
                double botrisk = VirtualBullet.getFirePower() / p.distanceSq(VirtualBullet.getX(t), VirtualBullet.getY(t));
                risk += botrisk;
            }
        }

        risk += CORNER_RISK / Utils.getDistSq(p, i.getBattleFieldWidth(), i.getBattleFieldHeight());
        risk += CORNER_RISK / Utils.getDistSq(p, 0, i.getBattleFieldHeight());
        risk += CORNER_RISK / Utils.getDistSq(p, 0, 0);
        risk += CORNER_RISK / Utils.getDistSq(p, i.getBattleFieldWidth(), 0);

        return risk;
    }
    public void drawMovement(RobocodeGraphicsDrawer g, EnemyData[] EnemyData) {
        g.setColor(Color.GREEN);
        g.fillOval((int) nextPosition.getX(), (int) nextPosition.getY(), 5, 5);
    }

    public void drawMovement(RobocodeGraphicsDrawer g, EnemyData[] EnemyData, TeammateData[] TeammateData,
            VirtualBullet[] VirtualBullets) {

        g.setColor(Color.RED);
        if (EnemyData != null)
            for (int b = 0; b < EnemyData.length; b++) {
                EnemyData Enemy = EnemyData[b];
                if (!Enemy.isDead()) {
                    g.drawOvalCenter((int) Enemy.getX(), (int) Enemy.getY(), 36, 36);
                }
            }
        g.setColor(Color.YELLOW);
        if (TeammateData != null)
            for (int b = 0; b < TeammateData.length; b++) {
                TeammateData Teammate = TeammateData[b];
                if (!Teammate.isDead()) {
                    g.drawOvalCenter((int) Teammate.getX(), (int) Teammate.getY(), 36, 36);
                }
            }
        g.setColor(Color.YELLOW);
        long t = i.getTime();
        if (VirtualBullets != null)
            for (int b = 0; b < VirtualBullets.length; b++) {
                VirtualBullet VirtualBullet = VirtualBullets[b];
                if (VirtualBullet.testActive(t)) {
                    g.drawOvalCenter((int) VirtualBullet.getX(t), (int) VirtualBullet.getY(t), 10, 10);
                }
            }
        g.setColor(Color.GREEN);
        g.fillOval((int) nextPosition.getX(), (int) nextPosition.getY(), 5, 5);
    }

}