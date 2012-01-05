package kid.Movement.Melee;

import java.awt.geom.*;

import kid.Data.Robot.*;
import kid.Data.Virtual.*;
import robocode.*;
import kid.RobocodeGraphicsDrawer;
import kid.Data.Gravity.GravityPoint;

public class AntiGravity_wMinimumRiskPoint extends AntiGravity {

    private MinimumRiskPoint MinimumRiskPoint;

    public AntiGravity_wMinimumRiskPoint(Robot MyRobot) {
        super(MyRobot);
        MinimumRiskPoint = new MinimumRiskPoint(MyRobot);
    }

    public AntiGravity_wMinimumRiskPoint(AdvancedRobot MyRobot) {
        super(MyRobot);
        MinimumRiskPoint = new MinimumRiskPoint(MyRobot);
    }

    public AntiGravity_wMinimumRiskPoint(TeamRobot MyRobot) {
        super(MyRobot);
        MinimumRiskPoint = new MinimumRiskPoint(MyRobot);
    }

    /*
        public AntiGravity addHeadOnAvodence() {
            HeadOnAvodence = true;
            return this;
        }
     */

    public void doMovement(EnemyData[] EnemyData) {
        Point2D point = new Point2D.Double();
        point.setLocation(MinimumRiskPoint.getMinimumRiskPoint(EnemyData));
        GravityEngine.addPoint(new GravityPoint(point.getX(), point.getY(), 50.0, i.getTime(), 1));
        super.doMovement(EnemyData);
    }


    public void doMovement(EnemyData[] EnemyData, TeammateData[] TeammateData, VirtualBullet[] VirtualBullets) {
        Point2D point = new Point2D.Double();
        point.setLocation(MinimumRiskPoint.getMinimumRiskPoint(EnemyData, TeammateData, VirtualBullets));
        GravityEngine.addPoint(new GravityPoint(point.getX(), point.getY(), 50.0, i.getTime(), 1));
        super.doMovement(EnemyData, TeammateData, VirtualBullets);
    }

    public void inEvent(Event e) {
        MinimumRiskPoint.inEvent(e);
        super.inEvent(e);
    }

    public void drawMovement(RobocodeGraphicsDrawer g, EnemyData[] EnemyData) {
        MinimumRiskPoint.drawMovement(g, EnemyData);
        super.drawMovement(g, EnemyData);
    }

    public void drawMovement(RobocodeGraphicsDrawer g, EnemyData[] EnemyData, TeammateData[] TeammateData,
                             VirtualBullet[] VirtualBullets) {
        MinimumRiskPoint.drawMovement(g, EnemyData, TeammateData, VirtualBullets);
        super.drawMovement(g, EnemyData, TeammateData, VirtualBullets);
    }

}
