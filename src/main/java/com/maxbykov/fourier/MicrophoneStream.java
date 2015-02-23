package com.maxbykov.fourier;

import org.apache.commons.math3.complex.Complex;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.LineUnavailableException;
import java.util.Scanner;

/**
 * generate data stream from microphone
 */
public class MicrophoneStream {

    static boolean runflag = false;
    static int SIZE = 1024;

    public static void main(String[] args) {

        System.out.println("Press ENTER and sing");
        Scanner in = new Scanner(System.in);
        in.nextLine();

        System.out.println("Transforming...");
        runflag = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    AudioFormat format = new AudioFormat(16000.0f, 8, 1, true, false);
                    TargetDataLine microphone = AudioSystem.getTargetDataLine(format);

                    DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
                    microphone = (TargetDataLine) AudioSystem.getLine(info);
                    microphone.open(format);

                    byte[] data = new byte[SIZE];
                    int bytes;
                    int cnt = 0;
                    microphone.start();
                    Oscilloscope oscil = new Oscilloscope(400, 800, "Signal");
                    Analyzer analyzer = new Analyzer(400, 800, "Spectrum");
                    Complex[] spectrum;

                    while (runflag) {
                        bytes = microphone.read(data, 0, SIZE);
                        oscil.drawData(data);

                        spectrum = Butterfly.DecimationInFrequency(toComplex(data), true);
                        Utils.normalize(spectrum);
                        analyzer.drawData(Utils.magnitude(spectrum));
                    }
                } catch (LineUnavailableException e) {
                    System.out.println("Microphone unavailable");
                }
            }
        }).start();

        System.out.println("Press ENTER to stop");
        in.nextLine();
        runflag = false;

         System.out.println("It's allright");
    }

    private static Complex[] toComplex(byte[] data) {
        Complex[] res = new Complex[data.length];
        for (int i = 0; i < data.length; i++) {
            res[i] = new Complex((double) data[i]);
        }
        return res;
    }
}
