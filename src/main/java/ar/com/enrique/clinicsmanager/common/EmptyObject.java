package ar.com.enrique.clinicsmanager.common;

import java.io.Serializable;

public class EmptyObject implements Serializable {

    private static final long serialVersionUID = 5327322585561894369L;

    private boolean success = true;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
