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
 * A utility class that provides the coder with the ability to add a draw menu to the lower left of
 * the battlefield. This menu is a boolean menu that dynamically creates elements as the user
 * requests them. When deciding to draw something the user should check to see if the draw menu item
 * has been enabled. This functionality provides a quick and clean way for the user to dynamically
 * control what a robot draws to the screen.
 * 
 * @author Brian Norman
 * @version 1.1
 */
public class DrawMenu {

   /**
    * The x-coordinate for the starting location of the menu.
    */
   private static int                   START_X        = 0;
   /**
    * The y-coordinate for the starting location of the menu.
    */
   private static int                   START_Y        = 1;

   /**
    * The width of the start menu.
    */
   private static int                   BASE_WIDTH     = 71;

   /**
    * The height of the start menu.
    */
   private static int                   BASE_HEIGHT    = 13;

   /**
    * The width of the sub-menus in the menu.
    */
   private static int                   MENU_WIDTH     = 71;

   /**
    * The height of the sub-menus in the menu.
    */
   private static int                   MENU_HEIGHT    = 13;

   /**
    * The relative x-coordinate for the starting location of the sub-menus. This is relative to the
    * lower right point of the title item.
    */
   private static int                   STRING_X       = 2;

   /**
    * The relative y-coordinate for the starting location of the sub-menus. This is relative to the
    * lower right point of the title item.
    */
   private static int                   STRING_Y       = 2;

   /**
    * The border color of a sub-menu that is open.
    */
   private static Color                 COLOR_OPEN     = Color.CYAN;

   /**
    * The border color of a sub-menu that is closed.
    */
   private static Color                 COLOR_CLOSED   = Color.RED;

   /**
    * If the whole menu is currently open.
    */
   private static boolean               OPEN           = false;

   /**
    * The HashMap of sub-menus keyed by their titles.
    */
   private static HashMap<String, Menu> MENUS          = new HashMap<String, Menu>();

   /**
    * The title of the menu item with the longest character length.
    */
   private static String                LONGEST_STRING = "Draw Menu";

   /**
    * 
    */
   private static File                  LAST_FILE      = null;

   private static Color                 SAVE_COLOR     = Color.RED;

   /**
    * Returns the menu item value of the specified item in the specified menu. If the the item does
    * not exist it is created with a starting value of <code>false</code>. See
    * {@link #getValue(String, String, boolean)} for a usage example.
    * 
    * @param menu
    *           the title of the sub-menu.
    * @param item
    *           the title of the item
    * @return the current value of the item.
    * @see #getValue(String, String, boolean)
    */
   public static boolean getValue(String menu, String item) {
      return getValue(menu, item, false);
   }

   /**
    * Returns the item value of the specified item in the specified sub-menu. If the the item does
    * not exist it is created with the specified default value. An example for use can be seen
    * bellow.
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
    * @param menu
    *           the title of the sub-menu.
    * @param item
    *           the title of the item
    * @param def
    *           the default value for the item.
    * @return the current value of the item.
    */
   public static boolean getValue(String menu, String item, boolean def) {
      Menu m = MENUS.get(menu);
      if (m == null) {
         MENUS.put(menu, m = new Menu());
         LONGEST_STRING = (LONGEST_STRING.length() > item.length() ? LONGEST_STRING : item);
      }
      return m.getValue(item, def);
   }

