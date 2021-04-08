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
import java.awt.Point;

/**
 * Used to save Point and Color into a List
 * @author Luiz H. Susin
 * @date 01/10/2018
 * @time 01:38 AM
 */
public class EvoPoint
{
	private Point position;
	private Color color;
	
	public EvoPoint(Point position, Color color)
	{
		this.position = position;
		this.color = color;
	}
	
	public Point getPosition()
	{
		return position;
	}
	
	public Color getColor()
	{
		return color;
	}
	
	public void setPosition(Point position)
	{
		this.position = position;
	}
	
	public void setColor(Color color)
	{
		this.color = color;
	}
}
