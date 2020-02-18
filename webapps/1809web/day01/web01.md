####body内部的常用标签
1.内容标题标签：h1-h6  字体大小不同  align:left/center/right 独占一行
2.段落标签:p ，上下会流空白，独占一行
3.hr:水平分割线
4.br:换行


5.无序列表
<ul type="square">
		<li>刘备</li>
		<li>孙尚香</li>
		<li>貂蝉</li>
		<li>吕布</li>
</ul>
			
			
6.有序列表
<ol type="A" start="5">
		<li>打开冰箱</li>
		<li>把大象装进去冰箱</li>
		<li>关上冰箱</li>
</ol>
		
		
7.定义列表
<dl>
	<dt>凉菜</dt>
			<dd>拍黄瓜</dd>
			<dd>拉皮</dd>
	<dt>炒菜</dt>
			<dd>锅包肉</dd>
</dl>


8.列表嵌套
		有序列表和无序列表可以互相嵌套
		
		
		
###分区元素
- div : 独占一行
- span: 共占一行

分区元素可以理解为容器，用于装各种标签，作用是对多个标签进行统一管理和代码复用
页面一般分为三个大区：
	<div>头部</div>
	<div>内容</div>
	<div>脚步</div>
在h5标准里也提供几个分区元素，作用和div一样
	<header>头部<header>
	<article>内容</article>
	<footer>脚步</footer>
	

###元素分类
1.块级元素：独占一行
	div, h1-h6, p ,hr

2.行内元素：共占一行
	span,
	这是字体加粗<Strong>
	这也是字体加粗<b>
	这是斜体<em>
	这也是斜体<i>
	这是删除线<del>
	这也是删除线<s>
	这是下划线<u>
	
###空格折叠
	多个空格只能识别一个
	&nbsp;可代表空格符
	
###常见特殊字符

	空格：&nbsp;
	换行：<br>
	小于号：&lt;
	大于号：&gt;
	###
###图片标签img
-单标签
-属性：
1.art:当图片不能正常显示时显示此文件
2.src:图片路径，根目录为html页面当前目录
3.title 当鼠标悬停时下你是的文本内容
4.width/heigh 宽高，1通过像素复制，2通过百分比复制
	
###图像地图
area:范围  shape：形状 rect:矩形 
coords：坐标（左上点与右下点）
href: 点击访问,文件则下载

<map name="mymap">
		<area shape="rect" coords="0,0,100,100"href="../imgs/aa2.jpg">
</map>
