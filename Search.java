package pos;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.*;

@SuppressWarnings("serial")
public class Search extends JFrame {

	private JPanel bigPanel;
	private JTextField productSearch;
	private JTable table;
	private SQL_Connection connection;
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Search() {
		
		try {
			
			connection = new SQL_Connection();	
			
		} catch(Exception exc) {
			
			JOptionPane.showMessageDialog(this,"Error: " + exc, " Error", JOptionPane.ERROR_MESSAGE);
		}
		
		setTitle("Client Search APP");
		setSize(new Dimension(500,550));
		bigPanel = new JPanel();
		bigPanel.setLayout(new BoxLayout(bigPanel, BoxLayout.Y_AXIS)); 
		setContentPane(bigPanel);
		
		JPanel panel = new JPanel();
		
		JLabel search = new JLabel("Enter The id or category");
		panel.add(search);
		
		productSearch = new JTextField();
		productSearch.setColumns(10);
		panel.add(productSearch);
	        
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String search = productSearch.getText();

					List<Data_Constructor> productFinder = null;
					
					if(search != null && isInteger(search)) {
						
						productFinder = connection.searchIdProducts(search);
									
					} else if (search != null) {
						
						productFinder = connection.searchCategoryProducts(search);
					}
					else {
						productFinder = connection.getAllProducts();
					}
					
					Table_model model = new Table_model(productFinder);
					
					table.setModel(model);
					
					}catch(Exception exc) {
						
						JOptionPane.showMessageDialog(Search.this,"Error: " + exc, " Error", JOptionPane.ERROR_MESSAGE);
					}
			}
		});
		
		panel.add(btnSearch);
		
		JComboBox Categories = new JComboBox();
		Categories.addItem("");
		Categories.addItem("Shampoo");
		Categories.addItem("Lip Gloss");
		Categories.addItem("Cleanser");
		Categories.addItem("Lotion");
		Categories.setSelectedItem(null);	
		
		Categories.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {			
				String chooseItem = (String) Categories.getSelectedItem();
				productSearch.setText(chooseItem);						
			}
		});
		
		panel.add(Categories);
		
		
		bigPanel.add(panel);
		
		JScrollPane scrollPane = new JScrollPane();
		
		table = new JTable();
		scrollPane.setViewportView(table);
		bigPanel.add(scrollPane);
		
		JPanel panel_1 = new JPanel();
		
		JButton backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Menu frame = new Menu();
				frame.setVisible(true);	
				dispose();	
			}
		});
		panel_1.add(backButton);
		
		JButton btnNewButton = new JButton("Add Products ");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				// create dialog
				Add dialog = new Add(Search.this, connection,null,false);

				// show dialog
				dialog.setVisible(true);
			}
		});
		panel_1.add(btnNewButton);
		
		JButton Edit = new JButton("Edit Products");
		Edit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// get the selected item
				int row = table.getSelectedRow();
				
				// make sure a row is selected
				if (row < 0) {
					JOptionPane.showMessageDialog(Search.this, "You must select an product", "Error",
							JOptionPane.ERROR_MESSAGE);				
					return;
				}
				
				// get the current product
				Data_Constructor tempproduct =  (Data_Constructor) table.getValueAt(row, Table_model.OBJECT_COL);
				
				// create dialog
				Add dialog = new Add(Search.this, connection, tempproduct, true);

				// show dialog
				dialog.setVisible(true);
				
			}
		});
		panel_1.add(Edit);
		
		
		JButton deleteButton = new JButton("Delete Products");
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					
				try {
					// get the selected row
					int row = table.getSelectedRow();

					// make sure a row is selected
					if (row < 0) {
						JOptionPane.showMessageDialog(Search.this, 
								"You must select a product", "Error", JOptionPane.ERROR_MESSAGE);				
						return;
					}

					// prompt the user
					int response = JOptionPane.showConfirmDialog(
							Search.this, "Delete this product?", "Confirm", 
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

					if (response != JOptionPane.YES_OPTION) {
						return;
					}

					// get the current product
					Data_Constructor products = (Data_Constructor) table.getValueAt(row, Table_model.OBJECT_COL);

					// delete the product
					connection.deleteProducts(products.getId());

					// refresh GUI
					refresh();

					// show success message
					JOptionPane.showMessageDialog(Search.this,
							"Product deleted succesfully.", "product Deleted",
							JOptionPane.INFORMATION_MESSAGE);

				} catch (Exception exc) {
					JOptionPane.showMessageDialog(Search.this,
							"Error deleting product: " + exc.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
							
			}
		});
		panel_1.add(deleteButton);
		bigPanel.add(panel_1);
	}
	
	public void refresh() {
		
		try {
			List<Data_Constructor> productFinder = connection.getAllProducts();

			// create the model and update the "table"
			Table_model model = new Table_model(productFinder);

			table.setModel(model);
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(this, "Error: " + exc, "Error",
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
