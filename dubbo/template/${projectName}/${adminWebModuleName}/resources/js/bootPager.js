function bootPager(selector, options) {

    var self = this;
    self.pageInfo = {
        pageIndex: 1,
        pageSize: 10,
        pages: 7,
        defaultId: 'bootPager',
        pageSizes: [5, 10, 20, 50, 100]
    };

    default_options = {
        changePageSize: function (pageSize) {
        },
        changePageIndex: function (pageIndex) {
        }
    };
    initPager();

    function initPager() {

        if (options) {
            $.extend(default_options, options);
        }

        $(selector).on('click', '#pageSizeGroup .dropdown-item', function () {
            var pageSize = parseInt($(this).data('value'))
            changePageSize(pageSize);
        });

        $(selector).on('click', '.page-item', function () {
            var pageIndex = parseInt($(this).data('value'))
            if (isNaN((pageIndex))) {
                return;
            }
            changePageIndex(pageIndex);
        });
    }

    self.refreshPageInfo = refreshPageInfo;
    function refreshPageInfo(pageInfo) {
        var page = self.pageInfo;
        page.pageIndex = pageInfo.pageIndex;
        page.pageSize = pageInfo.pageSize;
        page.total = pageInfo.total;
        page.pageCount = Math.ceil(page.total / page.pageSize);

        page.hasPrevPage = self.pageInfo.pageIndex > 1;
        page.hasNextPage = self.pageInfo.pageIndex < self.pageInfo.pageCount;

        var halfPageNums = parseInt(page.pages / 2);
        page.navigateFirstPage = page.pageIndex - halfPageNums;
        page.navigateLastPage = page.pageIndex + page.pages - halfPageNums - 1;

        while (page.navigateFirstPage < 1) {
            page.navigateFirstPage += 1;
            if (page.navigateLastPage < page.pageCount) {
                page.navigateLastPage += 1;
            }
        }
        while (page.navigateLastPage > page.pageCount) {
            page.navigateLastPage -= 1;
            if (page.navigateFirstPage > 1) {
                page.navigateFirstPage -= 1;
            }
        }
        refreshPager();
    }

    function refreshPager() {
        if (self.pageInfo.total == 0) {
            $(selector).empty();
            return;
        }
        var pageHtml = '';
        pageHtml += '<div class="btn-group" id="' + self.pageInfo.defaultId + '" role="group" aria-label="Button group with nested dropdown">';
        pageHtml += getPageHtml();
        pageHtml += getPageSizeHtml(self.pageInfo.pageSize);
        pageHtml += '</div>';
        $(selector).empty();
        $(selector).html(pageHtml);
    }

    self.changePageSize = changePageSize;

    function changePageSize(pageSize) {
        self.pageInfo.pageIndex = 1;
        self.pageInfo.pageSize = pageSize;
        $(selector).find('#pagerPageSize').text(pageSize);
        default_options.changePageSize(pageSize);
    }

    self.changePageIndex = changePageIndex;

    function changePageIndex(pageIndex) {
        self.pageInfo.pageIndex = pageIndex;
        default_options.changePageIndex(pageIndex);
    }

    function getPageSizeHtml(pageSize) {
        var pageHtml = '';
        pageHtml += '    <div class="btn-group" role="group" id="pageSizeGroup">';
        pageHtml += '        <button type="button" class="btn btn-secondary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">';
        pageHtml += '            每页 <span id="pagerPageSize">' + pageSize + '</span> 条';
        pageHtml += '        </button>';
        pageHtml += '        <div class="dropdown-menu">';
        self.pageInfo.pageSizes.forEach(function (item, index) {
            pageHtml += '            <a class="dropdown-item" href="javascript:;" data-value="' + item + '">' + item + '</a>';
        });
        pageHtml += '        </div>';
        pageHtml += '    </div>';
        return pageHtml;
    }

    function getPageHtml() {
        var pageInfo = self.pageInfo;
        var pageHtml = '';

        if (pageInfo.hasPrevPage) {
            pageHtml += '<div class="btn btn-secondary page-item" data-value="' + (pageInfo.pageIndex - 1) + '">上一页</div>';
        }
        if (1 < pageInfo.navigateFirstPage) {
            pageHtml += '<div class="btn btn-secondary page-item" data-value="' + 1 + '">' + 1 + '</div>';
        }
        if (2 < pageInfo.navigateFirstPage) {
            pageHtml += '<div class="btn btn-secondary page-item">...</div>';
        }

        for (var pageIndex = pageInfo.navigateFirstPage; pageIndex <= pageInfo.navigateLastPage; pageIndex++) {
            if (pageIndex == pageInfo.pageIndex) {
                pageHtml += '<div class="btn btn-dark page-item">' + pageIndex + '</div>';
            } else {
                pageHtml += '<div class="btn btn-secondary page-item" data-value="' + pageIndex + '">' + pageIndex + '</div>';
            }
        }

        if (pageInfo.pageCount > pageInfo.navigateLastPage + 1) {
            pageHtml += '<div class="btn btn-secondary page-item">...</div>';
        }
        if (pageInfo.pageCount > pageInfo.navigateLastPage) {
            pageHtml += '<div class="btn btn-secondary page-item" data-value="' + pageInfo.pageCount + '">' + pageInfo.pageCount + '</div>';
        }

        if (pageInfo.hasNextPage) {
            pageHtml += '<div class="btn btn-secondary page-item" data-value="' + (pageInfo.pageIndex + 1) + '">下一页</div>';
        }

        return pageHtml;
    }
}

