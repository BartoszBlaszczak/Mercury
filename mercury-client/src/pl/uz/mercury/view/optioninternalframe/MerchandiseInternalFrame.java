package pl.uz.mercury.view.optioninternalframe;

import java.util.Arrays;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.table.TableColumn;

import pl.uz.mercury.Properties.Locale;
import pl.uz.mercury.controler.option.common.PropertiesReader;
import pl.uz.mercury.dto.MerchandiseDto;
import pl.uz.mercury.filtercriteria.SearchCriteria;
import pl.uz.mercury.filtercriteria.SearchPredicate;
import pl.uz.mercury.view.optioninternalframe.common.InternalFrame;

public class MerchandiseInternalFrame
	extends InternalFrame
{
	private static final long	serialVersionUID	= 1L;
	
	private JLabel merchandiseLabel;
	private JTextField merchandiseTextField;

	public MerchandiseInternalFrame(PropertiesReader localizationReader)
	{
		super(localizationReader);
	}
	
	@Override
	protected void setUpColumns ()
	{
		setColumns(
				ID,
				localizationReader.getProperty(Locale.NAME),
				localizationReader.getProperty(Locale.QUANTITY)
				);
		TableColumn column = columnModel.getColumn(1);
		column.setMaxWidth(100000);
		column.setPreferredWidth(100);
	}

	@Override
	protected void setUpSearchPanel ()
	{
		merchandiseLabel = new JLabel(localizationReader.getProperty(Locale.MERCHANDISE));
		merchandiseTextField = new JTextField();
		
		addComponentToSearchPanel(merchandiseLabel, "");
		addComponentToSearchPanel(merchandiseTextField, "w :400:, span 2");
	}

	@Override
	public List<SearchCriteria> getSearchCriteria ()
	{
		return Arrays.asList(new SearchCriteria(MerchandiseDto.NAME, SearchPredicate.LIKE, merchandiseTextField.getText()));
	}
}
