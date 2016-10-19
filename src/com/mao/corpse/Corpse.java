package com.mao.corpse;


import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;


/**
 * 休闲策略游戏 《行尸走肉》 
 * 关于游戏： 
 * 1.游戏主要目标是人物牺牲前到达50km外的幸存者基地 
 * 2.菜单介绍 
 * 物品（使用包裹里物品） 
 * 休息（增加体力 但是饥饿饮水减少） 
 * 搜查（可能搜到物品和遇到行尸 消耗体力饥饿饮水） 
 * 前进（会遇到各种突发事件 每次前进减少1km距离 消耗体力饥饿饮水） 
 * 3.属性介绍
 * 体力（进行一些行动会消耗体力 体力为0人物会昏迷 降低健康） 
 * 健康（健康为0人物死亡 游戏结束） 
 * 饥饿（饥饿为0会消耗健康） 
 * 饮水（饮水为0会消耗健康）
 * 近战（对战近战时的攻击力） 
 * 远程（对战远程时的攻击力） 
 * 护甲（防御能力） 
 * PS：使用物品体力饥饿饮水最多提到100
 * 一些需要输入的地方尽量按着提示输入
 * 
 * 更新：
 * 1.添加存档功能
 * 2.优化代码
 * 3.增加遭遇行尸逃走体力惩罚
 * 4.修复某些概率事件必然触发的bug
 * @author JSD1411
 * @version 1.1
 */
