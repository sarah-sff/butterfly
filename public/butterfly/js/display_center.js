/**
 * Created by Administrator on 2016/4/20.
 */
butterflyApp.controller('displayCenterCtrl', function ($scope) {

    $scope.led=[];
    $scope.vals=[];

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

            var str=resp.data;
            console.log(str);
            console.log(str.indexOf('V'));

            if(str.indexOf('V_')>=0){
                $scope.vals=[];
                str=str.substr(2);
                console.log(str+"aaa");
                var dataArrays = str.split("-");
                console.log(dataArrays);
                for(var i=0;i<dataArrays.length && i<3;i++){
                    $scope.vals.push({value:dataArrays[i]});
                }
            }else if(str.indexOf('L_')>=0){
                str=str.substr(2);
                while(str && str.length<=7){
                    str+="0";
                }

                console.log(str);
                $scope.led=[];
                var lights = str.split("");
                for (var index = 0; index < lights.length; index++) {
                    if(index==0){
                        var on= false;
                        if( '1'==lights[index]){
                            on=true;
                        }
                        $scope.led.push({name:'自动',light:on});
                    }else if(index==1){
                        var on= false;
                        if( '1'==lights[index]){
                            on=true;
                        }
                        $scope.led.push({name:'手动',light:on});
                    }else if(index==2){
                        var on= false;
                        if( '1'==lights[index]){
                            on=true;
                        }
                        $scope.led.push({name:'泵1',light:on});
                    }else if(index==3){

                        var on= false;
                        if( '1'==lights[index]){
                            on=true;
                        }
                        $scope.led.push({name:'泵2',light:on});
                    }else if(index==4){
                        var on= false;
                        if( '1'==lights[index]){
                            on=true;
                        }
                        $scope.led.push({name:'巡检',light:on});
                    }else if(index==5){
                        var on= false;
                        if( '1'==lights[index]){
                            on=true;
                        }
                        $scope.led.push({name:'巡检',light:on});
                    }else if(index==6){
                        var on= false;
                        if( '1'==lights[index]){
                            on=true;
                        }
                        $scope.led.push({name:'巡检',light:on});
                    }

                }
            }




            $scope.$apply();
            console.log( $scope.led);
        }
    }
});