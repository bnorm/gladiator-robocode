package kid.Movement.OneOnOne;

import kid.*;
import kid.Data.*;
import kid.Data.Robot.*;
import kid.Data.Segmentation.*;
import kid.Data.Virtual.*;
import kid.Graphics.*;
import robocode.*;

public class TrueSurf extends WaveSurfing {

    public TrueSurf(AdvancedRobot MyRobot, Flatner f) {
        super(MyRobot, f);
    }

    public TrueSurf(TeamRobot MyRobot, Flatner f) {
        super(MyRobot, f);
    }

    public void doMovement(EnemyData EnemyData) {
        if (i.getOthers() > 1)
            return;
        double angle = Utils.getAngle(EnemyData.getX(), EnemyData.getY(), MyRobot.getX(), MyRobot.getY());
        int CurD = (Utils.sin(MyRobot.getHeading() - angle) * MyRobot.getVelocity() < 0) ? -1 : 1;
        if (MyRobot instanceof AdvancedRobot) {
            if (EnemyData != null) {
                EnemyWave[] HighRiskWaves = f.getMaxRiskWaves();
                if (HighRiskWaves == null || f.getMaxRiskWave() == null) {
                    Movement.setAhead(Double.POSITIVE_INFINITY *
                                      Movement.setTurnPerpenToXYwBFwDC(EnemyData.getX(), EnemyData.getY(),
                            SafeDist(SafeDist, EnemyData)));
                } else {

                    double riskf = 0.0;
                    double riskb = 0.0;

                    for (int w = 0; w < HighRiskWaves.length; w++) {
                        EnemyWave wave = HighRiskWaves[w];
                        double guessfactor = wave.getGuessFactor(MyRobot);
                        int index = Utils.getIndex(guessfactor, Bin.numBins);

                        int[] sectors = wave.getMyRobotsSectors();
                        int WaveD = wave.getDirection();

                        int robotwidthintidexes = (int) Utils.getIndex(Utils.asin(MyRobotsInfo.WIDTH /
                                Utils.getDist(wave.getStartX(), wave.getStartX(), i.getX(), i.getY())) /
                                wave.maxEscapeAngle(), sectors.length) - ((sectors.length - 1) / 2);

                        double indexstoside = (sectors.length - 1) / 2 + robotwidthintidexes;
                        indexstoside *= wave.distToImpact(MyRobot) /
                                Utils.getDist(wave.getStartX(), wave.getStartY(), MyRobot.getX(), MyRobot.getX());

                        double wriskf = 0.0;
                        double wriskb = 0.0;

                        int t = 1;
                        try {
                            for (; t <= indexstoside; t++) {
                                wriskf += sectors[index + (t * CurD * WaveD)];
                                wriskb += sectors[index - (t * CurD * WaveD)];
                            }
                        } catch (Exception e) {}

                        wriskf /= t;
                        wriskb /= t;
                        wriskf *= 1 / wave.distSqToImpact(MyRobot);
                        wriskb *= 1 / wave.distSqToImpact(MyRobot);
                        riskf += wriskf;
                        riskb += wriskb;
                    }

                    int WantD = (i.getRobotMovingSign() == 0 ? 1 : i.getRobotMovingSign());
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

    public void inEvent(Event e) {}

    public void drawMovement(RobocodeGraphicsDrawer g, EnemyData EnemyData) {}

}