public class Corpse {
static int t;// 时间
static int s;// 距离
static int len;// 昵称长度
static String name;// 昵称
static int tl;// 体力
static int jk;// 健康
static int je;// 饥饿
static int ys;// 饮水
static int jz;// 近战
static int yc;// 远程
static int hj;// 护甲
static int jzzb;// 近战装备编号
static int yczb;// 远程装备编号
static int hjzb;// 护甲装备编号
static String[] wp = new String[18];// 物品名称
static int[] wps = new int[18];// 物品数量
static int gf;// 行尸攻击方式（近战 远程）
static int hp;// 行尸体力
static int g;// 行尸攻击
static int f;// 行尸防御
static Scanner sca = new Scanner(System.in);
static int[] index = new int[18];// 用于临时物品显示


public static void main(String[] args) throws InterruptedException,
IOException {
int x;// 用于输入
// 物品名称赋值
wp[0] = "无";
wp[1] = "饮用水";
wp[2] = "食品";
wp[3] = "药物";
wp[4] = "肾上腺素";
wp[5] = "维生素";
wp[6] = "拳套";
wp[7] = "匕首";
wp[8] = "棒球棍";
wp[9] = "电锯";
wp[10] = "气枪";
wp[11] = "手弩";
wp[12] = "左轮手枪";
wp[13] = "冲锋枪";
wp[14] = "布衣";
wp[15] = "马甲";
wp[16] = "皮大衣";
wp[17] = "迷彩服";
// 物品初始化
for (int i = 1; i <= 17; i++) {
wps[i] = 0;
}
wps[1] = 2;
wps[2] = 2;
// 游戏版本显示
System.out.println("欢迎试玩《行尸走肉》ver1.1！请选择：(1.读取存档 2.新游戏)");
// 系统选择
File file = new File("XSZRsave.dat");
switch (sca.nextInt()) {
case 1:// 读档
if (file.exists()) {
RandomAccessFile load = new RandomAccessFile("XSZRsave.dat", "r");
t = load.read();
s = load.read();
len = load.read();
byte[] loadbuf = new byte[len];
load.read(loadbuf);
name = new String(loadbuf);
tl = load.read();
jk = load.read();
je = load.read();
ys = load.read();
jz = load.read();
yc = load.read();
hj = load.read();
jzzb = load.read();
yczb = load.read();
hjzb = load.read();
for (int i = 1; i <= 17; i++) {
wps[i] = load.read();
}
System.out.println("存档读取成功！");
load.close();
break;
} else {
System.out.println("存档不存在，请重新开始游戏！");
}
case 2:// 新游戏
// 属性初始化
t = 1;
s = 50;
tl = 100;
jk = 100;
je = 100;
ys = 100;
jz = 2;
yc = 2;
hj = 0;
jzzb = 0;
yczb = 0;
hjzb = 0;
System.out.println("请输入你的昵称：(输入Rick,Daryl或Michonne会有额外奖励!)");
name = sca.next();
len = name.length();
if (name.equals("Rick")) {
wps[1] += 10;
wps[2] += 10;
wps[3] += 10;
}
if (name.equals("Daryl")) {
wps[11] += 1;
}
if (name.equals("Michonne")) {
wps[7] += 1;
}
// 剧情介绍
System.out
.println("    这是一个丧尸横行的世界，丧尸是怎么爆发的？我不知道，我只知道要拼命活下去。我的故乡亚特兰大市早已沦陷称为死城，");
System.out
.println("丧尸在此占有绝对的统治地位，我必须在我身上的食物吃完之前赶往50km外的幸存者营地，希望到那之前，一切都不会太糟。");
Thread.sleep(1000);
}
// 开始游戏
OK: while (true) {
System.out.println();
System.out.println("游戏时间:【" + t + "天】 剩余距离:【" + s + "km】");
System.out.println("昵称:" + name);
System.out.println("体力:" + tl);
System.out.println("健康:" + jk);
System.out.println("饥饿:" + je);
System.out.println("饮水:" + ys);
System.out.println("近战:" + jz + "   装备:" + wp[jzzb]);
System.out.println("远程:" + yc + "   装备:" + wp[yczb]);
System.out.println("护甲:" + hj + "   装备:" + wp[hjzb]);
System.out.println();
System.out.println("请选择行动：(1.物品 2.休息 3.搜查 4.前进)");
switch (sca.nextInt()) {
case 1:
// 物品遍历
for (int i = 1, j = 1; i <= 17; i++) {
if (wps[i] != 0) {
index[j] = i;
System.out.println(j + "." + wp[i] + "*" + wps[i]);
j++;
}
}
System.out.println("0.返回");
System.out.println("请输入要使用的物品编号：");
while (true) {
x = sca.nextInt();
if (x == 0) {// 返回上一层
break;
} else {
if (wps[index[x]] == 0) {// 判断物品数量是否为0
System.out.println("没有物品，请重新输入！");
} else {// 执行物品效果
switch (index[x]) {
case 1:
ys += 30 + (int) (Math.random() * 11);// 30~40
if (ys >= 100) {
ys = 100;
}
System.out.println("使用饮用水，饮水增加！");
break;
case 2:
je += 30 + (int) (Math.random() * 11);// 30~40
if (je >= 100) {
je = 100;
}
System.out.println("使用食物，饥饿增加！");
break;
case 3:
jk += 20 + (int) (Math.random() * 11);// 20~30
if (jk >= 100) {
jk = 100;
}
System.out.println("使用药品，健康增加！");
break;
case 4:
tl += 40 + (int) (Math.random() * 21);// 40~60
jk -= 15 + (int) (Math.random() * 11);// 15~25
if (tl >= 100) {
tl = 100;
}
System.out.println("使用肾上腺素，体力增加！健康减少！");
break;
case 5:
jk += 40 + (int) (Math.random() * 11);// 40~50
if (jk >= 100) {
jk = 100;
}
System.out.println("使用维生素，健康增加！");
break;
case 6:
case 7:
case 8:
case 9:
if (jzzb != 0) {// 武器卸下
jz -= jzzb - 2;// 武器攻击力=武器编号-2
wps[jzzb]++;// 武器数量+1
}
jzzb = index[x];// 装备新武器
jz += jzzb - 2;
System.out.println("装备成功！");
break;
case 10:
case 11:
case 12:
case 13:
if (yczb != 0) {
yc -= yczb - 6;
wps[yczb]++;
}
yczb = index[x];
yc += yczb - 6;
System.out.println("装备成功！");
break;
case 14:
case 15:
case 16:
case 17:
if (hjzb != 0) {
hj -= hjzb - 13;
wps[hjzb]++;
}
hjzb = index[x];
hj += hjzb - 13;
System.out.println("装备成功！");
break;
}
wps[index[x]]--;
break;
}
}
}
break;
case 2:// 休息事件
x = (int) (Math.random() * 10);
if (x >= 0 && x < 5) {// 概率5/10
tl += 40;
System.out.println("你获得了充足的休息，体力+40！");
}
if (x >= 5 && x < 8) {// 概率3/10
tl += 15;
System.out.println("附近行尸的叫声很大，你只简单的小睡一会，体力+15！");
}
if (x >= 8) {// 概率2/10
tl -= 10;
System.out.println("你正在休息，忽然一大波行尸闯了进来，你仓皇逃走，体力-10！");
}
je -= 10 + (int) (Math.random() * 11);// 10~20
ys -= 10 + (int) (Math.random() * 11);// 10~20
gameover();
if (jk <= 0) {// 健康归零，游戏结束
break OK;
}
break;
case 3:// 搜查事件
x = (int) (Math.random() * 14);
if (x >= 0 && x < 4) {// 概率4/14
System.out.println("你搜查了半天，什么也没有发现！");
}
if (x >= 4 && x < 8) {// 概率4/14
getzbwp();
}
if (x >= 8 && x < 9) {// 概率1/14
xs();
if (jk <= 0) {// 健康归零，游戏结束
break OK;
}
}
if (x >= 9 && x < 12) {// 概率3/14
getwp();
}
if (x >= 12 && x < 14) {// 概率2/14
switch ((int) (Math.random() * 3)) {
case 0:
hj += 1;
System.out.println("你在体操馆里找到一本《防守技巧大全》，护甲增加！");
break;
case 1:
jz += 2;
System.out.println("你在搏击俱乐部找到一本《搏击技巧指南》，近战增加！");
break;
case 2:
yc += 2;
System.out.println("你在一辆车的后备箱里找到一本《枪械使用详解》，远程增加！");
break;
}
}
// 搜查消耗 5~15
tl -= 5 + (int) (Math.random() * 11);
je -= 5 + (int) (Math.random() * 6);
ys -= 5 + (int) (Math.random() * 6);
gameover();
if (jk <= 0) {// 健康归零，游戏结束
break OK;
}
break;
case 4:// 前进事件
x = (int) (Math.random() * 15);
if (x >= 0 && x < 1) {// 概率1/15
System.out.println("你偶然发现了一个幸存者扔下的背包，打开发现很多有用的物品！");
getwp();
getwp();
getzbwp();
}
if (x >= 1 && x < 2) {// 概率1/15
System.out
.println("你在路上发现一伙不怀好意的幸存者气势汹汹，他们要求你交出武器，是否交出：(1.交 2.不交)");
switch (sca.nextInt()) {
case 1:
if (jzzb != 0) {// 武器卸下
jz -= jzzb - 2;
}
if (yczb != 0) {
yc -= yczb - 6;
}
System.out.println("你听话的交出了装备的武器！");
break;
case 2:
if ((int) (Math.random() * 2) == 0) {
System.out
.println("你拒绝交出自己的武器，他们虽然人多但看你也不好惹，转身愤愤离去了！");
} else {
tl -= 10 + (int) (Math.random() * 11);
System.out
.println("你拒绝交出自己的武器，激怒了他们！发生火拼，但你凭借敏捷的身手逃走！体力减少！");
}
}
}
if (x >= 2 && x < 5) {// 概率3/15
switch ((int) (Math.random() * 5)) {
case 0:
case 1:
s -= 2;
System.out.println("一路上平安无事，前进了3km！");
break;
case 2:
case 3:
s -= 5;
System.out.println("精神状态不错，前进了6km！");
break;
case 4:
s -= 9;
System.out.println("精神状态大好，暴走了10km！");
break;
}
}
if (x >= 5 && x < 8) {// 概率3/15
xs();
if (jk <= 0) {// 健康归零，游戏结束
break OK;
}
}
if (x >= 8 && x < 9) {// 概率1/15
System.out.println("你在街道遇到一个被行尸包围的幸存者，是否施救：（1.是 2.否）");
switch (sca.nextInt()) {
case 1:
switch ((int) (Math.random() * 2)) {
case 0:
System.out.println("你来晚了一步，幸存者已被行尸感染！你及时逃离了街道！");
break;
case 1:
System.out
.println("你英勇的成功救助了幸存者！幸存者为了感谢你，分给你一些物品！");
getwp();
break;
case 2:
System.out
.println("你赶到发现幸存者已被感染！行尸发现了你，你仓皇逃走的过程中似乎掉了什么东西！");
losewp();
break;
}
break;
case 2:
System.out.println("你顶着良心的谴责，绕过了街道！");
break;
}
}
if (x >= 9 && x < 10) {// 概率1/15
ys += 30;
x = 1 + (int) (Math.random() * 3);
wps[1] += x;
System.out.println("你在野外发现了一些露水，并在此过夜！饮水增加！并获得饮用水！");
}
if (x >= 10 && x < 11) {// 概率1/15
je += 30;
x = 1 + (int) (Math.random() * 3);
wps[2] += x;
System.out.println("你在废弃超市发现了一些还能吃的压缩饼干，并在此过夜！饥饿增加！并获得食物！");
}
if (x >= 11 && x < 12) {// 概率1/15
x = 1 + (int) (Math.random() * 2);
wps[3] += x;
System.out.println("你在一家被行尸占领的医院发现了一些药品，并在此过夜！");
}
if (x >= 12 && x < 13) {// 概率1/15
jz += 2;
yc += 2;
hj++;
System.out
.println("你来到一个破旧的体育馆，为了应对以后，苦练一天！近战增加！远程增加！护甲增加！");
}
if (x >= 13 && x < 14) {// 概率1/15
System.out
.println("你遇到大批行尸迁移，该怎么办：(1.悄悄绕开 2.躲在废弃卡车下 3.抹腐尸的血扮行尸)");
switch (sca.nextInt()) {
case 1:
if ((int) (Math.random() * 2) == 0) {
System.out.println("你悄悄绕开行尸群，躲避成功！");
} else {
tl -= 15 + (int) (Math.random() * 11);
System.out
.println("你打算绕开行尸群，忽然被一只行尸发现，你费力逃走！体力大幅度减少！");
gameover();
if (jk <= 0) {// 健康归零，游戏结束
break OK;
}
}
break;
case 2:
if ((int) (Math.random() * 2) == 0) {
System.out.println("你躲在废弃卡车下，成功躲避大批行尸！");
} else {
jk -= 5 + (int) (Math.random() * 11);
tl -= 5 + (int) (Math.random() * 11);
System.out
.println("你躲在废弃卡车下，被一只爬行的行尸发现！你仓皇逃走！健康减少！体力减少！");
gameover();
if (jk <= 0) {// 健康归零，游戏结束
break OK;
}
}
break;
case 3:
s += 4;
System.out
.println("你摸上腐尸血跟上行尸队伍，没有被发现！却跟着走了很远才脱身！距离增加！");
break;
}
}
if (x >= 14 && x < 15) {// 概率1/15
System.out
.println("你在路上遇到一个幸存者，他看起来很饿！你决定：(1.给他食物 2.无视继续前进)");
switch (sca.nextInt()) {
case 1:
if (wps[2] >= 1) {
wps[2]--;
if ((int)(Math.random() * 2) == 0) {
wps[2] = 0;
System.out
.println("你给了他一份食物！他吃完后恢复了体力，忽然抢走你剩下的所有食物，不知去向！");
} else {
wps[12]++;
System.out
.println("你给了他一份食物！他吃完后恢复了体力，为了表示谢意给了你一把还有1颗子弹的左轮手枪！");
}
} else {
System.out.println("你的包裹里已经没有食物了！");
}
break;
case 2:
if ((int) (Math.random() * 2) == 0) {
jk -= 10 + (int) (Math.random() * 11);
System.out
.println("你无视他继续前进，忽然被他一枪击中肩膀，你匆忙逃走！健康降低！");
if (jk <= 0) {// 健康归零，游戏结束
break OK;
}
} else {
System.out.println("你无视他继续前进！");
}
break;
}
}
if (x >= 15 && x < 16) {// 概率1/15
tl = 100;
System.out
.println("你在路上忽然发现一只混在行尸群里蹦蹦跳跳来客串的天朝僵尸！一阵兴奋，体力回满！");
}
// 前进消耗
t++;
s--;
tl -= 5 + (int) (Math.random() * 11);
jk += (int) (Math.random() * 15) - 10;
je -= 10 + (int) (Math.random() * 11);
ys -= 10 + (int) (Math.random() * 11);
gameover();
if (jk <= 0) {// 健康归零，游戏结束
break OK;
}
if (s <= 0) {// 距离归零，游戏结束
break OK;
}
break;
}
// 存档管理
RandomAccessFile save = new RandomAccessFile("XSZRsave.dat", "rw");
save.write(t);
save.write(s);
save.write(len);
byte[] savebuf = name.getBytes();
save.write(savebuf);
save.write(tl);
save.write(jk);
save.write(je);
save.write(ys);
save.write(jz);
save.write(yc);
save.write(hj);
save.write(jzzb);
save.write(yczb);
save.write(hjzb);
for (int i = 1; i <= 17; i++) {
save.write(wps[i]);
}
save.close();


Thread.sleep(1000);
}
// 游戏结束
if (s <= 0) {
System.out.println("你用了" + t + "天，终于来到了幸存者基地！但只是暂时的安全，新的冒险还在继续！");
} else {
System.out.println("你用了" + t + "天，最终牺牲在距离终点" + s
+ "km的路上！Game Over！");
}
System.out.println("谢谢试玩《行尸走肉》！");
}


public static int pk(int HP, int G, int F, int hp, int g, int f)
throws InterruptedException {// 行尸对战模拟
while (true) {
hp -= G - f;
System.out.println("你对行尸造成了" + (G - f) + "点伤害！");
if (hp <= 0) {
break;
}
Thread.sleep(500);
HP -= g - F;
System.out.println("行尸对你造成了" + (g - F) + "点伤害！");
if (HP <= 0) {
break;
}
Thread.sleep(500);
}
return HP;
}


public static void gameover() {// 属性溢出判定
int y;
if (tl <= 0) {// 体力耗光惩罚
y = 1 + (int) (Math.random() * 2);
t += y;
tl = 30;
je -= 10 + y * 10 + (int) (Math.random() * 11);
ys -= 10 + y * 10 + (int) (Math.random() * 11);
System.out.println("你的体力耗光了！忽然感觉一阵眩晕！昏迷了" + y
+ "天，恢复了些许体力，饥饿和饮水大幅度减少！");
}
if (je < 0) {// 饥饿耗光惩罚
jk += je / 2;
je = 0;
System.out.println("你饿的头昏眼花！健康降低！");
}
if (ys < 0) {// 饮水耗光惩罚
jk += ys / 2;
ys = 0;
System.out.println("你口干舌燥！健康降低！");
}
}


public static void xs() throws InterruptedException {// 遭遇行尸判定
int y;
gf = (int) (Math.random() * 2);
hp = 20;
g = 4 + (int) (Math.random() * 7);
f = 0 + (int) (Math.random() * 4);
if (gf == 0) {
System.out.println("你遭遇一只【近战行尸】(体力:" + hp + "近战:" + g + "护甲:" + f
+ ")！是否战斗：(1.是 2.否)");
} else {
System.out.println("你遭遇一只【远程行尸】(体力:" + hp + "近战:" + g + "护甲:" + f
+ ")！是否战斗：(1.是 2.否)");
}
switch (sca.nextInt()) {
case 1:
if (gf == 0) {
tl = pk(tl, jz, hj, hp, g, f);
} else {
tl = pk(tl, yc, hj, hp, g, f);
}
if (tl <= 0) {// 战斗失败惩罚
jk -= 20;
gameover();
} else {
System.out.println("你成功击杀了行尸！");
y = (int) (Math.random() * 3);// 战斗胜利奖励
if (y == 0) {
if (gf == 0) {
jz += 1;
} else {
yc += 1;
}
System.out.println("通过战斗你积累了更多的作战经验！");
}
}
break;
case 2:
tl -= 5 + (int) (Math.random() * 6);
System.out.println("你在行尸反应之前，及时的逃离了！体力降低！");
break;
}
}


public static void getwp() throws InterruptedException {// 获得物品
int x, y;
x = 1 + (int) (Math.random() * 3);
y = 1 + (int) (Math.random() * 5);
wps[y] += x;
System.out.println("获得" + x + "个" + wp[y] + "！");
}


public static void getzbwp() throws InterruptedException {// 获得装备
int y;
y = 6 + (int) (Math.random() * 12);
wps[y] += 1;
System.out.println("获得一件" + wp[y] + "！");
}


public static void losewp() throws InterruptedException {// 失去物品
int y;
int j = 1;
for (int i = 1; i <= 17; i++) {
if (wps[i] != 0) {
index[j] = i;
j++;
}
}
y = (int) (Math.random() * j);
if (y == 0) {
System.out.println("节操掉了一地！");
} else {
wps[index[y]]--;
System.out.println("失去一个" + wp[index[y]] + "！");
}
}
}


