package pl.uz.mercury.controler;

import java.io.IOException;
import javax.naming.NamingException;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JOptionPane;

import pl.uz.mercury.Properties;
import pl.uz.mercury.Properties.Locale;
import pl.uz.mercury.controler.option.MerchandiseOption;
import pl.uz.mercury.controler.option.common.MercuryClientOption;
import pl.uz.mercury.exception.MercuryException;
import pl.uz.mercury.util.PropertiesReader;
import pl.uz.mercury.view.MainWindow;
import pl.uz.mercury.view.optioninternalframe.common.MercuryCrudOptionLocalization;

public class MercuryClientMain
{
	private MainWindow						mainWindow	= new MainWindow();
	private MercuryCrudOptionLocalization	localization;
	@SuppressWarnings("rawtypes")
	private MercuryClientOption[]			options;
	@SuppressWarnings("rawtypes")
	private MercuryClientOption				activeOption;

	private String							login;
	private String							password;
	private PropertiesReader				messageReader;

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
			loadLocalization();
			loadOptions();
			localize();
			mainWindow.setVisible(true);
		}
		catch (MercuryException e)
		{
			showMessage(e.getMessage());
			return;
		}
	}

	private void showMessage (String message)
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

	private void loadLocalization ()
			throws MercuryException
	{
		PropertiesReader localizationPropertiesReader;
		try
		{
			localizationPropertiesReader = new PropertiesReader(Properties.LOCALE_FILENAME);

			localization = new MercuryCrudOptionLocalization(localizationPropertiesReader.getProperty(Locale.options),
					localizationPropertiesReader.getProperty(Locale.add), localizationPropertiesReader.getProperty(Locale.show),
					localizationPropertiesReader.getProperty(Locale.update), localizationPropertiesReader.getProperty(Locale.delete),
					localizationPropertiesReader.getProperty(Locale.refresh), localizationPropertiesReader.getProperty(Locale.filtr),
					localizationPropertiesReader.getProperty(Locale.find));
		}
		catch (IOException e)
		{
			throw new MercuryException(messageReader.getProperty(Properties.Message.COULD_NOT_READ_LOCALE_FILE));
		}
	}

	@SuppressWarnings("rawtypes")
	private void loadOptions ()
	{
		try
		{
			options = new MercuryClientOption[] { new MerchandiseOption(localization, messageReader), };
		}
		catch (NamingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (MercuryClientOption option : options)
		{
			mainWindow.addInternalFrame(option.getInternalFrame());
			JButton optionButton = option.getOptionButton();
			optionButton.addActionListener(e -> setActiveOption(option));
			JCheckBoxMenuItem menuItem = option.getMenuItem();
			menuItem.addActionListener(e ->
			{
				if (menuItem.isSelected())
					mainWindow.addOptionButton(optionButton);
				else mainWindow.removeOptionButton(optionButton);
			});
			mainWindow.addMenuItem(option.getMenuItem());
		}
	}

	private void localize ()
	{
		mainWindow.localize(localization);
	}

	@SuppressWarnings("rawtypes")
	private void setActiveOption (MercuryClientOption newActiveOption)
	{
		if (activeOption != null)
		{
			activeOption.setVisible(false);
		}
		activeOption = newActiveOption;
		activeOption.setVisible(true);
	}
}

/*
 * showMainWindow logIn
 */