package com.mao.number2048;

import java.awt.image.BufferedImage;
import java.util.Random;
/**
 * 方格的父类
 * @author admin
 *
 */
public class Cell {
	protected int weight;
	protected int height;
	protected int number;            //存放数字
	protected BufferedImage image;

	/**
	 * 提供一个随机生成Cell2 和 Cell4的方法 
	 * @return Cell
	 */
	public static Cell getCell(){
		Random rand = new Random();
		int i = rand.nextInt(5);
		if (i == 4) {                //控制生成几率
			return new Cell4();
		} else {
			return new Cell2();
		}
	}
	
	public static Cell getCell(int number){
		switch (number) {
		case 2:
			return new Cell2();
		case 4:
			return new Cell4();
		case 8:
			return new Cell8();
		case 16:
			return new Cell16();
		case 32:
			return new Cell32();
		case 64:
			return new Cell64();
		case 128:
			return new Cell128();
		case 256:
			return new Cell256();
		case 512:
			return new Cell512();
		case 1024:
			return new Cell1024();
		case 2048:
			return new Cell2048();
		}
		return null;
	}
	/*
	 *基本get set方法 
	 * @return
	 */
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public BufferedImage getImage() {
		return image;
	}
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	public String toString() {
		return "Cell [ number=" + number + "]";
	}
}


/*
 * 存放Cell 的子类 2~2048
 * @author admin
 *
 */
class Cell2 extends Cell{
	 Cell2(){
		    number = 2;
		 	image = NumGame.cell2;
			this.weight = image.getWidth();
			this.height = image.getHeight();
	 }
}

class Cell4 extends Cell{
	 Cell4(){
			number = 4;
		 	image = NumGame.cell4;
			this.weight = image.getWidth();
			this.height = image.getHeight();
	 }
}

class Cell8 extends Cell{
	 Cell8(){
			number = 8;
		 	image = NumGame.cell8;
			this.weight = image.getWidth();
			this.height = image.getHeight();
	 }
}

class Cell16 extends Cell{
	 Cell16(){
			number = 16;
		 	image = NumGame.cell16;
			this.weight = image.getWidth();
			this.height = image.getHeight();
	 }
}

class Cell32 extends Cell{
	 Cell32(){
			number = 32;
		 	image = NumGame.cell32;
			this.weight = image.getWidth();
			this.height = image.getHeight();
	 }
}

class Cell64 extends Cell{
	 Cell64(){
			number = 64;
		 	image = NumGame.cell64;
			this.weight = image.getWidth();
			this.height = image.getHeight();
	 }
}

class Cell128 extends Cell{
	 Cell128(){
			number = 128;
		 	image = NumGame.cell128;
			this.weight = image.getWidth();
			this.height = image.getHeight();
	 }
}

class Cell256 extends Cell{
	 Cell256(){
			number = 256;
		 	image = NumGame.cell256;
			this.weight = image.getWidth();
			this.height = image.getHeight();
	 }
}

class Cell512 extends Cell{
	 Cell512(){
			number = 512;
		 	image = NumGame.cell512;
			this.weight = image.getWidth();
			this.height = image.getHeight();
	 }
}

class Cell1024 extends Cell{
	 Cell1024(){
			number = 1024;
		 	image = NumGame.cell1024;
			this.weight = image.getWidth();
			this.height = image.getHeight();
	 }
}

class Cell2048 extends Cell{
	 Cell2048(){
			number = 2048;
		 	image = NumGame.cell2048;
			this.weight = image.getWidth();
			this.height = image.getHeight();
	 }
}