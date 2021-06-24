package util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;

public class XMLUtil {

	public static <T> T xmlToBean(Class<T> clazz, File file) throws IOException, JAXBException {
		JAXBContext jc = JAXBContext.newInstance(clazz);
		Unmarshaller u = jc.createUnmarshaller();
		try (InputStream is = new FileInputStream(file)) {
			return (T) u.unmarshal(is);
		} catch (IOException | JAXBException e) {
			throw e;
		}
	}

	public static void beanToXml(Object obj, Class<?> clazz, File file) throws JAXBException, IOException {
		JAXBContext context = JAXBContext.newInstance(clazz);
		Marshaller marshaller = context.createMarshaller();
		//格式化输出，即按标签自动换行，否则就是一行输出
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		//设置编码（默认编码就是utf-8）
		marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		//是否省略xml头信息，默认不省略（false）
		marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false);
		FileWriter writer = new FileWriter(file);
		marshaller.marshal(obj, writer);
		// 控制台输出
		marshaller.marshal(obj,System.out);
		writer.flush();
	}

}