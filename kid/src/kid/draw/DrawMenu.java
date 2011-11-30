package kid.draw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import robocode.RobocodeFileWriter;

/**
 * A utility class that provides the coder with the ability to add a draw menu
 * to the lower left of the battlefield. This menu is a boolean menu that
 * dynamically creates elements as the user requests them. When deciding to draw
 * something the user should check to see if the draw menu item has been
 * enabled. This functionality provides a quick and clean way for the user to
 * dynamically control what a robot draws to the screen.
 * 
 * @author Brian Norman (KID)
 * @version 1.0
 */
public class DrawMenu {

   /**
    * The x-coordinate for the starting location of the menu.
    */
   private static int                   startX        = 0;
   /**
    * The y-coordinate for the starting location of the menu.
    */
   private static int                   startY        = 1;

   /**
    * The width of the start menu.
    */
   private static int                   baseRecWidth  = 71;

   /**
    * The height of the start menu.
    */
   private static int                   baseRecHeight = 13;

   /**
    * The width of the sub-menus in the menu.
    */
   private static int                   recWidth      = 71;

   /**
    * The height of the sub-menus in the menu.
    */
   private static int                   recHeight     = 13;

   /**
    * The relative x-coordinate for the starting location of the sub-menus. This
    * is relative to the lower right point of the title item.
    */
   private static int                   stringX       = 2;

   /**
    * The relative y-coordinate for the starting location of the sub-menus. This
    * is relative to the lower right point of the title item.
    */
   private static int                   stringY       = 2;

   /**
    * The border color of a sub-menu that is open.
    */
   private static Color                 colorOpen     = Color.CYAN;

   /**
    * The border color of a sub-menu that is closed.
    */
   private static Color                 colorClosed   = Color.RED;

   /**
    * If the whole menu is currently open.
    */
   private static boolean               open          = false;

   /**
    * The HashMap of sub-menus keyed by their titles.
    */
   private static HashMap<String, Menu> menus         = new HashMap<String, Menu>();

   /**
    * The title of the menu item with the longest character length.
    */
   private static String                longest       = "Draw Menu";

   /**
    * Returns the menu item value of the specified item in the specified menu.
    * If the the item does not exist it is created with a starting value of
    * <code>false</code>. See {@link #getValue(String, String, boolean)} for a
    * usage example.
    * 
    * @param item
    *           the title of the item
    * @param menu
    *           the title of the sub-menu.
    * @return the current value of the item.
    * @see #getValue(String, String, boolean)
    */
   public static boolean getValue(String item, String menu) {
      return getValue(item, menu, false);
   }

   /**
    * Returns the item value of the specified item in the specified sub-menu. If
    * the the item does not exist it is created with the specified default
    * value. An example for use can be seen bellow.
    * 
    * <pre>
    * ...
    * // Wherever you do graphical debugging
    * if (DrawMenu.getValue("Menu", "Item", true)) {
    *    ...
    *    // Do your graphical debugging stuff
    *    ...
    * }
    * ...
    * </pre>
    * 
    * @param item
    *           the title of the item
    * @param menu
    *           the title of the sub-menu.
    * @param def
    *           the default value for the item.
    * @return the current value of the item.
    */
   public static boolean getValue(String item, String menu, boolean def) {
      Menu m = menus.get(menu);
      if (m == null) {
         menus.put(menu, m = new Menu());
         longest = (longest.length() > item.length() ? longest : item);
      }
      return m.getValue(item, def);
   }

   /**
    * Loads the specified configuration from the robot's directory that has
    * specified values for menu items. See the sample code bellow for an
    * example.
    * 
    * <pre>
    * public void run() {
    *    DrawMenu.load(getDataFile("menu.draw"));
    *    ...
    *    // The rest of your run() code
    *    ...
    * }
    * </pre>
    * 
    * @param file
    *           the file to load.
    */
   public static void load(File file) {
      try {
         BufferedReader in = new BufferedReader(new FileReader(file));
         String line;
         while ((line = in.readLine()) != null) {
            String[] split = line.split("\\s*,\\s*");
            if (split.length > 2) {
               DrawMenu.getValue(split[0], split[1], Boolean.parseBoolean(split[2]));
            }
         }
         in.close();
         System.out.println("DrawMenu loaded from " + file.getName());
      } catch (IOException e) {
      }
   }

