package pl.uz.mercury.view.optioninternalframe.common;

public class MercuryCrudOptionLocalization
	extends MercuryOptionLocalization
{
	public final String	add;
	public final String	show;
	public final String	update;
	public final String	delete;
	public final String	refresh;

	public final String	filtr;
	public final String	find;

	public MercuryCrudOptionLocalization(String options, String add, String show, String update, String delete, String refresh, String filtr, String find)
	{
		super(options);
		
		this.add = add;
		this.show = show;
		this.update = update;
		this.delete = delete;
		this.refresh = refresh;

		this.filtr = filtr;
		this.find = find;
	}
}
