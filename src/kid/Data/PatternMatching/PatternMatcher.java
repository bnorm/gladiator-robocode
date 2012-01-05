package kid.Data.PatternMatching;

import kid.Colors;
import kid.Utils;
import kid.Data.Robot.EnemyData;
import kid.RobocodeGraphicsDrawer;

public class PatternMatcher implements java.io.Serializable {

    private Pattern[] RobotMovement;
    private int RobotMovementDeadPosition = 0;
    private long RobotMovementDeadPositionTime = Long.MAX_VALUE;
    private int RobotMovementIntPosition;

    private Pattern[] ProdicdedRobotMovement;
    //private int ProdicdedRobotMovementIntPosition;
    private static final int MaxTime = (int) (kid.Utils.getDist(0D, 0D, 5000D, 5000D) / kid.Utils.bulletVelocity(3D));

    private int RecentMovementStart;
    private int RecentMovementLength;
    private int RecentMovementLengthAjasted;

    public PatternMatcher(Pattern[] RobotMovement) {
        this.RobotMovement = RobotMovement;
        RobotMovementDeadPositionTime = Long.MAX_VALUE;
    }

    public Pattern[] MatchPattern(int arrayPosition, int length, long time) {
        RobotMovementIntPosition = arrayPosition;
        RecentMovementLengthAjasted = RecentMovementLength = length;

        int min = (arrayPosition) - RobotMovement.length;
        min = (min < 0 ? -1 : min);

        if (time < RobotMovementDeadPositionTime) {
            for (int i = arrayPosition - 1; i > min; i--) {
                if (RobotMovement[i % RobotMovement.length] == null) {
                    RobotMovementDeadPosition = i;
                    break;
                }
            }
        }
        RobotMovementDeadPositionTime = time;

        for (; RobotMovementDeadPosition > arrayPosition; )
            RobotMovementDeadPosition -= RobotMovement.length;

        RecentMovementLengthAjasted = Math.min((arrayPosition - RobotMovementDeadPosition) / 4,
                RecentMovementLengthAjasted);
        RecentMovementStart = (arrayPosition - RecentMovementLengthAjasted);

        int lengthl = RecentMovementLengthAjasted;
        int startl = 0;
        double difl = Double.POSITIVE_INFINITY;
        for (int i = (arrayPosition - (RecentMovementLengthAjasted * 2)); i > RobotMovementDeadPosition; i--) {
            double d = getDifference(i);
            if (difl > d) {
                difl = d;
                startl = i;
            }
        }
        for (int i = (RobotMovementDeadPosition - (RecentMovementLengthAjasted)); i > min; i--) {
            double d = getDifference(i);
            if (difl > d) {
                difl = d;
                startl = i;
            }
        }

        int lengths = 0;
        int starts = 0;
        double difs = Double.POSITIVE_INFINITY;
        /*
                 if ( RecentMovementLengthAjasted != length){

            RecentMovementLengthAjasted /= 2;
            RecentMovementStart = (arrayPosition - RecentMovementLengthAjasted);
            lengths = RecentMovementLengthAjasted;

            for (int i = (arrayPosition - (RecentMovementLengthAjasted * 2)); i > RobotMovementDeadPosition; i--) {
                double d = getDifference(i);
                if (difs > d) {
                    difs = d;
                    starts = i;
                }
            }
            for (int i = (RobotMovementDeadPosition - (RecentMovementLengthAjasted)); i > min; i--) {
                double d = getDifference(i);
                if (difs > d) {
                    difs = d;
                    starts = i;
                }
            }
                 }
         */

        int start = (difl < difs * 2 ? startl : starts);
        RecentMovementLengthAjasted = (difl < difs * 2 ? lengthl : lengths);

        ProdicdedRobotMovement = new Pattern[Math.min(arrayPosition - start, MaxTime)];

        int p = 0, rm = (start + RecentMovementLengthAjasted);
        for (; p < ProdicdedRobotMovement.length; p++, rm++) {
            ProdicdedRobotMovement[p] = RobotMovement[rm % RobotMovement.length];
        }
        return ProdicdedRobotMovement;
    }

    private double getDifference(int StartAt) {
        try {
            if (!RobotMovement[StartAt %
                RobotMovement.length].equals(RobotMovement[RecentMovementStart % RobotMovement.length]))
                return Double.POSITIVE_INFINITY;

            double dif = 0.0;
            int j = RecentMovementStart; int i = StartAt;
            for (; j < RecentMovementStart + RecentMovementLengthAjasted; i++, j++)
                dif += RobotMovement[i % RobotMovement.length].difference(RobotMovement[j % RobotMovement.length]);
            return dif;
        } catch (java.lang.NullPointerException e) {
            return Double.POSITIVE_INFINITY;
        }
    }

    public void drawRecentMovement(RobocodeGraphicsDrawer g, EnemyData EnemyRobot) {
        g.setColor(Colors.GREEN);
        double eX = EnemyRobot.getX(), eY = EnemyRobot.getY(), eH = EnemyRobot.getHeading();
        try {
            for (int t = RobotMovementIntPosition - 1; t > RobotMovementIntPosition - RecentMovementLengthAjasted; t--) {
                eX -= RobotMovement[t % RobotMovement.length].getVelocity() * Utils.sin(eH);
                eY -= RobotMovement[t % RobotMovement.length].getVelocity() * Utils.cos(eH);
                eH -= RobotMovement[t % RobotMovement.length].getHeadingChange();
                g.drawOval((int) eX, (int) eY, 2, 2);
            }
        } catch (Exception e) {}
    }

}
