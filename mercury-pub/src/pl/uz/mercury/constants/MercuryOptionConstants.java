package pl.uz.mercury.constants;

public enum MercuryOptionConstants
{
	MERCHANDISE ("merchandise jndi name");
	
	MercuryOptionConstants(String jndiName)
	{
		JNDI_NAME = jndiName;
	}
	
	public final String JNDI_NAME;
}
