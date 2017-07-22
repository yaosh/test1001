$(function(){
    //选择文档分类
    $("[name='type']").live("change", function(){
        var type = $('input:radio[name="type"]:checked').val();
        if(1 == type){
            $('.block_a').show();
            $('.block_b').hide();
            $('.block_c').hide();
            $('.block_d').hide();
        }else if(2 == type){
            $('.block_a').hide();
            $('.block_b').show();
            $('.block_c').hide();
            $('.block_d').hide();
        }else if(3 == type){
            $('.block_a').hide();
            $('.block_b').hide();
            $('.block_c').show();
            $('.block_d').hide();
        }else if(4 == type){
            $('.block_a').hide();
            $('.block_b').hide();
            $('.block_c').hide();
            $('.block_d').show();
        }else{
            $('.block_a').hide();
            $('.block_b').hide();
            $('.block_c').hide();
            $('.block_d').hide();
        }
    });
//    //选择文档分类
//    $("[name='city_flg']").live("change", function(){
//        var cityFlg = $('input:radio[name="city_flg"]:checked').val();
//        if(0 == cityFlg){
//            $('.city1').show();
//            $('.city2').hide();
//        }else if(1 == cityFlg){
//            $('.city1').hide();
//            $('.city2').show();
//        }else{
//            $('.city1').hide();
//            $('.city2').hide();
//        }
//    });
    
    // 显示标签选择窗口
    $(".tag-select").live("focus", function(){
        var $this = $(this),
        taglist = $this.next(".r-tag-list"),
        $others = $this.parents(".progressWrapper").siblings().find(".r-tag-list"),
        td = $(this).parent("td"),
        paddingTop =  parseInt(td.css("paddingTop")),
        width = taglist.prev(".tag-select").outerWidth(),
        top = taglist.prev(".tag-select").outerHeight();
        taglist.show();
        $others.hide();
        taglist.css({
            top: top + paddingTop,
            width: width
        });
    });
    
    // 点击空白处关闭标签选择窗口
    $(document).live("click",function(e){
        var target  = $(e.target);
        if(target.closest(".upload-tag-td").length == 0){
            $(".r-tag-list").hide();
        }
    });
    
    // 更新input
    $(".r-tag-list li").live("click",function(){
        var $this = $(this), 
        $input = $this.parents(".r-tag-list").prev(".tag-select"),
        $others = $input.parents(".progressWrapper").siblings().find(".r-tag-list"),
       
        tag = $.trim($this.text()),
        tags = $input.val(),
        tags_arr = tags.length > 0 ? (tags.split(',')) : [],
        len = tags_arr.length,
        i = 0;
        
        if ( tag !== '' && tag !== '关闭' ) {
            tag = tag.replace(/[，| |,,]/g,",");// 中文逗号替换
            
            if ( len === 0 ) {
                tags = tag;
            } else {
                //检查input中是否已经存在该tag
                for ( i ; i < len; i ++) {
                    if ( tag === tags_arr[i] ) {
                        return;
                    }
                }
                tags = tags + ',' + tag;
            }
            //更新input value
            $input.val(tags);
        }
    });
    
    //单个文档提交
    $(".btn-submit").live("click", function(){
        var $_form = $(this).parents('.form'),
            id = $.trim($_form.find("[name='id']").val()),
            title = $.trim($_form.find("[name='title']").val()),
            author = $_form.find("[name='author']").val(),
            summary = $_form.find("[name='summary']").val(),
            tags = $_form.find("[name='tags']").val(),
            collect_id = $_form.find("[name='collect_id']").val(),
            origin = $_form.find("[name='origin']").val(),
            type = $_form.find('input:radio[name="type"]:checked').val(),
            standard_type = $_form.find("[name='standard_type']").val(),
            profile_type = $_form.find("[name='profile_type']").val(),
            process_node = $_form.find("[name='process_node']").val(),
            process_node_other = $_form.find("[name='process_node_other']").val(),
            project_type = $_form.find("[name='project_type']").val(),
            project_type_other = $_form.find("[name='project_type_other']").val(),
            city = $_form.find("[name='city']").val(),
            city_other = $_form.find("[name='city_other']").val(),
//            developer_id = $_form.find("[name='developer_id']").val(),
            developer_name = $_form.find("[name='developer_name']").val(),
//            developer_other = $_form.find("[name='developer_other']").val(),
            year = $_form.find("[name='year']").val(),
            app_node = $_form.find("[name='app_node']").val(),
            app_type = $_form.find("[name='app_type']").val(),
            app_region = $_form.find("[name='app_region']").val(),
            file_path = $_form.find("[name='file_path']").val(),
            img = $_form.find("[name='img_path']").attr('src'),
            error_flg = false;
        
        $_form.find(".error-tip").html('');
        //表单验证
        if(''==title){
            $_form.find("[name='title']").parents('tr').find(".error-tip").html('标题不能为空');
            error_flg = true;
        }
        if(''==summary){
            $_form.find("[name='summary']").parents('tr').find(".error-tip").html('简介不能为空');
            error_flg = true;
        }
//        if(''==collect_id || 0==collect_id){
//            $_form.find("[name='collect_id']").parents('tr').find(".error-tip").html('请选择专辑');
//            error_flg = true;
//        }
        if(summary.length > 250){
            $_form.find("[name='summary']").parents('tr').find(".error-tip").html('简介只能在250字以内');
            error_flg = true;
        }
        if(tags.replace(/，/g, ',').split(',').length < 3){
            $_form.find("[name='tags']").parents('tr').find(".error-tip").html('至少添加3个标签');
            error_flg = true;
        }
        if(''==origin || 0==origin){
            $_form.find("[name='origin']").parents('tr').find(".error-tip").html('请选择文档来源');
            error_flg = true;
        }
        if(1==type){
            if(1 != origin){
                $_form.find("[name='origin']").parents('tr').find(".error-tip").html('文档分类是城格标准时，文档来源必须是内部生产的文档');
                error_flg = true;
            }
            if(''==standard_type || 0==standard_type){
                $_form.find("[name='standard_type']").parents('tr').find(".error-tip").html('请选择类型');
                error_flg = true;
            }
        }else if(4==type){
            if(''==profile_type || 0==profile_type){
                $_form.find("[name='profile_type']").parents('tr').find(".error-tip").html('请选择类型');
                error_flg = true;
            }
        }else if(2==type){
            if(''==process_node || 0==process_node){
                $_form.find("[name='process_node']").parents('tr').find(".error-tip").html('请选择业务流程节点');
                error_flg = true;
            }
            if(99==process_node && ''==process_node_other){
                $_form.find("[name='process_node']").parents('tr').find(".error-tip").html('请填写其他业务流程节点');
                error_flg = true;
            }
            if(99==project_type && ''==project_type_other){
                $_form.find("[name='project_type']").parents('tr').find(".error-tip").html('请填写其他项目类型');
                error_flg = true;
            }
//            if(9999==developer_id && ''==developer_other){
//                $_form.find("[name='developer_id']").parents('tr').find(".error-tip").html('请填写其他开发商');
//                error_flg = true;
//            }
        }else if(3==type){
            if(''==app_node || 0==app_node){
                $_form.find("[name='app_node']").parents('tr').find(".error-tip").html('请选择流程节点');
                error_flg = true;
            }
        }else{
            $_form.find("[name='type']").parents('tr').find(".error-tip").html('请选择文档分类');
            error_flg = true;
        }
        if(error_flg){
            return;
        }
        
        $.ajax({
            type: 'POST',
            url: "/lore.php?app=upload&act=edit",
            data: {
                'id':id,
                'title':title,
                'author':author,
                'summary':summary,
                'tags':tags,
                'collect_id':collect_id,
                'origin':origin,
                'type':type,
                'standard_type':standard_type,
                'profile_type':profile_type,
                'process_node':process_node,
                'process_node_other':process_node_other,
                'project_type':project_type,
                'project_type_other':project_type_other,
                'city':city,
                'city_other':city_other,
//                'developer_id':developer_id,
                'developer_name':developer_name,
//                'developer_other':developer_other,
                'year':year,
                'app_node':app_node,
                'app_type':app_type,
                'app_region':app_region,
                'file_path':file_path,
                'img':img
            },
            dataType: 'json',
            success: function(data){
                if(data.success){
                    if(file_path){
                        alert("操作成功，等待管理员审核！");
                        location.href = "/lore/user/" + $("#loginUser").attr('data-id') + "/doc/";
                    }else{
                        alert("操作成功！");
                        if(1 == $("#upload-area").length){
                            location.href = "/lore/detail/" + id + ".html";
                        }else{
                            $(".ui-dialog-close").click();
//                            if(1 != $_form.find("#open_type").val()){
                                window.location.reload();
//                            }
                        }
                    }
                }else{
                    if(data.data){
                        $_form.find("[name='"+data.data+"']").parents('tr').find(".error-tip").html(data.msg);
                    }else{
                        alert(data.msg);
                    }
                }
            }
        });
    });
    //选择其他
    $("[name='process_node']").live("change", function(){
        setOtherInput($(this), 'process_node_other');
    });
    $("[name='project_type']").live("change", function(){
        setOtherInput($(this), 'project_type_other');
    });
//    $("[name='developer_id']").live("change", function(){
//        setOtherInput($(this), 'developer_other');
//    });
//    //开发商选择
//    $("[name='developer_name']").live("click", function(){
//        var $_this = $(this),
//            pid = $_this.parents('.progressWrapper').attr('id');
//            href= "/lore.php?app=developer&act=dialogDeveloperList&width=500&height=580&pid=" + pid;
//        
//        $.dialog.dialogShow(href, "选择开发商");
//    });
});

function setOtherInput($this, otherInputName){
    var $_form = $this.parents('.form'),val = $this.val();
    if(99==val){
        $_form.find("[name='"+otherInputName+"']").val('').show();
    }else{
        $_form.find("[name='"+otherInputName+"']").val('').hide();
    }
}
