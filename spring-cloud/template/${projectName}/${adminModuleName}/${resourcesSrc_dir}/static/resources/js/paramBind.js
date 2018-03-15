
/*设置日期后，绑定v-model*/
$('input[type=datetime]').change(function() {
    var vmodel = $(this).data('v-model');
    if (vmodel) {
        var vmodelSplit = vmodel.split('.');
        if (vmodelSplit.length == 1) {
            Vue.set(app, vmodel, $(this).val());
        } else if (vmodelSplit.length == 2) {
            var target = vmodelSplit[0];
            var key = vmodelSplit[1];
            Vue.set(app[target], key, $(this).val());
        }
    }
});
