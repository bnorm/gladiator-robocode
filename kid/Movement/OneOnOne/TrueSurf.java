package kid.Movement.OneOnOne;

import java.awt.geom.Point2D;

import kid.*;
import kid.Data.*;
import kid.Data.Robot.*;
import kid.Data.Virtual.*;
import robocode.*;

public class TrueSurf extends WaveSurfing {

    private Point2D[] forwardPoints;
    private Point2D[] backwardPoints;

    public TrueSurf(AdvancedRobot MyRobot, MovementProfile f) {
        super(MyRobot, f);
    }

    public TrueSurf(TeamRobot MyRobot, MovementProfile f) {
        super(MyRobot, f);
    }

    public void doMovement(EnemyData EnemyData) {
        if (i.getOthers() > 1 || EnemyData == null)
            return;
        double mx = i.getX(), my = i.getY();
        long time = i.getTime();
        if (MyRobot instanceof AdvancedRobot) {
            EnemyWave[] bulletWaves = MovementProfile.getBulletWaves();
            if (bulletWaves == null || MovementProfile.getClosestWave() == null || bulletWaves.length == 0) {
                Movement.setAhead(100 * Movement.setTurnPerpenToXYwBFwDC(EnemyData.getX(), EnemyData.getY(),
                        SafeDist(EnemyData)));
            } else {

                int MAX_WAVES = 100;

                forwardPoints = new Point2D[Math.min(bulletWaves.length, MAX_WAVES)];
                backwardPoints = new Point2D[Math.min(bulletWaves.length, MAX_WAVES)];

                double[] wriskf = new double[Math.min(bulletWaves.length, MAX_WAVES)];
                double[] wriskb = new double[Math.min(bulletWaves.length, MAX_WAVES)];

                double[] wdistf = new double[Math.min(bulletWaves.length, MAX_WAVES)];
                double[] wdistb = new double[Math.min(bulletWaves.length, MAX_WAVES)];

                int WantD = Utils.sign(i.getVelocity());
                double WantDist = SafeDist(EnemyData);

                double heading = i.getRobotFrontHeading();
                double velocity = i.getVelocity();

                RobotVector forwards = new RobotVector(mx, my, heading, velocity);
                RobotVector backwards = new RobotVector(mx, my, heading, velocity);

                double targetVelFor = Utils.sign(velocity) * MyRobotsInfo.MAX_VELOCITY;
                double targetVelBack = Utils.sign(velocity) * -1 * MyRobotsInfo.MAX_VELOCITY;

                int ft = 0;
                int bt = 0;
                long t = time + 1;
                for (int w = 0, nwaves = bulletWaves.length; w < nwaves && w < MAX_WAVES; w++) {

                    int numsec = bulletWaves[w].getSectors().length;
                    int midsec = ((numsec - 1) / 2);

                    for (;; ft++) {
                        double x = forwards.getX(), y = forwards.getY();
                        if (bulletWaves[w].testHit(x, y, t + ft)) {
                            forwardPoints[w] = new Point2D.Double(x, y);
                            wdistf[w] = bulletWaves[w].distToImpact(x, y, t);
                            break;
                        }

                        double TargetTurn = Movement.getAngleForPerpenToRobotwDC(forwards, targetVelFor, EnemyData,
                                WantDist);
                        RobotVector temp = forwards.getNextVector(TargetTurn, targetVelFor);
                        Movement.ajustVectorForWall(temp);
                        TargetTurn = temp.getHeading() - forwards.getHeading();
                        forwards.moveVector(TargetTurn, targetVelFor);
                        Movement.ajustVectorForWallHit(forwards);
                        x = forwards.getX();
                        y = forwards.getY();

                        for (int r = w; r < bulletWaves.length && r < MAX_WAVES; r++) {
                            if (bulletWaves[r].testHit(x, y, t + ft))
                                continue;

                            EnemyWave wave = bulletWaves[r];
                            int index = Utils.getIndex(wave.getGuessFactor(x, y), numsec);

                            double dist = Utils.getDist(wave.getStartX(), wave.getStartY(), x, y);
                            double angletoside = Utils.asin(MyRobotsInfo.LENGTH_CONER / dist) / 2;
                            int indextoside = (Utils.getIndex(angletoside / wave.maxEscapeAngle(), numsec) - midsec);

                            double[] sectors = wave.getSectors();
                            for (int i = 0; i < indextoside * 2 + 1; i++) {
                                wriskf[r] += sectors[Math.min(numsec - 1, Math.max(0, index + (i - indextoside)))];
                            }
                        }
                    }

                    for (;; bt++) {
                        double x = backwards.getX(), y = backwards.getY();
                        if (bulletWaves[w].testHit(x, y, t + bt)) {
                            backwardPoints[w] = new Point2D.Double(x, y);
                            wdistb[w] = bulletWaves[w].distToImpact(x, y, t);
                            break;
                        }

                        double TargetTurn = Movement.getAngleForPerpenToRobotwDC(backwards, targetVelBack, EnemyData,
                                WantDist);
                        RobotVector temp = backwards.getNextVector(TargetTurn, targetVelBack);
                        Movement.ajustVectorForWall(temp);
                        TargetTurn = temp.getHeading() - backwards.getHeading();
                        backwards.moveVector(TargetTurn, targetVelBack);
                        Movement.ajustVectorForWallHit(backwards);
                        x = backwards.getX();
                        y = backwards.getY();

                        for (int r = w; r < bulletWaves.length && r < MAX_WAVES; r++) {
                            if (bulletWaves[r].testHit(x, y, t + bt))
                                continue;

                            EnemyWave wave = bulletWaves[r];
                            int index = Utils.getIndex(wave.getGuessFactor(x, y), numsec);

                            double dist = Utils.getDist(wave.getStartX(), wave.getStartY(), x, y);
                            double angletoside = Utils.asin(MyRobotsInfo.LENGTH_CONER / dist) / 2;
                            int indextoside = (Utils.getIndex(angletoside / wave.maxEscapeAngle(), numsec) - midsec);

                            double[] sectors = wave.getSectors();
                            for (int i = 0; i < indextoside * 2 + 1; i++) {
                                wriskb[r] += sectors[Math.min(numsec - 1, Math.max(0, index + (i - indextoside)))];
                            }
                        }
                    }

                    if (ft != 0) {
                        wriskf[w] /= ft;
                    }

                    if (bt != 0) {
                        wriskb[w] /= bt;
                    }

                    // d.println("Wave: " + w);
                    // d.println("Forward Time: " + ft);
                    // d.println("Backword Time: " + bt);
                }


                double riskf = 0.0;
                double riskb = 0.0;
                for (int w = 0; w < wriskf.length && w < bulletWaves.length; w++) {
                    double distsq = bulletWaves[w].distToImpact(MyRobot);
                    // d.println("Wave: " + w);
                    // d.println("Risk Forward: " + wriskf[w] / dist);
                    // d.println("Risk Backword: " + wriskb[w] / dist);
                    if (distsq > 0.0)
                        riskf += wriskf[w] / (Utils.sqr(wdistf[w]));
                    if (distsq > 0.0)
                        riskb += wriskb[w] / (Utils.sqr(wdistb[w]));
                }
                // d.println("Wave Risk Total");
                // d.println("Risk Forward: " + riskf);
                // d.println("Risk Backword: " + riskb);
                // d.println();

                // d.println("Risk Forward: " + wriskf[0]);
                // d.println("Risk Stop: " + wriskb[0]);
                if (wriskf[0] == wriskb[0]) {
                    double fdist = Math.abs(EnemyData.DistTo(forwardPoints[0].getX(), forwardPoints[0].getY())
                            - WantDist);
                    double bdist = Math.abs(EnemyData.DistTo(backwardPoints[0].getX(), backwardPoints[0].getY())
                            - WantDist);
                    // d.println(fdist);
                    // d.println(bdist * 2);
                    // d.println();
                    if (fdist > bdist * 2)
                        WantD *= -1;
                } else {
                    if (riskf > riskb)
                        WantD *= -1;
                }

                Movement.setAhead(Double.POSITIVE_INFINITY * WantD);
                Movement.setTurnPerpenToXYwBFwDCwRM(MovementProfile.getClosestWave().getStartX(), MovementProfile
                        .getClosestWave().getStartY(), WantDist);
            }
            Movement.setTurnToSmoothWalls();
        } else {
        }
    }

