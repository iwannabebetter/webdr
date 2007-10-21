package com.webdrbstruts.app.webapp.action;

import com.webdrbstruts.app.webapp.action.BaseAction;
import com.webdrbstruts.app.model.TipoSangre;
import com.webdrbstruts.app.service.GenericManager;


import java.util.List;

public class TipoSangreAction extends BaseAction {
    private GenericManager<TipoSangre, Long> tiposangreManager;
    private List tiposangres;

    public void setPersonManager(GenericManager<TipoSangre, Long> tiposangreManager) {
        this.tiposangreManager = tiposangreManager;
    }

    public List getTipoSangres() {
        return tiposangres;
    }

    public String list() {
    	tiposangres = tiposangreManager.getAll();
        return SUCCESS;
    }
}