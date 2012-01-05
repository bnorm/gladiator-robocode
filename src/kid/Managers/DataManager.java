package kid.Managers;

import java.awt.*;
import java.io.*;
import java.security.*;
import java.util.*;
import java.util.List;
import java.util.zip.*;

import kid.*;
import kid.Communication.*;
import kid.Data.*;
import kid.Data.Robot.*;
import kid.Data.Virtual.*;
import kid.Graphics.*;
import kid.Targeting.*;
import robocode.*;
import robocode.Event;
import robocode.Robot;

/**
 * <p>Title: DataManager</p>
 *
 * <p>Description: </p>
 *
 * @author KID
 * @version 0.1b
 */
public class DataManager {

    private static AdvancedRobot MyRobot = null;
    private static MyRobotsInfo i;

    private static boolean SaveData = false;

    private static final int RobotNotFound = -1;
    private static int EnemyCount = 0;
    private static EnemyData[] EnemyData;
    private static int TeammateCount = 0;
    private static TeammateData[] TeammateData;

    private List Bullets = new ArrayList();
    private List TeammateBullets = new ArrayList();

    private static boolean isGuessFactor = false;

    private static boolean isPatternMatching = false;

    private static boolean areVirtualGuns = false;
    private static Targeting[] VirtualGuns = null;


    public DataManager() {}

    public DataManager(boolean SaveData) {
        this.SaveData = SaveData;
    }

    public DataManager(Robot MyRobot) {
        this(MyRobot, null, null);
    }

    public DataManager(Robot MyRobot, boolean SaveData) {
        this(MyRobot, null, null, SaveData);
    }

    public DataManager(AdvancedRobot MyRobot) {
        this(null, MyRobot, null);
        this.MyRobot = MyRobot;
        MyRobot.addCustomEvent(new UpdateBullets());
    }

    public DataManager(AdvancedRobot MyRobot, boolean SaveData) {
        this(null, MyRobot, null, SaveData);
        this.MyRobot = MyRobot;
        MyRobot.addCustomEvent(new UpdateBullets());
    }

    public DataManager(TeamRobot MyRobot) {
        this(null, null, MyRobot);
        this.MyRobot = MyRobot;
        MyRobot.addCustomEvent(new UpdateBullets());
    }

    public DataManager(TeamRobot MyRobot, boolean SaveData) {
        this(null, null, MyRobot, SaveData);
        this.MyRobot = MyRobot;
        MyRobot.addCustomEvent(new UpdateBullets());
    }

    public DataManager(Robot robot, AdvancedRobot advancedRobot, TeamRobot teamRobot) {
        i = new MyRobotsInfo(robot, advancedRobot, teamRobot);
        if (robot != null || advancedRobot != null || teamRobot != null)
            setUpDataManager();
    }

    public DataManager(Robot robot, AdvancedRobot advancedRobot, TeamRobot teamRobot, boolean SaveData) {
        this.SaveData = SaveData;
        i = new MyRobotsInfo(robot, advancedRobot, teamRobot);
        if (robot != null || advancedRobot != null || teamRobot != null)
            setUpDataManager();
    }


    public void UpDateRobot(Robot MyRobot) {
        i = new MyRobotsInfo(MyRobot);
        setUpDataManager();
    }

    public void UpDateRobot(AdvancedRobot MyRobot) {
        if (this.MyRobot == null || this.MyRobot.getName() != MyRobot.getName()) {
            i = new MyRobotsInfo(MyRobot);
            this.MyRobot = MyRobot;
            setUpDataManager();
        }
    }

    public void UpDateRobot(TeamRobot MyRobot) {
        if (this.MyRobot == null || this.MyRobot.getName() != MyRobot.getName()) {
            i = new MyRobotsInfo(MyRobot);
            this.MyRobot = MyRobot;
            setUpDataManager();
        }
    }


    private void setUpDataManager() {
        EnemyData = new EnemyData[i.getTotalEnemys()];
        EnemyCount = 0;
        for (int b = 0; b < EnemyData.length; b++)
            EnemyData[b] = new EnemyData();
        TeammateData = new TeammateData[i.getTotalTeammates()];
        TeammateCount = 0;
        for (int b = 0; b < TeammateData.length; b++)
            TeammateData[b] = new TeammateData();
    }


