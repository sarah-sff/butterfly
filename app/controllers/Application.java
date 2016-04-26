package controllers;

import play.mvc.Controller;
import servise.CoilObserver;
import servise.func.OperationObserver;

public class Application extends Controller {
    private static int R = 1;

    public static void index() {

        CoilObserver.send();

//        RegisterObserver.send();
//        DeviceOperator.getStatus();
//        OperationObserver.getSettings();
        render();
    }


    public static void getSettings() {
        OperationObserver.getSettings();
    }
}