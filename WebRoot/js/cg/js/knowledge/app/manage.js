$(function(){
    /* 全选 */
    $(".checkall").live('change', function(){
        $(this).parents('table').find('.checkitem').attr('checked', this.checked)
    });
});

//图片缩略图
function setImgShow(classname) {
    var offset_x = 12;
    var offset_y = 18;
    $('.' + classname).mouseover(
            function(e) {
                var href = $(this).attr('data');
                var width = $(this).attr('img-width');
                if (!width) {
                    var tooltip = "<div id='tooltip'><img src='" + href
                            + "' alt='预览图'/></div>";
                } else {
                    var tooltip = "<div id='tooltip'><img width='" + width
                            + "' src='" + href + "' alt='预览图'/></div>";
                }
                $(this).append(tooltip);
                $("#tooltip").css({
                    "position" : "absolute",
                    "top" : (e.pageY + offset_y) + "px",
                    "left" : (e.pageX + offset_x) + "px"
                }).show("fast"); // 设置x坐标和y坐标，并且显示
            }).mouseout(function() {
        $("#tooltip").remove(); // 移除
    }).mousemove(function(e) {
        $("#tooltip").css({
            "top" : (e.pageY + offset_y) + "px",
            "left" : (e.pageX + offset_x) + "px"
        });
    });
}
//删除列表中某一条记录
function dropItem(url,obj){
    if(confirm('确定要删除该条记录吗？')){
        $.ajax({
            url:url,
            type:"post",
            dataType:"json",
            success:function(data){
                if(1==data.status){
                    alert(data.msg);
                    $(obj).parents('tr').remove();
                }else{
                    alert(data.msg);
                }
            }
        });
    }
}
//关闭弹出层
function closeDialog(){
    $(".ui-dialog-close").click();    
}

//检测复选框的值
function checkBox() {
    var chk_value = [];
    $(".checkitem:checked").each(function(){ 
        chk_value.push($(this).val());
    });
    if (chk_value.length == 0) {
        alert('无任何选中项！');
        return false;
    }else{
        if (confirm('确认要执行此操作？')) {
            return chk_value;
        };
    };
}

//批量审核文章
function setState(is_show) {
    var chk_value = checkBox();
    if (chk_value) {
        $.post("/lore.php?app=manageDoc&act=doArticleCheck", { "items":chk_value,"param":is_show} , function(data) {
            alert(data['msg']);
            if(data['success']) {
                closeDialog();
                window.location.reload();
            }
        } , 'json');
    };
}

//批量推荐或取消推荐
function setDigg(is_show) {
    var chk_value = checkBox();
    if (chk_value) {
        $.post("/lore.php?app=manageDoc&act=setDigg&is_show="+is_show , { "ids":chk_value } , function(data) {
            alert(data['msg']);
            if(data['success']) {
                closeDialog();
                window.location.reload();
            }
        } , 'json');
    };
}
