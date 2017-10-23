package com.engine;

import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder.VertexInfo;
import com.noise.OpenSimplexNoise;

public class GridThread extends Thread {
	
	private int gridMin = -25;
	private int gridMax = 25;
	private int scale = 1;				// These should have defaults incase something goes wrong
	
	public GridThread(int gridMin, int gridMax, int scale){
		this.gridMax = gridMax;
		this.gridMin = gridMin;
		this.scale = scale;
	}
	
	@Override
	public void run() {
		
		
		
	}
	
	public void render(MeshPartBuilder builder, OpenSimplexNoise noise, float offIncr, float size, float acceleration, int zS, int xS){
		float zoff = acceleration;
		for(int z = gridMin; z < 0; z += scale){
			float xoff = 0;
			for(int x = gridMin; x < 0; x += scale){
				zS = z+gridMin;
				xS = x+gridMin;
				builder.rect(new VertexInfo().setPos(xS, (float) (noise.eval(xoff, zoff)*size), zS),
							new VertexInfo().setPos(xS, (float) (noise.eval(xoff, zoff+offIncr)*size), zS+scale),
							new VertexInfo().setPos(xS+scale, (float) (noise.eval(xoff+offIncr, zoff)*size), zS),
							new VertexInfo().setPos(xS+scale, (float) (noise.eval(xoff+offIncr, zoff+offIncr)*size), zS+scale) );
				
				xoff += offIncr;
			}
			zoff += offIncr;
		}
	}
	
	public void threadSleep(){
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}