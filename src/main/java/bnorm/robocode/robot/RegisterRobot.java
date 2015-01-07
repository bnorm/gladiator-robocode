package bnorm.robocode.robot;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import bnorm.robocode.robot.listener.BattleEndedEventListener;
import bnorm.robocode.robot.listener.BulletHitBulletEventListener;
import bnorm.robocode.robot.listener.BulletHitEventListener;
import bnorm.robocode.robot.listener.BulletMissedEventListener;
import bnorm.robocode.robot.listener.CustomEventListener;
import bnorm.robocode.robot.listener.DeathEventListener;
import bnorm.robocode.robot.listener.HitByBulletEventListener;
import bnorm.robocode.robot.listener.HitRobotEventListener;
import bnorm.robocode.robot.listener.HitWallEventListener;
import bnorm.robocode.robot.listener.KeyEventListener;
import bnorm.robocode.robot.listener.MessageEventListener;
import bnorm.robocode.robot.listener.MouseEventListener;
import bnorm.robocode.robot.listener.MouseWheelListener;
import bnorm.robocode.robot.listener.PaintListener;
import bnorm.robocode.robot.listener.RobocodeEventListener;
import bnorm.robocode.robot.listener.RobotDeathEventListener;
import bnorm.robocode.robot.listener.RoundEndedEventListener;
import bnorm.robocode.robot.listener.ScannedRobotEventListener;
import bnorm.robocode.robot.listener.SkippedTurnListener;
import bnorm.robocode.robot.listener.StatusEventListener;
import bnorm.robocode.robot.listener.WinEventListener;
import robocode.BattleEndedEvent;
import robocode.BulletHitBulletEvent;
import robocode.BulletHitEvent;
import robocode.BulletMissedEvent;
import robocode.CustomEvent;
import robocode.DeathEvent;
import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.MessageEvent;
import robocode.RobotDeathEvent;
import robocode.RoundEndedEvent;
import robocode.ScannedRobotEvent;
import robocode.SkippedTurnEvent;
import robocode.StatusEvent;
import robocode.TeamRobot;
import robocode.WinEvent;

public class RegisterRobot extends TeamRobot {

   private final List<BulletHitEventListener> bulletHitEventListeners;
   private final List<BulletHitBulletEventListener> bulletHitBulletEventListeners;
   private final List<BulletMissedEventListener> bulletMissedEventListeners;
   private final List<HitByBulletEventListener> hitByBulletEventListeners;
   private final List<HitRobotEventListener> hitRobotEventListeners;
   private final List<HitWallEventListener> hitWallEventListeners;
   private final List<RobotDeathEventListener> robotDeathEventListeners;
   private final List<ScannedRobotEventListener> scannedRobotEventListeners;
   private final List<WinEventListener> winEventListeners;
   private final List<RoundEndedEventListener> roundEndedEventListeners;
   private final List<BattleEndedEventListener> battleEndedEventListeners;
   private final List<StatusEventListener> statusEventListeners;
   private final List<MouseWheelListener> mouseWheelListeners;
   private final List<MouseEventListener> mouseEventListeners;
   private final List<KeyEventListener> keyEventListeners;
   private final List<PaintListener> paintListeners;
   private final List<DeathEventListener> deathEventListeners;
   private final List<SkippedTurnListener> skippedTurnListeners;
   private final List<CustomEventListener> customEventListeners;
   private final List<MessageEventListener> messageEventListeners;

   public RegisterRobot() {
      this.bulletHitEventListeners = new ArrayList<>();
      this.bulletHitBulletEventListeners = new ArrayList<>();
      this.bulletMissedEventListeners = new ArrayList<>();
      this.hitByBulletEventListeners = new ArrayList<>();
      this.hitRobotEventListeners = new ArrayList<>();
      this.hitWallEventListeners = new ArrayList<>();
      this.robotDeathEventListeners = new ArrayList<>();
      this.scannedRobotEventListeners = new ArrayList<>();
      this.winEventListeners = new ArrayList<>();
      this.roundEndedEventListeners = new ArrayList<>();
      this.battleEndedEventListeners = new ArrayList<>();
      this.statusEventListeners = new ArrayList<>();
      this.mouseWheelListeners = new ArrayList<>();
      this.mouseEventListeners = new ArrayList<>();
      this.keyEventListeners = new ArrayList<>();
      this.paintListeners = new ArrayList<>();
      this.deathEventListeners = new ArrayList<>();
      this.skippedTurnListeners = new ArrayList<>();
      this.customEventListeners = new ArrayList<>();
      this.messageEventListeners = new ArrayList<>();
   }

