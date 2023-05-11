package kid.data.virtual;

import kid.*;
import kid.data.Data;
import kid.data.factor.GuessFactor;
import kid.data.robot.RobotData;
import kid.segmentation.*;
import robocode.*;

// TODO documentation: class

/**
 * @author Brian Norman
 * @version 0.0.1 beta
 */
public class DataWave<E extends Segmentable, F extends Data, G extends Data> extends VirtualWave {

   private static final long serialVersionUID = 4650614154195009930L;

   protected E[] data;
   protected F view;
   protected G reference;

   public DataWave(double startX, double startY, double heading, double firePower, long creationTime, E[] data, F view, G reference) {
      super(startX, startY, heading, firePower, creationTime);
      init(data, view, reference);
   }

   public DataWave(Bullet bullet, Robot firer, RobotData target, E[] data, F view, G reference) {
      super(firer.getX(), firer.getY(), Utils.getAngle(firer.getX(), firer.getY(), target.getX(), target.getY()), bullet.getPower(),
            firer.getTime());
      init(data, view, reference);
   }

   private void init(E[] data, F view, G reference) {
      this.data = data;
      this.view = view;
      this.reference = reference;
   }

   public E[] getData() {
      return data;
   }

   public F getView() {
      return view;
   }

   public G getReference() {
      return reference;
   }

   public void draw(RobocodeGraphicsDrawer grid, String commandString) {
      super.draw(grid, commandString);
      if (data instanceof GuessFactor[] && view instanceof RobotData) {
         GuessFactor[] factors = (GuessFactor[]) data;

         RobotData robot = (RobotData) view;
         int direction = Utils.getDirection(robot.getHeading(), robot.getVelocity(), getHeading());

         double[] values = new double[Leaf.DATA_BINS];
         double maxvalue = 0.01D;
         double dist = getDist(grid.getTime()) - getVelocity();
         int halfbins = (Leaf.DATA_BINS - 1) / 2;

         for (int i = 0; i < Leaf.DATA_BINS; i++) {
            for (GuessFactor gf : factors) {
               values[i] += 5.0D / (Utils.sqr(i - (halfbins + halfbins * gf.getGuessFactor())) + 5.0D);
            }
            maxvalue = Math.max(maxvalue, values[i]);
         }

         for (int i = 0; i < Leaf.DATA_BINS; i++) {
            if (i == halfbins)
               grid.setColor(Colors.YELLOW);
            else if (i > halfbins)
               grid.setColor(Colors.GREEN);
            else
               grid.setColor(Colors.RED);

            double radious = (values[i] / maxvalue) * 5.0D + 1.0D;
            double angle = getHeading() + (direction * getMaxEscapeAngle() * Utils.getGuessFactor(i, Leaf.DATA_BINS));
            grid.fillOvalCenter(Utils.getX(getStartX(), dist, angle), Utils.getY(getStartY(), dist, angle), radious, radious);
         }
      }
   }
}