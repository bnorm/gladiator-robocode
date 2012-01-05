package kid.Movement.OneOnOne;

import kid.*;
import kid.Data.*;
import kid.Data.Robot.*;
import kid.Data.Virtual.*;
import robocode.*;
import java.awt.geom.Rectangle2D;

public class TrueSurf extends WaveSurfing {

    protected double wearydist = 200;

    public TrueSurf(AdvancedRobot MyRobot, MovementProfile f) {
        super(MyRobot, f);
    }

    public TrueSurf(TeamRobot MyRobot, MovementProfile f) {
        super(MyRobot, f);
    }

    public void doMovement(EnemyData EnemyData) {
        if (i.getOthers() > 1)
            return;
        double mx = i.getX(), my = i.getY();
        long time = i.getTime();

        // double angle = Utils.getAngle(EnemyData.getX(), EnemyData.getY(), mx,
        // my);
        // int CurD = (Utils.sin(MyRobot.getHeading() - angle) *
        // MyRobot.getVelocity() < 0) ? -1 : 1;
        if (MyRobot instanceof AdvancedRobot) {
            if (EnemyData != null) {
                EnemyWave[] HighRiskWaves = f.getMaxRiskWaves();
                if (HighRiskWaves == null || f.getMaxRiskWave() == null || HighRiskWaves.length == 0) {
                    if (i.DistTo(EnemyData) < 300) {
                        Movement.setAhead(100 * Movement.setTurnPerpenToXYwBFwDC(EnemyData.getX(), EnemyData.getY(),
                                SafeDist(SafeDist, EnemyData)));
                    }
                } else {

                    double riskf = 0.0;
                    double riskb = 0.0;

                    int WantD = (i.getRobotMovingSign() == 0 ? 1 : i.getRobotMovingSign());

                    long maxtime = 1;
                    for (int w = 0; w < HighRiskWaves.length; w++)
                        maxtime = Math.max(maxtime, HighRiskWaves[w].timeToImpact(MyRobot));

                    RobotVector[] forwards = new RobotVector[(int) maxtime];
                    RobotVector[] backwards = new RobotVector[(int) maxtime];

                    double heading = i.getRobotFrontHeading();
                    double startvelocity = i.getVelocity() == 0 ? 1 : i.getVelocity();
                    double velocity = startvelocity;

                    forwards[0] = new RobotVector(Utils.getX(mx, velocity, heading), Utils.getY(my, velocity, heading),
                            heading, velocity);
                    backwards[0] = new RobotVector(Utils.getX(mx, i.getFutureVelocity(Utils.sign(startvelocity) * -1
                            * MyRobotsInfo.MAX_VELOCITY), heading), Utils.getY(my, i.getFutureVelocity(Utils
                            .sign(startvelocity)
                            * -1 * MyRobotsInfo.MAX_VELOCITY), heading), heading, velocity);

                    for (int t = 1; t < maxtime; t++) {
                        RobotVector v = forwards[t - 1].getNextVector(0, Utils.sign(startvelocity)
                                * MyRobotsInfo.MAX_VELOCITY);
                        Movement.ajustVectorForWall(v);
                        forwards[t] = v;
                    }
                    velocity = i.getVelocity();
                    for (int t = 1; t < maxtime; t++) {
                        RobotVector v = backwards[t - 1].getNextVector(0, Utils.sign(startvelocity) * -1
                                * MyRobotsInfo.MAX_VELOCITY);
                        Movement.ajustVectorForWall(v);
                        backwards[t] = v;
                    }

                    for (int w = 0; w < HighRiskWaves.length; w++) {
                        EnemyWave wave = HighRiskWaves[w];

                        int[] sectors = wave.getMyRobotsSectors();
                        int midsec = ((sectors.length - 1) / 2);

                        { // HeadOn avodence.
                            int waverisk = (int) ((wearydist / (wave.distToImpact(MyRobot) - MyRobotsInfo.WIDTH)) * 2);
                            waverisk = (waverisk < 0 ? 0 : waverisk);
                            int maxrisk = 0;
                            for (int i = 0; i < sectors.length; i++)
                                maxrisk = Math.max(maxrisk, sectors[i]);
                            sectors[midsec] = maxrisk + waverisk;
                            sectors[midsec - 1] = maxrisk + waverisk;
                        }

                        double wriskf = 0.0;
                        double wriskb = 0.0;

                        int ft = 0;
                        for (; ft < backwards.length; ft++) {
                            double x = forwards[ft].getX(), y = forwards[ft].getY();
                            int index = Utils.getIndex(wave.getGuessFactor(x, y), sectors.length);

                            double angletoside = Utils.asin(MyRobotsInfo.WIDTH
                                    / Utils.getDist(wave.getStartX(), wave.getStartY(), x, y));
                            int indextoside = (Utils.getIndex(angletoside / wave.maxEscapeAngle(), sectors.length) - midsec) / 2;

                            for (int i = 0; i < indextoside * 2 + 1 && index + (i - indextoside) < sectors.length
                                    && index + (i - indextoside) > -1; i++) {
                                wriskf += sectors[index + (i - indextoside)];
                            }
                            if (wave.testHit(new Rectangle2D.Double(x, y, MyRobotsInfo.WIDTH, MyRobotsInfo.HEIGHT),
                                    time + ft)) {
                                break;
                            }
                        }

                        int bt = 0;
                        for (; bt < backwards.length; bt++) {
                            double x = backwards[bt].getX(), y = backwards[bt].getY();
                            int index = Utils.getIndex(wave.getGuessFactor(x, y), sectors.length);

                            double angletoside = Utils.asin(MyRobotsInfo.WIDTH
                                    / Utils.getDist(wave.getStartX(), wave.getStartY(), x, y));
                            int indextoside = (Utils.getIndex(angletoside / wave.maxEscapeAngle(), sectors.length) - midsec) / 2;

                            for (int i = 0; i < indextoside * 2 + 1 && index + (i - indextoside) < sectors.length
                                    && index + (i - indextoside) > -1; i++) {
                                wriskb += sectors[index];
                            }
                            if (wave.testHit(new Rectangle2D.Double(x, y, MyRobotsInfo.WIDTH, MyRobotsInfo.HEIGHT),
                                    time + bt)) {
                                break;
                            }
                        }

                        wriskf /= ft;
                        wriskb /= bt;
                        wriskf *= 1 / wave.distSqToImpact(MyRobot);
                        wriskb *= 1 / wave.distSqToImpact(MyRobot);
                        riskf += wriskf;
                        riskb += wriskb;
                    }
                    if (riskf > riskb) {
                        WantD *= -1;
                    }
                    Movement.setAhead(Double.POSITIVE_INFINITY * WantD);
                    Movement.setTurnPerpenToXYwBFwDCwRM(f.getMaxRiskWave().getStartX(), f.getMaxRiskWave().getStartY(),
                            SafeDist(SafeDist, EnemyData));
                }
                Movement.setTurnToSmoothWalls();
            }
        } else {
        }
    }

