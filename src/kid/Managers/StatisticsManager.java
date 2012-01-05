package kid.Managers;

import robocode.*;
import kid.Utils;

/**
 * <p>
 * Title: StatisticsManager
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * @author KID
 * @version 0.1b
 */
public class StatisticsManager {

    private Robot MyRobot;
    private boolean DidIPrintStates = false;

    private static int AccumulatedShotsFired = 0;
    private static int AccumulatedShotsHit = 0;
    private int ShotsFired;
    private int ShotsHit;

    private static double AccumulatedDamageGiven = 0.0;
    private static double AccumulatedTakenDamage = 0.0;
    private double DamageGiven;
    private double TakenDamage;

    private static int AccumulatedTime = 0;
    private static int AccumulatedSkippedTurns = 0;
    private int SkippedTurns;

    private int FinishingPlace = Integer.MAX_VALUE;
    private static int[] Finishes = null;
    private String KilledBy = null;


    public StatisticsManager(Robot MyRobot) {
        this.MyRobot = MyRobot;
        DidIPrintStates = false;
        ShotsFired = 0;
        ShotsHit = 0;
        DamageGiven = 0;
        TakenDamage = 0;
        SkippedTurns = 0;
        if (Finishes == null) {
            Finishes = new int[MyRobot.getOthers() + 1];
            for (int i = 0; i < Finishes.length; i++) {
                Finishes[i] = 0;
            }
        }
    }

    public StatisticsManager(AdvancedRobot MyRobot) {
        this((Robot) MyRobot);
    }

    public StatisticsManager(TeamRobot MyRobot) {
        this((Robot) MyRobot);
    }

    public void inEvent(Event e) {
        if (e instanceof ScannedRobotEvent) {
            // ScannedRobotEvent SRE = (ScannedRobotEvent) e;
        } else if (e instanceof BulletHitEvent) {
            BulletHitEvent BHE = (BulletHitEvent) e;
            AccumulatedDamageGiven += Utils.bulletDamage(BHE.getBullet().getPower());
            DamageGiven += Utils.bulletDamage(BHE.getBullet().getPower());
            AccumulatedShotsFired++;
            ShotsFired++;
            AccumulatedShotsHit++;
            ShotsHit++;
        } else if (e instanceof BulletMissedEvent) {
            // BulletMissedEvent BME = (BulletMissedEvent) e;
            AccumulatedShotsFired++;
            ShotsFired++;
        } else if (e instanceof BulletHitBulletEvent) {
            // BulletHitBulletEvent BHBE = (BulletHitBulletEvent) e;
            AccumulatedShotsFired++;
            ShotsFired++;
        } else if (e instanceof SkippedTurnEvent) {
            // SkippedTurnEvent STE = (SkippedTurnEvent) e;
            AccumulatedSkippedTurns++;
            SkippedTurns++;
        } else if (e instanceof HitRobotEvent) {
            // HitRobotEvent HRE = (HitRobotEvent) e;
            AccumulatedTakenDamage += 0.6;
            TakenDamage += 0.6;
            AccumulatedDamageGiven += 0.6;
            DamageGiven += 0.6;
        } else if (e instanceof HitByBulletEvent) {
            HitByBulletEvent HBBE = (HitByBulletEvent) e;
            AccumulatedTakenDamage += Utils.bulletDamage(HBBE.getPower());
            TakenDamage += Utils.bulletDamage(HBBE.getPower());
            KilledBy = HBBE.getName();
        } else if (e instanceof HitWallEvent) {
            // HitWallEvent HWE = (HitWallEvent) e;
            // AccumulatedTakenDamage += Math.abs(velocity) * 0.5 - 1;
            // TakenDamage += Math.abs(velocity) * 0.5 - 1;
        } else if (e instanceof WinEvent) {
            // WinEvent WE = (WinEvent) e;
            if (FinishingPlace == Integer.MAX_VALUE) {
                FinishingPlace = MyRobot.getOthers();
                Finishes[FinishingPlace]++;
            }
        } else if (e instanceof DeathEvent) {
            // DeathEvent DE = (DeathEvent) e;
            if (FinishingPlace == Integer.MAX_VALUE) {
                FinishingPlace = MyRobot.getOthers();
                Finishes[FinishingPlace]++;
            }
        }
    }

    public void inEvents(Event[] e) {
        for (int i = 0; i < e.length; i++) {
            inEvent(e[i]);
        }
    }

    public void printStatistics() {
        if (DidIPrintStates)
            return;
        MyRobot.out.println("||****** MY ROBOTS STATISTICS ******||");
        MyRobot.out.println("||******   Finishings         ******||");
        MyRobot.out.println("|| Finishing Place: " + (FinishingPlace + 1));
        if (FinishingPlace != 0) {
            MyRobot.out.println("|| Killed By: " + KilledBy);
        }
        MyRobot.out.println("||******   Bullets            ******||");
        MyRobot.out.println("|| Shots Hit:   " + ShotsHit);
        MyRobot.out.println("|| Shots Fired: " + ShotsFired);
        MyRobot.out.println("|| Ratio:       " + ((double) ShotsHit / ShotsFired));
        MyRobot.out.println("||******   Damage             ******||");
        MyRobot.out.println("|| Damage Given:   " + DamageGiven);
        MyRobot.out.println("|| Damage Taken:   " + TakenDamage);
        MyRobot.out.println("|| Average Damage: " + (DamageGiven - TakenDamage));
        MyRobot.out.println("||******   Time and Turns     ******||");
        MyRobot.out.println("|| Skipped: " + SkippedTurns);
        MyRobot.out.println("|| Time:    " + MyRobot.getTime());
        MyRobot.out.println("|| Ratio:   " + ((double) SkippedTurns / MyRobot.getTime()));
        MyRobot.out.println("||**********************************||");

        AccumulatedTime += MyRobot.getTime();

        if (MyRobot.getNumRounds() - 1 == MyRobot.getRoundNum()) {
            MyRobot.out.println("||****** MY ROBOTS STATISTICS ******||");
            MyRobot.out.println("||******   Finishings         ******||");
            for (int i = 0; i < Finishes.length; i++)
                MyRobot.out.println("|| Finished " + (i + 1) + ", " + Finishes[i] + " Time(s)");
            MyRobot.out.println("||******   Bullets            ******||");
            MyRobot.out.println("|| Shots Hit:   " + AccumulatedShotsHit);
            MyRobot.out.println("|| Shots Fired: " + AccumulatedShotsFired);
            MyRobot.out.println("|| Ratio:       " + ((double) AccumulatedShotsHit / AccumulatedShotsFired));
            MyRobot.out.println("||******   Damage             ******||");
            MyRobot.out.println("|| Damage Given:   " + AccumulatedDamageGiven);
            MyRobot.out.println("|| Damage Taken:   " + AccumulatedTakenDamage);
            MyRobot.out.println("|| Average Damage: " + (AccumulatedDamageGiven - AccumulatedTakenDamage));
            MyRobot.out.println("||******   Time and Turns     ******||");
            MyRobot.out.println("|| Skipped: " + AccumulatedSkippedTurns);
            MyRobot.out.println("|| Time:    " + AccumulatedTime);
            MyRobot.out.println("|| Ratio:   " + ((double) AccumulatedSkippedTurns / AccumulatedTime));
            MyRobot.out.println("||**********************************||");
        }
        DidIPrintStates = true;

    }

}
