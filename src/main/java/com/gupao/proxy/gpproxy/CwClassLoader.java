package com.gupao.proxy.gpproxy;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * * @Package com.gupao.proxy.gpproxy
 * * @Description: ${todo}
 * * @author caiwei
 * * @date 2019/3/20
 **/
public class CwClassLoader extends ClassLoader {

    private File filePath;

    public CwClassLoader() {
        String path = CwClassLoader.class.getResource("").getPath();
        this.filePath = new File(path);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String classFile = (CwClassLoader.class.getPackage().getName()+"."+name);
        if(filePath.exists()){
            File file = new File(filePath, name.replaceAll("\\.","/")+".class");
            if(file.exists()){
                FileInputStream in = null;
                ByteArrayOutputStream bao = null;
                try{
                    in = new FileInputStream(file);
                    bao = new ByteArrayOutputStream();
                    byte[] bytes = new byte[1024];
                    int length;
                    while ((length = in.read(bytes)) != -1){
                        bao.write(bytes,0,length);
                    }
                    return defineClass(classFile,bao.toByteArray(),0,bao.size());
                }catch (Exception e){
                    e.printStackTrace();
                }


            }
        }
        return null;
    }
}
