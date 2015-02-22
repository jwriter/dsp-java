package com.maxbykov.fourier;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

/**
 * from Cay Horstmann "Java Core"; 8th edition; v1; chapter 7; "DrawTest"
 */

public class Oscilloscope {

    private double[] data;
    private int zero;
    private JComponent component;

    public Oscilloscope(final int height, final int width) {

        EventQueue.invokeLater(new Runnable() {
            public void run() {

                JFrame frame = new JFrame();
                frame.setTitle("Hi!");
                frame.setSize(width, height);
                zero = height / 2;

                 component = new JComponent(){
                    public void paintComponent(Graphics g) {
                        Graphics2D g2 = (Graphics2D) g;
                        //zero line
                        g2.draw(new Line2D.Double(1, zero, width - 1, zero));

                        if (data != null) {
                            double x1 = 0;
                            double y1 = zero - data[0];
                            double x2 = x1;
                            double y2 = y1;

                            for (int i = 1; i < data.length; i++) {
                                x2 = i;
                                y2 = zero - data[i];
                                g2.draw(new Line2D.Double(x1, y1, x2, y2));
                                x1 = x2;
                                y1 = y2;
                            }
                        }
                    }
                };

                frame.add(component);

                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }

    public void drawData(int[] signal) {
        this.data = new double[signal.length];
        for (int i = 0; i < signal.length; i++) {
            data[i] = (double) signal[i];
        }
        if (component != null)
            component.repaint();
    }

    public void drawData(byte[] signal) {
        this.data = new double[signal.length];
        for (int i = 0; i < signal.length; i++) {
            data[i] = (double) signal[i];
        }
        if (component != null)
            component.repaint();
    }

    public void setZero(int newZero) {
        zero = newZero;
        if (component != null)
            component.repaint();
    }


   //test client
   private static int SIZE = 100;
   private static int AMPL = 25;
   public static final int FREQUENCY = 20;

   public static void main(String[] args) {

       int[] testSignal = new int[SIZE];
       
       for (int i = 0; i < SIZE; i++) {
           testSignal[i] = (int) (AMPL * Math.sin(2 * Math.PI * ((double)i / (double)FREQUENCY)));

       }

       Oscilloscope oscill = new Oscilloscope(400, 800);
       //oscill.setZero(-100);
       oscill.drawData(testSignal);
       oscill.setZero(100);
   }
}