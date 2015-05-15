package greenfootAdditions;

import greenfoot.GreenfootImage;

public class GreenfootImageHelp {
	public static GreenfootImage scale(GreenfootImage gi_Image,int i_width,int i_height)
	{
	    java.awt.Image image_=gi_Image.getAwtImage().getScaledInstance(i_width,i_height,java.awt.Image.SCALE_SMOOTH);
	 
	    gi_Image=new GreenfootImage(i_width,i_height);
	    gi_Image.getAwtImage().createGraphics().drawImage(image_,0,0,null);
	    return gi_Image;
	}
}