    public RobotVector[] getForwardPoints() {
        return null;
    }

    public RobotVector[] getBackwardPoints() {
        return null;
    }

    public void inEvent(Event e) {
    }

    public void drawMovement(RobocodeGraphicsDrawer g, EnemyData EnemyData) {
        EnemyWave[] HighRiskWaves = f.getMaxRiskWaves();

        double mx = i.getX(), my = i.getY();
        long maxtime = 1;
        for (int w = 0; w < HighRiskWaves.length; w++)
            maxtime = Math.max(maxtime, HighRiskWaves[w].timeToImpact(MyRobot) + 1);

        RobotVector[] forwards = new RobotVector[(int) maxtime];
        RobotVector[] backwards = new RobotVector[(int) maxtime];

        double heading = i.getRobotFrontHeading();
        double startvelocity = i.getVelocity() == 0 ? 1 : i.getVelocity();
        double velocity = startvelocity;

        forwards[0] = new RobotVector(Utils.getX(mx, velocity, heading), Utils.getY(my, velocity, heading), heading,
                velocity);
        backwards[0] = new RobotVector(Utils.getX(mx, i.getFutureVelocity(Utils.sign(startvelocity) * -1
                * i.MAX_VELOCITY), heading), Utils.getY(my, i.getFutureVelocity(Utils.sign(startvelocity) * -1
                * i.MAX_VELOCITY), heading), heading, velocity);

        for (int t = 1; t < maxtime; t++) {
            RobotVector v = forwards[t - 1].getNextVector(0, Utils.sign(startvelocity) * i.MAX_VELOCITY);
            Movement.ajustVectorForWall(v);
            forwards[t] = v;
        }
        velocity = i.getVelocity();
        for (int t = 1; t < maxtime; t++) {
            RobotVector v = backwards[t - 1].getNextVector(0, Utils.sign(startvelocity) * -1 * i.MAX_VELOCITY);
            Movement.ajustVectorForWall(v);
            backwards[t] = v;
        }

        g.setColor(Colors.GREEN);
        for (int ft = 0; ft < forwards.length; ft++) {
            g.fillOval((int) forwards[ft].getX(), (int) forwards[ft].getY(), 2, 2);
        }
        g.setColor(Colors.RED);
        for (int bt = 0; bt < backwards.length; bt++) {
            g.fillOval((int) backwards[bt].getX(), (int) backwards[bt].getY(), 2, 2);
        }

    }

}
