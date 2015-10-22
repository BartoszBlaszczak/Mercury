package pl.uz.mercury.view;

import javax.swing.JFrame;

import pl.uz.mercury.view.optioninternalframe.common.InternalFrame;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.BoxLayout;

@SuppressWarnings("serial")
public class MainWindow
	extends JFrame
{
	private final JToolBar			buttonsBar				= new JToolBar();
	private final JMenuBar			menuBar					= new JMenuBar();
	private final JMenu				menu					= new JMenu("menu");

	public MainWindow()
	{
		initialize();
	}

	private void initialize ()
	{
		setTitle("Mercury");
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		buttonsBar.setFloatable(false);
		getContentPane().add(buttonsBar);

		setJMenuBar(menuBar);
		menuBar.add(menu);
	}
	
	public void localize (String optionsName)
	{
		menu.setText(optionsName);
	}
	
	public void addMenuItem(JMenuItem menuItem)
	{
		menu.add(menuItem);
	}
	
	public void addInternalFrame(InternalFrame internalFrame)
	{
		getContentPane().add(internalFrame);
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
