package kid.segmentation;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.io.*;
import java.util.*;

import kid.*;
import kid.data.*;
import robocode.RobocodeFileOutputStream;

// TODO documentation: class (10% complete)
// BORED code: cloneable?

public class Leaf<E extends Segmentable, F extends Data, G extends Data> implements Cloneable, Serializable, Printable, Drawable {

   /**
    * Determines if a de-serialized file is compatible with this class.<BR>
    * <BR>
    * Maintainers must change this value if and only if the new version of this class is
    * not compatible with old versions.
    */
   private static final long serialVersionUID = 1359764339473097463L;

   public static final int MAX_LEAF_SIZE = 40;

   public static final int DATA_BINS = 41;
   public static final int SEGMENTER_BINS = 41;

   private LinkedList<E> dataLog;

   private Segmenter<E, F, G>[] segmenters;
   private List<double[][]> segmenterGraphs;
   private double[] maxGraphValues;

   private List<List<Point2D>> additions;

   public Leaf(final Segmenter<E, F, G>[] segmenters) {
      init(segmenters);
   }

   private void init(final Segmenter<E, F, G>[] segmenters) {
      dataLog = new LinkedList<E>();
      this.segmenters = segmenters;

      maxGraphValues = new double[segmenters.length];

      segmenterGraphs = new ArrayList<double[][]>(segmenters.length);
      for (int i = 0; i < segmenters.length; i++)
         segmenterGraphs.add(new double[DATA_BINS][SEGMENTER_BINS]);

      additions = new ArrayList<List<Point2D>>(segmenters.length);
      for (int i = 0; i < segmenters.length; i++)
         additions.add(new ArrayList<Point2D>());
   }

   public void add(final E data, final F view, final G reference) {
      dataLog.add(data);

      double first = data.getIndex(DATA_BINS);

      for (int i = 0; i < segmenterGraphs.size(); i++) {
         double[][] double2D = segmenterGraphs.get(i);
         double second = segmenters[i].getIndex(view, reference, SEGMENTER_BINS);

         additions.get(i).add(new Point2D.Double(first, second));

         for (int j = 0; j < DATA_BINS; j++) {
            for (int k = 0; k < SEGMENTER_BINS; k++) {
               double addedHeight = 5.0D / (Utils.sqr(j - first) + Utils.sqr(k - second) + 5.0D);
               double2D[j][k] += addedHeight;
               maxGraphValues[i] = Math.max(double2D[j][k], maxGraphValues[i]);
            }
         }
      }
   }

   protected void clear() {
      dataLog.clear();
   }

   public LinkedList<E> getDataLog() {
      return dataLog;
   }


   public void print(PrintStream console) {
      // TODO method stub
   }

   public void print(RobocodeFileOutputStream output) {
      // TODO method stub
   }

   public void draw(final RobocodeGraphicsDrawer grid, String commandString) {
      int initialX = 20;
      int initialY = 20;
      int pointSize = 2;
      int pointDistX = 5;
      int pointDistY = 5;
      int segmenterDist = 20;

      grid.setColor(Colors.WHITE);
      grid.drawString("-1", initialX / 2.0D - 5.0D, initialY - pointDistY / 2.0D);
      grid.drawString(" 0", initialX / 2.0D - 5.0D, initialY + (DATA_BINS - 1) * pointDistY / 2.0D - pointDistY / 2.0D);
      grid.drawString(" 1", initialX / 2.0D - 5.0D, initialY + (DATA_BINS - 1) * pointDistY - pointDistY / 2.0D);

      for (int i = 0; i < segmenterGraphs.size(); i++) {

         double[][] double2D = segmenterGraphs.get(i);
         double maxValue = maxGraphValues[i];

         grid.setColor(Colors.WHITE);
         grid.drawString(String.valueOf((int) segmenters[i].minValue), initialX + (SEGMENTER_BINS * pointDistX * i + segmenterDist * i) - 5.0D,
                         initialX + pointDistY * SEGMENTER_BINS + 5);
         grid.drawString(String.valueOf((int) segmenters[i].midValue), initialX + (SEGMENTER_BINS * pointDistX * i + segmenterDist * i)
                                                                       + (DATA_BINS - 1) / 2.0D * pointDistY - 5.0D, initialX + pointDistY
                                                                                                                     * SEGMENTER_BINS + 5);
         grid.drawString(String.valueOf((int) segmenters[i].maxValue), initialX + (SEGMENTER_BINS * pointDistX * i + segmenterDist * i)
                                                                       + (DATA_BINS - 1) * pointDistY - 5.0D, initialX + pointDistY
                                                                                                              * SEGMENTER_BINS + 5);

         for (int j = 0; j < DATA_BINS; j++) {
            for (int k = 0; k < SEGMENTER_BINS; k++) {

               double value = double2D[j][k];
               float hue = (float) (Utils.TWO_THIRDS + Utils.ONE_THIRD * (value / maxValue));
               Color color = Color.getHSBColor(hue, 1.0F, 0.5F);

               grid.setColor((j == (DATA_BINS - 1) / 2.0D ? Colors.YELLOW : color));
               grid.fillOvalCenter(initialX + k * pointDistX + (SEGMENTER_BINS * pointDistX * i + segmenterDist * i), initialY + j * pointDistY,
                                   pointSize, pointSize);

            }
         }

         grid.setColor(Colors.GREEN);
         for (Point2D p : additions.get(i)) {
            int j = (int) p.getX();
            int k = (int) p.getY();
            grid.fillOvalCenter(initialX + k * pointDistX + (SEGMENTER_BINS * pointDistX * i + segmenterDist * i), initialY + j * pointDistY,
                                pointSize, pointSize);
         }
      }
   }
   @Override
   protected void finalize() throws Throwable {
      // BORED code: update 'finalize()'
      clear();
      dataLog = null;
      super.finalize();
   }

}
