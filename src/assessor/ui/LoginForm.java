package assessor.ui;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class LoginForm extends JPanel {
    public interface LoginListener {
        void onLoginSuccess();
    }
    
    private final List<LoginListener> loginListeners = new ArrayList<>();
    private final JTextField txtEmail = new JTextField();
    private final JPasswordField txtPassword = new JPasswordField();

    public LoginForm() {
        init();
    }

    private void init() {
        setLayout(new MigLayout("wrap,gapy 3", "[fill,300]"));

        add(new JLabel(new FlatSVGIcon("resources/icon/loginicon/favicon.svg", 1.5f)));

        JLabel lbTitle = new JLabel("Welcome back", JLabel.CENTER);
        lbTitle.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:bold +15;");
        add(lbTitle, "gapy 8 8");

        add(new JLabel("Sign in to access to your dashboard,", JLabel.CENTER));
        add(new JLabel("setting and projects.", JLabel.CENTER));

        JButton cmdFacebook = new JButton("Connect with Facebook", new FlatSVGIcon("resources/icon/loginicon/facebook.svg", 0.35f));
        cmdFacebook.putClientProperty(FlatClientProperties.STYLE, "" +
                "focusWidth:0;" +
                "font:+1;");
        add(cmdFacebook, "gapy 15 10");

        JLabel lbSeparator = new JLabel("Or sign in with email");
        lbSeparator.putClientProperty(FlatClientProperties.STYLE, "" +
                "foreground:$Label.disabledForeground;" +
                "font:-1;");

        // sizegroup to make group component are same size

        add(createSeparator(), "split 3,sizegroup g1");
        add(lbSeparator, "sizegroup g1");
        add(createSeparator(), "sizegroup g1");

        JLabel lbEmail = new JLabel("Email");
        lbEmail.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:bold;");
        add(lbEmail, "gapy 10 5");

//        JTextField txtEmail = new JTextField();
        txtEmail.putClientProperty(FlatClientProperties.STYLE, "" +
                "iconTextGap:10;");
        txtEmail.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter your email");
        txtEmail.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON, new FlatSVGIcon("resources/icon/loginicon/email.svg", 0.35f));

        add(txtEmail);

        JLabel lbPassword = new JLabel("Password");
        lbPassword.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:bold;");

        add(lbPassword, "gapy 10 5,split 2");

        JButton cmdForgotPassword = createNoBorderButton("Forgot Password ?");
        add(cmdForgotPassword, "grow 0,gapy 10 5");

//        JPasswordField txtPassword = new JPasswordField();
        txtPassword.putClientProperty(FlatClientProperties.STYLE, "" +
                "iconTextGap:10;" +
                "showRevealButton:true;");
        txtPassword.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter your password");
        txtPassword.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON, new FlatSVGIcon("resources/icon/loginicon/password.svg", 0.35f));

        add(txtPassword);

        add(new JCheckBox("Remember for 30 days"), "gapy 10 10");

        JButton cmdSignIn = new JButton("Sign in", new FlatSVGIcon("resources/icon/loginicon/next.svg")) {
            @Override
            public boolean isDefaultButton() {
                return true;
            }
        };
        
        cmdSignIn.addActionListener(e -> attemptLogin());
        
        cmdSignIn.putClientProperty(FlatClientProperties.STYLE, "" +
                "foreground:#FFFFFF;" +
                "iconTextGap:10;");
        cmdSignIn.setHorizontalTextPosition(JButton.LEADING);
        add(cmdSignIn, "gapy n 10");

        JLabel lbNoAccount=new JLabel("No account ?");
        lbNoAccount.putClientProperty(FlatClientProperties.STYLE,"" +
                "foreground:$Label.disabledForeground;");
        add(lbNoAccount,"split 2,gapx push n");

        JButton cmdCreateAccount=createNoBorderButton("Create an account");

        add(cmdCreateAccount,"gapx n push");
    }
    
    private void attemptLogin() {
        String email = txtEmail.getText();
        char[] password = txtPassword.getPassword();
        
        if (isValidLogin(email, password)) {
            loginListeners.forEach(LoginListener::onLoginSuccess);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials", 
                "Login Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
private boolean isValidLogin(String email, char[] password) {
    // Get actual UI component values
    String uiEmail = txtEmail.getText();
    char[] uiPassword = txtPassword.getPassword();
    
    // Debug raw input
    System.out.println("\n=== Login Validation Debug ===");
    System.out.println("Email field value: [" + String.valueOf(uiEmail) + "]");
    System.out.println("Password field value: [" + String.valueOf(uiPassword) + "]");
    System.out.println("Password length: " + uiPassword.length);

    // Trim and sanitize
    String cleanEmail = uiEmail != null ? uiEmail.trim() : "";
    String cleanPassword = uiPassword != null ? new String(uiPassword).trim() : "";
    
    // Debug cleaned values
    System.out.println("Cleaned email: [" + cleanEmail + "]");
    System.out.println("Cleaned password: [" + cleanPassword + "]");
    
    // Character-by-character comparison
    boolean emailMatch = "admin".equals(cleanEmail);
    boolean passwordMatch = "admin".equals(cleanPassword);
    
    System.out.println("Email matches: " + emailMatch);
    System.out.println("Password matches: " + passwordMatch);
    
    // Security: clear sensitive data
    if (uiPassword != null) Arrays.fill(uiPassword, '\0');
    if (password != null) Arrays.fill(password, '\0');
    
    return emailMatch && passwordMatch;
}

    private JSeparator createSeparator() {
        JSeparator separator = new JSeparator();
        separator.putClientProperty(FlatClientProperties.STYLE, "" +
                "stripeIndent:8;");
        return separator;
    }
    
    public void addLoginListener(LoginListener listener) {
        loginListeners.add(listener);
    }

    private JButton createNoBorderButton(String text) {
        JButton button = new JButton(text);
        button.putClientProperty(FlatClientProperties.STYLE, "" +
                "foreground:$Component.accentColor;" +
                "margin:1,5,1,5;" +
                "borderWidth:0;" +
                "focusWidth:0;" +
                "innerFocusWidth:0;" +
                "background:null;");
        return button;
    }
}
