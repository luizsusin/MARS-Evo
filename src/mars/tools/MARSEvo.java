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
package mars.tools;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import mars.Globals;
import mars.mips.hardware.AccessNotice;
import mars.mips.hardware.MemoryAccessNotice;
import mars.tools.marsevo.EvoRunnable;
import mars.tools.marsevo.util.EvoPoint;

/**
 * @author Luiz H. Susin
 * @date 26/09/2018
 * @time 10:13 PM
 */
public class MARSEvo implements MarsTool, Observer
{
	/** Start Address used on MMIO */
	private static final int ADDR_STARTRANGE = 0xFFFF8010;
	/** Final Address used on MMIO */
	private static final int ADDR_ENDRANGE = 0xFFFF8090;

	/** Address used to set the heading of the robot */
	public static final int ADDR_HEADING = 0xFFFF8010;
	/** Address used to set the tracer on (1) and off (0) */
	public static final int ADDR_PAINT = 0xFFFF8020;
	/** Address used to get the current X position */
	public static final int ADDR_XPOS = 0xFFFF8030;
	/** Address used to get the current Y position */
	public static final int ADDR_YPOS = 0xFFFF8040;
	/** Address used to get if the robot should move */
	public static final int ADDR_MOVE = 0xFFFF8050;
	/** Address used to set the robot speed (25-1000) */
	public static final int ADDR_VEL = 0xFFFF8060;
	/** Address used to set the red color (0-255) from the RGB trace */
	public static final int ADDR_RCOLOR = 0xFFFF8070;
	/** Address used to set the green color (0-255) from the RGB trace */
	public static final int ADDR_GCOLOR = 0xFFFF8080;
	/** Address used to set the blue color (0-255) from the RGB trace */
	public static final int ADDR_BCOLOR = 0xFFFF8090;
	
	/** Minimum speed for ADDR_VEL */
	private static int minSpeed = 25;
	
	/** Max speed for ADDR_VEL */
	private static int maxSpeed = 1000;
	
	/** RGB integers for using on colored marking */
	private static int r = 0, g = 0, b = 0;
	
	/** Coordinates for the current position */
	private static int x = 0, y = 0;
	
	/** List of points to mark using EvoPoint */
	private static List<EvoPoint> evoPoints = Collections.synchronizedList(new ArrayList<EvoPoint>());
	
	/** Is the bot moving */
	private static boolean botMoving = false;
	
	/** Is the bot paiting */
	private static boolean botPainting = false;
	
	/** The bot heading */
	private static int botHeading = 0;
	
	/** Current speed of the bot */
	private static int speed = 25;
	
	public static EvoRunnable eInstance;
	
	/**
	 * @return The name of the tool
	 */
	@Override
	public String getName() 
	{
		return "MARS Evo";
	}
	
	/**
	 * @return The version of the tool
	 */
	public static String getToolVersion() 
	{
		return "v1.0";
	}
	
	/**
	 * A new color to mark with the RGB scale inside
	 * @return The Color with the RGB
	 * */
	public static Color getMarkColor()
	{
		return new Color(r, g, b);
	}
	
	/**
	 * @return The minimum speed
	 */
	public static int getMinSpeed()
	{
		return minSpeed;
	}
	
	/**
	 * @return The maximum speed
	 */
	public static int getMaxSpeed()
	{
		return maxSpeed;
	}
	
	/**
	 * @return The current speed
	 */
	public static int getSpeed()
	{
		return speed;
	}
	
	/**
	 * Using Point to specify the Cartesian position
	 * @return A Point with the position X and Y
	 */
	public static Point getPosition()
	{
		return new Point(x, y);
	}
	
	/**
	 * Using Point to specify the Cartesian position
	 */
	public static void setPosition(Point position)
	{
		x = (int) position.getX();
		y = (int) position.getY();
	}
	
	/**
	 * @return Is the bot moving?
	 */
	public static boolean isBotMoving()
	{
		return botMoving;
	}
	
