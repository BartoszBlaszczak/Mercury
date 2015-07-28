package pl.uz.mercury.view.optioninternalframe.common;

import java.awt.BorderLayout;
import java.awt.Container;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import pl.uz.mercury.controler.listener.MercuryCrudOptionListener;
import net.miginfocom.swing.MigLayout;

import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public abstract class MercuryClientCrudOptionInternalFrame
	extends MercuryClientOptionInternalFrame
{
	private final JToolBar				toolBar					= new JToolBar();

	// TODO ikony
	private final JButton				addButton				= new JButton(new ImageIcon("/home/bartek/MERCURY/merchandise_icon.png"));
	private final JButton				showButton				= new JButton(new ImageIcon("/home/bartek/MERCURY/merchandise_icon.png"));
	private final JButton				updateButton			= new JButton(new ImageIcon("/home/bartek/MERCURY/merchandise_icon.png"));
	private final JButton				deleteButton			= new JButton(new ImageIcon("/home/bartek/MERCURY/merchandise_icon.png"));
	private final JButton				refreshButton			= new JButton(new ImageIcon("/home/bartek/MERCURY/merchandise_icon.png"));

	private final Container				contentPane				= super.getContentPane();
	private final JScrollPane			scrollPane				= new JScrollPane();
	private final JTable				table					= new JTable();
	private final ListSelectionModel	selectionModel			= table.getSelectionModel();
	private final DefaultTableModel		dataModel				= new DefaultTableModel();
	private final JPanel				searchPanel				= new JPanel();

	private JButton						filtrButton				= new JButton();
	private JButton						searchButton			= new JButton();
	private int							searchPanelRowNumber	= 1;
	private int							searchPanelColumnNumber	= 0;

	{
		toolBar.add(addButton);
		toolBar.add(showButton);
		toolBar.add(updateButton);
		toolBar.add(deleteButton);
		toolBar.add(refreshButton);
		contentPane.add(toolBar, BorderLayout.NORTH);

		table.setModel(dataModel);
		scrollPane.setViewportView(table);
		contentPane.add(scrollPane, BorderLayout.CENTER);

		searchPanel.setLayout(new MigLayout("", "[][]", "[][]"));
		searchPanel.add(filtrButton);
		searchPanel.add(searchButton);
		contentPane.add(searchPanel, BorderLayout.SOUTH);
	}

	public MercuryClientCrudOptionInternalFrame(MercuryCrudOptionLocalization localization)
	{
		localize(localization);		
	}

	private void localize (MercuryCrudOptionLocalization localization)
	{
		addButton.setText(localization.add);
		showButton.setText(localization.show);
		updateButton.setText(localization.update);
		deleteButton.setText(localization.delete);
		refreshButton.setText(localization.refresh);

		filtrButton.setText(localization.filtr);
		searchButton.setText(localization.find);
	}

	public void setUpListener (MercuryCrudOptionListener listener)
	{
		addButton.addActionListener(e -> listener.doAdd());
		showButton.addActionListener(e -> listener.doShow());
		updateButton.addActionListener(e -> listener.doUpdate());
		deleteButton.addActionListener(e -> listener.doDelete());
		refreshButton.addActionListener(e -> listener.doRefresh());

		filtrButton.addActionListener(e -> listener.doFiltr());
		searchButton.addActionListener(e -> listener.doFind());
	}

	public void addColumns (Object... columnNames)
	{
		for (Object columnName : columnNames)
		{
			dataModel.addColumn(columnName);
		}
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

	public void setSelection (List <Integer> rowsToSelect)
	{
		selectionModel.clearSelection();

		for (int row : rowsToSelect)
		{
			selectionModel.addSelectionInterval(row, row);
		}
	}

	protected abstract void setUpSearchPanel ();

	protected void addCriteriaToSearchPanel (SearchPanelEntry searchCriteria)
	{
		searchPanel.add(filtrButton, "cell " + searchPanelColumnNumber++ + " " + searchPanelRowNumber);
	}

	protected void addSearchPanelNewLine ()
	{
		searchPanelColumnNumber = 0;
		searchPanelRowNumber++;
	}
}
