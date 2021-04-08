/**
 * MARS Evo
 * Copyright (C) 2018 Luiz H. Susin [https://github.com/luizsusin/MARS-Evo]
 *
 * Developed to be used with MARS (MIPS Assembler and Runtime Simulator)
 * Copyright (c) 2003-2013, Pete Sanderson and Kenneth Vollmar
 *
 * This program is a free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation version 3.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package mars.tools.marsevo;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Point;

import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.JSeparator;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import mars.Globals;
import mars.tools.MARSEvo;
import mars.tools.marsevo.util.EvoPoint;
import mars.tools.marsevo.util.MarsEvoPanel;

import javax.swing.JButton;
import javax.swing.JSlider;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * @author Luiz H. Susin
 * @date 30/09/2018
 * @time 02:20 PM
 */
public class EvoRunnable extends JFrame implements Runnable
{
	private static final long serialVersionUID = 4560947771695001753L;
	private JPanel contentPane;
	
	/** The workspace for MARS Evo */
	public static MarsEvoPanel workspace;
	/** The Speed label for monitoring */
	public static JLabel lblspeedpxsec;
	/** The Speed slider for controlling */
	public static JSlider speedSlider;
	/** The X Position label */
	public static JLabel lblXPos;
	/** The Y Position label */
	public static JLabel lblYPos;
	/** The RGB label */
	public static JLabel lblRGB;
	/** The panel with the color preview from the mark (trace) color */
	public static JPanel colorPreview;

	/**
	 * The frame and image processor
	 */
	public EvoRunnable() 
	{
		setTitle("MARS Evo " + MARSEvo.getToolVersion());
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setBounds(100, 100, 552, 720);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblMarsEvo = new JLabel("<html><b>MARS Evo</b><font size=\"2\"><i> " + MARSEvo.getToolVersion() + "</i></font>");
		lblMarsEvo.setHorizontalAlignment(SwingConstants.CENTER);
		lblMarsEvo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblMarsEvo.setBounds(12, 12, 512, 25);
		contentPane.add(lblMarsEvo);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.GRAY);
		separator.setBounds(12, 43, 512, 2);
		contentPane.add(separator);
		
		workspace = new MarsEvoPanel();
		workspace.setBackground(Color.WHITE);
		workspace.setDoubleBuffered(true);
		workspace.setBorder(new LineBorder(Color.GRAY));
		workspace.setBounds(12, 49, 512, 480);
		contentPane.add(workspace);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(Color.GRAY);
		separator_1.setBounds(12, 553, 512, 2);
		contentPane.add(separator_1);
		
		JLabel lblControls = new JLabel("Monitoring Tools");
		lblControls.setHorizontalAlignment(SwingConstants.CENTER);
		lblControls.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblControls.setBounds(12, 532, 512, 18);
		contentPane.add(lblControls);
		
		JPanel workspaceSettingsPanel = new JPanel();
		workspaceSettingsPanel.setBorder(new TitledBorder(new LineBorder(new Color(128, 128, 128), 1, true), "Workspace Settings", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(128, 128, 128)));
		workspaceSettingsPanel.setBounds(12, 553, 158, 100);
		contentPane.add(workspaceSettingsPanel);
		workspaceSettingsPanel.setLayout(null);
		
