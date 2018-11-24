
$(function() {

    $('input[type="datetime"]').datetimepicker({
        autoclose: 1
    });

    $('input[type="date"]').datetimepicker({
        format: 'yyyy-mm-dd',
        minView: "month",
        autoclose: 1
    });

    /*设置日期后，绑定v-model*/
    $('input[type=datetime]').change(function () {
        var vmodel = $(this).data('v-model');
        commonFun.setVueModel(app, vmodel, $(this).val());
    });
});
