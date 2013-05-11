package kid.team;

import kid.graphics.Colors;
import robocode.TeamRobot;

// sniper, killer, hunter
/*
 * Maybe use a simaler angle melee targeting algarithum. defintly want to use VG, with PM, and GF be more agresive
 */
/**
 * <p>
 * Title: DeltaOhSeven, RC-1207, AKA 'Sev'
 * </p>
 * <p>
 * Description: Some soldiers fight because they have to; others fight because they like it.
 * Oh-Seven is definitely one of the latter. Most of the time he's a coldly efficient killer with a
 * grim sense of humor. But when things get hairy, he gets scary. Other members of the squad suspect
 * that someone spiked his cloning vat.
 * </p>
 * 
 * @author Brian Norman
 * @version .1
 */
public class Sev extends TeamRobot {

   @Override
   public void run() {
      setColors(Colors.DARK_RED, Colors.SILVER, Colors.VISER_BLUE);
   }

}
