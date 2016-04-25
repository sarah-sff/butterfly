package controllers;

import play.mvc.Controller;
import servise.CoilObserver;

public class Application extends Controller {
    private static int R = 1;

    public static void index() {

        CoilObserver.send();
//        DeviceOperator.getStatus();
//        OperationObserver.getSettings();
        render();
    }
}