(function($) {
    //原作者 autocomplete
    $(".ui-select-author").select({
        api: '/lore.php?app=upload&act=getSearchAuthor', 
        params: {}, //其它参数
        tpl: '<ul class="scrollbar">{{#each result}}<li data={{id}}><span>{{name}}</span></li>{{/each}}</ul>'
    });
    //开发商 autocomplete
    $(".ui-select-developer").select({
        api: '/lore.php?app=upload&act=getSearchDeveloper', 
        params: {}, //其它参数
        tpl: '<ul class="scrollbar">{{#each result}}<li data={{id}}><span>{{name}}</span></li>{{/each}}</ul>'
    });
}(jQuery));

