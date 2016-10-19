 package com.mao.tetris;

import java.util.Arrays;
import java.util.Random;



                /**4方格*/
public class Tetromino {
	protected Cell[] cells = new Cell[4];
	/*  旋转状态序号 states[index%states.length]  */
	protected State[ ] states;
	protected int index = 10000;                  
	
	/**内部类*/
	protected class State{
		int row0,col0,row1,col1,
			row2,col2,row3,col3;
		public State(int row0, int col0, int row1, int col1, int row2, int col2,
				int row3, int col3) {
			this.row0 = row0;	this.col0 = col0;
			this.row1 = row1;	this.col1 = col1;
			this.row2 = row2; this.col2 = col2;
			this.row3 = row3;	this.col3 = col3;
		}
	}
	public void rotateRight(){    //右旋转
		//计算index++
		//获取Sn(4组数据 0 1 2 3)
		//获取当前的轴
		//格子0 是轴 不变
		//格子1 的行列变为: 轴 + Sn[1]
		//格子2 的行列变为: 轴 + Sn[2]
		//格子3 的行列变为: 轴 + Sn[3]
		index++;
		State s = states[index % states.length];
		Cell o = cells[0];
		int row = o.getRow();
		int col = o.getCol();
		cells[1].setRow(row + s.row1);
		cells[1].setCol(col + s.col1);
		cells[2].setRow(row + s.row2);
		cells[2].setCol(col + s.col2);
		cells[3].setRow(row + s.row3);
		cells[3].setCol(col + s.col3);
	}
	public void rotateLeft(){    //左旋转
		index--;
		State s = states[index % states.length];
		Cell o = cells[0];
		int row = o.getRow();
		int col = o.getCol();
		cells[1].setRow(row + s.row1);
		cells[1].setCol(col + s.col1);
		cells[2].setRow(row + s.row2);
		cells[2].setCol(col + s.col2);
		cells[3].setRow(row + s.row3);
		cells[3].setCol(col + s.col3);
	}
	public static Tetromino randomOne(){         //随机生成方格
		Random rand = new Random();
		int type = rand.nextInt(7);
		switch (type) {
		case 0:
			return new T();
		case 1:
			return new I();
		case 2:
			return new S();
		case 3:
			return new Z();
		case 4:
			return new J();
		case 5:
			return new L();
		case 6:
			return new O();
		}
		return null;
	}
	public void softDrop(){                  //下落操作
		for(int i=0;i<cells.length;i++){
			cells[i].drop();
		}
	}
	public void moveLeft(){               //左移动操作
		for(int i=0;i<cells.length;i++){
			cells[i].moveLeft();
		}
	}
	public void moveRight(){             //右移动操作
		for(int i=0;i<cells.length;i++){
			cells[i].moveRight();
		}
	}
	/*显示方格每块格子的信息*/
	public String toString(){
		return Arrays.toString(cells);
	}
}

                            /*格子7种形状类*/
class T extends Tetromino{
	public T(){
		cells[0] = new Cell(0,4,Tetris.T);			 //以这块为中心点
		cells[1] = new Cell(0,3,Tetris.T);                
		cells[2] = new Cell(0,5,Tetris.T);
		cells[3] = new Cell(1,4,Tetris.T);
		states = new State[4];
		states[0]=new State(0,0,0,-1,0,1,1,0);//S0
		states[1]=new State(0,0,-1,0,1,0,0,-1);//S1
		states[2]=new State(0,0,0,1,0,-1,-1,0);//S2
		states[3]=new State(0,0,1,0,-1,0,0,1);//S3
	}
}

class I extends Tetromino{
	public I(){
		cells[0] = new Cell(0,4,Tetris.I);			 //以这块为中心点
		cells[1] = new Cell(0,3,Tetris.I);                
		cells[2] = new Cell(0,5,Tetris.I);
		cells[3] = new Cell(0,6,Tetris.I);
		states = new State[2];
		states[0] = new State(0,0,0,-1,0,1,0,2);
		states[1] = new State(0,0,-1,0,1,0,2,0);
	}
}

class S extends Tetromino{
	public S(){
		cells[0] = new Cell(1,4,Tetris.S);			 //以这块为中心点
		cells[1] = new Cell(1,3,Tetris.S);                
		cells[2] = new Cell(0,4,Tetris.S);
		cells[3] = new Cell(0,5,Tetris.S);
		states = new State[2];
		states[0] = new State(0,0,0,-1,-1,0,-1,1);
		states[1] = new State(0,0,-1,0,0,1,1,1);
	}
}

class Z extends Tetromino{
	public Z(){
		cells[0] = new Cell(1,4,Tetris.Z);			 //以这块为中心点
		cells[1] = new Cell(0,3,Tetris.Z);                
		cells[2] = new Cell(0,4,Tetris.Z);
		cells[3] = new Cell(1,5,Tetris.Z);
		states = new State[2];
		states[0] = new State(0,0,-1,-1,-1,0,0,1);
		states[1] = new State(0,0,-1,1,0,1,1,0);
	}
}

class O extends Tetromino{
	public O(){
		cells[0] = new Cell(0,4,Tetris.O);			 //以这块为中心点
		cells[1] = new Cell(0,5,Tetris.O);                
		cells[2] = new Cell(1,4,Tetris.O);
		cells[3] = new Cell(1,5,Tetris.O);
		states = new State[2];
		states[0] = new State(0,0,0,1,1,0,1,1);
		states[1] = new State(0,0,0,1,1,0,1,1);
	}
}

class L extends Tetromino{
	public L(){
		cells[0] = new Cell(0,4,Tetris.L);			 //以这块为中心点
		cells[1] = new Cell(0,3,Tetris.L);                
		cells[2] = new Cell(0,5,Tetris.L);
		cells[3] = new Cell(1,3,Tetris.L);
		states = new State[4];
		states[0] = new State(0,0,0,1,0,-1,-1,1);
		states[1] = new State(0,0,1,0,-1,0,1,1);
		states[2] = new State(0,0,0,-1,0,1,1,-1);
		states[3] = new State(0,0,-1,0,1,0,-1,-1);
	}
}

class J extends Tetromino{
	public J(){
		cells[0] = new Cell(0,4,Tetris.J);			 //以这块为中心点
		cells[1] = new Cell(0,3,Tetris.J);                
		cells[2] = new Cell(0,5,Tetris.J);
		cells[3] = new Cell(1,5,Tetris.J);
		states = new State[4];
		states[0] = new State(0,0,0,-1,0,1,1,1);
		states[1] = new State(0,0,-1,0,1,0,1,-1);
		states[2] = new State(0,0,0,1,0,-1,-1,-1);
		states[3] = new State(0,0,1,0,-1,0,-1,1);
	}
}
