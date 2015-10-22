package pl.uz.mercury.view.optioninternalframe;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import pl.uz.mercury.Properties.Locale;
import pl.uz.mercury.util.PropertiesReader;
import pl.uz.mercury.dto.MerchandiseDto;
import pl.uz.mercury.dto.TransactionDto;
import pl.uz.mercury.filtercriteria.SearchCriteria;
import pl.uz.mercury.filtercriteria.SearchPredicate;
import pl.uz.mercury.view.optioninternalframe.common.InternalFrame;

public class TransactionInternalFrame
	extends InternalFrame
{
	private static final long	serialVersionUID	= 1L;
	
	private JLabel dateLabel;
	private JTextField dateFromTextField;
	private JTextField dateToTextField;
	
	private JLabel merchandiseLabel;
	private JComboBox <MerchandiseDto> tableMerchandiseComboBox;
	private JComboBox <MerchandiseDto> searchMerchandiseComboBox;
	
	private JLabel costLabel;
	private JTextField minCostTextField;
	private JTextField maxCostTextField;

	public TransactionInternalFrame(PropertiesReader localizationReader)
	{
		super(localizationReader);
	}
	
	@Override
	protected DefaultTableModel getTableModel ()
	{
		return new DefaultTableModel(){
			private static final long	serialVersionUID	= 1L;
			@Override
			public boolean isCellEditable(int row, int column)
		    {
		        return Boolean.valueOf(column != 5);
		    }
		};
	}
	
	public void setUpMerchandiseComboBoxs(List<MerchandiseDto> dtos)
	{
		dtos.add(0, null);
		MerchandiseDto[] array = dtos.toArray(new MerchandiseDto[]{});
		tableMerchandiseComboBox.setModel(new DefaultComboBoxModel <MerchandiseDto> (array));
		searchMerchandiseComboBox.setModel(new DefaultComboBoxModel <MerchandiseDto> (array));
	}
	
	@Override
	protected void setUpColumns ()
	{
		setColumns(
				ID,
				localizationReader.getProperty(Locale.DATE),
				localizationReader.getProperty(Locale.MERCHANDISE),
				localizationReader.getProperty(Locale.QUANTITY),
				localizationReader.getProperty(Locale.PRICE),
				localizationReader.getProperty(Locale.COST)
				);
		tableMerchandiseComboBox = new JComboBox <MerchandiseDto>();
		setCellEditorComboBox(1, tableMerchandiseComboBox);
	}

	@Override
	protected void setUpSearchPanel ()
	{
		dateLabel = new JLabel(localizationReader.getProperty(Locale.DATE));
		dateFromTextField = new JTextField();
		dateToTextField = new JTextField();
		merchandiseLabel = new JLabel(localizationReader.getProperty(Locale.MERCHANDISE));
		searchMerchandiseComboBox = new JComboBox <MerchandiseDto>();
		costLabel = new JLabel(localizationReader.getProperty(Locale.COST));
		minCostTextField = new JTextField();
		maxCostTextField = new JTextField();
		
		addComponentToSearchPanel(dateLabel, "");
		addComponentToSearchPanel(dateFromTextField, "w :100:, span 2");
		addComponentToSearchPanel(dateToTextField, "w :100:, span 2, wrap");
		addComponentToSearchPanel(merchandiseLabel, "");
		addComponentToSearchPanel(searchMerchandiseComboBox, "wrap");
		addComponentToSearchPanel(costLabel, "");
		addComponentToSearchPanel(minCostTextField, "w :100:, span 2");
		addComponentToSearchPanel(maxCostTextField, "w :100:, span 2");
	}

	@Override
	public List<SearchCriteria> getSearchCriteria ()
	{
		List<SearchCriteria> searchCriteria = new ArrayList <>();
		
		if (!dateFromTextField.getText().isEmpty())
			searchCriteria.add(new SearchCriteria(TransactionDto.DATE, SearchPredicate.AFTER_OR_EQUAL, dateFromTextField.getText()));
		
		if (!dateToTextField.getText().isEmpty())
			searchCriteria.add(new SearchCriteria(TransactionDto.DATE, SearchPredicate.BEFORE_OR_EQUAL, dateToTextField.getText()));

		MerchandiseDto merchadiseDto = (MerchandiseDto)searchMerchandiseComboBox.getSelectedItem();
		if (merchadiseDto != null)
			searchCriteria.add(new SearchCriteria(TransactionDto.MERCHANDISE, MerchandiseDto.ID, merchadiseDto.getId().toString()));
		
		if (!minCostTextField.getText().isEmpty())
			searchCriteria.add(new SearchCriteria(TransactionDto.COST, SearchPredicate.GREATER_OR_EQUAL, minCostTextField.getText()));
		
		if (!maxCostTextField.getText().isEmpty())
			searchCriteria.add(new SearchCriteria(TransactionDto.COST, SearchPredicate.LESSER_OR_EQUAL, maxCostTextField.getText()));

		return searchCriteria;
	}
}