    /**
     * Uploads a EnemyData Class from file and loads it into the array of enemys.
     *
     * Must have added GuessFactor, PatternMatching, or VirtualGuns for it to load.
     *
     * @param SRE ScannedRobotEvent, the Event that gives it the name and current info on the enemy.
     * @return boolean, true if it was able to load it to the array, false other wise.
     */
    public boolean updateEnemyDataFromFile(ScannedRobotEvent SRE) {
        if (!SaveData)
            return false;
        if (!(isGuessFactor || isPatternMatching || areVirtualGuns))
            return false;
        try {
            ZipInputStream zipin;
            ObjectInputStream in;
            String name = SRE.getName();
            int end = name.indexOf(" ");
            if (end != -1) {
                name = name.substring(0, end);
            }
            zipin = new ZipInputStream(new FileInputStream(i.getDataFile(name + ".zip")));
            zipin.getNextEntry();
            in = new ObjectInputStream(zipin);
            EnemyData[EnemyCount] = (EnemyData) in.readObject();
            EnemyData[EnemyCount].updateItemFromFile(SRE.getName(),
                    Utils.getX(i.getX(), SRE.getDistance(), SRE.getBearing() + i.getRobotFrontHeading()),
                    Utils.getY(i.getY(), SRE.getDistance(), SRE.getBearing() + i.getRobotFrontHeading()),
                    SRE.getDistance(), SRE.getEnergy(), SRE.getHeading(), SRE.getVelocity(), SRE.getTime());
            in.close();
            MyRobot.out.println("Loaded " + SRE.getName() + " from file");
        } catch (ClassNotFoundException Exception) {
            MyRobot.out.println("Could not load " + SRE.getName() + " form file. (" + Exception.getClass() + ")");
            return false;
        } catch (AccessControlException Exception) {
            MyRobot.out.println("Could not load " + SRE.getName() + " form file. (" + Exception.getClass() + ")");
            updateEnemyDataFromFile(SRE);
            return false;
        } catch (InvalidClassException Exception) {
            MyRobot.out.println("Could not load " + SRE.getName() + " form file. (" + Exception.getClass() + ")");
            return false;
        } catch (IOException Exception) {
            MyRobot.out.println("Could not load " + SRE.getName() + " form file. (" + Exception.getClass() + ")");
            return false;
        }
        return true;
    }

    /**
     * Saves all enemys to file if there are GuessFactor, PatternMatching, or VirtualGuns.
     *
     * Will not save if it won the round, but will if it is the last round.
     */
    public void saveEnemyDataToFile() {
        if (!SaveData)
            return;
        if ((!(isGuessFactor || isPatternMatching || areVirtualGuns) || i.getOthers() == 0) &&
            MyRobot.getNumRounds() - 1 != MyRobot.getRoundNum())
            return;
        for (int e = 0; e < EnemyData.length; e++) {
            saveEnemyToFile(EnemyData[e]);
        }
    }

    private void saveEnemyToFile(EnemyData enemy) {
        try {
            String name = enemy.getName();
            int end = name.indexOf(" ");
            if (end != -1) {
                name = name.substring(0, end);
            }
            ZipOutputStream zipout = new ZipOutputStream(new RobocodeFileOutputStream(i.getDataFile(name + ".zip")));
            zipout.putNextEntry(new ZipEntry(name));
            ObjectOutputStream out = new ObjectOutputStream(zipout);
            out.writeObject(enemy);
            out.flush();
            zipout.closeEntry();
            out.close();
            MyRobot.out.println("Saved " + enemy.getName() + " to file");
        } catch (NotSerializableException Exception) {
            MyRobot.out.println("Could not save " + enemy.getName() + " to file. (" + Exception.getClass() + ")");
            Exception.printStackTrace();
        } catch (IOException Exception) {
            MyRobot.out.println("Could not save " + enemy.getName() + " to file. (" + Exception.getClass() + ")");
        } catch (AccessControlException Exception) {
            MyRobot.out.println("Could not save " + enemy.getName() + " to file. (" + Exception.getClass() + ")");
            saveEnemyToFile(enemy);
        }
    }

