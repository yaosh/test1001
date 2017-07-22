/**
 * @upload
 * @description:upload handle for knowledge
 *
 * @author: imbingdian@gmail.com 
 * @build:2013-08-09
 */
(function($) {
    var settings = {
        flash_url: "/assets/js/swfupload/swfupload.swf",
        upload_url: "/lore.php?app=swfupload",
//        post_params: {"PHPSESSID" : "<?php echo session_id(); ?>"},
        file_size_limit : "500 MB",
        file_types : "*.doc;*.xls;*.ppt;*.pdf;*.zip;*.jpg;*.png;*.docx;*.xlsx;*.pptx;",
        file_types_description : "支持文件",
        file_upload_limit : 0,
        file_queue_limit : 1,
        custom_settings : {
            progressTarget : "upload-progress",
            cancelButtonId : "btn-upload-cancel"
        },
        debug: false,
        
        // Button settings
        button_image_url: "/assets/img/knowledge/upload.png",
        button_width: "153",
        button_height: "40",
        button_placeholder_id: "btn-upload",
        button_text: '',
        button_text_style: "",
        button_text_left_padding: 12,
        button_text_top_padding: 3,
        
        // The event handler functions are defined in handlers.js
        file_queued_handler : fileQueued,
        file_queue_error_handler : fileQueueError,
        file_dialog_complete_handler : fileDialogComplete,
        upload_start_handler : uploadStart,
        upload_progress_handler : uploadProgress,
        upload_error_handler : uploadError,
        upload_success_handler : uploadSuccess,
        upload_complete_handler : uploadComplete,
        queue_complete_handler : queueComplete    // Queue plugin event
    };
    
    // when document ready, init swfupload
    $(function() {
        swfu = new SWFUpload(settings);
    });
}(jQuery));

