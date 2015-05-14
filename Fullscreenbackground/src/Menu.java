import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Menu extends JFrame {
	
	private JButton launchButton;
	private JButton colorButton;
	private Component colorViewer;
	private JCheckBox showTaskLine;
	
	private Color color = Color.BLACK;
	
	private Frame fullScreen;
	
	public static void main(String [] args) {
		Menu frame = new Menu();
		frame.setVisible(true);
	}
	
	public Menu() {
		super("Fullscreen Utility");
		setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
		setPreferredSize(new Dimension(250, 220));
		readyFrame();
		loadComponents();
		setLocationRelativeTo(null);
		
		fullScreen = new Frame(false, color, this);
	}
	
	private void loadComponents() {
		JPanel title = createTitle();
		JPanel colorDesc = getColorDescPanel();
		showTaskLine = new JCheckBox("Show taskbar");
		colorViewer = new Component(230, 20, color);
		colorButton = new JButton("    Edit Color   ");
		launchButton = new JButton("Launch");
		
		colorButton.addActionListener(colorAction());
		launchButton.addActionListener(launch());
		
		launchButton.setPreferredSize(colorButton.getPreferredSize());
		
		add(title);
		add(colorDesc);
		add(showTaskLine);
		add(colorViewer);
		add(colorButton);
		add(launchButton);
		pack();
	}

	private ActionListener launch() {
		final JFrame parentFrame = this;
		return new AbstractAction() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				Component panel = fullScreen.component;
				boolean taskBar = showTaskLine.isSelected();
				panel.setColor(color);
				panel.
			}
		};
	}

	private void readyFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
	}
	
	private ActionListener colorAction() {
		return new AbstractAction() {
			public void actionPerformed(ActionEvent arg0) {
				Color c = JColorChooser.showDialog(null, "Choose color", color);
				if(c!=null) {
					color = c;
					colorViewer.setColor(color);
				}
			}
		};
	}
	
	private JPanel createTitle() {
		JTextArea heading = new JTextArea("Fullscreen Utility");
		JTextArea underheading = new JTextArea("V 1.1 by Windsec");
		
		heading.setFont(new Font("Veranda", 0 ,30));
		heading.setEditable(false);
		heading.setFocusable(false);
		heading.setBackground(new Color(0F, 0F, 0F, 0F));
		
		underheading.setFont(new Font("Veranda", 0, 10));
		underheading.setEditable(false);
		underheading.setFocusable(false);
		underheading.setBackground(new Color(0F, 0F, 0F, 0F));
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(240, 90));
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		panel.add(heading);
		panel.add(underheading);
		
		return panel;
	}
	
	private JPanel getColorDescPanel() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(120, 20));
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		
		JTextArea colorDesc = new JTextArea("Current color: ");
		
		colorDesc.setEditable(false);
		colorDesc.setFocusable(false);
		colorDesc.setFont(new Font("Veranda", 1, 15));
		colorDesc.setBackground(new Color(0F, 0F, 0F, 0F));
		
		panel.add(colorDesc);
		
		return panel;
		
	}

}
