package ru.mirea;

import org.springframework.context.ConfigurableApplicationContext;

public class SetCtx {

    private ConfigurableApplicationContext ct;

    private Start st;

    public SetCtx() {
    }

    public SetCtx(Start st) {
        this.st = st;
    }

    public void setCt(ConfigurableApplicationContext ct1) {
        ct = ct1;
        MireaApplication.ctx = ct;
        st.starts();
    }
}
