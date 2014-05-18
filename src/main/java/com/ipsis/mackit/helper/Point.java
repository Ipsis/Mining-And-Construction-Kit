package com.ipsis.mackit.helper;

public class Point {

	public int x;
	public int y;
	public int z;
	
	public Point(int x, int y, int z) {
		
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void translate(int dx, int dy, int dz) {
		
		this.x += dx;
		this.y += dy;
		this.z += dz;
	}
	
	@Override
	public String toString() {
		
		return "(" + this.x + ", " + this.y + ", " + this.z + ")";
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if (obj == this)
			return true;
		
		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;
		
		Point p = (Point)obj;
		
		return (p.x == this.x && p.y == this.y && p.z == this.z);
	}
}
