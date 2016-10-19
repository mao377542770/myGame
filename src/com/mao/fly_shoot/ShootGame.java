package com.mao.fly_shoot;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
    /*游戏的主界面*/
public class ShootGame extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 400;        //背景宽
	public static final int HEIGHT = 654;		//背景高
	
	private static int aircraftRandom = 6;     //中型飞机生成几率 最大40
	private static int shootSpeed = 20;        //子弹发射频率      默认:25毫秒发射一次
	
	private int score = 0;                                //记录得分的
	private int state;                                //游戏状态

	/*初始化游戏状态*/
	public static final int START = 0;
	public static final int RUNNING = 1;
	public static final int PAUSE = 2;
	public static final int GAME_OVER =3;
	
	public static  BufferedImage background;
	public static  BufferedImage start;
	public static  BufferedImage gameover;
	public static  BufferedImage pause;
	public static  BufferedImage airplane;
	public static  BufferedImage aircraft;
	public static  BufferedImage bee;
	public static  BufferedImage hero0;
	public static  BufferedImage hero1;
	public static  BufferedImage bullet;
	public static  BufferedImage boss;
	
	static { // 静态代码块，初始化图片资源
		try {
			background = ImageIO.read(ShootGame.class.getResource("background.png"));
			start = ImageIO.read(ShootGame.class.getResource("start1.png"));
			airplane = ImageIO.read(ShootGame.class.getResource("airplane.png"));
			aircraft = ImageIO.read(ShootGame.class.getResource("aircraft.png"));
			bee = ImageIO.read(ShootGame.class.getResource("bee.png"));
			bullet = ImageIO.read(ShootGame.class.getResource("bullet.png"));
			hero0 = ImageIO.read(ShootGame.class.getResource("hero0.png"));
			hero1 = ImageIO.read(ShootGame.class.getResource("hero1.png"));
			boss = ImageIO.read(ShootGame.class.getResource("boss.png"));
			pause = ImageIO.read(ShootGame.class.getResource("pause.png"));
			gameover = ImageIO.read(ShootGame.class.getResource("gameover.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Hero hero = new Hero();          //创建英雄机对象
	public Bullet[] bullets = { };					  //创建子弹数组
	public FlyingObject[] flyings  = { };       //创建敌机和小蜜蜂对象 
	
				/*重写绘制paint方法*/
	public void paint(Graphics g) {
		g.drawImage(background, 0, 0, null); // 画图片
		paintHero(g);
		paintBullets(g);
		paintFlyingObjects(g);
		paintScore(g);
		paintState(g);
	}
			/*画英雄机*/
	public void paintHero(Graphics g) {
		g.drawImage(hero.image,hero.x,hero.y,null);
	}
			/*画子弹*/
	public void paintBullets(Graphics g) {
		for(int i = 0;i<bullets.length ;i++){
			Bullet b = bullets[i];
			g.drawImage(b.image,b.x,b.y,null);
		}
	}
	         /*画敌机和蜜蜂*/
	public void paintFlyingObjects(Graphics g) {
		for(int i=0;i<flyings.length;i++){
			FlyingObject f = flyings[i];
			g.drawImage(f.image,f.x,f.y,null);
		}
	}
			/*绘制游戏状态*/
	public void paintState(Graphics g){
		switch (state) {
		case START:
			g.drawImage(start,0,0,null);
			break;
		case PAUSE:
			g.drawImage(pause,0,0,null);
			break;
		case GAME_OVER:
			g.drawImage(gameover,0,0,null);
			break;
		}
	}
	public void paintScore(Graphics g){
		int x = 10;
		int y = 25;
		g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,15));
		g.setColor(new Color(0x002596));
		g.drawString("分数："+score,x , y);
		g.drawString("生命："+hero.getLife(), x, y+15);
	}
	public static void main(String[] args) {
		JFrame frame = new JFrame("-飞机大战-          o(∩_∩)o   猫猫");          //画框
		ShootGame game  = new ShootGame();	//面板
		frame.add(game);           //将面板加在画框上		
		frame.setSize(WIDTH, HEIGHT);                           
		frame.setAlwaysOnTop(false);                              //设置窗口总是在最前
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);     //设置窗口电X ，默认关闭程序
		frame.setLocationRelativeTo(null);    //设置相对位置
		frame.setVisible(true);	   //设置可见性,尽快调用 paint 方法
		game.action();                  //界面动起来
	}
	private Timer timer;   //定时器
	private int interval = 15;     //定时器的时间间隔（毫秒）
	public void action(){
		 //添加鼠标侦听事件
		MouseAdapter l = new MouseAdapter(){        //鼠标适配器

			public void mouseMoved(MouseEvent e){                 //重写该方法
				if (state == RUNNING) {
					int x = e.getX();
					int y = e.getY();
					hero.moveTo(x, y);
				}
			}
			public void mouseExited(MouseEvent e){
				if(state != GAME_OVER){
					state = PAUSE;
				}
			}
			public void mouseEntered(MouseEvent e){
				if(state == PAUSE){
					state = RUNNING;
				}
			}
			public void mouseClicked(MouseEvent e){             //重写鼠标点击方法
				switch(state){
				case START:
					state = RUNNING;
					break;
				case GAME_OVER:              //清场
					hero = new Hero();          //创建英雄机对象
					bullets = new Bullet[0];					  //创建子弹数组
					flyings  = new FlyingObject[0];       //创建敌机和小蜜蜂对象 
					score = 0;
					state =START;
					break;
				}
			}
		};     
		this.addMouseListener(l);
		this.addMouseMotionListener(l); // 处理鼠标滑动操作
		timer = new Timer();                  //创建定时器对象
		timer.schedule(new TimerTask(){
			public void run(){                  //定时执行的方法,重写run()
				if(state == RUNNING){
				enterAction();                  //调飞行物入场方法
				stepAction();					  //调飞行物走步方法
				shootAction();                //射击
				bangAction();                  //判断子弹是否打中
				outOfBoundsAction();         //越界处理
				checkGameOverAction();     //判断是否结束游戏 gameover
				}
				repaint();     }  //重绘（调用paint方法）
		}
		,interval,interval);         //1.定时触发要干的事，2.定时触发的间隔1，3定时触发的间隔
	}
	
	/*删除越界飞行物*/
	public void 	outOfBoundsAction(){          
		int index = 0;
		FlyingObject[] flyingLives = new FlyingObject[flyings.length];
		for(int i=0;i<flyings.length;i++){
			FlyingObject f = flyings[i];
			if(!flyings[i].outOfBounds()){
				flyingLives[index++] = f;
			}
		}
		flyings = Arrays.copyOf(flyingLives , index);
		/*删除越界子弹*/
		index = 0;
		Bullet[] bulletsLives = new Bullet[bullets.length];
		for(int i=0;i<bullets.length;i++){
			Bullet b = bullets[i];
			if(!bullets[i].outOfBounds()){
				bulletsLives[index++] = b;
			}
		}
		bullets = Arrays.copyOf(bulletsLives, index);
	}
	
	/*设计子弹方法*/
	int shootIndex = 0 ;                  //控制射击频率
	public void shootAction(){
		shootIndex ++;
		if(shootIndex % shootSpeed ==0){      //300毫秒发射一次
			Bullet[] bs = hero.shoot();             //英雄机发射的子弹
			bullets = Arrays.copyOf(bullets, bullets.length + bs.length);
			System.arraycopy(bs, 0, bullets, bullets.length - bs.length, bs.length);
		}
	}
	public void bangAction(){                  //子弹打飞行物
		for(int i=0; i<bullets.length;i++){
			Bullet b = bullets[i];
			if(bang(b)){                                //判断并删除被打到的飞机，并返回这颗子是否打中
				bullets[i] = bullets[bullets.length-1];
				bullets = Arrays.copyOf(bullets, bullets.length-1);      // 删除击中子弹
			}
		}
	}

	public boolean bang(Bullet b) {                                                  //碰撞检测 **
		int index = -1;                                                                        // 击中飞机的索引
		for (int i = 0; i < flyings.length; i++) {                                    // 遍历所有敌人
			FlyingObject obj = flyings[i]; // 得到每一个敌人
			if (obj.shootBy(b)) {
				index = i; // 记录被击中的飞机的下标
				break;
			}
		}
		if (index != -1) {                                                               // 有击中的敌人，并且index 就是被击中敌人的下标                     
			if (flyings[index].life == 1) {                                          //判断敌机生命为1  则执行以下判断
				FlyingObject one = flyings[index];
				flyings[index] = flyings[flyings.length - 1];
				flyings = Arrays.copyOf(flyings, flyings.length - 1);
				if (one instanceof Enemy) { // 判断有没有 敌人接口
					Enemy e = (Enemy) one; // 强转类型
					score += e.getScore();
				} else if (one instanceof Award) {
					Award a = (Award) one;                 
					int type = a.getType();
					switch (type) {
					case Award.DOUBLE_FIRE:
						hero.addDoubleFire(); // 双倍火力
						break;
					case Award.LIFE: // 加一条命
						hero.addLife();
						break;
					}
				}
			} else {
				flyings[index].life--;
				return true;
			}
			return true;
		}
		return false;
	}
	int flyEnteredIndex = 0;
	/*飞行物入场*/
	public void enterAction(){      //每10毫秒走一次
		flyEnteredIndex++;           //每走一次 enterActon +1
		if(flyEnteredIndex%40 == 0){  //10*40=400毫秒，每400毫秒进一次
			FlyingObject obj = nextOne();
			flyings = Arrays.copyOf(flyings, flyings.length+1);
			flyings[flyings.length-1] = obj;
		}
	}
	/**
	 * 随机生成飞行物
	 * @return 蜜蜂，小飞机，中飞机
	 */
	public static FlyingObject nextOne(){                    //*
		Random rand = new Random();
		int type = rand.nextInt(30);
		if(type == 0){
			return new Bee();                           //生成蜜蜂
		}else if(type>0 &&  type < aircraftRandom){            //生成中飞机
			return new Aircraft();
		}else if(type == 29){
			return new Boss();                              //生成Boss
		}
		else {
			return new Airplane();                  
		}
	}
	/*飞行物走步*/
	public void stepAction(){
		for(int i=0;i<flyings.length;i++){
			flyings[i].step(hero);
		}
		for(int i=0;i<bullets.length;i++){
			bullets[i].step(hero);
		}
		hero.step(hero);
	}
	public void checkGameOverAction(){                  //检查是否结束游戏
		if(isGameover()){             
			state = GAME_OVER;
		}
	}
	public boolean isGameover(){        //判断是否结束
		int index = -1;           //记录撞上的飞行物的坐标
		for(int i=0 ;i<flyings.length ;i++){
			if(hero.hit(flyings[i])){
				hero.substractLife();
				hero.setDoubleFire(0);
				index = i;
			}
			if(index !=-1){
				FlyingObject t =flyings[index];
				flyings[index] = flyings[flyings.length-1];
				flyings[flyings.length -1 ] = t;
				flyings = Arrays.copyOf(flyings, flyings.length -1);
			}
		}
		return hero.getLife()<= 0 ;
	}
}

