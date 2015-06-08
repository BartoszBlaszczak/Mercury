package pl.uz.mercury.view.datamodule.common;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JSplitPane;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class DataModule
	extends JFrame
{
	private JButton	cancelButton	= new JButton();
	private JButton	performButton	= new JButton();
	private JPanel	panel			= new JPanel();
	private JPanel	contentPane		= new JPanel();

	public DataModule(String cancelString, String performString)
	{
		cancelButton.setText(cancelString);
		performButton.setText(performString);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JSplitPane splitPane = new JSplitPane();
		splitPane.setEnabled(false);
		splitPane.setResizeWeight(0.5);
		contentPane.add(splitPane, BorderLayout.SOUTH);

		splitPane.setLeftComponent(cancelButton);
		splitPane.setRightComponent(performButton);

		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new MigLayout("", "[]", "[]"));
	}

	public static void main (String[] arguments)
	{
		new DataModule("oko", "maroko").setVisible(true);
	}

}
