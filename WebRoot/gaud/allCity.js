//////////////////定义城市信息
var myMap=new Map();

function initCityData()
{
	var tebiexingzhengquMap = new Map();
	tebiexingzhengquMap.set('香港',['香港','1852','113.8','115','22','22.5']);
	tebiexingzhengquMap.set('澳门',['澳门','1853','113.52','113.6','22.2','22.22']);
	myMap.set('特别行政区',tebiexingzhengquMap);

	var onelevelMap = new Map();
	onelevelMap.set('南京市',['南京市','025','118','119.8','31','32.6']);
	onelevelMap.set('北京市',['北京市','010','115.4','117.8','39','40.8']);
	onelevelMap.set('上海市',['上海市','021','120.6','122','30.6','32']);
	onelevelMap.set('广州市',['广州市','020','112','115','22.6','23.8']);
	onelevelMap.set('深圳市',['深圳市','0755','113.6','114.6','22.45','23']);
	onelevelMap.set('香港市',['香港市','1852','113.8','115','22','22.5']);
	onelevelMap.set('沈阳市',['沈阳市','024','122.4','123.9','41.1','43.1']);
	onelevelMap.set('大连市',['大连市','0411','120.9','123.6','38.7','40.2']);
	onelevelMap.set('天津市',['天津市','022','116','118.1','38','40.3']);
	onelevelMap.set('青岛市',['青岛市','0532','119','121','35','37.2']);
	onelevelMap.set('苏州市',['苏州市','0512','119.8','121.4','30.7','32.1']);
	onelevelMap.set('杭州市',['杭州市','0571','118.3','120.9','29.1','30.6']);
	onelevelMap.set('宁波市',['宁波市','0574','120.9','122.5','28.8','30.6']);
	onelevelMap.set('厦门市',['厦门市','0592','117.8','118.5','24.3','24.9']);
	onelevelMap.set('武汉市',['武汉市','027','113','115','29','31.4']);
	onelevelMap.set('重庆市',['重庆市','023','105.1','110.2','28.1','31.5']);
	onelevelMap.set('成都市',['成都市','028','103','105','30','31.4']);
	onelevelMap.set('西安市',['西安市','029','107.4','109.9','33.4','34.8']);
	onelevelMap.set('郑州市',['郑州市','0371','112.4','114.3','34.1','35']);
	onelevelMap.set('合肥市',['合肥市','0551','116.4','118','31','32.6']);
	onelevelMap.set('哈尔滨市',['哈尔滨市','0451','125.7','130.2','44','46.7']);
	onelevelMap.set('长春市',['长春市','0431','124.3','127.1','43','45.3']);
	onelevelMap.set('济南市',['济南市','0531','116.2','117.8','36','37.1']);
	onelevelMap.set('无锡市',['无锡市','0510','119.8','120.8','31.4','32']);
	onelevelMap.set('福州市',['福州市','0591','118.1','120.6','25.2','26.7']);
	onelevelMap.set('澳门市',['澳门市','1853','113.4','113.6','22','22.3']);
	onelevelMap.set('长沙市',['长沙市','0731','110.9','114.3','27.8','28.7']);
	onelevelMap.set('南昌市',['南昌市','0791','115.4','116.6','28.1','29.2']);
	onelevelMap.set('南宁市',['南宁市','0771','107.3','109.7','22.2','24.1']);
	onelevelMap.set('昆明市',['昆明市','0871','102.1','103.7','24.4','26.6']);
	onelevelMap.set('贵阳市',['贵阳市','0851','105.6','107.8','26.1','27.7']);
	onelevelMap.set('太原市',['太原市','0351','111.3','113.3','37.2','38.5']);
	onelevelMap.set('石家庄市',['石家庄市','0311','113.5','115.7','37.4','39']);
	myMap.set('首批',onelevelMap);
	
	var zhixiashiMap = new Map();
	zhixiashiMap.set('上海',['上海','021','120.6','122','30.6','32']);
	zhixiashiMap.set('北京',['北京','010','115.4','117.8','39','40.8']);
	zhixiashiMap.set('重庆',['重庆','023','105.1','110.2','28.1','31.5']);
	zhixiashiMap.set('天津',['天津','022','116','118.1','38','40.3']);	
	myMap.set('直辖市',zhixiashiMap);
	
	//return;
	
	var jiangsuMap = new Map();
    //jiangsuMap.set('南京市',['南京市','025','118.4','119','31.8','32.6']);
	jiangsuMap.set('徐州市',['徐州市','0516','116','119','33.3','35']);
	jiangsuMap.set('南通市',['南通市','0513','120.1','121.6','31.5','32.5']);
	jiangsuMap.set('镇江市',['镇江市','0511','118.9','120','31.6','32.4']);
	jiangsuMap.set('连云港市',['连云港市','0518','118','120.5','33.9','35.5']);
	jiangsuMap.set('常州市',['常州市','0519','119','120.8','31','32.3']);
	jiangsuMap.set('宿迁市',['宿迁市','0527','117.5','119.1','33.5','34.3']);
	jiangsuMap.set('淮安市',['淮安市','0517','117.6','120.4','32.4','34.1']);
	jiangsuMap.set('无锡市',['无锡市','0510','119.3','121','30.9','32.3']);
	jiangsuMap.set('盐城市',['盐城市','0515','119','121','32.3','34.3']);
	jiangsuMap.set('苏州市',['苏州市','0512','119.5','121.2','30.4','32.1']);
	jiangsuMap.set('泰州市',['泰州市','0523','119.1','121.3','31.8','33.3']);
	jiangsuMap.set('扬州市',['扬州市','0514','118.85','120.5','31.8','33.5']);
	
	myMap.set('江苏省',jiangsuMap);

	var shandongMap = new Map();
	shandongMap.set('荷泽市',['荷泽市','0530','114.2','116.5','34','35.8']);
	//shandongMap.set('济南市',['济南市','0531','116.2','117.8','36','37.1']);
	shandongMap.set('青岛市',['青岛市','0532','119.3','121','35.3','37.1']);
	shandongMap.set('淄博市',['淄博市','0533','117.3','119','35.5','37.4']);
	shandongMap.set('德州市',['德州市','0534','115.4','117.4','36.2','38.1']);
	shandongMap.set('烟台市',['烟台市','0535','119','122.6','36','38.3']);
	shandongMap.set('潍坊市',['潍坊市','0536','118','120.2','35.2','37.4']);
	shandongMap.set('济宁市',['济宁市','0537','115.5','117.7','34.4','36']);
	shandongMap.set('泰安市',['泰安市','0538','116','118.6','35.3','36.4']);
	shandongMap.set('临沂市',['临沂市','0539','117.2','119.2','34.2','36.2']);
	shandongMap.set('滨州市',['滨州市','0543','117.1','118.6','36.4','38.2']);
	shandongMap.set('东营市',['东营市','0546','118','119.3','36.5','38.1']);
	shandongMap.set('威海市',['威海市','0631','121.1','122.7','36.8','37.6']);
	shandongMap.set('枣庄市',['枣庄市','0632','116.4','118.3','34.2','35.4']);
	shandongMap.set('日照市',['日照市','0633','118.2','120.2','34.8','36.1']);
	shandongMap.set('莱芜市',['莱芜市','0634','117','119','35.2','36.6']);
	shandongMap.set('聊城市',['聊城市','0635','115','116.8','35.4','37.1']);
	
	myMap.set('山东省',shandongMap);

	
	var henanMap = new Map();
	henanMap.set('商丘市',['商丘市','0370','114.6','116.8','33.9','35']);
	//henanMap.set('郑州市',['郑州市','0371','112.4','114.3','34.1','35']);
	henanMap.set('安阳市',['安阳市','0372','113.3','115','35.1','36.6']);
	henanMap.set('新乡市',['新乡市','0373','113','115','34.9','35.8']);
	henanMap.set('许昌市',['许昌市','0374','112.4','115.4','33.4','34.6']);
	henanMap.set('平顶山市',['平顶山市','0375','112','114','33','34.2']);
	henanMap.set('信阳市',['信阳市','0376','113','116','31','33']);
	henanMap.set('南阳市',['南阳市','0377','110','113.8','32','34']);
	henanMap.set('开封市',['开封市','0378','114.1','115.7','34','35.1']);
	henanMap.set('洛阳市',['洛阳市','0379','111.2','113.4','33.3','35']);
	henanMap.set('焦作市',['焦作市','0391','112.5','113.8','34.8','35.5']);
	henanMap.set('鹤壁市',['鹤壁市','0392','113.5','115','35.2','36.1']);
	henanMap.set('濮阳市',['濮阳市','0393','114.5','116','35','36.2']);
	henanMap.set('周口市',['周口市','0394','114','115.8','33','34.5']);
	henanMap.set('缧河市',['缧河市','0395','113','114.5','33','34']);
	henanMap.set('驻马店市',['驻马店市','0396','113.1','115.6','32.1','33.4']);
	henanMap.set('三门峡市',['三门峡市','0398','110','112','33.5','35.6']);
	
	myMap.set('河南省',henanMap);

	var zhejiangMap = new Map();
	zhejiangMap.set('衢州市',['衢州市','0570','117.5','119.5','28','29.7']);
	//zhejiangMap.set('杭州市',['杭州市','0571','118.3','120.9','29.1','30.6']);
	zhejiangMap.set('湖州市',['湖州市','0572','119','120.7','30.4','31.2']);
	zhejiangMap.set('嘉兴市',['嘉兴市','0573','120.1','121.3','30.2','31.2']);
	zhejiangMap.set('宁波市',['宁波市','0574','120.5','122.2','28.5','30.4']);
	zhejiangMap.set('绍兴市',['绍兴市','0575','119.5','121.4','29.1','30.2']);
	zhejiangMap.set('台州市',['台州市','0576','120.3','122','28','29.4']);
	zhejiangMap.set('温州市',['温州市','0577','119.3','121.6','27','28.6']);
	zhejiangMap.set('丽水市',['丽水市','0578','118.4','120.7','27.2','29']);
	zhejiangMap.set('金华市',['金华市','0579','119','121','28.3','30']);
	zhejiangMap.set('舟山市',['舟山市','0580','121.3','123.3','29.3','31.1']);
	myMap.set('浙江省',zhejiangMap);

	
	var shanxishengMap = new Map();
	//shanxiMap.set('西安市',['西安市','029','107.4','109.9','33.4','34.8']);
	shanxishengMap.set('咸阳市',['咸阳市','0910','107.6','109.5','34','35.6']);
	shanxishengMap.set('延安市',['延安市','0911','107.7','110.6','35.4','37.6']);
	shanxishengMap.set('渭南市',['渭南市','0913','109.2','110.7','34.1','35']);
	shanxishengMap.set('榆林市',['榆林市','0912','107.4','111.3','36.8','39.6']);
	shanxishengMap.set('商州市',['商州市','0914','108.6','112.4','33','34.5']);
	shanxishengMap.set('安康市',['安康市','0915','108','110.1','31.5','33.9']);
	shanxishengMap.set('宝鸡市',['宝鸡市','0917','106.3','108.1','33.5','35.1']);
	shanxishengMap.set('汉中市',['汉中市','0916','105.5','108.4','32.1','34']);
	shanxishengMap.set('铜川市',['铜川市','0919','108','110.4','34.5','35.8']);
	myMap.set('陕西省',shanxishengMap);
	
    
	var anhuiMap = new Map();
	anhuiMap.set('滁州市',['滁州市','0550','117','119.3','31.8','33.3']);
	//anhuiMap.set('合肥市',['合肥市','0551','116.4','118','31','32.6']);
	anhuiMap.set('蚌埠市',['蚌埠市','0552','116.8','117.7','32.7','33.5']);
	anhuiMap.set('芜湖市',['芜湖市','0553','117.8','118.8','31','31.7']);
	anhuiMap.set('淮南市',['淮南市','0554','116.1','117.3','32','33.1']);
	anhuiMap.set('马鞍山市',['马鞍山市','0555','117.8','118.9','31.4','32.1']);
	anhuiMap.set('安庆市',['安庆市','0556','115.7','117.8','29.7','31.3']);
	anhuiMap.set('宿州市',['宿州市','0557','116.1','118.2','33.3','34.7']);
	anhuiMap.set('阜阳市',['阜阳市','0558','114.8','116.9','32.4','34.1']);
	anhuiMap.set('黄山市',['黄山市','0559','117','119.9','29','30.5']);
	anhuiMap.set('淮北市',['淮北市','0561','116.3','117.4','33.2','34.3']);
	anhuiMap.set('铜陵市',['铜陵市','0562','117','118.7','30','31.3']);
	anhuiMap.set('宣城市',['宣城市','0563','117.9','119.7','29.9','31.4']);
	anhuiMap.set('六安市',['六安市','0564','114.5','117','30.8','32.7']);
	anhuiMap.set('池州市',['池州市','0566','116.5','118.5','29.4','31']);
	anhuiMap.set('毫州市',['毫州市','0566','115','117','33','35']);
	anhuiMap.set('巢湖市',['巢湖市','0551','','','','']);
	
	myMap.set('安徽省',anhuiMap);


	var fujianMap = new Map();
	//fujianMap.set('福州市',['福州市','0591','118.1','120.6','25.2','26.7']);
	fujianMap.set('厦门市',['厦门市','0592','117.9','118.5','24.3','24.9']);
	fujianMap.set('宁德市',['宁德市','0593','118.5','120.8','26.3','27.7']);
	fujianMap.set('莆田市',['莆田市','0594','117.3','119.8','25','26.1']);
	fujianMap.set('泉州市',['泉州市','0595','117.5','119.1','24.3','26']);
	fujianMap.set('漳州市',['漳州市','0596','116.6','118','23.6','25.4']);
	fujianMap.set('龙岩市',['龙岩市','0597','115','118','24.4','26']);
	fujianMap.set('三明市',['三明市','0598','116.3','118.7','25.4','27.2']);
	fujianMap.set('南平市',['南平市','0599','117','119.3','26.2','28.4']);
	myMap.set('福建省',fujianMap);

	
	var guangdongMap = new Map();
	//guangdongMap.set('广州市',['广州市','020','112','115','22.6','23.8']);
	guangdongMap.set('深圳市',['深圳市','0755','113.7','114.7','22.4','23']);
	guangdongMap.set('珠海市',['珠海市','0756','113','114.4','21.6','22.5']);
	guangdongMap.set('汕尾市',['汕尾市','0660','114.5','116.5','22','24']);
	guangdongMap.set('阳江市',['阳江市','0662','111','113','21','23']);
	guangdongMap.set('揭阳市',['揭阳市','0663','115.6','116.7','22.8','23.8']);
	guangdongMap.set('云浮市',['云浮市','0766','111','112.7','22.3','23.4']);
	guangdongMap.set('茂名市',['茂名市','0668','110','112','21','23']);
	guangdongMap.set('江门市',['江门市','0750','112','113.5','22','23']);
	guangdongMap.set('韶关市',['韶关市','0751','112.8','114.9','23.8','25.6']);
	guangdongMap.set('惠州市',['惠州市','0752','113.8','115','22.6','23.5']);
	guangdongMap.set('梅州市',['梅州市','0753','115.3','117','23.3','25']);
	guangdongMap.set('汕头市',['汕头市','0754','116.2','117.4','23','23.7']);
	guangdongMap.set('佛山市',['佛山市','0757','112.3','113.4','22.6','23.6']);
	guangdongMap.set('肇庆市',['肇庆市','0758','112.2','112.95','22','24.8']);
	guangdongMap.set('湛江市',['湛江市','0759','109.6','111','20.3','22']);
	guangdongMap.set('中山市',['中山市','0760','113.1','113.9','22','22.8']);
	guangdongMap.set('河源市',['河源市','0762','114.2','115.6','23.1','24.3']);
	guangdongMap.set('清远市',['清远市','0763','112','114.5','23.5','25']);
	guangdongMap.set('潮州市',['潮州市','0768','116.3','117.5','23.515','25']);
	guangdongMap.set('东莞市',['东莞市','0769','112.9','114.1','22.4','24']);
	
	myMap.set('广东省',guangdongMap);

	
	var hebeiMap = new Map();
	//hebeiMap.set('石家庄市',['石家庄市','0311','113.5','115.7','37.4','39']);
	hebeiMap.set('邯郸市',['邯郸市','0310','113','116','36','37']);
	hebeiMap.set('保定市',['保定市','0312','113.6','116.4','38.1','40']);
	hebeiMap.set('张家口市',['张家口市','0313','113','117','39.4','42.2']);
	hebeiMap.set('承德市',['承德市','0314','115','120','40.2','42.7']);
	hebeiMap.set('唐山市',['唐山市','0315','117.5','119.4','38.9','40.5']);
	hebeiMap.set('廊坊市',['廊坊市','0316','116.6','117','37.4','39.9']);
	hebeiMap.set('沧州市',['沧州市','0317','115.7','117.9','37.4','39']);
	hebeiMap.set('衡水市',['衡水市','0318','114.6','116.7','36.5','39']);
	hebeiMap.set('邢台市',['邢台市','0319','113','116','36.9','37.8']);
	hebeiMap.set('秦皇岛市',['秦皇岛市','0335','118','120','39.4','41']);
	myMap.set('河北省',hebeiMap);

	
	var liaoningMap = new Map();
	//liaoningMap.set('沈阳市',['沈阳市','0024','122.4','123.9','41.1','43.1']);
	liaoningMap.set('铁岭市',['铁岭市','0410','123','125.1','41.9','43.4']);
	liaoningMap.set('大连市',['大连市','0411','120.9','123.6','38.7','40.2']);
	liaoningMap.set('鞍山市',['鞍山市','0412','122','124','40','41.6']);
	liaoningMap.set('抚顺市',['抚顺市','0413','123.6','125.5','41.2','42.5']);
	liaoningMap.set('本溪市',['本溪市','0414','123','126','40','41.8']);
	liaoningMap.set('丹东市',['丹东市','0415','123','126','39.5','41']);
	liaoningMap.set('锦州市',['锦州市','0416','120','122','40','42']);
	liaoningMap.set('营口市',['营口市','0417','121.5','124','39.5','41']);
	liaoningMap.set('阜新市',['阜新市','0418','121','123','41.6','42.9']);
	liaoningMap.set('辽阳市',['辽阳市','0419','122','124','40','41.7']);
	liaoningMap.set('朝阳市',['朝阳市','0421','118','122','40','42.5']);
	liaoningMap.set('盘锦市',['盘锦市','0427','121','123','40.6','41.7']);
	liaoningMap.set('葫芦岛市',['葫芦岛市','0429','119.2','121.7','39.9','41.2']);
	myMap.set('辽宁省',liaoningMap);

	
	var sichuanMap = new Map();
	//sichuanMap.set('成都市',['成都市','028','103','105','30','31.4']);
	//sichuanMap.set('重庆市',['重庆市','023','105.1','110.2','28.1','31.5']);
	sichuanMap.set('攀枝花市',['攀枝花市','0812','100','104','25.5','28']);
	sichuanMap.set('自贡市',['自贡市','0813','103','106','28.9','29.7']);
	sichuanMap.set('绵阳市',['绵阳市','0816','103.7','105.8','30.7','33.1']);
	sichuanMap.set('南充市',['南充市','0817','105','107','30','32']);
	sichuanMap.set('达州市',['达州市','0818','106','110','30.3','32.4']);
	sichuanMap.set('遂宁市',['遂宁市','0825','105','106','30.1','31.2']);
	sichuanMap.set('广安市',['广安市','0826','106.6','106.9','30.1','30.5']);
	sichuanMap.set('泸州市',['泸州市','0830','104','108','27.5','29.4']);
	sichuanMap.set('宜宾市',['宜宾市','0831','103','106','27','29.4']);
	sichuanMap.set('乐山市',['乐山市','0833','0102','105','28.4','30.1']);
	sichuanMap.set('内江市',['内江市','1832','104','106','28','30.1']);
	sichuanMap.set('西昌市',['西昌市','0834','101.7','102.5','29.1','30.1']);
	sichuanMap.set('雅安市',['雅安市','0835','101.9','103.4','28.8','31']);
	sichuanMap.set('马尔康市',['马尔康市','0837','101.2','102.7','30.5','32.4']);
	sichuanMap.set('德阳市',['德阳市','0838','103.7','105.3','30.5','31.7']);
	sichuanMap.set('广元市',['广元市','0839','104.6','106.8','31.5','33']);
	sichuanMap.set('巴中市',['巴中市','0827','106.3','107.8','31.2','32.8']);
	sichuanMap.set('眉山市',['眉山市','1833','102','105','29','30.5']);
	sichuanMap.set('资阳市',['资阳市','0832','104','106','29','31']);
	sichuanMap.set('甘孜藏族自治州',['甘孜藏族自治州','0836','97','103','27.9','34.5']);
	myMap.set('四川省',sichuanMap);

	
	var hubeiMap = new Map();
	//hubeiMap.set('武汉市',['武汉市','027','113','115','29','31.4']);
	hubeiMap.set('襄阳市',['襄阳市','0710','110','114','31','32.6']);
	hubeiMap.set('鄂州市',['鄂州市','0711','114','116','29','31']);
	hubeiMap.set('孝感市',['孝感市','0712','113','115','30.5','31.6']);
	hubeiMap.set('黄冈市',['黄冈市','0713','114.4','116.5','29.8','32']);
	hubeiMap.set('黄石市',['黄石市','0714','114','116','29.8','30.6']);
	hubeiMap.set('荆门市',['荆门市','0724','111','113.5','30','31.2']);
	hubeiMap.set('咸宁市',['咸宁市','0715','113.5','115.5','28','30.5']);
	hubeiMap.set('荆州市',['荆州市','0716','110.5','113.9','29.5','30.8']);
	hubeiMap.set('宜昌市',['宜昌市','0717','109','113','29.9','31.2']);
	hubeiMap.set('恩施土家族苗族自治州',['恩施土家族苗族自治州','0718','108','111','29','32']);
	hubeiMap.set('十堰市',['十堰市','0719','109','112.5','32','33.2']);
	hubeiMap.set('随州市',['随州市','0722','112','114','31','32.6']);
	hubeiMap.set('神农架林区',['神农架林区','1719','109','112.2','31','32.2']);
	hubeiMap.set('天门市',['天门市','1728','112','114','30','31.6']);
	hubeiMap.set('潜江市',['潜江市','2728','112','114','29.6','31']);
	hubeiMap.set('仙桃市',['仙桃市','0728','112','114','29.6','31']);
	myMap.set('湖北省',hubeiMap);

	
	var hunanMap = new Map();
	//hunanMap.set('长沙市',['长沙市','0731','110.9','114.3','27.8','28.7']);
	hunanMap.set('岳阳市',['岳阳市','0730','112.3','114.2','28.4','29.9']);
	hunanMap.set('湘潭市',['湘潭市','0732','111.9','114','27.3','28.2']);
	hunanMap.set('株洲市',['株洲市','0733','112','114','26','28.2']);
	hunanMap.set('衡阳市',['衡阳市','0734','111','114','26','28']);
	hunanMap.set('常德市',['常德市','0736','110','113','28','29.8']);
	hunanMap.set('郴州市',['郴州市','0735','112.2','114.3','24.8','26.9']);
	hunanMap.set('益阳市',['益阳市','0737','110.5','113.5','27.5','30']);
	hunanMap.set('娄底市',['娄底市','0738','110','112.8','27','28.2']);
	hunanMap.set('邵阳市',['邵阳市','0739','110.5','112.5','26','28']);
	hunanMap.set('张家界市',['张家界市','0744','108','112','28','30.5']);
	hunanMap.set('怀化市',['怀化市','0745','109','111','26','29']);
	hunanMap.set('永州市',['永州市','0746','110','113','24.6','26.8']);
	hunanMap.set('湘西土家族苗族自治州',['湘西土家族苗族自治州','0743','108','112','27','29.5']);
	myMap.set('湖南省',hunanMap);

	
	var neimengguMap = new Map();
	//neimengguMap.set('呼和浩特市',['呼和浩特市','0471','110.7','114','39.9','42']);
	neimengguMap.set('呼伦贝尔市',['呼伦贝尔市','0470','118','122','48','50']);
	neimengguMap.set('包头市',['包头市','0472','108','111','39','42']);
	neimengguMap.set('乌海市',['乌海市','0473','105','109','39','40.5']);
	neimengguMap.set('乌兰察布市',['乌兰察布市','0474','112','114.5','39.9','43']);
	neimengguMap.set('通辽市',['通辽市','0475','119','124','42','44.6']);
	neimengguMap.set('赤峰市',['赤峰市','0476','116','121','41','44.8']);
	neimengguMap.set('鄂尔多斯市',['鄂尔多斯市','0477','107','113','38.5','40.6']);
	neimengguMap.set('巴彦淖尔市',['巴彦淖尔市','0478','104','110','39','42']);
	neimengguMap.set('锡林郭勒盟',['锡林郭勒盟','0479','113','119','42','48']);
	neimengguMap.set('兴安盟',['兴安盟','0482','119','125','45','47.6']);
	neimengguMap.set('阿拉善盟',['阿拉善盟','0483','98','108','37','42.5']);
	myMap.set('内蒙古省',neimengguMap);

	var heilongjiangMap = new Map();
	//heilongjiangMap.set('哈尔滨市',['哈尔滨市','0451','125.7','130.2','44','46.7']);
	heilongjiangMap.set('齐齐哈尔市',['齐齐哈尔市','0452','122.4','126.7','45.8','49.6']);
	heilongjiangMap.set('牡丹江市',['牡丹江市','0453','127','131.3','43.4','46']);
	heilongjiangMap.set('佳木斯市',['佳木斯市','0454','129.4','135.1','45.9','48.5']);
	heilongjiangMap.set('绥化市',['绥化市','0455','124.2','128.5','45','47.7']);
	heilongjiangMap.set('黑河市',['黑河市','0456','124','130','48','52']);
	heilongjiangMap.set('伊春市',['伊春市','0458','126.5','131','47','49']);
	heilongjiangMap.set('大庆市',['大庆市','0459','123','127','44.5','48.5']);
	heilongjiangMap.set('大兴安岭地区',['大兴安岭地区','0457','121','128','50','55']);
	heilongjiangMap.set('鹤岗市',['鹤岗市','0468','129','132.6','46','48.2']);
	heilongjiangMap.set('鸡西市',['鸡西市','0467','128','134','43.5','47']);
	heilongjiangMap.set('七台河市',['七台河市','0464','129','133','44','48']);
	heilongjiangMap.set('双鸭山市',['双鸭山市','0469','129','134.5','45','48']);
	myMap.set('黑龙江省',heilongjiangMap);

	var guangxiMap = new Map();
	//guangxiMap.set('南宁市',['南宁市','0771','107.3','109.7','22.2','24.1']);
	guangxiMap.set('柳州市',['柳州市','0772','108','111','23','26.5']);
	guangxiMap.set('桂林市',['桂林市','0773','109','112','24','27']);
	guangxiMap.set('梧州市',['梧州市','0774','109','112.7','22.4','24.6']);
	guangxiMap.set('玉林市',['玉林市','0775','109','111.5','21.6','23.5']);
	guangxiMap.set('百色市',['百色市','0776','105','107.8','22','26']);
	guangxiMap.set('钦州市',['钦州市','0777','107.4','110','20.9','22.7']);
	guangxiMap.set('河池市',['河池市','0778','106','110','23.3','25.7']);
	guangxiMap.set('北海市',['北海市','0779','108.8','109.8','21.4','21.9']);
	guangxiMap.set('防城港市',['防城港市','0770','107.4','109','20.6','22.4']);
	guangxiMap.set('崇左市',['崇左市','1771','106.5','108.5','21.6','24']);
	guangxiMap.set('来宾市',['来宾市','1772','108','111','22','25']);
	guangxiMap.set('贺州市',['贺州市','1774','110','113','23','26']);
	guangxiMap.set('贵港市',['贵港市','1755','108','111','22','24']);
	myMap.set('广西省',guangxiMap);

	
	var jiangxiMap = new Map();
	//jiangxiMap.set('南昌市',['南昌市','0791','115.4','116.6','28.1','29.2']);
	jiangxiMap.set('鹰潭市',['鹰潭市','0701','116','118','27','29.5']);
	jiangxiMap.set('新余市',['新余市','0790','114.4','116.5','27','28.5']);
	jiangxiMap.set('九江市',['九江市','0792','113.9','117.5','28.9','30.1']);
	jiangxiMap.set('上饶市',['上饶市','0793','116.2','119.5','27','29.6']);
	jiangxiMap.set('抚州市',['抚州市','0794','115','118','26.4','28.5']);
	jiangxiMap.set('宜春市',['宜春市','0795','113.4','116.5','27','29.1']);
	jiangxiMap.set('吉安市',['吉安市','0796','113.5','117','25.9','28']);
	jiangxiMap.set('赣州市',['赣州市','0797','113.5','117','24.4','27.2']);
	jiangxiMap.set('景德镇市',['景德镇市','0798','116','118','28.5','30.5']);
	jiangxiMap.set('萍乡市',['萍乡市','0799','113.1','114.8','26','29']);
	myMap.set('江西省',jiangxiMap);

	
	var shanxiMap = new Map();
	//shanxiMap.set('太原市',['太原市','0351','111.3','113.3','37.2','38.5']);
	shanxiMap.set('忻州市',['忻州市','0350','110.4','114','38','39.5']);
	shanxiMap.set('大同市',['大同市','0352','112','115','39','41']);
	shanxiMap.set('阳泉市',['阳泉市','0353','113.2','114.5','37.4','38.2']);
	shanxiMap.set('晋中市',['晋中市','0354','112','114.3','36.5','38']);
	shanxiMap.set('长治市',['长治市','0355','112','114','35.5','37.5']);
	shanxiMap.set('晋城市',['晋城市','0356','111.5','114','35','36.1']);
	shanxiMap.set('临汾市',['临汾市','0357','110','113','35','37']);
	shanxiMap.set('运城市',['运城市','0359','110','112.1','34','36']);
	shanxiMap.set('吕梁市',['吕梁市','0358','110.3','112.4','36.7','38.8']);
	shanxiMap.set('朔州市',['朔州市','0349','111.8','113.6','39','40.3']);
	myMap.set('山西省',shanxiMap);

	
	var jilinMap = new Map();
	//jilinMap.set('长春市',['长春市','0431','124.3','127.1','43','45.3']);
	jilinMap.set('吉林市',['吉林市','0432','125.5','128','42.5','44.4']);
	jilinMap.set('延边朝鲜族自治州',['延边朝鲜族自治州','1433','128.3','130.5','42','44']);
	jilinMap.set('四平市',['四平市','0434','123.2','125.6','42','43.9']);
	jilinMap.set('通化市',['通化市','0435','125','127','40.5','43.5']);
	jilinMap.set('白城市',['白城市','0436','121','124.5','44','46.5']);
	jilinMap.set('辽源市',['辽源市','0437','124.7','125.9','42.3','43.3']);
	jilinMap.set('松原市',['松原市','0438','123','126.4','44','46']);
	jilinMap.set('白山市',['白山市','0439','125','128','41','43.5']);
	myMap.set('吉林省',jilinMap);

	var yunnanMap = new Map();
	//yunnanMap.set('昆明市',['昆明市','0871','102.1','103.7','24.4','26.6']);
	yunnanMap.set('西双版纳傣族自治州',['西双版纳傣族自治州','0691','100','102','21','23']);
	yunnanMap.set('芒市潞西市',['芒市','0692','98','99','24','25']);
	yunnanMap.set('昭通市',['昭通市','0870','102','106','26','29']);
	yunnanMap.set('大理市',['大理市','0872','98','102','24','27']);
	yunnanMap.set('红河哈尼族彝族自治州',['红河哈尼族彝族自治州','0873','101','105','22','25']);
	yunnanMap.set('曲靖市',['曲靖市','0874','102','106','24','28']);
	yunnanMap.set('保山市',['保山市','0875','98','101','24','26']);
	yunnanMap.set('文山壮族苗族自治州',['文山壮族苗族自治州','0876','103','107','22','25']);
	yunnanMap.set('玉溪市',['玉溪市','0877','101','104','23','25']);
	yunnanMap.set('楚雄彝族自治州',['楚雄彝族自治州','0878','100','103','24','27']);
	yunnanMap.set('普洱市',['普洱市','0879','100','102','22','24']);
	yunnanMap.set('临沧市',['临沧市','0883','98.4','101.5','23','25']);
	yunnanMap.set('怒江傈僳族自治州',['怒江傈僳族自治州','0886','98','100.5','24.5','28']);
	yunnanMap.set('迪庆藏族自治州',['迪庆藏族自治州','0887','97','102','26','29']);
	yunnanMap.set('丽江市',['丽江市','0888','99','101.5','25.5','28.5']);
	myMap.set('云南省',yunnanMap);

	var xinjiangMap = new Map();
	xinjiangMap.set('乌鲁木齐市',['乌鲁木齐市','0991','86','89','42','45']);
	xinjiangMap.set('塔城地区',['塔城地区','0901','82','88','43','48']);
	xinjiangMap.set('哈密地区',['哈密地区','0902','92','97','41','44']);
	xinjiangMap.set('和田地区',['和田地区','0903','76','83','36','38']);
	xinjiangMap.set('阿勒泰地区',['阿勒泰地区','0906','86','90','46','49']);
	xinjiangMap.set('克孜勒苏柯尔克孜自治州',['克孜勒苏柯尔克孜自治州','0908','75','79','39','41']);
	xinjiangMap.set('博尔塔拉蒙古自治州',['博尔塔拉蒙古自治州','0909','80','85','44','46']);
	xinjiangMap.set('克拉玛依市',['克拉玛依市','0990','80','87','44.6','46.4']);
	xinjiangMap.set('伊犁哈萨克自治州',['伊犁哈萨克自治州','0999','80','86','43','44.8']);
	xinjiangMap.set('石河子市',['石河子市','0993','84','86.9','43','45']);
	xinjiangMap.set('昌吉回族自治州',['昌吉回族自治州','0994','85','90','42','46']);
	xinjiangMap.set('吐鲁番地区',['吐鲁番地区','0995','87','92','41','44']);
	xinjiangMap.set('巴音郭楞蒙古自治州',['巴音郭楞蒙古自治州','0996','84','88','40','43.5']);
	xinjiangMap.set('阿克苏地区',['阿克苏地区','0997','78','85','39','43']);
	xinjiangMap.set('喀什地区',['喀什地区','0998','74','80','38','40']);
	xinjiangMap.set('伊犁哈萨克自治州',['伊犁哈萨克自治州','0999','80.1','91.1','40.2','49.2']);
	xinjiangMap.set('图木舒克地区',['图木舒克地区','1998','77','81','38','42']);
	xinjiangMap.set('五家渠市',['五家渠市','1994','86','89','43','45.5']);
	myMap.set('新疆省',xinjiangMap);


	var guizhouMap = new Map();
	//guizhouMap.set('贵阳市',['贵阳市','0851','105.6','107.8','26.1','27.7']);
	guizhouMap.set('遵义市',['遵义市','0852','105','109','27','29.1']);
	guizhouMap.set('安顺市',['安顺市','0853','105','107','25','27']);
	guizhouMap.set('黔南布依族苗族自治州',['黔南布依族苗族自治州','0854','106.8','109','25','28']);
	guizhouMap.set('黔东南苗族侗族自治州',['黔东南苗族侗族自治州','0855','107','110','25','28']);
	guizhouMap.set('铜仁市',['铜仁市','0856','107','111','27','31']);
	guizhouMap.set('毕节市',['毕节市','0857','104','107','26','27.8']);
	guizhouMap.set('六盘水市',['六盘水市','0858','103','106.5','25.5','27.2']);
	guizhouMap.set('黔西南布依族自治州',['黔西南布依族自治州','0859','103.5','107','23','26.3']);
	myMap.set('贵州省',guizhouMap);


	var gansuMap = new Map();
	gansuMap.set('兰州市',['兰州市','0931','103','104','36','37']);
	gansuMap.set('临夏回族自治区',['临夏回族自治区','0930','102','105','35','37']);
	gansuMap.set('平凉市',['平凉市','0933','105.2','107.5','34.5','36']);
	gansuMap.set('庆阳市',['庆阳市','0934','106.5','109','34.8','37.5']);
	gansuMap.set('张掖市',['张掖市','0936','97','103','38.2','40']);
	gansuMap.set('酒泉市',['酒泉市','0937','98','100','39','41']);
	gansuMap.set('天水市',['天水市','0938','103','108','33','35.2']);
	gansuMap.set('白银市',['白银市','0943','103','106','35','38']);
	gansuMap.set('定西市',['定西市','0932','103.5','106','34','36.1']);
	gansuMap.set('甘南州',['甘南州','0941','100.7','104.8','33.1','36.2']);
	gansuMap.set('嘉峪关市',['嘉峪关市','1937','97','99','38','41']);
	gansuMap.set('金昌市',['金昌市','0935','101','103','37','40']);
	gansuMap.set('武威市',['武威市','1935','101','104','36','39']);
	gansuMap.set('陇南市',['陇南市','2935','103','107','32','35']);
	myMap.set('甘肃省',gansuMap);


	var hainanMap = new Map();
	hainanMap.set('海口市',['海口市','0898','108.6','111.1','18','20.2']);
	myMap.set('海南省',hainanMap);

	
	var ningxiaMap = new Map();
	ningxiaMap.set('银川市',['银川市','0951','105','108','37','39.2']);
	ningxiaMap.set('石嘴山市',['石嘴山市','0952','106','108','38.5','40']);
	ningxiaMap.set('吴忠市',['吴忠市','0953','105.3','108.5','37','38.5']);
	ningxiaMap.set('固原市',['固原市','0954','105','108','35','37']);
	ningxiaMap.set('中卫市',['中卫市','1953','104','107','36','38']);
	myMap.set('宁夏省',ningxiaMap);


	var qinghaiMap = new Map();
	qinghaiMap.set('西宁市',['西宁市','0971','100','103','35','38']);
	qinghaiMap.set('海东市',['海东市','0972','101','103.5','35.5','37.8']);
	qinghaiMap.set('黄南州',['黄南州','0973','100.5','103.5','34','36.5']);
	qinghaiMap.set('海南藏族自治州',['海南藏族自治州','0974','99','102','34.8','36.6']);
	qinghaiMap.set('果洛藏族自治州',['果洛藏族自治州','0975','97.9','102','32.5','35.5']);
	qinghaiMap.set('玉树藏族自治州',['玉树藏族自治州','0976','90','100','31','37']);
	qinghaiMap.set('海西蒙古族藏族自治州',['海西蒙古族藏族自治州','0977','94','100','34','39.2']);
	qinghaiMap.set('海北藏族自治州',['海北藏族自治州','0970','98','103','36','38.5']);
	myMap.set('青海省',qinghaiMap);


	var xizangMap = new Map();
	xizangMap.set('拉萨市',['拉萨市','0891','89.5','92.5','29','30.5']);
	xizangMap.set('日喀则市',['日喀则市','0892','87','90.5','28.4','30']);
	xizangMap.set('林芝市',['林芝市','0894','93','98','29','30']);
	xizangMap.set('昌都市',['昌都市','0895','95','100','30','32']);
	xizangMap.set('那曲市',['那曲市','0896','90','95','30.4','32.4']);
	xizangMap.set('阿里市',['阿里市','0897','80','85','30','33']);
	xizangMap.set('山南市',['山南市','0893','90','94','28','29.6']);
	myMap.set('西藏省',xizangMap);
	

	var taiwanMap = new Map();
	taiwanMap.set('台湾',['台湾','1886','119','125','20','26']);
	myMap.set('台湾省',taiwanMap);
	
	//alert("map length:"+myMap.size());
}

initCityData();
