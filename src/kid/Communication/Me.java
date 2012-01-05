package kid.Communication;

import robocode.*;
import kid.Data.Robot.TeammateData;

public class Me implements java.io.Serializable {

    private static final long serialVersionUID = -3441293913999228649L;

    private TeammateData Teammate;

    public Me(TeamRobot MyRobot) {
        Teammate = new TeammateData(MyRobot);
    }

    public TeammateData getTeammate() {
        return Teammate;
    }
 
}