		JButton btnClearAndReset = new JButton("Clear and Reset");
		btnClearAndReset.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				MARSEvo.setPosition(new Point(0, 0));
				MARSEvo.getEvoPoints().clear();
				EvoRunnable.updatePositionSettings(new Point(0, 0));
				workspace.repaint();
			}
		});
		btnClearAndReset.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnClearAndReset.setBounds(12, 68, 134, 24);
		workspaceSettingsPanel.add(btnClearAndReset);
		
		lblXPos = new JLabel("<html><b>X Pos: </b>" + (int) MARSEvo.getPosition().getX() + "</html>");
		lblXPos.setHorizontalAlignment(SwingConstants.CENTER);
		lblXPos.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblXPos.setBounds(12, 20, 134, 16);
		workspaceSettingsPanel.add(lblXPos);
		
		lblYPos = new JLabel("<html><b>Y Pos: </b>" + (int) MARSEvo.getPosition().getY() + "</html>");
		lblYPos.setHorizontalAlignment(SwingConstants.CENTER);
		lblYPos.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblYPos.setBounds(12, 40, 134, 16);
		workspaceSettingsPanel.add(lblYPos);
		
		JPanel colorSettingsPanel = new JPanel();
		colorSettingsPanel.setBorder(new TitledBorder(new LineBorder(new Color(128, 128, 128), 1, true), "Color Settings", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(128, 128, 128)));
		colorSettingsPanel.setBounds(182, 553, 172, 100);
		contentPane.add(colorSettingsPanel);
		colorSettingsPanel.setLayout(null);
		
		lblRGB = new JLabel("<html><b>RGB: </b>[" + MARSEvo.getMarkColor().getRed() + ", " + MARSEvo.getMarkColor().getGreen() + ", " + MARSEvo.getMarkColor().getBlue() + "]</html>"); // Update
		lblRGB.setHorizontalAlignment(SwingConstants.CENTER);
		lblRGB.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblRGB.setBounds(12, 20, 148, 16);
		colorSettingsPanel.add(lblRGB);
		
		JLabel lblColorResult = new JLabel("<html><b>Color Result:</b></html>");
		lblColorResult.setHorizontalAlignment(SwingConstants.CENTER);
		lblColorResult.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblColorResult.setBounds(12, 40, 148, 16);
		colorSettingsPanel.add(lblColorResult);
		
		colorPreview = new JPanel();
		colorPreview.setBackground(MARSEvo.getMarkColor()); // Update
		colorPreview.setBorder(new LineBorder(new Color(128, 128, 128)));
		colorPreview.setBounds(12, 60, 148, 28);
		colorSettingsPanel.add(colorPreview);
		
		JPanel speedSettingsPanel = new JPanel();
		speedSettingsPanel.setBorder(new TitledBorder(new LineBorder(new Color(128, 128, 128), 1, true), "Speed Settings", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(128, 128, 128)));
		speedSettingsPanel.setBounds(366, 553, 158, 100);
		contentPane.add(speedSettingsPanel);
		speedSettingsPanel.setLayout(null);
		
		lblspeedpxsec = new JLabel("<html><b>Speed: </b>" + MARSEvo.getMinSpeed() + "px/sec</html>"); // Update
		lblspeedpxsec.setHorizontalAlignment(SwingConstants.CENTER);
		lblspeedpxsec.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblspeedpxsec.setBounds(12, 20, 134, 16);
		speedSettingsPanel.add(lblspeedpxsec);
		
		JLabel lblspeedControler = new JLabel("<html><b>Speed Controler:</html>");
		lblspeedControler.setHorizontalAlignment(SwingConstants.CENTER);
		lblspeedControler.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblspeedControler.setBounds(12, 40, 134, 16);
		speedSettingsPanel.add(lblspeedControler);
		
		speedSlider = new JSlider();
		speedSlider.setValue(MARSEvo.getMinSpeed());
		speedSlider.setMinimum(MARSEvo.getMinSpeed());
		speedSlider.setMaximum(MARSEvo.getMaxSpeed());
		speedSlider.setForeground(Color.GRAY);
		speedSlider.setBounds(12, 60, 134, 30);
		speedSettingsPanel.add(speedSlider);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setForeground(Color.GRAY);
		separator_2.setBounds(0, 662, 542, 2);
		contentPane.add(separator_2);
		
		JLabel lblMarsEvoCredits = new JLabel("<html><b>MARS Evo " + MARSEvo.getToolVersion() + "</b> - Developed by Luiz H. Susin (<a href=\"https://github.com/luizsusin\" target=\"_blank\">@luizsusin</a>)</html>");
		lblMarsEvoCredits.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblMarsEvoCredits.setHorizontalAlignment(SwingConstants.CENTER);
		lblMarsEvoCredits.setBounds(12, 664, 512, 16);
		contentPane.add(lblMarsEvoCredits);
	}

	/**
	 * Updates informations on demand
	 */
	@Override
	public void run() 
	{
		double headingAngle;
		Point position;
		int posX, posY;
		
		while(true)
		{
			synchronized(this)
			{
				if(MARSEvo.isBotMoving())
				{
					try
					{
						headingAngle = ((360 - MARSEvo.getBotHeading()) + 90) % 360;
						position = MARSEvo.getPosition();
						
						posX = (int) (position.getX() + Math.cos(Math.toRadians(headingAngle)));
						posY = (int) (position.getY() - Math.sin(Math.toRadians(headingAngle)));
						
						Globals.memory.setWord(MARSEvo.ADDR_XPOS, posX);
						Globals.memory.setWord(MARSEvo.ADDR_YPOS, posY);
						
						MARSEvo.setPosition(new Point(posX, posY));
						updatePositionSettings(position);
						
						if(MARSEvo.isBotPaiting())
							MARSEvo.getEvoPoints().add(new EvoPoint(MARSEvo.getPosition(), MARSEvo.getMarkColor()));
						
						workspace.repaint();
						
						Thread.sleep(1000 / speedSlider.getValue());
					}
					catch(Exception e) { e.printStackTrace(); }
				}
				
				updateSpeedLocally();
			}
		}
	}
	
	/**
	 * Updates the position of the MARS Evo
	 * @param position (Point2D)
	 */
	public static void updatePositionSettings(Point position)
	{
		lblXPos.setText("<html><b>X Pos: </b>" + (int) position.getX() + "</html>");
		lblYPos.setText("<html><b>Y Pos: </b>" + (int) position.getY() + "</html>");
	}
	
	/**
	 * Updates the color settings of the MARS Evo
	 * @param color (Color)
	 */
	public static void updateColorSettings(Color color)
	{
		lblRGB.setText("<html><b>RGB: </b>[" + color.getRed() + ", " + color.getGreen() + ", " + color.getBlue() + "]</html>");
		colorPreview.setBackground(color);
	}
	
	/**
	 * Updates the speed of the MARS Evo
	 * @param speed (Integer)
	 */
	public static void updateSpeedSettings()
	{
		int speed = MARSEvo.getSpeed();
		
		lblspeedpxsec.setText("<html><b>Speed: </b>" + speed + "px/sec</html>");
		speedSlider.setValue(speed);
	}
	
	/**
	 * Updates only the label of the MARS Evo
	 */
	public static void updateSpeedLocally()
	{
		lblspeedpxsec.setText("<html><b>Speed: </b>" + speedSlider.getValue() + "px/sec</html>");
	}
}
