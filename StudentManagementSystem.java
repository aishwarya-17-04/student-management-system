import ui.MainFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class StudentManagementSystem {
    public static void main(String[] args) {
        // Run UI creation on the Event Dispatch Thread (EDT) for thread safety
        SwingUtilities.invokeLater(() -> {
            try {
                // Set system look and feel for native window decorations
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
