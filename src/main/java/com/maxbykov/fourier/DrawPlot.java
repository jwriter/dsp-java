package com.maxbykov.fourier;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

/**
 * from Cay Horstmann "Java Core"; 8th edition; v1; chapter 7; "DrawTest"
 */

public class DrawPlot {
   
   public static void drawSpectrum(final double[] data) {
       
       EventQueue.invokeLater(new Runnable() {
          public void run() {
             DrawFrame frame = new DrawFrame(data);
             frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
             frame.setVisible(true);
          }
       });
   }
   
   void drawData(double[] data) {
       drawData(400, 1000, data, "Hi");
   }
   
   private void drawData(int height, int width, double[] data, String head) {
    
       
   }

   //test client
   private static int SIZE = 1024;
   public static void main(String[] args) {

       double[] spectrum = new double[SIZE];
       
       for (int i = 0; i < SIZE; i++) {
           spectrum[i] = i / ((double)SIZE);
       }
       spectrum[SIZE/3] = 1.0;
       
       drawSpectrum(spectrum);
   }
}

class DrawFrame2 extends JFrame {
    
   private final int HEIGHT = 400;
    
   public DrawFrame2(double[] data) {

      setTitle("Hi!");
      setSize(data.length, HEIGHT);

      DrawComponent component = new DrawComponent(data, HEIGHT);
      add(component);
   }
}


class DrawComponent2 extends JComponent {
   
    double[] data;
    int height;
    
   public DrawComponent2(double[] data, int height) {
        this.data = data;
        this.height = height;
    }

   public void paintComponent(Graphics g) {
       
      Graphics2D g2 = (Graphics2D) g;
      
      for (int i = 1; i < data.length; i++) {
          double x1 = i;
          double y1 = height - 80;
          double x2 = x1;
          double y2 = y1 - (height - 120) * data[i];
          g2.draw(new Line2D.Double(x1, y1, x2, y2));
      }
      
   }
}
