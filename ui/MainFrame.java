package ui;

import service.StudentService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainFrame extends JFrame {
    private StudentService service;
    private JPanel cards;
    private CardLayout cardLayout;

    private DashboardPanel dashboardPanel;
    private AddStudentPanel addStudentPanel;
    private ViewStudentsPanel viewStudentsPanel;
    private UpdateMarksPanel updateMarksPanel;

    private JPanel lastActiveNav = null;

    public MainFrame() {
        service = new StudentService();

        setTitle("Student Management System");
        setSize(1100, 700);
        setMinimumSize(new Dimension(900, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        initHeader();
        initSidebar();
        initContent();
        initFooter();

        // Initial refresh
        refreshDashboard();
    }

    private void initHeader() {
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 16));
        header.setBackground(UIUtils.COLOR_PRIMARY);
        header.setPreferredSize(new Dimension(getWidth(), 60));

        JLabel title = UIUtils.createLabel("Student Management System", UIUtils.FONT_H1, Color.WHITE);
        header.add(title);

        add(header, BorderLayout.NORTH);
    }

    private void initSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(UIUtils.COLOR_CARD_BG);
        sidebar.setPreferredSize(new Dimension(220, getHeight()));
        sidebar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, UIUtils.COLOR_BORDER));

        sidebar.add(Box.createRigidArea(new Dimension(0, 16)));

        JPanel dashNav = createNavItem("  Dashboard", "dashboard");
        JPanel addNav = createNavItem("  Add Student", "addStudent");
        JPanel viewNav = createNavItem("  View/Search", "viewStudents");
        JPanel updateNav = createNavItem("  Update Marks", "updateMarks");

        sidebar.add(dashNav);
        sidebar.add(addNav);
        sidebar.add(viewNav);
        sidebar.add(updateNav);

        // Set Dashboard active initially
        setActiveNav(dashNav);

        add(sidebar, BorderLayout.WEST);
    }

    private JPanel createNavItem(String text, String cardName) {
        JPanel item = new JPanel(new BorderLayout());
        item.setMaximumSize(new Dimension(220, 48));
        item.setBackground(UIUtils.COLOR_CARD_BG);
        item.setBorder(new EmptyBorder(0, 16, 0, 0));

        JLabel lbl = UIUtils.createLabel(text, UIUtils.FONT_BODY, UIUtils.COLOR_TEXT_PRIMARY);
        item.add(lbl, BorderLayout.CENTER);

        item.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(cards, cardName);
                if (cardName.equals("viewStudents")) {
                    viewStudentsPanel.refreshTable();
                } else if (cardName.equals("dashboard")) {
                    dashboardPanel.refreshStats();
                }
                setActiveNav(item);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (item != lastActiveNav) {
                    item.setBackground(new Color(240, 248, 255)); // Hover color
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (item != lastActiveNav) {
                    item.setBackground(UIUtils.COLOR_CARD_BG);
                }
            }
        });

        return item;
    }

    private void setActiveNav(JPanel navItem) {
        if (lastActiveNav != null) {
            lastActiveNav.setBackground(UIUtils.COLOR_CARD_BG);
            lastActiveNav.setBorder(new EmptyBorder(0, 16, 0, 0));
        }
        navItem.setBackground(new Color(235, 245, 251)); // Active light blue
        navItem.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 4, 0, 0, UIUtils.COLOR_PRIMARY),
                new EmptyBorder(0, 12, 0, 0)
        ));
        lastActiveNav = navItem;
    }

    private void initContent() {
        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);

        dashboardPanel = new DashboardPanel(service);
        addStudentPanel = new AddStudentPanel(service, this);
        viewStudentsPanel = new ViewStudentsPanel(service, this);
        updateMarksPanel = new UpdateMarksPanel(service, this);

        cards.add(dashboardPanel, "dashboard");
        cards.add(addStudentPanel, "addStudent");
        cards.add(viewStudentsPanel, "viewStudents");
        cards.add(updateMarksPanel, "updateMarks");

        add(cards, BorderLayout.CENTER);
    }

    private void initFooter() {
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footer.setBackground(UIUtils.COLOR_BACKGROUND);
        footer.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, UIUtils.COLOR_BORDER));
        footer.setPreferredSize(new Dimension(getWidth(), 30));

        footer.add(UIUtils.createLabel("Powered by Antigravity™", new Font("Segoe UI", Font.ITALIC, 12), UIUtils.COLOR_TEXT_SECONDARY));
        add(footer, BorderLayout.SOUTH);
    }

    public void refreshDashboard() {
        dashboardPanel.refreshStats();
    }
}
