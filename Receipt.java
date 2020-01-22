package pos;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import net.proteanit.sql.DbUtils;

	@SuppressWarnings("serial")
	public class Receipt extends JFrame {

		private JPanel bigPanel;
		private JTable table;
		private SQL_Connection connection;
		
		
		public Receipt() {
			
			try {
				
				connection = new SQL_Connection();
				
			} catch(Exception exc) {
				
				JOptionPane.showMessageDialog(this,"Error: " + exc, " Error", JOptionPane.ERROR_MESSAGE);
			}
			
			
			setTitle("Receipt History");
			setSize(new Dimension(500,550));
			bigPanel = new JPanel();
			bigPanel.setLayout(new BoxLayout(bigPanel, BoxLayout.Y_AXIS)); 
			setContentPane(bigPanel);
			
			JPanel panel = new JPanel();
			JPanel panel1 = new JPanel();
			JScrollPane scrollPane = new JScrollPane();
			
		    table = new JTable();
		    
		    try {
				displayTable();
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
		    
			scrollPane.setViewportView(table);
			panel.add(scrollPane);
			
			JButton clearButton = new JButton("Clear");
    		clearButton.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent arg0) {
    				
    				int response = JOptionPane.showConfirmDialog(
							Receipt.this, "Are you sure you want to clear the receipt history? ", "Confirm", 
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

					if (response != JOptionPane.YES_OPTION) {
						return;
					}
    				try {
						connection.deleteAllReceipt();
					} catch (SQLException e) {
						e.printStackTrace();
					}
    				
    				try {
    					displayTable();
    				} catch (SQLException e1) {
    					
    					e1.printStackTrace();
    				}
    				
    				JOptionPane.showMessageDialog(Receipt.this,
							"Receipt history cleared ",
							"Receipt saved",
							JOptionPane.INFORMATION_MESSAGE);
    		
    			}
    		});
    		
    		JButton backButton = new JButton("Back");
    		backButton.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent arg0) {
    				
    				Menu frame = new Menu();
    				frame.setVisible(true);	
    				dispose();	
    		
    			}
    		});
    		
    		panel1.add(clearButton);
    		panel1.add(backButton);
			bigPanel.add(panel);
			bigPanel.add(panel1);
		
		}
		
		void displayTable() throws SQLException {
			
			String url = "jdbc:mysql://localhost:3306/loginuser", username = "root", password = "1234";
			
			
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection conn = DriverManager.getConnection(url, username, password);
				String sql = "select * from receipt";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();
				table.setModel(DbUtils.resultSetToTableModel(rs));
				
				
			} catch (ClassNotFoundException e) {
				
				e.printStackTrace();
			}
			
		}
	
		public static void main(String[] args) {
			
			Receipt frame = new Receipt();
			frame.setVisible(true);
	  }

	}