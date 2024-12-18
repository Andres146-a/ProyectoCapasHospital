package com.hospital.repositorios;

import com.hospital.UI.MainController;

public abstract class BaseController {
    protected MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
