/*
 * common js
 */
$(function() {
    
    /*
    * gotop
    */
    $('#gotop').bind('click', function() {
        var _height = $(window).height() + $(this).offset().top,
            _duration= Math.ceil(_height/100);
        $('body,html').animate( {
            scrollTop : 0
        }, _duration);
    });
   
   
    /*
    * toggle toc
    */
    var $toolbarToc = $('.toolbar-toc');
    
    $('#toogle-toc').toggle(function() {
        var w = $(window).width(),
            left;
        
        if ( w > 1380 ) {
            left = 26;
        } else {
            left = -171;
        }
        
        $('.toolbar-toc').css({
            left: left
        });
        
        $toolbarToc.show();
    }, function() {
        $toolbarToc.hide();
    });
   
});

function emptySearch() {
	if ($("#search_wd").val() == "") {
		$("#search_wd").focus();
		return false;
	}
	return true;
}

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