package ui;

import service.StudentService;
import model.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DashboardPanel extends JPanel {
    private StudentService service;
    
    private JLabel totalStudentsLbl;
    private JLabel avgMarksLbl;
    private JLabel topPerformerLbl;
    private DefaultTableModel recentTableModel;

    public DashboardPanel(StudentService service) {
        this.service = service;
        setLayout(new BorderLayout(0, 24));
        setBackground(UIUtils.COLOR_BACKGROUND);
        setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        initUI();
    }

    private void initUI() {
        JLabel title = UIUtils.createLabel("Dashboard", UIUtils.FONT_H1, UIUtils.COLOR_TEXT_PRIMARY);
        add(title, BorderLayout.NORTH);

        // Stats Row using GridLayout
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 16, 0));
        statsPanel.setBackground(UIUtils.COLOR_BACKGROUND);

        totalStudentsLbl = UIUtils.createLabel("0", UIUtils.FONT_H1, UIUtils.COLOR_PRIMARY);
        avgMarksLbl = UIUtils.createLabel("0.0", UIUtils.FONT_H1, UIUtils.COLOR_PRIMARY);
        topPerformerLbl = UIUtils.createLabel("None", UIUtils.FONT_H2, UIUtils.COLOR_PRIMARY);

        statsPanel.add(createStatCard("Total Students", totalStudentsLbl));
        statsPanel.add(createStatCard("Average Marks", avgMarksLbl));
        statsPanel.add(createStatCard("Top Performer", topPerformerLbl));

        // Recent Students Table
        JPanel recentPanel = new JPanel(new BorderLayout(0, 8));
        recentPanel.setBackground(UIUtils.COLOR_BACKGROUND);
        recentPanel.add(UIUtils.createLabel("Recent Students", UIUtils.FONT_H2, UIUtils.COLOR_TEXT_PRIMARY), BorderLayout.NORTH);

        String[] columns = {"ID", "Name", "Course", "Marks", "Grade"};
        recentTableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(recentTableModel);
        table.setFont(UIUtils.FONT_BODY);
        table.setRowHeight(30);
        table.getTableHeader().setFont(UIUtils.FONT_BODY);
        table.getTableHeader().setForeground(UIUtils.COLOR_TEXT_PRIMARY);
        table.setEnabled(false); // Read-only dashboard table

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(UIUtils.COLOR_BORDER));
        recentPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel centerContainer = new JPanel(new BorderLayout(0, 24));
        centerContainer.setBackground(UIUtils.COLOR_BACKGROUND);
        centerContainer.add(statsPanel, BorderLayout.NORTH);
        centerContainer.add(recentPanel, BorderLayout.CENTER);

        add(centerContainer, BorderLayout.CENTER);
    }

    private JPanel createStatCard(String title, JLabel valueLabel) {
        JPanel card = UIUtils.createCardPanel();
        card.setLayout(new BorderLayout(0, 8));
        card.add(UIUtils.createLabel(title, UIUtils.FONT_BODY, UIUtils.COLOR_TEXT_SECONDARY), BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        return card;
    }

    public void refreshStats() {
        // CORE KEYWORD: 'static' method call on StudentService class to get total students directly.
        totalStudentsLbl.setText(String.valueOf(StudentService.getTotalStudents()));
        avgMarksLbl.setText(String.format("%.1f", service.getAverageMarks()));
        
        Student top = service.getTopPerformer();
        if (top != null) {
            topPerformerLbl.setText(top.getName() + " (" + top.calculateGrade() + ")");
        } else {
            topPerformerLbl.setText("None");
        }

        // Update recent students (last 5)
        recentTableModel.setRowCount(0);
        List<Student> all = service.getAllStudents();
        int start = Math.max(0, all.size() - 5);
        for (int i = all.size() - 1; i >= start; i--) {
            Student s = all.get(i);
            recentTableModel.addRow(new Object[]{s.getStudentId(), s.getName(), s.getCourse(), s.getMarks(), s.calculateGrade()});
        }
    }
}
