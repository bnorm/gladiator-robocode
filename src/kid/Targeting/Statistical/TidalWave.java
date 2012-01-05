package kid.Targeting.Statistical;

import java.awt.*;

import kid.*;
import kid.Movement.*;
import kid.Data.RobotVector;
import kid.Data.Robot.*;
import kid.Data.Virtual.DataWave;
import robocode.*;
import robocode.Robot;

public class TidalWave extends StatisticalTargeting {

    private RobotMovement m;

    public TidalWave(Robot MyRobot) {
        super(MyRobot);
        m = new RobotMovement(MyRobot);
    }

    public TidalWave(AdvancedRobot MyRobot) {
        super(MyRobot);
        m = new RobotMovement(MyRobot);
    }

    public TidalWave(TeamRobot MyRobot) {
        super(MyRobot);
        m = new RobotMovement(MyRobot);
    }

    public double getTargetingAngle(EnemyData EnemyRobot, double FirePower) {
        if (EnemyRobot.isDead())
            return MyRobot.getGunHeading();
        else if (EnemyRobot.getEnergy() == 0.0)
            return i.AngleTo(EnemyRobot);
        else if (i.getOthers() > 1) {
            return i.AngleTo(EnemyRobot);
        }
        DataWave[] ewaves = EnemyRobot.getWaves();
        int mid_sector = 0;
        double ex = EnemyRobot.getX(), ey = EnemyRobot.getY();
        double eh = EnemyRobot.getHeading(), ev = EnemyRobot.getVelocity();
        long time = i.getTime();
        double dist = i.DistTo(EnemyRobot), angle = i.AngleTo(EnemyRobot);
        for (int w = 0; w < ewaves.length; w++) {
            DataWave wave = ewaves[w];
            if (wave.distToImpact(EnemyRobot, i.getTime() + 1) < 0.0)
                continue;
            double[] wave_sectors = wave.getSectors();
            mid_sector = (wave_sectors.length - 1) / 2;

            RobotVector evec = new RobotVector(ex, ey, eh, ev);
            long foreward_hit_time = time;
            for (;; foreward_hit_time++) {
                evec.getNextVector(0, 8);
                m.ajustVectorForWall(evec);
                if (wave.testHit(evec.getX(), evec.getY(), foreward_hit_time)) {
                    break;
                }
            }
            int foreward_index = Utils.getIndex(wave.getGuessFactor(evec.getX(), evec.getY()), wave_sectors.length);

            evec = new RobotVector(ex, ey, eh, ev);
            long backward_hit_time = time;
            for (;; backward_hit_time++) {
                evec.getNextVector(0, -8);
                m.ajustVectorForWall(evec);
                if (wave.testHit(evec.getX(), evec.getY(), backward_hit_time)) {
                    break;
                }
            }
            int backward_index = Utils.getIndex(wave.getGuessFactor(evec.getX(), evec.getY()), wave_sectors.length);

            int min_index = foreward_index;
            for (int i = Math.min(foreward_index, backward_index); i < Math.max(foreward_index, backward_index); i++) {
                if (wave_sectors[i] < wave_sectors[min_index] && i != mid_sector)
                    min_index = i;
            }
            angle = i.AngleTo(ex, ey);
            double angleoffset = Utils.getAngleOffset(MyRobot, EnemyRobot, Utils.getGuessFactor(min_index,
                    wave_sectors.length), FirePower);
            angleoffset *= wave.distToImpact(ex, ey, time)/dist;
            ex = Utils.getX(i.getX(), dist, angle + angleoffset);
            ey = Utils.getY(i.getY(), dist, angle + angleoffset);
            time = (backward_hit_time + foreward_hit_time) / 2;
        }
        DataWave wave = new DataWave(MyRobot, EnemyRobot, FirePower, EnemyRobot.getSectors());
        double[] wave_sectors = wave.getSectors();

        RobotVector evec = new RobotVector(ex, ey, eh, ev);
        long foreward_hit_time = time;
        for (;; foreward_hit_time++) {
            evec.getNextVector(0, 8);
            m.ajustVectorForWall(evec);
            if (wave.testHit(evec.getX(), evec.getY(), foreward_hit_time)) {
                break;
            }
        }
        int foreward_index = Utils.getIndex(wave.getGuessFactor(evec.getX(), evec.getY()), wave_sectors.length);

        evec = new RobotVector(ex, ey, eh, ev);
        long backward_hit_time = time;
        for (;; backward_hit_time++) {
            evec.getNextVector(0, -8);
            m.ajustVectorForWall(evec);
            if (wave.testHit(evec.getX(), evec.getY(), backward_hit_time)) {
                break;
            }
        }
        int backward_index = Utils.getIndex(wave.getGuessFactor(evec.getX(), evec.getY()), wave_sectors.length);

        int min_index = foreward_index;
        for (int i = Math.min(foreward_index, backward_index); i < Math.max(foreward_index, backward_index); i++) {
            if (wave_sectors[i] < wave_sectors[min_index] && i != mid_sector)
                min_index = i;
        }
        angle = i.AngleTo(ex, ey);
        double angleoffset = Utils.getAngleOffset(MyRobot, EnemyRobot, Utils.getGuessFactor(min_index,
                wave_sectors.length), FirePower);
        angleoffset *= wave.distToImpact(ex, ey, i.getTime())/dist;
        ex = Utils.getX(i.getX(), dist, angle + angleoffset);
        ey = Utils.getY(i.getY(), dist, angle + angleoffset);
        
        return i.AngleTo(ex, ey);
    }

