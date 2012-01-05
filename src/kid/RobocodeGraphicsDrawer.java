package kid;

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

import robocode.Robot;

public class RobocodeGraphicsDrawer {

    Robot MyRobot;
    Graphics2D Graphics;

    public RobocodeGraphicsDrawer(Robot MyRobot, Graphics2D Graphics) {
        this.MyRobot = MyRobot;
        this.Graphics = Graphics;
    }

    public void dispose() {
        Graphics.dispose();
    }

    public void setPaintMode() {
        Graphics.setPaintMode();
    }

    public void clearRect(int x, int y, int width, int height) {
        Graphics.clearRect(x, y, width, height);
    }

    public void clipRect(int x, int y, int width, int height) {
        Graphics.clipRect(x, y, width, height);
    }

    public void drawLine(int x1, int y1, int x2, int y2) {
        Graphics.drawLine(x1, (int) (MyRobot.getBattleFieldHeight() - y1), x2,
                          (int) (MyRobot.getBattleFieldHeight() - y2));
    }

    public void drawOval(int x, int y, int width, int height) {
        Graphics.drawOval((x - width / 2), (int) (MyRobot.getBattleFieldHeight() - y - height / 2), width, height);
    }

    public void fillOval(int x, int y, int width, int height) {
        Graphics.fillOval((x - width / 2), (int) (MyRobot.getBattleFieldHeight() - y - height / 2), width, height);
    }

    public void fillRect(int x, int y, int width, int height) {
        Graphics.fillRect((x - width / 2), (int) (MyRobot.getBattleFieldHeight() - y - height / 2), width, height);
    }

    public void setClip(int x, int y, int width, int height) {
        Graphics.setClip(x, y, width, height);
    }

    public void copyArea(int x, int y, int width, int height, int dx, int dy) {
        Graphics.copyArea(x, y, width, height, dx, dy);
    }

