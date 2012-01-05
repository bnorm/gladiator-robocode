package kid.Communication;

import robocode.TeamRobot;
import kid.Data.Robot.*;
import kid.Data.Virtual.*;

public class Data implements java.io.Serializable {
    
    private static final long serialVersionUID = 5131684452528873817L;
    
    private Me Me;
    private EnemyData[] EnemyData;
    private VirtualBullet[] VirtualBullets;

    public Data(TeamRobot MyRobot, EnemyData[] EnemyData, VirtualBullet[] VirtualBullets) {
        Me = new Me(MyRobot);
        this.EnemyData = EnemyData;
        this.VirtualBullets = VirtualBullets;
    }

    public TeammateData getTeammate() {
        return Me.getTeammate();
    }

    public EnemyData[] getEnemyRobots() {
        return EnemyData;
    }

    public VirtualBullet[] getVirtualBullets() {
        return VirtualBullets;
    }

}
