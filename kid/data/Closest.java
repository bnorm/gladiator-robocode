package kid.data;

import robocode.Robot;

import kid.Utils;
import kid.data.robot.*;

// TODO document class

public class Closest extends RobotChooser {

    private static final double CLOSER_MARGEN = 0.9D;

    public RobotData getRobotOverride(Robot myRobot, RobotData[] robots) {
        RobotData robot = lastRobot;
        double distSq = Utils.getDistSq(myRobot.getX(), myRobot.getY(), lastRobot.getX(), lastRobot.getY());
        for (RobotData r : robots) {
            if (r.isDead())
                continue;
            double distTemp = Utils.getDistSq(myRobot.getX(), myRobot.getY(), r.getX(), r.getY());
            if (distTemp < distSq * CLOSER_MARGEN) {
                robot = r;
                distSq = distTemp;
            }
        }
        return robot;
    }

    public EnemyData getEnemyOverride(Robot myRobot, EnemyData[] enemys) {
        EnemyData enemy = lastEnemy;
        double distSq = Utils.getDistSq(myRobot.getX(), myRobot.getY(), lastEnemy.getX(), lastEnemy.getY());
        for (EnemyData e : enemys) {
            if (e.isDead())
                continue;
            double distTemp = Utils.getDistSq(myRobot.getX(), myRobot.getY(), e.getX(), e.getY());
            if (distTemp < distSq * CLOSER_MARGEN) {
                enemy = e;
                distSq = distTemp;
            }
        }
        return enemy;
    }

    public TeammateData getTeammateOverride(Robot myRobot, TeammateData[] teammates) {
        TeammateData teammate = lastTeammate;
        double distSq = Utils.getDistSq(myRobot.getX(), myRobot.getY(), lastTeammate.getX(), lastTeammate.getY());
        for (TeammateData t : teammates) {
            if (t.isDead())
                continue;
            double distTemp = Utils.getDistSq(myRobot.getX(), myRobot.getY(), t.getX(), t.getY());
            if (distTemp < distSq * CLOSER_MARGEN) {
                teammate = t;
                distSq = distTemp;
            }
        }
        return teammate;
    }

}