    public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        Graphics.drawArc(x, y, width, height, startAngle, arcAngle);
    }

    public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
        Graphics.drawRoundRect(x, y, width, height, arcWidth, arcHeight);
    }

    public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        Graphics.fillArc(x, y, width, height, startAngle, arcAngle);
    }

    public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
        Graphics.fillRoundRect(x, y, width, height, arcWidth, arcHeight);
    }

    public void drawPolyline(int[] xPoints, int[] yPoints, int nPoints) {
        Graphics.drawPolyline(xPoints, yPoints, nPoints);
    }

    public void fillPolygon(int[] xPoints, int[] yPoints, int nPoints) {
        Graphics.fillPolygon(xPoints, yPoints, nPoints);
    }

    public Color getColor() {
        return Graphics.getColor();
    }

    public void setColor(Color c) {
        Graphics.setColor(c);
    }

    public void setXORMode(Color c1) {
        Graphics.setXORMode(c1);
    }

    public Font getFont() {
        return Graphics.getFont();
    }

    public void setFont(Font font) {
        Graphics.setFont(font);
    }

    public Graphics create() {
        return Graphics.create();
    }

    public Rectangle getClipBounds() {
        return Graphics.getClipBounds();
    }

    public Shape getClip() {
        return Graphics.getClip();
    }

    public void setClip(Shape clip) {
        Graphics.setClip(clip);
    }

    public FontMetrics getFontMetrics(Font f) {
        return Graphics.getFontMetrics(f);
    }

    public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2,
                             ImageObserver observer) {
        return Graphics.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, observer);
    }

    public boolean drawImage(Image img, int x, int y, int width, int height, ImageObserver observer) {
        return Graphics.drawImage(img, x, y, width, height, observer);
    }

    public boolean drawImage(Image img, int x, int y, ImageObserver observer) {
        return Graphics.drawImage(img, x, y, observer);
    }

    public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2,
                             Color bgcolor, ImageObserver observer) {
        return Graphics.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, bgcolor, observer);
    }

    public boolean drawImage(Image img, int x, int y, int width, int height, Color bgcolor, ImageObserver observer) {
        return Graphics.drawImage(img, x, y, width, height, bgcolor, observer);
    }

    public boolean drawImage(Image img, int x, int y, Color bgcolor, ImageObserver observer) {
        return Graphics.drawImage(img, x, y, bgcolor, observer);
    }

    public void rotate(double theta) {
        Graphics.rotate(theta);
    }

    public void scale(double sx, double sy) {
        Graphics.scale(sx, sy);
    }

    public void shear(double shx, double shy) {
        Graphics.shear(shx, shy);
    }

    public void translate(double tx, double ty) {
        Graphics.translate(tx, ty);
    }

    public void rotate(double theta, double x, double y) {
        Graphics.rotate(theta, x, y);
    }

    public void translate(int x, int y) {
        Graphics.translate(x, y);
    }

    public Color getBackground() {
        return Graphics.getBackground();
    }

    public void setBackground(Color color) {
        Graphics.setBackground(color);
    }

    public Composite getComposite() {
        return Graphics.getComposite();
    }

    public void setComposite(Composite comp) {
        Graphics.setComposite(comp);
    }

    public GraphicsConfiguration getDeviceConfiguration() {
        return Graphics.getDeviceConfiguration();
    }

    public Paint getPaint() {
        return Graphics.getPaint();
    }

    public void setPaint(Paint paint) {
        Graphics.setPaint(paint);
    }

    public RenderingHints getRenderingHints() {
        return Graphics.getRenderingHints();
    }

    public void clip(Shape s) {
        Graphics.clip(s);
    }

    public void draw(Shape s) {
        Graphics.draw(s);
    }

    public void fill(Shape s) {
        Graphics.fill(s);
    }

    public Stroke getStroke() {
        return Graphics.getStroke();
    }

    public void setStroke(Stroke s) {
        Graphics.setStroke(s);
    }

    public FontRenderContext getFontRenderContext() {
        return Graphics.getFontRenderContext();
    }

    public void drawGlyphVector(GlyphVector g, float x, float y) {
        Graphics.drawGlyphVector(g, x, y);
    }

    public AffineTransform getTransform() {
        return Graphics.getTransform();
    }

    public void setTransform(AffineTransform Tx) {
        Graphics.setTransform(Tx);
    }

    public void transform(AffineTransform Tx) {
        Graphics.transform(Tx);
    }

    public void drawString(String s, float x, float y) {
        Graphics.drawString(s, (x), (float) (MyRobot.getBattleFieldHeight() - y));
    }

    public void drawString(String str, int x, int y) {
        Graphics.drawString(str, (int) (x), (int) (MyRobot.getBattleFieldHeight() - y));
    }

    public void drawString(AttributedCharacterIterator iterator, float x, float y) {
        Graphics.drawString(iterator, x, y);
    }

    public void drawString(AttributedCharacterIterator iterator, int x, int y) {
        Graphics.drawString(iterator, x, y);
    }

    public void addRenderingHints(Map hints) {
        Graphics.addRenderingHints(hints);
    }

    public void setRenderingHints(Map hints) {
        Graphics.setRenderingHints(hints);
    }

    public boolean hit(Rectangle rect, Shape s, boolean onStroke) {
        return Graphics.hit(rect, s, onStroke);
    }

    public void drawRenderedImage(RenderedImage img, AffineTransform xform) {
        Graphics.drawRenderedImage(img, xform);
    }

    public void drawRenderableImage(RenderableImage img, AffineTransform xform) {
        Graphics.drawRenderableImage(img, xform);
    }

    public void drawImage(BufferedImage img, BufferedImageOp op, int x, int y) {
        Graphics.drawImage(img, op, x, y);
    }

    public Object getRenderingHint(Key hintKey) {
        return Graphics.getRenderingHint(hintKey);
    }

    public void setRenderingHint(Key hintKey, Object hintValue) {
        Graphics.setRenderingHint(hintKey, hintValue);
    }

    public boolean drawImage(Image img, AffineTransform xform, ImageObserver obs) {
        return Graphics.drawImage(img, xform, obs);
    }

    public void drawPolygon(int[] xPoints, int[] yPoints, int nPoints) {
        Graphics.drawPolygon(xPoints, yPoints, nPoints);
    }

    public void drawRect(int x, int y, int width, int height) {
        Graphics.drawRect((x - width / 2), (int) (MyRobot.getBattleFieldHeight() - y - height / 2), width, height);
    }


}
