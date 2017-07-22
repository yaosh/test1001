$(function(){
   $(".thumbnail").on("mouseenter", ".pic", function() {
        $(this).parent().addClass("thumbnail-hover")
   });
   $(".thumbnail").on("mouseleave", ".pic", function() {
        $(this).parent().removeClass("thumbnail-hover")
   });
   $(".more-info").bind("mouseenter",function(){
        $(this).parent().addClass("thumbnail-hover")
   })
   $(".more-info").bind("mouseleave",function(){
        $(this).parent().removeClass("thumbnail-hover")
   });
    $(".thumbnail:nth-child(4n)").css({
        "padding-right":"0"
    });
    //编辑
    $(".btn-edit").bind("click",function(){
        if(1 == $(this).parents(".thumbnail").find(".i-check").length){
            alert("文档审核中，暂时不可编辑！");
            return;
        }
        location.href = "/lore/edit/" + $(this).attr("data-id") + ".html";
    });
    //删除
    $(".btn-del").bind("click",function(){
        if(1 == $(this).parents(".thumbnail").find(".i-check").length){
            alert("文档审核中，暂时不可删除！");
            return;
        }
        if(confirm('确认要删除吗？')){
            $.ajax({
                type: 'POST',
                url: "/lore.php?app=upload&act=drop",
                data: {'id':$(this).attr("data-id")},
                dataType: 'json',
                success: function(data){
                    alert(data.msg);
                    if(data.success){
                        location.reload();
                    }
                }
            });
        }
    });
    //收藏
    $(".btn-favorite").bind("click",function(){
        var $_this=$(this),art_id=$_this.attr("data-id"),type_flg=$_this.hasClass("btn-collect")?2:1;
        $.ajax({
            type: 'POST',
            url: "/lore.php?app=detail&act=favorite",
            data: {'art_id':art_id,'type_flg':type_flg},
            dataType: 'json',
            success: function(data){
                if(data.success){
                    if(1==type_flg){
                        $_this.addClass("btn-collect");
                        $_this.html("<span class=\"icon-heart icon-16\"></span>取消收藏");
                    }else{
                        $_this.removeClass("btn-collect");
                        $_this.html("<span class=\"icon-heart icon-16\"></span>收藏");
                    }
                }else{
                    alert(data.msg);
                }
            }
        });
    });
    //专辑删除
    $(".btn-board-del").bind("click",function(){
        if(confirm('确认要删除吗？')){
            $.ajax({
                type: 'POST',
                url: "/lore.php?app=collect&act=drop",
                data: {'id':$(this).attr("data-id")},
                dataType: 'json',
                success: function(data){
                    alert("操作成功！");
                    location.reload();
                }
            });
        }
    });
    $('.thumbnail .more-info').on('click', function() {
        var url = $(this).parents('.thumbnail').find('.caption a').attr('href');
        window.open(url);
    });
    $('.do-btns a').on('click', function(e) {
        e.stopPropagation();
    });
});
