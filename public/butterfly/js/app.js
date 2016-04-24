var butterflyApp = angular.module('butterflyApp', ['mgcrea.ngStrap', "xeditable"]);
butterflyApp.run(function (editableOptions) {
    editableOptions.theme = 'bs3'; // bootstrap3 theme. Can be also 'bs2', 'default'
})