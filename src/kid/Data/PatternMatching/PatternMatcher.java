package kid.Data.PatternMatching;

import java.util.*;

import kid.*;
import kid.Data.Robot.*;
import kid.Segmentation.*;
import kid.Segmentation.Segmentars.*;

public class PatternMatcher {

    private ArrayList RobotMovement;
    private PMBin FoldedRobotMovement;

    private Pattern[] ProdicdedRobotMovement;

    private static final int MaxTime = (int) (kid.Utils.getDist(0D, 0D, 5000D, 5000D) / kid.Utils.bulletVelocity(3D));

    // private int RecentMovementStart;
    private int RecentMovementLengthAjasted = 100;

    private Node[] Segments;
    private Observation Latest;


    public PatternMatcher() {
        RobotMovement = new ArrayList();
        FoldedRobotMovement = new PMBin();
        Segments = null;
        Latest = null;
    }

    public PatternMatcher(Segmentar[] segs) {
        RobotMovement = new ArrayList();
        FoldedRobotMovement = new PMBin();
        Segments = new Node[segs.length];
        for (int n = 0; n < Segments.length; n++)
            Segments[n] = new Node(segs[n], new PMBin());
    }


    public void addPattern(Pattern p, Observation latest) {
        if (latest != null)
            latest.setPattern(p);
        RobotMovement.add(p);
        FoldedRobotMovement.add(latest);
        Latest = latest;
        if (latest != null)
            if (Segments != null) {
                for (int s = 0; s < Segments.length; s++)
                    Segments[s].addObservation(latest, false);
            }
    }

    public int getArrayPosition() {
        return RobotMovement.size();
    }

    public Pattern[] MatchPattern(int length) {
        if (RobotMovement.size() == 0)
            return null;
        if (Segments == null) {
            return MatchPattern_wStrait(length);
        } else {
            return MatchPattern_wSegment(length);
        }
    }

    private Pattern[] MatchPattern_wSegment(int length) {
        RecentMovementLengthAjasted = Math.min((RobotMovement.size() - (int) Latest.getTime()) / 4, length);
        RecentMovementLengthAjasted = Math.max(RecentMovementLengthAjasted, 1);

        Pattern latestp = (Pattern) RobotMovement.get(RobotMovement.size() - 1);
        int indexlength = 0;
        for (int n = 0; n < Segments.length; n++) {
            indexlength += ((PMBin) Segments[n].getBin(Latest)).getPatterns(latestp).length;
        }
        int[] index = new int[indexlength];
        int patternposition = 0;
        for (int n = 0; n < Segments.length; n++) {
            int[] segmentp = ((PMBin) Segments[n].getBin(Latest)).getPatterns(latestp);
            for (int p = 0; p < segmentp.length; p++) {
                index[patternposition] = segmentp[p];
                patternposition++;
            }
        }

        int start = 0;
        double dif = Double.POSITIVE_INFINITY;
        int i = 0;
        for (; i < index.length; i++) {
            if (index[i] < RobotMovement.size() - RecentMovementLengthAjasted) {
                double d = getDifference(index[i]);
                if (dif > d) {
                    dif = d;
                    start = index[i];
                }
            }
        }

        ProdicdedRobotMovement = new Pattern[Math.min(RobotMovement.size() - start, MaxTime)];
        int p = 0, rm = (start + 1);
        for (; p < ProdicdedRobotMovement.length && rm < RobotMovement.size(); p++, rm++) {
            ProdicdedRobotMovement[p] = (Pattern) RobotMovement.get(rm);
        }

        return ProdicdedRobotMovement;
    }

    private Pattern[] MatchPattern_wStrait(int length) {
        RecentMovementLengthAjasted = Math.min((RobotMovement.size() - (int) Latest.getTime()) / 4, length);
        RecentMovementLengthAjasted = Math.max(RecentMovementLengthAjasted, 1);

        Pattern latestp = (Pattern) RobotMovement.get(RobotMovement.size() - 1);
        int[] indexs = FoldedRobotMovement.getPatterns(latestp);

        int start = 0;
        double dif = Double.POSITIVE_INFINITY;
        int i = 0;
        for (; i < indexs.length; i++) {
            if (indexs[i] < RobotMovement.size() - RecentMovementLengthAjasted) {
                double d = getDifference(indexs[i]);
                if (d == 0.0) {
                    dif = d;
                    start = indexs[i];
                    break;
                } else if (dif > d) {
                    dif = d;
                    start = indexs[i];
                }
            } else {
                // System.out.println("Pattern out of reach");
            }
        }

        // System.out.println(dif);

        ProdicdedRobotMovement = new Pattern[Math.min(RobotMovement.size() - start, MaxTime)];
        int p = 0, rm = (start + 1);
        for (; p < ProdicdedRobotMovement.length && rm < RobotMovement.size(); p++, rm++) {
            ProdicdedRobotMovement[p] = (Pattern) RobotMovement.get(rm);
        }

        return ProdicdedRobotMovement;
    }

    private double getDifference(int StartAt) {
        try {
            double dif = 0.0;
            int j = RobotMovement.size() - 1, i = StartAt, t = 0;
            for (; t < RecentMovementLengthAjasted; i--, j--, t++) {
                dif += ((Pattern) RobotMovement.get(i)).difference((Pattern) RobotMovement.get(j));
            }
            return dif;
        } catch (Exception e) {
            return Double.POSITIVE_INFINITY;
        }
    }

    /*
     * private int getLength(int StartAt) { try { int j = RobotMovement.size() -
     * 1, i = StartAt, t = 0; for (; t < RecentMovementLengthAjasted; i--, j--,
     * t++) { if (!((Pattern)
     * RobotMovement.get(i)).equals(RobotMovement.get(j))); return t; } return
     * t; } catch (Exception e) { return Integer.MAX_VALUE; } }
     */

    public void drawRecentMovement(RobocodeGraphicsDrawer g, EnemyData EnemyRobot) {
        g.setColor(Colors.GREEN);
        double eX = EnemyRobot.getX(), eY = EnemyRobot.getY(), eH = EnemyRobot.getHeading();
        try {
            for (int t = RobotMovement.size() - 1; t > RobotMovement.size() - RecentMovementLengthAjasted; t--) {
                eX -= ((Pattern) RobotMovement.get(t)).getVelocity() * Utils.sin(eH);
                eY -= ((Pattern) RobotMovement.get(t)).getVelocity() * Utils.cos(eH);
                eH -= ((Pattern) RobotMovement.get(t)).getHeadingChange();
                g.drawOval((int) eX, (int) eY, 2, 2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
