package kid.Data.PatternMatching;

import kid.*;
import kid.Data.Robot.*;
import kid.Segmentation.*;
import kid.Segmentation.Segmentars.*;

public class PolarPatternMatcher {

    private PolarPattern Head;
    private PolarPattern Foot;
    private PMBin FoldedRobotMovement;
    private long MaxLegth = 15000;
    private long MaxMatchLegth = 400;

    private Node SegmentTree;
    private Observation Latest;

    public PolarPatternMatcher() {
        Head = null;
        Foot = null;
        FoldedRobotMovement = new PMBin();
        SegmentTree = null;
        Latest = null;
    }

    public PolarPatternMatcher(Segmentar[] segs) {
        Head = null;
        Foot = null;
        FoldedRobotMovement = new PMBin();
        SegmentTree = new Node(segs, new PMBin());
    }


    public void addPattern(PolarPattern p, Observation latest) {
        if (p == null) {
            p = new PolarPattern(getArrayPosition() + 1);
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
            latest.setPolarPattern(p);
            p.setPrevious(Head);
            Head = p;
            if (p.getPrevious() != null) {
                p.getPrevious().setNext(p);
            }
            FoldedRobotMovement.add(latest);

            Latest = latest;
            if (latest != null)
                if (SegmentTree != null)
                    SegmentTree.addObservation(latest, false);
        }
        if (Head != null && Foot != null && Head.getIndex() - Foot.getIndex() > MaxLegth) {
            PolarPattern f = Foot;
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

    public PolarPattern MatchPattern() {
        if (Head != null) {
            PolarPattern Start = Head.getPreviousIndex();
            PolarPattern Current = Head.getPreviousIndex();
            int index = getArrayPosition();
            int length = 0;
            int t = 0;
            for (; Current != null; t++) {
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

    private int getLength(PolarPattern StartPattern) {
        PolarPattern p = StartPattern;
        PolarPattern s = Head;
        int t = 0;
        for (; p != null; t++) {
            if (p.equals(s) && !s.equals(PolarPattern.NullPattern)) {
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

    public void drawRecentMovement(RobocodeGraphicsDrawer g, EnemyData EnemyRobot) {
        g.setColor(Colors.GREEN);
        double eX = EnemyRobot.getX(), eY = EnemyRobot.getY(), eH = EnemyRobot.getHeading();
        PolarPattern p = Head;
        for (int t = 0; t < 200 && p != null && !p.equals(PolarPattern.NullPattern); t++) {
            eX -= p.getVelocity() * Utils.sin(eH);
            eY -= p.getVelocity() * Utils.cos(eH);
            eH -= p.getHeadingChange();
            g.drawOval((int) eX, (int) eY, 2, 2);
            p = p.getPrevious();
        }
    }

}