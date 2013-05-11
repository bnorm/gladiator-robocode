package kid.graphics;

import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.RenderingHints.Key;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.text.AttributedCharacterIterator;
import java.util.Map;

import kid.robot.RobotData;
import robocode.Robot;

// TODO documentation: class
// BORED code: methods

public class RGraphics extends Graphics2D {

   private Graphics2D graphics;
   private RobotData robot;

   public RGraphics(final Graphics2D graphics, final Robot myRobot) {
      init(graphics, myRobot);
   }

   private void init(final Graphics2D graphics, final Robot myRobot) {
      this.graphics = graphics;
      this.robot = new RobotData(myRobot);
   }

   public long getTime() {
      return robot.getTime();
   }

   public RobotData getRobot() {
      return robot;
   }

   // DONE
   public void drawLine(final int x1, final int y1, final int x2, final int y2) {
      graphics.drawLine(x1, y1, x2, y2);
   }

   // DONE
   public void drawLine(final double x1, final double y1, final double x2, final double y2) {
      drawLine((int) x1, (int) y1, (int) x2, (int) y2);
   }

   // DONE
   public void drawOval(final int x, final int y, final int width, final int height) {
      graphics.drawOval(x, y, width, height);
   }

   // DONE
   public void drawOval(final double x, final double y, final double width, final double height) {
      drawOval((int) x, (int) y, (int) width, (int) height);
   }

   // DONE
   public void drawOvalCenter(final int x, final int y, final int width, final int height) {
      drawOval((x - width / 2.0D), (y - height / 2.0D), width, height);
   }

   // DONE
   public void drawOvalCenter(final double x, final double y, final double width, final double height) {
      drawOvalCenter((int) x, (int) y, (int) width, (int) height);
   }

   // DONE
   public void fillOval(final int x, final int y, final int width, final int height) {
      graphics.fillOval(x, y, width, height);
   }

   // DONE
   public void fillOval(final double x, final double y, final double width, final double height) {
      fillOval((int) x, (int) y, (int) width, (int) height);
   }

   // DONE
   public void fillOvalCenter(final int x, final int y, final int width, final int height) {
      fillOval((x - width / 2.0D), (y - height / 2.0D), width, height);
   }

   // DONE
   public void fillOvalCenter(final double x, final double y, final double width, final double height) {
      fillOvalCenter((int) x, (int) y, (int) width, (int) height);
   }

   // DONE
   public void fillRect(final int x, final int y, final int width, final int height) {
      graphics.fillRect(x, y, width, height);
   }

   // DONE
   public void fillRect(final double x, final double y, final double width, final double height) {
      fillRect((int) x, (int) y, (int) width, (int) height);
   }

   // DONE
   public void fillRectCenter(final int x, final int y, final int width, final int height) {
      fillRect((x - width / 2.0), (y - height / 2.0), width, height);
   }

   // DONE
   public void fillRectCenter(final double x, final double y, final double width, final double height) {
      fillRectCenter((int) x, (int) y, (int) width, (int) height);
   }

   // DONE
   public void drawArc(final int x, final int y, final int circleWidth, final int circleHeight, final int startAngle,
         final int arcAngle) {
      graphics.drawArc(x, y, circleWidth, circleHeight, startAngle, arcAngle);
   }

   // DONE
   public void drawArc(final double x, final double y, final double circleWidth, final double circleHeight,
         final double startAngle, final double arcAngle) {
      drawArc((int) x, (int) y, (int) circleWidth, (int) circleHeight, (int) startAngle, (int) arcAngle);
   }

   // DONE
   public void drawArcCenter(final int x, final int y, final int circleWidth, final int circleHeight,
         final int startAngle, final int arcAngle) {
      drawArc((x - circleWidth / 2.0D), (y - circleHeight / 2.0D), circleWidth, circleHeight, startAngle, arcAngle);
   }

   // DONE
   public void drawArcCenter(final double x, final double y, final double circleWidth, final double circleHeight,
         final double startAngle, final double arcAngle) {
      drawArcCenter((int) x, (int) y, (int) circleWidth, (int) circleHeight, (int) startAngle, (int) arcAngle);
   }

