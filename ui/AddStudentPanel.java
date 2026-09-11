package ui;

import service.StudentService;
import model.Student;

import javax.swing.*;
import java.awt.*;

public class AddStudentPanel extends JPanel {
    private StudentService service;
    private MainFrame parentFrame;

    private JTextField idField, nameField, courseField, marksField;
    private JSpinner ageSpinner;

    public AddStudentPanel(StudentService service, MainFrame parentFrame) {
        this.service = service;
        this.parentFrame = parentFrame;
        setLayout(new BorderLayout(0, 24));
        setBackground(UIUtils.COLOR_BACKGROUND);
        setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        initUI();
    }

    private void initUI() {
        JLabel title = UIUtils.createLabel("Add New Student", UIUtils.FONT_H1, UIUtils.COLOR_TEXT_PRIMARY);
        add(title, BorderLayout.NORTH);

        JPanel card = UIUtils.createCardPanel();
        card.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        idField = UIUtils.createTextField();
        nameField = UIUtils.createTextField();
        courseField = UIUtils.createTextField();
        marksField = UIUtils.createTextField();
        ageSpinner = new JSpinner(new SpinnerNumberModel(18, 10, 100, 1));
        ageSpinner.setFont(UIUtils.FONT_BODY);

        int row = 0;
        addFormField(card, gbc, "Student ID *", idField, row++);
        addFormField(card, gbc, "Full Name *", nameField, row++);
        addFormField(card, gbc, "Age *", ageSpinner, row++);
        addFormField(card, gbc, "Course *", courseField, row++);
        addFormField(card, gbc, "Marks *", marksField, row++);

        JButton addBtn = UIUtils.createPrimaryButton("Add Student");
        JButton clearBtn = UIUtils.createSecondaryButton("Clear Form");

        addBtn.addActionListener(e -> addStudent());
        clearBtn.addActionListener(e -> clearForm());

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 0));
        btnPanel.setBackground(UIUtils.COLOR_CARD_BG);
        btnPanel.add(addBtn);
        btnPanel.add(clearBtn);

        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        card.add(btnPanel, gbc);

        // Center the card in the panel
        JPanel centerWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerWrapper.setBackground(UIUtils.COLOR_BACKGROUND);
        card.setPreferredSize(new Dimension(500, 400));
        centerWrapper.add(card);

        add(centerWrapper, BorderLayout.CENTER);
    }

    private void addFormField(JPanel card, GridBagConstraints gbc, String label, JComponent field, int row) {
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 1; gbc.weightx = 0.3;
        JLabel lbl = UIUtils.createLabel(label, UIUtils.FONT_BODY, UIUtils.COLOR_TEXT_SECONDARY);
        lbl.setHorizontalAlignment(SwingConstants.RIGHT);
        card.add(lbl, gbc);

        gbc.gridx = 1; gbc.weightx = 0.7;
        card.add(field, gbc);
    }

    private void addStudent() {
        try {
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            int age = (int) ageSpinner.getValue();
            String course = courseField.getText().trim();
            double marks = Double.parseDouble(marksField.getText().trim());

            if (id.isEmpty() || name.isEmpty() || course.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all required fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Student s = new Student(id, name, age, course, marks);
            if (service.addStudent(s)) {
                JOptionPane.showMessageDialog(this, "Student Added Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
                parentFrame.refreshDashboard(); // Refresh dash on addition
            } else {
                JOptionPane.showMessageDialog(this, "Student ID already exists.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid marks. Please enter a valid numeric value.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        idField.setText("");
        nameField.setText("");
        courseField.setText("");
        marksField.setText("");
        ageSpinner.setValue(18);
    }
}
