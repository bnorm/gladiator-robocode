package kid.Communication;

import robocode.*;
import kid.Data.Robot.TeammateData;

public class Sender implements java.io.Serializable {
    private static final long serialVersionUID = -3441293913999228649L;

    private TeammateData Sender;

    public Sender(TeamRobot MyRobot) {
        Sender = new TeammateData(MyRobot);
    }

    public TeammateData getTeammate() {
        return Sender;
    }
 
}
