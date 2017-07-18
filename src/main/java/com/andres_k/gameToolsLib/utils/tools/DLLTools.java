package com.andres_k.gameToolsLib.utils.tools;

import com.andres_k.gameToolsLib.utils.configs.GameInfo;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;

/**
 * Created by kevin on 18/07/2017.
 */
public class DLLTools {
    private final static String LIB_BIN = "/native/windows/";
    private final static String[] WIN_DLL32 = new String[]{"jinput-dx8", "jinput-raw", "lwjgl", "OpenAL32"};
    private final static String[] WIN_DLL64 = new String[]{"jinput-dx8_64", "jinput-raw_64", "lwjgl64","OpenAL64"};
    private final static String[] LIN_DLL32 = new String[]{};
    private final static String[] LIN_DLL64 = new String[]{};
    private final static String[] MAC_DLL = new String[]{};

    public static void init() {
        Console.write("DLL: Loading");
        String[] dlls = new String[]{};
        String os = System.getProperty("os.name");
        String arch = System.getProperty("os.arch");

        if (os.contains("Windows")) {
            if (arch.contains("64")) {
                dlls = WIN_DLL64;
            } else {
                dlls = WIN_DLL32;
            }
        } else if (os.contains("Linux")) {
            if (arch.contains("64")) {
                dlls = LIN_DLL64;
            } else {
                dlls = LIN_DLL32;
            }
        } else if (os.contains("Mac")) {
            dlls = MAC_DLL;
        }

        loadFromJar(dlls);
    }

    /**
     * When packaged into JAR extracts DLLs, places these into
     */
    private static void loadFromJar(String[] dlls) {
        String path = GameInfo.get().getGamePathTMP() + LIB_BIN;
        try {
            for (String dll : dlls) {
                File f = new File(path + dll + ".dll");
                if (!f.exists()) {
                    loadLib(LIB_BIN, path, dll);
                }
            }

            System.setProperty("java.library.path", path);
            Field fieldSysPath = ClassLoader.class.getDeclaredField( "sys_paths" );
            fieldSysPath.setAccessible( true );
            fieldSysPath.set( null, null );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Puts library to temp dir and loads to memory
     */
    private static void loadLib(String srcPath, String finalPath, String name) throws Exception {
        name = name + ".dll";
        try {
            InputStream in = DLLTools.class.getResourceAsStream(srcPath + name);
            File fileOut = new File(finalPath + name);
            OutputStream out = FileUtils.openOutputStream(fileOut);
            IOUtils.copy(in, out);
            in.close();
            out.close();
        } catch (Exception e) {
            throw new Exception("Failed to load required DLL", e);
        }
    }
}
