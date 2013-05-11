package dev.virtual;

import java.util.Collection;

import robocode.Bullet;
import robocode.Robot;
import dev.draw.RobotGraphics;
import dev.robots.RobotData;
import dev.utils.Utils;

public class DataWave<E> extends VirtualWave {

   private static final long serialVersionUID = 4650614154195399930L;

   protected Collection<E>   data;
   protected RobotData       view;
   protected RobotData       reference;

   public DataWave(double startX, double startY, double heading, double firePower, long creationTime,
         Collection<E> data, RobotData view, RobotData reference) {
      super(startX, startY, heading, firePower, creationTime);
      init(view, reference, data);
   }

   public DataWave(Bullet bullet, RobotData firer, RobotData target, Collection<E> data) {
      super(firer.getX(), firer.getY(), Utils.angle(firer.getX(), firer.getY(), target.getX(), target.getY()), bullet
            .getPower(), firer.getTime());
      init(target, firer, data);
   }

   public DataWave(RobotData firer, RobotData target, double firePower, Collection<E> data) {
      super(firer.getX(), firer.getY(), Utils.angle(firer.getX(), firer.getY(), target.getX(), target.getY()),
            firePower, firer.getTime());
      init(target, firer, data);
   }

   public DataWave(Robot firer, RobotData target, double firePower, Collection<E> data, RobotData reference) {
      super(firer.getX(), firer.getY(), Utils.angle(firer.getX(), firer.getY(), target.getX(), target.getY()),
            firePower, firer.getTime());
      init(target, reference, data);
   }

   private void init(RobotData view, RobotData reference, Collection<E> data) {
      this.data = data;
      this.view = view;
      this.reference = reference;
   }

   public Collection<E> getData() {
      return data;
   }

   public RobotData getView() {
      return view;
   }

   public RobotData getReference() {
      return reference;
   }

   @Override
   public void draw(RobotGraphics grid) {
      super.draw(grid);
   }

}