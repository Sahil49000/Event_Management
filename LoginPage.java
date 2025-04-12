import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage extends JFrame implements ActionListener {

    // Components for the login page
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JRadioButton adminRadioButton;
    private JRadioButton clientRadioButton;
    private ButtonGroup roleGroup;

    public LoginPage() {
        setLayout(null); // Use absolute positioning (null layout)

        // Set up the background image
        ImageIcon backgroundImage = new ImageIcon(ClassLoader.getSystemResource("assets/login.jpg"));
        JLabel backgroundLabel = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        backgroundLabel.setBounds(0, 0, 1600, 1000); // Set background size to the full frame
        add(backgroundLabel);

        

        // Create a panel to hold the login form, and center it with a semi-transparent background
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(null);
        loginPanel.setBackground(new Color(0, 0, 0, 150)); // Semi-transparent background with 60% opacity
        loginPanel.setBounds(0, 0, getWidth(), getHeight()); // Set bounds to position the panel in the top portion
        backgroundLabel.add(loginPanel);

        // Title label
        JLabel titleLabel = new JLabel("Event Management System");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 36));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setBounds(660, 50, 600, 50); // Position the title at the top
        backgroundLabel.add(titleLabel);

        // Username field
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(410, 180, 100, 30);  // Move it to the top
        usernameLabel.setForeground(Color.WHITE);
        backgroundLabel.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(150, 30, 200, 30);  // Position below the username label
        backgroundLabel.add(usernameField);

        // Password field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 80, 100, 30);  // Move it closer to the top
        passwordLabel.setForeground(Color.WHITE);
        backgroundLabel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(150, 80, 200, 30);  // Position below the password label
        backgroundLabel.add(passwordField);

        // Radio buttons for selecting role (Administrator or Client)
        JLabel roleLabel = new JLabel("Select Role:");
        roleLabel.setBounds(50, 120, 100, 30);
        roleLabel.setForeground(Color.WHITE);
        backgroundLabel.add(roleLabel);

        adminRadioButton = new JRadioButton("Administrator");
        adminRadioButton.setBounds(150, 120, 150, 30);
        adminRadioButton.setForeground(Color.WHITE);
        backgroundLabel.add(adminRadioButton);

        clientRadioButton = new JRadioButton("Client");
        clientRadioButton.setBounds(150, 150, 150, 30);
        clientRadioButton.setForeground(Color.WHITE);
        backgroundLabel.add(clientRadioButton);

        // Group the radio buttons so only one can be selected
        roleGroup = new ButtonGroup();
        roleGroup.add(adminRadioButton);
        roleGroup.add(clientRadioButton);

        // Login button
        loginButton = new JButton("Login");
        loginButton.setBounds(150, 200, 200, 40);  // Position the login button below the role selection
        loginButton.setBackground(new Color(50, 150, 250));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Tahoma", Font.BOLD, 20));
        loginButton.addActionListener(this);
        backgroundLabel.add(loginButton);

        // Frame settings
        setSize(1920,1080); // Maximize window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        backgroundLabel.setBounds(0, 0, getWidth(), getHeight()); // Make sure the background matches the full window size
        loginPanel.setBounds(300, 100, getWidth() - 600, 600);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // Get selected role from radio buttons
        String selectedRole = "";
        if (adminRadioButton.isSelected()) {
            selectedRole = "Administrator";
        } else if (clientRadioButton.isSelected()) {
            selectedRole = "Client";
        }

        // Here, you can add actual validation logic (e.g., checking username, password,
        // and role)
        if (username.isEmpty() || password.isEmpty() || selectedRole.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Welcome, Administrator!");
                this.dispose(); // Close the login window
                new AdminPage(); } else {
            if (selectedRole.equals("Administrator") && username.equals("admin") && password.equals("admin123")) {
                JOptionPane.showMessageDialog(this, "Welcome, Administrator!");
                // Open Admin panel or dashboard
                // new AdminPanel(); // You can create this panel or frame for the Admin
            } else if (selectedRole.equals("Client") && username.equals("client") && password.equals("client123")) {
                JOptionPane.showMessageDialog(this, "Welcome, Client!");
                // Open Client dashboard
                // new ClientPanel(); // You can create this panel or frame for the Client
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        new LoginPage(); // Run the login page
    }
}
