package ui;

import service.StudentService;
import model.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class ViewStudentsPanel extends JPanel {
    private StudentService service;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private TableRowSorter<DefaultTableModel> rowSorter;
    private MainFrame parentFrame;

    public ViewStudentsPanel(StudentService service, MainFrame parentFrame) {
        this.service = service;
        this.parentFrame = parentFrame;
        setLayout(new BorderLayout(0, 16));
        setBackground(UIUtils.COLOR_BACKGROUND);
        setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        initUI();
    }

    private void initUI() {
        // Title
        JLabel title = UIUtils.createLabel("All Students", UIUtils.FONT_H1, UIUtils.COLOR_TEXT_PRIMARY);
        
        // Search Bar
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        searchPanel.setBackground(UIUtils.COLOR_BACKGROUND);
        searchField = UIUtils.createTextField();
        searchField.setPreferredSize(new Dimension(300, 36));
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = searchField.getText();
                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });
        searchPanel.add(new JLabel("Search: "));
        searchPanel.add(searchField);

        JButton deleteBtn = UIUtils.createSecondaryButton("Delete Selected");
        deleteBtn.setForeground(UIUtils.COLOR_DANGER);
        deleteBtn.addActionListener(e -> deleteSelected());
        searchPanel.add(deleteBtn);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(UIUtils.COLOR_BACKGROUND);
        topPanel.add(title, BorderLayout.NORTH);
        topPanel.add(searchPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);

        // Table
        String[] columns = {"ID", "Name", "Age", "Course", "Marks", "Grade"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setFont(UIUtils.FONT_BODY);
        table.setRowHeight(30);
        table.getTableHeader().setFont(UIUtils.FONT_H2);
        table.getTableHeader().setForeground(UIUtils.COLOR_TEXT_PRIMARY);
        table.setGridColor(UIUtils.COLOR_BORDER);
        table.setShowVerticalLines(false);

        rowSorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(rowSorter);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(UIUtils.COLOR_BORDER));
        scrollPane.getViewport().setBackground(UIUtils.COLOR_CARD_BG);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void refreshTable() {
        tableModel.setRowCount(0);
        List<Student> students = service.getAllStudents();
        for (Student s : students) {
            Object[] row = {
                s.getStudentId(),
                s.getName(),
                s.getAge(),
                s.getCourse(),
                s.getMarks(),
                s.calculateGrade()
            };
            tableModel.addRow(row);
        }
    }

    private void deleteSelected() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            // Need to map the view row back to model row in case of active sorting/filtering
            int modelRow = table.convertRowIndexToModel(selectedRow);
            String id = (String) tableModel.getValueAt(modelRow, 0);
            
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete student " + id + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                service.deleteStudent(id);
                refreshTable();
                parentFrame.refreshDashboard(); // Tell parent to update dashboard stats
                JOptionPane.showMessageDialog(this, "Student deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a student to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }
}
