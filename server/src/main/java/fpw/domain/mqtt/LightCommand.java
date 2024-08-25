package fpw.domain.mqtt;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * a command for a light
 * 
 * @author dad
 *
 */
public class LightCommand {
	public String commandName;
	public String commandValue;

	public String getCommandValue() {
		return commandValue;
	}

	public void setCommandValue(String commandValue) {
		this.commandValue = commandValue;
	}

	public String getCommandName() {
		return commandName;
	}

	public void setCommandName(String commandName) {
		this.commandName = commandName;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
