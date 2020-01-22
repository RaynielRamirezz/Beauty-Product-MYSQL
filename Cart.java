package pos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.sql.SQLException;
//import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

@SuppressWarnings("serial")
public class Cart extends JFrame 
{
	private JTextField text_Id;
	private JTextField clientTextField;
	private JTextField Product_amount;
	private JTable table;
	private JTable table1;
	private SQL_Connection connection;
    double costValue;
    int amountProduct;
    JLabel Total_label;
    JLabel Total;
    JLabel price;
    JLabel Id_display;
    String Amount;
    int sumAmountCart;
    int GetProductAmount;
    double total;
    String chooseItem;
    
	
	@SuppressWarnings("unchecked")
	public Cart() 
	{
		
	try {
			
		   connection = new SQL_Connection();	
			
		} catch(Exception exc) {
			
			JOptionPane.showMessageDialog(this,"Error: " + exc, " Error", JOptionPane.ERROR_MESSAGE);
		}
	
		getContentPane().setBackground(Color.GRAY);
		setTitle("Cart");
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Rayniel Ramirez\\Desktop\\1cfc9ed5-3420-455c-9506-4ce6fbc1e18e_1.34468a6f9639e3f029805d8b9f871382.jpeg"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1280, 750);
		getContentPane().setLayout(null);
		
		JButton backButton = new JButton("Back");
		backButton.setBounds(10, 640, 150, 30);
		getContentPane().add(backButton);
		
		JScrollPane choose_inv = new JScrollPane();
		choose_inv.setBackground(Color.WHITE);
		choose_inv.setBounds(10, 40, 600, 450);
		getContentPane().add(choose_inv);
		
		JLabel Inventory_scroll = new JLabel("Inventory");
		Inventory_scroll.setBounds(10, 10, 80, 25);
		getContentPane().add(Inventory_scroll);
		
		@SuppressWarnings("rawtypes")
		JComboBox Categories = new JComboBox();
		Categories.setBounds(10, 500, 150, 30);
		Categories.addItem("");
		Categories.addItem("Shampoo");
		Categories.addItem("Lip Gloss");
		Categories.addItem("Cleanser");
		Categories.addItem("Lotion");
		Categories.setSelectedItem(null);
		getContentPane().add(Categories);
		
		Categories.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {			
				chooseItem = (String) Categories.getSelectedItem();
				text_Id.setText(chooseItem);						
			}
		});
		
		text_Id = new JTextField();
		text_Id.setBounds(170, 500, 150, 30);
		getContentPane().add(text_Id);
		text_Id.setColumns(10);
		
		JButton Search = new JButton("Search");
		Search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					String stringSearch = text_Id.getText();
					
					List<Data_Constructor> productFinder = null;

					if(stringSearch != null && isInteger(stringSearch)) {
						
						productFinder = connection.searchIdProducts(stringSearch);
									
					} else if (stringSearch != null) {
						
						productFinder = connection.searchCategoryProducts(stringSearch);
					}
					else {
						productFinder = connection.getAllProducts();
					}
					
					Table_model model = new Table_model(productFinder);
					
					table.setModel(model);
					
					}catch(Exception exc) {
						
						JOptionPane.showMessageDialog(Cart.this,"Error: " + exc, " Error", JOptionPane.ERROR_MESSAGE);
					}
			}
		});
		Search.setBounds(330, 500, 100, 30);
		getContentPane().add(Search);
		
		table = new JTable();
		choose_inv.setViewportView(table);
		
		JLabel label_ProductId = new JLabel("Product ID:");
		label_ProductId.setBounds(10, 540, 100, 30);
		getContentPane().add(label_ProductId);
		
		Id_display = new JLabel("");
		Id_display.setBounds(120, 540, 45, 30);
		getContentPane().add(Id_display);
		
		JLabel lblQuantity = new JLabel("Quantity:");
		lblQuantity.setBounds(10, 580, 100, 30);
		getContentPane().add(lblQuantity);
		
		Product_amount = new JTextField();
		Product_amount.setText("1");
		Product_amount.setBounds(120, 580, 46, 30);
		getContentPane().add(Product_amount);
		Product_amount.setColumns(10);
		
		clientTextField = new JTextField();
		clientTextField.setBounds(270, 540, 345, 30);
		getContentPane().add(clientTextField);
		
		JLabel Custumers = new JLabel("Customer:");
		Custumers.setBounds(180, 540, 80, 30);
		getContentPane().add(Custumers);
		
		JLabel InInventory_amount_label = new JLabel("Amount in inventory:");
		InInventory_amount_label.setBounds(440, 500, 120, 30);
		getContentPane().add(InInventory_amount_label);
		
		JLabel amount = new JLabel("");
		amount.setBounds(570, 500, 45, 30);
		getContentPane().add(amount);
		
		JLabel Price_label = new JLabel("Price:");
		Price_label.setBounds(200, 580, 60, 30);
		getContentPane().add(Price_label);
		
		price = new JLabel("");
		price.setBounds(246, 580, 80, 30);
		getContentPane().add(price);
		
		JButton Add = new JButton("add");
		Add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// get the selected item
				int row = table.getSelectedRow();
				
				// make sure a row is selected
				if (row < 0) {
					JOptionPane.showMessageDialog(Cart.this, "You must select a product", "Error",
							JOptionPane.ERROR_MESSAGE);				
					return;
				}
				
				
				// get the current product
				Data_Constructor products = (Data_Constructor) table.getValueAt(row, Table_model.OBJECT_COL); 
				
				if(products.getamountProduct() -  Integer.parseInt(Product_amount.getText()) >= 0) {
					
				BigDecimal bd = BigDecimal.valueOf(costValue += products.getcost().doubleValue() * 
						Integer.parseInt(Product_amount.getText()));
				//DecimalFormat df = new DecimalFormat("#.##");
				//String formatted = df.format(costValue); 			
			    bd = bd.setScale(2, RoundingMode.HALF_UP);
				Total.setText(String.valueOf(bd));	
				price.setText(String.valueOf(products.getcost().doubleValue()));
				Id_display.setText(String.valueOf(products.getId()));
				amountProduct = products.getamountProduct() - Integer.parseInt(Product_amount.getText());	
				sumAmountCart = connection.getAmountCartValue(products.getId()) + Integer.parseInt(Product_amount.getText());

				try {					
					connection.updateAmountProduct(amountProduct,products.getId());
					
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
				
				try {
					connection.addCart(products.getId(),products.getProductName(),
							Integer.parseInt(Product_amount.getText()),products.getcategoryProducts(),
					products.getcost());
					
				} catch (Exception e1) {
					try {
						connection.updateAmountCart(sumAmountCart,products.getId());
					} catch (SQLException e2) {
						
						e2.printStackTrace();
					}
				}
				
				refresh();
				
			 } else {
				 
				 JOptionPane.showMessageDialog(Cart.this,
						 "There's no longer any amount for that "
							 		+ "product, please go to the edit button to edit it", "Error",
							JOptionPane.ERROR_MESSAGE);
				 
			 }
			}
		});
		Add.setBounds(330, 580, 100, 30);
		getContentPane().add(Add);
		
		JLabel Cart_label = new JLabel("Cart");
		Cart_label.setBounds(640, 15, 45, 13);
		getContentPane().add(Cart_label);
		
		JScrollPane Reciep = new JScrollPane();
		Reciep.setBackground(Color.WHITE);
		Reciep.setBounds(640, 40, 600, 450);
		getContentPane().add(Reciep);
		
		JButton Delete = new JButton("delete");
		Delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					// get the selected row
					int row = table1.getSelectedRow();

					// make sure a row is selected
					if (row < 0) {
						JOptionPane.showMessageDialog(Cart.this, 
								"You must select a product", "Error", JOptionPane.ERROR_MESSAGE);				
						return;
					}

					// prompt the user
					int response = JOptionPane.showConfirmDialog(
							Cart.this, "Delete this product?", "Confirm", 
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

					if (response != JOptionPane.YES_OPTION) {
						return;
					}

					// get the current product
					Data_Constructor products = (Data_Constructor) table1.getValueAt(row, Table_model.OBJECT_COL);

					GetProductAmount = connection.getAmountProductValue(products.getId()) + 
							connection.getAmountCartValue(products.getId());
					
					try {					
						connection.updateAmountProduct(GetProductAmount,products.getId());
						
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}
					
					
					BigDecimal bd = BigDecimal.valueOf(costValue -= products.getcost().doubleValue() * 
							products.getamountProduct());
					bd = bd.setScale(2, RoundingMode.HALF_UP);
					//DecimalFormat df = new DecimalFormat("#.##");
					//String formatted = df.format(costValue); 
					Total.setText(String.valueOf(bd));
					
					// delete the product
					connection.deleteCart(products.getId());

					// refresh GUI
					refresh();

					// show success message
					JOptionPane.showMessageDialog(Cart.this,
							"Product deleted succesfully.", "product Deleted",
							JOptionPane.INFORMATION_MESSAGE);

				} catch (Exception exc) {
					JOptionPane.showMessageDialog(Cart.this,
							"Error deleting product: " + exc.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
				
				if(costValue < 0) {
					
					costValue = 0;
					Total.setText("0");
					
				}			
			}
		});
		
		Delete.setBounds(440, 580, 100, 30);
		getContentPane().add(Delete);
		
		Total_label = new JLabel("Total:");
		Total_label.setBounds(640, 500, 60, 30);
		getContentPane().add(Total_label);
		
		Total = new JLabel("");
		Total.setBounds(710, 500, 100, 30);
		getContentPane().add(Total);
		
		JButton CashOut = new JButton("Cash out");
		CashOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				dispose();
				JFrame frame = new JFrame("Receipt");   
			    frame.setSize(new Dimension(500,550));
		        frame.setLocation(200,200);
		        
		        JPanel mainPanel = new JPanel();
		        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));   
	            JPanel panel = new JPanel();
	            JPanel panel1 = new JPanel();
	            JPanel panel2 = new JPanel();
	            
	            
	            JScrollPane cartPane = new JScrollPane();
	            JTable tableReceipt = new JTable();
	    		cartPane.setViewportView(tableReceipt);
	    		panel.add(cartPane);
	    		
	    		List<Data_Constructor> productFinder = null;
	    		try {
					productFinder = connection.getAllCart();
                    Table_model modelReceipt = new Table_model(productFinder);
					
                    tableReceipt.setModel(modelReceipt);
					
				} catch (Exception e) {
				
					e.printStackTrace();
				}
	    	
	    		
	    		JButton submitButton = new JButton("Submit");
	    		submitButton.addActionListener(new ActionListener() {
	    			public void actionPerformed(ActionEvent arg0) {
	    				
	    				String client = clientTextField.getText();
	    				
	    				if(client != null && !client.isEmpty()) {
	    				
	    				int response = JOptionPane.showConfirmDialog(
								Cart.this, "Are you sure you want to make the receipt? ", "Confirm", 
								JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

						if (response != JOptionPane.YES_OPTION) {
							return;
						}
	    				
	    				try {
							connection.deleteAllCart();
							
						} catch (SQLException e) {
							
							e.printStackTrace();
						}
                        
	    				try {
	    					List<Data_Constructor> productFinder = connection.getAllCart();

	    					// create the model and update the "table"
	    					
	    					Table_model model = new Table_model(productFinder);

	    					tableReceipt.setModel(model);
	    					
	    				} catch (Exception exc) {
	    					JOptionPane.showMessageDialog(Cart.this, "Error: " + exc, "Error",
	    							JOptionPane.ERROR_MESSAGE);
	    				}
	    				refresh();
	    				
	    				BigDecimal bd = BigDecimal.valueOf(costValue);
						bd = bd.setScale(2, RoundingMode.HALF_UP);
	    				JOptionPane.showMessageDialog(Cart.this,
								"Receipt added succesfully. The total is " +  String.valueOf(bd),
								"Receipt saved",
								JOptionPane.INFORMATION_MESSAGE);			
	    				
	    				SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	    				Date date = new Date(System.currentTimeMillis());
                        String dateTime = formatter.format(date);
	    				
							try {
								connection.addReceipt(clientTextField.getText(), bd,dateTime);
							} catch (Exception e) {
								
								e.printStackTrace();
							}
							
	    				} else {
	    					
	    					JOptionPane.showMessageDialog(Cart.this,
	    							"Error. Be sure to check if everything has been written. ", "Error",
	    							JOptionPane.ERROR_MESSAGE);	    					
	    				}	
	    			}
	    		});
	    		
	    		JButton cancelButton = new JButton("cancel");
	    		cancelButton.addActionListener(new ActionListener() {
	    			public void actionPerformed(ActionEvent arg0) {
	    				
	    				frame.dispose();
	    				setVisible(true);
	    		
	    			}
	    		});
	    		
	    		BigDecimal bd = BigDecimal.valueOf(costValue);
				bd = bd.setScale(2, RoundingMode.HALF_UP);
	    		JLabel total = new JLabel();
	    		total.setText("Total: " + String.valueOf(bd));	
	    		
	    		panel1.add(total);
	    		panel2.add(submitButton);
	    		panel2.add(cancelButton);
	    		
	    		mainPanel.add(panel);
	    		mainPanel.add(panel1);
	    		mainPanel.add(panel2);
	    		
	    		
	    		frame.getContentPane().add(mainPanel);
	    		frame.setVisible(true);			
			}
		});
		CashOut.setBounds(820, 500, 100, 30);
		getContentPane().add(CashOut);
		
		table1 = new JTable();
		Reciep.setViewportView(table1);
		
		backButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				Menu frame = new Menu();
				frame.setVisible(true);	
				dispose();	
			}
		});
	}
	
		public void refresh() {
			
			try {
				List<Data_Constructor> productFinder = connection.getAllProducts();
				List<Data_Constructor> productFinder1 = connection.getAllCart();

				// create the model and update the "table"
				Table_model model = new Table_model(productFinder);
				Table_model model1 = new Table_model(productFinder1);

				table.setModel(model);
				table1.setModel(model1);
				
			} catch (Exception exc) {
				JOptionPane.showMessageDialog(Cart.this, "Error: " + exc, "Error",
						JOptionPane.ERROR_MESSAGE);
			}
	}
	
		public static boolean isInteger(String s) {
		    try {
		        Integer.parseInt(s);
		        return true;
		    } catch(NumberFormatException e) {
		        return false;
		    }
		}
	
}