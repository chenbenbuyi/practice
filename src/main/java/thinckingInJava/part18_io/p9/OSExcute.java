package thinckingInJava.part18_io.p9;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author chen
 * @date 2020/12/21 23:46
 * @Description
 */
public class OSExcute {
    public static void command(String command) {
        boolean err = false;
        try {
            Process process = new ProcessBuilder(command.split(" ")).start();
            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String s;
            while ((s = reader.readLine()) != null) {
                System.out.println(s);
            }
            BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((s = error.readLine()) != null) {
                System.err.println(s);
                err = true;
            }
        } catch (IOException e) {
            if (!command.startsWith("CMD/C")) {
                command("CMD/C " + command);
            } else
                throw new RuntimeException(e);
        }
        if (err) {
            throw new OSExcuteException("执行错误，命令为：" + command);
        }
    }

    public static void main(String[] args) {
        OSExcute.command("java -version");
    }
}