    /**
     * Method that handals all the Events that come in to your robot.
     * @param e Event, The current event.
     */
    public void inEvent(Event e) {
        if (e instanceof ScannedRobotEvent) {
            ScannedRobotEvent SRE = (ScannedRobotEvent) e;
            if (i.isTeammate(SRE.getName())) {
                try {
                    TeammateData[getTeammateNum(SRE.getName())].updateItem(Utils.getX(i.getX(), SRE.getDistance(),
                            SRE.getBearing() + i.getRobotFrontHeading()),
                            Utils.getY(i.getY(), SRE.getDistance(), SRE.getBearing() + i.getRobotFrontHeading()),
                             SRE.getDistance(), SRE.getEnergy(), SRE.getHeading(), SRE.getVelocity(), SRE.getTime());
                } catch (ArrayIndexOutOfBoundsException AE) {
                    TeammateData[TeammateCount++] = new TeammateData(SRE.getName(),
                            Utils.getX(i.getX(), SRE.getDistance(), SRE.getBearing() + i.getRobotFrontHeading()),
                            Utils.getY(i.getY(), SRE.getDistance(), SRE.getBearing() + i.getRobotFrontHeading()),
                            SRE.getDistance(), SRE.getEnergy(), SRE.getHeading(), SRE.getVelocity(), SRE.getTime());
                }
            } else {
                try {
                    EnemyData[getEnemyNum(SRE.getName())].updateItem(Utils.getX(i.getX(), SRE.getDistance(),
                            SRE.getBearing() + i.getRobotFrontHeading()),
                            Utils.getY(i.getY(), SRE.getDistance(), SRE.getBearing() + i.getRobotFrontHeading()),
                            SRE.getDistance(), SRE.getEnergy(), SRE.getHeading(), SRE.getVelocity(), SRE.getTime());
                } catch (ArrayIndexOutOfBoundsException AE) {
                    if (!updateEnemyDataFromFile(SRE)) {
                        EnemyData[EnemyCount] = new EnemyData(SRE.getName(),
                                Utils.getX(i.getX(), SRE.getDistance(), SRE.getBearing() + i.getRobotFrontHeading()),
                                Utils.getY(i.getY(), SRE.getDistance(), SRE.getBearing() + i.getRobotFrontHeading()),
                                SRE.getDistance(), SRE.getEnergy(), SRE.getHeading(), SRE.getVelocity(), SRE.getTime());
                    }
                    if (isGuessFactor) {
                        EnemyData[EnemyCount].addGuessFactor(MyRobot);
                    }
                    if (areVirtualGuns) {
                        EnemyData[EnemyCount].addVirtualGuns(MyRobot, VirtualGuns);
                    }
                    if (isPatternMatching) {
                        EnemyData[EnemyCount].addPatternMatching();
                    }
                    EnemyCount++;
                }
            }
        } else if (e instanceof RobotDeathEvent) {
            RobotDeathEvent RDE = (RobotDeathEvent) e;
            getRobot(RDE.getName()).setDeath();
        } else if (e instanceof MessageEvent) {
            MessageEvent ME = (MessageEvent) e;
            if (ME.getMessage() instanceof Data) {
                Data Message = (Data) ME.getMessage();
                try {
                    TeammateData[getTeammateNum(Message.getTeammate().getName())].updateItemFromTeammate(Message.
                            getTeammate());
                } catch (ArrayIndexOutOfBoundsException AE) {
                    TeammateData[TeammateCount++] = Message.getTeammate();
                }
                EnemyData[] EnemyRobots = Message.getEnemyRobots();
                for (int b = 0; b < EnemyRobots.length; b++) {
                    EnemyData enemy = EnemyRobots[b];
                    if (enemy.isDead())
                        continue;
                    EnemyData enemyToUpData = getEnemy(enemy.getName());
                    if (enemyToUpData.isDead())
                        continue;
                    enemyToUpData.updateItem(enemy);
                }
                VirtualBullet[] vb = Message.getVirtualBullets();
                if (vb != null) {
                    for (int b = 0; b < vb.length; b++)
                        TeammateBullets.add(vb[b]);
                }
            } else {
                TeamRobot robot = (TeamRobot) MyRobot;
            }
        } else if (e instanceof DeathEvent) {
            DeathEvent DE = (DeathEvent) e;
            saveEnemyDataToFile();
        } else if (e instanceof WinEvent) {
            WinEvent WE = (WinEvent) e;
            saveEnemyDataToFile();
        }
    }

