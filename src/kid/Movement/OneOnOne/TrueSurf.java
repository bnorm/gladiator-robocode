package kid.Movement.OneOnOne;

import kid.*;
import kid.Data.*;
import kid.Data.Robot.*;
import kid.Data.Virtual.*;
import robocode.*;

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
        if (MyRobot instanceof AdvancedRobot) {
            if (EnemyData != null) {
                EnemyWave[] HighRiskWaves = f.getBulletWaves();
                if (HighRiskWaves == null || f.getClosestWave() == null || HighRiskWaves.length == 0) {
                    if (i.DistTo(EnemyData) < 300) {
                        Movement.setAhead(100 * Movement.setTurnPerpenToXYwBFwDC(EnemyData.getX(), EnemyData.getY(),
                                SafeDist(SafeDist, EnemyData)));
                    }
                } else {

                    int MAX_WAVES = 100;

                    double[] wriskf = new double[Math.min(HighRiskWaves.length, MAX_WAVES)];
                    double[] wriskb = new double[Math.min(HighRiskWaves.length, MAX_WAVES)];

                    int WantD = i.getRobotMovingSign();
                    double heading = i.getRobotFrontHeading();
                    double velocity = i.getVelocity();

                    RobotVector forwards = new RobotVector(mx, my, heading, velocity);
                    RobotVector backwards = new RobotVector(mx, my, heading, velocity);

                    double targetVelFor = Utils.sign(velocity) * MyRobotsInfo.MAX_VELOCITY;
                    double targetVelBack = Utils.sign(velocity) * -1 * MyRobotsInfo.MAX_VELOCITY;


                    int ft = 0;
                    int bt = 0;
                    long t = time + 1;
                    for (int w = 0, nwaves = HighRiskWaves.length; w < nwaves && w < MAX_WAVES; w++) {
                        if (w > 0) {
                            wriskf[w] = wriskf[w - 1] * ft;
                            wriskb[w] = wriskb[w - 1] * bt;
                        }

                        EnemyWave wave = HighRiskWaves[w];

                        double[] sectors = wave.getMyRobotsSectors();
                        int numsec = sectors.length;
                        int midsec = ((numsec - 1) / 2);

                        if (f.needHeadOnAviodance()) { // HeadOn avodence.
                            double waverisk = (wearydist / (wave.distToImpact(MyRobot) - MyRobotsInfo.WIDTH)) * 2;
                            waverisk = (waverisk < 0 ? 0 : waverisk);
                            double maxrisk = 0;
                            for (int i = 0; i < numsec; i++)
                                maxrisk = Math.max(maxrisk, sectors[i]);

                            sectors[midsec] += (waverisk + maxrisk);
                        }

                        for (;; ft++) {
                            forwards.moveVector(Movement.getAngleForPerpenToRobotwDC(backwards, targetVelBack,
                                    EnemyData, SafeDist(SafeDist, EnemyData)), targetVelFor);
                            Movement.ajustVectorForWall(forwards);
                            double x = forwards.getX(), y = forwards.getY();

                            int index = Utils.getIndex(wave.getGuessFactor(x, y), numsec);

                            double angletoside = Utils.atan(MyRobotsInfo.WIDTH
                                    / Utils.getDist(wave.getStartX(), wave.getStartY(), x, y)) / 2;
                            int indextoside = (Utils.getIndex(angletoside / wave.maxEscapeAngle(), numsec) - midsec);

                            for (int i = 0; i < indextoside * 2 + 1; i++) {
                                wriskf[w] += sectors[Math.min(numsec - 1, Math.max(0, index + (i - indextoside)))];
                            }
                            if (wave.testHit(x, y, t + ft)) {
                                break;
                            }
                        }

                        for (;; bt++) {
                            backwards.moveVector(Movement.getAngleForPerpenToRobotwDC(backwards, targetVelBack,
                                    EnemyData, SafeDist(SafeDist, EnemyData)), targetVelBack);
                            Movement.ajustVectorForWall(backwards);
                            double x = backwards.getX(), y = backwards.getY();

                            int index = Utils.getIndex(wave.getGuessFactor(x, y), numsec);

                            double angletoside = Utils.asin(MyRobotsInfo.WIDTH
                                    / Utils.getDist(wave.getStartX(), wave.getStartY(), x, y)) / 2;
                            int indextoside = (Utils.getIndex(angletoside / wave.maxEscapeAngle(), numsec) - midsec);

                            for (int i = 0; i < indextoside * 2 + 1; i++) {
                                wriskb[w] += sectors[Math.min(numsec - 1, Math.max(0, index + (i - indextoside)))];
                            }
                            if (wave.testHit(x, y, t + bt)) {
                                break;
                            }
                        }

                        if (ft != 0)
                            wriskf[w] /= ft;
                        if (bt != 0)
                            wriskb[w] /= bt;
                        // d.println("Wave: " + w);
                        // d.println("Forward Time: " + ft);
                        // d.println("Backword Time: " + bt);
                    }
                    double riskf = 0.0;
                    double riskb = 0.0;
                    for (int w = 0; w < wriskf.length && w < HighRiskWaves.length; w++) {
                        double dist = HighRiskWaves[w].distSqToImpact(MyRobot);
                        if (dist <= 0.0)
                            continue;
                        // d.println("Wave: " + w);
                        // d.println("Risk Forward: " + wriskf[w] / dist);
                        // d.println("Risk Backword: " + wriskb[w] / dist);
                        riskf += wriskf[w] / dist;
                        riskb += wriskb[w] / dist;
                    }
                    // d.println("Wave Risk Total");
                    // d.println("Risk Forward: " + riskf);
                    // d.println("Risk Backword: " + riskb);
                    // d.println();
                    if (WantD == 0)
                        if (riskf < riskb)
                            WantD = 1;
                        else
                            WantD = -1;
                    else if (riskf > riskb)
                        WantD *= -1;

                    Movement.setAhead(Double.POSITIVE_INFINITY * WantD);
                    Movement.setTurnPerpenToXYwBFwDCwRM(f.getClosestWave().getStartX(), f.getClosestWave().getStartY(),
                            SafeDist(SafeDist, EnemyData));
                }
                Movement.setTurnToSmoothWalls();
            }
        } else {
        }
    }

    public void inEvent(Event e) {
    }

    public void drawMovement(RobocodeGraphicsDrawer g, EnemyData EnemyData) {
        EnemyWave[] HighRiskWaves = f.getBulletWaves();
        if (HighRiskWaves == null || HighRiskWaves.length == 0)
            return;

        double mx = i.getX(), my = i.getY();
        double heading = i.getRobotFrontHeading();
        double startvelocity = i.getVelocity();

        double targetVelFor = Utils.sign(startvelocity) * MyRobotsInfo.MAX_VELOCITY;
        RobotVector forwards = new RobotVector(Utils.getX(mx, i.getFutureVelocity(targetVelFor), heading), Utils.getY(
                my, i.getFutureVelocity(targetVelFor), heading), heading, startvelocity);

        double targetVelBack = -targetVelFor;
        RobotVector backwards = new RobotVector(Utils.getX(mx, i.getFutureVelocity(targetVelBack), heading), Utils
                .getY(my, i.getFutureVelocity(targetVelBack), heading), heading, startvelocity);

        g.setColor(Colors.GREEN);
        for (long t = i.getTime() - 1;; t++) {
            g.fillOvalCenter(forwards.getX(), forwards.getY(), 2, 2);
            forwards.moveVector(Movement.getAngleForPerpenToRobotwDC(forwards, targetVelFor, EnemyData, SafeDist(
                    SafeDist, EnemyData)), targetVelFor);
            Movement.ajustVectorForWall(forwards);
            if (HighRiskWaves[HighRiskWaves.length - 1].testHit(forwards.getX(), forwards.getY(), t)) {
                break;
            }
        }
        g.setColor(Colors.RED);
        for (long t = i.getTime() - 1;; t++) {
            g.fillOvalCenter(backwards.getX(), backwards.getY(), 2, 2);
            backwards.moveVector(Movement.getAngleForPerpenToRobotwDC(backwards, targetVelBack, EnemyData, SafeDist(
                    SafeDist, EnemyData)), targetVelBack);
            Movement.ajustVectorForWall(backwards);
            if (HighRiskWaves[HighRiskWaves.length - 1].testHit(backwards.getX(), backwards.getY(), t)) {
                break;
            }
        }

    }
}
