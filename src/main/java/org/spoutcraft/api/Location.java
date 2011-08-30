package org.spoutcraft.api;

import org.spoutcraft.api.animation.Animateable;

public class Location implements Animateable{
	private double x,y,z;
	private float yaw,pitch;
	private World world;
	public Location(World world, double x, double y, double z) {
		// TODO Auto-generated constructor stub
	}

	public Location(World world, double x, double y, double z, float yaw, float pitch) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Animateable getValueAt(double p, Animateable startValue, Animateable endValue) {
		Location p1 = (Location)startValue;
		Location p2 = (Location)endValue;
		if(!p1.world.equals(p2.world)){
			return null;
		}
		double x = p1.x, y = p1.y, z = p1.z;
		float yaw = p1.yaw, pitch = p1.pitch;
		x += (p1.x - p2.x)*p;
		y += (p1.y - p2.y)*p;
		z += (p1.z - p2.z)*p;
		yaw += (p1.yaw - p2.yaw)*p;
		pitch += (p1.pitch - p2.pitch)*p;
		
		return new Location(p1.world, x,y,z,yaw,pitch);
	}
}
