package kid.drones;

import kid.movement.robot.Movement;
import kid.movement.robot.WallMovement;

public class Wall extends MovementDrone {
   public Movement getMovement() {
      return new WallMovement(this);
   }
}