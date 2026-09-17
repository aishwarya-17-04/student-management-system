package ui;

import service.StudentService;
import model.Student;
import exception.StudentNotFoundException;
import exception.InvalidMarksException;

import javax.swing.*;
import java.awt.*;

public class UpdateMarksPanel extends JPanel {
    private StudentService service;
    private MainFrame parentFrame;

    private JTextField searchIdField;
    private JPanel resultCard;
    private JLabel infoLabel;
    private JTextField newMarksField;

    private String currentStudentId = null;

    public UpdateMarksPanel(StudentService service, MainFrame parentFrame) {
        this.service = service;
        this.parentFrame = parentFrame;
        setLayout(new BorderLayout(0, 24));
        setBackground(UIUtils.COLOR_BACKGROUND);
        setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        initUI();
    }

    private void initUI() {
        JLabel title = UIUtils.createLabel("Update Student Marks", UIUtils.FONT_H1, UIUtils.COLOR_TEXT_PRIMARY);
        
        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        searchPanel.setBackground(UIUtils.COLOR_BACKGROUND);
        searchIdField = UIUtils.createTextField();
        searchIdField.setPreferredSize(new Dimension(200, 36));
        JButton findBtn = UIUtils.createPrimaryButton("Find");
        findBtn.addActionListener(e -> findStudent());
        
        searchPanel.add(new JLabel("Enter Student ID:"));
        searchPanel.add(searchIdField);
        searchPanel.add(findBtn);

        JPanel topPanel = new JPanel(new BorderLayout(0, 16));
        topPanel.setBackground(UIUtils.COLOR_BACKGROUND);
        topPanel.add(title, BorderLayout.NORTH);
        topPanel.add(searchPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);

        // Result Card
        resultCard = UIUtils.createCardPanel();
        resultCard.setLayout(new GridBagLayout());
        resultCard.setVisible(false); // Hidden initially

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        infoLabel = UIUtils.createLabel("", UIUtils.FONT_BODY, UIUtils.COLOR_TEXT_PRIMARY);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        resultCard.add(infoLabel, gbc);

        gbc.gridy = 1; gbc.gridwidth = 1; gbc.weightx = 0.3;
        resultCard.add(UIUtils.createLabel("New Marks:", UIUtils.FONT_BODY, UIUtils.COLOR_TEXT_SECONDARY), gbc);

        newMarksField = UIUtils.createTextField();
        gbc.gridx = 1; gbc.weightx = 0.7;
        resultCard.add(newMarksField, gbc);

        JButton updateBtn = UIUtils.createPrimaryButton("Update");
        updateBtn.addActionListener(e -> updateMarks());
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        resultCard.add(updateBtn, gbc);

        JPanel centerWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
        centerWrapper.setBackground(UIUtils.COLOR_BACKGROUND);
        resultCard.setPreferredSize(new Dimension(400, 200));
        centerWrapper.add(resultCard);

        add(centerWrapper, BorderLayout.CENTER);
    }

    private void findStudent() {
        String id = searchIdField.getText().trim();
        // MODULE 3: EXCEPTION HANDLING - Gracefully handling lookup failures
        try {
            Student s = service.findStudentById(id);
            currentStudentId = s.getStudentId();
            infoLabel.setText("<html><b>Name:</b> " + s.getName() + " &nbsp;&nbsp; <b>Course:</b> " + s.getCourse() + "<br><b>Current Marks:</b> " + s.getMarks() + " (" + s.calculateGrade() + ")</html>");
            newMarksField.setText("");
            resultCard.setVisible(true);
        } catch (StudentNotFoundException ex) {
            resultCard.setVisible(false);
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Not Found", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateMarks() {
        try {
            double marks = Double.parseDouble(newMarksField.getText().trim());
            if (currentStudentId != null) {
                // MODULE 3: EXCEPTION HANDLING - using try-catch-finally
                try {
                    service.updateStudentMarks(currentStudentId, marks);
                    JOptionPane.showMessageDialog(this, "Marks updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    parentFrame.refreshDashboard();
                    resultCard.setVisible(false);
                    searchIdField.setText("");
                    currentStudentId = null;
                } catch (StudentNotFoundException | InvalidMarksException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                } finally {
                    // Just to demonstrate finally block usage for the viva
                    System.out.println("Update operation attempted on ID: " + currentStudentId);
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid marks. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
