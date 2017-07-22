/*
	A simple class for displaying file information and progress
	Note: This is a demonstration only and not part of SWFUpload.
	Note: Some have had problems adapting this class in IE7. It may not be suitable for your application.
*/

// Constructor
// file is a SWFUpload file object
// targetID is the HTML element id attribute that the FileProgress HTML structure will be added to.
// Instantiating a new FileProgress object with an existing file will reuse/update the existing DOM elements
function FileProgress(file, targetID, serverData) {
	this.fileProgressID = file.id;
	this.fileName = file.name;
	this.serverData = serverData;

	this.opacity = 100;
	this.height = 0;
	
	this.fileProgressWrapper = document.getElementById(this.fileProgressID);
	if (!this.fileProgressWrapper) {
		this.fileProgressWrapper = document.createElement("div");
		this.fileProgressWrapper.className = "progressWrapper";
		this.fileProgressWrapper.id = this.fileProgressID;

		this.fileProgressElement = document.createElement("div");
		this.fileProgressElement.className = "progressContainer";

		var progressCancel = document.createElement("a");
		progressCancel.className = "progressCancel";
		progressCancel.href = "#";
		progressCancel.style.visibility = "hidden";
		progressCancel.appendChild(document.createTextNode(" "));

		var progressText = document.createElement("div");
		progressText.className = "progressName";		
		progressText.appendChild(document.createTextNode(file.name));

		
		var progressBar = document.createElement("div");
		progressBar.className = "progressBarInProgress";

		var progressBarValue = document.createElement("div");
		progressBarValue.className = "progressBarValue";

		var progressStatus = document.createElement("div");
		progressStatus.className = "progressBarStatus";
		progressStatus.innerHTML = "&nbsp;";

		var progress = document.createElement("span");
		progress.className = 'progress-text';
		progress.innerHTML = "&nbsp";		

		this.fileProgressElement.appendChild(progressCancel);
		this.fileProgressElement.appendChild(progressText);
		this.fileProgressElement.appendChild(progressStatus);
		this.fileProgressElement.appendChild(progressBar);
		progressBar.appendChild(progressBarValue);
		this.fileProgressElement.appendChild(progress);
		this.fileProgressWrapper.appendChild(this.fileProgressElement);
		document.getElementById(targetID).appendChild(this.fileProgressWrapper);
	} else {
		this.fileProgressElement = this.fileProgressWrapper.firstChild;
		this.reset();
	}

	this.height = this.fileProgressWrapper.offsetHeight;
	this.setTimer(null);
}

FileProgress.prototype.setTimer = function (timer) {
	this.fileProgressElement["FP_TIMER"] = timer;
};
FileProgress.prototype.getTimer = function (timer) {
	return this.fileProgressElement["FP_TIMER"] || null;
};

FileProgress.prototype.reset = function () {
	this.fileProgressElement.className = "progressContainer";
	this.fileProgressElement.childNodes[2].innerHTML = "&nbsp;";
	this.fileProgressElement.childNodes[2].className = "progressBarStatus";	
	this.fileProgressElement.childNodes[3].className = "progressBarInProgress";
	// this.fileProgressElement.childNodes[3].style.width = "0%";
	
	this.appear();	
};

