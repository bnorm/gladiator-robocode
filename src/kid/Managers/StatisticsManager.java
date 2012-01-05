package kid.Managers;

import robocode.*;
import kid.Utils;
import java.util.*;
import kid.Data.Robot.EnemyData;

/**
 * <p>Title: StatisticsManager</p>
 *
 * <p>Description: uses some of Vuen's CalculatingScore, see: http://robowiki.net/cgi-bin/robowiki?CalculatingScore </p>
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

    private boolean isOneOnOne = false;
    private static double EnemyEnergyWhenIDie = 0.0;
    private EnemyData Enemy = new EnemyData();

    private static int AccumulatedTime = 0;
    private static int AccumulatedSkippedTurns = 0;
    private int SkippedTurns;

    private int FinishingPlace = Integer.MAX_VALUE;
    private static int[] Finishes = null;
    private String KilledBy = null;


    // used only in a dual
    public static double enemyEnergy;
    public static double myEnergy;
    public static String enemyName;
    public static double[] bullet = new double[2];
    public static double[] curbullet = new double[2];
    public static double[] survival = new double[2];


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
        if (MyRobot.getOthers() == 1)
            isOneOnOne = true;
    }

    public StatisticsManager(AdvancedRobot MyRobot) {
        this((Robot) MyRobot);
    }

    public StatisticsManager(TeamRobot MyRobot) {
        this((Robot) MyRobot);
    }

    public void inEvent(Event e) {
        if (e instanceof ScannedRobotEvent) {
            ScannedRobotEvent SRE = (ScannedRobotEvent) e;
            if (isOneOnOne) {
                Enemy.updateItemFromFile(0.0, 0.0, 0.0, SRE.getEnergy(), 0.0, 0.0, 0);

                myEnergy = MyRobot.getEnergy();
                enemyEnergy = SRE.getEnergy();
                if (enemyName == null) enemyName = SRE.getName();
            }
        } else if (e instanceof BulletHitEvent) {
            BulletHitEvent BHE = (BulletHitEvent) e;
            AccumulatedDamageGiven += Utils.bulletDamage(BHE.getBullet().getPower());
            DamageGiven += Utils.bulletDamage(BHE.getBullet().getPower());
            AccumulatedShotsFired++;
            ShotsFired++;
            AccumulatedShotsHit++;
            ShotsHit++;

            if (isOneOnOne) {
                if (BHE.getEnergy() < 0.001)return; //ignore if enemy dead
                curbullet[0] += 4 * BHE.getBullet().getPower() + 2 * Math.max(BHE.getBullet().getPower() - 1, 0);
            }
        } else if (e instanceof BulletMissedEvent) {
            BulletMissedEvent BME = (BulletMissedEvent) e;
            AccumulatedShotsFired++;
            ShotsFired++;
        } else if (e instanceof BulletHitBulletEvent) {
            BulletHitBulletEvent BHBE = (BulletHitBulletEvent) e;
            AccumulatedShotsFired++;
            ShotsFired++;
        } else if (e instanceof SkippedTurnEvent) {
            SkippedTurnEvent STE = (SkippedTurnEvent) e;
            AccumulatedSkippedTurns++;
            SkippedTurns++;
        } else if (e instanceof HitRobotEvent) {
            HitRobotEvent HRE = (HitRobotEvent) e;
            AccumulatedTakenDamage += 0.6;
            TakenDamage += 0.6;
            AccumulatedDamageGiven += 0.6;
            DamageGiven += 0.6;
        } else if (e instanceof HitByBulletEvent) {
            HitByBulletEvent HBBE = (HitByBulletEvent) e;
            AccumulatedTakenDamage += Utils.bulletDamage(HBBE.getPower());
            TakenDamage += Utils.bulletDamage(HBBE.getPower());
            KilledBy = HBBE.getName();

            if (isOneOnOne) {
                if (HBBE.getPower() * 4 + Math.max(0, HBBE.getPower() - 1) * 2 > myEnergy)
                    return; //ignore if self dead
                //this works regardless of order of hitbybullet and scan
                curbullet[1] += 4 * HBBE.getBullet().getPower() + 2 * Math.max(HBBE.getBullet().getPower() - 1, 0);
            }
        } else if (e instanceof HitWallEvent) {
            HitWallEvent HWE = (HitWallEvent) e;
            //AccumulatedTakenDamage += Math.abs(velocity) * 0.5 - 1;
            //TakenDamage += Math.abs(velocity) * 0.5 - 1;
        } else if (e instanceof WinEvent) {
            WinEvent WE = (WinEvent) e;
            if (FinishingPlace == Integer.MAX_VALUE) {
                FinishingPlace = MyRobot.getOthers();
                Finishes[FinishingPlace]++;
            }

            if (isOneOnOne) {
                survival[0] += 60;
                curbullet[0] += enemyEnergy;
                bullet[0] += curbullet[0] * 1.2;
                bullet[1] += curbullet[1];
                curbullet[0] = 0; curbullet[1] = 0;
            }
        } else if (e instanceof DeathEvent) {
            DeathEvent DE = (DeathEvent) e;
            if (FinishingPlace == Integer.MAX_VALUE) {
                FinishingPlace = MyRobot.getOthers();
                Finishes[FinishingPlace]++;
            }

            if (isOneOnOne) {
                EnemyEnergyWhenIDie += Enemy.getEnergy();

                survival[1] += 60;
                curbullet[1] += myEnergy;
                bullet[0] += curbullet[0];
                bullet[1] += curbullet[1] * 1.2;
                curbullet[0] = 0; curbullet[1] = 0;
            }
        }
    }

    public void inEvents(Event[] e) {
        for (int i = 0; i < e.length; i++) {
            inEvent(e[i]);
        }
    }

    public void outStatistics() {
        if (!DidIPrintStates) {
            MyRobot.out.println("||****** MY ROBOTS STATISTICS ******||");
            MyRobot.out.println("||******   Finishings         ******||");
            MyRobot.out.println("|| Finishing Place: " + (FinishingPlace + 1));
            if (FinishingPlace != 0) {
                MyRobot.out.println("|| Killed By: " + KilledBy);
                MyRobot.out.println("|| Position: (" + Math.round(MyRobot.getX()) + ", " + Math.round(MyRobot.getY()) +
                                    ")");
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
                if (enemyName != null && isOneOnOne) {

                    System.out.println("  ***********SCORECARD***********");
                    System.out.print("  ");
                    for (int i = 0; i < Math.max(MyRobot.getName().length(), enemyName.length()); i++) System.out.print(
                            " ");
                    System.out.println(" Total Survival Bullet");

                    String p0 = "  " + MyRobot.getName();
                    String p1 = "  " + enemyName;

                    String pTemp = " " + Math.round(bullet[0] + survival[0] + curbullet[0]);
                    for (int i = MyRobot.getName().length();
                                 i < Math.max(MyRobot.getName().length(), enemyName.length()) + 7 - pTemp.length(); i++)
                        p0 += " ";

                    pTemp = (" " + Math.round(bullet[1] + survival[1] + curbullet[1]));
                    for (int i = enemyName.length();
                                 i < Math.max(MyRobot.getName().length(), enemyName.length()) + 7 - pTemp.length(); i++)
                        p1 += " ";

                    p0 += Math.round(bullet[0] + survival[0] + curbullet[0]) + "  ";
                    p1 += Math.round(bullet[1] + survival[1] + curbullet[1]) + "  ";
                    pTemp = (" " + Math.round(survival[0]));
                    for (int i = 0; i < 8 - pTemp.length(); i++) p0 += " ";
                    pTemp = (" " + Math.round(survival[1]));
                    for (int i = 0; i < 8 - pTemp.length(); i++) p1 += " ";

                    p0 += Math.round(survival[0]) + "  ";
                    p1 += Math.round(survival[1]) + "  ";
                    pTemp = (" " + Math.round(bullet[0] + curbullet[0]));
                    for (int i = 0; i < 6 - pTemp.length(); i++) p0 += " ";

                    pTemp = (" " + Math.round(bullet[1] + curbullet[1]));
                    for (int i = 0; i < 6 - pTemp.length(); i++) p1 += " ";

                    p0 += Math.round(bullet[0] + curbullet[0]);
                    p1 += Math.round(bullet[1] + curbullet[1]);

                    if (bullet[0] + survival[0] + curbullet[0] >= bullet[1] + survival[1] + curbullet[1]) {
                        System.out.println(p0); System.out.println(p1);
                    } else {
                        System.out.println(p1); System.out.println(p0);
                    }
                }

                MyRobot.out.println("||****** MY ROBOTS STATISTICS ******||");
                MyRobot.out.println("||******   Finishings         ******||");
                for (int i = 0; i < Finishes.length; i++)
                    MyRobot.out.println("|| Finished " + (i + 1) + ", " + Finishes[i] + " Number of Times");
                if (isOneOnOne)
                    MyRobot.out.println("|| Avg Enemy Energy Left: " +
                                        Utils.round(EnemyEnergyWhenIDie / Finishes[1], .01));
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


    /** returns the score of the requested robot: 0=self, 1=enemy */
    public int getScore(int id) {
        return (int) Math.round(bullet[id] + curbullet[id] + survival[id]);
    }

    /** prints the scorecard to the console */
    public void outScore() {
        if (enemyName == null)return;

        MyRobot.out.println("  ***********SCORECARD***********");
        MyRobot.out.print("  ");
        for (int i = 0; i < Math.max(MyRobot.getName().length(), enemyName.length()); i++) System.out.print(" ");
        MyRobot.out.println(" Total Survival Bullet");

        String p0 = "  " + MyRobot.getName();
        String p1 = "  " + enemyName;

        String pTemp = " " + Math.round(bullet[0] + survival[0] + curbullet[0]);
        for (int i = MyRobot.getName().length();
                     i < Math.max(MyRobot.getName().length(), enemyName.length()) + 7 - pTemp.length(); i++) p0 += " ";

        pTemp = (" " + Math.round(bullet[1] + survival[1] + curbullet[1]));
        for (int i = enemyName.length();
                     i < Math.max(MyRobot.getName().length(), enemyName.length()) + 7 - pTemp.length(); i++) p1 += " ";

        p0 += Math.round(bullet[0] + survival[0] + curbullet[0]) + "  ";
        p1 += Math.round(bullet[1] + survival[1] + curbullet[1]) + "  ";
        pTemp = (" " + Math.round(survival[0]));
        for (int i = 0; i < 8 - pTemp.length(); i++) p0 += " ";
        pTemp = (" " + Math.round(survival[1]));
        for (int i = 0; i < 8 - pTemp.length(); i++) p1 += " ";

        p0 += Math.round(survival[0]) + "  ";
        p1 += Math.round(survival[1]) + "  ";
        pTemp = (" " + Math.round(bullet[0] + curbullet[0]));
        for (int i = 0; i < 6 - pTemp.length(); i++) p0 += " ";

        pTemp = (" " + Math.round(bullet[1] + curbullet[1]));
        for (int i = 0; i < 6 - pTemp.length(); i++) p1 += " ";

        p0 += Math.round(bullet[0] + curbullet[0]);
        p1 += Math.round(bullet[1] + curbullet[1]);

        if (bullet[0] + survival[0] + curbullet[0] >= bullet[1] + survival[1] + curbullet[1]) {
            MyRobot.out.println(p0); MyRobot.out.println(p1);
        } else {
            MyRobot.out.println(p1); MyRobot.out.println(p0);
        }
    }

}