	/**
	 * @return Is the bot paiting?
	 */
	public static boolean isBotPaiting()
	{
		return botPainting;
	}
	
	/**
	 * @return The bot heading
	 */
	public static int getBotHeading()
	{
		return botHeading;
	}
	
	/**
	 * Get the list of EvoPoints to mark
	 */
	public synchronized static List<EvoPoint> getEvoPoints()
	{
		return evoPoints;
	}

	/**
	 * Exhibits the tool's interface and start monitoring the memory addresses
	 */
	@Override
	public void action() 
	{
		try 
		{
			EvoRunnable eRun = new EvoRunnable();
			eInstance = eRun;
			
			eInstance.setVisible(true); 

			Thread t1 = new Thread(eRun);
			t1.start();

			Globals.memory.addObserver(this, ADDR_STARTRANGE, ADDR_ENDRANGE);
		} 
		catch (Exception e) { e.printStackTrace();}
	}

	/**
	 * Monitor the memory addresses and processes its requests on demand
	 */
	public void update(Observable o, Object arg) 
	{
		if (arg instanceof MemoryAccessNotice) 
		{
			MemoryAccessNotice notice = (MemoryAccessNotice) arg;
			int address = notice.getAddress();
			
			if (address < 0 && notice.getAccessType() == AccessNotice.WRITE) 
			{
				try
				{
					if (address == MARSEvo.ADDR_HEADING)
					{
						if (notice.getValue() >= 0 && notice.getValue() <= 360)
							MARSEvo.botHeading = notice.getValue();
						else Globals.memory.setWord(MARSEvo.ADDR_HEADING, 0);
					}
					
					if (address == MARSEvo.ADDR_VEL)
					{
						if (notice.getValue() >= MARSEvo.getMinSpeed() && notice.getValue() <= MARSEvo.getMaxSpeed())
						{
							MARSEvo.speed = notice.getValue();
							EvoRunnable.updateSpeedSettings();
						}
						else Globals.memory.setWord(MARSEvo.ADDR_VEL, MARSEvo.getMinSpeed());
					}
					
					if (address == MARSEvo.ADDR_MOVE)
					{
						if (notice.getValue() == 1)
							MARSEvo.botMoving = true;
						else MARSEvo.botMoving = false;
					}
					
					if (address == MARSEvo.ADDR_RCOLOR)
					{
						if (notice.getValue() >= 0 && notice.getValue() <= 255)
							MARSEvo.r = notice.getValue();
						else Globals.memory.setWord(MARSEvo.ADDR_RCOLOR, 0);
						
						EvoRunnable.updateColorSettings(MARSEvo.getMarkColor());
					}
					
					if (address == MARSEvo.ADDR_GCOLOR)
					{
						if (notice.getValue() >= 0 && notice.getValue() <= 255)
							MARSEvo.g = notice.getValue();
						else Globals.memory.setWord(MARSEvo.ADDR_GCOLOR, 0);
						
						EvoRunnable.updateColorSettings(MARSEvo.getMarkColor());
					}
					
					if (address == MARSEvo.ADDR_BCOLOR)
					{
						if (notice.getValue() >= 0 && notice.getValue() <= 255)
							MARSEvo.b = notice.getValue();
						else Globals.memory.setWord(MARSEvo.ADDR_BCOLOR, 0);
						
						EvoRunnable.updateColorSettings(MARSEvo.getMarkColor());
					}
					
					if (address == MARSEvo.ADDR_PAINT)
					{
						if (notice.getValue() == 1)
						{
							MARSEvo.botPainting = true;
							MARSEvo.getEvoPoints().add(new EvoPoint(MARSEvo.getPosition(), MARSEvo.getMarkColor()));
						}
						else MARSEvo.botPainting = false;
					}
				}
				catch(Exception e) { e.printStackTrace(); }
			}
		}
	}
}