   public void register(RobocodeEventListener... listeners) {
      register(Arrays.asList(listeners));
   }

   public void register(Iterable<? extends RobocodeEventListener> listeners) {
      for (RobocodeEventListener listener : listeners) {
         register(listener);
      }
   }

   public void register(RobocodeEventListener listener) {
      if (listener instanceof BulletHitEventListener) {
         addBulletHitEventListener((BulletHitEventListener) listener);
      }
      if (listener instanceof BulletHitBulletEventListener) {
         addBulletHitBulletEventListener((BulletHitBulletEventListener) listener);
      }
      if (listener instanceof BulletMissedEventListener) {
         addBulletMissedEventListener((BulletMissedEventListener) listener);
      }
      if (listener instanceof HitByBulletEventListener) {
         addHitByBulletEventListener((HitByBulletEventListener) listener);
      }
      if (listener instanceof HitRobotEventListener) {
         addHitRobotEventListener((HitRobotEventListener) listener);
      }
      if (listener instanceof HitWallEventListener) {
         addHitWallEventListener((HitWallEventListener) listener);
      }
      if (listener instanceof RobotDeathEventListener) {
         addRobotDeathEventListener((RobotDeathEventListener) listener);
      }
      if (listener instanceof ScannedRobotEventListener) {
         addScannedRobotEventListener((ScannedRobotEventListener) listener);
      }
      if (listener instanceof WinEventListener) {
         addWinEventListener((WinEventListener) listener);
      }
      if (listener instanceof RoundEndedEventListener) {
         addRoundEndedEventListener((RoundEndedEventListener) listener);
      }
      if (listener instanceof BattleEndedEventListener) {
         addBattleEndedEventListener((BattleEndedEventListener) listener);
      }
      if (listener instanceof StatusEventListener) {
         addStatusEventListener((StatusEventListener) listener);
      }
      if (listener instanceof MouseWheelListener) {
         addMouseWheelListener((MouseWheelListener) listener);
      }
      if (listener instanceof MouseEventListener) {
         addMouseEventListener((MouseEventListener) listener);
      }
      if (listener instanceof KeyEventListener) {
         addKeyEventListener((KeyEventListener) listener);
      }
      if (listener instanceof PaintListener) {
         addPaintListener((PaintListener) listener);
      }
      if (listener instanceof DeathEventListener) {
         addDeathEventListener((DeathEventListener) listener);
      }
      if (listener instanceof SkippedTurnListener) {
         addSkippedTurnListener((SkippedTurnListener) listener);
      }
      if (listener instanceof CustomEventListener) {
         addCustomEventListener((CustomEventListener) listener);
      }
      if (listener instanceof MessageEventListener) {
         addMessageEventListener((MessageEventListener) listener);
      }
   }

   public boolean addBulletHitEventListener(BulletHitEventListener listener) {
      return bulletHitEventListeners.add(listener);
   }

   public boolean removeBulletHitEventListener(BulletHitEventListener listener) {
      return bulletHitEventListeners.remove(listener);
   }

   public boolean addBulletHitBulletEventListener(BulletHitBulletEventListener listener) {
      return bulletHitBulletEventListeners.add(listener);
   }

   public boolean removeBulletHitBulletEventListener(BulletHitBulletEventListener listener) {
      return bulletHitBulletEventListeners.remove(listener);
   }

   public boolean addBulletMissedEventListener(BulletMissedEventListener listener) {
      return bulletMissedEventListeners.add(listener);
   }

   public boolean removeBulletMissedEventListener(BulletMissedEventListener listener) {
      return bulletMissedEventListeners.remove(listener);
   }

   public boolean addHitByBulletEventListener(HitByBulletEventListener listener) {
      return hitByBulletEventListeners.add(listener);
   }

   public boolean removeHitByBulletEventListener(HitByBulletEventListener listener) {
      return hitByBulletEventListeners.remove(listener);
   }

   public boolean addHitRobotEventListener(HitRobotEventListener listener) {
      return hitRobotEventListeners.add(listener);
   }

   public boolean removeHitRobotEventListener(HitRobotEventListener listener) {
      return hitRobotEventListeners.remove(listener);
   }

   public boolean addHitWallEventListener(HitWallEventListener listener) {
      return hitWallEventListeners.add(listener);
   }

   public boolean removeHitWallEventListener(HitWallEventListener listener) {
      return hitWallEventListeners.remove(listener);
   }

   public boolean addRobotDeathEventListener(RobotDeathEventListener listener) {
      return robotDeathEventListeners.add(listener);
   }

   public boolean removeRobotDeathEventListener(RobotDeathEventListener listener) {
      return robotDeathEventListeners.remove(listener);
   }

