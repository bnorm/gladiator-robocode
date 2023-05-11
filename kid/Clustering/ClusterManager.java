package kid.Clustering;

import kid.Data.Robot.Observation;

public class ClusterManager {

    private Cluster[] _clusters;

    private LinkObservation _head;
    private LinkObservation _tail;

    private int _numObser;

    public ClusterManager(Cluster[] Clusters) {
        _clusters = Clusters;
        _head = null;
        _tail = null;
        _numObser = 0;
    }


    public void add(Observation o) {
        if (o == null)
            return;
        // if (_tail == null) {
        // _head = _tail = new LinkObservation(_head, null, o);
        // } else {
        _head = new LinkObservation(_head, null, o);
        // }
        _numObser++;
    }

    public Observation[] getCluster(int max, Observation o) {
        if (_clusters.length == 0 || _head == null)
            return null;
        if (_numObser <= max) {
            Observation[] obser = new Observation[_numObser];
            LinkObservation c = _head;
            int i = 0;
            for (; c != null;) {
                obser[i] = c.getObservation();
                i++;
                c = c.getPrevious();
            }
            return obser;
        } else {
            Observation[] bestObs = new Observation[max];
            double[] bestDiff = new double[max];
            int count = 0;

            for (int i = 0; i < max; i++)
                bestDiff[i] = Double.POSITIVE_INFINITY;

            LinkObservation c = _head;
            for (; c != null;) {
                double diff = 0;
                for (int i = 0; i < _clusters.length; i++) {
                    diff += _clusters[i].getDiff(c.getObservation(), o) / _clusters[i].getMaxDiff();
                }
                for (int i = 0; i < bestDiff.length; count++, i++) {
                    if (diff < bestDiff[count % bestDiff.length]) {
                        bestDiff[count % bestDiff.length] = diff;
                        bestObs[count % bestObs.length] = c.getObservation();
                        count++;
                        break;
                    }
                }
                c = c.getPrevious();
            }
            return bestObs;
        }
    }

    private class LinkObservation {
        private LinkObservation _next;
        private Observation _obs;
        private LinkObservation _previous;

        public LinkObservation(LinkObservation Previous, LinkObservation Next, Observation o) {
            _obs = o;
            _previous = Previous;
            _next = Next;
            if (Previous != null && Previous.hasNext())
                Previous.getNext().setPrevious(this);
            if (Next != null && Next.hasPrevious())
                Next.getPrevious().setNext(this);
        }

        public Observation getObservation() {
            return _obs;
        }

        public LinkObservation getNext() {
            return _next;
        }

        public LinkObservation getPrevious() {
            return _previous;
        }

        public boolean hasNext() {
            return _next != null;
        }

        public boolean hasPrevious() {
            return _previous != null;
        }

        public void setNext(LinkObservation o) {
            _next = o;
        }

        public void setPrevious(LinkObservation o) {
            _previous = o;
        }
    }
}
