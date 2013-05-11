package kid.team;

import kid.graphics.Colors;
import robocode.TeamRobot;

// computers, melee, by the book
/*
 * jump in jump out movement
 */
/**
 * <p>
 * Title: DeltaFourOh, RC-1140, AKA 'Fixer'
 * </p>
 * <p>
 * Description: The acknowledged 'second-in-command' of the Deltas is a gruff, by-the-book type of clone. He insists on
 * calling his squad mates by their batch designations, rather than the more colorful nicknames they acquired. In the
 * heat of battle, he's most often the one urging the rest of the group to press onward. Known to the others as 'Fixer,'
 * Delta Four-Oh is the resident technology expert, and often handles computer slicing and code-breaking duties. Four-Oh
 * was a favorite of the borderline sociopathic Vau - as they shared a common obssession with tech and tactics.
 * </p>
 * 
 * @author Brian Norman
 * @version .1
 */
public class Fixer extends TeamRobot {
    
    @Override
   public void run() {
        setColors(Colors.DIRT_GREEN, Colors.SILVER, Colors.VISER_BLUE);
    }

}