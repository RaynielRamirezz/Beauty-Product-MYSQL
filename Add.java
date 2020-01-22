package pos;


import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Toolkit;

@SuppressWarnings("serial")
public class Add extends JFrame {

	JPanel bigpanel;
	private JTextField ProductName;
	private JTextField Categories;
	private JTextField cost1;
	private JTextField IDtextField;
	private JTextField Amount;
	
	private SQL_Connection connection;	
	private Search Search;
	
	private Data_Constructor previousConstructor = null;
	private boolean updateMode = false;

	
	public Add(Search theSearch,SQL_Connection theConnection, Data_Constructor thePreviousConstructor, 
			boolean theUpdateMode)
	{
		this();
		connection = theConnection;
		Search = theSearch;
		
		previousConstructor = thePreviousConstructor;
		updateMode = theUpdateMode;
		
		if (updateMode) {
			setTitle("Edit Information");
			
			populateGui(previousConstructor);
		}
	}
		
	/*public Add(Search theSearch,
			SQL_Connection theConnection) {
		this(theSearch, theConnection, null, false);
	}*/
	
	private void populateGui(Data_Constructor theConstructor) {

  		ProductName.setText(theConstructor.getProductName());
  		Categories.setText(theConstructor.getcategoryProducts());
  		cost1.setText(theConstructor.getcost().toString());
  		int id = theConstructor.getId();
  		String idConvert = String.valueOf(id);
  		IDtextField.setText(idConvert);
  		int productAmount = theConstructor.getamountProduct();
  		String productConvert = String.valueOf(productAmount);
  		Amount.setText(productConvert);
  		
  		
  	}
	