   // DONE
   public Color getColor() {
      return graphics.getColor();
   }

   // DONE
   public void setColor(final Color c) {
      graphics.setColor(c);
   }

   // DONE
   public Font getFont() {
      return graphics.getFont();
   }

   // DONE
   public void setFont(final Font font) {
      graphics.setFont(font);
   }

   // DONE
   public void drawString(final String s, final double x, final double y) {
      drawString(s, (float) x, (float) y);
   }

   @Override
   public void drawString(String str, float x, float y) {
      graphics.drawString(str, x, y);
   }

   // DONE
   public void drawString(final String str, final int x, final int y) {
      graphics.drawString(str, x, y);
   }

   // DONE
   public void drawRect(final int x, final int y, final int width, final int height) {
      graphics.drawRect(x, y, width, height);
   }

   // DONE
   public void drawRect(final double x, final double y, final double width, final double height) {
      drawRect((int) x, (int) y, (int) width, (int) height);
   }

   // DONE
   public void drawRectCenter(final int x, final int y, final int width, final int height) {
      drawRect((x - width / 2.0D), (y - height / 2.0D), width, height);
   }

   // DONE
   public void drawRectCenter(final double x, final double y, final double width, final double height) {
      drawRectCenter((int) x, (int) y, (int) width, (int) height);
   }

   public void dispose() {
      graphics.dispose();
   }

   public void setPaintMode() {
      graphics.setPaintMode();
   }

   public void clearRect(final int x, final int y, final int width, final int height) {
      graphics.clearRect(x, y, width, height);
   }

   public void clipRect(final int x, final int y, final int width, final int height) {
      graphics.clipRect(x, y, width, height);
   }

   public void setClip(final int x, final int y, final int width, final int height) {
      graphics.setClip(x, y, width, height);
   }

   public void copyArea(final int x, final int y, final int width, final int height, final int dx, final int dy) {
      graphics.copyArea(x, y, width, height, dx, dy);
   }

   public void drawRoundRect(final int x, final int y, final int width, final int height, final int arcWidth,
         final int arcHeight) {
      graphics.drawRoundRect(x, y, width, height, arcWidth, arcHeight);
   }

   public void fillArc(final int x, final int y, final int circleWidth, final int circleHeight, final int startAngle,
         final int arcAngle) {
      graphics.fillArc(x, y, circleWidth, circleHeight, startAngle, arcAngle);
   }

   public void fillRoundRect(final int x, final int y, final int width, final int height, final int arcWidth,
         final int arcHeight) {
      graphics.fillRoundRect(x, y, width, height, arcWidth, arcHeight);
   }

   public void drawPolyline(final int[] xPoints, final int[] yPoints, final int nPoints) {
      graphics.drawPolyline(xPoints, yPoints, nPoints);
   }

   public void fillPolygon(final int[] xPoints, final int[] yPoints, final int nPoints) {
      graphics.fillPolygon(xPoints, yPoints, nPoints);
   }

   public void setXORMode(final Color c1) {
      graphics.setXORMode(c1);
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

   public void setClip(final Shape clip) {
      graphics.setClip(clip);
   }

   public FontMetrics getFontMetrics(final Font f) {
      return graphics.getFontMetrics(f);
   }

   public boolean drawImage(final Image img, final int dx1, final int dy1, final int dx2, final int dy2, final int sx1,
         final int sy1, final int sx2, final int sy2, final ImageObserver observer) {
      return graphics.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, observer);
   }

   public boolean drawImage(final Image img, final int x, final int y, final int width, final int height,
         final ImageObserver observer) {
      return graphics.drawImage(img, x, y, width, height, observer);
   }

   public boolean drawImage(final Image img, final int x, final int y, final ImageObserver observer) {
      return graphics.drawImage(img, x, y, observer);
   }

