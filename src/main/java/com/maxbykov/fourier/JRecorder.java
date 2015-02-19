package com.maxbykov.fourier;

    
import javax.sound.sampled.*;

import java.io.*;
import java.util.Scanner;

import javax.sound.sampled.AudioFileFormat.Type;

public class JRecorder extends Thread {

    private TargetDataLine        m_line;
    private Type    m_targetType;
    private AudioInputStream    m_audioInputStream;
    private File            m_outputFile;

    public JRecorder(TargetDataLine m_line, Type m_targetType,  File m_outputFile) {
        this.m_line = m_line;
        this.m_targetType = m_targetType;
        this.m_audioInputStream = new AudioInputStream(m_line);
        this.m_outputFile = m_outputFile;
    }

    public void start() {
        m_line.start();
        super.start();
    }

    public void stopRecording() {
        m_line.stop();
        m_line.close();
    }


    public void run() {
        try {
            AudioSystem.write(m_audioInputStream, m_targetType, m_outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
                    
        File outputFile = new File("d:\\audiorec.wav");

        AudioFormat    audioFormat = new AudioFormat(
            AudioFormat.Encoding.PCM_SIGNED,
            44100.0F, 16, 2, 4, 44100.0F, false);

        DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioFormat);
        TargetDataLine targetDataLine = null;
        try {
            targetDataLine = (TargetDataLine) AudioSystem.getLine(info);
            targetDataLine.open(audioFormat);
        } catch (LineUnavailableException e) {
            System.out.println("unable to get a recording line");
            e.printStackTrace();
            System.exit(1);
        }

        Type    targetType = Type.WAVE;
        JRecorder j = new JRecorder(targetDataLine,targetType,outputFile);

        System.out.println("Press ENTER to start the recording.");
        Scanner in = new Scanner(System.in);
        in.nextLine();
        
        j.start();
        System.out.println("Recording...");

  
        System.out.println("Press ENTER to stop the recording.");
        in.nextLine();

        j.stopRecording();
        System.out.println("Recording stopped.");
    }

}


