package controllers;

import play.mvc.Controller;
import servise.DeviceOperator;

public class Application extends Controller {
    private static int  R=1;

    public static void index() {
        DeviceOperator.getStatus();
//        OperationObserver.getSettings();
        render();
    }
}