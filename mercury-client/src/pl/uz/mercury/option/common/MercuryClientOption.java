package pl.uz.mercury.option.common;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pl.uz.mercury.Properties;
import pl.uz.mercury.dto.common.MercuryOptionDto;
import pl.uz.mercury.exception.DeletingException;
import pl.uz.mercury.exception.RetrievingException;
import pl.uz.mercury.exception.SavingException;
import pl.uz.mercury.exception.ValidationException;
import pl.uz.mercury.service.common.MercuryService;
import pl.uz.mercury.util.PropertiesReader;
import pl.uz.mercury.view.optioninternalframe.common.InternalFrame;

import javax.ejb.EJBAccessException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.*;
import javax.swing.event.TableModelEvent;

public abstract class MercuryClientOption <Dto extends MercuryOptionDto, Service extends MercuryService <Dto>, OptionInternalFrame extends InternalFrame>
{
	protected final Service						service;
	protected final OptionInternalFrame			optionInternalFrame;
	private final JButton						optionButton	= new JButton();
	private final JCheckBoxMenuItem				menuCheckItem	= new JCheckBoxMenuItem();
	protected final PropertiesReader			optionLocaleReader;
	protected final PropertiesReader			messageReader;
	protected final PropertiesReader			localizatioReader;
	private final Set <Integer>					changedRows		= new HashSet <>();
	private final Set <Long>					idsToDelete		= new HashSet <>();
	private boolean								listenModel		= true;
	private boolean								firstUse		= true;
	private final MercuryCrudActionPerformer	actionPerformer	= new MercuryCrudActionPerformer();

	public MercuryClientOption(String jndiName, OptionInternalFrame optionInternalFrame, String optionPrefix, PropertiesReader messageReader)
			throws NamingException, IOException
	{
		this.service = InitialContext.doLookup(jndiName);
		this.optionInternalFrame = optionInternalFrame;
		this.localizatioReader = new PropertiesReader(Properties.LOCALE_FILENAME);
		this.optionLocaleReader = new PropertiesReader(Properties.LOCALE_FILENAME, optionPrefix);
		this.messageReader = messageReader;
		setUp();
	}

	protected abstract Object[] getData (Dto dto);

	protected abstract Dto getDto (Object[] data)
			throws ValidationException;

	protected abstract String getNameForRow (int row);

	private void setUp ()
	{
		String optionName = optionLocaleReader.getProperty(Properties.Option.Element.PLURAL_NAME);
		ImageIcon icon = new ImageIcon(getClass().getResource(optionLocaleReader.getProperty(Properties.Option.Element.ICON)));

		optionInternalFrame.setFrameIcon(icon);
		optionInternalFrame.setTitle(optionName);

		optionButton.setText(optionName);
		optionButton.setIcon(icon);

		menuCheckItem.setText(optionName);

		optionInternalFrame.setUpListener(actionPerformer);
	}

	public void showMessageFromProperity (String message, String... addicionalInfo)
	{
		String messageToShow = messageReader.getProperty(message);
		for (String info : addicionalInfo)
		{
			if (info != null) messageToShow = messageToShow.concat("\n").concat(info);
		}

		JOptionPane.showMessageDialog(null, messageToShow);
	}

	public void setVisible (boolean visible)
	{
		optionInternalFrame.setVisible(visible);
		if (firstUse)
		{
			actionPerformer.onRefresh();
			firstUse = false;
		}
	}

	public JButton getOptionButton ()
	{
		return optionButton;
	}

	public OptionInternalFrame getInternalFrame ()
	{
		return optionInternalFrame;
	}

	public JCheckBoxMenuItem getMenuItem ()
	{
		return menuCheckItem;
	}

	private void tryConnect (Runnable action)
	{
		try
		{
			action.run();
		}

		catch (EJBAccessException e)
		{
			showMessageFromProperity(Properties.Message.LACK_OF_PERMISSION);
		}

		catch (RuntimeException e)
		{
			showMessageFromProperity(Properties.Message.SERVER_PROBLEM_OR_WRONG_CREDENTIALS);
		}
	}

	protected void actualize ()
	{}

	class MercuryCrudActionPerformer
		implements OptionListener
	{
		@Override
		public void onAdd ()
		{
			listenModel = false;
			optionInternalFrame.add();
			listenModel = true;
		}

		@Override
		public void onUpdate ()
		{
			tryConnect( () ->
			{
				Set <Long> deleted = new HashSet <>();
				idsToDelete.forEach(id ->
				{
					try
					{
						service.delete(id);
						deleted.add(id);
					}
					catch (DeletingException e1)
					{
						showMessageFromProperity(Properties.Message.COULD_NOT_DELETE);
					}
				});
				idsToDelete.removeAll(deleted);

				Set <Integer> saved = new HashSet <>();
				changedRows.forEach(row ->
				{
					try
					{
						service.save(getDto(optionInternalFrame.getRowData(row)));
						saved.add(row);
					}
					catch (SavingException e)
					{
						showMessageFromProperity(Properties.Message.COULD_NOT_SAVE, getNameForRow(row));
					}
					catch (NullPointerException | ValidationException e)
					{
						showMessageFromProperity(Properties.Message.VALIDATION_ERROR, getNameForRow(row), e.getMessage());
					}
				});
				changedRows.removeAll(saved);

				onRefresh();
			});
		}

		@Override
		public void onDelete ()
		{
			listenModel = false;
			int selectedRowIndex = optionInternalFrame.getSelectedRowIndex();
			if (selectedRowIndex != -1)
			{
				Long id = optionInternalFrame.getRowId(selectedRowIndex);
				optionInternalFrame.delete(selectedRowIndex);
				changedRows.forEach(row ->
				{
					if (row > selectedRowIndex)
					{
						changedRows.remove(row);
						changedRows.add(row - 1);
					}
				});
				if (id != null)
				{
					idsToDelete.add(id);
					optionInternalFrame.setUpdateButtonState(true);
				}
			}
			listenModel = true;
		}

		@Override
		public void onRefresh ()
		{
			tryConnect( () ->
			{
				listenModel = false;
				try
				{
					List <Dto> dtos = service.getList(optionInternalFrame.getSearchCriteria());
					Object[][] data = dtos.stream().map(MercuryClientOption.this::getData).toArray(Object[][]::new);
					optionInternalFrame.setRows(data);
					changedRows.clear();
					idsToDelete.clear();
					optionInternalFrame.setUpdateButtonState(false);
					actualize();
				}
				catch (RetrievingException e)
				{
					showMessageFromProperity(Properties.Message.COULD_NOT_RETRIEVE);
				}
				catch (ValidationException e)
				{
					showMessageFromProperity(Properties.Message.BAD_CRITERIA, e.getMessage());
				}
				finally
				{
					listenModel = true;
				}
			});
		}

		@Override
		public void onChange (TableModelEvent e)
		{
			if (listenModel)
			{
				changedRows.add(e.getFirstRow());
				optionInternalFrame.setUpdateButtonState(true);
			}
		}
	}
}
