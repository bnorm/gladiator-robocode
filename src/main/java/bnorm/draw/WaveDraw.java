package bnorm.draw;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Collection;

import bnorm.virtual.IWave;

public class WaveDraw {

   private WaveDraw() {
   }

   public static final Color DEFAULT_COLOR = Color.BLUE;

   public static void drawWave(Graphics2D g, IWave wave, long time) {
      if (!DrawMenu.getValue("Virtual", "Waves", true)) {
         return;
      }

      drawWaveUnchecked(g, wave, time, DEFAULT_COLOR);
   }

   public static void drawWave(Graphics2D g, IWave wave, long time, Color color) {
      if (!DrawMenu.getValue("Virtual", "Waves", true)) {
         return;
      }

      drawWaveUnchecked(g, wave, time, color);
   }

   public static void drawWaves(Graphics2D g, Collection<? extends IWave> waves, long time) {
      if (!DrawMenu.getValue("Virtual", "Waves", true)) {
         return;
      }

      for (IWave w : waves) {
         drawWaveUnchecked(g, w, time, DEFAULT_COLOR);
      }
   }

   public static void drawWaves(Graphics2D g, Collection<? extends IWave> waves, long time, Color color) {
      if (!DrawMenu.getValue("Virtual", "Waves", true)) {
         return;
      }

      for (IWave w : waves) {
         drawWaveUnchecked(g, w, time, color);
      }
   }

   private static void drawWaveUnchecked(Graphics2D g, IWave wave, long time, Color color) {
      g.setColor(color);
      int dist = (int) wave.dist(time);
      g.drawOval((int) (wave.getX() - dist), (int) (wave.getY() - dist), 2 * dist, 2 * dist);
   }
}
