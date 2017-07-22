/**
 *  楼层平面图
 *
 *  reference:
 *  http://stackoverflow.com/questions/21216347/achieving-animated-zoom-with-d3-and-leaflet
 *  https://gist.github.com/ZJONSSON/3087431
 *  https://www.mapbox.com/mapbox.js/example/v1.0.0/disable-zooming-panning/ => api
 *  http://bl.ocks.org/mbostock/6226534
 */
/*global D3Map:true, d3: false, _: false */
;(function ( window, d3, _, undefined ) {
    'use strict';

    /*
     * 默认配置信息
     */
    var defaults = {
        container: '#d3-map-container',                     // 地图 container
        width: '100%',                                      // 地图容器宽度
        height: '100%',                                     // 地图高度
        mapType: 'business',                                // 初始显示地图类型，默认按业态分布查看
        initScale: 1,                                       // 初始化缩放比例
        showTextMinScale: 1,                                // 地图缩放比例大于 showTextMinScale 时显示 path 中的 text
        pathStrokeWidth: 2,                                 // 鼠标移入，选中状态 path 的边框宽度
        pathAlpha: 0.8,                                     // 地图 path 默认透明度
        mapInfoPanelTpl: d3.select('#map-info-tpl').html(), // 信息面板模板
        popoverTpl: d3.select('#popover-tpl').html(),       // popover 信息模板
       // presetPopoverTpl: d3.select('#preset-popover-tpl').html(), // 预设弹出层模板
        mapTypeTpl: d3.select('#map-type-tpl').html(),      // 地图类型选择模板
        mapSelectTpl: d3.select('#map-select-tpl').html(),  // 地图楼层选择模板
        mapLegendTpl: d3.select('#map-legend-tpl').html(),  // 地图示例模板
        mapTimelineTpl: d3.select('#map-timeline-tpl').html(),        // 时间轴模板
        mapTypePanelMaxWidth: '160px',
        mapTypePanelTranlateDuration: 300,
        prefix: 'd3-map-',                                  // 地图 id， class 前辍
        zoomExtent: [0.5, 10],                              // 缩放范围
        pathHoverCls: 'd3-map-path-hover',                  // 地图 path 鼠标移入 class
        pathSeletedCls: 'd3-map-path-selected',             // 地图 path 选中 class
        pathTextColor: '#666',                              // 地图 path 中文字颜色
        markerRadius: 3,
        signedColor: '#f4331d',                             // 已签约标记颜色
        unsignedColor: '',                                  // 为签约标记颜色
        nums: ['①', '②', '③', '④', '⑤', '⑥', '⑦', '⑧', '⑨', '⑩', '⑪', '⑫', '⑬', '⑭', '⑮', '⑯', '⑰', '⑱', '⑲', '⑳'],  // 显示商家数目
        progressMapConfig: {                                // 进度图配置
            color: {                                        // 进度颜色
                r: 255,
                g: 0,
                b: 0
            },
            coefficient: 0.008                               // 进度颜色透明度系数 * 进度百分比值 = 透明度
        },
        heatMapConfig: {                                    // heatMap config
            'radius': 45,
            'visible': true,
            'opacity': 40,
            'gradient': {
                0.45: "rgb(0,0,255)",
                0.55: "rgb(0,255,255)",
                0.65: "rgb(0,255,0)",
                0.95: "yellow",
                1.0: "rgb(255,0,0)"
            }
        },
        legend: {
            // 项目业态颜色图例
            colors: [
                {
                    color: '#dfcf95',
                    name: '一般零售'
                },
                {
                    color: '#61b8b2',
                    name: '主力店'
                },
                {
                    color: '#dba25a',
                    name: '百货'
                },
                {
                    color: '#537c8c',
                    name: '超市'
                },
                {
                    color: '#d0afb0',
                    name: '电影院'
                },
                {
                    color: '#b3b07b',
                    name: '餐饮'
                },
                {
                    color: '#a47aa9',
                    name: '生活服务'
                }
            ],
            // 预设业态颜色图例
            presets: [
                {
                    color: "#6bbbe1",
                    name: "零售"
                },
                {
                    color: "#fc947e",
                    name: "餐饮"
                },
                {
                    color: "#dc97c0",
                    name: "娱乐休闲"
                },
                {
                    color: "#48D7BD",
                    name: "超市"
                },
                {
                    color: "#efb596",
                    name: "酒店"
                },
                {
                    color: "#95ccee",
                    name: "生活服务"
                }
            ],
            progress: { // 进度百分比图例分布
                coefficient: 0.008,
                values: [0, 5, 15, 25, 40, 60, 90, 100],
                color: '255, 0, 0'
            }
        },
        callbacks: {                                        // 回调函数
            loaded: function($$loading) {                       // 地图渲染完成回调函数
                $$loading.remove();
            }
        }
    };

    /**
     * D3 Map contructor
     */
    function D3Map(options) {
        this.options = _.extend({}, defaults, options); // deep extend
        this.elements = {};

        this.scale = this.options.initScale;
        this.mapType = this.options.mapType;
        this._init();
    }

    /**
     * D3 Map prototype
     */
    D3Map.prototype = {
        constructor: D3Map,

        /*
         * init
         */
        _init: function () {
            this._initContainer();
            this._requestMapData();
            this._translateToCenter();
        },

        /*
         * 初始化地图容器
         */
        _initContainer: function() {
            var options = this.options,
                width = options.width,
                height = options.height,
                prefix = options.prefix,
                $$container = d3.select(this.options.container);

            // 地图 container
            this.elements.$$container = $$container;

            // loading 遮罩层，地图渲染完毕后移除
            this.elements.$$loading = this.elements.$$container.append('div')
                .attr('class', prefix + 'loading-mask');

            // 地图层
            this.elements.$$mapLayers = $$container.append('div').attr('id', prefix + 'layers').style({
                    width: '100%',
                    height: '100%'
                });

            // svg 地图层
            this.elements.$$map = this.elements.$$mapLayers.append('svg')
                .attr('id', prefix + 'common')
                .style({
                    width: width,
                    height: height
                });

            // heatmap 地图层
            this.elements.$$heatMap = this.elements.$$mapLayers.append('div').attr('id', prefix + 'heatmap').style({
                position: 'absolute',
                left: 0,
                top: 0,
                display: 'none',
                width: $$container.style('width'),
                height: $$container.style('height')
            });

            // 地图铺位路径 group
            this.elements.$$mapPathsGroup = this.elements.$$map.append('g').attr('id', prefix + 'paths-group');

            // popovers
            this.elements.$$mapPopovers = $$container.append('div').attr('id', prefix + 'popovers');

            // 时间轴组件
            this.elements.$$timeline = $$container.append('div').attr('id', prefix + 'timeline');

            // map info 组件
            this.elements.$$mapInfoPanel = $$container.append('div').attr('id', prefix + 'info-panel');

            // 切换楼层和地图类型组件
            this.elements.$$mapSwitchPanel = $$container.append('div').attr('id', prefix + 'switch-panel');
            this.elements.$$mapSelectPanel = this.elements.$$mapSwitchPanel.append('div').attr('id', prefix + 'select-panel');
            this.elements.$$mapTypePanel = this.elements.$$mapSwitchPanel.append('div').attr('id', prefix + 'type-panel');
        },


        /*
         * 请求地图 api 数据
         */
        _requestMapData: function () {
            var self = this,
                url = this.options.api;

            d3.json(url, function(result) {
                // 设置数据
                self.data = result;
                // 首次请求时处理数据
                self._handleMapData();
            });
        },

        /*
         * 处理地图数据
         */
        _handleMapData: function (index) {
            var prefix = this.options.prefix,
                data = this.data,
                historyDatas = data.history,
                len = historyDatas.length,
                currentIndex = index !== undefined ? index : len - 1,               // 如果没有设置 index, i 为数组最后一项
                currentData = _.extend(historyDatas[currentIndex], data.info),      // 第 index 时间的地图数据, 合并当前地图信息和地图基础信息，渲染一些组件时需要用到数据
                pathsData = currentData.paths || [],                                // 地图 paths 数组
                services = currentData.services || [],                              // 地图服务信息数组
                links = data.links || [],                                           // 地图上的链接数据
                background = currentData.background,                                // 地图背景图片 base64
                $$mapBackground,
                $$mapPathsGroup = this.elements.$$mapPathsGroup,
                $$pathsGroups,
                $$paths,
                pathsAttributes,
                $$mapService,
                $$links,
                $$linksPath,
                $$pois;

            // 清除地图路径数据
            $$mapPathsGroup.html('');

            // 绘制背景图
            $$mapBackground = background ? $$mapPathsGroup.append('g')
                .attr('id', prefix + 'backgroud')
                .append('svg:image')
                .attr('xlink:href', background.data)
                .attr('transform', 'translate(' + background.x + ', ' + background.y + ')')
                .attr('width', background.width)
                .attr('height', background.height) : '',
                // 插入地图 path groups
                $$pathsGroups = $$mapPathsGroup.selectAll('path')
                    .data(pathsData)
                    .enter()
                    .append('g')
                    .attr('data-id', function(d) {
                        return d.id;
                    })
                    .attr('id', function(d) {
                        return prefix + 'g-' + d.id;
                    })
                    .classed('is-poi', function(d) {
                        if ( d.is_poi ) {
                            return true;
                        } else {
                            return false;
                        }
                    })
                    .classed('is-service', function(d) {
                        if ( d.is_service ) {
                            return true;
                        } else {
                            return false;
                        }
                    });
                // 绘制地图 paths
                $$paths = $$pathsGroups.append('path'),
                pathsAttributes = $$paths.attr('d', function (d) { return d.path; })
                    .attr('data-id', function(d) {
                        return d.id;
                    })
                    .attr('data-value', function(d) {
                        var value = d.point;

                        return value !== undefined ? value : '';
                    })
                    .attr('id', function(d) {
                        return prefix + 'path-' + d.id;
                    });
                // 绘制服务信息
                $$mapService = $$mapPathsGroup.append('g')
                    .attr('id', prefix + 'services')
                    .selectAll('path')
                    .data(services)
                    .enter()
                    .append('image')
                    .attr('xlink:href', function(d) {
                        return d.image;
                    })
                    .attr('width', function(d) {
                        return d.width;
                    })
                    .attr('height', function(d) {
                        return d.height;
                    })
                    .attr('transform', function(d){
                        var x = d.x,
                            y = d.y,
                            rotate = d.rotate;

                        return 'translate(' + x + ', ' + y + ')' + 'rotate(' +rotate+ ')';
                    });
                $$pois = d3.selectAll('.is-poi'), // 选择所有店铺
                // 绘制 links
                $$links = $$mapPathsGroup.append('g')
                    .attr('id', prefix + 'links')
                    .selectAll('g')
                    .data(links)
                    .enter()
                    .append('g')
                    .attr('data-id', function(d) {
                        return d.id;
                    })
                    .classed(prefix + 'link', true);
                $$linksPath = $$links.append('path')
                    .attr('d', function(d) {
                        return d.path;
                    });

            // 缓存当前地图上 pois 和 paths
            this.elements.$$pois = $$pois;
            this.elements.$$paths = $$pathsGroups;

            // 渲染地图及其组件
            this.displayMap(this.mapType);
            this._renderPoiTexts($$pois);
            this._renderLinksTexts($$links);
            this._renderMapInfoPanel(currentData);
            this._renderMapSwitchPanel(_.extend(currentData, {floors: data.floors})); // 合并楼层信息到当前时间点数据
            this._renderMapLegendPanel(currentData);
            // 有超过一条历史记录时，显示时间轴
            if ( data.history.length > 1 ) {
                this._renderMapTimeline(data);
            }
            this._pathsBindEvent($$pois);
            this._legendBindEvent($$pois);
            this._mapSelectPanelAnimate();
            this._mapTypePanelAnimate();

            // 渲染完成后，缓存当前地图上 text
            this.elements.$$text = this.elements.$$mapPathsGroup.selectAll('text');
        },

        /*
         * 向 links 增加 对应的文本信息
         */
        _renderLinksTexts: function ($$links) {
            var self = this,
                options = this.options,
                initScale = options.initScale,
                showTextMinScale = options.showTextMinScale,
                display = initScale < showTextMinScale ? 'none' : 'block'; // 初如化比例大于显示文字的最小比例时，设置 display block

            $$links.each(function (path) {
                var $$this = d3.select(this),
                    center = self.getCentroid($$this);

                $$this.append('text')
                    .style({
                        display: display
                    })
                    .append('tspan')
                    .text(function(d) {
                        return d.title;
                    })
                    .attr('fill', '#666')
                    .attr('x', center[0] - 20)
                    .attr('y', center[1] + 5);
            });
        },

        /*
         * 向 path 增加 对应的文本信息
         * http://www.svgbasics.com/font_effects_italic.html
         */
        _renderPoiTexts: function($$pois) {
            var self = this,
                options = this.options,
                nums = options.nums,
                prefix = options.prefix,
                pathTextColor = options.pathTextColor,
                initScale = options.initScale,
                showTextMinScale = options.showTextMinScale,
                markerRadius = options.markerRadius,
                signedColor = options.signedColor,
                display = initScale < showTextMinScale ? 'none' : 'block'; // 初如化比例大于显示文字的最小比例时，设置 display block

            $$pois.each(function(path, i) {
                var center = self.getCentroid(d3.select(this)),
                    x = center[0],
                    y = center[1],
                    marker = path.point === 100 ? d3.select(this).append('circle')
                        .classed('marker', true)
                        .attr('r', markerRadius/self.scale)
                        .attr('cx', x)
                        .attr('cy', y)
                        .attr('fill', signedColor) : null,
                    text = d3.select(this).append('text')
                        .attr('data-id', path.id)
                        .attr('id', prefix + 'text-' + path.id)
                        .attr('text-anchor', 'middle')
                        .style('display', display)
                        .attr('x', x)
                        .attr('y', y)
                        .attr('fill', pathTextColor),
                    number = text.append('tspan')
                        .style('font-weight', 'bold')
                        .text(function () {
                            var index;

                            if ( path.id ) {
                                if ( path.shops ) {
                                    index = path.shops.length;
                                } else if ( path.name ) {
                                    index = 0;
                                } else {
                                    index = -1;
                                }
                            }

                            if(index >= 0){
                                return nums[index] + ' - '  + path.number || '';
                            }

                            if(index == -1){                                
                                return path.number || '';
                            }
                            
                        }),
                        //.text('1----' + path.number || ''),
                    area = text.append('tspan')
                        .classed(prefix + 'poi-area', true)
                        .attr('x', x)
                        // 缩放时按比例改变铺位号和面积之间的距离
                        .attr('y', y + 20/self.scale)
                        .text(path.area ? path.area + 'm2' : '');
            });
        },

        /*
         * 渲染地图信息组件
         */
        _renderMapInfoPanel: function(data) {
            var template = Handlebars.compile(this.options.mapInfoPanelTpl),
                html = template(data);

            this.elements.$$mapInfoPanel.html(html);
        },

        /*
         * 渲染 switch 组件
         * 楼层切换和地图 type 切换
         */
        _renderMapSwitchPanel: function(result) {
            var selectTemplate = Handlebars.compile(this.options.mapSelectTpl),
                typeTemplate = Handlebars.compile(this.options.mapTypeTpl);

            if ( !this.elements.$$mapSelectPanel.html() ) {
                this.elements.$$mapSelectPanel.html(selectTemplate(result));
            }
            if ( !this.elements.$$mapTypePanel.html() ) {
                this.elements.$$mapTypePanel.html(typeTemplate());
            }
        },

        /*
         * 渲染图例组件
         */
        _renderMapLegendPanel: function() {
            var template,
                html;

            // 如果图例组件为空，渲染图例组件
            if ( d3.select('#' + this.options.prefix + 'legends').empty() ) {
                template = Handlebars.compile(this.options.mapLegendTpl);
                html = template(_.extend(this.options.legend, { mapType: this.mapType}));

                this.elements.$$container.append('div').attr('id', this.options.prefix + 'legends')
                    .html(html);
            }

            this._showLegend();
        },


        /*
         * 显示图例组件
         */
        _showLegend: function() {
            d3.selectAll('.' + this.options.prefix + 'legend')
                .style({
                    display: 'none'
                });
            d3.select('#' + this.options.prefix + 'legend-' + this.mapType)
                .style({
                    display: 'block'
                });
        },

        /*
         * 渲染时间轴组件
         */
        _renderMapTimeline: function (data) {
            var self = this,
                template,
                len,
                left,
                html;

            // 如果 timeline 没有内容，渲染 timeline
            if ( ! this.elements.$$timeline.html() ) {
                template = Handlebars.compile(this.options.mapTimelineTpl);
                len = data.history.length;
                left = 100/(len-1);
                data.left  = left;
                html = template(data);

                // 插入 html
                this.elements.$$timeline.html(html);

                // bind events
                d3.selectAll('.timeline-slider-segment').on('click', function() {
                    var $$this = d3.select(this),
                        index = $$this.attr('data-index')*1;

                    d3.select('.timeline-slider-handle').style({
                        left: 100/(len-1) * index + '%'
                    });
                    self._handleMapData(index);
                });
            }
        },

        /*
         * 渲染业态分类层
         */
        _renderBusinessLayer: function () {
            var $$paths  = this.elements.$$paths,
                alpha = this.options.pathAlpha;

            $$paths.each(function (path) {
                var color = d3.rgb(path.color),
                    r = color.r,
                    g = color.g,
                    b = color.b,
                    rgba = 'rgba('+ r +','+ g +','+ b +', ' + alpha + ')';

                d3.select(this).select('path').style({
                    fill: rgba
                });
            });
        },


        /*
         * 渲染进度层
         */
        _renderProgressLayer: function () {
            var $$pois = this.elements.$$paths,
                config = this.options.progressMapConfig,
                color = config.color,
                r = color.r,
                g = color.g,
                b = color.b,
                coefficient = config.coefficient,
                alpha = this.options.pathAlpha;

            $$pois.each(function (path) {
                if ( path.point !== undefined ) {
                    d3.select(this).select('path').style({
                        fill: 'rgba(' + [r, g, b, path.point * coefficient].join(',') + ')'
                    });
                } else {
                    d3.select(this).select('path').style({
                        fill: (function () {
                            var color = d3.rgb(path.color),
                                r = color.r,
                                g = color.g,
                                b = color.b,
                                rgba = 'rgba('+ r +','+ g +','+ b +', ' + alpha + ')';

                            return rgba;
                        }())
                    });
                }
            });
        },

        /*
         * 渲染预设图
         */
        _renderPresetLayer: function () {
            var $$pois = this.elements.$$paths;

            $$pois.each(function (path) {
                d3.select(this).select('path').style({
                    fill: path.preset || (path.preset_shops && path.preset_shops.color) || "#ccc"
                });
            });
        },

        /*
         * 渲染热力图
         */
        _renderHeatMapLayer: function () {
            var self = this,
                $$pois = this.elements.$$pois,
                id = this.elements.$$heatMap.attr('id'),
                heatMapConfig = this.options.heatMapConfig,
                heatMapData = [],
                heatMap;

            heatMapConfig.element = document.getElementById(id)

            if ( d3.select('canvas').empty() ) {
                $$pois.each(function(path) {
                    var center = self.getCentroid(d3.select(this)),
                        x = center[0],
                        y = center[1],
                        point = path.point || 0;

                    heatMapData.push({
                        x: x,
                        y: y,
                        count: point
                    });
                });

                heatMap = h337.create(heatMapConfig);
                heatMap.store.setDataSet({
                    max: 100,
                    data: heatMapData
                });
            }

            this.elements.$$heatMap.style({
                display: 'block'
            });
        },

        /*
         * paths 绑定事件
         */
        _pathsBindEvent: function($$paths) {
            var self = this,
                options = self.options,
                prefix = options.prefix,
                $$popovers = this.elements.$$mapPopovers,
                popoverTemplate = Handlebars.compile(self.options.popoverTpl),
                presetPopoverTemplate = Handlebars.compile(self.options.presetPopoverTpl),
                template,
                pathHoverCls = options.pathHoverCls,
                pathSelectedCls = options.pathSeletedCls,
                pathStrokeWidth = options.pathStrokeWidth;

            // paths mouseover 事件，高亮显示当前区域
            $$paths.on('mouseenter', function(d) {
                d3.select(this).classed(pathHoverCls, true).style('fill', function(d) {
                    var color = d3.rgb(d.color),
                        r = color.r,
                        g = color.g,
                        b = color.b;

                    return 'rgba('+ r +','+ g +','+ b +', 1' +')';
                })
                    .select('path')
                    .style('stroke-width', (pathStrokeWidth/self.scale) + 'px');
            }).on('mouseleave', function(d) {
                d3.select(this).classed(pathHoverCls, false).style('fill', function(d) {
                    var alpha = options.pathAlpha,
                        color = d3.rgb(d.color),
                        r = color.r,
                        g = color.g,
                        b = color.b;
                    return 'rgba('+ r +','+ g +','+ b +',' + alpha +')';
                });
            });


            // path click 事件，显示当前位置的详细信息
            $$paths.on('click', function(d, i) {
                var $$this = d3.select(this),
                    $$popover = d3.select('#' + self.options.prefix + 'popover-' + d.id),
                    $$presetPopover = d3.select('#' + self.options.prefix + 'popover-preset-' + d.id),
                // center = self.getCentroid(d3.select(this)),
                // x = (parseInt(center[0], 10) - 180 ) + 'px', // 180 为 popover width 1/2
                // y = (parseInt(center[1], 10) - 130 ) + 'px', // 130 为 popover height
                    x = (d3.event.clientX - 180 ) + 'px', // 180 为 popover width 1/2
                    y = (d3.event.clientY - 130 ) + 'px', // 130 为 popover height
                    popoverContent,
                    popoverStyle = {
                        left: x,
                        top: y,
                        display: 'block'
                    };

                // 如果是服务设施
                if ( $$this.classed('is-service') ) {
                    return;
                }

                // 隐藏所有 popover
                d3.selectAll('.popover').style({ display: 'none' });
                $$paths.classed(pathSelectedCls, false);

                if ( self.mapType === 'preset' ) {
                    // 显当前 preset popover
                    // 如果 dom 中没有当前位置的  preset popover, 插入 preset popover html, 如果已经存在，则直接显示 preset popover
                    if ( $$presetPopover.empty() ) {
                        popoverContent = presetPopoverTemplate(d.preset_shops);
                        $$popover = $$popovers.append('div')
                            .attr('id', 'popover-preset-' + d.id)
                            .attr('class', 'popover popover-poi top')
                            .html(popoverContent);
                    }
                    $$popover.style(popoverStyle);
                    $$this.classed(pathSelectedCls, true)
                        .select('path')
                        .style('stroke-width', (pathStrokeWidth/self.scale) + 'px');

                } else {
                    // 显当前 popover
                    // 如果 dom 中没有当前位置的 popover, 插入 popover html, 如果已经存在，则直接显示 popover
                    if ( $$popover.empty() ) {
                        popoverContent = popoverTemplate(d);
                        $$popover = $$popovers.append('div')
                            .attr('id', 'popover-' + d.id)
                            .attr('class', 'popover popover-poi top')
                            .html(popoverContent);
                    }
                    $$popover.style(popoverStyle);
                    $$this.classed(pathSelectedCls, true)
                        .select('path')
                        .style('stroke-width', (pathStrokeWidth/self.scale) + 'px');
                }

            });

            // 关闭 popover
            // http://davidwalsh.name/event-delegate
            document.getElementById(prefix + 'popovers').addEventListener('click', function(e) {
                var $$target = d3.select(e.target),
                    $$popover,
                    $$path,
                    id;

                if (e.target && $$target.attr('class') == 'close' ) {
                    id = $$target.attr('data-id');
                    $$popover = d3.select(e.target.parentNode);
                    $$path = d3.select('#' + options.prefix + 'g-' + id);

                    $$popover.style('display', 'none');
                    $$path.classed(pathSelectedCls, false);
                }
            });
        },


        /*
         * _legendBindEvent
         * legend 绑定事件
         */
        _legendBindEvent: function () {
            var $$legends = d3.selectAll('#' + this.options.prefix + 'legend-progress span'),
                $$pois = this.elements.$$pois;

            $$legends.on('click', function () {
                var value = d3.select(this).attr('data-value');

                $$pois.classed('d3-map-path-filter', false);
                $$pois.each(function() {
                    var $$this = d3.select(this),
                        $$path = $$this.select('path');

                    if ( $$path.attr('data-value') === value ) {
                        $$this.classed('d3-map-path-filter', true);
                    }
                });
            });

        },

        /*
         * 地图缩放
         * http://stackoverflow.com/questions/11786023/how-to-disable-double-click-zoom-for-d3-behavior-zoom
         * http://bl.ocks.org/mbostock/2206590
         * http://stackoverflow.com/questions/15147136/how-to-know-the-current-zoom-level-in-d3-js
         * http://truongtx.me/2014/03/13/working-with-zoom-behavior-in-d3js-and-some-notes/
         */
        _zoom: function() {
            var self = this,
                options = this.options,
                initScale = this.options.initScale,
                zoomExtent = options.zoomExtent,
                markerRadius = options.markerRadius,
                showTextMinScale = options.showTextMinScale,
                $$map = this.elements.$$map,
                $$mapPathsGroup = this.elements.$$mapPathsGroup,
                $$markers = $$mapPathsGroup.selectAll('.marker'),
                zoomed = d3.behavior.zoom()
                    .scaleExtent(zoomExtent)
                    .scale(initScale)
                    .on('zoom', function() {
                        // 根据中心点修正 translate 坐标
                        var center = self.center,
                            centerX = center[0] * d3.event.scale,
                            centerY = center[1] * d3.event.scale,
                            translateX = d3.event.translate[0] + centerX/initScale,
                            translateY = d3.event.translate[1] + centerY/initScale,
                            translate = [translateX, translateY].join(','),
                            eventType = d3.event.sourceEvent.type,
                            scale = d3.event.scale,
                            $$text = self.elements.$$text;

                        if ( eventType === 'mousemove' ) {
                            $$mapPathsGroup.interrupt().transition()
                                .ease('linear')
                                .duration(300)
                                .attr('transform', 'translate('+ translate +')scale(' + scale + ')');
                        } else {
                            $$mapPathsGroup.attr('transform', 'translate('+ translate +')scale(' + scale + ')');
                        }

                        if ( scale < showTextMinScale ) {
                            $$text.style('display', 'none');
                        } else {
                            $$text.style('display', 'block');
                            // 设置面积的位置
                            $$text.each(function (i) {
                                var $$this = d3.select(this),
                                    $$area = $$this.select('.' + self.options.prefix + 'poi-area'),
                                    y = $$this.attr('y')*1 + 20/scale;

                                $$area.attr('y', y );
                            });
                        }

                        // set markers radius
                        $$markers.attr('r', markerRadius/scale);

                        // hide popover
                        d3.selectAll('.popover').style({ display: 'none' });

                        // set scale
                        self.scale = scale;
                    }),
                dragged = d3.behavior.drag();

            $$map.call(zoomed).on('dblclick.zoom', null);
        },



        /*
         * 地图初始化时使用画布中的 $$mapPathsGroup 居中
         * http://stackoverflow.com/questions/19710174/how-i-get-width-of-a-svg-element-using-d3js
         */
        _translateToCenter: function() {
            var self = this,
                $$map = this.elements.$$map,
                $$mapPathsGroup = this.elements.$$mapPathsGroup,
                $$loading = this.elements.$$loading,
                initScale = this.options.initScale,
                canvasWidth = parseInt($$map.style('width'), 10),
                canvasHeight = parseInt($$map.style('height'), 10),
                loaded = this.options.callbacks.loaded,
                mapBBox,
                mapWidth,
                mapHeight,
                centerX,
                centerY,
                timer;

            // 定时器检测地图内容是否可以获取到地图宽度，如果获取到宽度，清除定时器，并将地图移动致画面中心
            timer = setInterval(function() {
                mapBBox = $$mapPathsGroup.node().getBBox();

                if ( mapBBox.width !== 0 ) {
                    clearInterval(timer);
                    mapWidth = mapBBox.width*initScale;
                    mapHeight = mapBBox.height*initScale;
                    centerX = (canvasWidth - mapWidth)/2;
                    centerY = (canvasHeight - mapHeight)/2;

                    // 移动地图到中心点
                    $$mapPathsGroup.attr('transform', 'translate(' + centerX + ',' + centerY +')scale(' + initScale + ')');
                    // 定位 heatmap 位置与地图中心点重合
                    //self.elements.$$heatMap.style('transform', 'translate(' + centerX + 'px,' + centerY +'px)');
                    self.elements.$$heatMap.style({
                        left: centerX + 'px',
                        top: centerY + 'px'
                    });

                    //  设置地图的中心点
                    // 地图缩放时使用
                    self.center = [centerX, centerY];

                    // 地图加载完成后执行缩放方法
                    self._zoom();

                    //self.elements.$$container.style('display', 'block');
                    if ( typeof loaded === 'function' ) {
                        loaded($$loading);
                    }
                }
            }, 30);
        },


        /*
         * mapTypePanel animate
         */
        _mapTypePanelAnimate: function() {
            var self = this,
                $$mapTypePanel = this.elements.$$mapTypePanel,
                $$list = $$mapTypePanel.selectAll('li'),
                width = $$mapTypePanel.style('width'),
                height = $$mapTypePanel.style('height'),
                maxWidth = this.options.mapTypePanelMaxWidth,
                duration = this.options.mapTypePanelTranlateDuration,
                timer;


            // pc
            $$mapTypePanel.on('mouseenter', function() {
                    var $$this = d3.select(this),
                        len = $$mapTypePanel.selectAll('li')[0].length,
                        height = parseInt($$this.select('.active').style('height'), 10) * len + 'px';

                    $$list.style('display', 'block');
                    $$this.transition()
                        .duration(duration)
                        .style({
                            width: maxWidth,
                            height: height
                        });
                }).on('mouseleave', function() {
                    var self = this;

                    d3.select(this).transition()
                        .duration(duration)
                        .style({
                            width: width,
                            height: height
                        })
                        .each("end", function() {
                            // http://stackoverflow.com/questions/10692100/invoke-a-callback-at-the-end-of-a-transition
                            $$mapTypePanel.selectAll('li:not(.active)').style('display', 'none');
                        });
            });

            // mobile
            $$mapTypePanel.on('touchend', function() {
                var $$this = d3.select(this),
                    len = $$mapTypePanel.selectAll('li')[0].length,
                    height = parseInt($$this.select('.active').style('height'), 10) * len + 'px',
                    status = $$this.attr('data-status');

                if ( status === 'active' ) {
                    $$this.attr('data-status', '');
                    $$this.transition()
                        .duration(duration)
                        .style({
                            width: width,
                            height: 'auto'
                        })
                        .each("end", function() {
                            // http://stackoverflow.com/questions/10692100/invoke-a-callback-at-the-end-of-a-transition
                            $$mapTypePanel.selectAll('li:not(.active)').style('display', 'none');
                        });
                } else {
                    $$list.style('display', 'block');
                    $$this.transition()
                        .duration(duration)
                        .style({
                            width: maxWidth,
                            height: height
                        });
                    $$this.attr('data-status', 'active');
                }

            });

            $$mapTypePanel.selectAll('li').on('click', function() {
                var $$this = d3.select(this),
                    type = $$this.attr('data-type');

                $$mapTypePanel.selectAll('li').classed('active', false);
                $$this.classed('active', true);
                self.displayMap(type);
            });
            $$mapTypePanel.selectAll('li').on('touchend', function() {
                var $$this = d3.select(this),
                    type = $$this.attr('data-type');

                $$mapTypePanel.selectAll('li').classed('active', false);
                $$this.classed('active', true);
                self.displayMap(type);
            });
        },

        /*
         * mapSelectPanel animate
         */
        _mapSelectPanelAnimate: function () {
            var self = this,
                $$mapSelectPanel = this.elements.$$mapSelectPanel,
                $$list = $$mapSelectPanel.select('ul'),
                timer;

            // mouse event
            $$mapSelectPanel.on('mouseenter', function () {
                if (timer) {
                    clearTimeout(timer);
                }
                $$list.style({
                    display: 'block'
                }).transition()
                    .duration(300)
                    .style({
                        width: '150px'
                    });
            }).on('mouseleave', function () {
                timer = setTimeout(function () {
                    $$list.style({
                        display: 'none'
                    });
                }, 100);
            });

            // touch event
            $$mapSelectPanel.select('.current').on('touchend', function () {
                var display = $$list.style('display');

                if ( display === 'none' ) {
                    $$list.style({
                        display: 'block'
                    }).transition()
                        .duration(300)
                        .style({
                            width: '150px'
                        });
                } else {
                    $$list.style({
                        display: 'none'
                    })
                }
            });
        },

        /*
         * 显示地图类型
         * @type {String} 地图类型
         */
        displayMap: function(type) {
            var $$heatMap = this.elements.$$heatMap,
                $$mapPathsGroup = this.elements.$$mapPathsGroup,
                $$timeline = this.elements.$$timeline,
                center = this.center;

            switch (type) {
                case 'heatmap':
                    this._renderHeatMapLayer();
                    $$timeline.style('display', 'none');
                    $$mapPathsGroup.attr('transform', 'translate(' + center[0] + ',' + center[1] + ')scale(1)');
                    break;
                case 'progress':
                    $$heatMap.style('display', 'none');
                    $$timeline.style('display', 'none');
                    this._renderProgressLayer();
                    break;
                case 'preset':
                    $$timeline.style('display', 'block');
                    this._renderPresetLayer();
                    break;
                default:
                    $$timeline.style('display', 'none');
                    $$heatMap.style('display', 'none');
                    this._renderBusinessLayer();
            }
            this.mapType = type;
            this._showLegend();
        },


        /*
         * 获取重心(几何中心)
         * http://stackoverflow.com/questions/12062561/calculate-svg-path-centroid-with-d3-js
         */
        getCentroid: function(selection) {
            // get the DOM element from a D3 selection
            // you could also use "this" inside .each()
            var element = selection.node(),
            // use the native SVG interface to get the bounding box
                bbox = element.getBBox();
            // return the center of the bounding box
            return [bbox.x + bbox.width/2, bbox.y + bbox.height/2];
        }
    }

    // export D3Map
    window.D3Map = D3Map;
}( window, d3, _ ));