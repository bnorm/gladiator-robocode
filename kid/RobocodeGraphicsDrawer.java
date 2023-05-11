package kid;

import java.awt.*;
import java.awt.RenderingHints.Key;
import java.awt.font.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.awt.image.renderable.RenderableImage;
import java.text.AttributedCharacterIterator;
import java.util.Map;

import robocode.Robot;

// TODO documentation: class
// BORED code: methods

public class RobocodeGraphicsDrawer {

   private Graphics2D graphics;
   private Robot robot;
   private String commandString;

   public RobocodeGraphicsDrawer(Graphics2D graphics, Robot myRobot) {
      init(graphics, myRobot, new String());
   }

   public RobocodeGraphicsDrawer(Graphics2D graphics, Robot myRobot, String commandString) {
      init(graphics, myRobot, commandString);
   }

   private void init(Graphics2D graphics, Robot myRobot, String commandString) {
      this.graphics = graphics;
      this.robot = myRobot;
      this.commandString = commandString;
   }

   public String getCommandString() {
      return commandString;
   }

   public long getTime() {
      return robot.getTime();
   }


   public void dispose() {
      graphics.dispose();
      graphics = null;
   }

   public void setPaintMode() {
      graphics.setPaintMode();
   }

   public void clearRect(int x, int y, int width, int height) {
      graphics.clearRect(x, y, width, height);
   }

   public void clipRect(int x, int y, int width, int height) {
      graphics.clipRect(x, y, width, height);
   }


   // DONE
   public void drawLine(int x1, int y1, int x2, int y2) {
      graphics.drawLine(x1, y1, x2, y2);
   }

   // DONE
   public void drawLine(double x1, double y1, double x2, double y2) {
      drawLine((int) x1, (int) y1, (int) x2, (int) y2);
   }


   // DONE
   public void drawOval(int x, int y, int width, int height) {
      graphics.drawOval(x, y, width, height);
   }

   // DONE
   public void drawOval(double x, double y, double width, double height) {
      drawOval((int) x, (int) y, (int) width, (int) height);
   }

   // DONE
   public void drawOvalCenter(int x, int y, int width, int height) {
      drawOval((x - width / 2.0D), (y - height / 2.0D), width, height);
   }

   // DONE
   public void drawOvalCenter(double x, double y, double width, double height) {
      drawOval((x - width / 2.0D), (y - height / 2.0D), width, height);
   }


   // DONE
   public void fillOval(int x, int y, int width, int height) {
      graphics.fillOval(x, y, width, height);
   }

   // DONE
   public void fillOvalCenter(int x, int y, int width, int height) {
      fillOval((x - width / 2.0D), (y - height / 2.0D), width, height);
   }

   // DONE
   public void fillOvalCenter(double x, double y, double width, double height) {
      fillOval((x - width / 2.0D), (y - height / 2.0D), width, height);
   }


   // DONE
   public void fillOval(double x, double y, double width, double height) {
      fillOval((int) x, (int) y, (int) width, (int) height);
   }


   // DONE
   public void fillRectCenter(int x, int y, int width, int height) {
      fillRect((x - width / 2.0), (y - height / 2.0), width, height);
   }

   // DONE
   public void fillRectCenter(double x, double y, double width, double height) {
      fillRect((x - width / 2.0), (y - height / 2.0), width, height);
   }

   // DONE
   public void fillRect(int x, int y, int width, int height) {
      graphics.fillRect(x, y, width, height);
   }

   // DONE
   public void fillRect(double x, double y, double width, double height) {
      fillRect((int) x, (int) y, (int) width, (int) height);
   }


   public void setClip(int x, int y, int width, int height) {
      graphics.setClip(x, y, width, height);
   }

   public void copyArea(int x, int y, int width, int height, int dx, int dy) {
      graphics.copyArea(x, y, width, height, dx, dy);
   }


