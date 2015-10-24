package pl.uz.mercury.view.optioninternalframe.common;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import net.miginfocom.swing.MigLayout;
import pl.uz.mercury.Properties.Locale;
import pl.uz.mercury.Properties.IconPath;
import pl.uz.mercury.option.common.OptionListener;
import pl.uz.mercury.util.PropertiesReader;
import pl.uz.mercury.searchcriteria.SearchCriteria;

public abstract class InternalFrame
	extends JInternalFrame
{
	private static final long			serialVersionUID	= 1L;

	private final JToolBar				toolBar				= new JToolBar();

	private final JButton				addButton			= new JButton();
	private final JButton				updateButton		= new JButton();
	private final JButton				deleteButton		= new JButton();
	private final JButton				refreshButton		= new JButton();

	private final Container				contentPane			= super.getContentPane();
	private final JScrollPane			scrollPane			= new JScrollPane();
	private final JTable				table				= new JTable();
	protected final TableColumnModel	columnModel			= table.getColumnModel();
	private final ListSelectionModel	selectionModel		= table.getSelectionModel();
	private final DefaultTableModel		dataModel;
	private final JPanel				searchPanel			= new JPanel();

	protected final PropertiesReader	localizationReader;
	protected final String				ID					= "id";

	{
		updateButton.setEnabled(false);
		toolBar.add(addButton);
		toolBar.add(updateButton);
		toolBar.add(deleteButton);
		toolBar.add(refreshButton);
		contentPane.add(toolBar, BorderLayout.NORTH);

		dataModel = getTableModel();
		table.setModel(dataModel);
		scrollPane.setViewportView(table);
		contentPane.add(scrollPane, BorderLayout.CENTER);

		searchPanel.setLayout(new MigLayout());
		contentPane.add(searchPanel, BorderLayout.SOUTH);
	}

	public InternalFrame(PropertiesReader localizationReader)
	{
		this.localizationReader = localizationReader;
		localize();
		setUpSearchPanel();
		setUpColumns();
	}

	protected abstract void setUpColumns ();

	protected abstract void setUpSearchPanel ();

	public abstract List <SearchCriteria> getSearchCriteria ();

	protected DefaultTableModel getTableModel ()
	{
		return new DefaultTableModel();
	}

	private void localize ()
	{
		addButton.setText(localizationReader.getProperty(Locale.ADD));
		addButton.setIcon(new ImageIcon(getClass().getResource(localizationReader.getProperty(IconPath.ADD))));

		updateButton.setText(localizationReader.getProperty(Locale.UPDATE));
		updateButton.setIcon(new ImageIcon(getClass().getResource(localizationReader.getProperty(IconPath.UPDATE))));

		deleteButton.setText(localizationReader.getProperty(Locale.DELETE));
		deleteButton.setIcon(new ImageIcon(getClass().getResource(localizationReader.getProperty(IconPath.DELETE))));

		refreshButton.setText(localizationReader.getProperty(Locale.REFRESH));
		refreshButton.setIcon(new ImageIcon(getClass().getResource(localizationReader.getProperty(IconPath.REFRESH))));

		addComponentToSearchPanel(new JLabel(localizationReader.getProperty(Locale.SEARCH_CRITERIA)), "span 2, wrap");
	}

	public void setUpListener (OptionListener listener)
	{
		addButton.addActionListener(e -> listener.onAdd());
		updateButton.addActionListener(e -> listener.onUpdate());
		deleteButton.addActionListener(e -> listener.onDelete());
		refreshButton.addActionListener(e -> listener.onRefresh());

		dataModel.addTableModelListener(listener::onChange);
	}

	protected void setColumns (Object... columnNames)
	{
		for (Object columnName : columnNames)
		{
			dataModel.addColumn(columnName);
		}
		columnModel.removeColumn(columnModel.getColumn(columnModel.getColumnIndex(ID)));
	}

	protected void setCellEditorComboBox (int column, JComboBox <?> comboBox)
	{
		table.getColumnModel().getColumn(column).setCellEditor(new DefaultCellEditor(comboBox));
	}

	public void add ()
	{
		dataModel.addRow(getDefaultNewRow());
	}
	
	protected Object[] getDefaultNewRow()
	{
		return new Object[] {};
	}

	public int getSelectedRowIndex ()
	{
		return selectionModel.getMinSelectionIndex();
	}

	public Long getRowId (int index)
	{
		Object id = dataModel.getValueAt(index, dataModel.findColumn(ID));
		return (Long) id;
	}

	public Object getValue (int row, String columnName)
	{
		return dataModel.getValueAt(row, dataModel.findColumn(columnName));
	}

	public void delete (int index)
	{
		dataModel.removeRow(index);
	}

	public void addRows (Object[]... rowsData)
	{
		for (Object[] rowData : rowsData)
		{
			dataModel.addRow(rowData);
		}
	}

	public void setRows (Object[]... rowsData)
	{
		dataModel.setRowCount(0);
		addRows(rowsData);
	}

	public void setUpdateButtonState (boolean enabled)
	{
		updateButton.setEnabled(enabled);
	}

	public Object[] getRowData (int rowIndex)
	{
		Object[] rowData = new Object[dataModel.getColumnCount()];
		for (int i = 0; i < dataModel.getColumnCount(); i++)
		{
			rowData[i] = dataModel.getValueAt(rowIndex, i);
		}
		return rowData;
	}

	protected void addComponentToSearchPanel (Component component, String constraints)
	{
		searchPanel.add(component, constraints);
	}
}
