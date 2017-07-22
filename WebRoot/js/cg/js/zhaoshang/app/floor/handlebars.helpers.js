/**
 * handlebars helpers
 */
/* global Handlebars:false */
;(function (Handlebars) {
    'use strict';

    /*
     * display point
     * 百分比显示 helper
     */
    Handlebars.registerHelper('displayPoint', function(point, options) {
        var cls = 'text-danger',
            point = parseInt(point, 10),
            content = '<strong class="' + cls + '">' +point+ '%</strong>';
        /*
         if ( point <= 45 ) {
         cls = 'text-danger';
         } else {
         cls = 'text-success';
         }*/
        return new Handlebars.SafeString(content);
    });


    /*
     * calculatePosition
     * 计算 timeline segment left position 值
     */
    Handlebars.registerHelper('calculatePosition', function(index, left, options) {
        var content = parseInt(index, 10) * parseInt(left, 10) + '%';
        return new Handlebars.SafeString(content);
    });


    Handlebars.registerHelper('displayToken', function() {
        return window.location.search;
    });

    /*
     * progressLegend
     * 显示 progress 图例
     */
    Handlebars.registerHelper('progressLegend', function (progress, options) {
        var color = progress.color,
            values = progress.values,
            coefficient = progress.coefficient,
            len = values.length,
            i = 0,
            html = '',
            style;

        for ( i; i < len; i++) {
            style = 'background: rgba(' + color + ',' + (values[i]+10)*coefficient +')';
            html += '<span class="color-block" data-value="' + values[i] + '" style="' + style +'"><strong>' + values[i] +'%</strong></span>'
        }
        return new Handlebars.SafeString(html);
    });
}(Handlebars));


