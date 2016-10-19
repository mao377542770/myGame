package com.mao.number2048;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * 2048游戏主程序
 * 
 * @author mao
 * 
 */
public class NumGame extends JPanel {
	private static final long serialVersionUID = 1L;
	public static int SCORE = 0; // 存放分数
	private static int gametime = 0; // 存放用时

	/* 存放界面宽高 */
	public static final int ROWS = 3;
	public static final int COLS = 3;
	/*
	 * 用于存放方格的尺寸
	 */
	public static final int CELLSIZE = 116; // 经自己测算的值，不要变动
	/*
	 * 存放游戏状态
	 */
	public static int state;
	public static final int RUNNING = 0;
	public static final int PAUSE = 1;
	public static final int GAME_OVER = 2;
	public static boolean flag = false; // 用来存放每次移动是否有效,以判断下次是否生成方块
	/*
	 * 存放图片静态变量
	 */
	static BufferedImage background;
	static BufferedImage cell2;
	static BufferedImage cell4;
	static BufferedImage cell8;
	static BufferedImage cell16;
	static BufferedImage cell32;
	static BufferedImage cell64;
	static BufferedImage cell128;
	static BufferedImage cell256;
	static BufferedImage cell512;
	static BufferedImage cell1024;
	static BufferedImage cell2048;

	static {
		try {
			background = ImageIO.read(Cell.class.getResource("background.png"));
			cell2 = ImageIO.read(Cell.class.getResource("cell2.png"));
			cell4 = ImageIO.read(Cell.class.getResource("cell4.png"));
			cell8 = ImageIO.read(Cell.class.getResource("cell8.png"));
			cell16 = ImageIO.read(Cell.class.getResource("cell16.png"));
			cell32 = ImageIO.read(Cell.class.getResource("cell32.png"));
			cell64 = ImageIO.read(Cell.class.getResource("cell64.png"));
			cell128 = ImageIO.read(Cell.class.getResource("cell128.png"));
			cell256 = ImageIO.read(Cell.class.getResource("cell256.png"));
			cell512 = ImageIO.read(Cell.class.getResource("cell512.png"));
			cell1024 = ImageIO.read(Cell.class.getResource("cell1024.png"));
			cell2048 = ImageIO.read(Cell.class.getResource("cell2048.png"));
		} catch (IOException e) {
			System.out.println("读取数据失败，文件可能缺失");
			System.exit(0);
		}
	}
	/*
	 * 初始化方格二维数组
	 */
	public static Cell[][] cells = new Cell[ROWS + 1][COLS + 1];

	/**
	 * 初始化方格二维数组
	 */
	public static void cellsInit(Cell[][] cells) {
		for (int i = 0; i <= ROWS; i++) {
			for (int j = 0; j <= COLS; j++) {
				cells[ROWS][COLS] = null;
			}
		}
	}

	/**
	 * ------------------------------------ 以下是业务逻辑
	 */
	public void paint(Graphics g) { // 重写原有JPanl 下的 paint（） 方法
		g.drawImage(background, 0, 0, null);
		g.translate(79, 45); // 坐标平移
		paintScore(g); // 画分数
		paintTime(g); // 画时间
		paintCell(g); // 画格子
	}

	/*
	 * 绘制分数和游戏时间
	 */
	private static final int FONT_COLOR = 0x994488; // 设置文字颜色
	public static final int FONT_SIZE = 40; // 设置字体大小

	private void paintScore(Graphics g) {
		int x = 540;
		int y = 110;
		g.setColor(new Color(FONT_COLOR));
		Font font = g.getFont();
		font = new Font(font.getName(), font.getStyle(), FONT_SIZE);
		g.setFont(font);
		String str = "" + SCORE;
		g.drawString(str, x, y);
	}

	private void paintTime(Graphics g) {
		int x = 540;
		int y = 270;
		g.setColor(new Color(FONT_COLOR));
		Font font = g.getFont();
		font = new Font(font.getName(), font.getStyle(), FONT_SIZE);
		g.setFont(font);
		String str = "" + gametime + "s";
		g.drawString(str, x, y);
	}

	private static Timer timer; // 定时器
	private int interval = 1000; // 定时器的时间间隔（毫秒）

