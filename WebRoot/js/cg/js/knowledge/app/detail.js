$(function(){
//    //印象点击
//    $(".impression-list a").live("click", function(){
//        var $_this = $(this),name = $_this.html(),articleId = $("#id").val();
//        $.ajax({
//            type: 'POST',
//            url: "/lore.php?app=detail&act=dialogAddFeel",
//            data: {'name':name,'articleId':articleId},
//            dataType: 'json',
//            success: function(data){
//                alert(data.msg);
//                if(data.success){
//                    $.dialog.dialogClose();
//                    $('.impression-list').html(data.data);
//                }
//            }
//        });
//    });
    //添加评论
    $(".btn-comment-add").bind("click", function(){
        var articleId = $("#id").val(),content = $("#comment-content").val();
        $.ajax({
            type: 'POST',
            url: "/lore.php?app=detail&act=addComment",
            data: {'articleId':articleId,'content':content},
            dataType: 'json',
            success: function(data){
//                alert(data.msg);
                if(data.success){
                    $('#comment-content').val('');
                    $('.comment-list ul').html(data.data + $('.comment-list ul').html());
                    $('#comment-num').html(parseInt($('#comment-num').html())+1);
                }else{
                    alert(data.msg);
                }
            }
        });
    });
    //删除评论按钮
    $(".comment-list").on("mouseenter", "li", function() {
        $(this).find(".btn-delete").show();
    });
    $(".comment-list").on("mouseleave", "li", function() {
        $(this).find(".btn-delete").hide();
    });
    //删除评论
    $(".btn-delete").live("click", function(){
        var $_this = $(this),id = $_this.attr('data-id');
        $.ajax({
            type: 'POST',
            url: "/lore.php?app=detail&act=delComment",
            data: {'id':id},
            dataType: 'json',
            success: function(data){
                alert(data.msg);
                if(data.success){
                    $_this.parents('li').remove();
                    $('#comment-num').html(parseInt($('#comment-num').html())-1);
                }
            }
        });
    });
    //下载
    $(".btn-download").bind("click", function(){
        var $_this=$(this),id=$("#id").val(),url="/lore.php?app=detail&act=downloadFile";
        
        $_this.attr("disabled", "disabled");
        
        $.ajax({
            type: 'POST',
            url: url,
            data: {'id':id},
            dataType: 'json',
            success: function(data){
                if(data.success){
                    url = url + "&id=" + id;
                    location.href = url;
                }else{
                    alert(data.msg);
                }
                $_this.removeAttr("disabled");
            }
        });
    });
    //收藏
    $(".btn-favorite").bind("click",function(){
        var $_this=$(this),art_id=$("#id").val(),type_flg=$_this.children(".icon-heart").hasClass("icon-heart-active")?2:1;
        $.ajax({
            type: 'POST',
            url: "/lore.php?app=detail&act=favorite",
            data: {'art_id':art_id,'type_flg':type_flg},
            dataType: 'json',
            success: function(data){
                if(data.success){
                    if(1==type_flg){
                        $_this.children(".icon-heart").addClass("icon-heart-active");
                        $("#favorite-text").text("已收藏")
                        $('#favorite-num span').html(parseInt($('#favorite-num span').html())+1);
                    }else{
                        $_this.children(".icon-heart").removeClass("icon-heart-active");
                        $("#favorite-text").text("收藏")
                        $('#favorite-num span').html(parseInt($('#favorite-num span').html())-1);
                    }
                }else{
                    alert(data.msg);
                }
            }
        });
    });
    //推送到邮箱
    $(".btn-digg2email").bind("click",function(){
        var id=$("#id").val();
        $.ajax({
            type: 'POST',
            url: "/lore.php?app=detail&act=digg2email",
            data: {'id':id},
            dataType: 'json',
            success: function(data){
                if(data.success){
                    $.dialog.dialogShow('/lore.php?app=detail&act=dialogDigg2email&id='+id+'&height=420&width=710', '推送到邮箱');
                }else{
                    alert(data.msg);
                }
            }
        });
    });
    //推荐
    $(".btn-digg").bind("click",function(){
        var $_this=$(this),art_id=$("#id").val(),flg=$_this.children(".icon-thumbs-up").hasClass("icon-thumbs-up-active")?0:1;
        $.ajax({
            type: 'POST',
            url: "/lore.php?app=detail&act=digg",
            data: {'obj_id':art_id,'flg':flg},
            dataType: 'json',
            success: function(data){
                if(data.success){
                    if(1==flg){
                        $_this.children(".icon-thumbs-up").addClass("icon-thumbs-up-active");
                        $("#digg-text").text("已推荐")
                        $('#digg-num span').html(parseInt($('#digg-num span').html())+1);
                    }else{
                        $_this.children(".icon-thumbs-up").removeClass("icon-thumbs-up-active");
                        $("#digg-text").text("推荐")
                        $('#digg-num span').html(parseInt($('#digg-num span').html())-1);
                    }
                }else{
                    alert(data.msg);
                }
            }
        });
    });
    //设为封面
    $(".set-cover-link").bind("click",function(){
        var $_this=$(this),docId=$_this.attr('doc-id'),dataId=$_this.attr('data-id');
        $.ajax({
            type: 'POST',
            url: "/lore.php?app=detail&act=setCover",
            data: {'doc_id':docId,'data_id':dataId},
            dataType: 'json',
            success: function(data){
                if(data.success){
                    location.reload()
                }else{
                    alert(data.msg);
                }
            }
        });
    });
    
    if(flv){
        $('#documentViewer').FlexPaperViewer(
                { config : {
                    SWFFile : flv,
                    Scale : 1.0,
                    ZoomTransition : 'easeOut',
                    ZoomTime : 0.5,
                    ZoomInterval : 0.2,
                    FitPageOnLoad : false,
                    FitWidthOnLoad : true,
                    FullScreenAsMaxWindow : false,
                    ProgressiveLoading : true,
                    MinZoomSize : 0.5,
                    MaxZoomSize : 2,
                    SearchMatchAll : false,
                    InitViewMode : 'Portrait',
                    RenderingOrder : 'flash',
                    StartAtPage : '',
                    
                    ViewModeToolsVisible : true,
                    ZoomToolsVisible : true,
                    NavToolsVisible : true,
                    CursorToolsVisible : true,
                    SearchToolsVisible : true,
                    WMode : 'transparent',
                    localeChain: 'zh_CN',
                    
                    jsDirectory: staticUrl
                }}
        );
    }
});

//图片缩略图
function setImgShow(classname) {
    var offset_x = 12;
    var offset_y = 18;
    $('.' + classname).mouseover(function(e) {
        var href = $(this).attr('data');
        var width = $(this).attr('img-width');
        if (!width) {
            var tooltip = "<div id='tooltip'><img src='" + href
                    + "' alt='预览图'/></div>";
        } else {
            var tooltip = "<div id='tooltip'><img style='width:" + width
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