	public Add() {
		
		setSize(new Dimension(235, 390));
		setTitle("Add Products");
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Rayniel Ramirez\\Desktop\\beauty-body-cares-products-and-cosmetics-icon-set-vector-19981832.jpg"));
		bigpanel = new JPanel();
		bigpanel.setLayout(new BoxLayout(bigpanel, BoxLayout.Y_AXIS));
		setContentPane(bigpanel);
		
		JPanel panel = new JPanel();
		
		JLabel Product = new JLabel("Product Name");
		panel.add(Product);
		
		ProductName = new JTextField();
		ProductName.setColumns(20);
		ProductName.addKeyListener(new KeyAdapter() 
		{
			public void keyTyped(KeyEvent check) 
			{
				char  c = check.getKeyChar();
				if((Character.isDigit(c) || (c==KeyEvent.VK_BACK_SPACE))) 
				{
					check.consume();
				}
			}
		});
		panel.add(ProductName);
		
		JPanel panel3 = new JPanel();
		JLabel lblCategory = new JLabel("Category Product");
		panel3.add(lblCategory);
		Categories = new JTextField();
		Categories.setColumns(20);
		Categories.addKeyListener(new KeyAdapter() 
		{
			public void keyTyped(KeyEvent check) 
			{
				char  c = check.getKeyChar();
				if((Character.isDigit(c) || (c==KeyEvent.VK_BACK_SPACE))) 
				{
					check.consume();
				}
			}
		});
		panel3.add(Categories);
		
		JPanel panel4 = new JPanel();
		JLabel lblCost = new JLabel("Cost  ");
		panel4.add(lblCost);
		cost1 = new JTextField();
		cost1.setColumns(20);
		panel4.add(cost1);
		
		JPanel panel5 = new JPanel();
		JLabel idProduct = new JLabel("ID");
		panel5.add(idProduct);
		IDtextField = new JTextField();
		IDtextField.setColumns(20);
		IDtextField.addKeyListener(new KeyAdapter() 
		{
			public void keyTyped(KeyEvent check) 
			{
				char  c = check.getKeyChar();
				if(!(Character.isDigit(c) || (c==KeyEvent.VK_BACK_SPACE))) 
				{
					check.consume();
				}
			}
		});
		panel5.add(IDtextField);
		
		JPanel panel6 = new JPanel();
		JLabel lblProductAmount = new JLabel("Product Amount");
		panel6.add(lblProductAmount);
		Amount = new JTextField();
		Amount.setColumns(20);
		Amount.addKeyListener(new KeyAdapter() 
		{
			public void keyTyped(KeyEvent check) 
			{
				char  c = check.getKeyChar();
				if(!(Character.isDigit(c) || (c==KeyEvent.VK_BACK_SPACE))) 
				{
					check.consume();
				}
			}
		});
		panel6.add(Amount);
		
		bigpanel.add(panel);
		bigpanel.add(panel3);
		bigpanel.add(panel4);
		bigpanel.add(panel5);
		bigpanel.add(panel6);
		
		JPanel buttonPanel = new JPanel();
		
			JButton saveButton = new JButton("Save");
			saveButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
					saveproduct();						
				}
			});
			buttonPanel.add(saveButton);
			
			JButton cancelButton = new JButton("Cancel");
			cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
					dispose();
				}
			});
			buttonPanel.add(cancelButton);
		
		
		bigpanel.add(buttonPanel);
	}        
              protected BigDecimal convertStringToBigDecimal(String salaryStr) {

          		BigDecimal result = null;

          		try {
          			double salaryDouble = Double.parseDouble(salaryStr);

          			result = BigDecimal.valueOf(salaryDouble);
          		} catch (Exception exc) {
          			//System.out.println("Invalid value. Defaulting to 0.0");
          			JOptionPane.showMessageDialog(null, "Invalid value. Defaulting to 0.0");
          			result = BigDecimal.valueOf(0.0);
          		}

          		return result;
          	}
              
              protected BigDecimal convertStringToBigDecimal(String salaryStr,int cost) {

            		BigDecimal result = null;

            		try {
            			double salaryDouble = Double.parseDouble(salaryStr);

            			result = BigDecimal.valueOf(salaryDouble);
            		} catch (Exception exc) {
            			//System.out.println("Invalid value. Defaulting to 0.0");
            			JOptionPane.showMessageDialog(null, "Invalid value. Defaulting to " + cost);
            			result = BigDecimal.valueOf(0.0);
            		}

            		return result;
            	}

          	protected void saveproduct() {
          	
          		// get the product info from gui
          		String productName = ProductName.getText();
          		String categoryProducts = Categories.getText();
          		String cost = cost1.getText();
          		BigDecimal convertCost = convertStringToBigDecimal(cost);
          		String id = IDtextField.getText();
          		int convertID = Integer.parseInt(id);
          		String productAmount = Amount.getText();
          		int convertAmount = Integer.parseInt(productAmount);
          		
          		Data_Constructor constructor = null;

          		constructor = new Data_Constructor(convertID,productName, convertAmount,categoryProducts, convertCost);
          		/*if (updateMode) {
          			constructor = previousConstructor;
          			
          			constructor.setProductName(productName);
          			constructor.setcategoryProducts(categoryProducts);
          			constructor.setcost(convertCost);
          			constructor.setId(convertID);
          			constructor.setamountProduct(convertAmount);
          			
          		} else {
          			constructor = new Data_Constructor(convertID,productName, convertAmount,categoryProducts, convertCost);
          		}*/

          		try {
          			// save to the database
          			if (updateMode) {
          				connection.updateProducts(constructor);
          			} else {
          				connection.addProducts(constructor);
          			}

          			// close dialog
          			setVisible(false);
          			dispose();

          			// refresh gui list
          			Search.refresh();

          			// show success message
          			JOptionPane.showMessageDialog(Search,
          					"product saved succesfully.", "product Saved",
          					JOptionPane.INFORMATION_MESSAGE);
          		} catch (Exception exc) {
          			JOptionPane.showMessageDialog(Search,
          					"Error saving product: " + exc.getMessage(), "Error",
          					JOptionPane.ERROR_MESSAGE);
          		}
          		
          	}
}