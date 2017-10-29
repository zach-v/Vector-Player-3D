package com.game.launcher.screens;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;

public class FreqScreen extends Screen{
	
	private FastFourierTransformer fft;
	
	private ScreenManager screenManager;
	private int columns = 0, rows = 0, scale=10;
	private int[] grid; private double[] noiseGrid; private ArrayList<Double> vals;
//	private OpenSimplexNoise noise = new OpenSimplexNoise();
	
	private File file;
	private FileReader fileReader;
	private BufferedReader bufferedReader;
	
	public void create(ScreenManager screenManager) {
		this.screenManager = screenManager;
		columns = screenManager.game().getWidth() / scale;
		rows = screenManager.game().getHeight() / scale;
		fft = new FastFourierTransformer(DftNormalization.STANDARD);
		grid = new int[columns];
		noiseGrid = new double[128];
		complex = new Complex[columns];
		
		vals = new ArrayList<Double>();
		
		for(int i=0; i < columns; i++){
			grid[i] = i*scale;
		}
	
		try {
			file = new File("res/output.txt");
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);
			
			populate(bufferedReader);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void populate(BufferedReader bufferedReader) {
		String line;
		while(true){
			try {
				line = bufferedReader.readLine();
				vals.add(((Double.parseDouble(line)*multiply)+(screenManager.game().getHeight()/2)));
			} catch(NullPointerException e){
				break;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	private int len = 0, multiply = 450, index = 0;
	
	Complex[] complex;
	
	public void update() {
		complex = fft.transform(noiseGrid, TransformType.FORWARD);
		
		for (int i=0; i < columns; i++)
			System.out.println("complexity shit: "+complex[i].getReal());
	}
	
	public void draw(Graphics2D g) {
		Color greyish = new Color(25,25,25);
		g.setColor(greyish);
		g.fillRect(0, 0, screenManager.game().getWidth(), screenManager.game().getHeight());	// Sets background
		g.setColor(Color.darkGray);
		g.setStroke(new BasicStroke(0.1f));
		//g.translate(0, screenManager.game().getHeight()/2);
		//g.rotate(Math.PI/4, screenManager.game().getWidth() / 2, screenManager.game().getHeight() / 2);
		
		for(int j=0; j < rows; j++){
			for(int i=0; i < columns; i++){
				g.drawRect(i*scale, j*scale, scale, scale);
				//g.drawLine(i*scale, j*scale, j*scale, i*scale);
			}
		}
		
		g.setColor(Color.white);
		g.setStroke(new BasicStroke(1f));
		
		for(int i=0; i < columns-1; i++){
			g.drawLine(i*scale, (int)noiseGrid[i], (i*scale)+scale, (int)noiseGrid[i+1]);
		}
	}
}