function bootSelectPager(selector, options) {

    var self = this;
    self.pageInfo = {
        pageIndex: 1,
        pageSize: 10,
        pages: 15,
        defaultId: 'bootPager',
        pageSizes: [5, 10, 20, 50, 100]
    };

    default_options = {
        changePageSize: function (pageSize) {
        },
        changePageIndex: function (pageIndex) {
        }
    };

    initPager();

    self.refreshPageInfo = refreshPageInfo;
    function refreshPageInfo(pageInfo) {
        var page = self.pageInfo;
        page.pageIndex = pageInfo.pageIndex;
        page.pageSize = pageInfo.pageSize;
        page.total = pageInfo.total;
        page.pageCount = Math.ceil(page.total / page.pageSize);

        page.hasPrevPage = self.pageInfo.pageIndex > 1;
        page.hasNextPage = self.pageInfo.pageIndex < self.pageInfo.pageCount;

        var halfPageNums = parseInt(page.pages / 2);
        page.navigateFirstPage = page.pageIndex - halfPageNums;
        page.navigateLastPage = page.pageIndex + page.pages - halfPageNums - 1;

        while (page.navigateFirstPage < 1) {
            page.navigateFirstPage += 1;
            if (page.navigateLastPage < page.pageCount) {
                page.navigateLastPage += 1;
            }
        }
        while (page.navigateLastPage > page.pageCount) {
            page.navigateLastPage -= 1;
            if (page.navigateFirstPage > 1) {
                page.navigateFirstPage -= 1;
            }
        }
        refreshPager();
    }

    function refreshPager() {
        if (self.pageInfo.total == 0) {
            $(selector).empty();
            return;
        }
        var pageHtml = '';
        pageHtml += '<div class="btn-group" id="' + self.pageInfo.defaultId + '" role="group" aria-label="Button group with nested dropdown">';
        pageHtml += getPageHtml();
        pageHtml += getPageSizeHtml(self.pageInfo.pageSize);
        pageHtml += '</div>';
        $(selector).empty();
        $(selector).html(pageHtml);
    }

    self.initPager = initPager;

    function initPager() {

        if (options) {
            $.extend(default_options, options);
        }

        $(selector).on('click', '#pageSizeGroup .dropdown-item', function () {
            var pageSize = parseInt($(this).data('value'))
            changePageSize(pageSize);
        });

        $(selector).on('click', '.page-item', function () {
            var pageIndex = parseInt($(this).data('value'))
            if (isNaN((pageIndex))) {
                return;
            }
            changePageIndex(pageIndex);
        });
    }

    self.changePageSize = changePageSize;

    function changePageSize(pageSize) {
        self.pageInfo.pageIndex = 1;
        self.pageInfo.pageSize = pageSize;
        $(selector).find('#pagerPageSize').text(pageSize);
        default_options.changePageSize(pageSize);
    }

    self.changePageIndex = changePageIndex;

    function changePageIndex(pageIndex) {
        self.pageInfo.pageIndex = pageIndex;
        default_options.changePageIndex(pageIndex);
    }

    function getPageSizeHtml(pageSize) {
        var pageHtml = '';
        pageHtml += '    <div class="btn-group" role="group" id="pageSizeGroup">';
        pageHtml += '        <button type="button" class="btn btn-secondary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">';
        pageHtml += '            每页 <span id="pagerPageSize">' + pageSize + '</span> 条';
        pageHtml += '        </button>';
        pageHtml += '        <div class="dropdown-menu">';
        self.pageInfo.pageSizes.forEach(function (item, index) {
            pageHtml += '            <a class="dropdown-item" href="javascript:;" data-value="' + item + '">' + item + '</a>';
        });
        pageHtml += '        </div>';
        pageHtml += '    </div>';
        return pageHtml;
    }

    function getPageHtml() {
        var pageInfo = self.pageInfo;
        var pageHtml = '';

        if (pageInfo.hasPrevPage) {
            pageHtml += '<div class="btn btn-secondary page-item" data-value="' + (pageInfo.pageIndex - 1) + '">上一页</div>';
        } else {
            pageHtml += '<div class="btn btn-light disabled">上一页</div>';
        }

        pageHtml += '    <div class="btn-group" role="group">';
        pageHtml += '        <button type="button" class="btn btn-secondary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">';
        pageHtml += '            第 <span id="pagerPageIndex">' + pageInfo.pageIndex + '</span> 页';
        pageHtml += '        </button>';
        pageHtml += '        <div class="dropdown-menu">';

        if (1 < pageInfo.navigateFirstPage) {
            pageHtml += '            <a class="dropdown-item page-item" href="javascript:;" data-value="' + 1 + '">' + 1 + '</a>';
        }

        for (var pageIndex = pageInfo.navigateFirstPage; pageIndex <= pageInfo.navigateLastPage; pageIndex++) {
            pageHtml += '            <a class="dropdown-item page-item" href="javascript:;" data-value="' + pageIndex + '">' + pageIndex + '</a>';
        }

        if (pageInfo.pageCount > pageInfo.navigateLastPage) {
            pageHtml += '            <a class="dropdown-item page-item" href="javascript:;" data-value="' + pageInfo.pageCoun + '">' + pageInfo.pageCoun + '</a>';
        }

        pageHtml += '        </div>';
        pageHtml += '    </div>';

        if (pageInfo.hasNextPage) {
            pageHtml += '<div class="btn btn-secondary page-item" data-value="' + (pageInfo.pageIndex + 1) + '">下一页</div>';
        } else {
            pageHtml += '<div class="btn btn-light disabled">下一页</div>';
        }

        return pageHtml;
    }
}
