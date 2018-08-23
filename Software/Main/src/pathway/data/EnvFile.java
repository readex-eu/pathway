package pathway.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnvFile {
	private String dateTime;
	private String executable;
	private String arguments;
	private Map<String, String> environment;
	private List<String> modules;

	public EnvFile()
	{
		environment = new HashMap<String, String>();
		environment.put("ERROR", "Error getting environment");
		modules = new ArrayList<String>();
		modules.add("Error getting modules");
	}

	public String getDateTime()
	{
		return dateTime;
	}
	public void setDateTime(String dateTime)
	{
		this.dateTime = dateTime;
	}
	public String getExecutable()
	{
		return executable;
	}
	public void setExecutable(String executable)
	{
		this.executable = executable;
	}
	public String getArguments()
	{
		return arguments;
	}
	public void setArguments(String arguments)
	{
		this.arguments = arguments;
	}
	public Map<String, String> getEnvironment()
	{
		return environment;
	}
	public void setEnvironment(Map<String, String> environment)
	{
		this.environment = environment;
	}
	public List<String> getModules()
	{
		return modules;
	}
	public void setModules(List<String> modules)
	{
		this.modules = modules;
	}
}
