package kid.Communication;

import robocode.TeamRobot;
import kid.Data.Robot.*;
import kid.Data.Virtual.*;

public class Data implements java.io.Serializable {
    private static final long serialVersionUID = 5131684452528873817L;

    private Sender Sender;
    // private EnemyData[] EnemyData;
    private VirtualBullet[] VirtualBullets;

    public Data(TeamRobot MyRobot, EnemyData[] EnemyData, VirtualBullet[] VirtualBullets) {
        Sender = new Sender(MyRobot);
        // this.EnemyData = EnemyData;
        this.VirtualBullets = VirtualBullets;
    }

    public TeammateData getSender() {
        return Sender.getTeammate();
    }

//    public EnemyData[] getEnemyRobots() {
//        return new EnemyData[0];
//    }

    public VirtualBullet[] getVirtualBullets() {
        return VirtualBullets;
    }

}