    /**
     * Sends all info to the other teammates.
     */
    public void sendInfo() {
        if (MyRobot instanceof TeamRobot) {
            Data myinfo = new Data((TeamRobot) MyRobot, getEnemys(), getBullets());
            try {
                ((TeamRobot) MyRobot).broadcastMessage(myinfo);
            } catch (IOException ex) {}
        }
    }

    /**
     * Adds a bullet to the list of bullets so that it can send them.
     * @param b Bullet, The bullet that is fired.
     */
    public void firedBullet(Bullet b) {
        Bullets.add(new VirtualBullet(b, MyRobot));
    }

    /**
     * Returns true if all teammates out of the way, and the targeted robot is not dead.
     *
     * @param TargetedRobot EnemyData, The enemy that i will be firing at.
     * @param FirePower double, The fire power that i will fire with.
     * @return boolean
     */
    public boolean shouldFireBullet(EnemyData TargetedRobot, double FirePower) {
        if (TargetedRobot.isDead())return false;
        if (i.getTeammates() != 0) {
            double maxoffsetangle = Utils.asin(MyRobotsInfo.MAX_VELOCITY / Utils.bulletVelocity(FirePower)) / 2D;
            for (int t = 0; t < TeammateCount; t++) {
                TeammateData teammate = TeammateData[t];
                if (teammate.isDead())
                    continue;
                if (Math.abs(i.GunBearingTo(teammate)) <= maxoffsetangle)
                    return false;
            }
        }
        return true;
    }

    /**
     * Reutrns the array of bullets that I have active on the battle field.
     * @return VirtualBullet[], Bullets that I have active on the battle field.
     */
    public VirtualBullet[] getBullets() {
        try {
            return (VirtualBullet[]) Bullets.toArray();
        } catch (java.lang.ClassCastException e) {
            return null;
        }
    }


    public void AddGuessFactor() {
        isGuessFactor = true;
        for (int b = 0; b < EnemyCount; b++)
            EnemyData[b].addGuessFactor(MyRobot);
    }

    public void UpdateGuessFactor(double FirePower) {
        if (isGuessFactor)
            for (int b = 0; b < EnemyCount; b++)
                if (!EnemyData[b].isDead())
                    EnemyData[b].updateGuessFatorGun(FirePower);
    }


    public void AddPatternMatching() {
        isPatternMatching = true;
        for (int b = 0; b < EnemyCount; b++)
            EnemyData[b].addPatternMatching();
    }


    public void AddVirtualGuns(Targeting[] guns) {
        if (guns != VirtualGuns) {
            areVirtualGuns = true;
            VirtualGuns = guns;
            for (int b = 0; b < EnemyCount; b++)
                EnemyData[b].addVirtualGuns(MyRobot, guns);
        }
    }

    public void UpdateVirtualGuns(double FirePower) {
        if (areVirtualGuns)
            for (int b = 0; b < EnemyCount; b++)
                if (!EnemyData[b].isDead())
                    EnemyData[b].updateVirtualGuns(FirePower);
    }