FileProgress.prototype.setProgress = function (percentage) {
	this.fileProgressElement.className = "progressContainer green";
	this.fileProgressElement.childNodes[3].className = "progressBarInProgress";
	this.fileProgressElement.childNodes[3].childNodes[0].style.width = percentage + "%";
	this.fileProgressElement.childNodes[4].innerHTML = percentage + "%";
	this.appear();	
	
};
FileProgress.prototype.setComplete = function () {
	this.fileProgressElement.className = "progressContainer";
	this.fileProgressElement.childNodes[2].className = "progressBarStatus progressBarStatus-ok";	
	var file_type = this.fileName.substring(this.fileName.lastIndexOf(".")+1);
	file_type = file_type.toLocaleLowerCase();
	var tabIndex = this.fileProgressID.substring (this.fileProgressID.length-1);
	this.fileProgressElement.childNodes[1].style.paddingLeft = "35px";
	this.fileProgressElement.childNodes[1].style.background= "url(/assets/img/icon-24/"+file_type+".png) no-repeat 10px 50%";
	
		var oSelf = this;
		var toggleDetail = document.createElement("div");
		var uploadForm = document.createElement("form");
		var formTable = document.createElement("table");
		var reg = /\.\w+$/;
		var btn_delete = document.createElement("a");
		var button = document.createElement("button");

		// attributes
		btn_delete.href = "javascript:void(0)";
		btn_delete.className = "btn-delete";
		btn_delete.innerHTML = "删除";
		toggleDetail.className = "zhankai";
		toggleDetail.innerHTML = "收起" + '<span class="icon icon-caret-up"></span>';
		uploadForm.className = "form default-form upload-form unsubmit-form";
		uploadForm.id= this.fileProgressID+ "-form";
		
		//设置默认标题
		$('#form-template [name=title]').attr('value', this.fileName.replace(reg,''));
		//封面图上传
		$('#form-template [name=img_path]').attr('id', 'small_logo_show' + '_' + this.fileProgressID);
		$('#form-template [name=img]').attr('id', 'img' + '_' + this.fileProgressID);
		$('#form-template [name=img]').attr('onchange', 'ajaxFileUpload(\'img' + '_' + this.fileProgressID + '\',\'img\',\'small_logo_show' + '_' + this.fileProgressID + '\',4,1);');
		
		var html = "<input type=\"hidden\" name=\"file_path\" value=\""+this.serverData+"\" />";
        html += "<div class='upload-table'>";
        html += $('#form-template').html();
//        html += "<tr><th>名称：</th><td><input id='name"+ tabIndex +"' type=\"text\" value='"+this.fileName.replace(reg,'')+"' /><i>*</i> </td></tr></tr>";
//        html += "<tr><th>原作者：</th><td><input type=\"text\" /></td></tr></tr>";
//        html += "<tr><th>简介：</th><td><textarea></textarea></td></tr></tr>";
//        html += "<tr><th>标签：</th><td class='upload-tag-td'><input class='tag-select' type=\"text\" /><div class='r-tag-list'><div class='r-tag-list-inner'><h3>常用标签</h3><ul><li>定位解读</li><li>主题定位</li><li>功能定位</li><li>目标消费者</li><li>城市介绍</li><li>区位概况</li><li>GDP</li><li>商圈分布</li><li>地块介绍</li></ul><h3>前期规划</h3><ul><li>市场调研</li><li>定位解读</li><li>主题定位</li><li>功能定位</li><li>目标消费者</li><li>城市介绍</li><li>区位概况</li><li>GDP</li><li>商圈分布</li><li>地块介绍</li></ul><h3>建筑设计</h3><ul><li>地块红线</li><li>交通组织</li><li>日照</li><li>概念设计</li><li>建筑造型</li><li>消防排布</li><li>结构设计</li><li>效果图</li></ul><h3>招商</h3><ul><li>百货</li><li>业态品牌</li><li>电器</li><li>KTV</li><li>租金分布</li><li>超市</li><li>零售</li><li>主力店</li><li>租赁合同</li></ul><h3>经营管理</h3><ul><li>商场导视</li><li>商家管理</li><li>装修手册</li><li>企划合同</li><li>紧急预案</li><li>媒体推广</li><li>物业管理</li></ul></div></div></td></tr></tr>";
//        html += "<tr><th>专辑：</th><td><select><option>-请选择专辑-</option><option>专辑一</option><option>专辑二</option><option>专辑三</option><option>专辑四</option><option>专辑五</option></select><i>*</i> <a class='dialog' data-url='/dialog/create-album.php?height=420&amp;width=710' data-title='创建专辑' href='javascript:void(0)'>创建专辑</a></td></tr></tr>";
//        html += "<tr><th>业务流程节点： </th><td><select><option>-请选择业务流程节点-</option><option>前期规划</option><option>建筑设计</option><option>招商</option><option>经营管理</option><option>其他</option></select><i>*</i> </td></tr></tr>";
//        html += "<tr><th>项目类型：</th><td><select><option>--请选择项目类型--</option><option>综合体</option><option>街区</option><option>酒店</option><option>写字楼</option><option>其他</option><option>旅游地产</option></select><i>*</i> </td></tr></tr>";
//        html += "<tr><th>城市：</th><td><select><option>-请选择城市-</option><option>南京</option><option>上海</option><option>广州</option><option>其他</option></select><i>*</i> </td></tr></tr>";
//        html += "<tr><th>开发商：</th><td><select><option>-请选择开发商-</option><option>安徽金大地</option><option>万达</option><option>昆山中大</option><option>南京金鹰</option><option>其他</option></select><i>*</i> </td></tr></tr>";
//        html += "<tr><th>原作时间：</th><td><select><option>-请选择时间-</option><option>2005</option><option>2006</option><option>2007</option><option>2008</option><option>2009</option><option>2010</option><option>2011</option><option>2012</option><option>2013</option><option>2014</option></select></td></tr></tr>";
        // html +="<tr><th></th><td><button type=\"button\" class=\"btn btn-primary\">提交</button></td></tr>";
        html += "</div>";
        
        button.innerHTML = "保存信息";
        uploadForm.innerHTML = html;
        button.setAttribute("type", "button");
        button.className = "btn btn-primary btn-submit";
        uploadForm.appendChild(button);
        
        var script = document.createElement('script')
        script.type = 'text/javascript';
        script.src = "/assets/js/knowledge/upload/search.js";
        document.getElementsByTagName('head')[0].appendChild(script);
        
	toggleDetail.onclick = function(){
		if(uploadForm.style.display == "none"){
			this.innerHTML = "收起" + '<span class="icon icon-caret-up"></span>';
			uploadForm.style.display = "block";
		}else{
			this.innerHTML = "展开" + '<span class="icon icon-caret-down"></span>';
			uploadForm.style.display = "none";
		}
	}

//	button.onclick = function(){
//		uploadForm.style.display = "none";
//		uploadForm.className = "form default-form upload-form form-unexpand ";
//		toggleDetail.innerHTML = "展开" + '<span class="icon icon-caret-down"></span>';
//		toggleDetail.style.color = "#999";
//		toggleDetail.onclick = function(){
//			return false;
//		}
//	}
//	
//	btn_delete.onclick = function(){		
//		oSelf.setTimer(setTimeout(function () {
//			oSelf.disappear();
//		}, 1000));
//		
//	}
	
	this.fileProgressElement.appendChild(toggleDetail);
	this.fileProgressElement.appendChild(uploadForm);
	this.fileProgressElement.appendChild(btn_delete);

	$(this.fileProgressElement).find('.progressBarInProgress, .progress-text').hide();
	
	//批量编辑时上传
    if(!$("#batch-form").is(":hidden")){
        $(".unsubmit-form").hide();
        $(".unsubmit-form").parent().children(".zhankai").html('展开<span class="icon icon-caret-down"></span>').css('color','#999').attr('title','批量编辑中，不能单个编辑！').removeAttr('onclick').unbind('click');
    }
    //默认标题恢复
    $('#form-template [name=title]').attr('value','');
	$('#form-template [name=img_path]').attr('id', 'small_logo_show');
	$('#form-template [name=img]').attr('id', 'img');
	$('#form-template [name=img]').attr('onchange', 'ajaxFileUpload(\'img\',\'img\',\'small_logo_show\',4,1);');
};

