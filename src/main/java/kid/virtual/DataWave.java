package kid.virtual;

import kid.data.Data;
import kid.graphics.RGraphics;
import kid.robot.RobotData;
import kid.segmentation.Segmentable;
import kid.utils.Utils;
import robocode.Bullet;
import robocode.Robot;

// TODO documentation: class

/**
 * @author Brian Norman
 * @version 0.0.1 beta
 */
public class DataWave<E extends Data, F extends Segmentable, G extends Segmentable> extends VirtualWave {

   private static final long serialVersionUID = 4650614154195009930L;

   protected E[] data;
   protected F view;
   protected G reference;

   public DataWave(double startX, double startY, double heading, double firePower, long creationTime, E[] data, F view, G reference) {
      super(startX, startY, heading, firePower, creationTime);
      init(data, view, reference);
   }

   public DataWave(Bullet bullet, RobotData firer, RobotData target, E[] data, F view, G reference) {
      super(firer.getX(), firer.getY(), Utils.angle(firer.getX(), firer.getY(), target.getX(), target.getY()), bullet.getPower(), firer.getTime());
      init(data, view, reference);
   }

   public DataWave(RobotData firer, RobotData target, double firePower, E[] data, F view, G reference) {
      super(firer.getX(), firer.getY(), Utils.angle(firer.getX(), firer.getY(), target.getX(), target.getY()), firePower, firer.getTime());
      init(data, view, reference);
   }

   public DataWave(Robot firer, RobotData target, double firePower, E[] data, F view, G reference) {
      super(firer.getX(), firer.getY(), Utils.angle(firer.getX(), firer.getY(), target.getX(), target.getY()), firePower, firer.getTime());
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

   @Override
   public void draw(RGraphics grid) {
      super.draw(grid);
   }

}