/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assessor.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SQLyogDatePicker extends JPanel {
    private JTextField dateField;
    private JButton calendarButton;
    private JDialog calendarDialog;
    private JPanel calendarPanel;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private Calendar currentCalendar = Calendar.getInstance();

    public SQLyogDatePicker() {
        createUI();
        createCalendarDialog();
    }

    private void createUI() {
        setLayout(new BorderLayout(0, 0));
        setPreferredSize(new Dimension(120, 20));
        
        dateField = new JTextField(dateFormat.format(new Date()));
        dateField.setEditable(false);
        dateField.setHorizontalAlignment(JTextField.CENTER);
        
        calendarButton = new JButton("▼");
        calendarButton.setMargin(new Insets(0, 2, 0, 2));
        calendarButton.addActionListener(e -> toggleCalendar());
        
        add(dateField, BorderLayout.CENTER);
        add(calendarButton, BorderLayout.EAST);
    }

    private void createCalendarDialog() {
        calendarDialog = new JDialog();
        calendarDialog.setUndecorated(true);
        calendarDialog.setModal(true);
        
        JPanel container = new JPanel(new BorderLayout());
        container.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        // Header with navigation
        JPanel header = new JPanel(new BorderLayout());
        JButton prev = new JButton("◀");
        JButton next = new JButton("▶");
        JLabel monthLabel = new JLabel("", SwingConstants.CENTER);
        
        prev.addActionListener(e -> updateMonth(-1, monthLabel));
        next.addActionListener(e -> updateMonth(1, monthLabel));
        
        header.add(prev, BorderLayout.WEST);
        header.add(monthLabel, BorderLayout.CENTER);
        header.add(next, BorderLayout.EAST);
        
        // Calendar grid
        calendarPanel = new JPanel(new GridLayout(0, 7, 2, 2));
        
        // Control buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
        JButton nowButton = new JButton("Now");
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");
        
        nowButton.addActionListener(e -> setCurrentDate());
        okButton.addActionListener(e -> calendarDialog.setVisible(false));
        cancelButton.addActionListener(e -> {
            calendarDialog.setVisible(false);
            dateField.setText(dateFormat.format(currentCalendar.getTime()));
        });
        
        buttonPanel.add(nowButton);
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        
        container.add(header, BorderLayout.NORTH);
        container.add(calendarPanel, BorderLayout.CENTER);
        container.add(buttonPanel, BorderLayout.SOUTH);
        
        calendarDialog.add(container);
        updateCalendar(monthLabel);
    }

    private void updateMonth(int offset, JLabel monthLabel) {
        currentCalendar.add(Calendar.MONTH, offset);
        updateCalendar(monthLabel);
    }

    private void updateCalendar(JLabel monthLabel) {
        calendarPanel.removeAll();
        monthLabel.setText(new SimpleDateFormat("MMMM yyyy").format(currentCalendar.getTime()));
        
        Calendar temp = (Calendar) currentCalendar.clone();
        temp.set(Calendar.DAY_OF_MONTH, 1);
        
        int startDay = temp.get(Calendar.DAY_OF_WEEK);
        int daysInMonth = temp.getActualMaximum(Calendar.DAY_OF_MONTH);
        
        for(int i = 1; i < startDay; i++) {
            calendarPanel.add(new JLabel(""));
        }
        
        for(int day = 1; day <= daysInMonth; day++) {
            calendarPanel.add(createDayButton(day));
        }
        
        calendarPanel.revalidate();
        calendarPanel.repaint();
        calendarDialog.pack();
    }

    private JButton createDayButton(int day) {
        JButton button = new JButton(String.valueOf(day));
        button.setMargin(new Insets(0, 0, 0, 0));
        
        if(isToday(day)) {
            button.setBackground(new Color(255, 228, 181));
        }
        
        button.addActionListener(e -> {
            currentCalendar.set(Calendar.DAY_OF_MONTH, day);
            dateField.setText(dateFormat.format(currentCalendar.getTime()));
        });
        
        return button;
    }

    private boolean isToday(int day) {
        Calendar today = Calendar.getInstance();
        return currentCalendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
               currentCalendar.get(Calendar.MONTH) == today.get(Calendar.MONTH) &&
               day == today.get(Calendar.DAY_OF_MONTH);
    }

    private void setCurrentDate() {
        currentCalendar = Calendar.getInstance();
        dateField.setText(dateFormat.format(currentCalendar.getTime()));
        updateCalendar((JLabel) ((JPanel) calendarDialog.getContentPane().getComponent(0))
            .getComponent(0));
        calendarDialog.setVisible(false);
    }

    private void toggleCalendar() {
        if(!calendarDialog.isVisible()) {
            Point location = calendarButton.getLocationOnScreen();
            calendarDialog.setLocation(
                location.x - calendarDialog.getWidth() + calendarButton.getWidth(),
                location.y + calendarButton.getHeight()
            );
            calendarDialog.setVisible(true);
        }
    }

    // JavaBean properties
    public String getSelectedDate() {
        return dateField.getText();
    }

    public void setSelectedDate(String date) {
        try {
            currentCalendar.setTime(dateFormat.parse(date));
            dateField.setText(date);
        } catch (Exception e) {
            dateField.setText(dateFormat.format(new Date()));
        }
    }
}