    public void drawTargetingNamesAndGraph(RobocodeGraphicsDrawer g) {
        int sizeoffont = 10;
        g.setFont(new Font(null, Font.PLAIN, sizeoffont));

        String lengthofstring = new String();
        for (int gun = 0; gun < VirtualGuns.length; gun++) {
            if (VirtualGuns[gun].getNameOfTargeting().length() > lengthofstring.length())
                lengthofstring = VirtualGuns[gun].getNameOfTargeting();
        }

        int sizeofcircul = 5;

        int lenghtofbox = (int) (lengthofstring.length() * (sizeoffont / 2) + 2 * sizeofcircul);
        int heighttofbox = sizeoffont + (sizeoffont / 2);

        int lengthofratingber = 75;
        int heightofratingber = heighttofbox - 2;

        for (int gun = 0; gun < VirtualGuns.length; gun++) {
            g.setColor(VirtualGuns[gun].getTargetingColor());
            g.drawRect(lenghtofbox / 2, (1 + (heighttofbox / 2) + (heighttofbox * gun) + (1 * gun)), lenghtofbox,
                       heighttofbox);
            g.fillOval(sizeofcircul, (1 + heighttofbox / 2 + heighttofbox * gun + 1 * gun), sizeofcircul, sizeofcircul);
            g.setColor(Colors.WHITE);
            g.drawString(VirtualGuns[gun].getNameOfTargeting(), 2 * sizeofcircul,
                         (int) MyRobot.getBattleFieldHeight() - (heighttofbox * gun + 1 * gun + 3));
        }

        double[] gunhitrate = new double[VirtualGuns.length];
        for (int e = 0; e < EnemyCount; e++) {
            if (EnemyData[e].isDead() && i.getOthers() != 0)
                continue;
            VirtualGun[] vg = EnemyData[e].getVirtualGuns();
            for (int gun = 0; gun < vg.length; gun++) {
                gunhitrate[gun] += vg[gun].getHitRate();
            }
        }

        double maxhitrate = 0.0;
        for (int gun = 0; gun < gunhitrate.length; gun++) {
            if (gunhitrate[gun] > maxhitrate)
                maxhitrate = gunhitrate[gun];

            g.drawString(String.valueOf(Utils.round(gunhitrate[gun] /
                    (i.getOthers() == 0 ? i.getTotalEnemys() : i.getEnemys()), .001)), lenghtofbox + 5,
                         (int) MyRobot.getBattleFieldHeight() - (heighttofbox * gun + 1 * gun + 3));

        }
        for (int gun = 0; gun < VirtualGuns.length; gun++) {
            g.setColor(VirtualGuns[gun].getTargetingColor());
            double rateing = (gunhitrate[gun] / maxhitrate * lengthofratingber);
            g.fillRect((int) (lenghtofbox + 35 + (rateing / 2)),
                       (1 + (heighttofbox / 2) + (heighttofbox * gun) + (1 * gun)), (int) rateing,
                       (int) (heightofratingber));
        }

    }

    public void drawVirtualBullets(RobocodeGraphicsDrawer g) {
        for (int e = 0; e < EnemyCount; e++) {
            EnemyData enemy = EnemyData[e];
            if (enemy.isDead())
                continue;
            enemy.drawVirtualBullets(g);
        }
    }


    public void drawEnemys(RobocodeGraphicsDrawer g) {
        for (int e = 0; e < EnemyCount; e++) {
            g.setColor(Colors.GREEN);
            EnemyData enemy = EnemyData[e];
            if (enemy.isDead())
                continue;
            g.drawRect((int) enemy.getX(), (int) enemy.getY(), (int) MyRobotsInfo.WIDTH, (int) MyRobotsInfo.HEIGHT);
        }
    }

    public void drawColsestEnemy(RobocodeGraphicsDrawer g) {
        if (!getColsestEnemy().isDead()) {
            g.setColor(Colors.RED);
            g.drawRect((int) getColsestEnemy().getX(), (int) getColsestEnemy().getY(), (int) MyRobotsInfo.WIDTH,
                       (int) MyRobotsInfo.HEIGHT);
        }
    }


    private EnemyData ColsestEnemy = new EnemyData();
    private long Time_ColsestEnemy = -1;
    public EnemyData getColsestEnemy() {
        if (i.getTime() != Time_ColsestEnemy) {
            double dist = Double.POSITIVE_INFINITY;
            if (!ColsestEnemy.isDead())
                dist = i.DistToSq(ColsestEnemy) * 0.8;
            for (int b = 0; b < EnemyCount; b++) {
                EnemyData bot = EnemyData[b];
                if (bot.isDead())
                    continue;
                double distToBot = i.DistToSq(bot);
                if (distToBot < dist) {
                    dist = distToBot;
                    ColsestEnemy = bot;
                }
            }
            Time_ColsestEnemy = i.getTime();
        }
        return ColsestEnemy;
    }

    private TeammateData ColsestTeammate = new TeammateData();
    private long Time_ColsestTeammate = -1;
    public TeammateData getColsestTeammate() {
        if (i.getTime() != Time_ColsestTeammate) {
            double dist = Double.POSITIVE_INFINITY;
            if (!ColsestTeammate.isDead())
                dist = i.DistToSq(ColsestTeammate) * 0.8;
            for (int b = 0; b < EnemyCount; b++) {
                EnemyData bot = EnemyData[b];
                if (bot.isDead())
                    continue;
                double distToBot = i.DistToSq(bot);
                if (distToBot < dist) {
                    dist = distToBot;
                    ColsestTeammate = bot;
                }
            }
            Time_ColsestTeammate = i.getTime();
        }
        return ColsestTeammate;
    }