    public String getNameOfTargeting() {
        return "TidalWave";
    }

    public Color getTargetingColor() {
        return Color.CYAN;
    }

    public void drawTargeting(RobocodeGraphicsDrawer g, EnemyData EnemyRobot, double FirePower) {
        if (EnemyRobot.isDead())
            return;
        else if (EnemyRobot.getEnergy() == 0.0)
            return;
        int mid_sector = 0;
        DataWave[] ewaves = EnemyRobot.getWaves();
        double ex = EnemyRobot.getX(), ey = EnemyRobot.getY();
        double eh = EnemyRobot.getHeading(), ev = EnemyRobot.getVelocity();
        long time = i.getTime();
        double dist = i.DistTo(EnemyRobot), angle = i.AngleTo(EnemyRobot);
        for (int w = 0; w < ewaves.length; w++) {
            DataWave wave = ewaves[w];
            if (wave.distToImpact(EnemyRobot, i.getTime() + 1) < 0.0)
                continue;
            double[] wave_sectors = wave.getSectors();
            mid_sector = (wave_sectors.length - 1) / 2;

            RobotVector evec = new RobotVector(ex, ey, eh, ev);
            long foreward_hit_time = time;
            for (;; foreward_hit_time++) {
                evec.getNextVector(0, 8);
                m.ajustVectorForWall(evec);
                if (wave.testHit(evec.getX(), evec.getY(), foreward_hit_time)) {
                    break;
                }
            }
            int foreward_index = Utils.getIndex(wave.getGuessFactor(evec.getX(), evec.getY()), wave_sectors.length);

            evec = new RobotVector(ex, ey, eh, ev);
            long backward_hit_time = time;
            for (;; backward_hit_time++) {
                evec.getNextVector(0, -8);
                m.ajustVectorForWall(evec);
                if (wave.testHit(evec.getX(), evec.getY(), backward_hit_time)) {
                    break;
                }
            }
            int backward_index = Utils.getIndex(wave.getGuessFactor(evec.getX(), evec.getY()), wave_sectors.length);

            int min_index = foreward_index;
            for (int i = Math.min(foreward_index, backward_index); i < Math.max(foreward_index, backward_index); i++) {
                if (wave_sectors[i] < wave_sectors[min_index] && i != mid_sector)
                    min_index = i;
            }
            angle = i.AngleTo(ex, ey);
            double angleoffset = Utils.getAngleOffset(MyRobot, EnemyRobot, Utils.getGuessFactor(min_index,
                    wave_sectors.length), FirePower);
            angleoffset *= wave.distToImpact(ex, ey, time)/dist;
            ex = Utils.getX(i.getX(), dist, angle + angleoffset);
            ey = Utils.getY(i.getY(), dist, angle + angleoffset);
            time = (backward_hit_time + foreward_hit_time) / 2;
            g.setColor(Color.WHITE);
            g.drawString(String.valueOf(w), ex, ey);
            g.setColor(Color.GREEN);
            g.fillOval(ex, ey, 5, 5);
        }
        DataWave wave = new DataWave(MyRobot, EnemyRobot, FirePower, EnemyRobot.getSectors());
        double[] wave_sectors = wave.getSectors();

        RobotVector evec = new RobotVector(ex, ey, eh, ev);
        long foreward_hit_time = time;
        for (;; foreward_hit_time++) {
            evec.getNextVector(0, 8);
            m.ajustVectorForWall(evec);
            if (wave.testHit(evec.getX(), evec.getY(), foreward_hit_time)) {
                break;
            }
        }
        int foreward_index = Utils.getIndex(wave.getGuessFactor(evec.getX(), evec.getY()), wave_sectors.length);

        evec = new RobotVector(ex, ey, eh, ev);
        long backward_hit_time = time;
        for (;; backward_hit_time++) {
            evec.getNextVector(0, -8);
            m.ajustVectorForWall(evec);
            if (wave.testHit(evec.getX(), evec.getY(), backward_hit_time)) {
                break;
            }
        }
        int backward_index = Utils.getIndex(wave.getGuessFactor(evec.getX(), evec.getY()), wave_sectors.length);

        int min_index = foreward_index;
        for (int i = Math.min(foreward_index, backward_index); i < Math.max(foreward_index, backward_index); i++) {
            if (wave_sectors[i] < wave_sectors[min_index] && i != mid_sector)
                min_index = i;
        }
        angle = i.AngleTo(ex, ey);
        double angleoffset = Utils.getAngleOffset(MyRobot, EnemyRobot, Utils.getGuessFactor(min_index,
                wave_sectors.length), FirePower);
        angleoffset *= wave.distToImpact(ex, ey, i.getTime())/dist;
        ex = Utils.getX(i.getX(), dist, angle + angleoffset);
        ey = Utils.getY(i.getY(), dist, angle + angleoffset);
        g.setColor(Color.WHITE);
        g.drawString(String.valueOf(ewaves.length), ex, ey);
        g.setColor(Color.GREEN);
        g.fillOval(ex, ey, 5, 5);
    }
}
