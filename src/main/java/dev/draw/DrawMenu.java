package dev.draw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Iterator;

// BORED documentation: dev.draw.DrawMenu
public class DrawMenu {

   private static int                   startX        = 0;
   private static int                   startY        = 1;

   private static int                   recWidth      = 71;
   private static int                   recHeight     = 13;

   private static int                   baseRecWidth  = 71;
   private static int                   baseRecHeight = 13;

   private static int                   stringX       = 2;
   private static int                   stringY       = 2;

   private static Color                 colorOpen     = Color.CYAN;
   private static Color                 colorClosed   = Color.RED;

   private static boolean               open          = false;
   private static HashMap<String, Menu> menus         = new HashMap<String, Menu>();
   private static String                longest       = "Draw Menu";


   public static boolean getValue(String item, String menu) {
      return getValue(item, menu, false);
   }

   public static boolean getValue(String item, String menu, boolean def) {
      Menu m = menus.get(menu);
      if (m == null) {
         menus.put(menu, m = new Menu());
         longest = (longest.length() > item.length() ? longest : item);
      }
      return m.getValue(item, def);
   }

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



   private static class Menu {

      public static final Color        ITEM_ON  = Color.GREEN;
      public static final Color        ITEM_OFF = Color.RED;

      private boolean                  open     = false;
      private HashMap<String, Boolean> items    = new HashMap<String, Boolean>();
      private String                   longest  = " ";

      private int                      recWidth = 71;

      public boolean getValue(String item, boolean def) {
         Boolean value = this.items.get(item);
         if (value == null) {
            this.items.put(item, value = def);
            this.longest = (this.longest.length() > item.length() ? this.longest : item);
         }
         return value;
      }

      public boolean isOpen() {
         return this.open;
      }

      public void open() {
         this.open = true;
      }

      public void close() {
         this.open = false;
      }

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