   // DONE
   public void drawArc(int x, int y, int circleWidth, int circleHeight, int startAngle, int arcAngle) {
      graphics.drawArc(x, y, circleWidth, circleHeight, (startAngle - 90), arcAngle);
   }

   // DONE
   public void drawArc(double x, double y, double circleWidth, double circleHeight, double startAngle, double arcAngle) {
      drawArc((int) x, (int) y, (int) circleWidth, (int) circleHeight, (int) startAngle, (int) arcAngle);
   }

   // DONE
   public void drawArcCenter(int x, int y, int circleWidth, int circleHeight, int startAngle, int arcAngle) {
      drawArc((x - circleWidth / 2.0D), (y - circleHeight / 2.0D), circleWidth, circleHeight, startAngle, arcAngle);
   }

   // DONE
   public void drawArcCenter(double x, double y, double circleWidth, double circleHeight, double startAngle, double arcAngle) {
      drawArc((x - circleWidth / 2.0D), (y - circleHeight / 2.0D), circleWidth, circleHeight, startAngle, arcAngle);
   }


   public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
      graphics.drawRoundRect(x, y, width, height, arcWidth, arcHeight);
   }


   public void fillArc(int x, int y, int circleWidth, int circleHeight, int startAngle, int arcAngle) {
      graphics.fillArc(x, y, circleWidth, circleHeight, startAngle, arcAngle);
   }


   public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
      graphics.fillRoundRect(x, y, width, height, arcWidth, arcHeight);
   }


   public void drawPolyline(int[] xPoints, int[] yPoints, int nPoints) {
      graphics.drawPolyline(xPoints, yPoints, nPoints);
   }


   public void fillPolygon(int[] xPoints, int[] yPoints, int nPoints) {
      graphics.fillPolygon(xPoints, yPoints, nPoints);
   }


   // DONE
   public Color getColor() {
      return graphics.getColor();
   }

   // DONE
   public void setColor(Color c) {
      graphics.setColor(c);
   }


   public void setXORMode(Color c1) {
      graphics.setXORMode(c1);
   }


   // DONE
   public Font getFont() {
      return graphics.getFont();
   }

   // DONE
   public void setFont(Font font) {
      graphics.setFont(font);
   }


   public Graphics create() {
      return graphics.create();
   }

   public Rectangle getClipBounds() {
      return graphics.getClipBounds();
   }

   public Shape getClip() {
      return graphics.getClip();
   }

   public void setClip(Shape clip) {
      graphics.setClip(clip);
   }

   public FontMetrics getFontMetrics(Font f) {
      return graphics.getFontMetrics(f);
   }

   public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, ImageObserver observer) {
      return graphics.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, observer);
   }

   public boolean drawImage(Image img, int x, int y, int width, int height, ImageObserver observer) {
      return graphics.drawImage(img, x, y, width, height, observer);
   }

   public boolean drawImage(Image img, int x, int y, ImageObserver observer) {
      return graphics.drawImage(img, x, y, observer);
   }

   public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, Color bgcolor, ImageObserver observer) {
      return graphics.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, bgcolor, observer);
   }

   public boolean drawImage(Image img, int x, int y, int width, int height, Color bgcolor, ImageObserver observer) {
      return graphics.drawImage(img, x, y, width, height, bgcolor, observer);
   }

   public boolean drawImage(Image img, int x, int y, Color bgcolor, ImageObserver observer) {
      return graphics.drawImage(img, x, y, bgcolor, observer);
   }

   public void rotate(double theta) {
      graphics.rotate(theta);
   }

   public void scale(double sx, double sy) {
      graphics.scale(sx, sy);
   }

   public void shear(double shx, double shy) {
      graphics.shear(shx, shy);
   }

   public void translate(double tx, double ty) {
      graphics.translate(tx, ty);
   }

   public void rotate(double theta, double x, double y) {
      graphics.rotate(theta, x, y);
   }

   public void translate(int x, int y) {
      graphics.translate(x, y);
   }

   public Color getBackground() {
      return graphics.getBackground();
   }

   public void setBackground(Color color) {
      graphics.setBackground(color);
   }

   public Composite getComposite() {
      return graphics.getComposite();
   }

   public void setComposite(Composite comp) {
      graphics.setComposite(comp);
   }

   public GraphicsConfiguration getDeviceConfiguration() {
      return graphics.getDeviceConfiguration();
   }

   public Paint getPaint() {
      return graphics.getPaint();
   }

   public void setPaint(Paint paint) {
      graphics.setPaint(paint);
   }

   public RenderingHints getRenderingHints() {
      return graphics.getRenderingHints();
   }

   public void clip(Shape s) {
      graphics.clip(s);
   }

   public void draw(Shape s) {
      graphics.draw(s);
   }

   public void fill(Shape s) {
      graphics.fill(s);
   }

   public Stroke getStroke() {
      return graphics.getStroke();
   }

   public void setStroke(Stroke s) {
      graphics.setStroke(s);
   }

   public FontRenderContext getFontRenderContext() {
      return graphics.getFontRenderContext();
   }

   public void drawGlyphVector(GlyphVector g, float x, float y) {
      graphics.drawGlyphVector(g, x, y);
   }

   public AffineTransform getTransform() {
      return graphics.getTransform();
   }

   public void setTransform(AffineTransform Tx) {
      graphics.setTransform(Tx);
   }

   public void transform(AffineTransform Tx) {
      graphics.transform(Tx);
   }


   // DONE
   public void drawString(String s, double x, double y) {
      graphics.drawString(s, (float) x, (float) y);
   }

   // DONE
   public void drawString(String str, int x, int y) {
      graphics.drawString(str, x, y);
   }


   public void drawString(AttributedCharacterIterator iterator, float x, float y) {
      graphics.drawString(iterator, x, y);
   }

   public void drawString(AttributedCharacterIterator iterator, int x, int y) {
      graphics.drawString(iterator, x, y);
   }

   public void addRenderingHints(Map<?, ?> hints) {
      graphics.addRenderingHints(hints);
   }

   public void setRenderingHints(Map<?, ?> hints) {
      graphics.setRenderingHints(hints);
   }

   public boolean hit(Rectangle rect, Shape s, boolean onStroke) {
      return graphics.hit(rect, s, onStroke);
   }

   public void drawRenderedImage(RenderedImage img, AffineTransform xform) {
      graphics.drawRenderedImage(img, xform);
   }

   public void drawRenderableImage(RenderableImage img, AffineTransform xform) {
      graphics.drawRenderableImage(img, xform);
   }

   public void drawImage(BufferedImage img, BufferedImageOp op, int x, int y) {
      graphics.drawImage(img, op, x, y);
   }

   public Object getRenderingHint(Key hintKey) {
      return graphics.getRenderingHint(hintKey);
   }

   public void setRenderingHint(Key hintKey, Object hintValue) {
      graphics.setRenderingHint(hintKey, hintValue);
   }

   public boolean drawImage(Image img, AffineTransform xform, ImageObserver obs) {
      return graphics.drawImage(img, xform, obs);
   }

   public void drawPolygon(int[] xPoints, int[] yPoints, int nPoints) {
      graphics.drawPolygon(xPoints, yPoints, nPoints);
   }


   // DONE
   public void drawRect(int x, int y, int width, int height) {
      graphics.drawRect(x, y, width, height);
   }

   // DONE
   public void drawRect(double x, double y, double width, double height) {
      drawRect((int) x, (int) y, (int) width, (int) height);
   }

   // DONE
   public void drawRectCenter(int x, int y, int width, int height) {
      drawRect((x - width / 2.0D), (y - height / 2.0D), width, height);
   }

   // DONE
   public void drawRectCenter(double x, double y, double width, double height) {
      drawRect((x - width / 2.0D), (y - height / 2.0D), width, height);
   }

}