FileProgress.prototype.setError = function () {
	this.fileProgressElement.className = "progressContainer red";
	this.fileProgressElement.childNodes[3].className = "progressBarError";
	this.fileProgressElement.childNodes[3].style.width = "";

	var oSelf = this;
	this.setTimer(setTimeout(function () {
		oSelf.disappear();
	}, 1000));
};

FileProgress.prototype.setCancelled = function () {
	this.fileProgressElement.className = "progressContainer";
	this.fileProgressElement.childNodes[3].className = "progressBarError";
	this.fileProgressElement.childNodes[3].style.width = "";

	var oSelf = this;
	this.setTimer(setTimeout(function () {
		oSelf.disappear();
	}, 2000));
};

FileProgress.prototype.setStatus = function (status) {
	this.fileProgressElement.childNodes[2].innerHTML = status;
};

// Show/Hide the cancel button
FileProgress.prototype.toggleCancel = function (show, swfUploadInstance) {
	this.fileProgressElement.childNodes[0].style.visibility = show ? "visible" : "hidden";
	if (swfUploadInstance) {
		var fileID = this.fileProgressID;
		this.fileProgressElement.childNodes[0].onclick = function () {
			swfUploadInstance.cancelUpload(fileID);
			return false;
		};
	}
};

FileProgress.prototype.appear = function () {
	if (this.getTimer() !== null) {
		clearTimeout(this.getTimer());
		this.setTimer(null);
	}
	
	if (this.fileProgressWrapper.filters) {
		try {
			this.fileProgressWrapper.filters.item("DXImageTransform.Microsoft.Alpha").opacity = 100;
		} catch (e) {
			// If it is not set initially, the browser will throw an error.  This will set it if it is not set yet.
			this.fileProgressWrapper.style.filter = "progid:DXImageTransform.Microsoft.Alpha(opacity=100)";
		}
	} else {
		this.fileProgressWrapper.style.opacity = 1;
	}
		
	this.fileProgressWrapper.style.height = "";
	
	this.height = this.fileProgressWrapper.offsetHeight;
	this.opacity = 100;
	this.fileProgressWrapper.style.display = "";
	
};

// Fades out and clips away the FileProgress box.
FileProgress.prototype.disappear = function () {

	var reduceOpacityBy = 15;
	var reduceHeightBy = 4;
	var rate = 30;	// 15 fps

	if (this.opacity > 0) {
		this.opacity -= reduceOpacityBy;
		if (this.opacity < 0) {
			this.opacity = 0;
		}

		if (this.fileProgressWrapper.filters) {
			try {
				this.fileProgressWrapper.filters.item("DXImageTransform.Microsoft.Alpha").opacity = this.opacity;
			} catch (e) {
				// If it is not set initially, the browser will throw an error.  This will set it if it is not set yet.
				this.fileProgressWrapper.style.filter = "progid:DXImageTransform.Microsoft.Alpha(opacity=" + this.opacity + ")";
			}
		} else {
			this.fileProgressWrapper.style.opacity = this.opacity / 100;
		}
	}

	if (this.height > 0) {
		this.height -= reduceHeightBy;
		if (this.height < 0) {
			this.height = 0;
		}
		this.fileProgressWrapper.style.height = this.height + "px";
	}

	if (this.height > 0 || this.opacity > 0) {
		var oSelf = this;
		this.setTimer(setTimeout(function () {
			oSelf.disappear();
		}, rate));
	} else {
		this.fileProgressWrapper.style.display = "none";
		this.setTimer(null);
	}
};

// Fades out and 