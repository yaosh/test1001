// JavaScript Document
$(function(){
	$(".cityDo").live('click' , function(){
		var id = $(this).attr('id');
		var type = $(this).attr('type');
		if(type != "drop"){
			edit(id , type) ;
		}else{
			drop(id);
		}
	});
});

function updateCache(){
	if(confirm('确认要更新缓存吗')){
		pButton(1);
		$.post(aUrl+"&act=edit" , {"action":"updateCache"} , function(data){
			alert(data.msg);
			pButton(0);
		} , 'json');
	}
}
function checkForm(){
	var id = $("#id").val();
	var parent_id 	= $.trim($("#parent_id").val()) ;
	var title 		= $.trim($("#title").val()) ;
	var app 		= $.trim($("#app").val()) ;
	var act 		= $.trim($("#act").val()) ;
	var parameter 	= $.trim($("#parameter").val()) ;
	var sort_order 	= $.trim($("#sort_order").val()) ;
	var is_public 	= $('input:radio[name="is_public"]:checked').val() ;
	var is_menu	 	= $('input:radio[name="is_menu"]:checked').val() ;
	if(!title){
		alert('您还没有填写中文描述');
		$("#title").focus();
		return false ;
	}	
	
	$.post(aUrl+"&act=edit" , {"id":id , "parent_id":parent_id , "title":title , "app1":app , "act1":act , "parameter":parameter , "sort_order":sort_order , "is_public":is_public , "is_menu":is_menu} , function(data){
		alert(data.msg);
		if(data.success){
			closeDialog();
			return false ;
		}
	} , 'json');
	
	return false ;
}

function edit(id , type){
	var dataUrl = "?app=operate&act=edit&id="+id+"&doType="+type+"&height=400&width=500" ;
	var title = (type == "add") ? "添加操作" : "编辑操作" ;
	initDialog(dataUrl , title) ;
}

function drop(id) {
	if(confirm('只能删除无下级城市的地区，确认要删除吗')){
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

function getChilds(obj){
    var status 	= obj.attr('status');
    var id 		= obj.attr('fieldid');
    var pid 	= obj.parent('td').parent('tr').attr("class");
    var src 	= $(obj).attr('src');
	var path 	= RES_URL+"/img/admin/" ;
    if(status == 'open'){
        var pr 		= $(obj).parent('td').parent('tr');
        var selfurl = aUrl + "&isChild=1";
        var sr  	= pr.clone();
        var td2 	= sr.find("td:eq(2)");
        td2.prepend("<img class='preimg' src='"+path+"vertline.gif'/>")
                        .find("img[ectype=flex]")
                        .remove()
                        .end()
                        .find('span')
                        .remove();
        var img_count 	= td2.children("img").length;
        var td2html 	= td2.html();
		
		
		$.post(aUrl , {"id":id} , function(data){
			var table = "";
			if(data){
				var res = eval('(' + data + ')');
				
				for(var i = 0; i < res.length; i++){
					var img = "";
					if(res[i].level < 3){
						img = "<img src='"+path+"tv-expandable.gif' ectype='flex' status='open' fieldid="+res[i].id+" onclick='getChilds($(this))'>" ;
					}else{
						img = "<img src='"+path+"tv-item.gif'>" ;
					}
					img = img + "<span class='node_name'>&nbsp;&nbsp;" + res[i].title + "</span>";
					var drop = "" ;
					if (res[i].level == 1){
						drop = 	"<a href='javascript:void(0);' class='cityDo' id='"+res[i].id+"' type='add'>添加</a> " ;
					}
					table += 	"<tr class='"+pid+" row"+id+"' id='tr_"+res[i].id+"'>" + 
								"<td><input type='checkbox' value='"+res[i].id+"' class='checkitem' id='id_"+res[i].id+"' name='checkbox'></td>" + 
								"<td>"+res[i].id+"</td>" + 
								"<td class='node' style='padding-left:10px;text-align:left'>" + td2html+img + "</td>" + 
								"<td>"+res[i].sort_order+"</td>" + 
								"<td>" + drop +  
									"<a href='javascript:void(0);' class='cityDo'  id='"+res[i].id+"' type='edit'>编辑</a> " + 
									"<a href='javascript:void(0);' class='cityDo'  id='"+res[i].id+"' type='drop'>删除</a> " + 
								"</td>" + 
								
								"</tr>";
				}
				pr.after(table);
				$('img[ectype="inline_edit"]').unbind('click');
			}								   
		} );
		obj.attr('src',src.replace("tv-expandable","tv-collapsable"));
        obj.attr('status','close');
    }
	
    if(status == 'close'){
        $('.row' + id).hide();
        obj.attr('src',src.replace("tv-collapsable","tv-expandable"));
        obj.attr('status','open');
    }	
}