package com.gupao.proxy.gpproxy;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * * @Package com.gupao.proxy.gpproxy
 * * @Description: ${todo}
 * * @author caiwei
 * * @date 2019/3/19
 **/
public class CwProxy {

    private static final String ln = "\r\n";

    public static Object newProxyInstance(CwClassLoader loader,
                                          Class<?>[] interfaces,
                                          CwInvocationHandler h)
            throws IllegalArgumentException {
        try {
            //1..生成代理对象源码
            String src = generateSrc(interfaces);
            //2.将代理对象持久化
            String filePath = CwProxy.class.getResource("").getPath();
            File file = new File(filePath + "$Proxy0.java");
            FileWriter fw = new FileWriter(file);
            fw.write(src);
            fw.flush();
            fw.close();

            //3.编译字节码文件
            JavaCompiler jc = ToolProvider.getSystemJavaCompiler();
            StandardJavaFileManager manager = jc.getStandardFileManager(null, null, null);
            Iterable<? extends JavaFileObject> javaFileObjects = manager.getJavaFileObjects(file);
            JavaCompiler.CompilationTask task = jc.getTask(null, manager, null, null, null, javaFileObjects);
            task.call();
            manager.close();

            Class<?> proxyClass = loader.findClass("$Proxy0");
            Constructor<?> constructor = proxyClass.getConstructor(CwInvocationHandler.class);
            return constructor.newInstance(h);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private static String generateSrc(Class<?>[] interfaces) {
        Method[] methods = interfaces[0].getMethods();
        StringBuffer sb = new StringBuffer();
        sb.append("package com.gupao.proxy.gpproxy;").append(ln);
        sb.append("import java.lang.reflect.*;").append(ln);
        sb.append("public class $Proxy0 implements ").append(interfaces[0].getName()).append("{").append(ln);
        sb.append("private CwInvocationHandler h;").append(ln);
        sb.append("public ").append("$Proxy0(CwInvocationHandler h){").append(ln);
        sb.append("this.h = h;").append(ln);
        sb.append("}").append(ln);
        for(Method m: methods){
            sb.append("public ").append(m.getReturnType()).append(" ").append(m.getName()).append("(){").append(ln);
            sb.append("try{").append(ln);
            sb.append("Method m = ").append(interfaces[0].getName()+".class.getMethod(\""+ m.getName() +"\",new Class[]{});").append(ln);
            sb.append("this.h.invoke(this,m,null);").append(ln);
            sb.append("}catch(Throwable e){").append(ln);
            sb.append("e.printStackTrace();");
            sb.append("}").append(ln);
            sb.append("}").append(ln);

        }
        sb.append("}");
        return sb.toString();
    }
}