   public boolean drawImage(final Image img, final int dx1, final int dy1, final int dx2, final int dy2, final int sx1,
         final int sy1, final int sx2, final int sy2, final Color bgcolor, final ImageObserver observer) {
      return graphics.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, bgcolor, observer);
   }

   public boolean drawImage(final Image img, final int x, final int y, final int width, final int height,
         final Color bgcolor, final ImageObserver observer) {
      return graphics.drawImage(img, x, y, width, height, bgcolor, observer);
   }

   public boolean drawImage(final Image img, final int x, final int y, final Color bgcolor, final ImageObserver observer) {
      return graphics.drawImage(img, x, y, bgcolor, observer);
   }

   public void rotate(final double theta) {
      graphics.rotate(theta);
   }

   public void scale(final double sx, final double sy) {
      graphics.scale(sx, sy);
   }

   public void shear(final double shx, final double shy) {
      graphics.shear(shx, shy);
   }

   public void translate(final double tx, final double ty) {
      graphics.translate(tx, ty);
   }

   public void rotate(final double theta, final double x, final double y) {
      graphics.rotate(theta, x, y);
   }

   public void translate(final int x, final int y) {
      graphics.translate(x, y);
   }

   public Color getBackground() {
      return graphics.getBackground();
   }

   public void setBackground(final Color color) {
      graphics.setBackground(color);
   }

   public Composite getComposite() {
      return graphics.getComposite();
   }

   public void setComposite(final Composite comp) {
      graphics.setComposite(comp);
   }

   public GraphicsConfiguration getDeviceConfiguration() {
      return graphics.getDeviceConfiguration();
   }

   public Paint getPaint() {
      return graphics.getPaint();
   }

   public void setPaint(final Paint paint) {
      graphics.setPaint(paint);
   }

   public RenderingHints getRenderingHints() {
      return graphics.getRenderingHints();
   }

   public void clip(final Shape s) {
      graphics.clip(s);
   }

   public void draw(final Shape s) {
      graphics.draw(s);
   }

   public void draw(final Shape[] shapes) {
      for (Shape s : shapes)
         draw(s);
   }

   public void fill(final Shape s) {
      graphics.fill(s);
   }

   public Stroke getStroke() {
      return graphics.getStroke();
   }

   public void setStroke(final Stroke s) {
      graphics.setStroke(s);
   }

   public FontRenderContext getFontRenderContext() {
      return graphics.getFontRenderContext();
   }

   public void drawGlyphVector(final GlyphVector g, final float x, final float y) {
      graphics.drawGlyphVector(g, x, y);
   }

   public AffineTransform getTransform() {
      return graphics.getTransform();
   }

   public void setTransform(final AffineTransform Tx) {
      graphics.setTransform(Tx);
   }

   public void transform(final AffineTransform Tx) {
      graphics.transform(Tx);
   }

   public void drawString(final AttributedCharacterIterator iterator, final float x, final float y) {
      graphics.drawString(iterator, x, y);
   }

   public void drawString(final AttributedCharacterIterator iterator, final int x, final int y) {
      graphics.drawString(iterator, x, y);
   }

   public void addRenderingHints(final Map<?, ?> hints) {
      graphics.addRenderingHints(hints);
   }

   public void setRenderingHints(final Map<?, ?> hints) {
      graphics.setRenderingHints(hints);
   }

   public boolean hit(final Rectangle rect, final Shape s, final boolean onStroke) {
      return graphics.hit(rect, s, onStroke);
   }

   public void drawRenderedImage(final RenderedImage img, final AffineTransform xform) {
      graphics.drawRenderedImage(img, xform);
   }

   public void drawRenderableImage(final RenderableImage img, final AffineTransform xform) {
      graphics.drawRenderableImage(img, xform);
   }

   public void drawImage(final BufferedImage img, final BufferedImageOp op, final int x, final int y) {
      graphics.drawImage(img, op, x, y);
   }

   public Object getRenderingHint(final Key hintKey) {
      return graphics.getRenderingHint(hintKey);
   }

   public void setRenderingHint(final Key hintKey, final Object hintValue) {
      graphics.setRenderingHint(hintKey, hintValue);
   }

   public boolean drawImage(final Image img, final AffineTransform xform, final ImageObserver obs) {
      return graphics.drawImage(img, xform, obs);
   }

   public void drawPolygon(final int[] xPoints, final int[] yPoints, final int nPoints) {
      graphics.drawPolygon(xPoints, yPoints, nPoints);
   }

}
