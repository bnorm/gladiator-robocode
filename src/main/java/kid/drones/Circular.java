package kid.drones;

import kid.targeting.CircularTargeting;
import kid.targeting.Targeting;

public class Circular extends TargetingDrone {

   public Targeting getTargeting() {
      return new CircularTargeting(this);
   }

}