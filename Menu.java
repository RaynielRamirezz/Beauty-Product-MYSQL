package pos;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.BoxLayout;

@SuppressWarnings("serial")
public class Menu extends JFrame
{
	public Menu() 
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 120, 150);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		JButton Search = new JButton("Search Product");
		Search.setAlignmentX(Component.CENTER_ALIGNMENT);
		Search.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				dispose();
				Search frame = new Search();
				frame.setVisible(true);
			}
		});
		getContentPane().add(Search);
		 
		
		JButton Cart = new JButton("          Cart          ");
		Cart.setAlignmentX(Component.CENTER_ALIGNMENT);
		Cart.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				dispose();
				Cart frame = new Cart();
				frame.setVisible(true);
			}
		});
		getContentPane().add(Cart);
		
		JButton Reciept = new JButton("       Reciept       ");
		Reciept.setAlignmentX(Component.CENTER_ALIGNMENT);
		Reciept.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				dispose();
				Receipt frame = new Receipt();
				frame.setVisible(true);
			}
		});
		getContentPane().add(Reciept);
		
		JButton logout = new JButton("        Logout       ");
		logout.setAlignmentX(Component.CENTER_ALIGNMENT);
		logout.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				dispose();
				Login frame = new Login();
				frame.setVisible(true);
			}
		});
		getContentPane().add(logout);
	}
}
