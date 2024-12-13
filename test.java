/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tast;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Ahmed Shaaban
 */
public class test {
    static final JTextField tasWorkingOutput = new JTextField();
    static final JTextField tasSleepingOutput = new JTextField();
    static final JTextField studentsWaitingOutput = new JTextField();
    static final JTextField studentsLaterOutput = new JTextField();
    
    
    public test() {
        
        final JFrame frame = new JFrame("Sleeping TA");
        frame.setDefaultCloseOperation(3);
        frame.setSize(500, 350);
        frame.setLayout(new BorderLayout(10, 10));
        int marginSize = 10;
        JPanel inputPanel = new JPanel();
        inputPanel.setBorder(BorderFactory.createEmptyBorder(marginSize, marginSize, marginSize, marginSize));
        inputPanel.setLayout(new GridLayout(3, 2, 10, 10));
        JLabel taLabel = new JLabel("#TAs");
        final JTextField taInput = new JTextField();
        JLabel chairsLabel = new JLabel("#Chairs");
        final JTextField chairsInput = new JTextField();
        JLabel studentsLabel = new JLabel("#Students");
        final JTextField studentsInput = new JTextField();
        taInput.setColumns(5);
        chairsInput.setColumns(5);
        studentsInput.setColumns(5);
        inputPanel.add(taLabel);
        inputPanel.add(taInput);
        inputPanel.add(chairsLabel);
        inputPanel.add(chairsInput);
        inputPanel.add(studentsLabel);
        inputPanel.add(studentsInput);
        
        JPanel outputPanel = new JPanel();
        outputPanel.setBorder(BorderFactory.createEmptyBorder(marginSize, marginSize, marginSize, marginSize));
        outputPanel.setLayout(new GridLayout(4, 2, 10, 10));
        JLabel tasWorkingLabel = new JLabel("#TAs working");
        tasWorkingLabel.setHorizontalAlignment(4);
        tasWorkingLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        tasWorkingOutput.setEditable(false);
        JLabel tasSleepingLabel = new JLabel("#TAs Sleeping");
        tasSleepingLabel.setHorizontalAlignment(4);
        tasSleepingLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        tasSleepingOutput.setEditable(false);
        JLabel studentsWaitingLabel = new JLabel("#Students Waiting on chair");
        studentsWaitingLabel.setHorizontalAlignment(4);
        studentsWaitingLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        studentsWaitingOutput.setEditable(false);
        JLabel studentsLaterLabel = new JLabel("#Students will come later");
        studentsLaterLabel.setHorizontalAlignment(4);
        studentsLaterLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        studentsLaterOutput.setEditable(false);
        tasWorkingOutput.setColumns(10);
        tasSleepingOutput.setColumns(10);
        studentsWaitingOutput.setColumns(10);
        studentsLaterOutput.setColumns(10);
        outputPanel.add(tasWorkingLabel);
        outputPanel.add(tasWorkingOutput);
        outputPanel.add(tasSleepingLabel);
        outputPanel.add(tasSleepingOutput);
        outputPanel.add(studentsWaitingLabel);
        outputPanel.add(studentsWaitingOutput);
        outputPanel.add(studentsLaterLabel);
        outputPanel.add(studentsLaterOutput);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(marginSize, marginSize, marginSize, marginSize));
        JButton calculateButton = new JButton("Calculate");
        buttonPanel.add(calculateButton);
        calculateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int numTAs = Integer.parseInt(taInput.getText());
                    int numChairs = Integer.parseInt(chairsInput.getText());
                    int numStudents = Integer.parseInt(studentsInput.getText());
                    if (numTAs <= 0 || numChairs <= 0 || numStudents <= 0) {
                        JOptionPane.showMessageDialog(frame, "Only positive integers allowed", "Input Error", 0);
                        return;
                    }
                    
                    Simulation.takeUserInput(numStudents,numChairs,numTAs);
                    Simulation.createStudentThreads();
                    
                    tasWorkingOutput.setText("0");
                    tasSleepingOutput.setText(String.valueOf(numTAs));
                    studentsWaitingOutput.setText("0");
                    studentsLaterOutput.setText(String.valueOf(numStudents));
                } catch (NumberFormatException var9) {
                    JOptionPane.showMessageDialog(frame, "Only integers allowed", "Input Error", 0);
                }

            }
        });
        frame.add(inputPanel, "West");
        frame.add(outputPanel, "East");
        frame.add(buttonPanel, "South");
        frame.setVisible(true);
    }
    public static void tasWorkingOutput(int tasWorking){
        tasWorkingOutput.setText(String.valueOf(tasWorking));
    }
    public static void tasSleepingOutput(int tasSleeping){
        tasSleepingOutput.setText(String.valueOf(tasSleeping));
    }
    public static void studentsWaitingOutput(int studentsWaiting){
        studentsWaitingOutput.setText(String.valueOf(studentsWaiting));
    }
    public static void studentsLaterOutput(int studentsLater){
        studentsLaterOutput.setText(String.valueOf(studentsLater));
    }
}
