/**
 * 店铺列表和店铺信息展示
 */
/* global _:false, jQuery:false */
;(function(window, _, $, undefined) {
    'use strict';

    var $$map = d3.select('#d3-map-container'),
        poiModalTpl = d3.select('#modal-tpl').html(),
        poiModalTemplate = Handlebars.compile(poiModalTpl),
        poisModalTpl = d3.select('#pois-modal-tpl').html(),
        poisModalTemplate = Handlebars.compile(poisModalTpl),
        api = {
            pois: '/zs.php?app=floors&act=getPoisData&id=',
            poi: '/zs.php?app=talk&act=ajaxList&brand_id='
        },
        mapUrl = window.location.search,
        object = parseQuery(mapUrl),      
        userString = '&' + 'uid=' + object.uid + '&token=' + object.token;

    /*
     * 显示店铺列表信息
     */
    function showPois(id) {
        d3.json(api.pois + id + userString , function (result) {
            var html = poisModalTemplate(result),
                $$modal = d3.select('#modal-pois-' + id);

            if ( ! $$modal.empty() ) {
                $$modal.style('display', 'block');
                return;
            }

            $$map.append('div')
                .attr('class', 'modal')
                .attr('id', 'modal-pois-' + id)
                .html(html);
        });
    }

    // url 转义
    function parseQuery(query) {
        var params = {},
            pairs = query.split(/[;&?]/),
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
    
    

    /**/
    function showPoiDetail(id) {
        d3.json(api.poi + id + userString , function(result) {
            var html = poiModalTemplate(result),
                $$modal = d3.select('#modal-' +id),
                discuss = _.sortBy(result.discuss, function(item) {
                    return item.date;
                }),
                categories = _.pluck(discuss, 'date'),
                points = _.map(discuss, function(item){
                    return parseInt(item.point);
                });

            if ( ! $$modal.empty() ) {
                $$modal.style('display', 'block');
                return;
            }

            $$map.append('div')
                .attr('class', 'modal')
                .attr('id', 'modal-' + id)
                .html(html);

            $('#modal-' + id).find('.chart').highcharts({
                title: {
                    text: result.name + ' - 进度报表',
                    y: 35
                },
                chart: {
                    backgroundColor: '#f8f8f8',
                    marginTop: 100
                },
                xAxis: {
                    categories: categories
                },
                yAxis: {
                    title: {
                        text: '进度(%)'
                    },
                    max:100,
                    min:0,
                    plotLines: [{
                        value: 0,
                        width: 1,
                        color: '#808080'
                    }]
                },
                tooltip: {
                    valueSuffix: '%'
                },
                legend: {
                    layout: 'vertical',
                    align: 'right',
                    verticalAlign: 'middle',
                    borderWidth: 0
                },
                series: [
                    {
                        name: '进度',
                        data : points
                    }]
            });
        });
    }

    /*
     * popover 链接点击事件
     * http://stackoverflow.com/questions/9914587/javascript-event-delegation-handling-parents-of-clicked-elements
     */
    $('#d3-map-container').on('click', '.poi-detail-link', function() {
        var id = $(this).attr('data-id');
        if ( id ) {
            showPoiDetail(id);
        }
        // alert($(this).attr('data-token'))
    });

    $('#d3-map-container').on('click', '.d3-map-link', function () {
        var id = $(this).attr('data-id');
        if ( id ) {
            showPois(id);
        }
    });

    /*
     * 关闭modal
     */
    $('#d3-map-container').on('click', '.modal-close', function() {
        var id = $(this).attr('data-id');
        if ( id ) {
            $('#modal-' + id).fadeOut();
            $('#modal-pois-' + id).fadeOut();
        }
    });
}(window, _, jQuery));