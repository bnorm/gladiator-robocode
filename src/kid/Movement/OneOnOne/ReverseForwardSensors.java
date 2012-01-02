package kid.Movement.OneOnOne;

import java.awt.Color;
import java.awt.geom.Point2D;

import kid.*;
import kid.Data.*;
import kid.Data.Robot.*;
import kid.Data.Virtual.*;
import robocode.*;

public class ReverseForwardSensors extends WaveSurfing {

    private Point2D[] forwardPoints;
    private Point2D[] stopPoints;
    private Point2D[] backwardPoints;

    public ReverseForwardSensors(AdvancedRobot MyRobot, MovementProfile f) {
        super(MyRobot, f);
    }

    public ReverseForwardSensors(TeamRobot MyRobot, MovementProfile f) {
        super(MyRobot, f);
    }

    public void doMovement(EnemyData EnemyData) {
        TimeTracker.startMovementTime();
        if (i.getOthers() > 1 || EnemyData == null)
            return;
        double mx = i.getX(), my = i.getY();
        long time = i.getTime();
        if (MyRobot instanceof AdvancedRobot) {
            EnemyWave[] bulletWaves = MovementProfile.getBulletWaves();
            if (bulletWaves == null || MovementProfile.getClosestWave() == null
                || bulletWaves.length == 0) {
                Movement.setAhead(100 * Movement.setTurnPerpenToXYwBFwDC(EnemyData.getX(),
                                                                         EnemyData.getY(),
                                                                         SafeDist(EnemyData)));
            } else {

                int MAX_WAVES = 2;

                forwardPoints = new Point2D[Math.min(bulletWaves.length, MAX_WAVES)];
                stopPoints = new Point2D[Math.min(bulletWaves.length, MAX_WAVES)];
                backwardPoints = new Point2D[Math.min(bulletWaves.length, MAX_WAVES)];

                double[] wriskf = new double[Math.min(bulletWaves.length, MAX_WAVES)];
                double[] wrisks = new double[Math.min(bulletWaves.length, MAX_WAVES)];
                double[] wriskb = new double[Math.min(bulletWaves.length, MAX_WAVES)];

                int WantD = Utils.sign(i.getVelocity());
                double WantDist = SafeDist(EnemyData);

                double heading = i.getRobotFrontHeading();
                double velocity = i.getVelocity();

                RobotVector forwards = new RobotVector(mx, my, heading, velocity);
                RobotVector stop = new RobotVector(mx, my, heading, velocity);
                RobotVector backwards = new RobotVector(mx, my, heading, velocity);

                double targetVelFor = Utils.sign(velocity) * RobotInfo.MAX_VELOCITY;
                double targetVelStop = 0.0;
                double targetVelBack = Utils.sign(velocity) * -1 * RobotInfo.MAX_VELOCITY;

                int ft = 0;
                int st = 0;
                int bt = 0;

                long t = time + 1;
                int numsec = bulletWaves[0].getSectors().length;
                int midsec = ((numsec - 1) / 2);

                for (int w = 0, nwaves = bulletWaves.length; w < nwaves && w < MAX_WAVES; w++) {

                    for (;; ft++) {
                        double x = forwards.getX(), y = forwards.getY();
                        if (bulletWaves[w].testHit(x, y, t + ft)) {
                            forwardPoints[w] = new Point2D.Double(x, y);

                            EnemyWave wave = bulletWaves[w];
                            int index = Utils.getIndex(wave.getGuessFactor(x, y), numsec);

                            double dist = Utils.getDist(wave.getStartX(), wave.getStartY(), x, y);
                            double angletoside = Utils.asin(RobotInfo.LENGTH_CONER / dist) / 2;
                            int indextoside = (Utils.getIndex(angletoside / wave.maxEscapeAngle(),
                                                              numsec) - midsec);

                            double[] sectors = wave.getSectors();
                            for (int i = 0; i < indextoside * 2 + 1; i++) {
                                wriskf[w] += sectors[Math.min(
                                                              numsec - 1,
                                                              Math.max(0, index + (i - indextoside)))];
                            }

                            double TargetTurn = Movement.getAngleForPerpenToRobotwDC(forwards,
                                                                                     targetVelFor,
                                                                                     EnemyData,
                                                                                     WantDist);
                            RobotVector temp = forwards.getNextVector(TargetTurn, targetVelFor);
                            Movement.ajustVectorForWall(temp);
                            TargetTurn = temp.getHeading() - forwards.getHeading();
                            forwards.moveVector(TargetTurn, targetVelFor);
                            Movement.ajustVectorForWallHit(forwards);
                            x = forwards.getX();
                            y = forwards.getY();

                            wave = bulletWaves[w];
                            index = Utils.getIndex(wave.getGuessFactor(x, y), numsec);

                            dist = Utils.getDist(wave.getStartX(), wave.getStartY(), x, y);
                            angletoside = Utils.asin(RobotInfo.LENGTH_CONER / dist) / 2;
                            indextoside = (Utils.getIndex(angletoside / wave.maxEscapeAngle(),
                                                          numsec) - midsec);

                            for (int i = 0; i < indextoside * 2 + 1; i++) {
                                wriskf[w] += sectors[Math.min(
                                                              numsec - 1,
                                                              Math.max(0, index + (i - indextoside)))];
                            }

                            break;
                        }

                        double TargetTurn = Movement.getAngleForPerpenToRobotwDC(forwards,
                                                                                 targetVelFor,
                                                                                 EnemyData,
                                                                                 WantDist);
                        RobotVector temp = forwards.getNextVector(TargetTurn, targetVelFor);
                        Movement.ajustVectorForWall(temp);
                        TargetTurn = temp.getHeading() - forwards.getHeading();
                        forwards.moveVector(TargetTurn, targetVelFor);
                        Movement.ajustVectorForWallHit(forwards);
                        x = forwards.getX();
                        y = forwards.getY();
                    }

                    for (;; st++) {
                        double x = stop.getX(), y = stop.getY();
                        if (bulletWaves[w].testHit(x, y, t + st)) {
                            stopPoints[w] = new Point2D.Double(x, y);

                            EnemyWave wave = bulletWaves[w];
                            int index = Utils.getIndex(wave.getGuessFactor(x, y), numsec);

                            double dist = Utils.getDist(wave.getStartX(), wave.getStartY(), x, y);
                            double angletoside = Utils.asin(RobotInfo.LENGTH_CONER / dist) / 2;
                            int indextoside = (Utils.getIndex(angletoside / wave.maxEscapeAngle(),
                                                              numsec) - midsec);

                            double[] sectors = wave.getSectors();
                            for (int i = 0; i < indextoside * 2 + 1; i++) {
                                wrisks[w] += sectors[Math.min(
                                                              numsec - 1,
                                                              Math.max(0, index + (i - indextoside)))];
                            }

                            double TargetTurn = Movement.getAngleForPerpenToRobotwDC(stop,
                                                                                     targetVelStop,
                                                                                     EnemyData,
                                                                                     WantDist);
                            RobotVector temp = stop.getNextVector(TargetTurn, targetVelStop);
                            Movement.ajustVectorForWall(temp);
                            TargetTurn = temp.getHeading() - stop.getHeading();
                            stop.moveVector(TargetTurn, targetVelStop);
                            Movement.ajustVectorForWallHit(stop);
                            x = stop.getX();
                            y = stop.getY();

                            wave = bulletWaves[w];
                            index = Utils.getIndex(wave.getGuessFactor(x, y), numsec);

                            dist = Utils.getDist(wave.getStartX(), wave.getStartY(), x, y);
                            angletoside = Utils.asin(RobotInfo.LENGTH_CONER / dist) / 2;
                            indextoside = (Utils.getIndex(angletoside / wave.maxEscapeAngle(),
                                                          numsec) - midsec);

                            for (int i = 0; i < indextoside * 2 + 1; i++) {
                                wrisks[w] += sectors[Math.min(
                                                              numsec - 1,
                                                              Math.max(0, index + (i - indextoside)))];
                            }

                            break;
                        }

                        double TargetTurn = Movement.getAngleForPerpenToRobotwDC(stop,
                                                                                 targetVelStop,
                                                                                 EnemyData,
                                                                                 WantDist);
                        RobotVector temp = stop.getNextVector(TargetTurn, targetVelStop);
                        Movement.ajustVectorForWall(temp);
                        TargetTurn = temp.getHeading() - stop.getHeading();
                        stop.moveVector(TargetTurn, targetVelStop);
                        Movement.ajustVectorForWallHit(stop);
                        x = stop.getX();
                        y = stop.getY();
                    }

                    for (;; bt++) {
                        double x = backwards.getX(), y = backwards.getY();
                        if (bulletWaves[w].testHit(x, y, t + bt)) {
                            backwardPoints[w] = new Point2D.Double(x, y);

                            EnemyWave wave = bulletWaves[w];
                            int index = Utils.getIndex(wave.getGuessFactor(x, y), numsec);

                            double dist = Utils.getDist(wave.getStartX(), wave.getStartY(), x, y);
                            double angletoside = Utils.asin(RobotInfo.LENGTH_CONER / dist) / 2;
                            int indextoside = (Utils.getIndex(angletoside / wave.maxEscapeAngle(),
                                                              numsec) - midsec);

                            double[] sectors = wave.getSectors();
                            for (int i = 0; i < indextoside * 2 + 1; i++) {
                                wriskb[w] += sectors[Math.min(
                                                              numsec - 1,
                                                              Math.max(0, index + (i - indextoside)))];
                            }

                            double TargetTurn = Movement.getAngleForPerpenToRobotwDC(backwards,
                                                                                     targetVelBack,
                                                                                     EnemyData,
                                                                                     WantDist);
                            RobotVector temp = backwards.getNextVector(TargetTurn, targetVelBack);
                            Movement.ajustVectorForWall(temp);
                            TargetTurn = temp.getHeading() - backwards.getHeading();
                            backwards.moveVector(TargetTurn, targetVelBack);
                            Movement.ajustVectorForWallHit(backwards);
                            x = backwards.getX();
                            y = backwards.getY();

                            wave = bulletWaves[w];
                            index = Utils.getIndex(wave.getGuessFactor(x, y), numsec);

                            dist = Utils.getDist(wave.getStartX(), wave.getStartY(), x, y);
                            angletoside = Utils.asin(RobotInfo.LENGTH_CONER / dist) / 2;
                            indextoside = (Utils.getIndex(angletoside / wave.maxEscapeAngle(),
                                                          numsec) - midsec);

                            for (int i = 0; i < indextoside * 2 + 1; i++) {
                                wriskb[w] += sectors[Math.min(
                                                              numsec - 1,
                                                              Math.max(0, index + (i - indextoside)))];
                            }

                            break;
                        }

                        double TargetTurn = Movement.getAngleForPerpenToRobotwDC(backwards,
                                                                                 targetVelBack,
                                                                                 EnemyData,
                                                                                 WantDist);
                        RobotVector temp = backwards.getNextVector(TargetTurn, targetVelBack);
                        Movement.ajustVectorForWall(temp);
                        TargetTurn = temp.getHeading() - backwards.getHeading();
                        backwards.moveVector(TargetTurn, targetVelBack);
                        Movement.ajustVectorForWallHit(backwards);
                        x = backwards.getX();
                        y = backwards.getY();
                    }
                }


                double riskf = 0.0;
                double risks = 0.0;
                double riskb = 0.0;
                for (int w = 0; w < wriskf.length && w < bulletWaves.length; w++) {
                    double distsq = bulletWaves[w].distSqToImpact(MyRobot);
                    // d.println("Wave Risk: " + w);
                    // d.println("Risk Forward: " + wriskf[w] / distsq);
                    // d.println("Risk Stop: " + wrisks[w] / distsq);
                    // d.println("Risk Backword: " + wriskb[w] / distsq);
                    if (distsq <= 0.0)
                        continue;
                    riskf += wriskf[w] / distsq;
                    risks += wrisks[w] / distsq;
                    riskb += wriskb[w] / distsq;
                }
                // d.println("Wave Risk Total");
                // d.println("Risk Forward: " + riskf);
                // d.println("Risk Stop: " + risks);
                // d.println("Risk Backword: " + riskb);
                double minRisk = Math.min(riskf, Math.min(riskb, risks));
                if (WantD == 0) {
                    WantD = 1;
                }
                if (wriskf[0] == wriskb[0]) {
                    double fdist = Math.abs(EnemyData.DistTo(forwardPoints[0].getX(),
                                                             forwardPoints[0].getY())
                                            - WantDist);
                    double bdist = Math.abs(EnemyData.DistTo(backwardPoints[0].getX(),
                                                             backwardPoints[0].getY())
                                            - WantDist);
                    if (fdist > bdist * 2)
                        WantD *= -1;
                } else {
                    if (minRisk == riskf)
                        WantD *= 1;
                    else if (minRisk == riskb)
                        WantD *= -1;
                    else
                        WantD *= 0;
                }

                // d.println();

                Movement.setAhead(Double.POSITIVE_INFINITY * WantD);
                Movement.setTurnPerpenToXYwBFwDCwRM(MovementProfile.getClosestWave().getStartX(),
                                                    MovementProfile.getClosestWave().getStartY(),
                                                    WantDist);
            }
            Movement.setTurnToSmoothWalls();
        } else {
        }
        TimeTracker.stopMovementTime();
    }

    public void inEvent(Event e) {
    }

    public void drawMovement(RobocodeGraphicsDrawer g, EnemyData EnemyData) {
        if (MovementProfile.getBulletWaves() == null 
            || MovementProfile.getBulletWaves().length == 0)
            return;

            if (forwardPoints != null) {
                g.setColor(Color.GREEN);
                for (int p = 0; p < forwardPoints.length; p++)
                    if (forwardPoints[p] != null)
                        g.fillOvalCenter(forwardPoints[p].getX(), forwardPoints[p].getY(), 5, 5);
            }

        if (stopPoints != null) {
            g.setColor(Color.YELLOW);
            for (int p = 0; p < stopPoints.length; p++)
                if (stopPoints[p] != null)
                    g.fillOvalCenter(stopPoints[p].getX(), stopPoints[p].getY(), 5, 5);
        }

        if (backwardPoints != null) {
            g.setColor(Color.RED);
            for (int p = 0; p < backwardPoints.length; p++)
                if (backwardPoints[p] != null)
                    g.fillOvalCenter(backwardPoints[p].getX(), backwardPoints[p].getY(), 5, 5);
        }

    }
}
