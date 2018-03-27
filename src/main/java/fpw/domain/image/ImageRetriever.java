package fpw.domain.image;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface ImageRetriever {
	@JsonIgnore public Image getImage() throws IOException;
	public String getID();
	public void setID(String name);
	public boolean isExtraprocessing();

}