   public boolean addScannedRobotEventListener(ScannedRobotEventListener listener) {
      return scannedRobotEventListeners.add(listener);
   }

   public boolean removeScannedRobotEventListener(ScannedRobotEventListener listener) {
      return scannedRobotEventListeners.remove(listener);
   }

   public boolean addWinEventListener(WinEventListener listener) {
      return winEventListeners.add(listener);
   }

   public boolean removeWinEventListener(WinEventListener listener) {
      return winEventListeners.remove(listener);
   }

   public boolean addRoundEndedEventListener(RoundEndedEventListener listener) {
      return roundEndedEventListeners.add(listener);
   }

   public boolean removeRoundEndedEventListener(RoundEndedEventListener listener) {
      return roundEndedEventListeners.remove(listener);
   }

   public boolean addBattleEndedEventListener(BattleEndedEventListener listener) {
      return battleEndedEventListeners.add(listener);
   }

   public boolean removeBattleEndedEventListener(BattleEndedEventListener listener) {
      return battleEndedEventListeners.remove(listener);
   }

   public boolean addStatusEventListener(StatusEventListener listener) {
      return statusEventListeners.add(listener);
   }

   public boolean removeStatusEventListener(StatusEventListener listener) {
      return statusEventListeners.remove(listener);
   }

   public boolean addMouseWheelListener(MouseWheelListener listener) {
      return mouseWheelListeners.add(listener);
   }

   public boolean removeMouseWheelListener(MouseWheelListener listener) {
      return mouseWheelListeners.remove(listener);
   }

   public boolean addMouseEventListener(MouseEventListener listener) {
      return mouseEventListeners.add(listener);
   }

   public boolean removeMouseEventListener(MouseEventListener listener) {
      return mouseEventListeners.remove(listener);
   }

   public boolean addKeyEventListener(KeyEventListener listener) {
      return keyEventListeners.add(listener);
   }

   public boolean removeKeyEventListener(KeyEventListener listener) {
      return keyEventListeners.remove(listener);
   }

   public boolean addPaintListener(PaintListener listener) {
      return paintListeners.add(listener);
   }

   public boolean removePaintListener(PaintListener listener) {
      return paintListeners.remove(listener);
   }

   public boolean addDeathEventListener(DeathEventListener listener) {
      return deathEventListeners.add(listener);
   }

   public boolean removeDeathEventListener(DeathEventListener listener) {
      return deathEventListeners.remove(listener);
   }

   public boolean addSkippedTurnListener(SkippedTurnListener listener) {
      return skippedTurnListeners.add(listener);
   }

   public boolean removeSkippedTurnListener(SkippedTurnListener listener) {
      return skippedTurnListeners.remove(listener);
   }

   public boolean addCustomEventListener(CustomEventListener listener) {
      return customEventListeners.add(listener);
   }

   public boolean removeCustomEventListener(CustomEventListener listener) {
      return customEventListeners.remove(listener);
   }

   public boolean addMessageEventListener(MessageEventListener listener) {
      return messageEventListeners.add(listener);
   }

   public boolean removeMessageEventListener(MessageEventListener listener) {
      return messageEventListeners.remove(listener);
   }

   @Override
   public final void onBulletHit(BulletHitEvent event) {
      for (BulletHitEventListener bulletHitEventListener : bulletHitEventListeners) {
         bulletHitEventListener.onBulletHitEvent(event);
      }
   }

   @Override
   public final void onBulletHitBullet(BulletHitBulletEvent event) {
      for (BulletHitBulletEventListener bulletHitBulletEventListener : bulletHitBulletEventListeners) {
         bulletHitBulletEventListener.onBulletHitBulletEvent(event);
      }
   }

   @Override
   public final void onBulletMissed(BulletMissedEvent event) {
      for (BulletMissedEventListener bulletMissedEventListener : bulletMissedEventListeners) {
         bulletMissedEventListener.onBulletMissedEvent(event);
      }
   }

   @Override
   public final void onHitByBullet(HitByBulletEvent event) {
      for (HitByBulletEventListener hitByBulletEventListener : hitByBulletEventListeners) {
         hitByBulletEventListener.onHitByBulletEvent(event);
      }
   }

   @Override
   public final void onHitRobot(HitRobotEvent event) {
      for (HitRobotEventListener hitRobotEventListener : hitRobotEventListeners) {
         hitRobotEventListener.onHitRobotEvent(event);
      }
   }

   @Override
   public final void onHitWall(HitWallEvent event) {
      for (HitWallEventListener hitWallEventListener : hitWallEventListeners) {
         hitWallEventListener.onHitWallEvent(event);
      }
   }