	/**
	 * 绘制方格
	 */
	public void paintCell(Graphics g) {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (cells[i][j] != null) {
					Cell cell = cells[i][j];
					g.drawImage(cell.getImage(), i * CELLSIZE, j * CELLSIZE,
							null);
				}
			}
		}
	}

	/*
	 * main 方法
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame(
				"--2048小游戏--                    o(∩_∩)o     猫猫");
		NumGame game = new NumGame();
		frame.add(game);
		frame.setSize(745, 530);
		frame.setAlwaysOnTop(false);
		frame.setUndecorated(true); // 去掉边框
		frame.setLocationRelativeTo(null); // 设置相对位置为null
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true); // 显示窗口

		cellsInit(cells);     //初始化每个方格
		game.action();
	}

	/**
	 * action方法 用于控制整个游戏的运行
	 */
	private void action() {
		enterAction();
		enterAction(); // 写两次的原因是游戏初始时应该有 2 个
		state = RUNNING;
		/** 添加键盘按下事件 */
		KeyAdapter l = new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_Q || key == KeyEvent.VK_ESCAPE) {
					/**
					 * 弹出确认提示框
					 */
					int option = JOptionPane.showConfirmDialog(null,
							"是否确认结束游戏?", "结束游戏", JOptionPane.YES_NO_OPTION,
							JOptionPane.WARNING_MESSAGE, null);
					   switch (option) {
					   case JOptionPane.YES_NO_OPTION: {
						System.exit(0);// 强行关闭当前进程
				     	}
					}
				}
				switch (state) {
				case PAUSE:
					timer.cancel();
					if (key == KeyEvent.VK_C) {
						state = RUNNING;
						time();
					}
					break;
				case GAME_OVER:
					if (key != KeyEvent.VK_SPACE) {
						restartAction();// 游戏重新开始
						time();
						state = RUNNING;
					}
					break;
				case RUNNING:
					if (key == KeyEvent.VK_M) {
						state = PAUSE;
						timer.cancel();
						break;
					}
					switch (key) {
					case KeyEvent.VK_DOWN:
						softDropAction();
						dropAdd();
						nextPaint();
						overTest(nextPaint());
						break;
					case KeyEvent.VK_UP:
						softUpAction();
						upAdd();
						nextPaint();
						overTest(nextPaint());
						break;
					case KeyEvent.VK_RIGHT:
						moveRightAction();
						rightAdd();
						overTest(nextPaint());
						break;
					case KeyEvent.VK_LEFT:
						moveLeftAction();
						leftAdd();
						nextPaint();
						overTest(nextPaint());
						break;
					}
				}
				repaint();     //再画一次
			}
		};
		// 绑定到当前面板
		this.requestFocus();
		this.addKeyListener(l); // 添加侦听
		time();
	}
	
	private void time() {
		timer = new Timer(); // 创建定时器对象
		timer.schedule(new TimerTask() {
			public void run() { // 定时执行的方法,重写run()
				gametime += 1;
				repaint();
			} // 重绘（调用paint方法）
		}, interval, interval);
	}

	private boolean nextPaint() {
		// 当前一次和后一次没有发生任何变化时,不进行2和4的生成
		if (flag == true) {
			enterAction();
			flag = false;
			return false;            //不需要进行结束判断
		} 
		return true;               //需要进行结束判断 
	}

	// 游戏结束判断
	private void overTest(boolean f) {
		if (f == false) {
			int flag1 = 0;
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
					if (cells[i][j] != null) {
						flag1 += 1;
					}
				}
			}
			if (flag1 >= 16) {
				/**
				 * 弹出确认提示框
				 */
				int option = JOptionPane.showConfirmDialog(null,
						"游戏结束:您的得分为:\n      " + SCORE + "分\n ",
						"游戏结束", JOptionPane.YES_NO_OPTION,
						JOptionPane.WARNING_MESSAGE, null);
				switch (option) {
				case JOptionPane.YES_NO_OPTION:
					state  = PAUSE;
				}
			}
		}
	}

	/*
	 * 所有格子左移操作
	 */
	private void moveLeftAction() { // 左移去空
		int i, j, k;
		for (i = 1; i < 4; i++) {
			for (j = 0; j < 4; j++) {
				if (cells[i][j] != null) {
					k = i;
					while (k - 1 >= 0 && cells[k - 1][j] == null) {
						cells[k - 1][j] = Cell.getCell(cells[k][j].getNumber());
						cells[k][j] = null;
						k--;
						flag = true;
					}
				}
			}
		}
	}

	private void leftAdd() { // 左移合并,分数在此产生
		int i, j;
		for (i = 1; i < 4; i++) {
			for (j = 0; j < 4; j++) {
				if (cells[i][j] != null && cells[i - 1][j] != null
						&& cells[i][j].number == cells[i - 1][j].number) {
					cells[i - 1][j] = Cell.getCell(cells[i][j].number * 2);
					cells[i][j] = null;
					SCORE += cells[i - 1][j].number;
					flag = true;
				}
			}
		}
		moveLeftAction();
	}

	/*
	 * 所有格子右移操作
	 */
	private void moveRightAction() {
		int i, j, k;
		for (i = 2; i >= 0; i--) {
			for (j = 0; j < 4; j++) {
				if (cells[i][j] != null) {
					k = i;
					while (k + 1 <= 3 && cells[k + 1][j] == null) {
						cells[k + 1][j] = Cell.getCell(cells[k][j].getNumber());
						cells[k][j] = null;
						k++;
						flag = true;
					}
				}
			}
		}
	}

	private void rightAdd() {
		int i, j;
		for (i = 2; i >= 0; i--) {
			for (j = 0; j < 4; j++) {
				if (cells[i][j] != null && cells[i + 1][j] != null
						&& cells[i][j].number == cells[i + 1][j].number) {
					cells[i + 1][j] = Cell.getCell(cells[i][j].number * 2);
					cells[i][j] = null;
					SCORE += cells[i + 1][j].number;
					flag = true;
				}
			}
		}
		moveRightAction();
	}

	/*
	 * 所有格子下移操作
	 */
	
	private void softDropAction() {
		int i, j, k;
		for (j = 2; j >= 0; j--) {
			for (i = 0; i < 4; i++) {
				if (cells[i][j] != null) {
					k = j;
					while (k + 1 <= 3 && cells[i][k + 1] == null) {
						cells[i][k + 1] = Cell.getCell(cells[i][k].getNumber());
						cells[i][k] = null;
						k++;
						flag = true;
					}
				}
			}
		}
	}

	private void dropAdd() {
		int i, j;
		for (j = 2; j >= 0; j--) {
			for (i = 0; i < 4; i++) {
				if (cells[i][j] != null && cells[i][j + 1] != null
						&& cells[i][j].number == cells[i][j + 1].number) {
					cells[i][j + 1] = Cell.getCell(cells[i][j].number * 2);
					cells[i][j] = null;
					SCORE += cells[i][j + 1].number;
					flag = true;
				}
			}
		}
		softDropAction();
	}

	private void softUpAction() {
		int i, j, k;
		for (j = 1; j < 4; j++) {
			for (i = 0; i < 4; i++) {
				if (cells[i][j] != null) {
					k = j;
					while (k - 1 >= 0 && cells[i][k - 1] == null) {
						cells[i][k - 1] = Cell.getCell(cells[i][k].getNumber());
						cells[i][k] = null;
						k--;
						flag = true;
					}
				}
			}
		}
	}

	private void upAdd() {
		int i, j;
		for (j = 1; j < 4; j++) {
			for (i = 0; i < 4; i++) {
				if (cells[i][j] != null && cells[i][j - 1] != null
						&& cells[i][j].number == cells[i][j - 1].number) {
					cells[i][j - 1] = Cell.getCell(cells[i][j].number * 2);
					cells[i][j] = null;
					SCORE += cells[i][j - 1].number;
					flag = true;
				}
			}
		}
		softUpAction();
	}

	private void restartAction() {
		SCORE = 0;
		gametime = 0;
		flag = false;
		cells = new Cell[ROWS + 1][COLS + 1];
		cellsInit(cells);
		state = RUNNING;
		System.out.println(SCORE+","+gametime+","+state);
		enterAction();
		enterAction(); // 写两次的原因是游戏初始时应该有 2 个
		System.out.println("游戏重新开始!");
	}

	/**
	 * 调度方格入场
	 */
	private void enterAction() {
		randomCell();
	}

	/**
	 * 随机生成的格子,并让格子不与画面上的格子冲突
	 * 
	 * @param
	 */
	public void randomCell() {
		Random rand = new Random();
		int row, col;
		do {
			row = rand.nextInt(4);
			col = rand.nextInt(4);
		} while (cells[row][col] != null);
		Cell cell = Cell.getCell();
		cells[row][col] = cell;
	}

	public void repeatedAction() {
	}
}