    public void inEvent(Event e) {
    }

    public void drawMovement(RobocodeGraphicsDrawer g, EnemyData EnemyData) {
        EnemyWave[] HighRiskWaves = MovementProfile.getBulletWaves();
        if (HighRiskWaves == null || HighRiskWaves.length == 0)
            return;

        double WantDist = SafeDist(EnemyData);

        double mx = i.getX(), my = i.getY();
        double heading = i.getRobotFrontHeading();
        double velocity = i.getVelocity();

        double targetVelFor = Utils.sign(velocity) * MyRobotsInfo.MAX_VELOCITY;
        RobotVector forwards = new RobotVector(mx, my, heading, velocity);

        double targetVelBack = -targetVelFor;
        RobotVector backwards = new RobotVector(mx, my, heading, velocity);

        g.setColor(Colors.GREEN);
        for (long t = i.getTime() + 1;; t++) {
            double TargetTurn = Movement.getAngleForPerpenToRobotwDC(forwards, targetVelFor, EnemyData, WantDist);
            RobotVector temp = forwards.getNextVector(TargetTurn, targetVelFor);
            Movement.ajustVectorForWall(temp);
            TargetTurn = temp.getHeading() - forwards.getHeading();
            forwards.moveVector(TargetTurn, targetVelFor);
            Movement.ajustVectorForWallHit(forwards);
            g.fillOvalCenter(forwards.getX(), forwards.getY(), 2, 2);
            if (HighRiskWaves[HighRiskWaves.length - 1].testHit(forwards.getX(), forwards.getY(), t)) {
                break;
            }
        }
        for (int p = 0; p < forwardPoints.length; p++)
            g.fillOvalCenter(forwardPoints[p].getX(), forwardPoints[p].getY(), 5, 5);

        g.setColor(Colors.RED);
        for (long t = i.getTime() + 1;; t++) {
            double TargetTurn = Movement.getAngleForPerpenToRobotwDC(backwards, targetVelBack, EnemyData, WantDist);
            RobotVector temp = backwards.getNextVector(TargetTurn, targetVelBack);
            Movement.ajustVectorForWall(temp);
            TargetTurn = temp.getHeading() - backwards.getHeading();
            backwards.moveVector(TargetTurn, targetVelBack);
            Movement.ajustVectorForWallHit(backwards);
            g.fillOvalCenter(backwards.getX(), backwards.getY(), 2, 2);
            if (HighRiskWaves[HighRiskWaves.length - 1].testHit(backwards.getX(), backwards.getY(), t)) {
                break;
            }
        }
        for (int p = 0; p < backwardPoints.length; p++)
            g.fillOvalCenter(backwardPoints[p].getX(), backwardPoints[p].getY(), 5, 5);

    }
}
