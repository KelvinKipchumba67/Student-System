public class Main {
    public static void main(String[] args) {
        // Starts the application at login view
        java.awt.EventQueue.invokeLater(() -> {
            new com.system.view.LoginView().setVisible(true);
        });
    }
}