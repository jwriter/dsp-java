package com.maxbykov.fourier;

import java.awt.*;
import java.awt.geom.*;

public class Oscilloscope extends Viewer {

    public Oscilloscope(int height, int width, String title) {
        super(height, width, title);
    }

    @Override
    void drawView(Graphics2D g) {
        g.draw(new Line2D.Double(1, zero, width - 1, zero));

        if (data != null) {
            double x1 = 0;
            double y1 = zero - data[0];
            double x2 = x1;
            double y2 = y1;

            double k = ((double)width)/data.length;
            for (int i = 1; i * k < width; i++) {
                if (i < data.length) {
                    x2 = i*k;
                    y2 = zero - data[i];
                    g.draw(new Line2D.Double(x1, y1, x2, y2));
                    x1 = x2;
                    y1 = y2;
                } else {
                    System.out.println("OOOPS " + i + "   k-" + k);
                }
            }
        }
    }



   //test client
   private static int SIZE = 400;
   private static int AMPL = 25;
   public static final int FREQUENCY = 20;

   public static void main(String[] args) {

       int[] testSignal = new int[SIZE];
       
       for (int i = 0; i < SIZE; i++) {
           testSignal[i] = (int) (AMPL * Math.sin(2 * Math.PI * ((double)i / (double)FREQUENCY)));

       }

       Oscilloscope oscill = new Oscilloscope(400, 800, "SignalShort");
       oscill.drawData(testSignal);

       Oscilloscope oscill2 = new Oscilloscope(400, 200, "SignalLong");
       oscill2.drawData(testSignal);

       oscill.setZero(100);

   }
}