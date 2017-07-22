
// 百度地图API功能
    var map = new BMap.Map('map');
    var poi = new BMap.Point(116.307852,40.057031);
    //map.centerAndZoom(poi, 16);
	 map.centerAndZoom($("#city-name").val());
    map.enableScrollWheelZoom();
    
    var overlays = {};
	overlays.overlay={};
	overlays.label={};
	overlays.marker={};
	var current_overlay,current_label,current_marker;
    //回调获得覆盖物信息
    var overlaycomplete = function(e){
        if ( e.drawingMode == BMAP_DRAWING_POLYGON ) {
			var path_list = e.overlay.getPath();
			$("#block-form").show();
			current_overlay=e.overlay;
			showCityList();
        }
    };

	function cancelOverlay() {
		if (current_overlay&&!current_overlay.id) {
			map.removeOverlay(current_overlay);
		}
		$("#block-form").hide();
		current_overlay=null;
	}
	var current_id=0;
	function submitOverlay() {
		if (current_overlay) {
			var data = {};
			var block_name = $.trim($("#block-name").val());
			var city_id = $("#city-id").val();
			
			if (!block_name) {
				alert("请输入区域名称！");
				return;
			}
			if (current_overlay.id) {
				data.id = current_overlay.id;
			}
			data.name = block_name;
			data.city = city_id;
			data.points = current_overlay.getPath();
			data.note = $.trim($("#block-note").val());
			$.post(
				"/zs.php?app=street&act=editBlock",
				data,
				function(rs) {
					if (!rs.success) {
						alert(rs.msg);
					}
					
					if (rs.success) {
						if (current_overlay.id) {
							updateViewAfterEdit(current_overlay,rs.data);
						}else{
							updateViewAfterAdd(current_overlay,rs.data);
						}
						current_overlay=null
						$("#group-name").val("");
						$("#new-form").hide();
						$("#block-name").val("");
						$("#block-note").val("");
						$("#block-form").hide();
					}
				},
				'json'
			);
			
		}
		
	}


    //实例化鼠标绘制工具
    var drawingManager = new BMapLib.DrawingManager(map, {
        isOpen: false, //是否开启绘制模式
        enableDrawingTool: true, //是否显示工具栏
        drawingToolOptions: {
            anchor: BMAP_ANCHOR_TOP_RIGHT, //位置
            offset: new BMap.Size(5, 5), //偏离值
            scale: 0.5, //工具栏缩放比例
			drawingModes:[ BMAP_DRAWING_POLYGON]
		},
		polygonOptions: styleOptions //多边形的样式
    });

    
    //添加鼠标绘制工具监听事件，用于获取绘制结果
    drawingManager.addEventListener('overlaycomplete', overlaycomplete);
	function addMenu(marker) {
		
	}

window.onload=function(){
drawingManager.setDrawingMode(BMAP_DRAWING_POLYGON);
//drawingManager.enableCalculate();
}

function getMenu(pid) {
	var menu = new BMap.ContextMenu();
	menu.addItem( getEditMenuItem(pid));
	menu.addItem( getDeleteMenuItem(pid));
	return menu;

}

function getDeleteMenuItem(pid) {
	return new BMap.MenuItem("删除", function(){
		if (!confirm("确认删除该区域？")) {
			return;
		}
		$.post(
			"/zs.php?app=street&act=deleteBlock",
			{id:pid},
			function(rs){
				if (rs.success) {
					ppid = "p"+pid;
					for (i in overlays) {
						map.removeOverlay(overlays[i][ppid]);
					}
				}else{
					alert(rs.msg);
				}
			},
			'json'
		);
		
	});
}

function getEditMenuItem(id) {
	return new BMap.MenuItem("编辑", function(){
		$("#block-form").show();
			showCityList(id);
			$("#block-name").val(overlays.label['p'+id].getTitle());
			current_overlay = overlays.overlay['p'+id];
			$.post(
				"/zs.php?app=street&act=getBlockInfo",
				{id:id},
				function(info){
					$("#block-note").val(info.note);
				},
				'json'
			);
	});
}

function updateViewAfterAdd(current_overlay,info) {
	var points,point,label,marker;
	current_overlay.id = info.id;
	points = current_overlay.getPath();
	point = new BMap.Point(info.center.lng,info.center.lat);
	label= new BMap.Label(info.name);
	label.setPosition(point);
	marker= new BMap.Marker(point);
	label.setStyle(label_style);
	label.setTitle(info.name);
	label.pid=info.id;
	marker.pid=info.id;
	marker.addContextMenu(getMenu(info.id));
	
	map.addOverlay(label);
	map.addOverlay(marker);
	overlays.overlay['p'+info.id] = current_overlay;
	overlays.label['p'+info.id] = label;
	overlays.marker['p'+info.id] = marker;
}


function updateViewAfterEdit(current_overlay,info) {
	overlays.label['p'+info.id].setContent(info.name);
	overlays.label['p'+info.id].setTitle(info.name);
}
function showGroupForm() {
	$("#new-form").show("normal");
	$(".new-btn").hide("normal");
}

function submitGroupForm() {
	
}

function showCityList(id,upid) {
	var info={};
	if (id) {
		info.id=id;
	}
	if (upid) {
		info.upid=upid;
	}
	$.post(
		'/zs.php?app=street&act=getCityList',
		info,
		function(data) {
			$("#province-id").val(data.province_id);
			var init_html = "";
			for (i in data.city_list ) {
					init_html += '<option value="'+data.city_list[i].id+'">'+data.city_list[i].name+'</option>';
			}
			$("#city-id").html(init_html);
			$("#city-id").val(data.city_id);
		},
		'json'
	);
}

$("#province-id").live("change",function(){
	showCityList(null, $(this).val());
})