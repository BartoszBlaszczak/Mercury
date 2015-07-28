package pl.uz.mercury.controler.option.common;

import javax.naming.NamingException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import pl.uz.mercury.Properties;
import pl.uz.mercury.dto.common.MercuryOptionDto;
import pl.uz.mercury.serviceremoteinterface.common.MercuryService;
import pl.uz.mercury.util.PropertiesReader;
import pl.uz.mercury.view.optioninternalframe.common.MercuryClientOptionInternalFrame;

public abstract class MercuryClientOption <Dto extends MercuryOptionDto, Service extends MercuryService, InternalFrame extends MercuryClientOptionInternalFrame>
{
	// protected final Service service;
	protected final InternalFrame	optionInternalFrame;
	private final JButton			optionButton	= new JButton();
	private final JCheckBoxMenuItem	menuCheckItem	= new JCheckBoxMenuItem();
	protected PropertiesReader		optionPropertiesReader;
	protected PropertiesReader		messageReader;

	public MercuryClientOption(String jndiName, InternalFrame optionInternalFrame, PropertiesReader optionPropertiesReader,
			PropertiesReader messageReader)
			throws NamingException
	{
		// this.service = InitialContext.doLookup(jndiName);
		this.optionInternalFrame = optionInternalFrame;
		this.optionPropertiesReader = optionPropertiesReader;
		this.messageReader = messageReader;
		setUp();
	}

	private void setUp ()
	{
		String optionName = optionPropertiesReader.getProperty(Properties.Option.Element.pluralname);
		ImageIcon icon = new ImageIcon(optionPropertiesReader.getProperty(Properties.Option.Element.icon));

		optionInternalFrame.setFrameIcon(icon);
		optionInternalFrame.setTitle(optionName);

		optionButton.setText(optionName);
		optionButton.setIcon(icon);
		
		menuCheckItem.setText(optionName);
	}

	public void setVisible (boolean visible)
	{
		optionInternalFrame.setVisible(visible);
	}

	public JButton getOptionButton ()
	{
		return optionButton;
	}

	public InternalFrame getInternalFrame ()
	{
		return optionInternalFrame;
	}
	
	public JCheckBoxMenuItem getMenuItem()
	{
		return menuCheckItem;
	}
}
