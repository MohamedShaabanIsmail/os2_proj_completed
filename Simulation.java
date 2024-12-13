/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tast;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

/**
 *
 * @author Ahmed Shaaban
 */
public class Simulation {
     /// Field declarations
    private static int numChairs, numStudents, numTAs;
    private static Semaphore Chairs, TAs; // Corrected variable name
    private static ArrayList<Student> students = new ArrayList<>(); // Correctly initialized

    /**
     * Console method to take inputs for testing purposes.
     */
    protected static void takeUserInput() {
        Scanner input = new Scanner(System.in);

        System.out.print("Number of Students: ");
        numStudents = input.nextInt();

        System.out.print("Number of chairs: ");
        numChairs = input.nextInt();

        System.out.print("Number of TAs: ");
        numTAs = input.nextInt();

        input.close();

        Chairs = new Semaphore(numChairs); // Corrected reference
        TAs = new Semaphore(numTAs);       // Corrected reference
    }

    /**
     * Takes user input and initializes the number of students, chairs, and TAs.
     * GUI Method
     *
     * @param students Number of students
     * @param chairs   Number of chairs
     * @param tas      Number of TAs
     */
    public static void takeUserInput(int students, int chairs, int tas) {
        numStudents = students;
        numChairs = chairs;
        numTAs = tas;

        Chairs = new Semaphore(numChairs); // Corrected reference
        TAs = new Semaphore(numTAs);       // Corrected reference
    }

    /**
     * Create student threads.
     */
    public static void createStudentThreads() {
        createStudentThreads(0);
    }

    public static void createStudentThreads(int taWaitInterval) {
        Student.data(numTAs,numStudents);
        for (int i = 1; i <= numStudents; i++) {
            Student student = new Student(i, taWaitInterval);
            student.start();
            //students.add(student); // Corrected reference
        }
    }

    /**
     * Get system information for display.
     *
     * @param display If true, display the information.
     * @return Map of system details.
     */
    public static Map<String, Integer> getInfo(boolean display) {
        int sleepingTA, waitingStudents, workingTA, laterStudents, classFinished;

        sleepingTA = TAs.availablePermits();
        waitingStudents = numChairs - Chairs.availablePermits(); // Corrected reference
        workingTA = numTAs - TAs.availablePermits();
        laterStudents = countRunningStudents() - waitingStudents - workingTA;
        classFinished = !isStudentThreadRunning() ? 1 : 0;

        if (display) {
            displayInfo(sleepingTA, waitingStudents, workingTA, laterStudents);
        }

        return Map.of(
                "sleepingTA", sleepingTA,
                "workingTA", workingTA,
                "waitingStudents", waitingStudents,
                "laterStudents", laterStudents,
                "classFinished", classFinished);
    }

    private static void displayInfo(int sleep, int waiting, int working, int later) {
        System.out.println("TAs working: " + working);
        System.out.println("TAs sleeping: " + sleep);
        System.out.println("Students waiting on chairs: " + waiting);
        System.out.println("Students that will come later: " + later);
    }

    public static void terminateStudentThreads() {
        for (Thread thread : Thread.getAllStackTraces().keySet())
            if (thread.getName().startsWith("Student#") && thread instanceof Student)
                ((Student) thread).terminate();
    }

    public static void resetSemaphores() {
        Chairs.drainPermits(); // Corrected reference
        Chairs.release(numChairs); // Corrected reference
        TAs.drainPermits();
        TAs.release(numTAs);
    }

    public static int countRunningStudents() {
        return Student.countRunning(); // Ensure Student has the countRunning method implemented
    }

    public static boolean isStudentThreadRunning() {
        for (Student student : students)
            if (student.isAlive())
                return true;
        return false;
    }

    public static Semaphore getChairs() {
        return Chairs; // Corrected reference
    }

    public static Semaphore getTAs() {
        return TAs; // Corrected reference
    }
    
}
