// JavaScript Document
$(function(){
	$(".treeDo").live('click' , function(){
		var id = $(this).attr('id');
		var type = $(this).attr('type');
		if(type != "drop"){
			edit(id , type) ;
		}else{
			drop(id);
		}
	});
});

function checkForm(){
	var id = $("#id").val();
	var name = $.trim($("#name").val());
	var level = $.trim($("#level").val());
	
	if(!name){
		alert('请填写职务名称');
		$("#name").focus();
		return false ;
	}
	if(!level){
		alert('请填写职务等级');
		$("#level").focus();
		return false ;
	}else{
		if(level <= 0 || level >= 1000){
			alert('职务等级必须大于0，小于1000 ');
			$("#level").focus();
			return false ;
		}	
	}
	pButton(1);
	$.post(aUrl+"&act=edit" , {"id":id , "name":name , "level":level } , function(data){
		alert(data.msg);
		if(data.success){
			closeDialog();
			f5('');
		}else{
			pButton(0);	
		}
	} , 'json');
	
	return false ;
}

//添加与修改
function edit(id , type){
	var dataUrl = "?app="+APP+"&act=edit&id="+id+"&height=400&width=500" ;
	var title = (type == "add") ? "添加职务" : "编辑职务" ;
	initDialog(dataUrl , title) ;
}

function drop(id) {
	if(confirm('确认要删除吗')){
		$.post(
			aUrl+"&act=drop",
			{ id:id },
			function (data){
				alert(data.msg);
				if(data.success){
					dropRemove(id);
				}
			},"json"
		);
	}
}