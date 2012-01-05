package kid.Data.PatternMatching;

import kid.Data.Robot.*;
import kid.Segmentation.*;
import kid.Segmentation.Segmentars.*;

public class LatVelPatternMatcher {

    private LatVelPattern Head;
    private LatVelPattern Foot;
    private LVBin FoldedRobotMovement;
    private long MaxLegth = 15000;
    private long MaxMatchLegth = 400;

    private Node SegmentTree;
    private Observation Latest;

    public LatVelPatternMatcher() {
        Head = null;
        Foot = null;
        FoldedRobotMovement = new LVBin();
        SegmentTree = null;
        Latest = null;
    }

    public LatVelPatternMatcher(Segmentar[] segs) {
        Head = null;
        Foot = null;
        FoldedRobotMovement = new LVBin();
        SegmentTree = new Node(segs, new LVBin());
    }


    public void addPattern(LatVelPattern p, Observation latest) {
        if (p == null) {
            p = new LatVelPattern(getArrayPosition() + 1);
        }
        if (latest == null) {
            p.setPrevious(Head);
            Head = p;
            if (p.getPrevious() != null) {
                p.getPrevious().setNext(p);
            }
        } else {
            if (Foot == null)
                Foot = p;
            latest.setGFPattern(p);
            p.setPrevious(Head);
            Head = p;
            if (p.getPrevious() != null) {
                p.getPrevious().setNext(p);
            }
            FoldedRobotMovement.add(latest);
            if (SegmentTree != null)
                SegmentTree.addObservation(latest, false);


            Latest = latest;
        }
        if (Head != null && Foot != null && Head.getIndex() - Foot.getIndex() > MaxLegth) {
            LatVelPattern f = Foot;
            f.getNext().setPrevious(null);
            if (f.getNextIndex() != null)
                f.getNextIndex().setPreviousIndexPattern(null);
            if (f.getPrevious() != null)
                f.getPrevious().setNext(null);
            if (f.getPreviousIndex() != null)
                f.getPreviousIndex().setNextIndexPattern(null);
            Foot = f.getNext();
            f.clear();
            f = null;
        }
    }

    public int getArrayPosition() {
        if (Head != null)
            return Head.getIndex();
        else
            return -1;
    }

    public LatVelPattern MatchPattern() {
        if (Head != null) {
            LatVelPattern Start = Head.getPreviousIndex();
            LatVelPattern Current = Head.getPreviousIndex();
            int index = getArrayPosition();
            int length = 0;
            for (; Current != null;) {
                while (Current.getPreviousIndex() != null && index < Current.getIndex()) {
                    Current = Current.getPreviousIndex();
                }
                int l = getLength(Current);
                index -= l;
                if (l > length) {
                    Start = Current;
                    length = l;
                    if (l >= MaxMatchLegth)
                        break;
                }
                Current = Current.getPreviousIndex();
            }
            return Start;
        }
        return null;
    }

    private int getLength(LatVelPattern StartPattern) {
        LatVelPattern p = StartPattern;
        LatVelPattern s = Head;
        int t = 0;
        for (; p != null; t++) {
            if (p.equals(s) && !s.equals(LatVelPattern.NullPattern)) {
                p = p.getPrevious();
                s = s.getPrevious();
            } else {
                return t;
            }
            if (t >= MaxMatchLegth) {
                return t;
            }
        }
        return t;
    }

    /*
     * public void drawRecentMovement(RobocodeGraphicsDrawer g, EnemyData
     * EnemyRobot) { g.setColor(Colors.GREEN); DataWave[] waves =
     * EnemyRobot.getWaves(); double eX = EnemyRobot.getX(), eY =
     * EnemyRobot.getY(), eH = EnemyRobot.getHeading(); LatVelPattern p = Head;
     * if (waves.length == 0) return; double dist = waves[waves.length -
     * 1].getDist(EnemyRobot.getTime()); for (int t = 0; t < waves.length && p !=
     * null && !p.equals(GFPattern.NullPattern); t++) { DataWave w = waves[t];
     * double waveheading = w.getHeading(); waveheading +=
     * Utils.getAngleOffset(w.getStartX(), w.getStartY(), EnemyRobot,
     * p.getGuessFactor(), waves[t].getFirePower()); eX =
     * Utils.getX(w.getStartX(), dist, waveheading); eY =
     * Utils.getY(w.getStartY(), dist, waveheading); g.fillOvalCenter(eX, eY, 3,
     * 3); p = p.getPrevious(); } }
     */
}