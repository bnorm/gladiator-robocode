package kid.Movement.OneOnOne;

import kid.*;
import kid.Data.*;
import kid.Data.Robot.*;
import kid.Data.Virtual.*;
import robocode.*;

public class TrueSurf extends WaveSurfing {

    protected double DistWeigth = 0.25;

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
            EnemyWave[] HighRiskWaves = f.getBulletWaves();
            if (HighRiskWaves == null || f.getClosestWave() == null || HighRiskWaves.length == 0) {
                Movement.setAhead(100 * Movement.setTurnPerpenToXYwBFwDC(EnemyData.getX(), EnemyData.getY(),
                        SafeDist(EnemyData)));
            } else {

                int MAX_WAVES = 100;

                double[] wriskf = new double[Math.min(HighRiskWaves.length, MAX_WAVES)];
                double[] wriskb = new double[Math.min(HighRiskWaves.length, MAX_WAVES)];

                int WantD = i.getRobotMovingSign();
                double WantDist = SafeDist(EnemyData);

                double heading = i.getRobotFrontHeading();
                double velocity = i.getVelocity();

                RobotVector forwards = new RobotVector(mx, my, heading, velocity);
                RobotVector backwards = new RobotVector(mx, my, heading, velocity);

                double targetVelFor = Utils.sign(velocity) * MyRobotsInfo.MAX_VELOCITY;
                double targetVelBack = Utils.sign(velocity) * -1 * MyRobotsInfo.MAX_VELOCITY;

                int ft = 1;
                int bt = 1;
                long t = time;
                for (int w = 0, nwaves = HighRiskWaves.length; w < nwaves && w < MAX_WAVES; w++) {

                    // double[] sectors = wave.getMyRobotsSectors();
                    // double[] rollingGF = f.getRollingGF();
                    int numsec = HighRiskWaves[w].getSectors().length;
                    int midsec = ((numsec - 1) / 2);

                    for (;; ft++) {
                        double TargetTurn = Movement.getAngleForPerpenToRobotwDC(forwards, targetVelFor, EnemyData,
                                WantDist);
                        RobotVector temp = forwards.getNextVector(TargetTurn, targetVelFor);
                        Movement.ajustVectorForWall(temp);
                        TargetTurn = temp.getHeading() - forwards.getHeading();
                        forwards.moveVector(TargetTurn, targetVelFor);
                        double x = forwards.getX(), y = forwards.getY();

                        for (int r = w; r < HighRiskWaves.length && r < MAX_WAVES; r++) {
                            EnemyWave wave = HighRiskWaves[r];
                            int index = Utils.getIndex(wave.getGuessFactor(x, y), numsec);

                            double dist = Utils.getDist(wave.getStartX(), wave.getStartY(), x, y);
                            double angletoside = Utils.asin(MyRobotsInfo.LENGTH_CONER / dist) / 2;
                            int indextoside = (Utils.getIndex(angletoside / wave.maxEscapeAngle(), numsec) - midsec);

                            double[] sectors = wave.getSectors();
                            // for (int i = 0; i < rollingGF.length; i++) {
                            // sectors[Utils.getIndex(rollingGF[i], numsec)] +=
                            // .1;
                            // }
                            for (int i = 0; i < indextoside * 2 + 1; i++) {
                                wriskf[r] += sectors[Math.min(numsec - 1, Math.max(0, index + (i - indextoside)))];
                            }
                        }

                        if (HighRiskWaves[w].testHit(x, y, t + ft)) {
                            break;
                        }
                    }

                    for (;; bt++) {
                        double TargetTurn = Movement.getAngleForPerpenToRobotwDC(backwards, targetVelBack, EnemyData,
                                WantDist);
                        RobotVector temp = backwards.getNextVector(TargetTurn, targetVelBack);
                        Movement.ajustVectorForWall(temp);
                        TargetTurn = temp.getHeading() - backwards.getHeading();
                        backwards.moveVector(TargetTurn, targetVelBack);
                        double x = backwards.getX(), y = backwards.getY();

                        for (int r = w; r < HighRiskWaves.length && r < MAX_WAVES; r++) {
                            EnemyWave wave = HighRiskWaves[r];
                            int index = Utils.getIndex(wave.getGuessFactor(x, y), numsec);

                            double dist = Utils.getDist(wave.getStartX(), wave.getStartY(), x, y);
                            double angletoside = Utils.asin(MyRobotsInfo.LENGTH_CONER / dist) / 2;
                            int indextoside = (Utils.getIndex(angletoside / wave.maxEscapeAngle(), numsec) - midsec);

                            double[] sectors = wave.getSectors();
                            // for (int i = 0; i < rollingGF.length; i++) {
                            // sectors[Utils.getIndex(rollingGF[i], numsec)] +=
                            // .1;
                            // }
                            for (int i = 0; i < indextoside * 2 + 1; i++) {
                                wriskb[r] += sectors[Math.min(numsec - 1, Math.max(0, index + (i - indextoside)))];
                            }
                        }

                        if (HighRiskWaves[w].testHit(x, y, t + bt)) {
                            break;
                        }
                    }

                    wriskf[w] /= ft;

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
                    if (riskf > riskb)
                        WantD = -1;
                    else
                        WantD = 1;
                else if (riskf > riskb)
                    WantD *= -1;

                Movement.setAhead(Double.POSITIVE_INFINITY * WantD);
                Movement.setTurnPerpenToXYwBFwDCwRM(f.getClosestWave().getStartX(), f.getClosestWave().getStartY(),
                        WantDist);
            }
            Movement.setTurnToSmoothWalls();
        } else {
        }
    }
    public void inEvent(Event e) {
    }

    public void drawMovement(RobocodeGraphicsDrawer g, EnemyData EnemyData) {
        EnemyWave[] HighRiskWaves = f.getBulletWaves();
        if (HighRiskWaves == null || HighRiskWaves.length == 0)
            return;

        double WantDist = SafeDist(EnemyData);

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
        for (long t = i.getTime();; t++) {
            g.fillOvalCenter(forwards.getX(), forwards.getY(), 2, 2);
            double TargetTurn = Movement.getAngleForPerpenToRobotwDC(forwards, targetVelFor, EnemyData, WantDist);
            RobotVector temp = forwards.getNextVector(TargetTurn, targetVelFor);
            Movement.ajustVectorForWall(temp);
            TargetTurn = temp.getHeading() - forwards.getHeading();
            forwards.moveVector(TargetTurn, targetVelFor);
            if (HighRiskWaves[0].testHit(forwards.getX(), forwards.getY(), t)) {
                break;
            }
        }
        g.setColor(Colors.RED);
        for (long t = i.getTime();; t++) {
            g.fillOvalCenter(backwards.getX(), backwards.getY(), 2, 2);
            double TargetTurn = Movement.getAngleForPerpenToRobotwDC(backwards, targetVelBack, EnemyData, WantDist);
            RobotVector temp = backwards.getNextVector(TargetTurn, targetVelBack);
            Movement.ajustVectorForWall(temp);
            TargetTurn = temp.getHeading() - backwards.getHeading();
            backwards.moveVector(TargetTurn, targetVelBack);
            if (HighRiskWaves[0].testHit(backwards.getX(), backwards.getY(), t)) {
                break;
            }
        }

    }
}