   /**
    * Loads the specified configuration from the robot's directory that has specified values for
    * menu items. See the sample code bellow for an example.
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
            } else {
               System.err.println("Trouble reading line: " + line);
            }
         }
         in.close();
         LAST_FILE = file;
         System.out.println("DrawMenu loaded from " + file.getName());
      } catch (IOException e) {
      }
   }

   /**
    * Saves the configuration to the specified file in the robot's directory. See the sample code
    * bellow for an example.
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
         for (String s : MENUS.keySet()) {
            Menu m = MENUS.get(s);
            Set<String> items = m.getItems();
            for (String i : items)
               out.println(s + "," + i + "," + m.getValue(i, false));
         }
         out.close();
         LAST_FILE = file;
         System.out.println("DrawMenu saved to " + file.getName());
      } catch (IOException e) {
      }
   }

   /**
    * Processes a MouseEvent by checking for clicked events in the area of the menu. This method
    * should be called every time the robot receives a mouse event. See the sample code bellow for
    * an example.
    * 
    * <pre>
    * ...
    * // As a robot event catcher
    * public void onMouseClicked(MouseEvent e) {
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
         SAVE_COLOR = Color.RED;
         double x = e.getX() - START_X;
         double y = e.getY() - START_Y;

         if (y < 0.0 && x < 0.0) {
            return;
         }

         if (OPEN) {
            boolean found = false;

            // finds if the click was in a sub-menu
            Iterator<Menu> iter = MENUS.values().iterator();
            for (int i = 0; iter.hasNext() && !found; i++) {
               found = iter.next().inMenu(e, i);
            }

            // finds if the click was on a menu title
            if (!found) {
               if (x <= MENU_WIDTH && x >= 0.0D && y >= MENU_HEIGHT) {
                  iter = MENUS.values().iterator();
                  for (int i = 0; iter.hasNext() && !found; i++) {
                     Menu menu = iter.next();
                     if (y <= (i + 2) * MENU_HEIGHT) {
                        if (menu.isOpen()) {
                           menu.close();
                        } else {
                           for (Menu m : MENUS.values()) {
                              m.close();
                           }
                           menu.open();
                        }
                        found = true;
                     }
                  }
               }
            }

            // finds if the click was on the save button
            if (!found && LAST_FILE != null) {
               if (y <= BASE_HEIGHT && x > BASE_WIDTH && x <= 2 * BASE_WIDTH + 1) {
                  save(LAST_FILE);
                  SAVE_COLOR = Color.GREEN;
                  // OPEN = false;
                  found = true;
               }
            }

            // click was not found in all menus, close the menu
            if (!found) {
               OPEN = false;
               for (Menu m : MENUS.values()) {
                  m.close();
               }
            }

            // menu is not open, check to open
         } else if (x <= BASE_WIDTH && y <= BASE_HEIGHT) {
            OPEN = true;
         }
      }
   }

   /**
    * Draws the menu onto the battlefield. This method should be called every time the robot draws
    * to the battlefield. See the sample bellow for an example.
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

      BASE_WIDTH = (int) graphics.getFontMetrics().getStringBounds("Draw Menu", graphics).getWidth() + 10;
      BASE_HEIGHT = (int) graphics.getFontMetrics().getStringBounds(LONGEST_STRING, graphics).getHeight() + 2;

      if (OPEN) {
         MENU_WIDTH = (int) graphics.getFontMetrics().getStringBounds(LONGEST_STRING, graphics).getWidth();
         MENU_HEIGHT = BASE_HEIGHT;

         // draw menus
         int i = 0;
         for (String key : MENUS.keySet()) {
            Menu menu = MENUS.get(key);
            graphics.setColor(menu.isOpen() ? COLOR_OPEN : COLOR_CLOSED);
            graphics.drawRect(START_X, START_Y + (i + 1) * MENU_HEIGHT, MENU_WIDTH - 1, MENU_HEIGHT - 1);
            graphics.drawString(key, START_X + STRING_X, START_Y + STRING_Y + (i + 1) * MENU_HEIGHT);
            menu.draw(graphics, i);
            i++;
         }

         // draw save button
         graphics.setColor(SAVE_COLOR);
         graphics.drawRect(BASE_WIDTH + 1, START_Y, BASE_WIDTH - 1, BASE_HEIGHT - 1);
         graphics.drawString("Save Now", BASE_WIDTH + STRING_X, START_Y + STRING_Y);
      }

      graphics.setColor(OPEN ? COLOR_OPEN : COLOR_CLOSED);
      graphics.drawRect(START_X, START_Y, BASE_WIDTH - 1, BASE_HEIGHT - 1);
      graphics.drawString("Draw Menu", START_X + STRING_X, START_Y + STRING_Y);

      graphics.setColor(c);
   }

   /**
    * An inner-class representing the sub-menus in the draw menu. Each menu has its own list of
    * items that store the values.
    * 
    * @author Brian Norman
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
       * The title of the item in the sub-menu with the longest character length.
       */
      private String                   longest  = " ";

      /**
       * The width of the items in the sub-menu.
       */
      private int                      recWidth = 71;

      /**
       * Returns the item value of the specified item in the sub-menu. If the the item does not
       * exist it is created with the specified default value.
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
       * Returns if the specified MouseEvent is in the sub-menu. The index of the sub-menu is also
       * passed in to calculate the relative position of the mouse click.
       * 
       * @param e
       *           the mouse event the robot received.
       * @param menuNumber
       *           the index of the sub-menu in the draw menu.
       * @return if the mouse click was in the sub-menu.
       */
      public boolean inMenu(MouseEvent e, int menuNumber) {
         if (this.open) {
            int menuStartX = START_X + DrawMenu.MENU_WIDTH;
            int menuStartY = START_Y + (menuNumber + 1) * MENU_HEIGHT;

            int x = e.getX() - menuStartX;
            int y = e.getY() - menuStartY;
            if (x <= this.recWidth && x >= 0.0D && y >= 0.0D) {
               for (int i = 0; i < this.items.keySet().size(); i++) {
                  if (y <= (i + 1) * MENU_HEIGHT) {
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
       * Draws the sub-menu onto the battlefield. The index of the sub-menu is also passed in to
       * calculate the absolute position of the sub-menu on the battlefield.
       * 
       * @param graphics
       *           the object that handles the drawing.
       * @param menuNumber
       *           the index of the sub-menu in the draw menu.
       */
      public void draw(Graphics graphics, int menuNumber) {
         if (this.open) {
            this.recWidth = (int) graphics.getFontMetrics().getStringBounds(this.longest, graphics).getWidth() + 20;

            int menuStartX = START_X + DrawMenu.MENU_WIDTH;
            int menuStartY = START_Y + (menuNumber + 1) * MENU_HEIGHT;

            int i = 0;
            for (String key : this.items.keySet()) {
               graphics.setColor(this.items.get(key) ? ITEM_ON : ITEM_OFF);
               graphics.drawRect(menuStartX, menuStartY + i * MENU_HEIGHT, this.recWidth - 1, MENU_HEIGHT - 1);
               graphics.drawString(key, menuStartX + STRING_X, menuStartY + STRING_Y + i * MENU_HEIGHT);
               i++;
            }
         }
      }

   }

}
