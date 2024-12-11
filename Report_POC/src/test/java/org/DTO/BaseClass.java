package org.DTO;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class BaseClass {
    public static final String reportPath = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss").format(new Date());
}
