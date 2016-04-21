/**
 * Created by Administrator on 2016/4/20.
 */
butterflyApp.controller('displayCenterCtrl', function ($scope) {

    $scope.led={};

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

            while(str && str.length<=7){
                str+="0";
            }

            console.log(str);
            var lights = str.split("");
            for (var index = 0; index < lights.length; index++) {
                if(index==0){
                    $scope.led.automatic= lights[index];
                }else if(index==1){
                    $scope.led.manual= lights[index];
                }else if(index==2){
                    $scope.led.pump1= lights[index];
                }else if(index==3){
                    $scope.led.pump2= lights[index];
                }else if(index==4){
                    $scope.led.inspection= lights[index];
                }
            }


            $scope.$apply();
            console.log( $scope.led);
        }
    }
});