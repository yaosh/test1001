$(function(){
    /*
     * 更多城市
     */
    $('#J-more-city').on('click', function(e) {
        var left = $(this).offset().left,
            top = $(this).offset().top;
        
        e.stopPropagation();
        
        $('#more-city').css({
            display: 'block',
            left: left + 5,
            top: top + 18
        })
    });
    $('#more-city input').on('keyup', function() {
        var q = $(this).val(),
            tpl = '{{#each result}}<li><a href="' + $('#city-href').val() + '&city={{id}}">{{name}}</a></li>{{/each}}',
            template = Handlebars.compile(tpl),
            $ul = $('#more-city .tab-select').find('ul');
        
        $.ajax({
            url: '/lore.php?app=search&act=getMoreCity&q='+q,
            dataType: 'json',
            success: function(data) {
                var html;
                if ( 200 === data.code ) {
                    html = template(data);
                    $ul.html(html)
                }
            }
        })
    });
    
    /*
     * 更多开发商
     */
    $('#J-more-developer').on('click', function(e) {
        var left = $(this).offset().left,
            top = $(this).offset().top;
        
        e.stopPropagation();
        
        $('#more-developer').css({
            display: 'block',
            left: left + 5,
            top: top + 18
        })
    });
    $('#more-developer input').on('keyup', function() {
        var q = $(this).val(),
            tpl = '{{#each result}}<li><a href="' + $('#developer-href').val() + '&developer_id={{id}}">{{name}}</a></li>{{/each}}',
            template = Handlebars.compile(tpl),
            $ul = $('#more-developer .tab-select').find('ul');
        
        $.ajax({
            url: '/lore.php?app=search&act=getMoreDeveloper&q='+q,
            dataType: 'json',
            success: function(data) {
                var html;
                if ( 200 === data.code ) {
                    html = template(data);
                    $ul.html(html)
                }
            }
        })
    });
    
    $('.more-select').on('click', function(e) {
        e.stopPropagation();
    });
    $('body').on('click', function() {
        $('.more-select').hide();
    });
    
    $('.nav-tabs a').click(function (e) {
        e.preventDefault();
        $(this).tab('show');
    });
});