package com.mao.tetris;

import java.awt.image.BufferedImage;
   /**单格*/
public class Cell {
	protected int row;     //行坐标
	protected int col;      //纵坐标
    protected BufferedImage image ;     //格子贴图
    
    public Cell(int row,int col , BufferedImage image){
    	this.row = row;
    	this.col = col;
    	this.image = image;
    }
    

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public BufferedImage getImage() {                  //返回格子图片
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}
    public void  drop(){
    	row++;
    }
    public void moveRight(){
    	col ++;
    }
    public void moveLeft(){
    	col --;
    }
    public  String toString(){
		return row+","+col;
    }
}

