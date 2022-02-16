package loginform.src;

import java.awt.*;
import java.awt.event.ActionEvent;

import java.sql.*;

import javax.swing.*;

public class loginform extends JFrame {
    final private Font mainFont = new Font("Segoe print", Font.ROMAN_BASELINE, 18);
    JTextField tfEmail;
    private Container pfPassword;
    public void initialize(){
        /*******Form Panel******** */

        JLabel lbLoginForm= new JLabel ("Login Form", SwingConstants.CENTER);
        lbLoginForm.setFont(mainFont);

        JLabel lbEmail=new JLabel("Email");
        lbEmail.setFont(mainFont);

        tfEmail = new JTextField();
        tfEmail.setFont(mainFont);

        JLabel lbPassword = new JLabel("Password");
        lbPassword.setFont(mainFont);

        pfPassword = new JPasswordField();
        pfPassword.setFont(mainFont);

        JPanel formPanel = new JPanel();
        formPanel. setLayout(new GridLayout(0,1,10,10));
        formPanel.add(lbLoginForm);
        formPanel.add(lbEmail);
        formPanel.add(tfEmail);
        formPanel.add(lbPassword);
        formPanel.add(pfPassword);
/***************** buttons panel */

JButton btnLogin = new JButton("Login");
btnLogin.setFont(mainFont);
btnLogin.addActionListener(new ActionListener(){

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        String email = tfEmail.getText();
        String password =String.valueOf(((JPasswordField) pfPassword).getPassword());

User user =getAuthenticatedUser(email, password);

if (user!= null){
    MainFrame mainFrame =new MainFrame();
    mainFrame. initialize(user);
    dispose();
}
else {
    JOptionPane.showMessageDialog(LoginForm.this,
    "Email or Password Invalid",
    "Try again",
    JOptionPane.ERROR_MESSAGE);
}

    }

});

JButton btnCancel =new JButton("Cancel");
btnCancel. setFont(mainFont);
btnCancel.addActionListener(new ActionListener(){

    @Override
    public void actionPerformed(ActionEvent e) {
        dispose();

    }


});
JPanel buttonPanel =new Jpanel();
buttonsPanel.setLayout(new GridLayout(1,2,10,0));
buttonsPanel.add(btnLogin);
buttonsPanel.add(btnCancel);




        add(formPanel, BorderLayout.NORTH);
        add(buttonsPanel, BorderLayout.SOuth);




        setTitle("Login form");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(400,500);
        setMinimumSize(new Dimension(350, 450));

        //setResizable(false)
        setLocationRelativeTo(null);
        setVisible(true);
        
    }
}

private User getAuthenticatedUser(String email, String password){
    User user = null;

    final String DB_URL = "jdbc: mysql://localhost/MyStore?serverTimezone=UTC";
    final String USERNAME = "root";
    final String PASSWORD = "Stupidboys1!";

    try{
        Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        //Connected to database successfully
        String sql = "SELECT * FROM users WHERE email=? AND password=?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement. setString(1, email);
        preparedStatement. setString(2, password);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            user =new User();
            user.name = resultSet.getString("name");
            user.email = resultSet.getString("email");
            user.phone = resultSet.getString("phone");
            user.address = resultSet.getString("address");
            user.password = resultSet.getString("password");
        }
preparedStatement.close();
conn.close();

    }catch(Exception e){
        System.out.println("DAtabase connection failed");
    }
    return user;
}

Run|Debug
public static void main(String[] args) {
    LoginForm loginForm =new LoginForm();
    loginForm.initialize();
}
}

