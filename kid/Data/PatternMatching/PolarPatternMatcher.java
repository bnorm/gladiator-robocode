package kid.Data.PatternMatching;

import kid.*;
import kid.Data.Robot.*;
import kid.Segmentation.*;
import kid.Segmentation.Segmentars.*;

public class PolarPatternMatcher {

    private PolarPattern Head = null;
    private PolarPattern Foot = null;
    private long MaxLegth = 15000;
    private long MaxMatchLegth = 500;

    private PMBin FoldedRobotMovement = null;
    private Node SegmentTree = null;

    public PolarPatternMatcher() {
        FoldedRobotMovement = new PMBin();
    }

    public PolarPatternMatcher(Segmentar[] segs) {
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
            if (FoldedRobotMovement != null)
                FoldedRobotMovement.add(latest);
            if (SegmentTree != null)
                SegmentTree.addObservation(latest, false);

            if (SegmentTree != null && latest.getTime() % 100 == 0)
                SegmentTree.printTree();

        }
        if (Head != null && Foot != null && Head.getIndex() - Foot.getIndex() > MaxLegth) {
            PolarPattern f = Foot;
            Foot = f.getNext();
            f.clear();
            f = null;
        }
    }

    public int getArrayPosition() {
        if (Head != null)
            return Head.getIndex();
        return -1;
    }

    public PolarPattern MatchPattern() {
        if (Head != null) {
            PolarPattern Start = Head.getPreviousIndex();
            PolarPattern Current = Head.getPreviousIndex();
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