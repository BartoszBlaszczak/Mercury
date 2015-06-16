package pl.uz.mercury.view;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JFrame;

import pl.uz.mercury.view.optioninternalframe.common.MercuryClientOptionInternalFrame;
import pl.uz.mercury.view.optioninternalframe.common.MercuryOptionLocalization;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class MainWindow
	extends JFrame
{
	private final JToolBar			buttonsBar				= new JToolBar();
	private final JMenuBar			menuBar					= new JMenuBar();
	private final JMenu				menu					= new JMenu("menu");

	/**
	 * Create the application.
	 * 
	 * @throws IOException
	 */
	public MainWindow()
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws IOException
	 */
	private void initialize ()
	{
		setTitle("Mercury");
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));

		buttonsBar.setFloatable(false);
		getContentPane().add(buttonsBar, BorderLayout.NORTH);

		setJMenuBar(menuBar);
		menuBar.add(menu);
	}
	
	public void localize (MercuryOptionLocalization localization)
	{
		menu.setText(localization.options);
	}
	
	public void addMenuItem(JMenuItem menuItem)
	{
		menu.add(menuItem);
	}
	
	public void addInternalFrame(MercuryClientOptionInternalFrame internalFrame)
	{
		getContentPane().add(internalFrame, BorderLayout.CENTER);
	}
	
	public void addOptionButton (JButton button)
	{
		buttonsBar.add(button);
	}
	
	public void removeOptionButton (JButton button)
	{
		buttonsBar.remove(button);
		buttonsBar.repaint();
	}
}
