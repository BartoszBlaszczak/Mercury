package pl.uz.mercury;

import java.io.IOException;

import javax.naming.NamingException;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JOptionPane;

import pl.uz.mercury.Properties.Locale;
import pl.uz.mercury.controler.option.MerchandiseOption;
import pl.uz.mercury.controler.option.PurchaseOption;
import pl.uz.mercury.controler.option.SaleOption;
import pl.uz.mercury.controler.option.common.MercuryClientOption;
import pl.uz.mercury.controler.option.common.PropertiesReader;
import pl.uz.mercury.exception.MercuryException;
import pl.uz.mercury.view.MainWindow;

public class MercuryClientMain
{
	private MainWindow					mainWindow	= new MainWindow();
	@SuppressWarnings("rawtypes")
	private MercuryClientOption[]		options;
	@SuppressWarnings("rawtypes")
	private MercuryClientOption			activeOption;
	private PropertiesReader			messageReader;
	private PropertiesReader 			localizationPropertiesReader;

	public static void main (String[] args)
			throws IOException
	{
		new MercuryClientMain();
	}

	public MercuryClientMain()
	{
		try
		{
			loadMessageReader();
			loadLocalizationReader();
			loadOptions();
			mainWindow.localize(localizationPropertiesReader.getProperty(Locale.OPTIONS));
			mainWindow.setVisible(true);
		}
		catch (MercuryException e)
		{
			showMessage(e.getMessage());
			return;
		}
	}

	public void showMessage (String message)
	{
		JOptionPane.showMessageDialog(null, message);
	}
	
	private void loadMessageReader ()
			throws MercuryException
	{
		try
		{
			messageReader = new PropertiesReader(Properties.MESSAGES_FILENAME);
		}
		catch (IOException e)
		{
			throw new MercuryException("Couldn't read " + Properties.MESSAGES_FILENAME);
		}
	}
	
	private void loadLocalizationReader ()
			throws MercuryException
	{
		try
		{
			localizationPropertiesReader = new PropertiesReader(Properties.LOCALE_FILENAME);
		}
		catch (IOException e)
		{
			throw new MercuryException(messageReader.getProperty(Properties.Message.COULD_NOT_READ_LOCALE_FILE));
		}
	}

	@SuppressWarnings("rawtypes")
	private void loadOptions ()
			throws MercuryException
	{
		try
		{
			options = new MercuryClientOption[] { 
					new PurchaseOption(localizationPropertiesReader, messageReader),
					new MerchandiseOption(localizationPropertiesReader, messageReader),
					new SaleOption(localizationPropertiesReader, messageReader)
					};
		}
		catch (NamingException e)
		{
			throw new MercuryException(messageReader.getProperty(Properties.Message.WRONG_JNDI_NAME));
		}
		catch (IOException e)
		{
			throw new MercuryException(messageReader.getProperty(Properties.Message.COULD_NOT_READ_LOCALE_FILE));
		}

		for (MercuryClientOption option : options)
		{
			mainWindow.addInternalFrame(option.getInternalFrame());
			JButton optionButton = option.getOptionButton();
			optionButton.addActionListener(e -> setActiveOption(option));
			mainWindow.addOptionButton(optionButton);
			JCheckBoxMenuItem menuItem = option.getMenuItem();
			menuItem.setSelected(true);
			menuItem.addActionListener(e ->
			{
				if (menuItem.isSelected())
					mainWindow.addOptionButton(optionButton);
				else mainWindow.removeOptionButton(optionButton);
			});
			mainWindow.addMenuItem(option.getMenuItem());
		}
	}

	@SuppressWarnings("rawtypes")
	private void setActiveOption (MercuryClientOption newActiveOption)
	{
		if (activeOption != null) activeOption.setVisible(false);
		activeOption = newActiveOption;
		activeOption.setVisible(true);
	}
}