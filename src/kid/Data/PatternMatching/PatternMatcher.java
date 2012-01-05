package kid.Data.PatternMatching;

import kid.*;
import kid.Data.Robot.*;
import kid.Segmentation.*;
import kid.Segmentation.Segmentars.*;

public class PatternMatcher {

    private Pattern Head;
    private Pattern Foot;
    private PMBin FoldedRobotMovement;
    private long MaxLegth = 15000;
    private long MaxMatchLegth = 400;

    private Node[] Segments;
    private Observation Latest;

    public PatternMatcher() {
        Head = null;
        Foot = null;
        FoldedRobotMovement = new PMBin();
        Segments = null;
        Latest = null;
    }

    public PatternMatcher(Segmentar[] segs) {
        Head = null;
        Foot = null;
        FoldedRobotMovement = new PMBin();
        Segments = new Node[segs.length];
        for (int n = 0; n < Segments.length; n++)
            Segments[n] = new Node(segs[n], new PMBin());
    }


    public void addPattern(Pattern p, Observation latest) {
        if (latest == null && p == null) {
            p = new Pattern(getArrayPosition() + 1);
            p.setPrevious(Head);
            Head = p;
            if (p.getPrevious() != null) {
                p.getPrevious().setNext(p);
            }
        } else {
            if (Head == null)
                Foot = p;
            latest.setPattern(p);
            p.setPrevious(Head);
            Head = p;
            if (p.getPrevious() != null) {
                p.getPrevious().setNext(p);
            }
            FoldedRobotMovement.add(latest);

            Latest = latest;
            if (latest != null)
                if (Segments != null) {
                    for (int s = 0; s < Segments.length; s++)
                        Segments[s].addObservation(latest, false);
                }
        }
        if (Head.getIndex() - Foot.getIndex() > MaxLegth) {
            Pattern f = Foot;
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

    public Pattern MatchPattern() {
        if (Head != null) {
            Pattern Start = Head.getPreviousIndex();
            Pattern Current = Head.getPreviousIndex();
            int length = 0;
            int t = 0;
            for (; Current != null; t++) {
                int l = getLength(Current);
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

    private int getLength(Pattern StartPattern) {
        Pattern p = StartPattern;
        Pattern s = Head;
        int t = 0;
        for (; p != null; t++) {
            if (p.equals(s) && !s.equals(Pattern.NullPattern)) {
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
        Pattern p = Head;
            for (int t = 0; t < 200 && p != null && !p.equals(Pattern.NullPattern); t++) {
                eX -= p.getVelocity() * Utils.sin(eH);
                eY -= p.getVelocity() * Utils.cos(eH);
                eH -= p.getHeadingChange();
                g.drawOval((int) eX, (int) eY, 2, 2);
                p = p.getPrevious();
            }
    }

}