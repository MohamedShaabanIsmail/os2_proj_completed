/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tast;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Ahmed Shaaban
 */
public class Student extends Thread {
    
    private static Semaphore takeTA = new Semaphore(1), takeChair = new Semaphore(1);
    private static int tasWorking=0, tasSleeping, studentWating=0, studentLater;
    
    private int id;
    private int taWaitInterval; // To simulate work
    private volatile boolean running = true;

    public Student(int id, int waitInterval) {
        super("Student#" + id); // Runnable Thread Name
        this.id = id;

        if (waitInterval < 0) // Default in the creation of Student Threads is zero
            throw new IllegalArgumentException("Invalid Wait Interval");

        this.taWaitInterval = waitInterval;
        
    }

    public Student(int id) {
        this(id, 0); // Calls the main constructor
    }

    @Override
    public void run() {
        while (running) {
            if (Simulation.getChairs().tryAcquire()) { // Acquiring a chair
                try {
                    takeChair.acquire();
                    test.studentsWaitingOutput(++studentWating);
                    test.studentsLaterOutput(--studentLater);
                    takeChair.release();
                    // Acquiring a TA
                    Simulation.getTAs().acquire();
                    takeTA.acquire();
                    test.studentsWaitingOutput(--studentWating);
                    test.tasWorkingOutput(++tasWorking);
                    test.tasSleepingOutput(--tasSleeping);
                    takeTA.release();
                    System.out.println(getName() + " is being helped by a TA.");
                    
                    // Simulating TA help time
                    if (taWaitInterval > 0) {
                        Thread.sleep(taWaitInterval * 1000);
                    } else {
                        randomWait(1, 3); // Default wait time
                    }

                    Simulation.getTAs().release(); // Releasing the TA
                    takeTA.acquire();
                    test.tasWorkingOutput(--tasWorking);
                    test.tasSleepingOutput(++tasSleeping);
                    takeTA.release();
                    System.out.println(getName() + " is done and leaving.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Simulation.getChairs().release(); // Releasing the chair
                }
                return;
            } else {
                System.out.println(getName() + " couldn't find a chair. Trying again later.");
                randomWait(1, 3); // Wait before trying again
            }
        }
    }
    
    public static void data (int tas, int students){
        tasSleeping = tas;
        studentLater = students;
    }

    public static int countRunning() { // Made static
        int count = 0;
        for (Thread thread : Thread.getAllStackTraces().keySet())
            if (thread.getName().startsWith("Student#") && thread instanceof Student)
                count++;
        return count;
    }
    
    public void terminate() {
        running = false;
        System.out.println(getName() + " is terminated.");
    }

    private void randomWait(int minSeconds, int maxSeconds) {
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(minSeconds, maxSeconds + 1) * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
