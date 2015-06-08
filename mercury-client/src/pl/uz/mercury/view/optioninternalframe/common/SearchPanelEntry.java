package pl.uz.mercury.view.optioninternalframe.common;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import pl.uz.mercury.filtercriteria.FilterPredicate;

public class SearchPanelEntry
{
	public final JLabel							label		= new JLabel();
	public final JComboBox <FilterPredicate>	comboBox	= new JComboBox <>(FilterPredicate.values());
	public final JTextField						textField	= new JTextField();

	public SearchPanelEntry(String label)
	{
		this.label.setText(label);
	}
}