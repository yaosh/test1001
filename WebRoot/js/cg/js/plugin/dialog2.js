/**
 * Copyright 2012, Athena UI Library
 * MIT Licensed
 * @ message
 * @author imbingdian@gmail.com
 * @build 2012/12/07
 *
 * @require animate/easing.js
 *
 * @usage：
 * <a href="javascript:void(0)" class="dialog" data-url="url?height=400&width=710&dialog_type=ajax&show_title=true&animate=true" data-title="Title">show dialog</a>
 * $.dialog.dialogShow(url, title);
 * $.dialog.dialogClose();
 */

/*global jQuery:false */
;(function($, undefined) {
	
	/**
	 * Dialog CLASS definition
	*/
	function Dialog() {
		this.flag = true;
	}
	
	/**
	 * parseQuery
	 * @param {String} query
	 */
	function _parseQuery(query) {
		var params = {},
			pairs = query.split(/[;&]/),
			len = pairs.length,
			i,
			key_val,
			key,
			val;
			
		if ( ! query ) {
			return params; //return empty object
		}
		
		for ( i = 0; i < len; i++ ) {
			key_val = pairs[i].split('=');
			if ( !key_val || key_val.length !== 2 ) {
				continue;
			}
			key = decodeURI( key_val[0] );
			val = decodeURI( key_val[1] );
			val = val.replace(/\+/g, ' ');
			params[key] = val;
		}
		
		return params;
	}
	
	/**
	 * Public method definition
	 * @param {Object HTMLElement} el
	 * @param {Object} options 
	*/
	Dialog.prototype = {
		constructor: Dialog,
		
		/**
		 * dialogShow
		 * @param {String} url
		 * @param {String} title
		 */
		dialogShow: function(url, title) {
			var self = this,
				query_string = url.replace(/^[^\?]+\??/,''),//？id=1002&width=800&height=450,
				params = _parseQuery(query_string),
				width = params.width,//dialog 宽度
				height = params.height,//dialog 高度
				animate = params.animate || 'false',//string: default false
				type = params.type || 'ajax',//string: ajax/url/flash/image, default ajax
				//callback = params.callback,//callback 
				show_title = params.show_title || 'true',//string: default true
				title_height = params.title_height || 61,
				margin_left = -width/2,
				win_height = $(window).height(),
				scroll_top = $(window).scrollTop(),
				_top = (win_height - height) * 0.4 + scroll_top,
				top_pos = _top > 0 ? _top : scroll_top,
				overlay_html = '<div id="overlay"></div>',
				dialog_html,
				title_html = '',
				frame_height,
				overlay_exist = $('#overlay').length !== 0 ? true : false;
				
			//show
			function show(html) {
				$('body').append(html);
				
				//ainamate
				if ( animate === 'true') {
					$('.ui-dialog').animate({
						top: top_pos
					}, 500);
				} else if ( animate === 'fade') {
					$('.ui-dialog').css({
						display: 'none',
						top: top_pos
					});
					$('.ui-dialog').fadeIn();
				} else {
					$('.ui-dialog').css({
						top: top_pos
					});
				}
			}
			
			//if the overlay div exist, remove $('.ui-dialog')
			if ( overlay_exist ) {
				$('.ui-dialog').remove();
				overlay_html = '';
			}
			
			
			//show title
			if ( show_title === 'true' ) {
				title_html = '<div class="ui-dialog-hd">'+ title +'</div>';
			}
			
			//url type
			if ( type === "ajax" ) { //ajax
				$.ajax({
					url: url,
					dataType: 'html',
					success: function(data) {
						dialog_html = '<div class="ui-dialog" style="width:'+ width +'px; height:'+ height +'px; margin-left:'+ margin_left +'px;">'+
									title_html+
									'<div class="ui-dialog-bd clear">'+
									data+
									'</div><div class="ui-dialog-ft"></div><a href="javascript:void(0);" class="ui-dialog-close">X</a></div>';
						
						show( overlay_html + dialog_html);
						self.flag = true;
					},
					error: function() {
						dialog_html = '加载失败，请稍后再试。';
					}
				});
			} else if ( type === "url" ) { //url
				//frame height
				if ( show_title === 'true' ) {
					frame_height = height - title_height;
				} else {
					frame_height = height;
				}
				dialog_html = '<div class="ui-dialog" style="width:'+ width +'px; height:'+ height +'px; margin-left:'+ margin_left +'px;">'+ 
							title_html+ 
							'<div class="ui-dialog-bd clear">'+ 
							'<iframe frameborder="0" width="'+ width +'" height="'+ frame_height +'" scrolling="no" src="' +url+ '"></iframe>'+ 
							'</div><div class="ui-dialog-ft"></div><a href="javascript:void(0);" class="ui-dialog-close">X</a></div>';
				
				show( overlay_html + dialog_html);
				self.flag = true;
			}

		},
		
		/**
		 * dialog close
		 */
		dialogClose: function() {
			var len = $('.ui-dialog').length;
			
			if ( len === 1) {
				//如果有1个ui-dialog时，遮罩层remove
				$('.ui-dialog').remove();
				$('#overlay').remove();
			} else {
				//如果有多个ui-dialog时，遮罩层不remove
				$('.ui-dialog:last').remove();
			}
		},
		
		/**
		 * init
		 */
		init: function() {
			var self = this;
			
			$('.dialog').die().live('click', function() {
				if ( self.flag ) {
					self.flag = false; 
					
					var title = $(this).attr('data-title'),
					url = $(this).attr('data-url');
					
					self.dialogShow(url, title);
				}
				
			});
			
			$('.ui-dialog-close').live('click', function() {
				self.dialogClose();
				self.flag = true;
			});
			
		}
	};
	
	var dialog = new Dialog();
	
	$.dialog = dialog;
	$.dialog.init();
}(jQuery));

/*
 * Todo
 * 重构
 * add img support
 * add video support
 * close style
 * callback
 */

/* dialog中get方式提交表单
 * author 刘小祥
 * date 2013-4-7
 */
function dialogSubmit(form,width,height){
	var url=$(form).attr("action");
	$(form).find("input[type=text]").each(function(){
		url += "&"+$(this).attr("name")+"="+$(this).val().replace("&","%26");
	});
	$(form).find("input[type=hidden]").each(function(){
		url += "&"+$(this).attr("name")+"="+$(this).val().replace("&","%26");
	});
	$(form).find("select").each(function(){
		url += "&"+$(this).attr("name")+"="+$(this).val().replace("&","%26");
	});
	width = width ? width : 500;
	height = height ? height : 580;
	url += "&height="+height+"&width="+width;
	$.dialog.dialogShow(url,$(".ui-dialog-hd").text());
	return false;
}
