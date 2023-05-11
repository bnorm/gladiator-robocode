package kid.Segmentation;

import kid.Data.Robot.*;

public class Log {

    private LinkObservation First;
    private LinkObservation Last;
    private int length;

    public Log() {
        length = 0;
        First = null;
        Last = null;
    }

    public void add(Observation o) {
        if (o == null)
            return;
        LinkObservation lo = new LinkObservation(o);
        if (First == null)
            First = lo;
        lo.setPrevious(Last);
        Last = lo;
        if (lo.getPrevious() != null) {
            lo.getPrevious().setNext(lo);
        }
        length++;
    }

    public int size() {
        return length;
    }

    public Observation[] getList() {
        Observation[] Observations = new Observation[size()];
        LinkObservation o = First;
        for (int i = 0; i < Observations.length; i++) {
            Observations[i] = o.getObservation();
            o = o.getNext();
        }
        return Observations;
    }

    public void clear() {
        length = 0;
        First = null;
        Last = null;
    }

    private class LinkObservation {
        private LinkObservation Next;
        private Observation Observation;
        private LinkObservation Previous;

        public LinkObservation(Observation o) {
            Observation = o;
        }

        public LinkObservation getNext() {
            return Next;
        }
        public Observation getObservation() {
            return Observation;
        }
        public LinkObservation getPrevious() {
            return Previous;
        }

        public void setNext(LinkObservation o) {
            Next = o;
        }
        public void setPrevious(LinkObservation o) {
            Previous = o;
        }
    }
}
