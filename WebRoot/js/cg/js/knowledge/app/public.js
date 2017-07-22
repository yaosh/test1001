$(function(){
	$('#search-form').submit(function(){
		var param = $.trim($("#search-field").val());
		if(param){
			window.open("/lore/search/?keyword="+param);
		}else{
			alert('请输入关键字');
			$(".search-field").focus();
		}
		return false;
	});
	//省份城市二级联动
	$(".prov-select").live("change",function(){
		var $_this = $(this),id=$.trim($_this.val());
		$.ajax({
			type: 'POST',
			url: "/lore.php?app=upload&act=provCityLink",
			data: {"id":id},
			dataType: 'json',
			success: function(data){
				//城市
				$_this.parents('tr').find(".city-select").html(data.html);
			}
		});
	});
	//部门人员二级联动
	$(".dep-select").live("change",function(){
		var $_this = $(this),id=$.trim($_this.val());
		$.ajax({
			type: 'POST',
			url: "/lore.php?app=manageCount&act=ajaxDepMemberLink",
			data: {"dep_id":id},
			dataType: 'json',
			success: function(data){
				//人员
				$(".mem-select").html(data.html);
			}
		});
	});
});

function loginOut(){
	if(confirm('确认要退出吗')){
		$.post( "/index.php?act=login&do=out&is_ajax=1", null , function(data){
				if (data.success) {
					location.href="/index.php?act=login";
					return;
				}
				alert(data.msg);
			},'json'
		);
	}
}

//ajax file upload
/**
* fileId  input file  的id
* fileName input file 的name
* showFiledId 要显示图片的id
* type  区分不同图片的类型
* sortno 切过图的图的序号  图片尺寸管理中的'序号'字段值
*/
function ajaxFileUpload(fileId,fileName,showFileId,type,sortno){
	$.ajaxFileUpload({
		url: '/index.php?app=uploadFile&act=upload&type='+type+'&fileName='+fileName+'&sortno='+sortno,  //需要链接到服务器地址
		secureuri: false,
		fileElementId: fileId,
		dataType: 'json',
		success: function (data){
			if(1==data.status){
				$('#'+showFileId).attr('src',data.img);
				$('#'+showFileId).show(); 
			}else{
				alert('上传失败');
			}
		}
 });
}