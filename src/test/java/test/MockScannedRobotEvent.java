package test;

import robocode.ScannedRobotEvent;

public class MockScannedRobotEvent extends ScannedRobotEvent {

   private static final long serialVersionUID = -3750901116734222968L;

   private long              time_;
   private String            name_;
   private double            energy_;
   private double            heading_;
   private double            bearing_;
   private double            distance_;
   private double            velocity_;

   public MockScannedRobotEvent(long time, String name, double energy, double bearing, double distance, double heading,
         double velocity) {
      super(name, energy, bearing, distance, heading, velocity);
      this.time_ = time;
      this.name_ = name;
      this.energy_ = energy;
      this.bearing_ = bearing;
      this.distance_ = distance;
      this.heading_ = heading;
      this.velocity_ = velocity;
   }

   @Override
   public long getTime() {
      return time_;
   }

   @Override
   public double getBearing() {
      return bearing_ * 180.0 / Math.PI;
   }

   @Override
   public double getBearingRadians() {
      return bearing_;
   }

   @Override
   public double getDistance() {
      return distance_;
   }

   @Override
   public double getEnergy() {
      return energy_;
   }

   @Override
   public double getHeading() {
      return heading_ * 180.0 / Math.PI;
   }

   @Override
   public double getHeadingRadians() {
      return heading_;
   }

   @Override
   public String getName() {
      return name_;
   }

   @Override
   public double getVelocity() {
      return velocity_;
   }

}
