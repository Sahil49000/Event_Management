import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.*;

public class AddEvent extends JFrame implements ActionListener {
    // Declare components for the AddEvent page
    private JTextField customerNameField, contactNumberField, eventDateField, eventTimeField, expectedPeopleField;
    private JComboBox<String> eventVenueComboBox, eventThemeComboBox, extraProgramsComboBox, eventTypeComboBox,
            foodTypeComboBox, paymentMethodComboBox, dressCodeComboBox, eventStatusComboBox, cuisineTypeComboBox,
            transportationForEventComboBox, transportationForGuestsComboBox, accommodationForGuestsComboBox;
    private JButton submitButton;
    private BufferedImage backgroundImage;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/EventManagement";
    private static final String USER = "root";  // Replace with your database username
    private static final String PASS = "admin";  // Replace with your database password


    public AddEvent() {
        // Set layout manager to BorderLayout
        setLayout(new BorderLayout());

        // Load and blur the background image
        backgroundImage = loadImage("/assets/login.jpg");
        if (backgroundImage == null) {
            System.out.println("Background image not found. Make sure the path is correct.");
        }

        // Apply stronger blur effect to the background image
        backgroundImage = applyGaussianBlur(backgroundImage);

        // Create a background panel and paint the image
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        // Set size of background panel and add it to the frame
        backgroundPanel.setLayout(new BorderLayout());
        setContentPane(backgroundPanel); // Add the background panel as the content pane

        // Add a transparent panel between the background and the content
        JPanel transparentPanel = new JPanel();
        transparentPanel.setOpaque(false); // Make this panel transparent

        // Add a semi-transparent panel to hold the buttons (for a blur-like effect)
        JPanel semiPanel = new JPanel();
        semiPanel.setLayout(new BorderLayout()); // Use BorderLayout to divide the area
        semiPanel.setOpaque(true); // Make this panel semi-transparent
        semiPanel.setBackground(new Color(0, 0, 0, 150)); // 60% opacity black

        // Full page size for the semi-transparent panel
        semiPanel.setBounds(0, 0, getWidth(), getHeight()); // Fullscreen panel

        // Title label
        JLabel titleLabel = new JLabel("  Add New Event  ");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 60)); // Increased font size
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the title label

        // Title background (optional: semi-transparent dark background behind title)
        JPanel titleBackground = new JPanel();
        titleBackground.setOpaque(true);
        titleBackground.setBackground(new Color(0, 0, 0, 120)); // Semi-transparent black background
        titleBackground.setLayout(new BorderLayout());
        titleBackground.add(titleLabel, BorderLayout.CENTER);

        // Add the title background to the semiPanel (above the form)
        semiPanel.add(titleBackground, BorderLayout.NORTH);

        // Space between title and form
        semiPanel.add(Box.createVerticalStrut(30), BorderLayout.NORTH);

        // Create a JPanel for form components (to add margins and scrolling)
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout()); // Use GridBagLayout for column-wise form layout
        formPanel.setOpaque(false); // Make sure the form panel is transparent to show the background

        // Create GridBagConstraints for component positioning
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add space between components
        gbc.anchor = GridBagConstraints.WEST;

        // Add components with labels and fields in two columns
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(createLabeledComponent("Customer Name:", customerNameField = new JTextField(20)), gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        formPanel.add(createLabeledComponent("Contact Number:", contactNumberField = new JTextField(20)), gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(createLabeledComponent("Event Date (yyyy-MM-dd):", eventDateField = new JTextField(20)), gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        formPanel.add(createLabeledComponent("Event Time (HH:mm:ss):", eventTimeField = new JTextField(20)), gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(createLabeledComponent("Expected People:", expectedPeopleField = new JTextField(20)), gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        formPanel.add(createLabeledComboBox("Event Venue:", eventVenueComboBox = new JComboBox<>(new String[] { "Wedding", "Corporate", "Birthday", "Conference", "Party", "Other" })), gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(createLabeledComboBox("Event Theme:", eventThemeComboBox = new JComboBox<>(new String[] { "Wedding", "Corporate", "Birthday", "Conference", "Party", "Other" })), gbc);

        gbc.gridx = 1; gbc.gridy = 3;
        formPanel.add(createLabeledComboBox("Extra Programs:", extraProgramsComboBox = new JComboBox<>(new String[] { "Live Band", "DJ", "Photo Booth", "Games", "None" })), gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(createLabeledComboBox("Event Type (Genre):", eventTypeComboBox = new JComboBox<>(new String[] { "Wedding", "Corporate", "Birthday", "Conference", "Party", "Other" })), gbc);

        gbc.gridx = 1; gbc.gridy = 4;
        formPanel.add(createLabeledComboBox("Dress Code:", dressCodeComboBox = new JComboBox<>(new String[] { "Formal", "Casual", "Themed" })), gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(createLabeledComboBox("Food Type:", foodTypeComboBox = new JComboBox<>(new String[] { "Veg", "Non-Veg", "Both" })), gbc);

        gbc.gridx = 1; gbc.gridy = 5;
        formPanel.add(createLabeledComboBox("Cuisine Type:", cuisineTypeComboBox = new JComboBox<>(new String[] { "Indian", "Italian", "Chinese", "Mexican", "Other" })), gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        formPanel.add(createLabeledComboBox("Transportation for Event:", transportationForEventComboBox = new JComboBox<>(new String[] { "Bus", "Car", "Van", "None" })), gbc);

        gbc.gridx = 1; gbc.gridy = 6;
        formPanel.add(createLabeledComboBox("Transportation for Guests:", transportationForGuestsComboBox = new JComboBox<>(new String[] { "Bus", "Car", "Van", "None" })), gbc);

        gbc.gridx = 0; gbc.gridy = 7;
        formPanel.add(createLabeledComboBox("Accommodation for Guests:", accommodationForGuestsComboBox = new JComboBox<>(new String[] { "Hotel", "Guest House", "None" })), gbc);

        gbc.gridx = 1; gbc.gridy = 7;
        formPanel.add(createLabeledComboBox("Payment Method:", paymentMethodComboBox = new JComboBox<>(new String[] { "Credit Card", "Cash", "Bank Transfer" })), gbc);

        gbc.gridx = 0; gbc.gridy = 8;
        formPanel.add(createLabeledComboBox("Event Status:", eventStatusComboBox = new JComboBox<>(new String[] { "Scheduled", "Completed", "Cancelled" })), gbc);

        // Submit button
        gbc.gridx = 0; gbc.gridy = 9; gbc.gridwidth = 2;
        submitButton = new JButton("Submit Event");
        styleButton(submitButton);
        formPanel.add(submitButton, gbc);

        // Wrap the formPanel inside JScrollPane for scrolling functionality
        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // Always show vertical scrollbar
        scrollPane.setOpaque(false); // Make the scroll pane transparent
        scrollPane.getViewport().setOpaque(false); // Make the viewport transparent

        // Add the scrollable content to the semi-transparent panel
        semiPanel.add(scrollPane, BorderLayout.CENTER);

        // Add the semi-transparent panel to the background panel
        backgroundPanel.add(semiPanel, BorderLayout.CENTER);

        // Frame settings
        setSize(1920, 1080); // Set the frame size to 1920x1080
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize window
        setVisible(true);  // Make the window visible
    }

    // Helper method to create a labeled component with margins
    private JPanel createLabeledComponent(String labelText, JTextField textField) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.setOpaque(false); // Make the panel transparent
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Tahoma", Font.PLAIN, 20));
        label.setForeground(Color.WHITE);
        panel.add(label);
        panel.add(textField);
        return panel;
    }

    // Helper method to create a labeled combo box with margins
    private JPanel createLabeledComboBox(String labelText, JComboBox<String> comboBox) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.setOpaque(false); // Make the panel transparent
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Tahoma", Font.PLAIN, 20));
        label.setForeground(Color.WHITE);
        panel.add(label);
        panel.add(comboBox);
        return panel;
    }

    // Method to load the image
    private BufferedImage loadImage(String path) {
        try {
            return javax.imageio.ImageIO.read(getClass().getResource(path));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Method to apply a stronger Gaussian blur to the image
    private BufferedImage applyGaussianBlur(BufferedImage src) {
        int radius = 30;  // Increased radius for a stronger blur
        float[] matrix = new float[radius * radius];
        for (int i = 0; i < radius; i++) {
            for (int j = 0; j < radius; j++) {
                matrix[i * radius + j] = 1f / (radius * radius); // Simple blur matrix
            }
        }

        Kernel kernel = new Kernel(radius, radius, matrix);
        ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);

        return op.filter(src, null);
    }

    // Method to style buttons
    private void styleButton(JButton button) {
        button.setBackground(new Color(50, 150, 250));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Tahoma", Font.BOLD, 24)); // Adjusted font size
        button.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button
        button.setFocusPainted(false); // Remove the focus paint when clicked
        button.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 0), 1)); // Make the border invisible
        button.setOpaque(true); // Make sure the background is opaque
        button.addActionListener(this);
    }

    // Action listener to handle button clicks
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == submitButton) {
            // Get values from the fields
            String customerName = customerNameField.getText();
            String contactNumber = contactNumberField.getText();
            String eventDate = eventDateField.getText();
            String eventTime = eventTimeField.getText();
            String expectedPeople = expectedPeopleField.getText();

            // Get values from combo boxes
            String eventVenue = (String) eventVenueComboBox.getSelectedItem();
            String eventTheme = (String) eventThemeComboBox.getSelectedItem();
            String extraPrograms = (String) extraProgramsComboBox.getSelectedItem();
            String eventType = (String) eventTypeComboBox.getSelectedItem();
            String dressCode = (String) dressCodeComboBox.getSelectedItem();
            String foodType = (String) foodTypeComboBox.getSelectedItem();
            String cuisineType = (String) cuisineTypeComboBox.getSelectedItem();
            String transportationForEvent = (String) transportationForEventComboBox.getSelectedItem();
            String transportationForGuests = (String) transportationForGuestsComboBox.getSelectedItem();
            String accommodationForGuests = (String) accommodationForGuestsComboBox.getSelectedItem();
            String paymentMethod = (String) paymentMethodComboBox.getSelectedItem();
            String eventStatus = (String) eventStatusComboBox.getSelectedItem();

            // Insert event details into database
            insertEventIntoDatabase(customerName, contactNumber, eventDate, eventTime, expectedPeople, eventVenue,
                    eventTheme, extraPrograms, eventType, dressCode, foodType, cuisineType, transportationForEvent,
                    transportationForGuests, accommodationForGuests, paymentMethod, eventStatus);
        }
    }

    // Method to insert event data into MySQL database
    private void insertEventIntoDatabase(String customerName, String contactNumber, String eventDate, String eventTime,
                                         String expectedPeople, String eventVenue, String eventTheme, String extraPrograms,
                                         String eventType, String dressCode, String foodType, String cuisineType,
                                         String transportationForEvent, String transportationForGuests, String accommodationForGuests,
                                         String paymentMethod, String eventStatus) {

        String sql = "INSERT INTO Events (customer_name, contact_number, event_date, event_time, expected_people, event_venue, event_theme, extra_programs, event_type, dress_code, food_type, cuisine_type, transportation_for_event, transportation_for_guests, accommodation_for_guests, payment_method, event_status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set parameters for the PreparedStatement
            pstmt.setString(1, customerName);
            pstmt.setString(2, contactNumber);
            pstmt.setString(3, eventDate);
            pstmt.setString(4, eventTime);
            pstmt.setInt(5, Integer.parseInt(expectedPeople));
            pstmt.setString(6, eventVenue);
            pstmt.setString(7, eventTheme);
            pstmt.setString(8, extraPrograms);
            pstmt.setString(9, eventType);
            pstmt.setString(10, dressCode);
            pstmt.setString(11, foodType);
            pstmt.setString(12, cuisineType);
            pstmt.setString(13, transportationForEvent);
            pstmt.setString(14, transportationForGuests);
            pstmt.setString(15, accommodationForGuests);
            pstmt.setString(16, paymentMethod);
            pstmt.setString(17, eventStatus);

            // Execute the insert statement
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Event has been successfully added to the database!");

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to add event to the database.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Main method to run the page
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AddEvent()); // Run the AddEvent page
    }
}