   @Override
   public final void onRobotDeath(RobotDeathEvent event) {
      for (RobotDeathEventListener robotDeathEventListener : robotDeathEventListeners) {
         robotDeathEventListener.onRobotDeathEvent(event);
      }
   }

   @Override
   public final void onScannedRobot(ScannedRobotEvent event) {
      for (ScannedRobotEventListener scannedRobotEventListener : scannedRobotEventListeners) {
         scannedRobotEventListener.onScannedRobotEvent(event);
      }
   }

   @Override
   public final void onWin(WinEvent event) {
      for (WinEventListener winEventListener : winEventListeners) {
         winEventListener.onWinEvent(event);
      }
   }

   @Override
   public final void onRoundEnded(RoundEndedEvent event) {
      for (RoundEndedEventListener roundEndedEventListener : roundEndedEventListeners) {
         roundEndedEventListener.onRoundEndedEvent(event);
      }
   }

   @Override
   public final void onBattleEnded(BattleEndedEvent event) {
      for (BattleEndedEventListener battleEndedEventListener : battleEndedEventListeners) {
         battleEndedEventListener.onBattleEndedEvent(event);
      }
   }

   @Override
   public final void onStatus(StatusEvent e) {
      for (StatusEventListener statusEventListener : statusEventListeners) {
         statusEventListener.onStatusEvent(e);
      }
   }

   @Override
   public final void onMouseWheelMoved(MouseWheelEvent e) {
      for (MouseWheelListener mouseWheelListener : mouseWheelListeners) {
         mouseWheelListener.onMouseWheelEvent(e);
      }
   }

   @Override
   public final void onMouseDragged(MouseEvent e) {
      for (MouseEventListener mouseEventListener : mouseEventListeners) {
         mouseEventListener.onMouseEvent(e);
      }
   }

   @Override
   public final void onMouseMoved(MouseEvent e) {
      for (MouseEventListener mouseEventListener : mouseEventListeners) {
         mouseEventListener.onMouseEvent(e);
      }
   }

   @Override
   public final void onMouseReleased(MouseEvent e) {
      for (MouseEventListener mouseEventListener : mouseEventListeners) {
         mouseEventListener.onMouseEvent(e);
      }
   }

   @Override
   public final void onMousePressed(MouseEvent e) {
      for (MouseEventListener mouseEventListener : mouseEventListeners) {
         mouseEventListener.onMouseEvent(e);
      }
   }

   @Override
   public final void onMouseExited(MouseEvent e) {
      for (MouseEventListener mouseEventListener : mouseEventListeners) {
         mouseEventListener.onMouseEvent(e);
      }
   }

   @Override
   public final void onMouseEntered(MouseEvent e) {
      for (MouseEventListener mouseEventListener : mouseEventListeners) {
         mouseEventListener.onMouseEvent(e);
      }
   }

   @Override
   public final void onMouseClicked(MouseEvent e) {
      for (MouseEventListener mouseEventListener : mouseEventListeners) {
         mouseEventListener.onMouseEvent(e);
      }
   }

   @Override
   public final void onKeyTyped(KeyEvent e) {
      for (KeyEventListener keyEventListener : keyEventListeners) {
         keyEventListener.onKeyEvent(e);
      }
   }

   @Override
   public final void onKeyReleased(KeyEvent e) {
      for (KeyEventListener keyEventListener : keyEventListeners) {
         keyEventListener.onKeyEvent(e);
      }
   }

   @Override
   public final void onKeyPressed(KeyEvent e) {
      for (KeyEventListener keyEventListener : keyEventListeners) {
         keyEventListener.onKeyEvent(e);
      }
   }

   @Override
   public final void onPaint(Graphics2D g) {
      for (PaintListener paintListener : paintListeners) {
         paintListener.onPaint(g);
      }
   }

   @Override
   public final void onDeath(DeathEvent event) {
      for (DeathEventListener deathEventListener : deathEventListeners) {
         deathEventListener.onDeathEvent(event);
      }
   }

   @Override
   public final void onSkippedTurn(SkippedTurnEvent event) {
      for (SkippedTurnListener skippedTurnListener : skippedTurnListeners) {
         skippedTurnListener.onSkippedTurnEvent(event);
      }
   }

   @Override
   public final void onCustomEvent(CustomEvent event) {
      for (CustomEventListener customEventListener : customEventListeners) {
         customEventListener.onCustomEvent(event);
      }
   }

   @Override
   public final void onMessageReceived(MessageEvent event) {
      for (MessageEventListener messageEventListener : messageEventListeners) {
         messageEventListener.onMessageEvent(event);
      }
   }
}
