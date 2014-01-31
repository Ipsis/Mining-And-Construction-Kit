package com.ipsis.mackit.helper;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.ipsis.mackit.lib.Reference;

import cpw.mods.fml.common.FMLLog;

/**
* Straight from EE3
*
* @author pahimar
*/
public class LogHelper
{

    private static Logger eeLogger = Logger.getLogger(Reference.MOD_ID);

    public static void init() {
    	
        eeLogger.setParent(FMLLog.getLogger());
    }

    public static void log(Level logLevel, Object object) {

        if (object != null)
            eeLogger.log(logLevel, object.toString());
        else
            eeLogger.log(logLevel, "null");
    }

    public static void severe(Object object) {
    	
        log(Level.SEVERE, object);
    }

    public static void debug(Object object) {
    	
        if (object != null)
            log(Level.WARNING, String.format("[DEBUG] %s", object.toString()));
        else
            log(Level.WARNING, "[DEBUG] null");
    }

    public static void warning(Object object) {
    	
        log(Level.WARNING, object);
    }

    public static void info(Object object) {

        log(Level.INFO, object);
    }

    public static void config(Object object) {

        log(Level.CONFIG, object);
    }

    public static void fine(Object object) {

        log(Level.FINE, object);
    }

    public static void finer(Object object) {

        log(Level.FINER, object);
    }

    public static void finest(Object object) {

        log(Level.FINEST, object);
    }
}