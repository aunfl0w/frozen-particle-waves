package fpw.domain.image;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FailImage extends Image {

	static byte[] data;
	static {
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("failed.jpg");
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int length = 0;
		while (length >= 0) {
			try {
				length = is.read(buffer);
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (length >= 0)
				bos.write(buffer, 0, length);

		}
		data = bos.toByteArray();
	}

	public FailImage(String id) {
		super(id, "image/jpeg");
	}

	@Override
	public byte[] getData() {
		return FailImage.data;
	}

	@Override
	public void setData(byte[] data) {
	}

}