   /**
    * Saves the configuration to the specified file in the robot's directory.
    * See the sample code bellow for an example.
    * 
    * <pre>
    * // As a robot event catcher
    * public void onDeath(DeathEvent event) {
    *    ...
    *    DrawMenu.save(getDataFile("menu.draw"));
    * }
    * 
    * // As a robot event catcher
    * public void onWin(WinEvent event) {
    *    ...
    *    DrawMenu.save(getDataFile("menu.draw"));
    * }
    * </pre>
    * 
    * @param file
    *           the file to save to.
    */
   public static void save(File file) {
      try {
         PrintWriter out = new PrintWriter(new RobocodeFileWriter(file));
         for (String s : menus.keySet()) {
            Menu m = menus.get(s);
            Set<String> items = m.getItems();
            for (String i : items)
               out.println(s + "," + i + "," + m.getValue(i, false));
         }
         out.close();
         System.out.println("DrawMenu saved to " + file.getName());
      } catch (IOException e) {
      }
   }

   /**
    * Processes a MouseEvent by checking for clicked events in the area of the
    * menu. This method should be called every time the robot receives a mouse
    * event. See the sample code bellow for an example.
    * 
    * <pre>
    * ...
    * // As a robot event catcher
    * public void onMouseEvent(MouseEvent e) {
    *    DrawMenu.inMouseEvent(e);
    *    ...
    * }
    * ...
    * </pre>
    * 
    * @param e
    *           the mouse event to precess.
    */
   public static void inMouseEvent(MouseEvent e) {
      if (e.getID() == MouseEvent.MOUSE_CLICKED) {
         if (open) {
            boolean found = false;

            // finds item
            Iterator<Menu> iter = menus.values().iterator();
            for (int i = 0; iter.hasNext() && !found; i++) {
               found = iter.next().inMenu(e, i);
            }

            if (!found) {
               double x = e.getX() - startX;
               double y = e.getY() - startY;
               if (x <= recWidth && x >= 0.0D && y >= recHeight) {
                  iter = menus.values().iterator();
                  for (int i = 0; iter.hasNext() && !found; i++) {
                     Menu menu = iter.next();
                     if (y <= (i + 2) * recHeight) {
                        if (menu.isOpen()) {
                           menu.close();
                        } else {
                           for (Menu m : menus.values())
                              m.close();
                           menu.open();
                        }
                        found = true;
                     }
                  }
               }

               if (!found) {
                  open = false;
                  for (Menu m : menus.values())
                     m.close();
               }
            }
         } else {
            double x = e.getX() - startX;
            double y = e.getY() - startY;
            if (x <= baseRecWidth && y <= baseRecHeight && y >= 0.0D && x >= 0.0D)
               open = true;
         }
      }
   }

   /**
    * Draws the menu onto the battlefield. This method should be called every
    * time the robot draws to the battlefield. See the sample bellow for an
    * example.
    * 
    * <pre>
    * ...
    * // As an advanced robot override
    * public void onPaint(Graphics2D g) {
    *    DrawMenu.draw(g);
    *    ...
    *    // Other drawing things
    *    ...
    * }
    * ...
    * </pre>
    * 
    * @param graphics
    *           the object that handles the drawing.
    */
   public static void draw(Graphics graphics) {
      Color c = graphics.getColor();

      baseRecWidth = (int) graphics.getFontMetrics().getStringBounds("Draw Menu", graphics).getWidth() + 20;
      baseRecHeight = (int) graphics.getFontMetrics().getStringBounds(longest, graphics).getHeight() + 2;

      if (open) {
         recWidth = (int) graphics.getFontMetrics().getStringBounds(longest, graphics).getWidth();
         recHeight = baseRecHeight;

         int i = 0;
         for (String key : menus.keySet()) {
            Menu menu = menus.get(key);
            graphics.setColor(menu.isOpen() ? colorOpen : colorClosed);
            graphics.drawRect(startX, startY + (i + 1) * recHeight, recWidth - 1, recHeight - 1);
            graphics.drawString(key, startX + stringX, startY + stringY + (i + 1) * recHeight);
            menu.draw(graphics, i);
            i++;
         }
      }

      graphics.setColor(open ? colorOpen : colorClosed);
      graphics.drawRect(startX, startY, baseRecWidth - 1, baseRecHeight - 1);
      graphics.drawString("Draw Menu", startX + stringX, startY + stringY);

      graphics.setColor(c);
   }

