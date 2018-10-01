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
package mars.tools.marsevo.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import javafx.geometry.Point2D;
import mars.tools.MARSEvo;

/**
 * @author Luiz H. Susin
 * @date 01/10/2018
 * @time 12:23 AM
 */
public class MarsEvoPanel extends JPanel 
{
	private static final long serialVersionUID = -3800239631962531334L;
	
	public MarsEvoPanel() { }

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
		
		for(EvoPoint evoPoint : MARSEvo.getEvoPoints())
		{
			Point2D evoPos = evoPoint.getPosition();
			
			g2.setColor(evoPoint.getColor());
			g2.fillRect((int) evoPos.getX(), (int) evoPos.getY(), 1, 1);
		}
		
		Point2D position = MARSEvo.getPosition();
		
		g2.setColor(Color.black);
		g2.fillRect((int) position.getX(), (int) position.getY(), 20, 20);
	}
}
