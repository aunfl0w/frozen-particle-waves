package fpw.domain.image;

public class Image {
	
	byte data[] = null;
	
	/**
	 * used in the content type header
	 */
	String contentType = "";
	String id = "";
	
	public Image(String id, String contentType){
		this.contentType = contentType;
		this.id = id;
	}

	public String getId() {
		return id;
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	public String getContentType() {
		return contentType;
	}
}