   /**
    * An inner-class representing the sub-menus in the draw menu. Each menu has
    * its own list of items that store the values.
    * 
    * @author Brian Norman (KID)
    * @version 1.0
    */
   private static class Menu {

      /**
       * The border color of an item that is selected.
       */
      public static final Color        ITEM_ON  = Color.GREEN;

      /**
       * The border color of an item that is not selected.
       */
      public static final Color        ITEM_OFF = Color.RED;

      /**
       * If the sub-menu is currently open.
       */
      private boolean                  open     = false;

      /**
       * A HashMap of item values keyed by the title of the item.
       */
      private HashMap<String, Boolean> items    = new HashMap<String, Boolean>();

      /**
       * The title of the item in the sub-menu with the longest character
       * length.
       */
      private String                   longest  = " ";

      /**
       * The width of the items in the sub-menu.
       */
      private int                      recWidth = 71;

      /**
       * Returns the item value of the specified item in the sub-menu. If the
       * the item does not exist it is created with the specified default value.
       * 
       * @param item
       *           the title of the item
       * @param def
       *           the default value for the item.
       * @return the current value of the item.
       */
      public boolean getValue(String item, boolean def) {
         Boolean value = this.items.get(item);
         if (value == null) {
            this.items.put(item, value = def);
            this.longest = (this.longest.length() > item.length() ? this.longest : item);
         }
         return value;
      }

      /**
       * Returns the set of titles for the items in the sub-menu.
       * 
       * @return the set of tiles.
       */
      public Set<String> getItems() {
         return items.keySet();
      }

      /**
       * Returns if the sub-menu is open.
       * 
       * @return if the sub-menu is open.
       */
      public boolean isOpen() {
         return this.open;
      }

      /**
       * Opens the sub-menu.
       */
      public void open() {
         this.open = true;
      }

      /**
       * Closes the sub-menu.
       */
      public void close() {
         this.open = false;
      }

      /**
       * Returns if the specified MouseEvent is in the sub-menu. The index of
       * the sub-menu is also passed in to calculate the relative position of
       * the mouse click.
       * 
       * @param e
       *           the mouse event the robot received.
       * @param menuNumber
       *           the index of the sub-menu in the draw menu.
       * @return if the mouse click was in the sub-menu.
       */
      public boolean inMenu(MouseEvent e, int menuNumber) {
         if (this.open) {
            int menuStartX = startX + DrawMenu.recWidth;
            int menuStartY = startY + (menuNumber + 1) * recHeight;

            int x = e.getX() - menuStartX;
            int y = e.getY() - menuStartY;
            if (x <= this.recWidth && x >= 0.0D && y >= 0.0D) {
               for (int i = 0; i < this.items.keySet().size(); i++) {
                  if (y <= (i + 1) * recHeight) {
                     String s = (String) (this.items.keySet().toArray())[i];
                     this.items.put(s, !this.items.get(s));
                     return true;
                  }
               }
            }
         }
         return false;
      }

      /**
       * Draws the sub-menu onto the battlefield. The index of the sub-menu is
       * also passed in to calculate the absolute position of the sub-menu on
       * the battlefield.
       * 
       * @param graphics
       *           the object that handles the drawing.
       * @param menuNumber
       *           the index of the sub-menu in the draw menu.
       */
      public void draw(Graphics graphics, int menuNumber) {
         if (this.open) {
            this.recWidth = (int) graphics.getFontMetrics().getStringBounds(this.longest, graphics).getWidth() + 20;

            int menuStartX = startX + DrawMenu.recWidth;
            int menuStartY = startY + (menuNumber + 1) * recHeight;

            int i = 0;
            for (String key : this.items.keySet()) {
               graphics.setColor(this.items.get(key) ? ITEM_ON : ITEM_OFF);
               graphics.drawRect(menuStartX, menuStartY + i * recHeight, this.recWidth - 1, recHeight - 1);
               graphics.drawString(key, menuStartX + stringX, menuStartY + stringY + i * recHeight);
               i++;
            }
         }
      }

   }

}
