package com.mao.tetris;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;    //用此类来定义 图片类型的对象
import java.io.IOException;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;         //使用ImageIO 来读取图片
import javax.swing.JFrame;				
import javax.swing.JPanel;



public class Tetris extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Cell[][] wall ;
	Tetromino tetromino ;		//正在下落的方块
	Tetromino nextOne;			//下一块是什么
	int lines;             //销毁行数
	int score;				//游戏分数
	public static final int ROWS = 20 ;
	public static final int COLS = 10;
	public static final int CELL_SIZE = 26;      //控制方格的尺寸
	/**
	 * 添加游戏状态，静态变量
	 */
	private int state;
	public static final int RUNNING = 0;
	public static final int PAUSE = 1;
	public static final int GAME_OVER = 2;
	/**速度*/
	private int speed =1000 ;
	/**难度级别*/
	private int level = 9;
	/**
	 * 在Tetris 类中添加定时器
	 */
	private Timer timer;
	
	 /**背景图片(使用静态变量来加载图片)*/
	private static BufferedImage background;
	private static BufferedImage gameover;
	private static BufferedImage pause;
	public static BufferedImage T;
	public static BufferedImage I;
	public static BufferedImage J;
	public static BufferedImage O;
	public static BufferedImage S;
	public static BufferedImage Z;
	public static BufferedImage L;
	
	 /**使用静态变量来加载图片*/
	static{
		try {
			/*在Tetris.class 同包下找到该图片*/
			background = ImageIO.read(Tetris.class.getResource("TETRIS.png"));
			gameover = ImageIO.read(Tetris.class.getResource("gameover.png"));
			pause = ImageIO.read(Tetris.class.getResource("pause.png"));
			T = ImageIO.read(Tetris.class.getResource("T.png"));
			I = ImageIO.read(Tetris.class.getResource("I.png"));
			J = ImageIO.read(Tetris.class.getResource("J.png"));
			O = ImageIO.read(Tetris.class.getResource("O.png"));
			S = ImageIO.read(Tetris.class.getResource("S.png"));
			Z = ImageIO.read(Tetris.class.getResource("Z.png"));
			L = ImageIO.read(Tetris.class.getResource("L.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void paint(Graphics g){           //重写原有JPanl 下的 paint（） 方法
		g.drawImage(background,0,0,null);
		g.translate(15,15);     //坐标平移
		paintWall(g);
		paintTetromino(g);
		paintNextOne(g);
		paintScore(g);         //绘制分数
		paintState(g);          //绘制游戏状态	
	}
	private void paintState(Graphics g){
		switch (state) {
		case PAUSE:
			g.drawImage(pause,-15,-15,null);
			break;
		case GAME_OVER:
			g.drawImage(gameover,-15,-15,null);
			break;
		}
	}
	public static void main(String[] args) {
		JFrame frame = new JFrame("--俄罗斯方块--                    o(∩_∩)o     猫猫");
		Tetris tetris = new Tetris();
		frame.add(tetris);
		frame.setSize(530,580);                        //设置窗口大小
		frame.setAlwaysOnTop(true);            //总在最上
	//	frame.setUndecorated(true);              //去掉边框
		frame.setLocationRelativeTo(null);			//设置相对位置为null
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);               //显示窗口
		tetris.action();
	}
	
	/**绘制分数所需要的FONT参数*/
	public static final int FONT_COLOR = 0x668800;
	public static final int FONT_SIZE = 25;
	
	public void paintScore(Graphics g){               //绘制分数
		int x = 290;
		int y =160;
		g.setColor(new Color(FONT_COLOR));
		Font font = g.getFont();               //取得g 当前字体
		font = new Font(font.getName(),font.getStyle(),FONT_SIZE);
		g.setFont(font);
		String str = "当前得分:"+score;
		g.drawString(str, x, y);
		y += 56;
		str = "清除行数:"+lines;
		g.drawString(str, x, y);
		y+= 56;
		g.drawString(" 级      别:"+level,x , y);
	}
	public void paintNextOne(Graphics g){          //绘制下一块方格
		if(nextOne == null){
			return;
		}
		//将每个格子的row，col 的坐标换算成 x,y 然后贴图
		Cell[] cells = nextOne.cells;
		for(int i = 0; i< cells.length ; i++){
			Cell cell = cells[i];
			int x = (cell.getCol()+10)*CELL_SIZE;  
			int y = (cell.getRow()+1)*CELL_SIZE;
			g.drawImage(cell.getImage(),x-1,y-1,null);
		}
	}
	public void paintTetromino(Graphics g){             //绘制正在下落的方格
		if(tetromino == null){
			return;
		}
		Cell[] cells = tetromino.cells;
		for(int i = 0; i< cells.length ; i++){
			Cell cell = cells[i];
			int x = cell.getCol()*CELL_SIZE;  
			int y = cell.getRow()*CELL_SIZE;
			g.drawImage(cell.getImage(),x-1,y-1,null);
		}
	}
	private void paintWall(Graphics g){                  //绘制界面
		for(int row = 0 ;row < wall.length ; row++){
			for(int col = 0;col < wall[row].length ; col++){
				Cell cell = wall[row][col];       //代表墙上的每个格子
				int x = col * CELL_SIZE;
				int y = row * CELL_SIZE;
				if(cell == null ){
					g.drawRect(x, y,CELL_SIZE, CELL_SIZE);
				}else{
					g.drawImage(cell.getImage(), x-1,y-1,null);            //x-1,y-1 是为了格子黑色边框不重叠，美观
				}
			}
		}
	}	
	public void startAction(){          //开始游戏
		
	}	
	public void action(){            //启动方法
		wall = new Cell[ROWS][COLS];
		tetromino = Tetromino.randomOne();     //调用静态方法，生成tetromino 对象		
		nextOne = Tetromino.randomOne();

		state = RUNNING;          //初始化游戏状态为RUNNING

		
		/**处理键盘按下事件，在按下按键的时候执行下路方法*/
		KeyAdapter l = new KeyAdapter(){
			//key 按键 pressed 按下
			public void keyPressed(KeyEvent e){
				int key = e.getKeyCode();
				if(key==KeyEvent.VK_Q){
					System.exit(0);//强行关闭当前进程
				}
				switch(state){
				case GAME_OVER:
					if(key==KeyEvent.VK_S){
						restartAction();//游戏重新开始
					}
					return;//提前结束方法,不再执行后续方法
				case PAUSE:
					if(key == KeyEvent.VK_C){
						state =RUNNING;
					}
					break;
				case RUNNING:
					if (key == KeyEvent.VK_P) {
						state = PAUSE;
					}
					switch (key) {
					case KeyEvent.VK_DOWN:
						softDropAction();
						break;
					case KeyEvent.VK_RIGHT:
						moveRightAction();
						break;
					case KeyEvent.VK_LEFT:
						moveLeftAction();
						break;
					case KeyEvent.VK_SPACE:
						hardDropAction();
						break;
					case KeyEvent.VK_UP:
						rotateRightAction();
						break;
					case KeyEvent.VK_Z:
						rotateRightAction();
						break;
					}
				}
				repaint();     //再画一次
			}
		};	
		//绑定时间到当前面板
		this.requestFocus();
		this.addKeyListener(l);
		
		//在action 方法中添加，定时计划任务
		timer = new Timer();
		TimerTask task = new TimerTask(){     //重写抽象类TimerTask 中的run()方法，并
			public void run(){
				if (level <= 9) {
					level = lines / 10;                         //每10行增加一个等级
				}
				if(state == RUNNING ){
					for(int i=0;i<=level/2;i++){         //没两个等级加一次速度
						softDropAction();
					}
				}
				repaint();
			}
		};
		timer.schedule(task,10,speed);    
		System.out.println(speed);
	}
	
	private boolean outOfBounds(){   //检测当前正在下路的方块是否出界

		Cell[] cells = tetromino.cells;
		for(int i=0;i<cells.length;i++){
			int col = cells[i].getCol();
			if(col <0 || col >=COLS){
				return true;                        //为true 表示出界了
			}
		}
		return false;
	}
	private boolean coincide(){           //检测正在下路的砖块是否与墙上的砖块重叠
		Cell[] cells = tetromino.cells;
		for(int i=0; i<cells.length ; i++){
			int row = cells[i].getRow();
			int col  = cells[i].getCol();
			//如果墙上有 row ， col 位置有格子就重叠了
			if(row >=0 && row < ROWS && col >= 0 && col <= COLS
					&& wall[row][col]!= null){
				return true;        //重叠
			}
		}
		return false;
	}
	public void moveRightAction(){     //向右移动的流程控制
		//尝试先向右移动，如果出界，则向左移动修正回来
		tetromino.moveRight();
		if(outOfBounds() || coincide()){        //如果为真表示超界限或者碰撞
			tetromino.moveLeft();             //移回来
		}
	}
	public void moveLeftAction(){        //向左移动的流程设置
		//尝试先向左移动，如果出界，则向右移动修正回来
		tetromino.moveLeft();
		if(outOfBounds() || coincide()){        //如果为真表示超界限或者碰撞
			tetromino.moveRight();             //移回来
		}
	}
	public void softDropAction(){        //下落流程控制
		if(canDrop()){
			tetromino.softDrop();
		}else{
			landIntoWall();                          //着落在墙上
			destoryLines();							//摧毁漫行
			if (isGameOver()) {
				state = GAME_OVER;
			} else {
				tetromino = nextOne;
				nextOne = Tetromino.randomOne(); // 产生随机方块
			}
		}
	}
	public void rotateRightAction(){                //旋转控制方法
		tetromino.rotateRight();
		if(outOfBounds() || coincide() ){
			tetromino.rotateLeft();
		}
	}
	/**根据一次删除的行数来计分*/
	private static int[] scoreTable = {0,1,10,50,100};
	
	public void destoryLines(){              //摧毁满行+计分
		int lines = 0;                               //控制一次删除几行
		for(int row = 0 ;row < wall.length; row++){
			if(fullCells(row)){
				deleteRow(row);
				lines++;
			}
		}
		this.score += scoreTable[lines];
		this.lines += lines;
	}
	private void deleteRow(int row){       //删行操作
		for(int i = row; i>=1 ;i--){
			System.arraycopy(wall[i-1], 0, wall[i], 0, COLS);
		}
		Arrays.fill(wall[0], null);
	}
	private boolean fullCells(int row){     //*检测这一行每个格子是否满了，如果满了返回true
		for(Cell cell : wall[row]){                 //相当于循环遍历那一行
			if(cell == null){
				return false;                               //如果有null返回false
			}
		}
		return true;
	}
	private void landIntoWall(){          //实现格子着落在墙上 
		Cell[] cells = tetromino.cells;
		for(int i=0;i<cells.length ;i++){
			Cell cell = cells[i];
			int row = cell.getRow();
			int col = cell.getCol();
			wall[row][col] = cell;
		}
	}
	private boolean canDrop(){                 //检测方块是否可以下落
		Cell[] cells = tetromino.cells;
		for(int i = 0 ; i<cells.length ;i++){
			int row = cells[i].getRow();
			if(row == ROWS - 1){
				return false;
			}
		}
		for(Cell cell:cells){            //java 5 之后可以使用的循环技巧
			int row = cell.getRow() + 1 ;
			int col = cell.getCol() ;
			if(row >= 0 && row < ROWS && col >= 0 && col <= COLS
					&& wall[row][col]!=null){
				return false;
			}
		}
		return true;
	}
	public void hardDropAction(){            //硬下落方式
		while (canDrop()){
			tetromino.softDrop();
		}
		landIntoWall();                          //着落在墙上
		destoryLines();							//摧毁漫行
		if (isGameOver()) {
			state = GAME_OVER;
		} else {
			tetromino = nextOne;
			nextOne = Tetromino.randomOne(); // 产生随机方块
		}
	}
	/**
	 * 重置游戏
	 */
	private void restartAction() {
		this.lines = 0;
		this.score = 0;
		this.wall = new Cell[ROWS][COLS];
		this.tetromino = Tetromino.randomOne();
		this.nextOne = Tetromino.randomOne();
		this.state = RUNNING;
		this.speed = 1000;
		this.level = 0;
	}
	/**
	 * 检测游戏是否结束
	 * 如果下一个方块没有出场位置，则游戏结束
	 *   -- 下一个出场方块每个格子行对应的墙上位置如果有格子，就游戏结束
	 * @return boolean
	 */
	private boolean isGameOver(){
		Cell[] cells = nextOne.cells;
		for(Cell cell : cells){
			int row = cell.getRow();
			int col = cell.getCol();
			if(wall[row][col] != null){
				return true;
			}
		}
		return false;
	}

}


















