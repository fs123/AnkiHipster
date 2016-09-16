package ch.ivyteam.hipster.server.rest;

import ch.ivyteam.hipster.anki.message.Light;

public class LightInfo 
{
	public Light name;
	public boolean on;
	
	@Override
	public String toString() 
	{
		return "LightInfo [name="+name+", on="+on+"]";	
	}
}
