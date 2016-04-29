/**
 * Created by Administrator on 2016/4/20.
 */
butterflyApp.controller('displayCenterCtrl', function ($scope, $modal, $http) {


    //记录当前选择地址的情况下是否有收到socket消息
    $scope.hasReceiveSocketData = false;


    var getAddress = function(){
        $http({
            url:'/getAddress',
            method:'GET',
            headers: {'Content-Type':'application/x-www-form-urlencoded'},
        }).success(function(data,header,config,status){

            console.log(data);

            $scope.deviceAddress = data.address;
        }).error(function(data,header,config,status){
        });
    };

    getAddress();

    $scope.range = function (min, max, step) {
        step = step || 1;
        var input = [];
        for (var i = min; i <= max; i += step) {
            input.push(i);
        }
        return input;
    };

    $scope.updateSetting = function (key) {

        var index = -1;

        for(var i=0;i<$scope.settingMetas.length;i++){
            if($scope.settingMetas[i].key == key){

                index = $scope.settingMetas[i].index;
                console.log($scope.settingMetas[i]);
                break;
            }
        }

        var dataJson=$.param({index:index,key:key,value:$scope.settingValues[key]});


        $http({
            url:'/saveSettings',
            method:'POST',
            headers: {'Content-Type':'application/x-www-form-urlencoded'},
            data:dataJson
        }).success(function(data,header,config,status){

        }).error(function(data,header,config,status){
        });

        // todo save
        console.log(key, $scope.settingValues[key]);
    };

    $scope.showStatus = function (destValue, options) {

        var text = "Not set";

        if (options && options.length) {
            angular.forEach(options, function (option) {
                if (option.value == destValue) {
                    text = option.text;
                    return;
                }
            });
        }

        return text;
    };

    // 设置功能的设计：由一个list组成，包含index，name，key, type（类型），disable，[以及值范围的options组成]
    // 类型分为：1，text；2，number（min, max）；3，单选（options）
    // options的组成包含text，value
    $scope.getNumberRanges = function (start, end, gap) {
        var rangeValues = $scope.range(start, end, gap);
        var rangeOptions = [];
        angular.forEach(rangeValues, function (value) {
            rangeOptions.push({text: value, value: value});
        });

        return rangeOptions;
    };

    $scope.settingValues = {password: '123', rs485: 3, bumpSwitch: 1};
    $scope.settingMetas = [
        {index: 0, name: "密码", key: "password", type: 1, options: []}, {
            index: 1,
            name: "RS485地址",
            key: "rs485address",
            type: 3,
            options: $scope.getNumberRanges(1, 31, 1)
        }, {
            index: 2,
            name: "换泵选择",
            key: "choosePump",
            type: 3,
            options: [{value: 0, text: '有换泵功能'},
                {value: 1, text: '固定1号泵'},
                {value: 2, text: ' 固定2号泵'}]
        }, {
            index: 3,
            disable: 1,
            name: "保留",
            key: "reserved1",
            type: 1
        }, {
            index: 4,
            disable: 1,
            name: "保留",
            key: "reserved2",
            type: 1
        }, {
            index: 5,
            name: "屏蔽启动时间",
            key: "restartTime",
            type: 3,
            options: [{value: 2, text: '2秒'},
                {value: 3, text: '3秒'},
                {value: 4, text: '4秒'},
                {value: 5, text: '5秒'},
                {value: 6, text: '6秒'}]
        }, {
            index: 6,
            name: "保护选择",
            key: "protectMode",
            type: 3,
            options: [{value: 0, text: '定时限打开'},
                {value: 1, text: '反时限打开'}]
        }, {
            index: 7,
            name: "电机功率选择",
            key: "motorPower",
            type: 3,
            options: [{value: 0, text: '0.55kw'},
                {value: 1, text: '0.75kw'},
                {value: 2, text: '1.1kw'},
                {value: 3, text: '1.5kw'},
                {value: 4, text: '2.2kw'},
                {value: 5, text: '3.0kw'},
                {value: 6, text: '4.0kw'},
                {value: 7, text: '5.5kw'},
                {value: 8, text: '7.5kw'}]
        }, {
            index: 8,
            name: "过流保护值",
            key: "overCurrentProtectVal",
            type: 3,
            options: [{value: 0, text: '关闭过流保护功能'},
                {value: 1, text: '过流120%'},
                {value: 2, text: '过流130%'},
                {value: 3, text: '过流140%'}]
        }, {
            index: 9,
            name: "过流保护延时",
            key: "overCurrentProtectTime",
            type: 2,
            min: 5,
            max: 120
        }, {
            index: 10,
            name: "欠流保护值",
            key: "underCurrentProtectVal",
            type: 3,
            options: [{value: 0, text: '关闭欠流保护功能'},
                {value: 1, text: '欠流50%'},
                {value: 2, text: '欠流65%'},
                {value: 3, text: '欠流80%'}]
        }, {
            index: 11,
            name: "欠流保护延时",
            key: "underCurrentProtectTime",
            type: 2,
            min: 5,
            max: 120
        }, {
            index: 12,
            name: "电压选择",
            key: "voltage",
            type: 3,
            options: [{value: 0, text: '220v'},
                {value: 1, text: '380v'}]
        }, {
            index: 13,
            name: "过压保护值",
            key: "overVoltageProtectVal",
            type: 3,
            options: [{value: 220, text: '220-260v'},
                {value: 380, text: '380-450v'},
                {value: 0, text: '无过压保护功能'}]
        }, {
            index: 14,
            name: "过压保护延时",
            key: "overVoltageProtectTime",
            type: 2,
            min: 5,
            max: 120
        }, {
            index: 15,
            name: "欠压保护值",
            key: "underVoltageProtectVal",
            type: 3,
            options: [{value: 220, text: '180-220v'},
                {value: 380, text: '310-380v'},
                {value: 0, text: '无欠压保护功能'}]
        }, {
            index: 16,
            name: "欠压保护延时",
            key: "underVoltageProtectTime",
            type: 2,
            min: 5,
            max: 120
        }, {
            index: 17,
            name: "巡检周期(天)",
            key: "inspectPeriod",
            type: 2,
            min: 6,
            max: 45
        }, {
            index: 18,
            name: "巡检时间(秒)",
            key: "inspectTime",
            type: 2,
            min: 3,
            max: 120
        }, {
            index: 19,
            name: "切换时间(秒)",
            key: "switchDelay",
            type: 2,
            min: 3,
            max: 10
        }, {
            index: 20,
            name: "电压校准",
            key: "voltageCalibration",
            type: 2,
            min: 0,
            max: 9999
        }, {
            index: 21,
            name: "电流1校准",
            key: "current1Calibration",
            type: 2,
            min: 0,
            max: 999999
        }, {
            index: 22,
            name: "电流2校准",
            key: "current2Calibration",
            type: 2,
            min: 0,
            max: 999999
        }, {
            index: 23,
            name: "LED亮度调整",
            key: "ledBrightness",
            type: 3,
            options: $scope.getNumberRanges(1, 7, 1)
        }, {
            index: 24,
            name: "电流校准方式",
            key: "currentCalibrationMode",
            type: 3,
            options: [{value: 0, text: '线性校准'},
                {value: 1, text: '查表校准'}]
        }, {
            index: 25,
            name: "软件版本",
            key: "version",
            type: 1
        }

    ];

    $scope.deviceAddress = 0;
    $scope.updateAddress = function(deviceAddress){

        var dataJson=$.param({address:deviceAddress});

        $http({
            url:'/setAddress',
            method:'POST',
            headers: {'Content-Type':'application/x-www-form-urlencoded'},
            data:dataJson
        }).success(function(data,header,config,status){

            $scope.hasReceiveSocketData = false ;

        }).error(function(data,header,config,status){
        });
    }

    // Pre-fetch an external template populated with a custom scope
    var myOtherModal = $modal({
        scope: $scope,
        templateUrl: 'public/modals/settings.html',
        show: false,
        title: '设置列表',
        content: "hello suff"
    });
    // Show when some event occurs (use $promise property to ensure the template has been loaded)
    $scope.showModal = function () {
        getSettings();
        myOtherModal.$promise.then(myOtherModal.show);
    };

    var getSettings = function (){
        $http({
            url:'/getSettings',
            method:'GET'
        }).success(function(data,header,config,status){

        }).error(function(data,header,config,status){
        });

    };

    /**
     * 手动/自动模式切换
     */
    $scope.modeSwitch = function(){

        var autoMode=$scope.led[0].light;
        var manualMode=$scope.led[1].light;

        var dataJson=$.param({autoMode:autoMode,manualMode:manualMode});

        $http({
            url:'/setMode',
            method:'POST',
            headers: {'Content-Type':'application/x-www-form-urlencoded'},
            data:dataJson
        }).success(function(data,header,config,status){

        }).error(function(data,header,config,status){
        });
    };


    $scope.inspect = function(){
        $http({
            url:'/inspect',
            method:'POST',
        }).success(function(data,header,config,status){

        }).error(function(data,header,config,status){
        });
    };


    $scope.motor1 = function(){
        $http({
            url:'/motor1',
            method:'POST',
        }).success(function(data,header,config,status){

        }).error(function(data,header,config,status){
        });
    };

    $scope.motor2 = function(){
        $http({
            url:'/motor2',
            method:'POST',
        }).success(function(data,header,config,status){

        }).error(function(data,header,config,status){
        });
    }


    $scope.led = [{},{},{},{},{},{},{}];
    $scope.vals = [{},{},{}];

    var address = "ws://" + "localhost:9005";

    var ws;

    try {
        ws = new WebSocket(address);
    } catch (e) {
        console.log("[WebSocketUtil]连接WEB SOCKET服务器异常", e);
    }

    if (ws) {
        /**
         * 监听来自web socket服务器的任务信息
         *
         * @param evt 状态更新的任务信息
         */
        ws.onmessage = function (resp) {

            $scope.hasReceiveSocketData = true ;

            var str = resp.data;

            if (str.indexOf('V_') >= 0) {
                $scope.vals = [];
                str = str.substr(2);
                var dataArrays = str.split("-");
                for (var i = 0; i < dataArrays.length && i < 3; i++) {
                    $scope.vals.push({value: dataArrays[i]});
                }
            } else if (str.indexOf('L_') >= 0) {
                str = str.substr(2);
                $scope.led = [];
                var lights = str.split("");
                for (var index = lights.length-1; index >=0; index--) {
                    if (index == 0) {
                        var on = false;
                        if ('1' == lights[index]) {
                            on = true;
                        }
                        $scope.led.push({name: '巡检', light: on});
                    } else if (index == 1) {
                        var on = false;
                        if ('1' == lights[index]) {
                            on = true;
                        }
                        $scope.led.push({name: '巡检', light: on});
                    } else if (index == 2) {
                        var on = false;
                        if ('1' == lights[index]) {
                            on = true;
                        }
                        $scope.led.push({name: '巡检', light: on});
                    } else if (index == 3) {

                        var on = false;
                        if ('1' == lights[index]) {
                            on = true;
                        }
                        $scope.led.push({name: '泵2', light: on});
                    } else if (index == 4) {
                        var on = false;
                        if ('1' == lights[index]) {
                            on = true;
                        }
                        $scope.led.push({name: '泵1', light: on});
                    } else if (index == 5) {
                        var on = false;
                        if ('1' == lights[index]) {
                            on = true;
                        }
                        $scope.led.push({name: '手动', light: on});
                    } else if (index == 6) {
                        var on = false;
                        if ('1' == lights[index]) {
                            on = true;
                        }
                        $scope.led.push({name: '自动', light: on});
                    }

                }
            } else if (str.indexOf('S_') >= 0) {
                str = str.substr(2);
                var dataJson=JSON.parse(str);

                console.log(dataJson);
                $scope.settingValues = dataJson }


            $scope.$apply();
        }
    }
});