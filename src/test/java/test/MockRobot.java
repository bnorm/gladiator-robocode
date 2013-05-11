package test;

import java.awt.Color;
import java.awt.Graphics2D;

import robocode.Bullet;
import robocode.Robot;

public class MockRobot extends Robot {

   private double x_;
   private double y_;

   private double heading_;
   private double velocity_;

   public MockRobot() {
   }

   @Override
   public void ahead(double distance) {
   }

   @Override
   public void back(double distance) {
   }

   @Override
   public void doNothing() {
   }

   @Override
   public void fire(double power) {
   }

   @Override
   public Bullet fireBullet(double power) {
      return null;
   }

   @Override
   public double getBattleFieldHeight() {
      return 0.0;
   }

   @Override
   public double getBattleFieldWidth() {
      return 0.0;
   }

   @Override
   public double getEnergy() {
      return 0.0;
   }

   @Override
   public Graphics2D getGraphics() {
      return null;
   }

   @Override
   public double getGunCoolingRate() {
      return 0.0;
   }

   @Override
   public double getGunHeading() {
      return 0.0;
   }

   @Override
   public double getGunHeat() {
      return 0.0;
   }

   @Override
   public double getHeading() {
      return heading_;
   }

   @Override
   public double getHeight() {
      return 0.0;
   }

   @Override
   public String getName() {
      return null;
   }

   @Override
   public int getNumRounds() {
      return 0;
   }

   @Override
   public int getOthers() {
      return 0;
   }

   @Override
   public double getRadarHeading() {
      return 0.0;
   }

   @Override
   public int getRoundNum() {
      return 0;
   }

   @Override
   public long getTime() {
      return 0L;
   }

   @Override
   public double getVelocity() {
      return velocity_;
   }

   @Override
   public double getWidth() {
      return 0.0;
   }

   @Override
   public double getX() {
      return x_;
   }

   @Override
   public double getY() {
      return y_;
   }

   @Override
   public void resume() {
   }

   @Override
   public void run() {
   }

   @Override
   public void scan() {
   }

   @Override
   public void setAdjustGunForRobotTurn(boolean independent) {
   }

   @Override
   public void setAdjustRadarForGunTurn(boolean independent) {
   }

   @Override
   public void setAdjustRadarForRobotTurn(boolean independent) {
   }

   @Override
   public void setAllColors(Color color) {
   }

   @Override
   public void setBodyColor(Color color) {
   }

   @Override
   public void setBulletColor(Color color) {
   }

   @Override
   public void setColors(Color bodyColor, Color gunColor, Color radarColor, Color bulletColor, Color scanArcColor) {
   }

   @Override
   public void setColors(Color bodyColor, Color gunColor, Color radarColor) {
   }

   @Override
   public void setDebugProperty(String key, String value) {
   }

   @Override
   public void setGunColor(Color color) {
   }

   @Override
   public void setRadarColor(Color color) {
   }

   @Override
   public void setScanColor(Color color) {
   }

   @Override
   public void stop() {
   }

   @Override
   public void stop(boolean overwrite) {
   }

   @Override
   public void turnGunLeft(double degrees) {
   }

   @Override
   public void turnGunRight(double degrees) {
   }

   @Override
   public void turnLeft(double degrees) {
   }

   @Override
   public void turnRadarLeft(double degrees) {
   }

   @Override
   public void turnRadarRight(double degrees) {
   }

   @Override
   public void turnRight(double degrees) {
   }

}
