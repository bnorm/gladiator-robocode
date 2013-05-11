package kid.team;

import kid.graphics.Colors;
import robocode.TeamRobot;

/**
 * <p>
 * Title: DeltaThreeEight, RC-1138, AKA 'Boss'
 * </p>
 * <p>
 * Description: Even among clones, there is a heirarchy. Three-Eight is undisputed leader of the
 * Deltas. Relatively taciturn, when he speaks, it's usually to bark out an order to his squad.
 * Three-Eight has earned the respect and loyalty of his squad, and he repays that dedication in
 * strong leadership. Despite being trained by Walon Vau, Three-Eight somehow inherited Jango's
 * strong Concord Dawn accent and speech patterns.
 * </p>
 * 
 * @author Brian Norman
 * @version .1
 */
public class Boss extends TeamRobot {

   @Override
   public void run() {
      setColors(Colors.OFF_ORANGE, Colors.SILVER, Colors.VISER_BLUE);
   }

}
