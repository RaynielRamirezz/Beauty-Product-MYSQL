package pos;

import java.util.List;

import javax.swing.table.AbstractTableModel;


@SuppressWarnings("serial")
class Table_model extends AbstractTableModel {

	private static final int firstCOL = 0;
	private static final int secondCOL = 1;
	private static final int thirdCOL = 2;
	private static final int fourthCOL = 3;
	public static final int OBJECT_COL = -1;

	

	private String[] columnNames = { "id", "product", "amount", "Category","cost" };
	private List<Data_Constructor> productFinder;

	public Table_model(List<Data_Constructor> theproductFinder) {
		productFinder = theproductFinder;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return productFinder.size();
	}

	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}

	@Override
	public Object getValueAt(int row, int col) {

		Data_Constructor products = productFinder.get(row);

		switch (col) {
		case firstCOL:
			return products.getId();
		case secondCOL:
			return products.getProductName();
		case thirdCOL:
			return products.getamountProduct();
		case fourthCOL:
			return products.getcategoryProducts();
		case OBJECT_COL:
			return products;	
		default:
			return products.getcost();
		}
	}
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}
}
