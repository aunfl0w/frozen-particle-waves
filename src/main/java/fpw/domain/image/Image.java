package fpw.domain.image;

public class Image {
	
	byte data[] = null;
	
	/**
	 * used in the content type header
	 */
	String contentType = "";

	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
}