    public RobotData getColsestRobot() {
        if (i.DistToSq(getColsestEnemy()) < i.DistToSq(getColsestTeammate()))
            return getColsestEnemy();
        return getColsestTeammate();
    }


    private EnemyData SmallestRiskEnemy = new EnemyData();
    private long Time_SmallestRiskEnemy = -1;
    public EnemyData getSmallestRiskEnemy() {
        if (i.getTime() != Time_SmallestRiskEnemy) {
            double risk = Double.POSITIVE_INFINITY;
            for (int b = 0; b < EnemyCount; b++) {
                EnemyData bot = EnemyData[b];
                if (bot.isDead())
                    continue;
                double riskOfBot = bot.getEnergy() / i.getEnergy();
                riskOfBot = 1 / ((riskOfBot > 2 ? 2 : riskOfBot) * i.DistToSq(bot.getX(), bot.getY()));
                if (riskOfBot < risk) {
                    risk = riskOfBot;
                    SmallestRiskEnemy = bot;
                }
            }
            Time_SmallestRiskEnemy = i.getTime();
        }
        return SmallestRiskEnemy;
    }

    private RobotData[] Robots;
    private long Time_Robots = -1;
    public RobotData[] getRobots() {
        if (i.getTime() == Time_Robots) {
            return Robots;
        } else {
            RobotData[] robots = new RobotData[EnemyCount + TeammateCount];
            int bots = 0;
            for (int b = 0; b < EnemyCount; bots++, b++)
                robots[bots] = EnemyData[b];
            for (int b = 0; b < TeammateCount; bots++, b++)
                robots[bots] = TeammateData[b];
            Robots = robots;
            Time_Robots = i.getTime();
            return robots;
        }
    }

    private EnemyData[] Enemys;
    private long Time_Enemys = -1;
    public EnemyData[] getEnemys() {
        if (i.getTime() == Time_Enemys) {
            return Enemys;
        } else {
            EnemyData[] enemys = new EnemyData[EnemyCount];
            for (int b = 0; b < EnemyCount; b++)
                enemys[b] = EnemyData[b];
            Enemys = enemys;
            Time_Enemys = i.getTime();
            return enemys;
        }
    }

    private TeammateData[] Teammates;
    private long Time_Teammates = -1;
    public TeammateData[] getTeammates() {
        if (i.getTime() == Time_Teammates) {
            return Teammates;
        } else {
            TeammateData[] teammates = new TeammateData[TeammateCount];
            for (int b = 0; b < TeammateCount; b++)
                teammates[b] = TeammateData[b];
            Teammates = teammates;
            Time_Teammates = i.getTime();
            return teammates;
        }
    }


    private int getRobotNum(String name) {
        if (getEnemyNum(name) != RobotNotFound)
            return getEnemyNum(name);
        else
            return getTeammateNum(name);
    }

    private int getEnemyNum(String name) {
        for (int b = 0; b < EnemyCount; b++) {
            if (name.equals(EnemyData[b].getName()))
                return b;
        }
        return RobotNotFound;
    }

    private int getTeammateNum(String name) {
        for (int b = 0; b < TeammateCount; b++) {
            if (name.equals(TeammateData[b].getName()))
                return b;
        }
        return RobotNotFound;
    }


    public RobotData getRobot(String name) {
        if (getRobotNum(name) != RobotNotFound) {
            if (i.isTeammate(name))
                return getTeammate(name);
            return getEnemy(name);
        }
        return new EnemyData();
    }

    public EnemyData getEnemy(String name) {
        if (getEnemyNum(name) != RobotNotFound)
            return EnemyData[getEnemyNum(name)];
        return new EnemyData();
    }

    public TeammateData getTeammate(String name) {
        if (getTeammateNum(name) != RobotNotFound)
            return TeammateData[getTeammateNum(name)];
        return new TeammateData();
    }


    private class UpdateVirtualGuns extends Condition {
        public boolean test() {
            return false;
        }
    }


    private class UpdateBullets extends Condition {
        public boolean test() {
            long time = MyRobot.getTime();
            for (int b = 0; b < Bullets.size(); b++) {
                VirtualBullet bullet = (VirtualBullet) Bullets.get(b);
                if (!bullet.testActive(time))
                    Bullets.remove(b--);
            }
            return false;
        }
    }

}
